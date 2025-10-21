package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class AssessmentCheckerData {

	private int strandegreeIdPk;
	
	private String code;
	
	private String name;
	
	private int questionIdPk;
	
	private int answerIdPk;
	
	private Boolean isCorrect;
}
