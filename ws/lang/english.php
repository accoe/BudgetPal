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
$errors['CANNOT_GET_PRODUCT_CATEGORIES'] = 'Cannot get product categories';
$errors['NOT_SUPPORTED'] = 'This operation is not yet supported';
$errors['STUB_METHOD'] = 'This method is a stub (hopefully just for now)';
$errors['PRODUCT_CATEGORY_NOT_ADDED'] = 'Product category cannot be added';
$errors['PRODUCT_CATEGORY_NOT_UPDATED'] = 'Product category cannot be updated';
$errors['PRODUCT_CATEGORY_NOT_DELETED'] = 'Product category cannot be deleted';
$errors['PRODUCT_CATEGORY_EXISTS'] = 'This product category already exists';
$errors['PRODUCT_CATEGORY_NOT_EXISTS'] = 'This product category doesn\'t exist';
$errors['CANNOT_GET_INCOMES_CATEGORIES'] = 'Cannot get incomes categories';
$errors['INCOME_CATEGORY_EXISTS'] = 'This income category already exists';
$errors['INCOME_CATEGORY_NOT_ADDED'] = 'Income category cannot be added';
$errors['PRODUCT_EXISTS'] = 'This product already exists';
$errors['PRODUCT_NOT_ADDED'] = 'This product cannot be added';
$errors['CANNOT_GET_EXPENSES'] = 'Cannot get expenses';
$errors['EXPENSE_NOT_ADDED'] = 'This expense cannot be added';

$errors['NO_SUCH_ORDER'] = 'There is no order type';
$errors['CANNOT_GET_PRODUCTS'] = 'Cannot get list of products';
$errors['CANNOT_GET_INCOMES'] = 'Cannot get list of incomes';
$ok['INCOME_ADDED'] = 'Income has been added successfully';
$errors['INCOME_NOT_ADDED'] = 'Income has not been added';
$errors['CANNOT_GET_ACTIVITIES'] = 'Cannot get list of recent activities';
$errors['NO_INCOMES_ADDED'] = 'You don\'t have any incomes' ;
$errors['NO_EXPENSES_ADDED'] = 'You don\'t have any expenses';
$errors['NO_NOTIFICATIONS'] = 'You don\'t have any notifications';
$errors['NOTIFICATION_MARKED'] = 'Notification has been marked as read';
$errors['NOTIFICATION_NOT_MARKED'] = 'Notification has not been marked as read';
$errors['NO_SUCH_NOTIFICATION'] = 'This notification doesn\'t exist';
$errors['UPDATED'] = 'Updated successfully';
$errors['NOT_UPDATED'] = 'Not updated';
$errors['SCHEDULED_EXPENSE_ADDED'] = 'Scheduled expense has been added successfully';
$errors['SCHEDULED_EXPENSE_NOT_ADDED'] = 'Scheduled expense has not been added';
$errors['SCHEDULED_EXPENSE_ALREADY_EXISTS'] = 'Scheduled expense already exists';
$errors['NO_SCHEDULED_EXPANSES'] = 'You don\'t have any scheduled expenses';
$errors['SCHEDULED_INCOME_ADDED'] = 'Scheduled income has been added successfully';
$errors['SCHEDULED_INCOME_NOT_ADDED'] = 'Scheduled income has not been added';
$errors['SCHEDULED_INCOME_ALREADY_EXISTS'] = 'Scheduled income already exists';
$errors['NO_SCHEDULED_INCOMES'] = 'You don\'t have any scheduled incomes';
$errors['NO_EXPENSES_IN_MONTH'] = 'You don\'t have any expenses in this month';
$errors['NO_INCOMES_IN_MONTH'] = 'You don\'t have any incomes in this month';
$errors['NO_LIMITS'] = 'You don\'t have any limits defined';
$errors['LIMIT_ADDED'] = 'Limit has been added successfully';
$errors['LIMIT_NOT_ADDED'] = 'Limit has not been added';
$errors['NEW_NOTIFICATIONS_ADDED'] = 'New notification has been added';
$errors['NO_NEW_NOTIFICATIONS'] = 'You don\'t have any new notifications';
$errors['NO_LIMITS'] = 'You don\'t have any limits defined';



$ok['PRODUCT_ADDED'] = 'Product has been added successfully';
$ok['EXPENSE_ADDED'] = 'Expanse has been added successfully';
$ok['INCOME_CATEGORY_ADDED'] = 'Income category has been added successfully';
$ok['PRODUCT_CATEGORY_ADDED'] = 'Product category has been added successfully';
$ok['PRODUCT_CATEGORY_UPDATED'] = 'Product category has been updated successfully';
$ok['PRODUCT_CATEGORY_DELETED'] = 'Product category has been deleted successfully';
$ok['BUDGET_DELETED'] = 'Budget has been deleted successfully';
$ok['BUDGET_UPDATED'] = 'Budget has been updated successfully';
$ok['BUDGET_ADDED'] = 'New budget has been added successfully';
$ok['LOGGED_OUT'] = 'You have been logged out';
$ok['LOGGED_IN'] = 'You have been logged in';
$ok['REGISTERED'] = 'User has been successfully registered';

$infos['NO_BUDGETS'] = 'You dont have any budgets defined';





function status($key){
	global $errors, $infos, $ok;

	if (array_key_exists($key, $errors))
		return array('error' => $errors[$key]);

	if (array_key_exists($key, $infos))
		return array('info' =>  $infos[$key]);

	if (array_key_exists($key, $ok))
		return array('ok' =>  $ok[$key]);

	return array('error' => 'No such key: \''.$key.'\'');
}
?>