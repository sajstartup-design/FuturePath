package saj.startup.pj.model.dao.entity;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class QuestionData {
	
	public QuestionData(int questionIdPk, String category, String question, String strandegree, Boolean isActive, Timestamp createdAt) {
        this.questionIdPk = questionIdPk;
        this.category = category;
        this.question = question;
        this.strandegree = strandegree;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

	private int questionIdPk;
	
	private String category;
	
	private String question;
	
	private String strandegree;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
	
	private List<AnswerData> answers = null;
}
