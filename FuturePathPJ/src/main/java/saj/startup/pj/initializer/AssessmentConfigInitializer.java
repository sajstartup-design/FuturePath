package saj.startup.pj.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import saj.startup.pj.model.dao.AssessmentConfigDao;
import saj.startup.pj.model.dao.entity.AssessmentConfigEntity;

@Component
@RequiredArgsConstructor
public class AssessmentConfigInitializer {

	@Autowired
    private AssessmentConfigDao assessmentConfigDao;

    @PostConstruct
    public void initAnalytics() { 

        long count = assessmentConfigDao.count();

        if (count == 0) {
            AssessmentConfigEntity config = new AssessmentConfigEntity();
            config.setTotalQuestion(10);
            config.setDefaultDegreeAvailability(true);
            config.setCustomDegreeAvailability(true);
            config.setDefaultStrandAvailability(true);
            config.setCustomStrandAvailability(true);

            assessmentConfigDao.save(config);
            System.out.println("✅ Assessment Configuration record initialized.");
        } else {
            System.out.println("ℹ️ Assessment Configuration record already exists — no action needed.");
        }
    }
}
