package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class StrandegreeQuestionData {

	private String category;
	
	private String code;
	
	private String name;
	
	private int totalQuestions;
}
