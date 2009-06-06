package it.uniroma3.rnaclassifier.paum.models;

import it.uniroma3.rnaclassifier.model.ClassifierSetup;

public class PAUMSetup extends ClassifierSetup {

	private Float tauP;

	private Float tauN;

	private Integer totalLoops;

	private Float eta;

	//Variabile di switch dell'inizializzazione di PAUM
	//VALORI
	// 1 : tutti valori utente
	// 2 : impostazione utente margini
	// 3 : impostazine utente margini ed eta
	private int mode; 
	
	public PAUMSetup(Float eta, Float tauN, Float tauP, Integer totalLoops) {
		super();
		this.eta = eta;
		this.tauN = tauN;
		this.tauP = tauP;
		this.totalLoops = totalLoops;

		this.mode = 1; 
	}

	public PAUMSetup(Float tauN, Float tauP) {
		super();
		this.tauN = tauN;
		this.tauP = tauP;
		
		this.mode = 2; 
	}
	
	public PAUMSetup(Float tauN, Float tauP, Float eta) {
		super();
		this.tauN = tauN;
		this.tauP = tauP;
		this.eta = eta; 

		this.mode = 3; 
	}
	

	public int getMode() {
		return mode;
	}

	public Float getTauP() {
		return tauP;
	}

	public void setTauP(Float tauP) {
		this.tauP = tauP;
	}

	public Float getTauN() {
		return tauN;
	}

	public void setTauN(Float tauN) {
		this.tauN = tauN;
	}

	public Integer getTotalLoops() {
		return totalLoops;
	}

	public void setTotalLoops(Integer totalLoops) {
		this.totalLoops = totalLoops;
	}

	public Float getEta() {
		return eta;
	}

	public void setEta(Float eta) {
		this.eta = eta;
	}

	
}
