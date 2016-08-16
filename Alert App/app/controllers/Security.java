package controllers;
 
import models.*;
 
public class Security extends Secure.Security {
	
	static boolean authenticate(String username, String password) {
		System.out.println("security.authenticate called");
	    return Client.connect(username, password) != null;
	}
	
	
	static boolean check(String profile) {
		
		System.out.println("security.check called");
	    if("admin".equals(profile)) {
	        return Client.find("byEmail", connected()).<Client>first().isAdmin;
	    }
	    return false;
	}
    
}
