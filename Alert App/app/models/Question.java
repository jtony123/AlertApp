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
public class Question extends Model implements Comparable<Question>{
	
	@Required
	public String question;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	public List<Answer> answers;
	
	
	// decide how many answers will be offered for this question
	// with each subsequent answer given an integer value starting at 1 ascending
	// and decide at which point a flag should be raised
	public int flagThreshold;
	
	
	public Question (String question, int flagThreshold){
			
		this.question = question;
		this.answers = new ArrayList<Answer>();
		this.flagThreshold = flagThreshold;
		
	}
	
	public String toString(){
		return question;
	}
	
	public int compareTo(Question otherQuestion){
		return question.compareTo(otherQuestion.question);
	}
	
//	public static Question findOrCreateByName(String q){
//		Question question = Question.find("byQuestion", q).first();
//		if(question == null){
//			question = new Question(q, 0);
//		}
//		return question;
//	}
	
	public static List<Map> getCloud(){
		List<Map> result=Question.find("select new map(t.question as question, count(p.id) as pound) from Player p join p.questions as t group by t.question order by t.question").fetch();
		return result;
	
	}
	

}
