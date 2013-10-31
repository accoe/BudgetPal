<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';


	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();
	if ($ws->isConnected())
		echo 'Connection has been established!';
	else
		echo 'Connection has been not established';


	echo '<br/><h1>Users list</h1>';
	$ws->userPrint();


	echo '<br/><h1>Users list</h1>';
	$nick = 'krystek';
	if ($ws->userNameAlreadyExists($nick))
		echo "User $nick exists";
	else
		echo "User $nick doesn't exist";

	$G = $_GET;

	// 'a' means 'action'
	if(isset($_GET['a']))
	{
		$action = $G['a'];
		if ($ws->isLogged())
		{
			echo "User ".$_SESSION['username']." is currently logged in";

			//Wylogowanie
			if ($action == 'logout')
			{	
				$ws->Logout();
			}


		}
		else{
			echo "Nobody is currently logged in";	

			// Rejestracja uzytkownika
			if ($action == 'register')
			{
				if (isset($G['user']) && isset($G['pass']) && isset($G['email']))
					$ws->register($G['user'],$G['pass'],$G['email']);

			}

			// Logowanie uzytkownika
			if ($action == 'login')
			{
				if (isset($G['user']) && isset($G['pass']))
					$ws->login($G['user'],$G['pass']);

			}

		}
	}
?>
