package ab1.impl.Siarheyeu;

import ab1.Configuration;
import ab1.NFA;

import java.util.List;
import java.util.Set;


class NFAImpl implements NFA {

    private final int numStates;
    private final Integer initState;

    private final Set<Integer> acceptingStates;
    private final List<NFA.Transition> transitions;

    public NFAImpl(
            int numStates,
            Integer initState,
            Set<Integer>acceptingStates,
            List<NFA.Transition>transitions
    ) {
        if (numStates <= 0) {
            throw new IllegalArgumentException("Number of states must be positive");
        }
        if (initState == null || initState < 0 || initState >= numStates) {
            throw new IllegalArgumentException("Invalid initial state");
        }
        if (acceptingStates == null || transitions == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }

        this.numStates = numStates;
        this.initState = initState;
        this.acceptingStates = Set.copyOf(acceptingStates);
        this.transitions = List.copyOf(transitions);
    }


    @Override
    public int getNumStates() {
        return this.numStates;
    }

    @Override
    public Integer getInitialState() {
        return this.initState;
    }

    @Override
    public Set<Integer> getAcceptingStates() {
        return this.acceptingStates;
    }

    @Override
    public boolean isAcceptingState(Integer s) {
        return this.getAcceptingStates().contains(s);
    }

    @Override
    public List<NFA.Transition> getTransitions() {
        return this.transitions;
    }

    @Override
    public NFA union(NFA a) {
        return null;
    }

    @Override
    public NFA concat(NFA a) {
        return null;
    }

    @Override
    public NFA kleeneStar() {
        return null;
    }

    @Override
    public Set<Configuration> step(Configuration configuration) {
        return null;
    }
}