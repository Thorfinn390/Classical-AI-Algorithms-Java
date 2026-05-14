class State implements Comparable<State> {
    String location;  // 5 chars: A,B,C,D,T (e/w)
    int g;            // cost so far
    int h;            // heuristic
    String action;    // what caused this state
    State parent;     // link to previous state

    State(String loc, int g, int h, String act, State par) {
        this.location = loc;
        this.g = g;
        this.h = h;
        this.action = act;
        this.parent = par;
    }

    int f() { return g + h; }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.f(), other.f());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State)) return false;
        return ((State)o).location.equals(this.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
