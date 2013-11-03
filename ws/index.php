<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';
$PATH = "http://mybudgetpal.com/krystek/";


	echo 'Go to the server script: <a href="server.php">server.php</a><br/>';
	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();
	echo '<br/><h1>Users list</h1>';
	$ws->userPrint();
	echo "<h1>Sample usage</h1>";
	echo 'Log in as \'test\' user <a target="_blank" href="'. $PATH .'server.php?a=login&user=test&pass=ed5465b9220df9ce176d0bf30d6a317729bd9d37e4ae1cc015cb24c99af1df49">[click]</a> <br/>';
	echo 'Log out <a target="_blank" href="'. $PATH .'server.php?a=logout">[click]</a> <br/>';




?>
