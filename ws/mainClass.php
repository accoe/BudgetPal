<?php

class mainClass
{

    var $mysqli;
    var $DBHOST;
    var $DBNAME;
    var $DBUSER;
    var $DBPASS;
	
    public function __construct($user, $password, $host, $database) 
    {
        $this->DBUSER = $user;
        $this->DBPASS = $password;
        $this->DBHOST = $host;
        $this->DBNAME = $database;
        $this->InitializeSession();
    }

    public function Connect()
    {
    	$this->mysqli = new mysqli($this->DBHOST, $this->DBUSER, $this->DBPASS, $this->DBNAME);
    	$this->mysqli->connect_error;
    }
    
    public function Close()
    {
    	$this->mysqli->close();
    }
    
    public function UserPrint()
    {
    	$result = $this->mysqli->query("SELECT * FROM Uzytkownicy");
    	while ($row = $result->fetch_assoc())
    		echo "<tr><td>{$row['ID_Uzytkownika']}.</td><td>{$row['login']}</td><td>{$row['email']}</td><td>{$row['dataRejestracji']}</td><td>".substr($row['haslo'],0,30)."...</td></tr>";
    }

    public function isLogged()
    {
    	if(isset($_SESSION['userId'], $_SESSION['username'], $_SESSION['login_string'])) {
    		$userId = $_SESSION['userId'];
    		$login_string = $_SESSION['login_string'];
    		$username = $_SESSION['username'];
    		$user_browser = $_SERVER['HTTP_USER_AGENT'];
    		if ($s = $this->mysqli->prepare("SELECT haslo FROM Uzytkownicy WHERE ID_Uzytkownika = ? LIMIT 1")) {
    			$s->bind_param('i', $userId);
    			$s->execute();
    			$s->store_result();
    			if($s->num_rows == 1) {
    				$s->bind_result($password);
    				$s->fetch();
    				$login_check = hash('sha512', $password.$user_browser);
    				if($login_check == $login_string) {
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }	
    
    
    
    private function InitializeSession() 
    {
        $session_name = 'myBudgetPal';
        ini_set('session.use_only_cookies', 1);
        $cookieParams = session_get_cookie_params();
        session_set_cookie_params($cookieParams["lifetime"], $cookieParams["path"], $cookieParams["domain"]);
        session_name($session_name);
        session_start();
        session_regenerate_id();
    }


    private function isConnected() 
    {
        if (!$this->mysqli->connect_error)
            return true;
        else
            return false;
    }
    
    private function DoesUserHaveBudgetWithName($userId,$name)
    {
    	$userId = $_SESSION['userId'];
    	if ($s = $this->mysqli->prepare("SELECT ID_Budzetu FROM Budzet where ID_Uzytkownika = ? AND nazwa= ?")) {
    		$s->bind_param('is',$userId,$name);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows > 0)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    
    private function BudgetExists($userId,$budget_id)
    {
    	$userId = $_SESSION['userId'];
    	if ($s = $this->mysqli->prepare("SELECT ID_Budzetu FROM Budzet where ID_Uzytkownika = ? AND ID_Budzetu = ?")) {
    		$s->bind_param('is',$userId,$budget_id);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows > 0)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    
    private function UserNameAlreadyExists($username)
    {
    	if ($s = $this->mysqli->prepare("SELECT login FROM Uzytkownicy where login = ?")) {
    		$s->bind_param('s', $username);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows == 1)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    
    
    private function UserEmailAlreadyExists($email)
    {
    	if ($s = $this->mysqli->prepare("SELECT email FROM Uzytkownicy where email = ?")) {
    		$s->bind_param('s', $email);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows == 1)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    
    
    private function ProductCategoryExist($name)
    {
    	if ($s = $this->mysqli->prepare("SELECT nazwa FROM KategorieProduktow where nazwa = ?")) {
    		$s->bind_param('s', $name);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows == 1)
    			return true;
    		else
    			return false;
    	}
    	return false;
    
    }
    
    


    
    
    
    
    
    
    
    



   

    /** 
     * @desc Funkcja rejestruje uzytkownika
     * @param string, string, string
     * @return array
     * @example krystek, trunde, krystek@example.com
     * @logged false
     */
    public function Register($login, $password, $email) 
    {
        if (!$this->UserNameAlreadyExists($login)) {
            if (!$this->UserEmailAlreadyExists($email)) {
                if ($s = $this->mysqli->prepare("INSERT INTO Uzytkownicy (login, haslo, email, ip, userAgent, potwierdzony) values (?, ?, ?, ?, ?, 0)")) {
                    $password = hash('sha256',$password);
                    $s->bind_param('sssss', $login,$password,$email,$_SERVER['REMOTE_ADDR'],$_SERVER['HTTP_USER_AGENT']);
                    $s->execute();
                    $s->store_result();
                }
                return status('REGISTERED');
            }
            else
                return status('EMAIL_TAKEN');
        }
        else
            return status('USERNAME_TAKEN');
    }

	/** 
	  * @desc Zaloguj uzytkownika
	  * @param string, string
	  * @return array
	  * @example test, ed5465b9220df9ce176d0bf30d6a317729bd9d37e4ae1cc015cb24c99af1df49
	  * @logged false
	  */
    public function Login($user, $password)
    {
        if ($this->UserNameAlreadyExists($user)) {
            if ($s = $this->mysqli->prepare("SELECT ID_Uzytkownika, login, haslo FROM Uzytkownicy where login = ? AND haslo = ?")) {
                $s->bind_param('ss', $user,$password);
                $s->execute();
                $s->store_result();
                $s->bind_result($userId, $username, $password);
                $s->fetch();
                if ($s->num_rows == 1) {
                    // Poprawnie zalogowano
                    $user_browser = $_SERVER['HTTP_USER_AGENT'];
                    $_SESSION['userId'] = $userId;
                    $_SESSION['username'] = $username;
                    $_SESSION['login_string'] = hash('sha512', $password.$user_browser);
                    return status('LOGGED_IN');
                }
            }
        }
        else
            return status('WRONG_PASS');
    }

	/** 
	  * @desc Sprawdza czy uzytkownik jest zalogowany
	  * @param void
	  * @return bool
	  */


    /** 
      * @desc Wylogowuje uztykownika
      * @param void
      * @return array
      * @example void
      * @logged true
      */
    public function Logout() 
    {
        $_SESSION = array();
        $params = session_get_cookie_params();
        setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"]);
        session_destroy();
        return status('LOGGED_OUT');
    }

    /** 
      * @desc Zwraca liste budzetow nalezacych do uzytkownika
      * @param void
      * @return array
      * @example void
      * @logged true
      */
    public function GetBudgets() 
    {
            if ($s = $this->mysqli->prepare("SELECT ID_Budzetu, nazwa, opis FROM Budzet where ID_Uzytkownika = ?")) {
                $s->bind_param('i', $_SESSION['userId']);
                $s->execute();
                $s->bind_result($ID_Budzetu,$nazwa,$opis);
                $arr = array();
                while ( $s->fetch() ) {
                    $row = array('ID_Budzetu' => $ID_Budzetu,'nazwa' => $nazwa,'opis' => $opis);
                    $arr[] = $row;
                }
                return array('count' =>  $s->num_rows,
                             'budgets' => $arr);
            }
            else
                return status('NO_BUDGETS');
    }
   
    
    /** 
      * @desc Dodaje budzet 
      * @param string, string
      * @return array
      * @example testowy, Testowy opis budzetu
      * @logged true
      */
    public function AddBudget($name, $description)
    {
        $userId = $_SESSION['userId'];
        if ($this->DoesUserHaveBudgetWithName($userId,$name))
        	return status('BUDGET_EXISTS');
        else{
	    	if ($s = $this->mysqli->prepare("INSERT INTO Budzet (ID_Uzytkownika,nazwa,opis) values (?, ?, ?);")) {
	    		$s->bind_param('iss',$userId,$name, $description);
	    		$s->execute();
	    		$s->bind_result();
	    		return status('BUDGET_ADDED');
	    	}
	    	else
	    		return status('BUDGET_NOT_ADDED');
	    }
    }
    
    /**
     * @desc Modyfikuje zdefiniowany budzet
     * @param int, string, string
     * @return array
     * @example 14, Nowa nazwa, zmieniony opis
     * @logged true
     */
    public function UpdateBudget($budget_id,$name,$description)
    {
    	$userId = $_SESSION['userId'];
    	if (!$this->BudgetExists($userId,$budget_id))
    		return status('NO_SUCH_BUDGET');
    	{
    		// Pobierz stare wartości
    		if ($s = $this->mysqli->prepare("SELECT nazwa, opis FROM Budzet where where ID_Uzytkownika = ? ID_Budzetu = ?")) {
    			$s->bind_param('ii',$userId, $budget_id);
    			$s->execute();
    			$s->bind_result($nazwa,$opis);
    			$arr = array();
    			$s->fetch();
    			if (empty($name))
    				$name = $nazwa;
    			if (empty($description))
    				$description = $opis;
    		}
    		if ($s = $this->mysqli->prepare("UPDATE Budzet set nazwa = ?, opis = ? where ID_Uzytkownika = ? AND ID_Budzetu = ?;")) {
    			$s->bind_param('ssii',$name,$description,$userId,$budget_id);
    			$s->execute();
    			$s->bind_result();
    			return status('BUDGET_UPDATED');
    		}
    		else
    			return status('BUDGET_NOT_UPDATED');
    	}
    }
    
    /** 
      * @desc Usuwa zdefiniowany budzet
      * @param int
      * @return array
      * @example 14
      * @logged true
      */
    public function DeleteBudget($budget_id)
    {
        $userId = $_SESSION['userId'];
        if (!$this->BudgetExists($userId,$budget_id))
        		return status('NO_SUCH_BUDGET');
        { 
	    	if ($s = $this->mysqli->prepare("DELETE FROM Budzet where ID_Uzytkownika = ? AND ID_Budzetu = ?;")) {
	    		$s->bind_param('ii',$userId,$budget_id);
	    		$s->execute();
	    		$s->bind_result();
	    		return status('BUDGET_DELETED');
	    	}
	    	else
	    		return status('BUDGET_NOT_DELETED');
        }
    }
    
    
    /**
     * @desc Pobiera listę kategorii produktów
     * @param void
     * @return array
     * @example void
     * @logged true
     */
    public function GetProductCategories()
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_KatProduktu, nazwa FROM KategorieProduktow")) {
    		$s->execute();
    		$s->bind_result($ID_KatProduktu,$nazwa);
    		$arr = array();
            	while ( $s->fetch() ) {
               		$row = array('ID_KatProduktu' => $ID_KatProduktu,'nazwa' => $nazwa);
                    $arr[] = $row;
                }
                return array('count' =>  $s->num_rows,
                             'categories' => $arr);
    	}
    	else
    		return status('CANNOT_GET_PRODUCT_CATEGORIES');
    }


    /** 
      * @desc Dodaje kategorie do listy kategorii produktow
      * @param string
      * @return array
      * @example owoce
      * @logged true
      */
    public function AddProductCategory($name)
    {
    	$userId = $_SESSION['userId'];
    	if ($this->ProductCategoryExist($name))
    		return status('PRODUCT_CATEGORY_EXISTS');
    	else{
    		if ($s = $this->mysqli->prepare("INSERT INTO KategorieProduktow (nazwa) values (?);")) {
    			$s->bind_param('s',$name);
    			$s->execute();
    			$s->bind_result();
    			return status('PRODUCT_CATEGORY_ADDED');
    		}
    		else
    			return status('PRODUCT_CATEGORY_NOT_ADDED');
    	}
    }

    /**
     * @desc Dodaje nowy wydatek
     * @param int, string, string
     * @return array
     * @example owoce
     * @logged true
     */
    public function AddExpense($budget_Id,$name,$cost,$purchase = -1)
    {
    	/*
        $userId = $_SESSION['userId'];
        if (!$this->BudgetExists($userId,$budget_id))
        	return status('NO_SUCH_BUDGET');
        else{
	    	if ($s = $this->mysqli->prepare("INSERT INTO Budzet (ID_Uzytkownika,nazwa,opis) values (?, ?, ?);")) {
	    		$s->bind_param('iss',$userId,$name, $description);
	    		$s->execute();
	    		$s->bind_result();
	    		return status('EXPENSE_ADDED');
	    	}
	    	else
	    		return status('EXPENSE_NOT_ADDED');
	    }
	    */
    	return status('STUB_METHOD');
    }
    
    
    
    
    
    
    // usuwanie i edycja produktow z budzetu
    // dodawanie nowego produktu do listy produktow
    // 		jezeli nie ma na liscie w slowniku to dodaje
    // zrobic slownik wartosc z paragonu - id produktu na liscie
    // zmiana danych uzytkownika
    // dodawanie przychodow
    // modyfikowanie i usuwanie przychodow
    
    // raporty pokaz wydatki wg produktow - x dni, tydzien, x tygodni, miesiac, x miesiecy, rok, caly czas
    // raporty pokaz przychodu wg produktow - x dni, tydzien, x tygodni, miesiac, x miesiecy, rok, caly czas
    // dodawanie modyfikowanie i usuwanie planowanych wydatkow i przychodow - automatyczne powiadomienie
    
    // dodawanie zakupow - nazwa i sklep oraz lista produktow dwie metody- dodaje zakupy, dodaj wydatki do zakupow
    
    // dodawanie zleceń stałych 
    //dodawnie powiadomień - przy dodaniu zlecenia stałego dodaj powiadomienie
    
    
}
?>