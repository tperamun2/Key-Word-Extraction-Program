
public class CompositeComparator implements Comparator {

	@Override
	/**
	 * compare returns -1 if frequency of a is less than frequency of b, 1 otherwise).
	 * In case if frequencies of a, b are the same, it uses the alphabetical order on the words to break this tie
	 *  returns -1 if the word of Composite a is smaller, alphabetically, than the word of b, and 1 otherwise
	 */
	
	public int compare(Object a, Object b) throws ClassCastException {
		
		Composite aComp = (Composite)a;
		Composite bComp = (Composite)b;
		
		if (aComp.frequency()==bComp.frequency()){
			if(aComp.word().compareTo(bComp.word())<0){
				return -1;
			}
			else 
				return 1;
		}
		else if(aComp.frequency()< bComp.frequency())
			return -1;
		else 
			return 1;
		
	}

}
