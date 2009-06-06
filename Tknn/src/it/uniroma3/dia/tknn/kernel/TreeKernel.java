package it.uniroma3.dia.tknn.kernel;

import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.model.Edge;
import it.uniroma3.dia.tknn.model.Node;
import it.uniroma3.dia.tknn.algorithm.Algorithm;
import it.uniroma3.dia.tknn.algorithm.LevenshteinDistance;
import it.uniroma3.dia.tknn.exception.KernelException;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class TreeKernel implements IKernel{
	public static Logger log = Logging.getInstance(TreeKernel.class);
	
	private List<Node> aTree;
	private List<Node> bTree;
	
	
	private double k; 
	private double c; 
	
	public TreeKernel(List<Node> aTree, List<Node> bTree){
		super();
		this.aTree = aTree; 
		this.bTree = bTree; 
		this.c = 0.0; 
		this.k = 0.0; 

	}
	/**
	 * Algoritmo TREE KERNEL 
	 * TREE-KERNEL
	 * c = valore normalizzato di levenstein
	 * 
	 * Se le produzioni n1 e n2 sono differenti
			k(n1,n2) = 0 //distance
	 * Se le produzioni di n1 e n2 sono le stesse e n1 e n2 sono pre-terminali
			k(n1,n2) = c //distance
	 * Se le produzioni di n1 e n2 sono le stesse e n1 e n2 non sono preterminali
			k(n1,n2) = c * (1+k(prod(n1),prod(n2)))
	 */
	public void execute() throws KernelException{
		this.verifyTree(); 
		this.getTreeDistance(); 
		log.debug("TREE DISTANCE : "+this.k); 

	}

	private void getTreeDistance(){
		
		//Comparazione dei nodi Root
		this.c = this.compareNode(this.aTree.get(0),this.bTree.get(0));
		
		for (int i =1; i<this.aTree.size(); i++) {
				Node aNode = this.aTree.get(i); 
				Node bNode = this.bTree.get(i); 
				double currentComparison = this.compareNode(aNode, bNode); 
				this.k = this.c * (1+ currentComparison);
				this.c = currentComparison; 
		}
		
	}
	private void verifyTree(){
		int aSize = this.aTree.size(); 
		int bSize = this.bTree.size(); 
		
		if(aSize>bSize){
			List<Node> appTree = new LinkedList<Node>(); 
			appTree = this.aTree; 
			this.aTree = this.bTree; 
			this.bTree = appTree; 
		}
	}
//	private double getTreeDistance(Node aRoot, Node bRoot){
//		 
//		if(aRoot != null){
//			//Se il nodo non è una foglia, ha una lista di archi associata
//			if(aRoot.getChilds() != null && aRoot.getChilds().size() != 0){
//				this.c += this.compareNode(aRoot, bRoot); 
//				log.debug("Compared Node "+this.c); 
//				// Per ogni arco, prendo il nodo 
//				for(Edge aEdge : aRoot.getChilds()){
//						
//					for(Edge bEdge : bRoot.getChilds()){
//					
//						//Comparazione dei nodi in cui mi trovo
//						this.c += this.compareNode(aEdge.getDestination(), bEdge.getDestination()); 
//						
//						//Ricorsione per la comparazione sui nodi figli
//						this.k = this.c * (1 +  this.getTreeDistance(aEdge.getDestination(), bEdge.getDestination())); 
//					}
//					log.debug("Value of K "+this.k); 
//				}
//			}else{
//				log.debug("A Tree has only a one Node (Root)"); 
//				this.c += compareNode(aRoot, bRoot); 
//				log.debug("Compare root "+this.c);
//				this.k += this.c; 
//			}
//			
//		}else{
//			log.debug("A Root == null");
//			this.k += 0; 
//		}
//		return this.k; 
//		
//	}
	private double compareNode(Node a, Node b){
		
		double distance = 0; 
		LevenshteinDistance ld = new LevenshteinDistance(a.getBases(),b.getBases()); 
		ld.execute(); 
		distance = ld.getResult(); 	
		return distance; 
	}


	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}	
	
	
	
}
