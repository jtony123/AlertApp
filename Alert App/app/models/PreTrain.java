package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class PreTrain extends Model {

	@Required
	@ManyToOne
	public Player player;
	
	@Required
	public Date inputDate;
	
	public String comment;
	
	public boolean outOfRange;
	
	@Required
	public boolean isComplete;
	
	@OneToMany(mappedBy="preTrain", cascade=CascadeType.ALL)
	public List<Answer> answers;
	
	// i want to save the answer to each question with this pretrain as sets of pretrain-question-answer trio
	
	public PreTrain (Player player, Date preTrainDate, boolean isComplete){
		
		this.player = player;	
		this.inputDate = preTrainDate;
		this.isComplete = isComplete;
		answers= new ArrayList<Answer>();
		
	}
	
	public void addPlayerPreTrainComment(String comment) {
		this.comment = comment;
		this.save();
	}
	
	public void addPlayerPreTrainAnswer(Answer answer){
		answers.add(answer);
		
		if(answer.answerValue > answer.question.flagThreshold){
			this.outOfRange = true;
		} else {
			this.outOfRange = false;
		}
		
		this.save();
	}
	
	
}
