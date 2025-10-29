package saj.startup.pj.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import saj.startup.pj.model.dao.entity.key.HistoryQuestionKey;

@Data
@Entity
@Table(name = "history_question")
public class HistoryQuestionEntity {
	
	@Id
	private int resultIdPk;

	private int userIdPk;

	private int questionIdPk;

	private int answerIdPk;
	
	private Boolean isCorrect;
}
