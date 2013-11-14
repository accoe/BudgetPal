<?php

class mainClass
{

    var $mysqli;
    var $DBHOST;
    var $DBNAME;
    var $DBUSER;
    var $DBPASS;
    var $userId;
	
    public function __construct($user, $password, $host, $database) 
    {
        $this->DBUSER = $user;
        $this->DBPASS = $password;
        $this->DBHOST = $host;
        $this->DBNAME = $database;
        $this->InitializeSession();
        $this->userId = $_SESSION['userId'];
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
    		$login_string = $_SESSION['login_string'];
    		$username = $_SESSION['username'];
    		$user_browser = $_SERVER['HTTP_USER_AGENT'];
    		if ($s = $this->mysqli->prepare("SELECT haslo FROM Uzytkownicy WHERE ID_Uzytkownika = ? LIMIT 1")) {
    			$s->bind_param('i', $this->userId);
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
    	
    	if ($s = $this->mysqli->prepare("SELECT ID_Budzetu FROM Budzet where ID_Uzytkownika = ? AND nazwa= ?")) {
    		$s->bind_param('is',$this->userId,$name);
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
    	
    	if ($s = $this->mysqli->prepare("SELECT ID_Budzetu FROM Budzet where ID_Uzytkownika = ? AND ID_Budzetu = ?")) {
    		$s->bind_param('is',$this->userId,$budgetId);
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
    
    
    
    private function OrderASCBy($sql, $columns){
    	return $sql . " ORDER BY ".$columns." ASC";
    }
    
    private function OrderDESCBy($sql, $columns){
    	return $sql . " ORDER BY ".$columns." DESC";
    }
    
    private function Limit($sql, $limit){
    	return $sql . " LIMIT ".$limit;
    }
    
	private function OrderBy($sql, $columns, $mode)
	{
		if ($mode == 'ASC')
			return $this->OrderASCBy($sql, $columns);
		else if ($mode == 'DESC')
			return $this->OrderDESCBy($sql, $columns);
		else
			return status('NO_SUCH_ORDER');
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
                $s->bind_result($this->userId, $username, $password);
                $s->fetch();
                if ($s->num_rows == 1) {
                    // Poprawnie zalogowano
                    $user_browser = $_SERVER['HTTP_USER_AGENT'];
                    $_SESSION['userId'] = $this->userId;
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
                $s->bind_param('i', $this->userId);
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
        if ($this->GetBudgetByName($name))
        	return status('BUDGET_EXISTS');
        else{
	    	if ($s = $this->mysqli->prepare("INSERT INTO Budzet (ID_Uzytkownika,nazwa,opis) values (?, ?, ?);")) {
	    		$s->bind_param('iss',$this->userId,$name, $description);
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
    	if (!$this->DoesBudgetExist($budgetId))
    		return status('NO_SUCH_BUDGET');
    	{
    		// Pobierz stare wartości
    		if ($s = $this->mysqli->prepare("SELECT nazwa, opis FROM Budzet where where ID_Uzytkownika = ? ID_Budzetu = ?")) {
    			$s->bind_param('ii',$this->userId, $budgetId);
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
    			$s->bind_param('ssii',$name,$description,$this->userId,$budgetId);
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
        if (!$this->DoesBudgetExist($budgetId))
        		return status('NO_SUCH_BUDGET');
        { 
	    	if ($s = $this->mysqli->prepare("DELETE FROM Budzet where ID_Uzytkownika = ? AND ID_Budzetu = ?;")) {
	    		$s->bind_param('ii',$this->userId,$budgetId);
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
    	if ($this->DoesBudgetExist($budgetId)){
	    	if ($s = $this->mysqli->prepare("SELECT W.ID_Wydatku,W.ID_Produktu, P.nazwa, W.kwota, W.data 
	    			FROM Wydatki W join Produkty P on W.ID_Produktu = P.ID_Produktu where W.ID_Budzetu = ?")) {
	    		$s->bind_param('i', $budgetId);
	    		$s->execute();
	    		$s->bind_result($ID_Wydatku,$ID_Produktu, $nazwa, $kwota, $data);
	    		$arr = array();
	            	while ( $s->fetch() ) {
	               		$row = array('ID_Wydatku' => $ID_Wydatku,'ID_Produktu' => $ID_Produktu,'nazwa' => $nazwa,'kwota' => $kwota, 'data' => $data);
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
    public function AddExpense($budgetId,$name,$amount,$purchaseId = -1)
    {
    	if (empty($purchaseId))
    		$purchaseId = null;
    	
    	// Jezeli dany produkt nie istnieje to dodajemy go do listy z kategoria produktow 'inny'
    	if (!$this->GetProductByName($name))
 	  		$this->AddProduct(1, $name);
		
    	$productId = $this->GetProductByName($name);
               
        
        if (!$this->DoesBudgetExist($budgetId))
        	return status('NO_SUCH_BUDGET');
        else{
	    	if ($s = $this->mysqli->prepare("INSERT INTO Wydatki (ID_Budzetu,ID_Produktu,kwota,ID_Zakupu) values (?, ?, ?, ?);")) {
	    		$s->bind_param('iidi',$budgetId,$productId,$amount,$purchaseId);
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
    public function UpdateExpense($expenseId,$name,$amount,$purchaseId = -1)
    {
    /*
    	if (empty($purchaseId))
    		$purchaseId = null;
    	 
    	if (!$this->GetProductByName($name))
    		$this->AddProduct(1, $name);
    
    	$productId = $this->GetProductByName($name);
    	
    
    	if (!$this->DoesBudgetExist($this->userId,$budgetId))
    		return status('NO_SUCH_BUDGET');
    	else{
    		if ($s = $this->mysqli->prepare("INSERT INTO Wydatki (ID_Budzetu,ID_Produktu,kwota) values (?, ?, ?);")) {
    			$s->bind_param('iid',$budgetId,$productId,$amount);
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
    
    ///TODO implementacja tej metody
    /**
     * @desc Usuwa zdefiniowany wydatek
     * @param int
     * @return array
     * @example 12
     * @logged true
     */
    public function DeleteExpense($expenseId)
    {
    	return status('STUB_METHOD');
    }
    
    
    /**
     * @desc Pobiera listę dochodow ze wskazanego budzetu
     * @param int
     * @return array
     * @example 1
     * @logged true
     */
    public function GetIncomes($budgetId)
    {
    	if ($this->DoesBudgetExist($budgetId)){
    		if ($s = $this->mysqli->prepare("SELECT ID_Przychodu, nazwa, kwota, data FROM Przychody where ID_Budzetu = ?")) {
    	    			$s->bind_param('i', $budgetId);
    	    			$s->execute();
    	    			$s->bind_result($ID_Przychodu, $nazwa, $kwota, $data);
    	    			$arr = array();
    	    			while ( $s->fetch() ) {
    	    				$row = array('ID_Przychodu' => $ID_Przychodu,'nazwa' => $nazwa,'kwota' => $kwota, 'data'=> $data);
    	    				$arr[] = $row;
    	    			}
    	    			return array('count' =>  $s->num_rows,
    	    					'incomes' => $arr);
    		}
    		else
    			return status('CANNOT_GET_INCOMES');
    	}
    	else
    		return status('NO_SUCH_BUDGET');
    }
    
    
    /**
     * @desc Dodaje nowy przychod
     * @param int, string, double, int
     * @return array
     * @example 3, Wypłata listopad, 1600, 1
     * @logged true
     */
    public function AddIncome($budgetId,$name,$amount,$incomeCategory)
    {
    	if (!$this->DoesBudgetExist($budgetId))
    		return status('NO_SUCH_BUDGET');
    	else{
    		if ($s = $this->mysqli->prepare("INSERT INTO Przychody (ID_Budzetu,ID_KatPrzychodu,kwota,nazwa) values (?, ?, ?, ?);")) {
    			$s->bind_param('iids',$budgetId,$incomeCategory,$amount,$name);
    			$s->execute();
    			$s->bind_result();
    			return status('INCOME_ADDED');
    		}
    		else
    			return status('INCOME_NOT_ADDED');
    	}
    }
    
    /**
     * @desc Pobiera listę ostatnich operacji ze wskazanego budzetu
     * @param int, string, int
     * @return array
     * @example 1, DESC, 20
     * @logged true
     */
    public function GetRecentActivities($budgetId, $order = "DESC ", $limit = 20)
    {	
    	if (empty($order))
    		$order = "DESC";
    	if (empty($limit))
    		$limit = 20;    	 	
    	
   		$sql = $this->OrderBy('(SELECT  "przychod" AS  "rodzaj", nazwa, kwota, data FROM Przychody WHERE ID_Budzetu =1) UNION
    (SELECT  "wydatek" AS  "rodzaj", nazwa, kwota, W.data FROM Wydatki W JOIN Produkty P ON W.ID_Produktu = P.ID_Produktu WHERE ID_Budzetu =1)'
    	, "data", $order);   		
		$sql = $this->Limit($sql,$limit);
    	if ($this->DoesBudgetExist($budgetId)){
    		if ($s = $this->mysqli->prepare($sql)) {
    			$s->bind_param('ii', $budgetId,$budgetId);
    			$s->execute();
    			$s->bind_result($rodzaj, $nazwa, $kwota, $data);
    			$arr = array();
    			while ( $s->fetch() ) {
    				$row = array('rodzaj' => $rodzaj,'nazwa' => $nazwa,'kwota' => $kwota, 'data'=> $data);
    				$arr[] = $row;
    			}
    			return array('count' =>  $s->num_rows,
    					'activities' => $arr);
    		}
    		else
    			return status('CANNOT_GET_ACTIVITIES');
    	}
    	else
    		return status('NO_SUCH_BUDGET');
    }
   
    
    /**
     * @desc Pobiera sume przychodow
     * @param int
     * @return array
     * @example 1
     * @logged true
     */
    public function GetIncomesSum($budgetId)
    { 	
    	if ($this->DoesBudgetExist($budgetId)){
    		if ($s = $this->mysqli->prepare("SELECT sum(kwota) as suma FROM Przychody where ID_Budzetu = ?")) {
    		$s->bind_param('i', $budgetId);
    		$s->execute();
    		$s->bind_result($suma);
    		$s->store_result();
    		$s->fetch();
    		if ($s->num_rows > 0)
    			return round($suma,2);
    		else
    			return status('NO_INCOMES_ADDED');
    		}
    	}
    	return status('NO_SUCH_BUDGET');
    }
    
    /**
     * @desc Pobiera sume wydatkow
     * @param int
     * @return array
     * @example 1
     * @logged true
     */
    public function GetExpensesSum($budgetId)
    {    	
    	if ($this->DoesBudgetExist($budgetId)){
    		if ($s = $this->mysqli->prepare("SELECT sum(kwota) as suma FROM Wydatki where ID_Budzetu = ?")) {
    			$s->bind_param('i', $budgetId);
    			$s->execute();
    			$s->bind_result($suma);
    			$s->store_result();
    			$s->fetch();
    			if ($s->num_rows > 0)
    				return round($suma,2);
    			else
    				return status('NO_EXPENSES_ADDED');
    		}
    	}
    	else
    		return status('NO_SUCH_BUDGET');
    }
    
    
    
    /**
     * @desc Pobiera bilans danego budzetu
     * @param int
     * @return array
     * @example 1
     * @logged true
     */
    public function GetBudgetBilans($budgetId)
    {
    	if ($this->DoesBudgetExist($budgetId)){
			$bilans = 0;
        	$incomes = $this->GetIncomesSum($budgetId);
        	$expenses = $this->GetExpensesSum($budgetId);
			$bilans = $incomes - $expenses;
    		return array('bilans' =>  round($bilans,2));
    	}
    	else
    		return status('NO_SUCH_BUDGET');
    }
    
    
    
    ///TODO 2 usuwanie i edycja wydatkow z budzetu -2
    //TODO zrobic slownik wartosc z paragonu - id produktu na liscie
    //TODO zmiana danych uzytkownika
    //TODO modyfikowanie i usuwanie przychodow
    
    //TODO raporty pokaz wydatki wg produktow - x dni, tydzien, x tygodni, miesiac, x miesiecy, rok, caly czas
    //TODO raporty pokaz przychodu wg produktow - x dni, tydzien, x tygodni, miesiac, x miesiecy, rok, caly czas
    //TODO dodawanie modyfikowanie i usuwanie planowanych wydatkow i przychodow - automatyczne powiadomienie
    
    //TODO dodawanie zakupow - nazwa i sklep oraz lista produktow dwie metody- dodaje zakupy, dodaj wydatki do zakupow
    
    //TODO dodawanie zleceń stałych 
    //TODO dodawnie powiadomień - przy dodaniu zlecenia stałego dodaj powiadomienie
    
    
}
?>