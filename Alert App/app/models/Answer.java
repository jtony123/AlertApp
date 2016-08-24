package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Answer extends Model {

	@Required
	@ManyToOne
	public Question question;
	
	@ManyToOne
	public PreTrain preTrain;
	
	@Required
	public String answer;
	
	@Required
	public int answerValue;
	
	public boolean raiseFlag;
	
	public Answer (Question question, String answer, int answerValue){
		
		this.question = question;	
		this.answer = answer;
		this.answerValue=answerValue;
		
	}
}
