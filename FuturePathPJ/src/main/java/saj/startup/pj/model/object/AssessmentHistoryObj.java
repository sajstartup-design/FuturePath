package saj.startup.pj.model.object;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AssessmentHistoryObj {

	private int resultIdPk;
	
	private int totalCorrect;
	
	private int totalIncorrect;
	
	private int totalQuestions;
	
	private double score;
	
	private Timestamp dateTaken;
	
	
}
