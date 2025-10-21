package saj.startup.pj.model.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommendationObj {
    private String code;
    private String name;
    private int correctCount;
    private double percentage;
}