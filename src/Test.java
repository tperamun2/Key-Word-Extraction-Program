// Compile this program with  your code by running javac Test.java, and then run it with java Test 
// Each failed tests 1-10 are 4  marks. Test 11 is 10 marks
//package caculatenr;

//package findkeywords;
import java.util.Iterator;
import java.util.Random;

public class Test{
private static int numItems = 16384*2;

public static int computeHeight(Position n, AVLTree t)
    {
	if ( t.external(n) ) return(0);
	else return( 1+Math.max(computeHeight(t.left(n),t),computeHeight(t.right(n),t)) );
    }

public static boolean checkTree(Position n, AVLTree t)
    {
	boolean balanced = true;
	giveHeight(n,balanced,t);
	return(balanced);
    }

public static int giveHeight(Position n, boolean balanced,AVLTree t)
    {
	if ( t.external(n)  ) return(0);
	else
	    {
		int  l = giveHeight(t.left(n),balanced,t);
		int r = giveHeight(t.right(n),balanced,t);			
		if (Math.abs(l-r)> 1) {
                    balanced = false;
                
                }
		return( 1+Math.max(l,r) );
	    }
    }

// returns null  if there is an element in the left subtree of node p
// such that its value is larger than the element stored at p
// If this method returns a non-null String, then it is the largest key in the 
// subtree rooted at p
public static String checkLeftSubtree(AVLTree t,Position p, Comparator comp)
{
    if (t.external(p)) 
        return(new String("whatever"));
    else{
        
        String resultLeft = checkLeftSubtree(t,t.left(p),comp);
        if (resultLeft == null) return null;
        
        if (comp.compare(resultLeft,"whatever") != 0 ) {
            if ( comp.compare(resultLeft,(((DictEntry) (p.element())).key())) > 0 ) return null;
        }

        String resultRight = checkLeftSubtree(t,t.right(p),comp);
        if (  resultRight == null ) return(null);
        
        if (comp.compare(resultRight,"whatever") != 0) {
            if ( comp.compare(resultRight,(((DictEntry) (p.element())).key())) > 0  )
            return(resultRight);
            else return (  (String) (((DictEntry) (p.element())).key())  );
        }
        else return((String) (((DictEntry) (p.element())).key()));
    }
    
}

// returns null  if there is an element in the right subtree of node p
// such that its value is smaller than the element stored at p
// If this method returns a non-null String, then it is the smallest key in the 
// subtree rooted at p
public static String checkRightSubtree(AVLTree t,Position p, Comparator comp)
{
    if (t.external(p)) 
        return(new String("whatever"));
    else{
        
        String resultLeft = checkRightSubtree(t,t.left(p),comp);
        if (resultLeft == null) return null;
        
        //if (comp.compare(resultLeft,"whatever") != 0 ) {
        //    if ( comp.compare(resultLeft,(((DictEntry) (p.element())).key())) > 0 ) return null;
        //}

        String resultRight = checkRightSubtree(t,t.right(p),comp);
        if (  resultRight == null ) return(null);
        
        if (comp.compare(resultRight,"whatever") != 0 ) {
            if ( comp.compare(resultRight,(((DictEntry) (p.element())).key())) < 0 ) return null;
        }        
        
        if (comp.compare(resultLeft,"whatever") != 0) {
            if ( comp.compare(resultLeft,(((DictEntry) (p.element())).key())) < 0  )
            return(resultLeft);
            else return (  (String) (((DictEntry) (p.element())).key())  );
        }
        else return((String) (((DictEntry) (p.element())).key()));
    }
    
}



public static boolean checkIfBST(AVLTree treeI, Comparator comp){
    return( (checkLeftSubtree(treeI,treeI.giveRoot(),comp) != null ) && (checkRightSubtree(treeI,treeI.giveRoot(),comp)!= null)  );

} 

public static void main(String[] args) throws AVLtreeException{
   
    StringComparator stringComp = new StringComparator(); 
    int startTest = 1;
    
    if ( args.length == 1  ) // run starting at some test
        startTest = (new Integer(args[0])).intValue();
    
    switch(startTest){ 
    
    case 1: // Test 1: add and find a few entries
        try {
            AVLTree tree = new AVLTree(stringComp);
            String st;

            st = new String("hello");
            tree.insertNew(st,new Integer(0));
            st = new String("hello6");
            tree.insertNew(st,new Integer(1));
          
            if ( tree.find(new String("hello")) == null  || tree.find(new String("hello6")) == null  )
                System.out.println("*****Test 1: Failed");
            else System.out.println("Test 1: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 1: Failed");
        	e.printStackTrace();
        }
    
 
    case 2: //Test 2: find non existant entry
        try {
            AVLTree tree = new AVLTree(stringComp);
            String st;

            st = new String("hello");
            tree.insertNew(st,new Integer(0));
            st = new String("hello6");
            tree.insertNew(st,new Integer(1));
         
            if ( tree.find(new String("hello7")) != null )
            System.out.println("*****Test 2: Failed");
            else System.out.println("Test 2: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 2: Failed");
        }    
    
    case 3: // Test 3: try to insert a duplicate key entry
        try {
            AVLTree tree = new AVLTree(stringComp);
            String st;
            st = new String("hello");
            tree.insertNew(st,new Integer(0));
            tree.insertNew(st,new Integer(1));
            System.out.println("*****Test 3: Failed");
        }
        catch (AVLtreeException e){
            System.out.println("Test 3: Passed");
        }
    case 4: //Test 4: try to change value
        try {
            AVLTree tree = new AVLTree(stringComp);
            String st;

            st = new String("hello");
            tree.insertNew(st,new Integer(0));
            st = new String("hello6");
            tree.insertNew(st,new Integer(1));
        
            tree.modifyValue(new String("hello" ),new Integer(5) );
            DictEntry e = tree.find( new String("hello" ) );
            if ( ((Integer) e.value()).intValue() != 5 )
                System.out.println("*****Test 4: Failed");
            else  System.out.println("Test 4: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 4: Failed");
        }
   

    case 5 :// Test 5: insert many entries
        try {
            boolean fail = false;
            AVLTree t = new AVLTree(stringComp);
            String st;
       
            for ( int i = 0; i < 10000;i++ )  {
                st = new String(Integer.toString(i));
                t.insertNew(st,new Integer(i));
            }
            for ( int i = 0; i < 10000;i++ )   {
            	st = new String(Integer.toString(i));
		DictEntry e = t.find(st);
		if ( e == null ) fail = true;
            }
            if ( fail )
                System.out.println("*****Test 5: Failed");
            else System.out.println("Test 5: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 5: Failed");
    }

    case 6: //Test 6: try to change value in non-existing entry
        try {
            AVLTree tree = new AVLTree(stringComp);
            String st;

            st = new String("hello");
            tree.insertNew(st,new Integer(0));
            st = new String("hello6");
            tree.insertNew(st,new Integer(1));
                
            tree.modifyValue(new String("hello454646" ),new Integer(5) );
            System.out.println("*****Test 6: Failed");
        }
        catch (AVLtreeException e){
            System.out.println("Test 6: Passed");
    }
        
    case 7: //Test 7: try to remove an entry
        try {
            AVLTree tree = new AVLTree(stringComp);
            String st;

            st = new String("hello");
            tree.insertNew(st,new Integer(0));
            st = new String("hello6");
            tree.insertNew(st,new Integer(1));
            
          //  tree.remove(new String("hello"));
            tree.remove(new String("hello6" ));
            DictEntry e= tree.find(new String("hello6" ));
            if (e != null ) System.out.println("*****Test 7: Failed");
            else System.out.println("Test 7: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 7: Failed");
        }
   
    case 8: // Test 8: Check in order iterator 
        try {
            boolean fail = false;
            AVLTree t = new AVLTree(new IntegerComparator());
            int num = 1000,i,j;
            int[] table = new int[num];
       
            for ( i = 0; i < num; i++) table[i] = i;
       
            Random r = new Random();
       
            for ( i = 0; i < num; i++){
                int t1 = r.nextInt(num);
                int t2 = r.nextInt(num);
                int temp = table[t1];
                table[t1]= table[t2];
                table[t2] = temp;
            }
           
              
            for ( i = 0; i < num;i++ )   {
                t.insertNew(new Integer(i), new Integer(0));
            }

            Iterator<DictEntry> it = t.inOrder();
            int count = 0;
            while (it.hasNext())
            {
                DictEntry next = it.next();
                if ( ((Integer) next.key()).intValue() != count )
                {
                    fail = true;
                    break;
                }
                count++;
            }        
            if ( count != num) fail = true;
            if ( fail )
                System.out.println("*****Test 8: Failed");
            else System.out.println("Test 8: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 8: Failed");
        }
   
    case 9: // Test 9: Insert and remove lots of entries
        try {
            boolean fail = false;
            AVLTree t = new AVLTree(new IntegerComparator());
            
            int num = 10000,i,j;
            int[] table = new int[num];
       
            for ( i = 0; i < num; i++) table[i] = i;

       
            Random r = new Random();
       
            for ( i = 0; i < num; i++){
                int t1 = r.nextInt(num);
                int t2 = r.nextInt(num);
                int temp = table[t1];
                table[t1]= table[t2];
                table[t2] = temp;
            }
              
            for ( i = 0; i < num; i++ )   {
                t.insertNew(new Integer(table[i]), new Integer(i));
            }
            
            for ( i = 0; i < num/2;i++ )   {
                t.remove(new Integer(table[i]));
            }
            
            for ( i = 0; i < num/2;i++ )   {
                if (t.find(new Integer(table[i])) != null){
                    fail = true;
                    break;
                }
           }
            for ( i = num/2; i < num;i++ )   {
              
                if (t.find(new Integer(table[i])) == null){
                    fail = true;
                    break;
                }
           }
            
            if ( fail )
                System.out.println("*****Test 9: Failed" );
            else System.out.println("Test 9: Passed");
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 9: Failed exe");
            e.printStackTrace();
        } 
        
    case 10: // Test the findnLargest method()
        try{
            boolean fail = false;
            AVLTree t = new AVLTree(new IntegerComparator());
        
            int num = 10000,i,j;
            int[] table = new int[num];
   
            for ( i = 0; i < num; i++) table[i] = i;
   
            Random r = new Random();
   
            for ( i = 0; i < num; i++){
                int t1 = r.nextInt(num);
                int t2 = r.nextInt(num);
                int temp = table[t1];
                table[t1]= table[t2];
                table[t2] = temp;
            }

            for ( i = 0; i < num;i++ )   {
                t.insertNew(new Integer(table[i]), new Integer(i));
            }
            
            int n = 100;
            Iterator<DictEntry> it = t.findnLargestKeys(n);
            
            int number = num-1;
            while (it.hasNext()){
                DictEntry next = it.next();
                Integer nextKey = (Integer) next.key();
                if ( nextKey.intValue() != number ) {
                    fail = true;
                    break;
                }
                number--;
            }
        if ( fail )
            System.out.println("*****Test 10: Failed " );
        else System.out.println("Test 10: Passed");
            
        }
        catch (AVLtreeException e){
            System.out.println("*****Test 10: Failed");
        }
   
    
        case 11:  // Test 11: Check if the tree is indeed a binary search tree
            try{
                Comparator comp = new StringComparator();
                AVLTree treeI = new AVLTree (comp);
                int numItems = 1000;
                for ( int i = 0; i < numItems;i++ ){
                    treeI.insertNew(new String((new Integer(i).toString())),new Integer(i));
                }
                if ( checkIfBST(treeI,comp)) System.out.println("Test 11: Passed");
                else System.out.println("*****Test 11: Failed");
            }
            catch (AVLtreeException e){
                System.out.println("*****Test 11: Failed");
            }

        case 12: // Test 12: check height-balance property
            try{
                AVLTree treeI = new AVLTree (new StringComparator());
                int numItems = 1000;
                for ( int i = 0; i < numItems;i++ ){
                    treeI.insertNew(new String((new Integer(i).toString())),new Integer(i));
                }
  
                int  height = computeHeight(treeI.giveRoot(),treeI);
                boolean fail = false;
                int size = numItems;

                int bound = (int) (Math.log(size)/Math.log(2)) ;
                if ( height <   bound-2 || height > 2*bound+2 )
                    fail = true;
                //System.out.println("Height is: "+ height + " Bound is: " + bound);
 
                boolean balanced   = checkTree(treeI.giveRoot(),treeI);
    
                if ( fail || !balanced  ) System.out.println("*****Test 12: Failed ");
                else System.out.println("Test 12: Passed");
            }
        catch (AVLtreeException e){
            System.out.println("*****Test 12: Failed");
        }

}
}
}
