package saj.startup.pj.model.dao.entity;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class QuestionData {

	private int questionIdPk;
	
	private String category;
	
	private String question;
	
	private String strandegree;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
}
