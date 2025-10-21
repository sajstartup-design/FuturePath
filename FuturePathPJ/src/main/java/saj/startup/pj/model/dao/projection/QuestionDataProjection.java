package saj.startup.pj.model.dao.projection;

import java.sql.Timestamp;

public interface QuestionDataProjection {
    int getQuestionIdPk();
    String getCategory();
    String getQuestion();
    String getStrandegree();
    Boolean getIsActive();
    Timestamp getCreatedAt();
}
