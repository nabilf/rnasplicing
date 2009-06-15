package it.uniroma3.rnasystem.model;

import java.io.Serializable;

public enum RNAClass implements Serializable {
        
        NONE, EI, IE, N, MICRO, MACRO; 
        
        public static  RNAClass getRNAClass(String clazz){
                if(clazz.equalsIgnoreCase(RNAClass.EI.toString())) {
                        return RNAClass.EI;
                } else if(clazz.equalsIgnoreCase(RNAClass.IE.toString()))
                        return RNAClass.IE; 
                else if(clazz.equalsIgnoreCase(RNAClass.N.toString()))
                        return RNAClass.N; 
                else
                        return RNAClass.NONE; 
        }
        
}
