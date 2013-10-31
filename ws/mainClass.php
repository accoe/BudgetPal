<?php


class mainClass
{

	var $mysqli;

	var $DBHOST;
	var $DBNAME;
	var $DBUSER;
	var $DBPASS;



	public function __construct($user, $password, $host, $database){
		$this->DBUSER = $user;
		$this->DBPASS = $password;
		$this->DBHOST = $host;
		$this->DBNAME = $database;

		$this->InitializeSession();
	} 



	private function InitializeSession(){
		$session_name = 'myBudgetPal';
        ini_set('session.use_only_cookies', 1); 
        $cookieParams = session_get_cookie_params(); 
        session_set_cookie_params($cookieParams["lifetime"], $cookieParams["path"], $cookieParams["domain"]); 
        session_name($session_name); 
        session_start();
        session_regenerate_id();
	}

	public function Connect(){
		$this->mysqli = new mysqli($this->DBHOST, $this->DBUSER, $this->DBPASS, $this->DBNAME);
		$this->mysqli->connect_error;
	}

	public function Close(){
		$this->mysqli->close();
	}

	public function isConnected(){
		if (!$this->mysqli->connect_error)
			return true;
		else
			return false;
	}


	public function UserPrint(){
		$result = $this->mysqli->query("SELECT * FROM Uzytkownicy");
	    while ($row = $result->fetch_assoc())
			echo $row['login']."<br/>";
	}


	public function UserNameAlreadyExists($username){	
		if ($s = $this->mysqli->prepare("SELECT login FROM Uzytkownicy where login = ?")) { 
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
		if ($s = $this->mysqli->prepare("SELECT email FROM Uzytkownicy where email = ?")) { 
			$s->bind_param('s', $email);
			$s->execute();
			$s->store_result();
			if ($s->num_rows == 1)
				return true;
			else
				return false;
	  	}
	  	return false;
	}


	public function Register($login, $password, $email){
		if (!$this->UserNameAlreadyExists($login)){
			if (!$this->UserEmailAlreadyExists($email)){
				if ($s = $this->mysqli->prepare("INSERT INTO Uzytkownicy (login, haslo, email, ip, userAgent, potwierdzony) values (?, ?, ?, ?, ?, 0)")) { 
					$password = hash('sha256',$password);
					$s->bind_param('sssss', $login,$password,$email,$_SERVER['REMOTE_ADDR'],$_SERVER['HTTP_USER_AGENT']);
					$s->execute();
					$s->store_result();
	  			}
			}
			else
				return array('error' => 'This email is already taken!');	

		}	
		else{
			return array('error' => 'This username is already taken!');	
		}
	}


	public function Login($login, $password)
	{
		if ($this->UserNameAlreadyExists($login)){
			if ($s = $this->mysqli->prepare("SELECT ID_Uzytkownika, login, haslo FROM Uzytkownicy where login = ? AND haslo = ?")) { 
				$s->bind_param('ss', $login,$password);
				$s->execute();
				$s->store_result();
				$s->bind_result($userId, $username, $password);
				$s->fetch();
				if ($s->num_rows == 1){
					// Poprawnie zalogowano
					$user_browser = $_SERVER['HTTP_USER_AGENT']; 
					$_SESSION['userId'] = $userId; 
					$_SESSION['username'] = $username;
					$_SESSION['login_string'] = hash('sha512', $password.$user_browser);	
				}
  			}
		}	
		else
			echo 'Username or password is wrong';
	}


	public function isLogged(){
		if(isset($_SESSION['userId'], $_SESSION['username'], $_SESSION['login_string'])) {
     		$userId = $_SESSION['userId'];
     		$login_string = $_SESSION['login_string'];
     		$username = $_SESSION['username'];
     		$user_browser = $_SERVER['HTTP_USER_AGENT']; 
     		if ($s = $this->mysqli->prepare("SELECT haslo FROM Uzytkownicy WHERE ID_Uzytkownika = ? LIMIT 1")) { 
		        $s->bind_param('i', $userId);
		        $s->execute(); 
		        $s->store_result();
		        if($s->num_rows == 1) { 
		           $s->bind_result($password);
		           $s->fetch();
		           $login_check = hash('sha512', $password.$user_browser);
		           if($login_check == $login_string) {		              
		              return true;
		           }
		       }
		   }
		}
		return false;
	}


	public function Logout(){
		$_SESSION = array();
		$params = session_get_cookie_params();
		setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"]);
		session_destroy();
	}

}
?>