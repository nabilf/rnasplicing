package it.uniroma3.rnakernels.regex;

import it.uniroma3.rnakernels.models.Index;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexManager {

	
	public static List<Index> applyRegex(String s, String regex){
		List<Index> indexes = new LinkedList<Index>(); 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		while(matcher.find()){
			Index i = new Index((matcher.end()-1 ),(matcher.start()+1)); 
			indexes.add(i); 
		}		
		return indexes; 
	}
	
	
	public static String applyAndReplace(String regex, String seq, String replace, String startBracket, String endBracket){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(seq);
		while(matcher.find()){
			int offset = (matcher.end()-1 )-(matcher.start()+1); 
			seq = seq.replaceFirst(regex,startBracket+replace+offset+endBracket); 
		}
		return seq; 
	}
	
}
