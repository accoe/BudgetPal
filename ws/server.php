<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';
require_once 'english.php';

$json = true;
define('USE_JSON',true);
define('PRETTY_PRINT',true);

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


			if ($action == 'test')
			{	
				show($ws->test());
			}


		//ZALOGOWANY
		if ($ws->isLogged())
		{
			//Wylogowanie
			if ($action == 'logout')
			{	
				show($ws->Logout());
			}
			//Lista budzetow
			if ($action == 'budget')
			{	
				show($ws->getBudgets());
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
					show($ws->login($G['user'],$G['pass']));

			}

		}
	}
?>
