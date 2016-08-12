package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {
	
    @Before
    static void setConnectedClient() {
    	System.out.println("setConnected called");
        if(Security.isConnected()) {
            Client client = Client.find("byEmail", Security.connected()).first();
            renderArgs.put("client", client.fullname);
        }
    }

	
    public static void index() {
    	System.out.println("application.index method called");
    	List<Player> players = Player.find("order by playernumber desc").fetch(10);
        render(players);
    }
    
    public static void authenticate(){
    	System.out.println("application.authenticate method called");
    	//Client client = Client.find(query, params);
    	List<Player> players = Player.find("order by playernumber desc").fetch(10);
    	render("Application/index.html", players);
    }
    
    public static void showAll(){
    	System.out.println("showAll method called");
    	List<Player> players = Player.find("order by playernumber desc").fetch(10);
    	
    	render("Application/index.html", players);
    }
    
//    public static void userPhoto(long id) { 
//    	   final User user = User.findById(id); 
//    	   response.setContentTypeIfNotSet(user.photo.type());
//    	   java.io.InputStream binaryData = user.photo.get();
//    	   renderBinary(binaryData);
//    	} 
    
    
    public static void show(Long id) {
    	
    	System.out.println("show method with id="+id);
        Player player = Player.findById(id);
        if (player.playerPhoto.exists()){
        	response.setContentTypeIfNotSet(player.playerPhoto.type());
    		java.io.InputStream binaryData = player.playerPhoto.get();
    		renderBinary(binaryData);
        }
		render(player);
    }
    
    public static void addPlayer(Long id, Blob photo) {
    	Player player = Player.findById(id);
    	player.updatePhoto(photo);
    	System.out.println(player.playername + " found, photo id= ");
    	System.out.println(photo.get().toString());
    	
    	   index();
    	}
    
    
    

}