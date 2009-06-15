package it.uniroma3.rnasystem.logging;

import it.uniroma3.rnaclassifier.model.PerformanceMeasure;
import it.uniroma3.rnaclassifier.performance.contingency.ContingencyMatrix;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class PerformanceLogger {
	private static Logger log = Logger.getLogger(PerformanceLogger.class); 
	
	private static FileOutputStream fos ;
	
	public static void openFile(){
		
		File fileToSave = new File("performance/");
		log.debug(fileToSave.getAbsolutePath()); 
		try {
			if(!fileToSave.exists()) {
				fileToSave.mkdirs();
			}
			fos = new FileOutputStream(fileToSave+File.separator+RNASystemConfiguration.performance_file);
			
		} catch (FileNotFoundException e) {
			log.error ("Unable to create file ");
		} catch (IOException e) {
			log.error("Unable to write file "+fileToSave.getName()); 
		}
	}

	public static void writeReport(List<PerformanceMeasure> list, List<ContingencyMatrix> cmList){
		if(fos == null){
			openFile();
		}
		try {
			
			StringBuilder builder = new StringBuilder(); 
			builder.append(getReportHeader()); 
			builder.append(getDataTable()); 
			builder.append(getClassificationParameter()); 
			builder.append(getCMMatrixList(cmList)); 
			builder.append(getPerformanceTable(list)); 

			fos.write(builder.toString().getBytes()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public static String getReportHeader(){
		return "\\documentclass[12pt]{amsart} \n" +
		"\\usepackage{geometry} \n" +
		"\\geometry{a4paper} \n" +
		"\\begin{document}\n"+
		"\\centering \n" +
		"********** RNA - SYSTEM ********** \\\\"+
		" \n ";
	}
	
	private static String getDataTable(){
		return getStartSection("Dati")+getTableHeader("|c|c|c|c|")+"\\hline {\\bf{Folding}}  & {\\bf{Struttura}} &{\\bf{Classificazione}} & {\\bf{Metrica}}  \\\\ \n"+
" \\hline \\hline \n"+
		""+RNASystemConfiguration.foldingAlgorithm+"&"+RNASystemConfiguration.buildingAlgorithm+
		"&"+RNASystemConfiguration.classifierAlgorithm+"&"+RNASystemConfiguration.metricsAlgorithm+"\\\\ \n \\hline"+
		getTableFooter("")+getEndSection(); 
	}
	
	private static String getClassificationParameter() {
		String s =  getStartSection("Parametri Classificatore")+getTableHeader("|c||c|")
		+"\\hline {\\bf{Parametro}}  & {\\bf{Valore}} \\\\ \n"+
		" \\hline \\hline \n"; 
		if(RNASystemConfiguration.classifierAlgorithm.toUpperCase().equals("KNN".toUpperCase())){
			s += "K & "+RNASystemConfiguration.K+" \\\\ \n \\hline"; 
		}else if(RNASystemConfiguration.classifierAlgorithm.toUpperCase().equals("PAUM".toUpperCase())){
			s+= "ETA &"+RNASystemConfiguration.ETA+" \\\\ \n \\hline "; 
			s+= "TauP &"+RNASystemConfiguration.TAUP+" \\\\ \n \\hline "; 
			s+= "TauN &"+RNASystemConfiguration.TAUN+" \\\\ \n \\hline "; 
			s+= "TotalLoops &"+RNASystemConfiguration.TOTALLOOPS+" \\\\ \n \\hline "; 
		}
		s += getTableFooter("")+getEndSection(); 
		return s; 
	}
	private static String getCMMatrixList(List<ContingencyMatrix> cmList){
		String s = getStartSection("Matrici di Confusione"); 
		for (ContingencyMatrix cm : cmList) {
			s += getCMMatrix(cm); 
		}
		return s+getEndSection(); 
	}
	
	private static String getPerformanceTable(List<PerformanceMeasure> list){
		String s =  getStartSection("Misure di Performance")+getTableHeader("ccccccc")+
		getPerformanceTableHeadLine(); 
		for (PerformanceMeasure performanceMeasure : list) {	 
			s += writeLine(performanceMeasure); 
		}
		s += getTableFooter("")+getEndSection(); 
		return s; 
	}
	private static String getCMMatrix(ContingencyMatrix cm){
		return getTableHeader("|c||c|c|c|")+
		"\\hline & {\\bf{Label y = +1}}  & {\\bf{Label y = -1}} \\\\ \n"+
		" \\hline \\hline \n"+
		"{\\bf{Prediction h(x) = +1}} &"+cm.get(0,0)+"&"+cm.get(0,1)+"\\\\ \n \\hline \n"+
		"{\\bf{Prediction h(x) = -1}} &"+cm.get(1,0)+"&"+cm.get(1,1)+"\\\\ \n \\hline \n"+
		getTableFooter("Matrice classe "+cm.getClazz().toString()); 
	}
	
	private static String getStartSection(String section){
		return "\\section*{\\bf{"+section+"}} \n "; 
	}
	private static String getEndSection(){
		return "\n \\rule{1.12\\linewidth}{0.1mm} \n"; 
	}
	private static String getTableHeader(String pattern){
		String tableHeader = "\n \\begin{table}[h]\n" +
				"\\begin{tabular}{"+pattern+"}\n"; 
		return tableHeader; 
	}
	private static String getTableFooter(String caption){
		String tableFooter = "\\end{tabular}\n" +
				"\\caption{"+caption+"}\n" +
				"\\end{table}\n";
		return tableFooter; 
	}
	
	private static String getPerformanceTableHeadLine(){ 
		return "\\hline \n" +
		"& {\\bf{Accuracy}}  & {\\bf{Error Rate}} &{\\bf{ Precision}} & " +
		"{\\bf{Recall}} & {\\bf{F1-Measure}} &{\\bf{ BreakEven}}  " +
		"\\\\ \n" +
		"\\hline \\hline \n"; 
	}
	private static String writeLine(PerformanceMeasure pm){

		NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(2);
		
		String line = pm.getRNAClassEnum().toString()+"&" +f.format(pm.getAccuracy())+"&" +
				""+f.format(pm.getErrorRate())+"&"+f.format(pm.getPrecision())+"&"+f.format(pm.getRecall())+"&" +
						""+f.format(pm.getF1Measure())+"&"+f.format(pm.getBreakEven())+" \\\\ \n"; 
		return line; 
	}
	private static String getFooter(){
		String footer = "\\end{document}"; 
		return footer; 
	}
	public static void closeFile(){
		try {
			fos.write(getFooter().getBytes());
			fos.close();
		} catch (IOException e) {
			log.error("Unable to close file"); 
		} 
	}
	
}
