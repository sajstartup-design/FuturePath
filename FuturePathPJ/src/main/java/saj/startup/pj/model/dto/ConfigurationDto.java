package saj.startup.pj.model.dto;

import lombok.Data;

@Data
public class ConfigurationDto {

	private int totalQuestion;
	
	private boolean defaultDegreeAvailability;
	
	private boolean customDegreeAvailability;
	
	private boolean defaultStrandAvailability;
	
	private boolean customStrandAvailability;
}
