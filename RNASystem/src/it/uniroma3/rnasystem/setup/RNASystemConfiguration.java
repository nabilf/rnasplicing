package it.uniroma3.rnasystem.setup;


public class RNASystemConfiguration {

	

	public static float trainingSetFactor =  0.70f; 
	
	public static String dataSetPath = "dataset/"; 
	public static String uciDataSetName = "uci.txt"; 
	public static String datasetName = "dataset.txt"; 
	public static String foldingDataset = "folding.txt"; 
	
	public static String cacheDatasetPath="dataset/cache/"; 
	public static String buildingDatasetPath="dataset/building/"; 
	
	public static final String performance_path = "performance/";
	public static final String performance_file="performance.tex"; 
	
	public static boolean useFoldingCache = true;
	public static String foldingAlgorithm = "Nussinov"; 
	public static String foldingClass = "it.uniroma3.rnafolding.algorithm.Nussinov"; 
	
	public static boolean useBuildingCache = true; 
	public static String buildingAlgorithm = "Graph"; 
	public static String BuildingClass = "it.uniroma3.rnakernels.builders.GraphBuilder"; 
	
	
	public static boolean useClassifierCache = false; 
	public static String classifierAlgorithm="Knn";
	public static String classifierClass="it.uniroma3.rnaclassifier.knn.classifier.KnnClassifier";
	
	
	public static String kernelClass = "it.uniroma3.rnakernels.kernel.SequenceKernel"; 
	
	//KNN Parameter
	public static final int K = 10;
	
	//PAUM Parameter
	
	public static final boolean NORMALIZE = false;

	public static final float ETA = 1;

	public static final float TAUN = 0.1f;

	public static final float TAUP = 1f;

	public static final int TOTALLOOPS = 2000;

	public static final String metricsAlgorithm = "SequenceKernel";

	
}
