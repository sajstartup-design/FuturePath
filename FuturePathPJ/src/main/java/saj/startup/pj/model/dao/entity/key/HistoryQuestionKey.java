package saj.startup.pj.model.dao.entity.key;

import java.io.Serializable;

import lombok.Data;

@Data
public class HistoryQuestionKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userIdPk;
    private int questionIdPk;
    private int answerIdPk;
}
