import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

public class FindKeyWords {

	public static void main(String[] args) throws java.io.IOException, AVLtreeException {
//		 if (args.length != 3) {
//			 System.out.println("Usage: FindKeyWords k file.txt MostFrequentEnglishWords.txt");
//			System.exit(0);
//		 }

		BufferedInputStream mostFreq, otherFile;
		int k;
		
		try {

//			k = Integer.parseInt(args[0]); //the number for the frequency of a word
//			otherFile = new BufferedInputStream(new FileInputStream(args[1])); //file for any novel 
//			mostFreq = new BufferedInputStream(new FileInputStream(args[2])); //MostFrequentEnglishWords.txt

			//
			 mostFreq = new BufferedInputStream(new FileInputStream(
			 "/Users/timalperamune/Documents/workspace/2210Assignment4/MostFrequentEnglishWords.txt"));
			 otherFile = new BufferedInputStream(
			 new
			 FileInputStream("/Users/timalperamune/Documents/workspace/2210Assignment4/SmithWealthNations.txt"));

			AVLTree tree1 = new AVLTree(new StringComparator());

			FileWordRead readWords = new FileWordRead(otherFile);
			// Part 1
			while (readWords.hasNextWord()) {
				String word = readWords.nextWord();
				if (tree1.find(word) != null) { //if found
					tree1.modifyValue(word, (Integer) tree1.find(word).value() + 1);
				}else {
					tree1.insertNew(word, 1);
				}
			}
			// Part 2
			AVLTree tree2 = new AVLTree(new CompositeComparator());
			Iterator<DictEntry> it = tree1.inOrder(); //Call inOrder from AVLTree to do an inOrder Traversal on tree1

			while (it.hasNext()) {
				DictEntry entry = it.next();
				Composite comp = new Composite((String) entry.key(), (Integer) entry.value());
				tree2.insertNew(comp, null);
			}
			// Part 3
			AVLTree tree3 = new AVLTree(new StringComparator());
			FileWordRead freqWords = new FileWordRead(mostFreq);

			while (freqWords.hasNextWord()) {
				String word = freqWords.nextWord();

				if (tree3.find(word) == null)
					tree3.insertNew(word, null);
			}

			 Iterator<DictEntry> it2 = tree2.findnLargestKeys(1000);
			//Iterator<DictEntry> it2 = tree2.findnLargestKeys(k);
			AVLTree tree4 = new AVLTree(new StringComparator());

			while (it2.hasNext()) {
				DictEntry e = it2.next();
				Composite en = (Composite) e.key();

				if (tree3.find(en.word()) != null) {
					continue; // ignore and go to the next iteration
				}
				tree4.insertNew(en.word(), en.frequency());
			}
			Iterator<DictEntry> it3 = tree4.inOrder();
			while (it3.hasNext()) {
				DictEntry entry = it3.next();
				System.out.println(entry.key() + " " + entry.value()); //prints the most frequent words in all the novels as long
																		// as it's not in the file MostFrequentEnglishWords.txt			
			}

		} catch (IOException e) { // catch exceptions caused by file
			// input/output errors
			System.out.println("Check your file name");
			System.exit(0);
		}

	}
}