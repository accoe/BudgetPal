<?php
$errors = array();
$infos = array();
$ok = array();


$errors['USERNAME_TAKEN'] = 'This username is already taken!';



function status($key){
	global $errors, $infos, $ok;

	if (array_key_exists($key, $errors))
		return array('error' => $errors[$key]);

	if (array_key_exists($key, $infos))
		return array('info' =>  $infos[$key]);

	if (array_key_exists($key, $ok))
		return array('ok' =>  $ok[$key]);

	return array('error' => 'No such key!');
}



?>