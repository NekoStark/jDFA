package it.unifi.ing.jdfa.ops;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;

public class AcceptanceOperation {

	private DFA dfa;

	public AcceptanceOperation(DFA dfa) {
		this.dfa = dfa;
	}
	
	public boolean verify(String string) {
		char[] tokenized = tokenize(string);
		State current = dfa.getStartState();

		for (Character c : tokenized) {
			current = dfa.getNextState(current, new Symbol(c));
			//TODO: E' possibile che non esista una transizione?
		}

		return dfa.getAcceptingStates().contains(current);
	}
	
	public char[] tokenize(String string) {
		string.chars()
			.mapToObj(c -> new Symbol((char)c))
			.forEach(c -> {
				if(!dfa.getAlphabet().contains(c)){
					throw new IllegalArgumentException("String contains invalid symbol " + c);
				}
			});
		
		return string.toCharArray();
	}
	
}
