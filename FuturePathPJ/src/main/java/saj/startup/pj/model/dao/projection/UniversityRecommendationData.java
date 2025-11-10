package saj.startup.pj.model.dao.projection;

public interface UniversityRecommendationData {
    Integer getUniversityIdPk();
    String getUniversityName();
    String[] getOfferedPrograms();
    String getCity();
    String getProvince();
    int getRanking();
}
