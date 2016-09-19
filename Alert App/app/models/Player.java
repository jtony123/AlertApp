package models;

import java.io.File;
import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class Player extends Model {

	@Required
	public String playername;
	
	@Required
	public Integer playernumber;
	public Date dateadded;
	
	public Blob playerPhoto;
	
	public File file;
	public String filename;
	
	@Required
	@ManyToOne
	public Client coach;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Category> categories;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Question> questions;
	
	@OneToMany(mappedBy="player", cascade=CascadeType.ALL)
	public List<GPSData> gpsdata;
	
	
	@OneToMany(mappedBy="player", cascade=CascadeType.ALL)
	public List<Questionnaire> questionnaire;
	
	
	public Player(String playerName, Integer playerNumber, Blob playerPhoto, Client coach){
		this.playername = playerName;
		this.playernumber = playerNumber;
		if(playerPhoto != null){
			this.playerPhoto=playerPhoto;
		}
		this.playerPhoto=playerPhoto;
		this.coach = coach;
		this.dateadded = new Date();
		
		this.gpsdata = new ArrayList<GPSData>();
		this.questionnaire = new ArrayList<Questionnaire>();
		this.categories = new TreeSet<Category>();
		// all players are automatically categorised in All
		this.categoriseItWith("All");
		
		this.questions = new TreeSet<Question>();
		
	}

	

	public Player addGPSData(Date inputDate) {
		
		GPSData newGPSData = new GPSData(this);
		newGPSData.inputDate=inputDate;
	    this.gpsdata.add(newGPSData);
	    this.save();
	    return this;
	}
	

	
	public Player addQuestionnaire(Date date, String answer) {
		
		Questionnaire q = new Questionnaire(this, date);
		this.questionnaire.add(q);
		this.save();
		return this;
	}
	
	
	public Player updatePhoto (Blob photo){
		
		this.playerPhoto=photo;
		this.save();
		return this;
	}
	
	public Player previous() {

		 Player player = Player.find("select distinct p from Player p where p.coach=?1 AND p.playernumber < ?2 AND p.categories.size > ?3 order by playernumber desc", this.coach, playernumber, 0).first();
		 if(player == null) {
			 player = Player.find("select distinct p from Player p where p.coach=?1 AND p.categories.size > ?2 order by playernumber desc", this.coach, 0).first();
		 }
		 return player;
	}
	 
	public Player next() {
		Player player = Player.find("select distinct p from Player p where p.coach=?1 AND p.playernumber > ?2 AND p.categories.size > ?3 order by playernumber asc", this.coach, playernumber, 0).first();
		if(player == null) {
			 player = Player.find("select distinct p from Player p where p.coach=?1 AND p.categories.size > ?2 order by playernumber asc", this.coach, 0).first();
		 }
		
	    return player;
	}
	
	
	/**
	 * Categorising a player
	 * @param name
	 * @return
	 */
	public Player categoriseItWith(String name){
		categories.add(Category.findOrCreateByName(name));
		return this;
	}
	
	/**
	 * Get players in a specific category
	 * @param name
	 * @return
	 */
	public static List<Player> findCategorisedWith(String category) {
		
		return Player.find("select distinct p from Player p join p.categories as t where t.name = ?", category).fetch();		
		
	}
	
	/**
	 * Get players in a specific category
	 * @param name
	 * @return
	 */
	public static List<Player> findClientsPlayersCategorisedWith(Client client, String category) {
		
		return Player.find("select distinct p from Player p join p.categories c where p.coach=?1 AND c.name=?2", client, category).fetch();		
		
	}
	
	public static List<QuestionCategory> findPlayerQuestionsCategories(int playernumber){
		
//		SELECT * FROM QuestionCategory
//		WHERE id IN
//		(SELECT questioncategories_id FROM Question_QuestionCategory qqc JOIN Question q ON qqc.Question_id = q.id
//		WHERE Question_id IN
//		(SELECT questions_id FROM Player_Question pq INNER JOIN Player p ON pq.Player_id = p.id where playernumber = '5'));
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM QuestionCategory ");
		sb.append("WHERE id IN ");
		sb.append("(SELECT questioncategories_id FROM Question_QuestionCategory qqc JOIN Question q ON qqc.Question_id = q.id ");
		sb.append("WHERE Question_id IN ");
		sb.append("(SELECT questions_id FROM Player_Question pq INNER JOIN Player p ON pq.Player_id = p.id where playernumber = '");
		sb.append(playernumber);
		sb.append("'));");
		
		String queryString = sb.toString();
		System.out.println(queryString);
		Query query = JPA.em().createNativeQuery(queryString, QuestionCategory.class);//.createNativeQuery(queryString);
		List<QuestionCategory> result = query.getResultList();
		return result;
	}
	
	public static List<Question> findPlayerQuestionsCategorisedWith(String questioncategory, int playernumber){

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM Question ");
		sb.append("WHERE id IN ");
		sb.append("(SELECT questions_id FROM Player_Question pq INNER JOIN Player p ON pq.Player_id = p.id where playernumber = '");
		sb.append(playernumber);
		sb.append("') ");
		sb.append("AND id IN ");
		sb.append("(SELECT Question_id FROM Question_QuestionCategory qqc INNER JOIN QuestionCategory qc ON qqc.questioncategories_id = qc.id where name = '");
		sb.append(questioncategory);
		sb.append("');");//   ');");
		String queryString = sb.toString();
		Query query = JPA.em().createNativeQuery(queryString, Question.class);//.createNativeQuery(queryString);
		List<Question> result = query.getResultList();
		return result;

	}

	
	/**
	 * Get players belonging to several categories
	 * @param name
	 * @return
	 */
	public static List<Player> findCategorisededWith(String... categories) {
	    return Player.find(
	            "select distinct p from Player p join p.categories as t where t.name in (:categories) group by p.id, p.playername, p.coach having count(t.id) = :size"
	    ).bind("categories", categories).bind("size", categories.length).fetch();
	}
	
	
	public String toString() {
	    return playername;
	}
	
	
}
