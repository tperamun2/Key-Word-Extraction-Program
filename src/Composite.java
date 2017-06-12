
public class Composite {

	private String key; // This key is a String word
	private int frequencyCount; // keeps track of the frequency of the word

	public Composite(String word, Integer frequency) {

		this.key = word;
		this.frequencyCount = frequency;

	}

	public Integer frequency() {
		return frequencyCount;

	}

	public String word() {
		return key;
	}
}
