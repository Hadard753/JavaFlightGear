package matrix;

import java.awt.Point;
import java.util.ArrayList;

import algorithm.Searchable;
import algorithm.State;

public class MatrixSearch implements Searchable<Point> {

	Matrix matrix;
	public State<Point> entry;
	public State<Point> exit;

	public MatrixSearch(Matrix matrix, State<Point> entry, State<Point> exit) {
		this.matrix = matrix;
		this.entry = entry;
		this.exit = exit;
	}

	@Override
	public State<Point> getInitialState() {
		return entry;
	}

	@Override
	public ArrayList<State<Point>> getAllNextPossibleStates(State<Point> s) {
		ArrayList<State<Point>> successors = new ArrayList<>();
		
		Point right = new Point((int)s.getState().getX() + 1, (int) s.getState().getY());
		Point left = new Point((int)s.getState().getX() - 1, (int) s.getState().getY());
		Point up = new Point((int)s.getState().getX(), (int) s.getState().getY() + 1);
		Point down = new Point((int)s.getState().getX(), (int) s.getState().getY() - 1);
		
		addIfPossible(right, successors, s);
		addIfPossible(left, successors, s);
		addIfPossible(up, successors, s);
		addIfPossible(down, successors, s);
		
		return successors;
	}

	private void addIfPossible(Point p, ArrayList<State<Point>> successors, State<Point> source) {
		if(matrix.isInMatrix(p.x, p.y)) {
			State<Point> state = new State<>(p);
			state.setParentState(source);
			state.setCost(source.getCost() + matrix.getData()[p.x][p.y]);
			successors.add(state);
		}
	}
	
	@Override
	public State<Point> getGoalState() {
		return exit;
	}

	@Override
	public String backtrace(State goal) {
		State<Point> current = goal;
		State<Point> parentState;
		String directions = "";
		while (!current.equals(this.getInitialState())) {
			parentState = current.getParentState();
			if (current.getState().getY() == parentState.getState().getY() + 1)
				directions = "Right," + directions;
			if (current.getState().getY() == parentState.getState().getY() - 1)
				directions = "Left," + directions;
			if (current.getState().getX() == parentState.getState().getX() - 1)
				directions = "Up," + directions;
			if (current.getState().getX() == parentState.getState().getX() + 1)
				directions = "Down," + directions;

			current = current.getParentState();
		}
		return directions.substring(0, directions.length() - 1);
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < matrix.getNumOfRows(); i++) {
			for (int j = 0; j < matrix.getNumOfCols(); j++) {
				sb.append(matrix.getData()[i][j] + ",");
			}
		}
		sb.append(entry.getState().getX() + "," + entry.getState().getY() + ",");
		sb.append(exit.getState().getX() + "," + exit.getState().getY());
		return sb.toString();
	}

}
