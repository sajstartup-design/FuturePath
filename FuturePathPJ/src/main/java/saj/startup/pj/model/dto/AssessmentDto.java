package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class AssessmentDto {
	
	private String mode;
	
	private List<String> degrees;
	
	private List<String> strands;
}
