package saj.startup.pj.model.object;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.entity.AnswerData;

@Data
public class QuestionObj {

	private int idPk;

	private String category;
	
	private String question;
	
	private String strandegree;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
	
	private List<AnswerData> answers;
}
