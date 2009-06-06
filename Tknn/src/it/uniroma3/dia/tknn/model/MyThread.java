package it.uniroma3.dia.tknn.model;

public abstract class MyThread extends Thread {
	protected int end = 0;

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	} 
	
	@Override
	public abstract void run(); 
	
	
}
