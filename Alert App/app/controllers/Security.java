package controllers;
 
import models.*;
 
public class Security extends Secure.Security {
	
	static boolean authenticate(String username, String password) {
		System.out.println("security route");
	    return Client.connect(username, password) != null;
	}
    
}
