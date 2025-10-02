package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDto {

	public String category;
	
	private int strandegreeIdPk;
	
	private String question;
	
	private List<String> answers;
}
