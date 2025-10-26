package ab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

import ab1.Ab1;
import ab1.NFA;
import ab1.Configuration;

import ab1.impl.Siarheyeu.Ab1Impl;

public class Ab1Tests {
	private static NFA n1; // leere Menge
	private static NFA n2; // epsilon
	private static NFA n3; // a*
	private static NFA n4; // ab*
	private static NFA n5; // ab*c*
	private static NFA n6; // c*
	private static NFA n7; // .*
	private static NFA n8; // michael.morak@aau.at
	private static NFA n9; // a b c
	private static NFA n10; // a|b|c
	private static NFA n11; // a *b *c
	private static NFA n12; // .*a|.*b.*|c.*

	static Ab1Impl test = new Ab1Impl();

	@BeforeAll
	public static void InitializeNFA1() {
		n1 = test.fromPattern(null);
	}

	@BeforeAll
	public static void InitializeNFA2() {
		n2 = test.fromPattern("");
	}

	@BeforeAll
	public static void InitializeNFA3() {
		n3 = test.fromPattern("a*");
	}

	@BeforeAll
	public static void InitializeNFA4() {
		n4 = test.fromPattern("ab*");
	}

	@BeforeAll
	public static void InitializeNFA5() {
		n5 = test.fromPattern("ab*c*");
	}

	@BeforeAll
	public static void InitializeNFA6() {
		n6 = test.fromPattern("c*");
	}

	@BeforeAll
	public static void InitializeNFA7() {
		n7 = test.fromPattern(".*");
	}

	@BeforeAll
	public static void InitializeNFA8() {
		n8 = test.fromPattern("michael.morak@aau.at");
	}

	@BeforeAll
	public static void InitializeNFA9() {
		n9 = test.fromPattern("a b c");
	}

	@BeforeAll
	public static void InitializeNFA10() {
		n10 = test.fromPattern("a|b|c");
	}

	@BeforeAll
	public static void InitializeNFA11() {
		n11 = test.fromPattern("a *b *c");
	}

	@BeforeAll
	public static void InitializeNFA12() {
		n12 = test.fromPattern(".*a|.*b.*|c.*");
	}

	@Test
	public void NFA_Language() {
		testLanguageNFA1(n1);
		testLanguageNFA2(n2);
		testLanguageNFA3(n3);
		testLanguageNFA4(n4);
		testLanguageNFA5(n5);
		testLanguageNFA6(n6);
		testLanguageNFA7(n7);
		testLanguageNFA8(n8);
		testLanguageNFA9(n9);
		testLanguageNFA10(n10);
		testLanguageNFA11(n11);
		testLanguageNFA12(n12);

		testNFASimpleAccept();
		testNFASimpleReject();

	}

	@Test
	public void NFA_Operations_Union() {
		////////////////////// Vereinigung ///////////////////

		NFA n = n1.union(n2);
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		////////////////////////

		n = n2.union(n3);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		//////////////////////////

		n = n3.union(n4);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		////////////////////////

		n = n4.union(n5);
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "abc"));

		///////////////////////

		n = n5.union(n6);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "abc"));

		////////////////////////

		n = n6.union(n7);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "abc"));

		////////////////////////

		n = n7.union(n8);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "abc"));

		////////////////////////

		n = n8.union(n9);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

		////////////////////////

		n = n9.union(n10);
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

		////////////////////////

		n = n10.union(n11);
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

		////////////////////////

		n = n11.union(n12);
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

	}

	@Test
	public void NFA_Operations_Concat() {

		////////////////////// Concat ///////////////////

		NFA n = n1.concat(n2);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n2.concat(n3);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		//////////////////////////

		n = n3.concat(n4);
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n4.concat(n5);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		///////////////////////

		n = n5.concat(n6);
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n6.concat(n7);
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

		////////////////////////

		n = n7.concat(n8);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n8.concat(n9);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n9.concat(n10);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));
		assertTrue(test.accepts(n, "a b ca"));
		assertTrue(test.accepts(n, "a b cb"));

		////////////////////////

		n = n10.concat(n11);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));
		assertTrue(test.accepts(n, "aa b c"));
		assertTrue(test.accepts(n, "ba b c"));

		////////////////////////

		n = n11.concat(n12);
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));
		assertTrue(test.accepts(n, "a b ca"));
		assertTrue(test.accepts(n, "a b cb"));

	}

	@Test
	public void NFA_Operations_Star() {
		////////////////////// Stern ///////////////////

		NFA n = n1.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		////////////////////////

		n = n2.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		//////////////////////////

		n = n3.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		////////////////////////

		n = n4.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		///////////////////////

		n = n5.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "abc"));

		////////////////////////

		n = n6.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "abc"));

		////////////////////////

		n = n7.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "abc"));

		////////////////////////

		n = n8.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n9.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

		////////////////////////

		n = n10.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertFalse(test.accepts(n, "a b c"));

		////////////////////////

		n = n11.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

		////////////////////////

		n = n12.kleeneStar();
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "ab"));
		assertTrue(test.accepts(n, "a b c"));

	}

	@Test
	public void test_Regex() {

		String regex = test.pattern();

		assertTrue(Pattern.matches(regex, "ababcabab"));
		assertTrue(Pattern.matches(regex, "ababcabcab"));
		assertTrue(Pattern.matches(regex, "abaccbababb"));

		assertFalse(Pattern.matches(regex, "abababab"));
		assertFalse(Pattern.matches(regex, "abacabbab"));
		assertFalse(Pattern.matches(regex, "abacababbc"));

	}

	@Test
	public void test_transitions1() {

		NFA n = test.fromTransitions(test.transitions1(), 0, Set.of(1));

		assertTrue(test.accepts(n, "aaaaaaaaaabb"));
		assertTrue(test.accepts(n, "ababababaab"));
		assertTrue(test.accepts(n, "aab"));

		assertFalse(test.accepts(n, "aaab"));
		assertFalse(test.accepts(n, "ababaaab"));
		assertFalse(test.accepts(n, "babababaab"));

	}

	@Test
	public void test_transitions2() {

		NFA n = test.fromTransitions(test.transitions2(), 0, Set.of(1));

		assertTrue(test.accepts(n, "10101010101"));
		assertTrue(test.accepts(n, "1111111111"));
		assertTrue(test.accepts(n, "01010101010111111111010101010"));

		assertFalse(test.accepts(n, "1111111111111100"));
		assertFalse(test.accepts(n, "00000000000"));
		assertFalse(test.accepts(n, "01101000100100"));

	}

	@Test
	public void test_transitions3() {

		NFA n = test.fromTransitions(test.transitions3(), 0, Set.of(1));

		assertTrue(test.accepts(n, "this"));
		assertTrue(test.accepts(n, "thus"));
		assertTrue(test.accepts(n, "thatcheritis"));

		assertFalse(test.accepts(n, "then"));
		assertFalse(test.accepts(n, "that"));
		assertFalse(test.accepts(n, "thesis"));

	}

	@Test
	public void test_transitions4() {

		NFA n = test.fromTransitions(test.transitions4(), 0, Set.of(1));

		// Reste der Divisionen der Matrikelnummern durch 10
		String[] mn = test.matriculationNumbers();
		int rest1 = Integer.parseInt(mn[0]) % 10;
		int rest2 = mn.length >= 2 ? Integer.parseInt(mn[1]) % 10 : rest1;
		int rest3 = mn.length >= 3 ? Integer.parseInt(mn[2]) % 10 : rest2;

		assertTrue(test.accepts(n, "1111111111111100"));
		assertTrue(test.accepts(n, "0011111111111111"));
		assertTrue(test.accepts(n, "00000000000"));
		assertTrue(test.accepts(n, "01101000100100"));

		// 2 Einsen
		if(2 == rest1 || 2 == rest2 || 2 == rest3)
			assertTrue(test.accepts(n, "10000000000000001"));
		else
			assertFalse(test.accepts(n, "10000000000000001"));


		// 6 Einsen
		if(6 == rest1 || 6 == rest2 || 6 == rest3)
			assertTrue(test.accepts(n, "10101010101"));
		else
			assertFalse(test.accepts(n, "10101010101"));

		// 10 Einsen
		if(0 == rest1 || 0 == rest2 || 0 == rest3)
			assertTrue(test.accepts(n, "1111111111"));
		else
			assertFalse(test.accepts(n, "1111111111"));

	}

	public void testNFASimpleAccept() {
		NFA n = test.fromPattern("a");
		Configuration c0 = new Configuration(n.getInitialState(), "a");
		Set<Configuration> s = n.step(c0);

		boolean hasAccepted = false;
		for(Configuration c : s)
			hasAccepted |= (n.isAcceptingState(c.getState()) && "".equals(c.getWord()));
		assertTrue(hasAccepted);
	}

	public void testNFASimpleReject() {
		NFA n = test.fromPattern("b");
		Configuration c0 = new Configuration(n.getInitialState(), "a");
		Set<Configuration> s = n.step(c0);

		boolean hasAccepted = false;
		for(Configuration c : s)
			hasAccepted |= (n.isAcceptingState(c.getState()) && "".equals(c.getWord()));
		assertFalse(hasAccepted);
	}

	private void testLanguageNFA1(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertFalse(test.accepts(n, "c"));
	}

	private void testLanguageNFA2(NFA n) {
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertFalse(test.accepts(n, "c"));
	}

	private void testLanguageNFA3(NFA n) {
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertFalse(test.accepts(n, "c"));
	}

	private void testLanguageNFA4(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertTrue(test.accepts(n, "abb"));
		assertFalse(test.accepts(n, "c"));
	}

	private void testLanguageNFA5(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertTrue(test.accepts(n, "abb"));
		assertTrue(test.accepts(n, "ac"));
	}

	private void testLanguageNFA6(NFA n) {
		assertTrue(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertFalse(test.accepts(n, "abb"));
		assertTrue(test.accepts(n, "c"));
	}

	private void testLanguageNFA7(NFA n) {
		assertTrue(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "aaa"));
		assertTrue(test.accepts(n, "b"));
		assertTrue(test.accepts(n, "abb"));
		assertTrue(test.accepts(n, "c"));
	}

	private void testLanguageNFA8(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertFalse(test.accepts(n, "b"));
		assertFalse(test.accepts(n, "abb"));
		assertFalse(test.accepts(n, "c"));
	}

	private void testLanguageNFA9(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "abc"));
		assertTrue(test.accepts(n, "a b c"));
	}

	private void testLanguageNFA10(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertFalse(test.accepts(n, "aa"));
		assertFalse(test.accepts(n, "aaa"));
		assertTrue(test.accepts(n, "b"));
		assertFalse(test.accepts(n, "abb"));
		assertTrue(test.accepts(n, "c"));
	}

	private void testLanguageNFA11(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertFalse(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "abc"));
		assertTrue(test.accepts(n, "ab c"));
		assertTrue(test.accepts(n, "a b c"));
		assertFalse(test.accepts(n, "abb"));
		assertFalse(test.accepts(n, "c"));
	}

	private void testLanguageNFA12(NFA n) {
		assertFalse(test.accepts(n, ""));
		assertTrue(test.accepts(n, "a"));
		assertTrue(test.accepts(n, "aa"));
		assertTrue(test.accepts(n, "aaa"));
		assertTrue(test.accepts(n, "b"));
		assertTrue(test.accepts(n, "abba"));
		assertTrue(test.accepts(n, "c"));
	}
}
