package controllers;

import play.*;
import play.cache.Cache;
import play.db.jpa.Blob;
import play.mvc.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import models.*;
import filehandling.CSVLoader;

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
    	//List<Player> players = Player.find("byCoach", connectedClient).fetch();
    	List<Player> players = Player.findClientsPlayersCategorisedWith(connectedClient, "All");
    	
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
    	System.out.println(gpsdataDeadline);
    	
    	String active = "dashboard";
        render(active, categories, players, preTraindeadline, postTraindeadline, gpsdataDeadline);
    }
    
    public static void myindex(int playernumber, String category) {
    	System.out.println("application.myindex method called - playernumber=" +playernumber);
    	System.out.println("Category called = " + category);
    	//List<Player> players = Player.find("byCoach", connectedClient).fetch();
    	List<Player> players;
    	if(category==null){
    		players = Player.findClientsPlayersCategorisedWith(connectedClient, "All");
    		category = "All";
    	} else {
    		players = Player.findClientsPlayersCategorisedWith(connectedClient, category);
    	}
    	
    	Player player;
    	if (playernumber == 0) {
    		player = players.get(0);
    	} else {
    		player = Player.find("byPlayernumber", playernumber).first();
    	}

    	int playerIndex = players.indexOf(player);
    	System.out.println("index of player = " + playerIndex);
    	
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
    	
    	String active = "new_dashboard";
        render(active, categories, category, player, playerIndex, players, preTraindeadline, postTraindeadline, gpsdataDeadline);
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
    
//    public static void addPlayer(Long id, Blob photo) {
//    	Player player = Player.findById(id);
//    	player.updatePhoto(photo);
//    	System.out.println(player.playername + " found, photo id= ");
//    	System.out.println(photo.get().toString());
//    	
//    	   index();
//    	}
    
    public static void preQuestionForm(){
    	render();
    }
    
    public static void savePreTrain(int playernumber, String[] anAnswer){
    	
    	Player player = Player.find("byPlayernumber", playernumber).first();
    	
    	if(anAnswer != null){
    		PreTrain preTrain = new PreTrain(player, new Date(), true);
            for(String ans : anAnswer) {
                Answer answerGiven = Answer.findById(Long.parseLong(ans));
                preTrain.addPlayerPreTrainAnswer(answerGiven);
            }
    	} else {
    		System.out.println("no buttons selected");
    	}
    	index();
    }
    
    public static void readCSV(){
    
    	render();
    }
    
 public static void getCSV(int playerNumber){
	 System.out.println(playerNumber);
	 //renderBinary(Play.getFile("public/javascripts/"+filename+".csv"));
	 
	 renderBinary(Play.getFile("data/GraphCSVFiles/player"+playerNumber+".csv"));
 }
    
 public static void saveCSV(String filepath){
    	System.out.println("filepath found = "+filepath);
    	//	/Users/anthonyjackson/Desktop/AJ Licence.jpg
    	CSVLoader cSVLoader = new CSVLoader();
    	File file = new File(filepath);
    	System.out.println("file exists? "+file.exists());
    	cSVLoader.loadFile(filepath);
    	index();
    }
    

}