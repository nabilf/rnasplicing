package it.uniroma3.dia.tknn.constant;

public interface Constant {

	/*Setup Properties*/
	public final static String LOG4J_INIT = "config/log4j.xml";
	public final static String PROPERTIES_PATH = "config/sys.properties"; 
	
	/*RNA*/
	public final static String RNA_SEQUENCE1 = "RNA_SEQUENCE1"; 
	public final static String RNA_SEQUENCE2 = "RNA_SEQUENCE2"; 
	
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
	public static final String DATASET_PATH = "DATASET_PATH";
	public static final String TRAINING_SET_FACTOR = "TRAINING_SET_FACTOR";
	public static final String K = "K";
	public static final String PERFORMANCE_PATH = "Performance.tex";
	public static final String ARFF_PATH = "uci.arff";
	public static final String CM_PATH = "cm.tex";
}
