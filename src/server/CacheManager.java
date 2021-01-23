package server;

public interface CacheManager<Problem, Solution> {
	public boolean isSolutionExists(Problem problem);

	public Solution getSolution(Problem problem);

	public void storeSolution(Problem problem, Solution solution);
}
