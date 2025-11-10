package saj.startup.pj.model.dao.projection;

import java.sql.Timestamp;

public interface AssessmentResultData {
	int getResultIdPk();
	String getFullName();
	int getTotalCorrect();
	int getTotalIncorrect();
	int getTotalQuestion();
	double getScore();
	Timestamp getDateTaken();
	String getCategory();
}
