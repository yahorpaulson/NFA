package ab1;

import java.util.Set;
import java.util.List;

/**
 * Schnittstelle zur Implementierung eines NFA.
 */
public interface NFA {

	/**
	 * Klasse, die einen Übergang in der Übergangsrelation repräsentiert.
	 */
	public final class Transition { 

		/** Der Ausganszustand */
		public final Integer currentState;

		/** Das zu lesende Symbol. null für Epsilon-Übergänge */
		public final Character symbol;

		/** Der Folgezustand */
		public final Integer nextState;

		/** Erzeugt eine neue Übergangs-Instanz */
		public Transition(Integer currentState,
				  Character symbol,
				  Integer nextState)
		{
			this.currentState = currentState;
			this.symbol = symbol;
			this.nextState = nextState;
		}
	}

	/**
	 * @return Anzahl Zustände
	 */
	public int getNumStates();

	/**
	 * @return Startzustand
	 */
	public Integer getInitialState();

	/**
	 * @return Menge der Endzustände
	 */
	public Set<Integer> getAcceptingStates();

	/**
	 * @param s zu testender Zustand
	 * @throws IllegalArgumentException Wenn es den Zustand nicht gibt
	 * @return true, wenn der Zustand s ein Endzustand ist. Ansonsten false.
	 */
	public boolean isAcceptingState(Integer s);

	/**
	 * Liefert alle Übergänge des NFAs als Liste.
	 *
	 * @return Eine Liste aller Zustandsübergänge im NFA.
	 */
	public List<Transition> getTransitions();
	
	/**
	 * Erzeugt einen Automaten, der die Vereinigung des Automaten mit dem
	 * übergebenen Automaten darstellt.
	 *
	 * @param a der zu vereinigende Automat
	 * @return neuer Automat
	 */
	public NFA union(NFA a);

	/**
	 * Hängt den Automaten a an den Automaten an
	 *
	 * @param a der anzuhängende Automat
	 * @return neuer Automat
	 */
	public NFA concat(NFA a);

	/**
	 * Bildet den Kleene-Stern des Automaten
	 * @return neuer Automat
	 */
	public NFA kleeneStar();

	/**
	 * Berechnet alle Nachfolgekonfigurationen einer gegebenen
	 * Konfiguration, also einen Berechnungsschritt.
	 *
	 * @param configuration Die Ausgangskonfiguration
	 * @throws IllegalArgumentException wenn die Konfiguration einen
	 *                                  ungültigen Zustand enthält
	 * @return alle Nachfolgekonfigurationen gemäß Ableitungsrelation des NFAs
	 */
	public Set<Configuration> step(Configuration configuration);
}
