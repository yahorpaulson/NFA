package ab1;

/**
 * Repräsentiert eine Konfiguration eines endlichen Automaten.
 */
public class Configuration {

	private Integer state;
	private String word;

	public Configuration(Integer state, String word)
	{
		if(word == null) throw new IllegalArgumentException("Paramter 'word' cannot be null!");

		this.state = state;
		this.word = word;
	}

	public Integer getState() { return new Integer(this.state); }
	public String  getWord()  { return new String(this.word);  }

	@Override
	public int hashCode() {
		return state.hashCode() + this.word.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) return true;
		if (!(o instanceof Configuration)) return false;
		 
		Configuration c = (Configuration) o;
		 
		return Integer.compare(state, c.state) == 0 && word.compareTo(c.word) == 0;
	}
}
