package saj.startup.pj.initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import saj.startup.pj.model.dao.AnalyticsDao;
import saj.startup.pj.model.dao.entity.AnalyticsEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalyticsInitializer {

	@Autowired
    private AnalyticsDao analyticsDao;

    @PostConstruct
    public void initAnalytics() { 
        // Check if analytics record exists
        long count = analyticsDao.count();

        if (count == 0) {
            AnalyticsEntity analytics = new AnalyticsEntity();
            analytics.setTotalDegreeAssessments(0);
            analytics.setTotalStrandAssessments(0);
            analytics.setTotalPassedDegreeAssessments(0);
            analytics.setTotalFailedDegreeAssessments(0);
            analytics.setTotalPassedStrandAssessments(0);
            analytics.setTotalFailedStrandAssessments(0);

            analyticsDao.save(analytics);
            System.out.println("✅ Analytics record initialized.");
        } else {
            System.out.println("ℹ️ Analytics record already exists — no action needed.");
        }
    }
}
