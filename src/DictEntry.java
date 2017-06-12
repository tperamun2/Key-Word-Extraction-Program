
public class DictEntry {
	private Object key; // Every Dictionary Entry has a key
	private Object value; // and a value for that particular key

	public DictEntry(Object key, Object value) {
		this.key = key;
		this.value = value;

	}

	public Object key() {
		return key;
	}

	public Object value() {
		return value;
	}
}
