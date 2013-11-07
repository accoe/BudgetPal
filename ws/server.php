<?php
require_once 'mainClass.php';
require_once 'config.php';
require_once 'english.php';

$json = true;
define('USE_JSON', true);
define('PRETTY_PRINT', true);

if ($json)
    require_once 'json.php';
else
    require_once 'default.php';
$ws = new mainClass($user, $pass, $host, $base);
$ws->Connect();
if (isset($_GET['a'])) {
    switch ($_GET['a']) {
        case 'register': {
            if (!$ws->IsLogged()) {
                if (isset($_GET['login']) && isset($_GET['password']) && isset($_GET['email'])) {
                    show($ws->Register($_GET['login'], $_GET['password'], $_GET['email']));
                } else {
                    show(status('WRONG_PARAMETERS'));
                }
            } else {
                show(status('MUST_BE_LOGGED_OUT'));
            }
            break;
        }
        case 'login': {
            if (!$ws->IsLogged()) {
                if (isset($_GET['user']) && isset($_GET['password'])) {
                    show($ws->Login($_GET['user'], $_GET['password']));
                } else {
                    show(status('WRONG_PARAMETERS'));
                }
            } else {
                show(status('MUST_BE_LOGGED_OUT'));
            }
            break;
        }
    }
}
$ws->Close();
?>