/* Comparator for strings. Uses Lexographical ordering */
//package a3;
public class StringComparator implements Comparator{
    public StringComparator(){}
    /* compare returns negative integer if a < b, 0 if a = b, and positive integer if a > b */
    public int compare(Object a, Object b) throws ClassCastException{
	String sa = (String) a;
	String sb = (String) b;
          
	return(sa.compareTo(sb));
	
    }  
}

