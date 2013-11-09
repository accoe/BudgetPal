<?php
// Wczytaj plik z jezykiem i wstaw do tablicy klucze przetlumaczonych komunikatow
$lines = file('../lang/english.php');
$translated = array();

foreach ($lines as $line_num => $line) {
 	$pattern = @'/\[([^.]{3,})\]/';
 	preg_match($pattern, htmlspecialchars($line), $matches);
	$status = $matches[1];
	$translated[] = $status;
}


$lines = file('../mainClass.php');

 foreach ($lines as $line_num => $line) {
 	$pattern = @'/return status\(([^.]+)\);/';
 	preg_match($pattern, htmlspecialchars($line), $matches);
	$status = $matches[1];
	if (!in_array($status, $translated))
		echo '$errors['.$status. '] = \'\';'."<br>";
}
?>