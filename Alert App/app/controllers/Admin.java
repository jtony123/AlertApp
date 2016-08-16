package controllers;

import models.Client;
import play.*;
import play.mvc.*;


public class Admin extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            Client client = Client.find("byEmail", Security.connected()).first();
            renderArgs.put("user", client.fullname);
        }
    }
 
    public static void index() {
        render();
    }
    
}
