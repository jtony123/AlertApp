
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class PostTrain extends Model {

	@Required
	@ManyToOne
	public Player player;
	
	@Required
	public Date inputDate;
	
	@Required
	public String answer;
	
	@Required
	public boolean isComplete;
	
	
	
	public PostTrain (Player player, Date postTrainDate, String answer, boolean isComplete){
		
		this.player = player;	
		this.inputDate = postTrainDate;
		this.answer = answer;
		this.isComplete = isComplete;
		
	}
	
	
}
