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
	
	@Required
	public String answer;
	
	public Answer (Question question, String answer){
		
		this.question = question;	
		this.answer = answer;
		
	}
}
