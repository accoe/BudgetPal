package json;

public class Budget {
	
	public int ID_Budzetu;
	public String nazwa;
	public String opis;

	public String toString(){
		return "["+ID_Budzetu+"] "+nazwa+"\t"+opis;
	}
}
