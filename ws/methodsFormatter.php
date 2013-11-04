<?php

Class methodsFormatter{

	var $class;
	var $showComments;
	var $showMethodBody;
	var $counter;

	public function __construct($class){
		$this->class = $class;
		$this->showComments = false;
		$this->showMethodBody = true;
		$this->counter=0;
	}


	public function AllInOneBlock(){
		echo '<pre class="prettyprint php">';
		$class_methods = get_class_methods($this->class);
		foreach ($class_methods as $method_name) {
			$this->formatSingleMethod($method_name);
		}
		echo '</pre>';	
	}

	public function EachInSeparateBlock(){
		$class_methods = get_class_methods($this->class);
		foreach ($class_methods as $method_name) {
			echo '<pre class="prettyprint php">';
			$this->formatSingleMethod($method_name);
			echo '</pre>';	
		}
	}


	public function formatSingleMethod($method_name){
		$this->counter++;
		$method = new ReflectionMethod($this->class, $method_name);
		$params = $method->getParameters();
		$params_num = $method->getNumberOfParameters();
		$comment = $method->getDocComment();
		$header = "";
		if (strlen($comment)>0){
			if ($this->showComments)
				printf("\n    %s\n", str_replace("\t"," ",$comment));
			$header .= trim($this->getValueFromComment("@return",$comment))." ";
		}
		else
		{
			$header .=  'void ';

		}
		$header .=  $method_name.'(';

		if ($params_num == 0){
			$header .=  'void';
		}
		else{
	    	for ($i = 0; $i < $params_num -1; $i++) {
	        	$header .=  $params[$i]->getName().', ';
	    	}
	    	$header .=  $params[$params_num -1]->getName();
		}
		$header .=  ')';


		if ($this->showMethodBody){
			echo '<label class="collapse" for="method_'.$this->counter.'">'.$header.'</label>';
  			echo '<input id="method_'.$this->counter.'" type="checkbox">';
  			echo '<div>';
				$this->methodBody($method);
  			echo '</div>';
		}
		else
		{
			echo $header;
		}
	}


	private function getValueFromComment($value, $comment){
		$offset = strpos($comment,$value);
		$pos = strpos($comment," ",$offset);
		return substr($comment, $pos, ( strpos($comment, PHP_EOL, $pos) ) - $pos);
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

?>