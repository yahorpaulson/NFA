package ab1.impl.Siarheyeu;

import ab1.Configuration;
import ab1.NFA;

import java.util.*;


class NFAImpl implements NFA {

    private final int numStates;
    private final Integer initState;

    private final Set<Integer> acceptingStates;
    private final List<NFA.Transition> transitions;

    public NFAImpl(
            int numStates,
            Integer initState,
            Set<Integer> acceptingStates,
            List<NFA.Transition> transitions
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
        for (Integer state : acceptingStates) {
            if (state < 0 || state >= numStates) {
                throw new IllegalArgumentException("Invalid accepting state: " + state);
            }
        }
        for (NFA.Transition transition : transitions) {
            if (transition.currentState < 0 || transition.currentState >= numStates ||
                    transition.nextState < 0 || transition.nextState >= numStates) {
                throw new IllegalArgumentException("Invalid transition states: " + transition.currentState + " -> " + transition.nextState);
            }
        }



        this.numStates = numStates;
        this.initState = initState;
        this.acceptingStates = Set.copyOf(acceptingStates); //fully immutable
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
        if (s == null || s < 0 || s >= numStates)
            throw new IllegalArgumentException("Illegal state error: " + s);
        return this.getAcceptingStates().contains(s);
    }

    @Override
    public List<NFA.Transition> getTransitions() {
        return this.transitions;
    }

    @Override
    public NFA union(NFA a) {
        if(a == null) {
            throw new NullPointerException("Union error: NFA 'a' is null");
        }


        //define offsets for states of both NFAs in the new NFA
        final int offsetA = 1;
        final int offsetB = 1 + this.getNumStates();

        final int newInit = 0; //new initial state
        final int newNumStates = 1 + this.getNumStates() + a.getNumStates(); // number of total states

        List <NFA.Transition> newTransitions = new ArrayList<>();

        newTransitions.add(new NFA.Transition(newInit, null, this.getInitialState() + offsetA)); //epsilon transition to initial state of first NFA
        newTransitions.add(new NFA.Transition(newInit, null, a.getInitialState() + offsetB)); //epsilon transition to initial state of second NFA


        for(NFA.Transition t : this.getTransitions()) {
            newTransitions.add(new NFA.Transition(t.currentState + offsetA, t.symbol, t.nextState + offsetA));
        } //set new transitions taking into account the offset for 3 -> 5 will be 4 -> 6

        for(NFA.Transition t : a.getTransitions()) {
            newTransitions.add(new NFA.Transition(t.currentState + offsetB, t.symbol, t.nextState + offsetB));
        } //same for second NFA


        //define new accepting states taking into account offsets, same as for transitions
        Set<Integer> newAcceptingStates = new HashSet<>();
        for(Integer s : this.getAcceptingStates()) {
            newAcceptingStates.add(s + offsetA);
        }
        for(Integer s : a.getAcceptingStates()) {
            newAcceptingStates.add(s + offsetB);
        }

        return new NFAImpl(
                newNumStates,
                newInit,
                newAcceptingStates,
                newTransitions
        );
    }

    @Override
    public NFA concat(NFA a) {
        if (a == null) {
            throw new NullPointerException("Concat error: NFA 'a' is null");
        }
        int offset = this.getNumStates();
        int newNumStates = this.getNumStates() + a.getNumStates();

        List <NFA.Transition> newTransitions = new ArrayList<>(); //new list of transitions for new NFA

        for(NFA.Transition t : this.getTransitions()) {
            newTransitions.add(new NFA.Transition(t.currentState, t.symbol, t.nextState));
        } //add transitions of first NFA as is
        for(NFA.Transition t : a.getTransitions()) {
            newTransitions.add(new NFA.Transition(t.currentState + offset, t.symbol, t.nextState + offset));
        } //add transitions of second NFA with offset


        for(Integer s : this.getAcceptingStates()) {
            newTransitions.add(new NFA.Transition(s, null, a.getInitialState() + offset));
        } //add epsilon transitions from accepting states of first NFA to initial state of second NFA

        Set<Integer> newAcceptingStates = new HashSet<>();
        for(Integer s : this.acceptingStates) {
            newAcceptingStates.add(s);
        }
        for (Integer s : a.getAcceptingStates()) {
            newAcceptingStates.add(s + offset);
        } //define new accepting states taking into account offsets






        return new NFAImpl(
                newNumStates,
                this.getInitialState(),
                newAcceptingStates,
                newTransitions

        );
    }

    @Override
    public NFA kleeneStar() {
        if(this.numStates == 0) {
            throw new IllegalStateException("Kleene star error: NFA has no states");
        }


        Integer initState = 0;
        int newNumStates = this.numStates + 1; //new initial state added

        List<NFA.Transition> newTransitions = new ArrayList<>();
        newTransitions.add(new NFA.Transition(initState, null, this.getInitialState() + 1)); //epsilon transition to old initial state



        for(NFA.Transition t : this.getTransitions()) {
            newTransitions.add(new NFA.Transition(t.currentState + 1, t.symbol, t.nextState + 1));
        } //add old transitions with offset

        for(Integer s : this.getAcceptingStates()) {
            newTransitions.add(new NFA.Transition(s + 1, null, initState));
        } //add epsilon transitions from old accepting states to old initial state

        Set<Integer> newAcceptingStates = new HashSet<>();
        newAcceptingStates.add(initState); //new initial state is also accepting
        for(Integer s : this.getAcceptingStates()) {
            newAcceptingStates.add(s + 1);
        } //add old accepting states with offset
        return new NFAImpl(
                newNumStates,
                initState,
                newAcceptingStates,
                newTransitions
        );

    }

    @Override
    public Set<Configuration> step(Configuration configuration) {
        if (configuration == null) {
            throw new NullPointerException("Configuration undefined error");
        }
        int currentState = configuration.getState();
        if (currentState < 0 || currentState >= this.numStates) {
            throw new IllegalArgumentException("Illegal state error");
        }
        String word = configuration.getWord();

        Set<Integer> S0 = epsilon(Set.of(currentState)); // all possible states via epsilon transitions

        if (word.isEmpty()) {
            Set<Configuration> resConfig = new HashSet<>();
            for (Integer state : S0) {
                resConfig.add(new Configuration(state, "")); //if word is empty -> add epsilon reachable states
            }
            return resConfig;
        }
        char currentSymbol = word.charAt(0);
        String restWord = word.substring(1);

        


        Set<Integer> statesAfterMove = move(S0, currentSymbol); // states reachable after consuming current symbol

        Set<Configuration> resConfig = new HashSet<>();
        for (Integer s : statesAfterMove) {
            Set<Integer> S1 = epsilon(Set.of(s));
            for (Integer state : S1) {
                resConfig.add(new Configuration(state, restWord)); // add new configurations
            }
        }



        return resConfig;
    }

    private Set<Integer> epsilon(Set<Integer> states) {
        Set<Integer> res = new HashSet<>(states);   //copy of states

        Deque<Integer> stack = new java.util.ArrayDeque<>(states);
        while (!stack.isEmpty()) {
            int stackState = stack.pop();
            for (NFA.Transition t : transitions) {
                if (t.symbol == null && t.currentState == stackState) {
                    if (res.add(t.nextState)) {
                        stack.push(t.nextState);
                    }
                }
            }
        }
        return res;
    }

    private Set<Integer> move(Set<Integer> states, char symbol) {
        Set<Integer> newStates = new HashSet<>();
        for (Integer s : states) {
            for (NFA.Transition transition : transitions) {
                if (transition.currentState.equals(s) && transition.symbol != null && transition.symbol == symbol) {
                    newStates.add(transition.nextState);
                }

            }
        }
        return newStates;
    }
}