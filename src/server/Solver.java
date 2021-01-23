package server;

public interface Solver<Problem, Solution> {
	Solution solve(Problem p);
}