package saj.startup.pj.model.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import saj.startup.pj.model.object.RecommendationObj;

@Data
public class AssessmentDto {
	
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
	
	Map<String, RecommendationObj> recommendationMap;
	
	public List<RecommendationObj> getTop3Recommendations() {
	    return recommendationMap.values().stream()
	        .filter(rec -> rec.getPercentage() > 0) // ✅ only include non-zero percentages
	        .sorted((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()))
	        .limit(3)
	        .collect(Collectors.toList());
	}
	
	// The top 3-letter combination, e.g. "E-R-C"
    private String combination;

    // Friendly, natural explanation of what that means (no RIASEC terms)
    private String message;

    // Section heading or display title (e.g. "Your Interest Summary")
    private String categoryName;

    // Example careers or fields related to their interest
    private String exampleFields;

    // Optional: could link to next page or assessment ID
    private String nextStepLink;

    // Optional: display label for the next step button
    private String nextStepLabel;


}
