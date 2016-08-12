package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
    	System.out.println("index method called");
        render();
    }
    
    public static void authenticate(){
    	System.out.println("authenticate method called");
    	List<Player> players = Player.find("order by playernumber desc").fetch(10);
    	render("Application/dashboard.html", players);
    }
    
    public static void showAll(){
    	System.out.println("showAll method called");
    	List<Player> players = Player.find("order by playernumber desc").fetch(10);
    	for(Player p : players){
    		System.out.println(p.playername + " - id=" + p.playerPhoto.getUUID());
    		//response.setContentTypeIfNotSet(p.playerPhoto.type());
    		//java.io.InputStream binaryData = p.playerPhoto.get();
    	}
    	
    	render("Application/dashboard.html", players);
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