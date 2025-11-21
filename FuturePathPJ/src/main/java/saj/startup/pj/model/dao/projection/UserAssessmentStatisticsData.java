package saj.startup.pj.model.dao.projection;

import java.sql.Timestamp;

public interface UserAssessmentStatisticsData {

    int getTakenAssessments();
    double getAverageScores();
    double getHighestScores();
    double getLowestScores();
    Timestamp getLastTaken();
    int getLastResultIdPk();

}
