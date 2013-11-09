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

    /**
     * @desc Sprawdza czy uzytkownik jest zalogowany
     * @param void
     * @return bool
     */
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
    
    
    
    private function GetBudgetByName($name)
    {
    	$userId = $_SESSION['userId'];
    	if ($s = $this->mysqli->prepare("SELECT ID_Budzetu FROM Budzet where ID_Uzytkownika = ? AND nazwa= ?")) {
    		$s->bind_param('is',$userId,$name);
    		$s->execute();
    		$s->bind_result($ID_Budzetu);
    		$s->store_result();
    		$s->fetch();
    		if ($s->num_rows > 0)
    			return $ID_Budzetu;
    		else
    			return false;
    	}
    	return false;
    }
    
    private function DoesBudgetExist($budgetId)
    {
    	$userId = $_SESSION['userId'];
    	if ($s = $this->mysqli->prepare("SELECT ID_Budzetu FROM Budzet where ID_Uzytkownika = ? AND ID_Budzetu = ?")) {
    		$s->bind_param('is',$userId,$budgetId);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows > 0)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    

    
    
    private function GetProductCategoryByName($name)
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_KatProduktu FROM KategorieProduktow where nazwa = ?")) {
    		$s->bind_param('s', $name);
    		$s->execute();
    		$s->bind_result($ID_KatProduktu);
    		$s->store_result();
    		$s->fetch();
    		if ($s->num_rows > 0)
    			return $ID_KatProduktu;
    		else
    			return false;  		
    	}
    	return false;
    
    }
    
    private function DoesProductCategoryExist($product_cat)
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_KatProduktu FROM KategorieProduktow where ID_KatProduktu = ?")) {
    		$s->bind_param('i', $product_cat);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows == 1)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    
    
    //Zwraca id kategorii przychodu
    private function GetIncomeCategoryByName($name)
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_KatPrzychodu FROM KategoriePrzychodow where nazwa = ?")) {
    		$s->bind_param('i', $name);
    		$s->execute();
    		$s->bind_result($ID_KatPrzychodu);
    		$s->store_result();
    		$s->fetch();
    		if ($s->num_rows > 0)
    			return $ID_KatPrzychodu;
    		else
    			return false;
    	}
    	return false;
    
    }
    
    private function DoesIncomeCategoryExist($incomeCat)
    {
    	if ($s = $this->mysqli->prepare("SELECT nazwa FROM KategoriePrzychodow where ID_KatPrzychodu = ?")) {
    		$s->bind_param('i', $incomeCat);
    		$s->execute();
    		$s->store_result();
    		if ($s->num_rows == 1)
    			return true;
    		else
    			return false;
    	}
    	return false;
    }
    

    

    

    private function GetProductByName($name)
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_Produktu FROM Produkty where nazwa = ?")) {
    		$s->bind_param('s', $name);
    		$s->execute();
    		$s->bind_result($ProductID);
    		$s->store_result();
    		$s->fetch();
    		if ($s->num_rows > 0)
    			return $ProductID;
    		else
    			return false;
    	}
    	return false;
    }
    

    
    
    private function DoesProductExist($productId)
    {
    	if ($s = $this->mysqli->prepare("SELECT nazwa FROM Produkty where ID_Produktu = ?")) {
    		$s->bind_param('i', $productId);
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
        if ($this->GetBudgetByName($name))
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
    public function UpdateBudget($budgetId,$name,$description)
    {
    	$userId = $_SESSION['userId'];
    	if (!$this->DoesBudgetExist($budgetId))
    		return status('NO_SUCH_BUDGET');
    	{
    		// Pobierz stare wartości
    		if ($s = $this->mysqli->prepare("SELECT nazwa, opis FROM Budzet where where ID_Uzytkownika = ? ID_Budzetu = ?")) {
    			$s->bind_param('ii',$userId, $budgetId);
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
    			$s->bind_param('ssii',$name,$description,$userId,$budgetId);
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
    public function DeleteBudget($budgetId)
    {
        $userId = $_SESSION['userId'];
        if (!$this->DoesBudgetExist($budgetId))
        		return status('NO_SUCH_BUDGET');
        { 
	    	if ($s = $this->mysqli->prepare("DELETE FROM Budzet where ID_Uzytkownika = ? AND ID_Budzetu = ?;")) {
	    		$s->bind_param('ii',$userId,$budgetId);
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
    	if ($this->GetProductCategoryByName($name))
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
     * @desc Pobiera listę kategorii przychodow
     * @param void
     * @return array
     * @example void
     * @logged true
     */
    public function GetIncomeCategories()
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_KatPrzychodu, nazwa FROM KategoriePrzychodow")) {
    		$s->execute();
    		$s->bind_result($ID_KatPrzychodu,$nazwa);
    		$arr = array();
    		while ( $s->fetch() ) {
    			$row = array('ID_KatPrzychodu' => $ID_KatPrzychodu,'nazwa' => $nazwa);
    			$arr[] = $row;
    		}
    		return array('count' =>  $s->num_rows,
    				'categories' => $arr);
    	}
    	else
    		return status('CANNOT_GET_INCOMES_CATEGORIES');
    }
    
    
    /**
     * @desc Dodaje kategorie do listy kategorii przychodow
     * @param string
     * @return array
     * @example pensja
     * @logged true
     */
    public function AddIncomeCategory($name)
    {
    	$userId = $_SESSION['userId'];
    	if ($this->GetIncomeCategoryByName($name))
    		return status('INCOME_CATEGORY_EXISTS');
    	else{
    		if ($s = $this->mysqli->prepare("INSERT INTO KategoriePrzychodow (nazwa) values (?);")) {
    			$s->bind_param('s',$name);
    			$s->execute();
    			$s->bind_result();
    			return status('INCOME_CATEGORY_ADDED');
    		}
    		else
    			return status('INCOME_CATEGORY_NOT_ADDED');
    	}
    }
    
    
    
    
    /**
     * @desc Dodaje produkt do listy produktow
     * @param int,string
     * @return array
     * @example 1, jablko
     * @logged true
     */
    public function AddProduct($prodact_cat, $name)
    {
    	$userId = $_SESSION['userId'];
    	if (!$this->DoesProductCategoryExist($prodact_cat))
    		return status('PRODUCT_CATEGORY_NOT_EXISTS');
    	
    	if ($this->GetProductByName($name))
    		return status('PRODUCT_EXISTS');
    	else{
    		if ($s = $this->mysqli->prepare("INSERT INTO Produkty (ID_KatProduktu, nazwa) values (?, ?);")) {
    			$s->bind_param('is',$prodact_cat,$name);
    			$s->execute();
    			$s->bind_result();
    			return status('PRODUCT_ADDED');
    		}
    		else
    			return status('PRODUCT_NOT_ADDED');
    	}
    }  
    
    
    /**
     * @desc Pobiera listę produktów
     * @param void
     * @return array
     * @example void
     * @logged true
     */
    public function GetProducts()
    {
    	if ($s = $this->mysqli->prepare("SELECT ID_Produktu, ID_KatProduktu, nazwa, data FROM Produkty")) {
    		$s->execute();
    		$s->bind_result($ID_Produktu,$ID_KatProduktu,$nazwa,$data);
    		$arr = array();
    		while ( $s->fetch() ) {
    			$row = array('ID_Produktu' => $ID_Produktu,'ID_KatProduktu' => $ID_KatProduktu,'nazwa' => $nazwa, 'data' => $data);
    			$arr[] = $row;
    		}
    		return array('count' =>  $s->num_rows,
    				'products' => $arr);
    	}
    	else
    		return status('CANNOT_GET_PRODUCT_CATEGORIES');
    }
    
    /**
     * @desc Pobiera listę wydatków ze wskazanego budzetu
     * @param int
     * @return array
     * @example 1
     * @logged true
     */
    public function GetExpenses($budgetId)
    {
    	$userId = $_SESSION['userId'];
    	if ($this->DoesBudgetExist($budgetId)){
	    	if ($s = $this->mysqli->prepare("SELECT W.ID_Wydatku,W.ID_Budzetu,W.ID_Produktu, P.nazwa, W.kwota 
	    			FROM Wydatki W join Produkty P on W.ID_Produktu = P.ID_Produktu where W.ID_Budzetu = ?")) {
	    		$s->bind_param('i', $budgetId);
	    		$s->execute();
	    		$s->bind_result($ID_Wydatku,$Id_Budzetu,$IP_Produktu, $nazwa, $kwota);
	    		$arr = array();
	            	while ( $s->fetch() ) {
	               		$row = array('ID_Wydatku' => $ID_Wydatku,'Id_Budzetu' => $Id_Budzetu,'IP_Produktu' => $IP_Produktu,'nazwa' => $nazwa,'kwota' => $kwota);
	                    $arr[] = $row;
	                }
	                return array('count' =>  $s->num_rows,
	                             'expenses' => $arr);
	    	}
	    	else
	    		return status('CANNOT_GET_EXPENSES');
    	}
    	else
    		return status('NO_SUCH_BUDGET');
    }
    
    
    /**
     * @desc Dodaje nowy wydatek
     * @param int, string, double, int
     * @return array
     * @example 3, jablko, 1.3, 1
     * @logged true
     */
    public function AddExpense($budgetId,$name,$cost,$purchaseId = -1)
    {
  		
    	if (empty($purchaseId))
    		$purchaseId = null;
    	
    	// Jezeli dany produkt nie istnieje to dodajemy go do listy z kategoria produktow 'inny'
    	if (!$this->GetProductByName($name))
 	  		$this->AddProduct(1, $name);
		
    	$productId = $this->GetProductByName($name);
        $userId = $_SESSION['userId'];       
        
        if (!$this->DoesBudgetExist($userId,$budgetId))
        	return status('NO_SUCH_BUDGET');
        else{
	    	if ($s = $this->mysqli->prepare("INSERT INTO Wydatki (ID_Budzetu,ID_Produktu,kwota,ID_Zakupu) values (?, ?, ?, ?);")) {
	    		$s->bind_param('iidi',$budgetId,$productId,$cost,$purchaseId);
	    		$s->execute();
	    		$s->bind_result();
	    		return status('EXPENSE_ADDED');
	    	}
	    	else
	    		return status('EXPENSE_NOT_ADDED');
	    }
    }
    
    ///TODO implementacja tej metody
    /**
     * @desc Edytuje wydatek
     * @param int, string, double, int
     * @return array
     * @example 3, jablko, 1.3, 1
     * @logged true
     */
    public function UpdateExpense($expenseId,$name,$cost,$purchaseId = -1)
    {
    /*
    	if (empty($purchaseId))
    		$purchaseId = null;
    	 
    	if (!$this->GetProductByName($name))
    		$this->AddProduct(1, $name);
    
    	$productId = $this->GetProductByName($name);
    	$userId = $_SESSION['userId'];
    
    	if (!$this->DoesBudgetExist($userId,$budgetId))
    		return status('NO_SUCH_BUDGET');
    	else{
    		if ($s = $this->mysqli->prepare("INSERT INTO Wydatki (ID_Budzetu,ID_Produktu,kwota) values (?, ?, ?);")) {
    			$s->bind_param('iid',$budgetId,$productId,$cost);
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
    
    
    
    //TODO usuwanie i edycja wydatkow z budzetu
    //TODO zrobic slownik wartosc z paragonu - id produktu na liscie
    //TODO zmiana danych uzytkownika
    //TODO dodawanie przychodow
    //TODO modyfikowanie i usuwanie przychodow
    
    //TODO raporty pokaz wydatki wg produktow - x dni, tydzien, x tygodni, miesiac, x miesiecy, rok, caly czas
    //TODO raporty pokaz przychodu wg produktow - x dni, tydzien, x tygodni, miesiac, x miesiecy, rok, caly czas
    //TODO dodawanie modyfikowanie i usuwanie planowanych wydatkow i przychodow - automatyczne powiadomienie
    
    //TODO dodawanie zakupow - nazwa i sklep oraz lista produktow dwie metody- dodaje zakupy, dodaj wydatki do zakupow
    
    //TODO dodawanie zleceń stałych 
    //TODO dodawnie powiadomień - przy dodaniu zlecenia stałego dodaj powiadomienie
    
    
}
?>