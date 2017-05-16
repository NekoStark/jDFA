package it.unifi.ing.jdfa.ops.minimizer;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.builder.DFABuilder;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling.TableFillingStrategy;

public class MinimizerTest {

	private DFA dfa, minimized;
	
	@Test
	public void test() {
		DFA result = new Minimizer().execute(dfa, new TableFillingStrategy());

		System.out.println(result);
		System.out.println(minimized);
		
		assertTrue(result.equals( minimized ));
	}
	
	@Before
	public void setUp() {
		dfa = new DFABuilder()
				.states("a, b, c, d, e, f")
				.alphabet('0', '1')
				.transition("a", '0', "b")
				.transition("a", '1', "c")
				.transition("b", '0', "a")
				.transition("b", '1', "d")
				.transition("c", '0', "e")
				.transition("c", '1', "f")
				.transition("d", '0', "e")
				.transition("d", '1', "f")
				.transition("e", '0', "e")
				.transition("e", '1', "f")
				.transition("f", '0', "f")
				.transition("f", '1', "f")
				.startState("a")
				.acceptingStates("c, d, e")
				.get();
		
		minimized = new DFABuilder()
				.states("ab, cde, f")
				.alphabet('0', '1')
				.transition("ab", '0', "ab")
				.transition("ab", '1', "cde")
				.transition("cde", '0', "cde")
				.transition("cde", '1', "f")
				.transition("f", '0', "f")
				.transition("f", '1', "f")
				.startState("ab")
				.acceptingStates("cde")
				.get();
	}
	
}
