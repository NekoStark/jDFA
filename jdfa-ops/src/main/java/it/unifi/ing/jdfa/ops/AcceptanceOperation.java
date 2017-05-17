package it.unifi.ing.jdfa.ops;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;
import it.unifi.ing.dfa.model.operation.DFAOperation;

public class AcceptanceOperation implements DFAOperation {

	private Map<String, Boolean> result;

	public AcceptanceOperation(String... strings) {
		result = Stream.of(strings).collect(Collectors.toMap(Function.identity(), s->Boolean.FALSE));
	}
	
	@Override
	public void execute(DFA dfa) {
		result.entrySet().forEach(e -> e.setValue(verify(dfa, e.getKey())));
	}
	
	public Map<String, Boolean> getResult() {
		return Collections.unmodifiableMap(result);
	}
	
	private boolean verify(DFA dfa, String string) {
		char[] tokenized = tokenize(dfa, string);
		State current = dfa.getStartState();

		for (Character c : tokenized) {
			current = dfa.getNextState(current, new Symbol(c));
		}

		return dfa.getAcceptingStates().contains(current);
	}
	
	private char[] tokenize(DFA dfa, String string) {
		string.chars()
			.mapToObj(c -> new Symbol((char)c))
			.forEach(c -> {
				if(!dfa.getAlphabet().contains(c)){
					throw new IllegalArgumentException("String contains invalid symbol " + c);
				}
			});
		
		return string.toCharArray();
	}
	
	@Override
	public void printResult() {
		System.out.println(result);
	}

}
