package matrix;

import java.awt.Point;
import java.util.ArrayList;

import algorithm.State;

public class Matrix {
	public int[][] data;
	public int numOfRows;
	public int numOfCols;

	public Matrix(int[][] data) {
		this.data = data;
		this.numOfCols = data.length;
		this.numOfRows = data[0].length;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public int getNumOfCols() {
		return numOfCols;
	}

	public int[][] getData() {
		return data;
	}

	public boolean isInMatrix(int row, int col) {
		return row >= 0 && row < numOfRows && col >= 0 && col < numOfCols;
	}
}
