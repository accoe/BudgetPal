<?php


class mainClass
{

	var $connection;

	var $host;
	var $database;

	var $user;
	var $password;



	public function __construct($user, $password, $host, $database){
		$this->user = $user;
		$this->password = $password;
		$this->host = $host;
		$this->database = $database;
	} 

	public function Connect(){
		$this->connection = mysql_connect($this->host, $this->database, $this->password);
		if (!$this->connection) 
    		die('Could not connect: ' . mysql_error());
    	else
    		mysql_select_db($this->database);
	}

	public function Close(){
		mysql_close($this->connection);
	}

	public function isConnected(){
		if ($connection)
			return true;
		else
			return false;
	}

}


?>