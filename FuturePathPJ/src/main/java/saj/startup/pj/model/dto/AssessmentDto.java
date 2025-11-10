package saj.startup.pj.model.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import saj.startup.pj.model.dao.entity.HistoryQuestionData;
import saj.startup.pj.model.dao.projection.AssessmentStatisticsData;
import saj.startup.pj.model.dao.projection.UniversityRecommendationData;
import saj.startup.pj.model.dao.projection.UserAssessmentStatisticsData;
import saj.startup.pj.model.object.RecommendationObj;

@Data
public class AssessmentDto {
	
	private int idPk;
	
	private String answeredJson;
	
	private String mode;
	
	private List<String> degrees;
	
	private List<String> strands;
	
	private HashMap<Integer, Integer> answered;
	
	private int totalCorrect;
	
	private int totalIncorrect;
	
	private int totalQuestion;
	
	private double percentage;
	
	private String result;
	
	private int resultIdPk;
	
	private Map<String, RecommendationObj> recommendationMap;
	
	private List<RecommendationObj> top3Recommendations;
	
	// The top 3-letter combination, e.g. "E-R-C"
    private String combination;

    // Friendly, natural explanation of what that means (no RIASEC terms)
    private String message;

    // Example careers or fields related to their interest
    private String exampleFields;
    
    // 🔹 RIASEC category totals for form binding
    private int realistic;
    private int investigative;
    private int artistic;
    private int social;
    private int enterprising;
    private int conventional;
    
    private double realisticPercentage;
    private double investigativePercentage;
    private double artisticPercentage;
    private double socialPercentage;
    private double enterprisingPercentage;
    private double conventionalPercentage;
    
    private String realisticPercentageStr;
    private String investigativePercentageStr;
    private String artisticPercentageStr;
    private String socialPercentageStr;
    private String enterprisingPercentageStr;
    private String conventionalPercentageStr;

    private List<HistoryQuestionData> questions;
    private List<UniversityRecommendationData> universities;
    
    private AssessmentStatisticsData assessmentStatistics;
    private UserAssessmentStatisticsData userAssessmentStatistics;
    
    
    private List<Integer> riasecCodes;

}
