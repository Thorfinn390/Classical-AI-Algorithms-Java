public class GenericSolver {
    private void solve(FormulatedProblem p, SearchAlgorithm alg){
        alg.solve(p);
    }

    public static void main(String [] args){
        GenericSolver s = new GenericSolver();
        SearchAlgorithm alg = new AStar(); //Change function by changing the object

        // int start[] = {1,0,3,8,2,4,7,6,5};
        // int goal[]  = {1,2,3,8,0,4,7,6,5};

        // int start[] = {1,2,3,8,6,4,0,7,5};
        // int goal[]  = {1,2,3,0,8,6,7,5,4};

        int start[] = {3,4,5,1,2,6,8,7,0};
        int goal[]  = {1,2,3,8,0,4,7,6,5};

        FormulatedProblem p = new EightPuzzleProblem(start, goal);
        s.solve(p, alg);
    }
}
