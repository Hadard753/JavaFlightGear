package algorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS<Solution> extends CommonSearcher<Solution> {

	@Override
	public Solution search(Searchable s) {
		openList.add(s.getInitialState());
		HashSet<State<Point>> closedSet = new HashSet<>();

		while (openList.size() > 0) {
			State<Point> state = (State<Point>) openList.poll();
			closedSet.add(state);
			if (state.equals(s.getGoalState())) {
				return (Solution) s.backtrace(state);
			}

			ArrayList<State<Point>> succesors = s.getAllNextPossibleStates(state);
			for (State<Point> successor : succesors) {
				if (!closedSet.contains(successor) && !openList.contains(successor)) {
					successor.setParentState(state);
					openList.add(successor);
				} else if (openList.contains(successor)) {
					updateTobetterPath(successor);
				}
			}
		}
		return null;
	}

	private void updateTobetterPath(State<Point> s) {
		LinkedList<State<Point>> q = new LinkedList<>();
		State<Point> temp;
		while (!openList.isEmpty()) {
			temp = (State<Point>) openList.poll();
			if (temp.equals(s) && s.getCost() < temp.getCost()) {
				temp.setCost(s.getCost());
				temp.setParentState(s.getParentState());
			}
			q.add(temp);
		}
		openList.addAll(q);
	}

}
