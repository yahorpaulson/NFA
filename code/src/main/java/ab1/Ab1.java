package ab1;

import java.util.List;
import java.util.Set;

/**
 * Schnittstelle zur Erzeugung und Ausführung von endlichen Automaten.
 */
public interface Ab1 {

	/** Diese Methode liefert ein String-Array der Länge 1, 2, oder 3, in
	 * dem die Matrikelnummern der Gruppenmitglieder enthalten sind.
	 *
	 * @return String-Array mit Matrikelnummern
	 */
	public String[] matriculationNumbers();

	/**
	 * Erzeugt einen NFA, der Wörter über dem Alphabet A-Z, a-z, 0-9 und
	 * dem Leerzeichen erkennt, die dem übergebenen Muster entsprechen. Das
	 * Muster ist ein vereinfachter regulärer Ausdruck, in dem nur einzelne
	 * Zeichen, deren Verkettung (durch Hintereinanderschreiben), sowie die
	 * Sonderzeichen "." und "*" vorkommen, wobei das Sonderzeichen "." für
	 * ein beliebiges Zeichen aus dem Alphabet steht, und das Sonderzeichen
	 * "*" bedeutet, dass das vorangegangene Zeichen beliebig oft
	 * wiederholt werden darf (Hinweis: auch die Kombination ".*" ist
	 * erlaubt). Mehrere solcher vereinfachten regulären Ausdrücke können
	 * durch das zusätzliche Sonderzeichen "|" aneinandergereiht werden,
	 * wodurch ausgedrückt wird, dass Wörter des ersten regulären Ausdrucks
	 * ODER des zweiten regulären Ausdrucks ODER des dritten usw. erkannt
	 * werden sollen. Der leere String entspricht dem Leerwort. Kein String
	 * ("null") entspricht der leeren Menge.
	 *
	 * Tipp: Verwenden Sie die NFA-Methoden für Vereinigung, Konkatenation,
	 * und Klenee-Stern.
	 *
	 * @param pattern das Textmuster, das vom NFA erkannt werden soll
	 * @return den entsprechenden NFA für das Textmuster
	 */
	public NFA fromPattern(String pattern);

	/**
	 * Erzeugt einen NFA aus der gegebenen Liste der Übergänge mit den
	 * übergebenen Start- und Endzuständen. Das Eingabealphabet und die
	 * Menge der Zustände werden implizit mittels der Liste der Übergänge
	 * spezifiziert: Genau die dort vorkommenden Symbole bzw. Zustände
	 * formen das Alphabet bzw. die Zustandsmenge des NFA.
	 *
	 * @param transitions Die Übergangsrelation als Liste
	 * @param initialState Der Startzustand
	 * @param acceptingState Die Menge der Endzustände
	 * @return den entsprechenden NFA
	 */
	public NFA fromTransitions(List<NFA.Transition> transitions,
			           Integer initialState,
				   Set<Integer> acceptingStates);

	/**
	 * Überprüft, ob der NFA ein gegebenes Wort akzeptiert. Verwenden Sie
	 * bei der Implementierung ausschließlich die Methoden des
	 * NFA-Interfaces (speziell die Methoden step und isAcceptingState).
	 *
	 * @param a der NFA
	 * @param word das Input-Wort
	 * @return true, wenn der NFA das Wort akzeptiert, sonst false
	 */
	public boolean accepts(NFA a, String word);

	/**
	 * Erstellen Sie einen vereinfachten (siehe oben) regulären Ausdruck,
	 * der auf alle Zeichenketten passt, die die Zeichenkette "abc" oder
	 * "cba" enthalten.
	 *
	 * Hinweis: Der Ausdruck soll daher mit der "fromPattern"-Methode oben
	 * verwendet werden können.
	 */
	public String pattern();

	/**
	 * Erstellen Sie eine Übergangsrelation für einen NFA, dessen Sprache
	 * über dem Alphabet {a, b} alle Wörter beinhaltet, die das Wort "aab"
	 * als Infix haben, und dieser Infix an einer geraden Position beginnt
	 * (also bei Index 0, 2, 4, ...).
	 *
	 * Der Startzustand hat hierbei die Nummer 0, der (einzige) Endzustand
	 * die Nummer 1.
	 *
	 * Hinweis: Bei einem NFA können mehrere Endzustände immer mittels
	 * Epsilon-Übergängen zu einem einzigen Zusammengefasst werden.
	 */
	public List<NFA.Transition> transitions1();

	/**
	 * Erstellen Sie eine Übergangsrelation für einen NFA, dessen Sprache
	 * über dem Alphabet {1, 0} genau jene Wörter sind, die das Wort "00"
	 * nicht beinhalten.
	 *
	 * Der Startzustand hat hierbei die Nummer 0, der (einzige) Endzustand
	 * die Nummer 1.
	 *
	 * Hinweis: Bei einem NFA können mehrere Endzustände immer mittels
	 * Epsilon-Übergängen zu einem einzigen Zusammengefasst werden.
	 */
	public List<NFA.Transition> transitions2();

	/**
	 * Erstellen Sie eine Übergangsrelation für einen NFA, dessen Sprache
	 * all jene Wörter umfasst, die Zeichenketten über dem lateinischen
	 * Alphabet sind, die mit "th" beginnen, und mit "s" enden, sonst aber
	 * kein "s" beinhalten.
	 *
	 * Der Startzustand hat hierbei die Nummer 0, der (einzige) Endzustand
	 * die Nummer 1.
	 *
	 * Hinweis: Bei einem NFA können mehrere Endzustände immer mittels
	 * Epsilon-Übergängen zu einem einzigen Zusammengefasst werden.
	 */
	public List<NFA.Transition> transitions3();

	/**
	 * Erstellen Sie eine Übergangsrelation für einen NFA, dessen Sprache
	 * all jene Wörter über dem Binäralphabet {0, 1} umfasst, die entweder
	 * mit 0 beginnen, oder mit 0 aufhören, oder wo die Anzahl der
	 * enthaltenen Einsen kongruent zu einer Ihrer Matrikelnummern (siehe
	 * Methode "matriculationNumbers") modulo 10 sind.
	 *
	 * Der Startzustand hat hierbei die Nummer 0, der (einzige) Endzustand
	 * die Nummer 1.
	 *
	 * Hinweis: Bei einem NFA können mehrere Endzustände immer mittels
	 * Epsilon-Übergängen zu einem einzigen Zusammengefasst werden.
	 */
	public List<NFA.Transition> transitions4();
}
