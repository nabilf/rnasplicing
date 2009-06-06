package it.uniroma3.dia.tknn.test;

import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.model.Element;
import it.uniroma3.dia.tknn.setup.SysSetup;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class TreeTest {
	
	public static Logger log = Logging.getInstance(TreeTest.class); 
	
	public static void main(String[] args) {
		SysSetup.init();
		TreeTest treeTest = new TreeTest();
		String rna = "(((.))..((.)))...((.))(.)";
		Element root = treeTest.getRootNode(rna);
		
		System.out.println("root: "+root);
		
		String rna_left = treeTest.getRestLeft(rna,root);
		String rna_right = treeTest.getRestRight(rna,root);
		
		Element node_left = treeTest.getNode(rna_left);
		Element node_right = treeTest.getNode(rna_right);
		
		System.out.println("rna_left: "+rna_left+" child node: "+node_left);
		System.out.println("rna_right: "+rna_right+" child node: "+node_right);		
	}
	
	public String getRestLeft(String s, Element e)
	{
		return s.substring(0,e.getI()); 
	}
	
	public String getRestRight(String s, Element e)
	{
		return s.substring(e.getJ()+1,s.length()); 
	}
	
	public Element getRootNode(String s){
		List<Element> childs = this.getRootChildren(s); 
		Element root = new Element(); 
		try{
			if(childs.size()>1){
				for (int i = 1; i < childs.size(); i++) {
					Element previous = childs.get(i-1); 
					Element current = childs.get(i); 
					//Se gli indici non sono strettamente successivi ho trovato la root
					if(current.getI() != previous.getJ()+1){
						root.setI(previous.getJ()+1); 
						root.setJ(current.getI()-1); 
					}
				}		
			}else{
				log.debug("Only One Child"); 
				Element e = childs.get(0);
				if(s.indexOf('.')!=-1){
					log.debug("Root Element at position "+s.indexOf('.') ); 
					root.setI(s.indexOf('.')); 
					root.setJ(s.indexOf('.')); 
				}
				else if(e.getI()>0){
					log.debug("Root Element at Start"); 
					root.setI(0);
					root.setJ(e.getI()-1); 
				}else{
					log.debug("Root Element at End"); 
					root.setI(e.getJ()+1); 
					root.setJ(s.length()-1); 
				}
			//	throw new Exception("Only one child! "); 
			}
		}catch(Exception e ){
			log.error(e.getMessage()); 
		}
		return root; 
	}
	
	public List<Element> getRootChildren(String s){
		List<Element> childs = new ArrayList<Element>(); 
		
		int i=0;
		int firstIndex = s.indexOf('('); 
		//log.debug("FIRST INDEX of ( "+firstIndex); 
		int offSet = firstIndex; 
		
		while(i<s.length()){
			Element e = this.getRootChild(s.substring(firstIndex), (offSet));
			firstIndex = e.getJ()+1; 
			offSet = e.getJ()+1; 
			//log.debug("Child fist index "+firstIndex);
			//log.debug("offset"+offSet); 
			childs.add(e); 
			
			i = e.getJ()+1; 
		}
		log.debug("Number of Childs "+childs.size()); 
		
		return childs; 
	}
	
	private Element getRootChild(String s, int offset){
		//log.debug("Sequence length : "+s.length()); 
		log.debug("Subsequence "+s); 
		Element e = new Element(); 
		e.setI(offset+s.indexOf('(')); 
	
		log.debug("child first index "+e.getI()); 
		int open = 1; 
		int close = 0;
		int i=s.indexOf('(')+1; 
		
		while( i<s.length() && open != close ){
			if(s.charAt(i) == '('){
				open++;	
			}else if(s.charAt(i)==')'){
				close++; 
			}
			i++; 
		}
		e.setJ(offset+i-1); 	
		log.debug("child last index "+e.getJ());
		return e; 
	}
	
	private Element getNode(String s)
	{
		Element e = new Element();
		int index = 0;
		boolean run = true;
		
		index = s.indexOf('.',index+1);
		
		while (!checkIndex(s,index) && index>-1)
		{
			index = s.indexOf('.',index+1);
		}
		
		e.setI(index);
		
		run = index>-1;
		while (run)
		{
			if (s.charAt(index+1)=='.')
				index++;
			else run=false;
		}
		
		e.setJ(index);
		
		return e;
	}
	
	private boolean checkIndex(String s, int index)
	{
		int openL = 0;
		int closeL = 0;
		int openR = 0;
		int closeR = 0;
		
		for (int i=0 ;i<s.length(); i++)
		{
			if (s.charAt(i)=='(' && i<index)
				openL++;
			else if (s.charAt(i)==')' && i<index)
				closeL++;
			else if (s.charAt(i)=='(' && i>index)
				openR++;
			else if (s.charAt(i)==')' && i>index)
				closeR++;
		}
		
		return (openL+closeL)-(openR+closeR)==0;
	}

}
