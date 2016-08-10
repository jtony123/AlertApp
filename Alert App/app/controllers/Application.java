package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	System.out.println("index method called");
        render();
    }
    
    public static void authenticate(){
    	System.out.println("authenticate method called");
    	Player frontPlayer = Player.find("order by dateadded desc").first();
    	List<Player> otherPlayers = Player.find("order by dateadded desc").from(1).fetch(10);
    	
    	
    	render("Application/dashboard.html", frontPlayer, otherPlayers);
    	//render("Application/show.html", player);
    }
    
    public static void show(Long id) {
        Player post = Player.findById(id);
        render(post);
    }
    

}