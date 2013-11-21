<?php
require_once '../mainClass.php';
require_once '../config.php';

Class javaGenerator{

	var $class;
	var $method;
	var $instance;
	var $doNotCreate;

	public function __construct($class){
		$this->class = $class;
		$this->showComments = false;
		$this->showMethodBody = true;
		$this->counter=0;
		$this->publicOnly = true;
		$this->doNotCreate = array('__construct','UserPrint','isLogged');
	}

	public function Initialize($instance_name){
		$this->instance = $instance_name;		
		$this->doNotCreate = array('__construct','Connect','Close','UserPrint','isLogged');
		
	}

	public function generateJavaCode(){
		$class_methods = get_class_methods($this->class);
		echo '
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class WebService {
	GetStatus status;
	URLConnection connection;
	String json;
	String wsPath = "http://mybudgetpal.com/ws/";
	
	private String getJsonFromUrl(String url) throws IOException
	{
		try {
			connection = new URL(wsPath + url).openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
		String json = "";
		String line;
		while ((line = in.readLine()) != null) {
			json += line.trim();
		}
		in.close();
		this.json = json;
		return json;
	}';
		foreach ($class_methods as $method_name) {		
			if (!in_array($method_name,$this->doNotCreate))
			{
				echo $this->createMethodHeader($method_name)."<br/>";
				echo "{<br/>"; // poczatek metody
				echo "\t".$this->createMethodBody($method_name);

				echo "}<br/>"; // koniec metody
			}
		}
		echo '}<br>'; // koniec klasy
	}

	
	
	
	public function createMethodHeader($method_name){
		$method = new ReflectionMethod($this->class, $method_name);
		$params = $method->getParameters();
		$params_num = $method->getNumberOfParameters();
		$params_type = array();
		$comment = $method->getDocComment();
		$header = "";
		if (strlen($comment)>0){
			if ($this->showComments)
				printf("\n    %s\n", str_replace("\t"," ",$comment));			
			if ($method->isPublic())
				$header .= 'public ';
			else
				$header .= 'private ';
			
			$header .= trim($this->getValueFromComment("@return",$comment))." ";
			$params_type = $this->getParamTypesFromComment($comment);
		}
		else
		{
			if ($method->isPublic())
				$header .= 'public ';
			else
				$header .= 'private ';
			$header .=  'void ';
		}
		$header .=  $method_name.'(';

		if ($params_num > 0){
			if (count($params_type) == $params_num){
		    	for ($i = 0; $i < $params_num -1; $i++) {
				$header .=  $params_type[$i]." ".$params[$i]->getName();
				if ($params[$i]->isDefaultValueAvailable()){
					if ($params_type[$i] == 'string')
						$header .= ' = "'.$params[$i]->getDefaultValue().'"';
					else
						$header .= ' = '.$params[$i]->getDefaultValue();
				}
		    		$header .= ', ';
		    	}
		    	$header .=  $params_type[$params_num -1]." ".$params[$params_num -1]->getName();
		    	if ($params[$i]->isDefaultValueAvailable()){
		    		if ($params_type[$i] == 'string')
		    			$header .= ' = "'.$params[$i]->getDefaultValue().'"';
		    		else
		    			$header .= ' = '.$params[$i]->getDefaultValue();
		    	}
			}
			else 
				die($params_num." ".count($example_val).'Number of parameters in method header and number of examples isn\'t equal');
		}
		$header .=  ')';
		return $header;
	}


	public function createMethodBody($method_name){	
		$method = new ReflectionMethod($this->class, $method_name);
		$comment = $method->getDocComment();
		$return = trim($this->getValueFromComment("@return",$comment));
		$primitives = array('double', 'int');

		$body = '';
		$body .= 'String url = "'.$this->createUrl($method_name).';'."<br>";
		$body .= 'this.getJsonFromUrl(url);';
		$body .= 'this.status = new GetStatus(json); ';
		if ($return == 'boolean'){


		}
		else{
			$body .= 'if (this.status.isSet()){';
			if (in_array($return, $primitives))
				$body .= '	return -1;';
			else
				$body .= '	return null;';
			$body .= '}';
			$body .= 'else';
			$body .= '{';
			$body .= '	return new Gson().fromJson(json, '.$return.'.class);';
			$body .= '}';
		}
		return $body;
	}



	private function getValueFromComment($value, $comment){
		$offset = strpos($comment,$value);
		$pos = strpos($comment," ",$offset);
		return substr($comment, $pos, ( strpos($comment, PHP_EOL, $pos) ) - $pos);
	}

	private function getDescriptionFromComment($method_name){
		$method = new ReflectionMethod($this->class, $method_name);
		$comment = $method->getDocComment();
		echo '<div class="desc">';
		echo trim($this->getValueFromComment('@desc', $comment));
		echo '</div>';
	}
	
	private function getParamTypesFromComment($comment)
	{
		$params = $this->getValueFromComment('@param',$comment);
		$params_array = explode(',',$params);
		foreach ($params_array as &$param) {
			$param = trim($param);
		}
		return $params_array;
	}
	
	private function createUrl($methodName){
		$method = new ReflectionMethod($this->class, $methodName);
		$params = $method->getParameters();
		$params_num = $method->getNumberOfParameters();
		$link = "server.php?a=".strtolower($methodName);
			if ($params_num > 0){
				for ($i = 0; $i < $params_num -1; $i++) {
					$arg = "&".trim($params[$i]->getName()).'="+'.trim($params[$i]->getName()).'+"';
					$link .= $arg;
				}
				$arg = "&".trim(end($params)->getName()).'="+'.trim(end($params)->getName());
			}
			else
				$link .= '"';
			return $link. $arg;
		}
	
	

	private function methodBody($method){
		$filename = $method->getFileName();
		$start_line = $method->getStartLine(); 
		$end_line = $method->getEndLine();
		$length = $end_line - $start_line;
		$source = file($filename);
		$body = implode("", array_slice($source, $start_line, $length));
		print_r($body);
	}

}

$x = new javaGenerator("mainClass");
$x->Initialize('ws');
$x->generateJavaCode('mainClass');

?>