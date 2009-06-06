package it.uniroma3.dia.tknn.log;

import it.uniroma3.dia.tknn.constant.Constant;
import it.uniroma3.dia.tknn.model.PerformanceMeasure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ContingencyMatrixLogger {
	private static Logger log = Logger.getLogger(ContingencyMatrixLogger.class); 
	private static FileOutputStream fos ;
	
	public static void openFile(){
		
		File fileToSave = new File(Constant.CM_PATH);
		try {
			fos = new FileOutputStream(fileToSave);
			
		} catch (FileNotFoundException e) {
			log.error ("Unable to create file ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String getHeader(){
		String header ="\\documentclass[12pt]{amsart} \n" +
				"\\usepackage{geometry} \n" +
				"\\geometry{a4paper} \n" +
				"\\begin{document}\n"+
				"\\centering \n"; 
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
