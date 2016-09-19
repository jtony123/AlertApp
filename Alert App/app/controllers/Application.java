package controllers;

import play.*;
import play.cache.Cache;
import play.data.Upload;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
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
    	
    	System.out.println(playernumber + " - " + category);

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
    		Category cat = Category.find("byName", category).first();
    		if (!player.categories.contains(cat)){
    			List<Player> playersInThisCategory = Player.findClientsPlayersCategorisedWith(connectedClient, category);
    			if(playersInThisCategory.isEmpty()){
    				player = Player.find("byPlayername", "no players").first();
    			} else {
    				System.out.println("here");
    				player = playersInThisCategory.get(0);
    			}
    		}
    		//
    	}
    	System.out.println("no is "+player.playernumber);
    	
    	//if(player.categories.contains(o))

    	int playerIndex = players.indexOf(player);
    	
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
    
    public static void record(int playernumber, String category, String questioncategoryrequested) {
    	
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
    	
    	
    	
//    	QuestionCategory qc = QuestionCategory.find("byName", "pretrain").first();
//    	System.out.println("qc = "+qc.name);
    	

    	
    	
//    	System.out.println("qc = "+qc.questionCategory +" - " + qc.question.question);
//        
//    	System.out.println("getting pretrain questions");
//    	List<Question> pretrains = Question.findCategorisedWith("PreTrain");
//        for(Question q : pretrains){
//        	System.out.println(q.question);
//        	for(Answer a : q.answers){
//        		System.out.print(" - "+a.answer);
//        	}
//        	System.out.println();
//        	
//        }
//        
//        System.out.println();
//        System.out.println("getting players questions");
//    	Set<Question> playerquestions = player.questions;
//        for(Question q : playerquestions){
//        	System.out.println(q.question);
//        	for(Answer a : q.answers){
//        		System.out.print(" - "+a.answer);
//        	}
//        	System.out.println();
//        	
//        }
        
    	List<QuestionCategory> questioncategories = player.findPlayerQuestionsCategories(player.playernumber);
    	for(QuestionCategory qcat : questioncategories){
    		System.out.println("name = " +qcat.name+" - ");
    	}
    	
    	List<Question> categoryquestions = null;
    	if (questioncategoryrequested != null){
    		categoryquestions = player.findPlayerQuestionsCategorisedWith(questioncategoryrequested, player.playernumber);
    	} else {
    		if(questioncategories.size() > 0) {
    		categoryquestions = player.findPlayerQuestionsCategorisedWith(questioncategories.get(0).name, player.playernumber);
    		} 
    	}
        
    	int playerIndex = players.indexOf(player);
    	
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
    	
    	String active = "record";
		//render(active, player, preTraindeadline, postTraindeadline, gpsdataDeadline);
		render(active, categories, category, questioncategories, categoryquestions, player, playerIndex, players, preTraindeadline, postTraindeadline, gpsdataDeadline);

}

    
 
    
    public static void saveQuestionnaire(int playernumber, String[] anAnswer){
    	
    	Player player = Player.find("byPlayernumber", playernumber).first();
    	
    	if(anAnswer != null){
    		Questionnaire questionnaire = new Questionnaire(player, new Date());
            for(String ans : anAnswer) {
                Answer answerGiven = Answer.findById(Long.parseLong(ans));
                questionnaire.addPlayerQuestionnaireAnswer(answerGiven);
            }
    	} else {
    		System.out.println("no buttons selected");
    	}
    	
    	++playernumber;
    	record(playernumber, "All", null);
    }
    
    
    
    public static void readCSV(){
    
    	render();
    }
    
 public static void getCSV(int playerNumber){
	 Player player = Player.find("byPlayernumber", playerNumber).first();
	 renderBinary(Play.getFile("data/attachments/GraphCSVFiles/"+player.filename));
 }

 
 public static void saveCSV(Blob data){ 


	 File file = data.getFile();

	 CSVLoader csvloader = new CSVLoader();
	 csvloader.loadCSVFile(file.getAbsolutePath());

	 Map<Integer, ArrayList<String>> playerfiles = csvloader.getPlayerfiles();
	 Iterator it = playerfiles.entrySet().iterator();
	 while (it.hasNext()) {
		 Map.Entry pair = (Map.Entry)it.next();
		 CSVOutput output = new CSVOutput();

		 Player player = Player.find("byPlayernumber", pair.getKey()).first();
		 player.filename =  output.writeOutFile("data/attachments/GraphCSVFiles/", "player"+player.playernumber+"_1", csvloader.getHeader(), (List<String>) pair.getValue());

		 player.save();
	 }

	 index(4,"All");
 }
 
 public static void preQuestionForm(){
	 	render();
	 }
 
//	public static void savePreTrain(int playernumber, String[] anAnswer) {
//
//		Player player = Player.find("byPlayernumber", playernumber).first();
//
//		if (anAnswer != null) {
//			PreTrain preTrain = new PreTrain(player, new Date(), true);
//			for (String ans : anAnswer) {
//				Answer answerGiven = Answer.findById(Long.parseLong(ans));
//				preTrain.addPlayerPreTrainAnswer(answerGiven);
//			}
//		} else {
//			System.out.println("no buttons selected");
//		}
//		index(0, "All");
//	}
 

    

}