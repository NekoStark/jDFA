package it.unifi.ing.jdfa.ops;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.operation.DFAOperation;

public class FormatAsDigraph implements DFAOperation {

	private StringBuilder result;
	
	@Override
	public void execute(DFA dfa) {
		result = new StringBuilder();
		
		result.append("digraph ")
				.append(dfa.hashCode())
				.append(" {\n");
		
		dfa.getStates().forEach(s ->
			result.append("\t")
				.append(s)
				.append(dfa.getAcceptingStates().contains(s)? " [shape=doublecircle];\n" : ";\n")
		);
		
		dfa.getTransitions().forEach(t ->
			result.append("\t")
				.append(t.getFrom())
				.append(" -> ")
				.append(t.getTo())
				.append(" [label=")
				.append(t.getSymbol())
				.append("];\n")
		);
		
		result.append("}");
	}

	@Override
	public String asString() {
		return result.toString();
	}

}
