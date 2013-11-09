<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300' rel='stylesheet' type='text/css'>
<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/toggle.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.0/js/toastr.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.0.1/css/toastr.min.css" rel="stylesheet"/>
<title>MyBugetPal.com - planowanie bud&#380;etu domowego</title>
<script>
function showToast()
{	
	toastr.success('Wygenerowano nowy plik serwera!')
}
</script>

<body onload="">
<div id="header">
<div class="logo"><img src="/budget_logo_280_white.png"></div>
</div>

<div id="content">
<div class="box_min">Go to the server: <a href="server.php">server.php</a> <a onclick="showToast()" href="utils/createServer.php">(generate new one)</a><br>Get not translated messages: <a href="utils/translator.php">translator.php</a></div>
<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

require_once 'mainClass.php';
require_once 'config.php';
require 'utils/methodsFormatter.php';
$PATH = "http://mybudgetpal.com/krystek/";

	$ws = new mainClass($user,$pass,$host,$base);
	$ws->Connect();

	echo '<h1>User list</h1>';
	echo '<table>
	<tr><td>ID</td><td>Username</td><td>Email</td><td>Registration date</td><td>Hashed password</td></tr>';
	$ws->userPrint();
	echo '</table>';

	echo "<h1>Methods</h1><ul>";
	$formatter = new methodsFormatter('mainClass');
	$formatter->EachInSeparateBlock();

?>


<div id="footer">
Created by <a href="http://swidurski.pl">Krystian &#346;widurski</a> and <a href="http://www.behance.net/accoe">Tomasz Kasprzak</a>
<div style="float:right"><a href="https://github.com/TheKrystek/BudgetPal">Follow this project on GitHub <img src="img/github.png"/></a></div>
</div>
</div>
</body>