public interface  Action {
    int getCostPerAction();
    Object apply(Object state);
}
