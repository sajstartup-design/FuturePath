package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class HistoryQuestionData {
	
	private String question;
	
	private String correctAnswer;
	
	private String userAnswer;
	
	private String code;
	
	private String name;
	
	private Boolean isCorrect;
}
