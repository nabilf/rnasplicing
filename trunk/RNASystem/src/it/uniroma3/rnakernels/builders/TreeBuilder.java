package it.uniroma3.rnakernels.builders;

import it.uniroma3.rnakernels.constant.Constant;
import it.uniroma3.rnakernels.exception.RNABuilderException;
import it.uniroma3.rnakernels.exception.TreeException;
import it.uniroma3.rnakernels.models.Edge;
import it.uniroma3.rnakernels.models.Element;
import it.uniroma3.rnakernels.models.ElementComparator;
import it.uniroma3.rnakernels.models.Node;
import it.uniroma3.rnakernels.setup.BasePairMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;


public class TreeBuilder extends Builder {
	public static Logger log = Logger.getLogger(TreeBuilder.class);
	
	private String sequence; 
	private String structure; 
	
	private List<Edge> edges; 
	private List<Node> nodes; 
	
	private List<Node> tree; 
	
	public TreeBuilder(){
		super(); 
	}
	public TreeBuilder(String sequence, String structure){
		super(); 
		super.sequence = sequence; 
		super.structure = structure; 
		this.edges = new LinkedList<Edge>(); 
		this.nodes = new LinkedList<Node>(); 
		this.tree = new LinkedList<Node>(); 
	}
	
	public List<Node> getTree() {
		return this.tree;
	}

	public void setTree(List<Node> tree) {
		this.tree = tree;
	}

	@Override
	public List<Node> execute() throws RNABuilderException{
		this.buildTree(); 
		printTree();
		return this.tree; 
		
	}	
	
	
	private void buildTree() throws TreeException {
		try{
			/*
			 * BUILDING TREE
			 */
			log.info("Building tree for sequence "+this.sequence);
			log.info("Building tree for structure "+this.structure);
			
			log.info("Getting Root Node..."); 
			Node root = this.getRootNode(); 
			
			/*2- Creo le liste di nodi e di archi relativi
			 * Creando una lista unica*/
			log.info("Creating edges and nodes list.."); 
			
			this.edges = getEdge(this.structure); 
			this.nodes = getNode(this.structure);
	
			log.info("Edges List Size "+this.edges.size()); 
			log.info("Nodes List size "+this.nodes.size()); 
			
			/*3- Ordinamento della lista di archi per l'indice I (parentesi aperta)*/
			Collections.sort(this.edges, new ElementComparator(Constant.COMPARE_I)); 
	
			//Impostazione Size Stem 
			this.setStemSize(); 
			
			log.info("Setting tree Level"); 
			/*4- Setting dei livelli di profonditˆ nell'albero ad archi e nodi*/
			this.setEdgeLevel(); 
	
			log.info("Building Tree Structure.."); 
			/*5- Ordinamenti delle liste in base al livello di profonditˆ ( al contrario) */
			Collections.sort(this.edges, new ElementComparator(Constant.COMPARE_LEVEL)); 
			Collections.sort(this.nodes, new ElementComparator(Constant.COMPARE_LEVEL)); 
			 
			/*6- Setting del livello dei nodi*/
			this.setNodeLevel(); 
	
			/*7- Merge dei nodi*/
			Collections.sort(this.nodes, new ElementComparator(Constant.COMPARE_LEVEL)); 
			this.mergeNode(); 
			
			/*5- Aggiunta della root alla lista di nodi */
			this.nodes.add(root); 
			
			
			/*7- Costruzione dell'albero*/
			this.setNodeEdge();		
			log.info("Setting Sequence.. "); 
			
			/*8- Setting delle basi nell'albero*/
			this.setSequence(); 
			
			Collections.sort(this.tree, new ElementComparator(Constant.COMPARE_LEVEL)); 
			 
			log.info("Tree Created"); 
		}catch(Throwable t){
			log.error("Unable to create Tree "+t.getMessage()); 
			t.printStackTrace(); 
			throw new TreeException("Unable to create Tree "+t.getMessage()); 
		}
		
	}
	private void mergeNode() {
		
		Map<Integer, LinkedList<Node>> levelNode = new HashMap<Integer, LinkedList<Node>>(); 
		
		for(Node n : this.nodes){
			LinkedList<Node> nodes = levelNode.get(n.getLevel()); 
			
			if(nodes == null){
				nodes = new LinkedList<Node>(); 
				levelNode.put(n.getLevel(), nodes); 
			}
			nodes.add(n); 
		}
		
		this.nodes = new LinkedList<Node>(); 
		Set<Integer> keySet = levelNode.keySet(); 
		Iterator<Integer> it = keySet.iterator(); 
		while(it.hasNext()){
			Node ln = new Node(); 
			ln.setLevel(it.next()); 
			List<Node> nodes = levelNode.get(ln.getLevel()); 
			for (Node node : nodes) {
				Element e = new Element(node.getI(), node.getJ());
				//ln.getEdges().addAll(node.getEdges()); 
				ln.getElement().add(e); 
			}
			this.nodes.add(ln); 
		}
	}
	private Node getRootNode(){

		Node root = findRoot();
		if(root.getI()!=0 && root.getJ()!= 0){
			root.setBases(this.sequence.substring(root.getI(),root.getJ()+1));
			log.info("Root Bases "+root.getBases()); 
			root.setLevel(1); 
			this.getSubSequence(root.getI(), root.getJ()); 
		}
		return root; 
	}
	private void getSubSequence(int i, int j) {
		
		if(i == 0){
			this.sequence = this.sequence.substring(j); 
			this.structure = this.structure.substring(j); 
		}else{
			this.sequence = this.sequence.substring(0, i+1); 
			this.structure = this.structure.substring(0, i+1); 
		}
		log.info("SubSequence "+this.sequence); 
		log.info("SubStructure"+ this.structure); 
	}

	private Node findRoot(){
		int i=0; 
		int start = i; 
		int end = i; 
		int firstBacket = this.structure.indexOf("("); 
		//Se inizia con una parentesi la root sta alla fine
		if(firstBacket == 0 ){
			if(this.structure.charAt(this.structure.length()-1)== '.'){
				end = this.structure.length()-1; 
				i = end; 
				while(i>=0 && this.structure.charAt(i) == '.'){
					i--; 
				}
				start = i; 
			}
		}else{
			while (i<this.structure.length() && this.structure.charAt(i) == '.' ){
				i++; 
			}
			end = i; 
		}
		Node root = new Node(); 
		root.setI(start); 
		root.setJ(end); 
		log.info("Root Node : start "+start+" end "+end);
		return root; 
	}
	
	private List<Node> getNode(String s){
		List<Node> nodes = new ArrayList<Node>();  
		 
		for(int i=0; i<s.length()-1; i++){
			if(s.charAt(i)=='.'){
				Node node = new Node(); 
				node.setI(i); 
				while(i<s.length()-1 && s.charAt(i+1)== '.'){
					i++; 
				}
				node.setJ(i);
				nodes.add(node); 	
			}
		}	
		return nodes; 
	}

	private List<Edge> getEdge(String s){
		Stack<Edge> stack = new Stack<Edge>(); 
		List<Edge> edges = new ArrayList<Edge>(); 
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i) == '('){
				Edge e = new Edge(i,0); 
				stack.push(e); 
			}else if(s.charAt(i) == ')'){
				Edge e = stack.pop(); 
				e.setJ(i); 
				edges.add(e); 
			}
		}
		return edges; 
	}
	private void setEdgeLevel(){
		int j = 0 ; 
		for (int i = 1; i<this.edges.size(); i++) {
			int level =0; 
			j = i-1; 
			Edge current = this.edges.get(i); 
			while(j>=0){
				
				if(current.getJ()<this.edges.get(j).getJ()){
					level--; 
				}
				j--; 
			}
			current.setLevel(level); 
		}
	 
	}
	
	private void setNodeLevel(){
		for (Node node : this.nodes) {
			int edgePosition = 0; 
			int i_min = this.sequence.length(); 
			int j_min = this.sequence.length(); 
			for (Edge edge : this.edges) {
				if(edge.getI()<node.getI() && node.getJ()<edge.getJ()){ 
					int i_diff = node.getI()-edge.getI(); 
					int j_diff = edge.getJ()-node.getJ(); 
					if(i_diff <= i_min || j_diff <= j_min){
						edgePosition = this.edges.indexOf(edge);
						i_min = i_diff; 
						j_min = j_diff; 
					}
				}
			}
			Edge e = this.edges.get(edgePosition); 
			node.setLevel(e.getLevel()); 
		
			//Set del nodo correlato dall'arco
			this.edges.get(edgePosition).setDestination(node);
		}
	}
	private void setNodeEdge(){
		for (Node node : this.nodes) {
			((Node)node).setChilds(findSameLevelEdges( node ));
		}	
		this.tree = this.nodes; 
	}
	private void setStemSize(){
		//Array per la memorizzazione degli archi da eliminare dalla lista
		int[] edgeRemoved = new int[this.edges.size()+1]; 
		
		for(Edge edge : this.edges){
			int size = 1; 
			if(!edge.isRemoved()){
				for(int i = (this.edges.indexOf(edge)+1); i<this.edges.size(); i++){
					Edge current = this.edges.get(i); 	
					//Se le parentesi I e le parentesi J sono consecutive, sto trovando archi consecutivi
					if( (current.getI() == (edge.getI()+size)) &&
						(current.getJ() == (edge.getJ()-size))	){
						current.setRemoved(true); 
						size++;
						edgeRemoved[this.edges.indexOf(current)] = this.edges.indexOf(current);
					}
				}
			}
			edge.setStemSize(size); 
		}
		
		List<Edge> app = new LinkedList<Edge>(); 
		for(Edge edge : this.edges){
			if(!edge.isRemoved()){
				app.add(edge); 
			}
		}
		this.edges = new LinkedList<Edge>();
		this.edges.addAll(app); 
	}
	private List<Edge> findSameLevelEdges( Node node){
		List<Edge> edges = new LinkedList<Edge>(); 
		for (Edge element : this.edges) {
			if(element.getLevel() == node.getLevel()-1){ 
				edges.add(element);
			}
		}
		return edges; 
	}
	
	private void setSequence(){
		for(Node node : this.tree){
			String bases = ""; 
			if(node.getLevel()!=1){
				for(Element e : node.getElement()){
					bases += this.sequence.substring(e.getI(),e.getJ()+1); 
				}
				node.setBases(bases); 
			}
			for(Edge edge : node.getChilds()){ 
				String subSequence = this.sequence.substring(edge.getI(), edge.getJ()+1);
				edge.setBases(convertEdgeSequence(subSequence, edge.getStemSize())); 
			}
		}
		
	}
	private String convertEdgeSequence(String subSequence, int offset){ 
		int i = 0; 
		int j = subSequence.length()-1; 
		String result = ""; 
		while ( i < offset){
			char baseA = subSequence.charAt(i); 
			char baseB = subSequence.charAt(j);
			String key = ""+baseA+baseB;  
			result += BasePairMap.map.get(key).getConversion(); 
			i++; 
			j--; 
			
		}
		return result; 
	}
	public  void printNode(){
		log.info("NODE LIST "); 
		for (Node element : this.nodes) {
			log.info("element : I "+element.getI()+" - "+element.getJ()+" ; LEVEL : "+element.getLevel()+" BASES : "+element.getBases()+"" +
					" Element "+element.getElement().size()+ " EDGES "+element.getEdges().size()); 
		}
	}
	public  void printTree(){
		log.info("**** TREE **** "); 
		for (Node element : this.tree) {
			log.info("LEVEL : "+element.getLevel()+"" +
					" \t Element "+element.getElement().size()+ "\t EDGES "+element.getEdges().size()+"\t BASES : "+element.getBases()); 
		}
	}
	public  void printEdge(){
		log.info("EDGE LIST "); 
		for (Element element : this.edges) {
			log.info("element : I "+element.getI()+" - "+element.getJ()+" ; LEVEL : "+element.getLevel()+" BASES : "+element.getBases()); 
		}
	}
	
}
