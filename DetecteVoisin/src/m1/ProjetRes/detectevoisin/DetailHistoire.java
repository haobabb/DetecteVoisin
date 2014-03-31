package m1.ProjetRes.detectevoisin;

import java.util.Date;



public class DetailHistoire {
     Date date= new Date();
     String name;
     String mac;
     
     
     public DetailHistoire(Date date,String name, String mac){
    	 this.date=date;
    	 this.name=name;
    	 this.mac=mac;
    	 
     }


	public String getName() {
		
		return this.name;
	}
	
	public String getMac(){
		return this.mac;
	}
	public String getDate(){
		return (this.date).toString();
	}
	public String toString(){
		return this.name+this.mac+this.getDate();
	}
}
