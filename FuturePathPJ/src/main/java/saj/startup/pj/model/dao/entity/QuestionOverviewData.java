package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class QuestionOverviewData {

	private int totalQuestions;
	private int totalDegreeQuestions;
	private int totalStrandQuestions;
	private int totalActiveQuestions;
	private int totalInactiveQuestions;
}
