<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';

	echo '<a href="server.php">server.php</a><br/>';


	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();
	if ($ws->isConnected())
		echo 'Connection has been established!';
	else
		echo 'Connection has been not established';


	echo '<br/><h1>Users list</h1>';
	$ws->userPrint();

	$nick = 'krystek';
	echo "<h1>Does $nick exist?</h1>";
	if ($ws->userNameAlreadyExists($nick))
		echo "User $nick exists";
	else
		echo "User $nick doesn't exist";
?>
