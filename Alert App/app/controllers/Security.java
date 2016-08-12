package controllers;
 
import models.*;
 
public class Security extends Secure.Security {
	
	static boolean authenticate(String username, String password) {
		System.out.println("security.authenticate called");
	    return Client.connect(username, password) != null;
	}
    
}
