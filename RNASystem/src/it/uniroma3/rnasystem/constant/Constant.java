package it.uniroma3.rnasystem.constant;

public interface Constant {

	/*Setup Properties*/
	public final static String LOG4J_INIT = "config/log4j.xml";
	public final static String PROPERTIES_PATH = "config/sys.properties"; 
	
	public final static String FOLDING_CACHE ="FOLDING_CACHE";
	public final static String BUILDER_CACHE ="BUILDER_CACHE";
	public final static String CLASSIFIER_CACHE ="CLASSIFIER_CACHE";
	
	public final static String UCIDATASET_NAME ="UCIDATASET_NAME"; 
	public final static String FINAL_DATASET_NAME ="FINAL_DATASET_NAME"; 
	
	public final static String DATASET_PATH = "DATASET_PATH"; 
	public final static String FOLDING_CACHE_PATH ="FOLDING_CACHE_PATH";
	public final static String BUILDER_CACHE_PATH ="BUILDER_CACHE_PATH";
	
	public static final String TRAININGSET_FACTOR = "TRAINING_SET_FACTOR"; 

	public final static String FOLDING_ALGORITHM = "FOLDING_ALGORITHM"; 
	public final static String BUILDER_ALGORITHM = "BUILDER_ALGORITHM"; 
	public final static String CLASSIFIER_ALGORITHM ="CLASSIFIER_ALGORITHM";
	public static final String PAUM_TAUP = "PAUM_TAUP"; 
	public static final String PAUM_TAUN = "PAUM_TAUN"; 
	public static final String PAUM_ETA = "PAUM_ETA"; 
	public static final String PAUM_TOTALLOOP = "PAUM_TOTALLOOP";
	public static final int COMPARE_PREDICTED_CLASS = 0;
	public static final int COMPARE_REAL_CLASS = 1;
	
	public static final int SEQUENCE_COMPARE_K = 0;
	public static final int SEQUENCE_COMPARE_CLASS = 1; 
}

