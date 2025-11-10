package saj.startup.pj.model.dao.projection;

public interface AssessmentStatisticsData {

    int getTakenAssessments();
    double getAverageScores();
    int getDegreeAssessments();
    double getDegreeAverageScores();
    int getStrandAssessments();
    double getStrandAverageScores();

    // Score brackets
    int getScore0_25();
    int getScore26_50();
    int getScore51_75();
    int getScore76_100();
}
