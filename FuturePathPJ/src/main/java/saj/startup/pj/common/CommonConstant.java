package saj.startup.pj.common;

import java.util.Map;

public class CommonConstant {

	public static final int USER_MAX_DISPLAY = 10;
	public static final int STRANDEGREE_MAX_DISPLAY = 10;
	public static final int UNIVERSITY_MAX_DISPLAY = 10;
	public static final int QUESTION_MAX_DISPLAY = 10;
	
	public static final String PRIVATE = "PRIVATE";
	public static final String PUBLIC = "PUBLIC";
	
	public static final String STRAND = "STRAND";
	public static final String DEGREE = "DEGREE";
	
	public static final String ROLE_USER = "USER";
	public static final String ROLE_ADMIN = "ADMIN";
	
	public static final String DEGREE_DEFAULT_MODE = "DEGREE-DEFAULT-MODE";
	public static final String DEGREE_CUSTOM_MODE = "DEGREE-CUSTOM-MODE";
	public static final String STRAND_DEFAULT_MODE = "STRAND-DEFAULT-MODE";
	public static final String STRAND_CUSTOM_MODE = "STRAND-DEFAULT-MODE";

	public static final Map<Integer, String> RIASEC_CODE_MAP = Map.ofEntries(
	        Map.entry(1, "R-I-C"),
	        Map.entry(2, "R-I-C"),
	        Map.entry(3, "R-I-C"),
	        Map.entry(4, "R-A-I"),
	        Map.entry(5, "R-S-I"),
	        Map.entry(6, "R-S-I"),
	        Map.entry(7, "R-I-E"),
	        Map.entry(8, "I-R-C"),
	        Map.entry(9, "I-R-S"),
	        Map.entry(10, "I-R-C"),
	        Map.entry(11, "I-C-R"),
	        Map.entry(12, "I-R-C"),
	        Map.entry(13, "I-S-A"),
	        Map.entry(14, "I-C-E"),
	        Map.entry(15, "A-R-I"),
	        Map.entry(16, "A-S-I"),
	        Map.entry(17, "A-R-I"),
	        Map.entry(18, "A-S-E"),
	        Map.entry(19, "A-I-R"),
	        Map.entry(20, "A-E-S"),
	        Map.entry(21, "S-A-E"),
	        Map.entry(22, "S-E-A"),
	        Map.entry(23, "S-I-R"),
	        Map.entry(24, "S-E-A"),
	        Map.entry(25, "S-I-E"),
	        Map.entry(26, "S-I-A"),
	        Map.entry(27, "E-C-S"),
	        Map.entry(28, "E-A-S"),
	        Map.entry(29, "E-S-I"),
	        Map.entry(30, "E-C-I"),
	        Map.entry(31, "E-S-C"),
	        Map.entry(32, "E-A-I"),
	        Map.entry(33, "C-E-I"),
	        Map.entry(34, "C-I-E"),
	        Map.entry(35, "C-I-R"),
	        Map.entry(36, "C-E-I"),
	        Map.entry(37, "C-E-S")
	    );

	    public static final Map<Integer, String> RIASEC_DETAIL_MAP = Map.ofEntries(
	        Map.entry(1, "Civil Engineering"),
	        Map.entry(2, "Mechanical Engineering"),
	        Map.entry(3, "Aerospace Engineering"),
	        Map.entry(4, "Architecture (Design focus)"),
	        Map.entry(5, "Physical Therapy / Kinesiology"),
	        Map.entry(6, "Nursing (Practical focus)"),
	        Map.entry(7, "Agriculture / Crop Science"),
	        Map.entry(8, "Computer Science"),
	        Map.entry(9, "Biology / Pre-Med"),
	        Map.entry(10, "Chemistry / Biochemistry"),
	        Map.entry(11, "Mathematics / Statistics"),
	        Map.entry(12, "Physics"),
	        Map.entry(13, "Psychology (Research focus)"),
	        Map.entry(14, "Economics (Theoretical focus)"),
	        Map.entry(15, "Graphic Design / Digital Media"),
	        Map.entry(16, "English Literature"),
	        Map.entry(17, "Fine Arts / Studio Art"),
	        Map.entry(18, "Music / Theater Arts"),
	        Map.entry(19, "Architecture (Aesthetics focus)"),
	        Map.entry(20, "Journalism / Creative Writing"),
	        Map.entry(21, "Elementary / Secondary Education"),
	        Map.entry(22, "Social Work / Counseling"),
	        Map.entry(23, "Nursing (Caring / Interpersonal focus)"),
	        Map.entry(24, "Communication Studies"),
	        Map.entry(25, "Public Health"),
	        Map.entry(26, "History (Teaching / Human focus)"),
	        Map.entry(27, "Business Management"),
	        Map.entry(28, "Marketing / Advertising"),
	        Map.entry(29, "Pre-Law / Political Science"),
	        Map.entry(30, "Finance (Sales / Client focus)"),
	        Map.entry(31, "Hospitality / Tourism"),
	        Map.entry(32, "Entrepreneurship"),
	        Map.entry(33, "Accounting"),
	        Map.entry(34, "Information Systems (MIS)"),
	        Map.entry(35, "Data Analytics (Entry-level)"),
	        Map.entry(36, "Finance (Compliance / Recording focus)"),
	        Map.entry(37, "Paralegal Studies")
	    );
}
