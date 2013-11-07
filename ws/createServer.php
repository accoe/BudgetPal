<?php
ob_start();
require_once 'mainClass.php';
require_once 'config.php';



class createServer {
	var $class;
	var $method;
	var $instance;
	
	var $doNotCreate;
	
	
	public function Initialize($instance_name){
		$this->instance = $instance_name;		
		$this->doNotCreate = array('__construct','Connect','Close','UserPrint','isLogged');
		
	}
	
	
	public function createClassHandler($class){
		$this->class = $class;
		$class_methods = get_class_methods($class);
		
		echo '<?php
				require_once \'mainClass.php\';
				require_once \'config.php\';
				require_once \'english.php\';
				
				$json = true;
				define(\'USE_JSON\',true);
				define(\'PRETTY_PRINT\',true);
				
				if ($json)
					require_once \'json.php\';
				else
					require_once \'default.php\';
				$'.$this->instance.' = new mainClass($user,$pass,$host,$base);
				$'.$this->instance.'->Connect();';
		echo 'if (isset($_GET[\'a\'])){switch($_GET[\'a\']){';
		$this->createMethodHandler('Register');
		$this->createMethodHandler('Login');
		//foreach ($class_methods as $method_name)
		//$this->createMethodHandler($method_name);
		echo '}}';
		echo '$'.$this->instance.'->Close();?>';
	}
	
	
	
	private function hasToBeLoggedIn($comment){
		$offset = strpos($comment,'@logged');
		$pos = strpos($comment," ",$offset);
		$logged = substr($comment, $pos, ( strpos($comment, PHP_EOL, $pos) ) - $pos);
		$logged = trim($logged);
		if (strlen($logged) == 0)
			die('Method "'.$this->method.'" has not set the parameter @logged');	
		
		if ($logged == 'true')
			return true;
		else 
			return false;
	}

	public function createMethodHandler($method_name){
		$this->method = $method_name;
		$method = new ReflectionMethod($this->class, $method_name);
		$params = $method->getParameters();
		$params_num = $method->getNumberOfParameters();
		$comment = $method->getDocComment();
		// Generujemy serwer dla wszystkich metod publicznych i nie bedacych konstruktorem
		if ($method->isPublic() && !in_array($method_name,$this->doNotCreate))
		{	
			echo 'case \''.strtolower($method_name).'\' : {';
			// Nie trzeba byc zalogowanym
			if (!$this->hasToBeLoggedIn($comment)){
				echo 'if (!$ws->IsLogged()){';
					echo 'if ('.$this->createIssetForParams($params).'){';
						echo '$'.$this->instance.'->'.$method_name.'('.$this->createGETParams($params).');';
					echo '}else{';
						echo 'show(status(\'WRONG_PARAMETERS\'));';
					echo '}';
				echo '}else{show(status(\'MUST_BE_LOGGED_OUT\'));}';
			}
			else {
				echo 'if ($ws->IsLogged()){';
				echo 'if ('.$this->createIssetForParams($params).'){';
				echo '$'.$this->instance.'->'.$method_name.'('.$this->createGETParams($params).');';
				echo '}else{';
				echo 'show(status(\'WRONG_PARAMETERS\'));';
				echo '}';
				echo '}else{show(status(\'MUST_BE_LOGGED_IN\'));}';
			}
			echo 'break;}';
		}
	}

	private function createIssetForParams($params){
		$condition = "";
		for ($i = 0; $i < count($params)-1; $i++) {
			$arg = 'isset($_GET[\''.$params[$i]->getName().'\']) && ';
			$condition .= $arg;
		}
		$condition .= 'isset($_GET[\''.end($params)->getName().'\'])';
		return $condition;	
	}

	private function createGETParams($params){
		$parameters = "";
		for ($i = 0; $i < count($params)-1; $i++) {
			$arg = '$_GET[\''.$params[$i]->getName().'\'], ';
			$parameters .= $arg;
		}
		$parameters .= '$_GET[\''.end($params)->getName().'\']';
		return $parameters;
	}


}

$x = new createServer();
$x->Initialize('ws');
$x->createClassHandler('mainClass');


$page = ob_get_contents();
ob_end_flush();
$fp = fopen("server.php","w");
fwrite($fp,$page);
fclose($fp);

?>