<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';
$json = true;

if ($json)
	require_once 'json.php';
else
	require_once 'default.php';


	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();
	$G = $_GET;

	// 'a' means 'action'
	if(isset($_GET['a']))
	{
		$action = $G['a'];
		//ZALOGOWANY
		if ($ws->isLogged())
		{
			echo "User ".$_SESSION['username']." is currently logged in";

			//Wylogowanie
			if ($action == 'logout')
			{	
				$ws->Logout();
			}


		}
		//NIEZALOGOWANY
		else{
			// Rejestracja uzytkownika
			if ($action == 'register')
			{
				if (isset($G['user']) && isset($G['pass']) && isset($G['email']))
					show($ws->register($G['user'],$G['pass'],$G['email']));

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
