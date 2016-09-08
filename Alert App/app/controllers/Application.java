package controllers;

import play.*;
import play.cache.Cache;
import play.data.Upload;
import play.db.jpa.Blob;
import play.mvc.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import org.apache.commons.io.IOUtils;

import models.*;
import utilities.CSVLoader;
import utilities.CSVOutput;

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
    

    
    public static void index(int playernumber, String category) {
    	//System.out.println("application.index method called - playernumber=" +playernumber);
    	//System.out.println("Category called = " + category);
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
    	System.out.println("file name of player = " + player.filename);
    	
    	
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
    	index(0, "All");
    }
    
    public static void readCSV(){
    
    	render();
    }
    
 public static void getCSV(int playerNumber){
	 Player player = Player.find("byPlayernumber", playerNumber).first();
	 renderBinary(Play.getFile("data/attachments/" +player.filename));
	 
	 //renderBinary(Play.getFile("data/attachments/GraphCSVFiles/player"+playerNumber+".csv"));
 }
    
 public static void saveCSV(int playernumber, Blob data){
	 
	 
	 
	  File file;
	  String filename = null;
	 // just for development
	 if(playernumber != 0) {
		Player player = Player.find("byPlayernumber", playernumber).first();
	   	file = data.getFile();
	   	player.file = data.getFile();
	   	player.filename = file.getName();
	   	player.save();
	   	filename = file.getName();
	   	System.out.println("name = " + filename);
	   	System.out.println("saved");
	 } else {
		file = data.getFile();
    	CSVLoader csvloader = new CSVLoader();
    	csvloader.loadCSVFile(file.getAbsolutePath());//.loadFile(file.getAbsolutePath());
    	CSVOutput output = new CSVOutput("data/attachments/GraphCSVFiles/" + file.getName());
    	output.writeOutFile(csvloader.getHeader(), csvloader.getDatapoints());
	 }
	   
	   	

    	
    	
//    	System.out.println("file exists? "+file.exists());
//    	cSVLoader.loadFile(filepath);
    	
	 
    	
    	
    	
    	index(4,"All");
    }
    

}