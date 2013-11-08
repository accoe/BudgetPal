<?php
$errors = array();
$infos = array();
$ok = array();


$errors['USERNAME_TAKEN'] = 'This username is already taken!';
$errors['EMAIL_TAKEN'] = 'This email is already taken!';
$errors['WRONG_PARAMETERS'] = 'Wrong parameters, check manual!';
$errors['MUST_BE_LOGGED_IN'] = 'To perform this action you have to be logged in';
$errors['MUST_BE_LOGGED_OUT'] = 'To perform this action you have to be logged out';
$errors['WRONG_PASS'] = 'Username or password is wrong';
$errors['BUDGET_NOT_ADDED'] = 'Budget cannot be added';
$errors['BUDGET_NOT_UPDATED'] = 'Budget cannot be updated';
$errors['BUDGET_NOT_DELETED'] = 'This budget cannot be deleted';
$errors['NO_SUCH_BUDGET'] = 'Budget doesn\'t exist';
$errors['BUDGET_EXISTS'] = 'Choose a different name for this budget';
$errors[''] = '';

$infos['NO_BUDGETS'] = 'You dont have any budgets defined';

$ok['BUDGET_DELETED'] = 'Budget has been deleted successfully';
$ok['BUDGET_UPDATED'] = 'Budget has been updated successfully';
$ok['BUDGET_ADDED'] = 'New budget has been added successfully';
$ok['LOGGED_OUT'] = 'You have been logged out';
$ok['LOGGED_IN'] = 'You have been logged in';
$ok['REGISTERED'] = 'User has been successfully registered';

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