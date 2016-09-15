package models;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class QuestionCategory extends Model implements Comparable<QuestionCategory> {

	@Required
	public String name;

	public QuestionCategory(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public int compareTo(QuestionCategory otherQuestionCategory) {
		return name.compareTo(otherQuestionCategory.name);
	}

	public static QuestionCategory findOrCreateByName(String name) {
		QuestionCategory questionCategory = QuestionCategory.find("byName", name).first();
		if (questionCategory == null) {
			questionCategory = new QuestionCategory(name);
		}
		return questionCategory;
	}
	
    public static List<Map> getCloud() {
        List<Map> result = QuestionCategory.find(
            "select new map(t.name as questionCategory, count(p.id) as pound) from Question p join p.questioncategories as t group by t.name order by t.name"
        ).fetch();
        return result;
    }

}
