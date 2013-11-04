<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/toggle.css" />


<title>MyBugetPal.com - planowanie bud&#380;etu domowego</title>

<div id="header">
<div class="logo"><img src="/budget_logo_280_white.png"></div>
</div>

<div id="content">
<div class="box_min">Go to the server: <a href="server.php">server.php</a></div>
<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';
require 'methodsFormatter.php';
$PATH = "http://mybudgetpal.com/krystek/";

	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();

	echo '<h1>User list</h1>';
	echo '<table>
	<tr><td>ID.</td><td>Username</td><td>Email</td><td>Registration date</td><td>Hashed password</td></tr>';
	$ws->userPrint();
	echo '</table>';

	echo "<h1>Sample usage</h1><ul>";
	echo '<li>&rarr; Log in as \'test\' user <a target="_blank" href="'. $PATH .'server.php?a=login&user=test&pass=ed5465b9220df9ce176d0bf30d6a317729bd9d37e4ae1cc015cb24c99af1df49">[click]</a></li>';

	echo '<li>&rarr; Log out <a target="_blank" href="'. $PATH .'server.php?a=logout">[click]</a></li>';
	echo '</ul>';
	echo "<h1>Methods</h1><ul>";
	$formatter = new methodsFormatter('mainClass');
	$formatter->EachInSeparateBlock();

?>


<div id="footer">&nbsp;</div>
Created by <a href="http://swidurski.pl">Krystian Swidurski</a> and <a href="http://www.behance.net/accoe">Tomasz Kasprzak</a>
<div style="float:right"><a href="https://github.com/TheKrystek/BudgetPal">Follow this project on GitHub <img src="img/github.png"/></a></div>
</div>