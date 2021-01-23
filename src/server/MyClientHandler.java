package server;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithm.BFS;
import algorithm.Searchable;
import algorithm.State;
import matrix.Matrix;
import matrix.MatrixSearch;

public class MyClientHandler implements ClientHandler {

	Solver<Searchable, String> solver;
	CacheManager<String, String> cm;

	public MyClientHandler() {
		solver = new SolverSearcher(new BFS<String>());
		cm = new FileCacheManager<>();
	}


	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {

		BufferedReader clientInput = new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter serverOutput = new PrintWriter(outToClient);

		try {
			// build string matrix from user input
			String line;
			String[] userLineData;
			int rows = 0, cols = 0;
			ArrayList<String[]> stringMatrix = new ArrayList<>();
			while (!(line = clientInput.readLine()).equals("end")) {
				userLineData = line.split(",");
				stringMatrix.add(userLineData);
				rows++;
			}
			
			// convert string matrix to integer matrix
			cols = stringMatrix.get(0).length;
			int[][] matrix = new int[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					matrix[i][j] = Integer.parseInt(stringMatrix.get(i)[j]);
				}
			}
			
			// read entry point and exit point from user input 
			userLineData = clientInput.readLine().split(",");
			Point entryPoint = new Point(Integer.parseInt(userLineData[0]), Integer.parseInt(userLineData[1]));
			userLineData = clientInput.readLine().split(",");
			Point exitPoint = new Point(Integer.parseInt(userLineData[0]), Integer.parseInt(userLineData[1]));

			// solve the user problem and return the solution
			Searchable problem = new MatrixSearch(
					new Matrix(matrix), new State<>(entryPoint), new State<>(exitPoint));
			String problemString = problem.getString();
			String solution;
			if (cm.isSolutionExists(problemString)) {
				solution = cm.getSolution(problemString);
			} else {
				solution = solver.solve(problem);
				cm.storeSolution(problemString, solution);
			}
			serverOutput.println(solution);
			serverOutput.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
