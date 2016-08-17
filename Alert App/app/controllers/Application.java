package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {
	
	static Client connectedClient;
	
    @Before
    static void setConnectedClient() {

        if(Security.isConnected()) {
        	connectedClient = Client.find("byEmail", Security.connected()).first();
            renderArgs.put("client", connectedClient.fullname);
        }
    }
    

    public static void index() {
    	System.out.println("application.index method called");
    	List<Player> players = Player.find("byCoach", connectedClient).fetch();
    	List<Category> categories = Category.findAll();
    	
    	Date preTraindeadline = new Date();
    	preTraindeadline.setHours(6);
    	preTraindeadline.setMinutes(0);
    	preTraindeadline.setSeconds(0);
    	
    	Date postTraindeadline = new Date();
    	postTraindeadline.setHours(6);
    	postTraindeadline.setMinutes(0);
    	postTraindeadline.setSeconds(0);
    	
    	Date gpsdataDeadline = new Date();
    	gpsdataDeadline.setHours(6);
    	gpsdataDeadline.setMinutes(0);
    	gpsdataDeadline.setSeconds(0);
    	
    	String active = "dashboard";
        render(active, categories, players, preTraindeadline, postTraindeadline, gpsdataDeadline);
    }
    
    public static void listCategory(String category) {
    	
    	List<Category> categories = Category.findAll();
        List<Player> players = Player.findClientsPlayersCategorisedWith(connectedClient, category);
        
    	Date preTraindeadline = new Date();
    	preTraindeadline.setHours(6);
    	preTraindeadline.setMinutes(0);
    	preTraindeadline.setSeconds(0);
    	
    	Date postTraindeadline = new Date();
    	postTraindeadline.setHours(6);
    	postTraindeadline.setMinutes(0);
    	postTraindeadline.setSeconds(0);
    	
    	Date gpsdataDeadline = new Date();
    	gpsdataDeadline.setHours(6);
    	gpsdataDeadline.setMinutes(0);
    	gpsdataDeadline.setSeconds(0);
    	
    	String active = "categorised";
        render(active, category, categories, players, preTraindeadline, postTraindeadline, gpsdataDeadline);
    }
 
    
    public static void playerPhoto(long id) { 
    	   final Player player = Player.findById(id); 
    	   response.setContentTypeIfNotSet(player.playerPhoto.type());
    	   java.io.InputStream binaryData = player.playerPhoto.get();
    	   renderBinary(binaryData);
    	} 
    
    
    public static void show(int playernumber) {
    	
        	System.out.println("show method with id="+playernumber);
            Player player = Player.find("byPlayernumber", playernumber).first();
            //List<Player> players = Player.find("byCoach", connectedClient).fetch();
            
        	Date preTraindeadline = new Date();
        	preTraindeadline.setHours(6);
        	preTraindeadline.setMinutes(0);
        	preTraindeadline.setSeconds(0);
        	
        	Date postTraindeadline = new Date();
        	postTraindeadline.setHours(6);
        	postTraindeadline.setMinutes(0);
        	postTraindeadline.setSeconds(0);
        	
        	Date gpsdataDeadline = new Date();
        	gpsdataDeadline.setHours(6);
        	gpsdataDeadline.setMinutes(0);
        	gpsdataDeadline.setSeconds(0);
        	
        	String active = "show";
    		render(active, player, preTraindeadline, postTraindeadline, gpsdataDeadline);
 
    }
    
    public static void addPlayer(Long id, Blob photo) {
    	Player player = Player.findById(id);
    	player.updatePhoto(photo);
    	System.out.println(player.playername + " found, photo id= ");
    	System.out.println(photo.get().toString());
    	
    	   index();
    	}
    
    
    

}