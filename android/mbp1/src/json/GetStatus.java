package json;

import com.google.gson.Gson;

public class GetStatus {
	
	private String status;
	private String msg;
	private boolean error;
	private boolean ok;
	private boolean info;

	
	public boolean isSet(){
		return this.error || this.ok || this.info;
	}
	
	public boolean isError(){
		return this.error;
	}
	
	public boolean isOk(){
		return this.ok;
	}
	
	public boolean isInfo(){
		return this.info;
	}
	
	public String toString(){
		return "["+status+"] "+msg;
	}
	
	public GetStatus(String json){	
		this.error = false;
		this.ok = false;
		this.info = false;
		try {
			Status status = new Gson().fromJson(json, Status.class);
			if (status.error != null){
				this.error = true;	
				this.status = "error";
				this.msg = status.error;
			}
			
			if (status.ok != null){
				this.ok = true;	
				this.status = "ok";
				this.msg = status.ok;
			}
			
			if (status.info != null){
				this.info = true;	
				this.status = "info";
				this.msg = status.info;
			}
		}
		catch (Exception e){
			// Nic nie rob - bo spodziewamy sie wyjatku za kazdym razem, gdy nie bedzie statusu tylko obiekt, liczba itp.
		}
	}
}
