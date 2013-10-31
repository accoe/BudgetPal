<?php


class mainClass
{

	var $mysqli;

	var $DBHOST;
	var $DBNAME;
	var $DBUSER;
	var $DBPASSWORD;



	public function __construct($user, $password, $host, $database){
		$this->DBUSER = $user;
		$this->DBPASSWORD = $password;
		$this->DBHOST = $host;
		$this->DBNAME = $database;
	} 

	public function Connect(){
		$this->mysqli = new mysqli($this->DBHOST, $this->DBUSER, $this->DBPASSWORD, $this->DBNAME);
	}

	public function Close(){
		$mysqli->close();
	}

	public function isConnected(){
		if ($connection)
			return true;
		else
			return false;
	}


	public function UserPrint(){
		$result = $mysqli->query("SELECT * FROM Uzytkownicy");
	    while ($row = $result->fetch_row()))
			echo $row['login']." ";
	}


	public function UserNameAlreadyExists($username){	
		if ($s = $mysqli->prepare("SELECT login,haslo,email FROM Uzytkownicy where login = ?")) { 
			$s->bind_param('s', $username);
			$s->execute();
			$s->store_result();
			if ($s->num_rows == 1)
				return true;
			else
				return false;
	  	}
	  	return false;
	}

	public function UserEmailAlreadyExists($email){
		$result = mysql_query("SELECT * FROM Uzytkownicy where email = '$email'");		
		if (mysql_num_rows($result) > 0)
			return true;
		else 
			return false;
	}


	public function Register($USER, $password, $email){
		if (!$this->USERNameAlreadyExists($USER)){
			if (!thist->USEREmailAlreadyExists($email)){
				echo 'USER has been registered';

			}
			else
				echo 'This email is already taken!';
		}	
		else
			echo 'This USERname is already taken!';
	}























}


?>