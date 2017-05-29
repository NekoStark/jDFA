package it.unifi.ing.jdfa.runner;

import java.util.logging.Logger;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.operation.DFAOperation;
import it.unifi.ing.jdfa.runner.exception.DFARunnerException;

public class Main {

	private Main() {
	}

	public static void main(String[] args) {
		if(args.length <= 0) {
			throw new DFARunnerException("parameters needed for execution");
		}

		DFAOperation op = Runner.getOperation(args[0], args);
		DFA dfa = Runner.buildDFA(args);
		dfa.execute(op);

		System.out.println(op.asString());
		System.exit(0);
	}
}
