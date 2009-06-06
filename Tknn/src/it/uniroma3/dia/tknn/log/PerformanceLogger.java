package it.uniroma3.dia.tknn.log;

import it.uniroma3.dia.tknn.constant.Constant;
import it.uniroma3.dia.tknn.model.PerformanceMeasure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class PerformanceLogger {
	private static Logger log = Logger.getLogger(PerformanceLogger.class); 
	private static FileOutputStream fos ;
	
	public static void openFile(){
		
		File fileToSave = new File(Constant.PERFORMANCE_PATH);
		try {
			fos = new FileOutputStream(fileToSave);
			fos.write(getHeader().getBytes());
		} catch (FileNotFoundException e) {
			log.error ("Unable to create file ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeReport(Map<Integer, List<PerformanceMeasure>> map){
		Set<Integer> keySet = map.keySet(); 
		Iterator<Integer> it = keySet.iterator(); 
		List<Integer> list = new LinkedList<Integer>(); 
		while(it.hasNext()){
			int k = it.next(); 
			list.add(k); 

		}
		Collections.sort(list); 
		log.debug("List table size"+list.size()); 
		for (Integer integer : list) {
			writePerformanceLog(map.get(integer), integer); 
		}
	}
	public static void writePerformanceLog(List<PerformanceMeasure> list, int k){
		if(fos == null){
			openFile(); 
		}
		try {
			//fos.write(getBlockHeader(k).getBytes()); 
			fos.write(getTableHeader("ccccccc").getBytes()); 
			fos.write(getTableHeadLine().getBytes());
			for (PerformanceMeasure performanceMeasure : list) {
 
				fos.write(writeLine(performanceMeasure).getBytes()); 
	
			}
			fos.write(getTableFooter(""+k).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void writeMeasureReport(Map<Integer, List<PerformanceMeasure>> performanceMap){
		NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(2);
		
		Set<Integer> keySet = performanceMap.keySet(); 
		Iterator<Integer> it = keySet.iterator(); 
		try {
			fos.write(getReportHeader().getBytes());
			fos.write(getTableHeader("ccc").getBytes()); 
			fos.write(getReportTableHeadLine().getBytes());
				String headLine = ""; 
			while(it.hasNext()){
				int k = it.next(); 
				headLine = ""+k+"& & \\\\ \n" +"\\hline \n";
				fos.write(headLine.getBytes()); 
				String line  = ""; 
				List<PerformanceMeasure> pm = performanceMap.get(k); 
				for (PerformanceMeasure performanceMeasure : pm) {
					 line ="&"+ performanceMeasure.getRNAClassEnum().toString()+" &"+f.format(performanceMeasure.getF1Measure())+"\\\\ \n"; 
					 fos.write(line.getBytes()); 
				}
				
				
			}
			fos.write(getTableFooter("").getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static String getBlockHeader(int k){
		return "Misure di performance per k = "+k+" \\\\ \n"; 
	}
	public static String getReportHeader(){
		return "Tabella riassuntiva delle F1-Measure  \\ \n"; 
	}
	private static String getHeader(){
		String header ="\\documentclass[12pt]{amsart} \n" +
				"\\usepackage{geometry} \n" +
				"\\geometry{a4paper} \n" +
				"\\begin{document}\n"+
				"\\centering \n" +
				"Matrice di performance \\\\ \n" +
				"Classificatore KNN \\\\ " +
				"tauP : 0,1 \\\\ \n" +
				"tauN : 1,0 \\\\ \n"+
"tauN : 1,0 \\\\ \neta : 1\\\\ \n normalizzata \\\\ \n";

		return header; 
	}
	private static String getTableHeader(String pattern){
		String tableHeader = "\\begin{table}[h]\n" +
				"\\begin{tabular}{"+pattern+"}\n"; 
		return tableHeader; 
	}
	private static String getReportTableHeadLine(){
		String header = "\\hline \n" +
				"{\\bf{K}} & {\\bf{Class}}&{\\bf{F1-Measure}} \\\\ \n" +
				"\\hline \\hline \n"; 
		return header; 
	}
	private static String getTableHeadLine(){
		String header = "\\hline \n" +
				"& {\\bf{Accuracy}}  & {\\bf{Error Rate}} &{\\bf{ Precision}} & " +
				"{\\bf{Recall}} & {\\bf{F1-Measure}} &{\\bf{ BreakEven}}  " +
				"\\\\ \n" +
				"\\hline \\hline \n"; 
		return header; 
	}
	private static String writeLine(PerformanceMeasure pm){

		NumberFormat f = NumberFormat.getNumberInstance();
		f.setMaximumFractionDigits(2);
		
		String line = pm.getRNAClassEnum().toString()+"&" +f.format(pm.getAccuracy())+"&" +
				""+f.format(pm.getErrorRate())+"&"+f.format(pm.getPrecision())+"&"+f.format(pm.getRecall())+"&" +
						""+f.format(pm.getF1Measure())+"&"+f.format(pm.getBreakEven())+" \\\\ \n"; 
		return line; 
	}
	private static String getTableFooter(String className){
		String tableFooter = "\\end{tabular}\n" +
				"\\caption{Performance Measure class : "+className+"}\n" +
				"\\end{table}\n";
		return tableFooter; 
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
