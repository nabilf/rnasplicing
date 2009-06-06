package it.uniroma3.rnakernels.constant;

public interface Constant {

	/*Setup Properties*/
	public final static String LOG4J_INIT = "config/log4j.xml";
	public final static String PROPERTIES_PATH = "config/sys.properties"; 
	
	/*ELEMENT COMPARATOR*/
	public final static int COMPARE_I = 1; 
	public final static int COMPARE_J = 2; 
	public final static int COMPARE_LEVEL = 3;
	
	/*RECORD COMPARATOR*/
	public final static int COMPARE_K = 0; 
	public final static int COMPARE_CLASS = 1; 
	
	/*BASE PAIR PROBABILITY*/
	public static final String AU_PROB = "AU_PROB"; 
	public static final String GU_PROB = "GU_PROB";
	public static final String GC_PROB = "GC_PROB";
	public static final String GT_PROB = "GT_PROB";
}
