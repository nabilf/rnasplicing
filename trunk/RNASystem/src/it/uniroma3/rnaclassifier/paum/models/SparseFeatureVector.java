package it.uniroma3.rnaclassifier.paum.models;
public class SparseFeatureVector implements Cloneable{
  /** length of feature vector (number of non-zero elements). */
  int len;
  /** indexes of non-zero elements. */
  public int[] indexes;
  /** Values of non-zero elements. */
  public float[] values;

  /** Trivial constructor. */
  public SparseFeatureVector() {
    len = 0;
    indexes = null;
    values = null;
  }

  /** Constructor with length and two arrays. */
  public SparseFeatureVector(int num) {
    len = num;
    indexes = new int[num];
    values = new float[num];
  }

  public int getLen() {
    return len;
  }

  public int[] getIndexes() {
    return indexes;
  }
  
  public SparseFeatureVector clone(){
	  SparseFeatureVector clone = new SparseFeatureVector(this.len); 
	  clone.setIndexes(this.getIndexes()); 
	  clone.setValues(this.getValues()); 
	  return clone; 
  }

  public float[] getValues() {
    return values;
  }

public void setLen(int len) {
	this.len = len;
}

public void setIndexes(int[] indexes) {
	this.indexes = indexes;
}

public void setValues(float[] values) {
	this.values = values;
}
  
}