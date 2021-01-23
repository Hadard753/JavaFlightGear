package algorithm;

import java.awt.Point;
import java.util.PriorityQueue;

public abstract class CommonSearcher<Solution> implements Searcher {

	protected PriorityQueue<State<Point>> openList;

	public CommonSearcher() {
		this.openList = new PriorityQueue<>(((o1, o2) -> (int) (o1.getCost() - o2.getCost())));
	}

	@Override
	public abstract Solution search(Searchable s);

}
