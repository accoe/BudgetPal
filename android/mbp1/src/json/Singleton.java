package json;

public class Singleton {
    private static Singleton instance; 
    public WebService ws;
    public String customVar = "poprawna wartoœæ" ; 
     
    public static void initInstance(){ 
        //if(instance == null) { 
            instance = new Singleton();
        //}
    }
     
    public static Singleton getInstance(){ 
        return instance; 
    }
     
    public Singleton() { 
    	ws = new WebService();
    }
    
    public WebService getWs() {
    	return ws;
    }
}