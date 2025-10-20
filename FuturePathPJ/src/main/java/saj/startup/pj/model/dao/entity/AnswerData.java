package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class AnswerData {

	private int answerIdPk;

	private int questionIdPk;
	
	private String answer;
	
	private Boolean isCorrect;
}
