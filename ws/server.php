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

	$mustBeLoggedIn = array('logout','budget');
	$mustBeLoggedOut = array('login','register');

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
				show($ws->Logout());

			//Lista budzetow
			if ($action == 'budget')
				show($ws->getBudgets());

			// Jezeli jestesmy zalogowani a probujemy wykonac akcję tylko dla wylogowanych
			if (in_array($action, $mustBeLoggedOut))
				show(status('MUST_BE_LOGGED_OUT'));
		}
		//NIEZALOGOWANY
		else{
			// Rejestracja uzytkownika
			if ($action == 'register')
			{
				if (isset($G['user']) && isset($G['pass']) && isset($G['email']))
					show($ws->register($G['user'],$G['pass'],$G['email']));
				else 
					show(status('WRONG_PARAMETERS'));
			}
				
			// Logowanie uzytkownika
			if ($action == 'login')
			{
				if (isset($G['user']) && isset($G['pass']))
					show($ws->login($G['user'],$G['pass']));

			}

			// Jezeli jestesmy wylogowani a probujemy wykonac akcję tylko dla zalogowanych
			if (in_array($action, $mustBeLoggedIn))
				show(status('MUST_BE_LOGGED_IN'));
		}
	}
?>
