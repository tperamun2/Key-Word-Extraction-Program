/* Comparator for Integers */
//package a3;
public class IntegerComparator implements Comparator{
    public IntegerComparator(){}
   
    public int compare(Object a, Object b) throws ClassCastException{
	Integer aComp = (Integer) a;
	Integer bComp = (Integer) b;
        
	if ( aComp.intValue() < bComp.intValue() ) return(-1);
        else if  (aComp.intValue() == bComp.intValue() ) return(0);
	else return(1);
    };  
}

