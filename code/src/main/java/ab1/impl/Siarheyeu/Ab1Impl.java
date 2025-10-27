package ab1.impl.Siarheyeu;

import java.util.List;
import java.util.Set;

import ab1.Ab1;
import ab1.NFA;

public class Ab1Impl implements Ab1 {

	@Override
	public String[] matriculationNumbers() {
		// TODO: Auto-generated method stub


		//Tipp:
		return new String[] {"11930943"};
	}

	@Override
	public NFA fromPattern(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NFA fromTransitions(List<NFA.Transition> transitions,
			           Integer initialState,
				   Set<Integer> acceptingStates) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean accepts(NFA a, String word) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String pattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NFA.Transition> transitions1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NFA.Transition> transitions2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NFA.Transition> transitions3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NFA.Transition> transitions4() {
		// TODO Auto-generated method stub
		return null;
	}

}
