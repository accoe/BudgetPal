<?php
error_reporting(E_ALL);
require_once 'mainClass.php';
require_once 'config.php';


	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();
	if ($ws->isConnected())
		echo 'Connection has been established!';

	$ws->userPrint();
	$ws->userNameAlreadyExists('Krystek');


	$G = $_GET;

	// 'a' means 'action'
	if(isset($G['a']))
	{
		$action = $G['a'];
		if ($action == 'register')
		{
			if (isset($G['user']) && isset($G'pass']) && isset($G['email']))
				$ws->register()
					


		}





	}

?>
