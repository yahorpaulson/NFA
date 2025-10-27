package ab1.impl.Siarheyeu;

import ab1.Configuration;
import ab1.NFA;

import java.util.HashSet;
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
        if(configuration == null){
            throw new NullPointerException("Configuration undefined");
        }
        int currentState = configuration.getState();
        if(currentState < 0 || currentState >= this.numStates){
            throw new IllegalArgumentException("Illegal state");
        }
        String word = configuration.getWord();

        Set<Integer> S0 = epsilon(Set.of(currentState));

        if(word.isEmpty()){
            Set<Configuration> res = new HashSet<>();
            for (Integer p: S0){
                res.add(new Configuration(p,word));
            }
            return res;


        } else {
            char firstLetter = word.charAt(0);
            String restWord = word.substring(1);

        }



        return null;
    }

    private Set<Integer> epsilon(Set<Integer> states){
        Set<Integer> res = new HashSet<>(states);

        for (NFA.Transition transition : transitions){
            if(transition.symbol==null && res.contains(transition.currentState) && !res.contains(transition.nextState)){
                res.add(transition.nextState);
            }


        }
        return res;
    }
    private Set<Integer> move(Set<Integer>states, char symbol){
        return null;
    }
}