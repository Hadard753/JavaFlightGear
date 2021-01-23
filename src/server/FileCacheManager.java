package server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution> {
	Map<Integer, Solution> cache = new HashMap<>();

	public FileCacheManager() {
	}

	@Override
	public boolean isSolutionExists(Problem problem) {
		return cache.containsKey(problem.hashCode());
	}

	@Override

	public Solution getSolution(Problem problem) {
		if (isSolutionExists(problem)) {
			return cache.get(problem.hashCode());
		}
		try {
			return (Solution) new String(Files.readAllBytes(Paths.get(problem.hashCode() + ".txt")), "utf8");
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public void storeSolution(Problem problem, Solution solution) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(problem.hashCode() + ".txt"));
			pw.println(solution);
			pw.flush();
			pw.close();
			cache.put(problem.hashCode(), solution);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
