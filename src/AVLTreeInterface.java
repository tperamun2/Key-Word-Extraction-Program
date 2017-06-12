// This is the interface for the AVLTree class you have to write 
//package findkeywords;

import java.util.Iterator;
public interface AVLTreeInterface{
   
    // true if node at position p is external, false otherwise
    public boolean external(Position p);
    
    // returns the postition of the left child of p
    public Position left(Position p);
    
    // returns the postition of the right child of p
    public Position right(Position p);
    
    // Returns the dictionary entry associated with the input key. If key is not in the tree returns null 
    public DictEntry find(Object key);

    // Delete entry with key K. Throws exception if key  is not present in the tree
    public DictEntry remove(Object key) throws AVLtreeException;
   
    // Finds the entry with the input key, and changes the value of this entry to valueNew 
    // If the entry with input key is not in the tree, throws AVLtreeException          
    public void modifyValue(Object key, Object valueNew ) throws AVLtreeException;
 	
    // inserts new entry in the dictionary. If the entry with the input key already exists in the 
    // tree, throws AVLtreeException                                                             
    public void insertNew(Object key, Object value) throws AVLtreeException;

    // Returns true if no entries in the tree, false otherwise 
    public boolean isEmpty();

    // Returns the root of the tree. 
    public Position giveRoot();
  
    // returns the height of the tree
    public int treeHeight();
    
    // returns an iterator over all entries in the dictionary
    // "inOrder" traversal is performed
    public Iterator<DictEntry> inOrder();
    
    // finds n largest key entries and returns iterator over these entries. 
    // the order should be decreasing, i.e. first the largest entry
    // then the second largest... lastly the nth largest entry
    public Iterator<DictEntry> findnLargestKeys(int n);
    
    
    // returns the number of entries in the tree
    public int size();
}


