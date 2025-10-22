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


}
