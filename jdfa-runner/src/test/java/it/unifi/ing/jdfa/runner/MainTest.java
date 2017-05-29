package it.unifi.ing.jdfa.runner;

import org.junit.Test;

import it.unifi.ing.jdfa.runner.exception.DFARunnerException;

public class MainTest {

	@Test(expected=DFARunnerException.class)
	public void testMainNoParams() {
		String[] params = {};
		Main.main(params);
	}
	
}
