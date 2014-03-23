/*
 Copyright (c) 2014 Mohammad El-Abid

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
*/

package edu.bridgeport.melabid.cs301.life;

import com.reliablerabbit.colors.ColoredString;

public class Board {
	private boolean[][] board;

	public Board(boolean[][] board) {
		setBoard(board);
	}

	public void setBoard(boolean[][] board) {
		this.board = board;
	}

	public void nextGeneration() {
		boolean[][] nextBoard = new boolean[board.length][board[0].length];

		for(int row = 0; row < board.length; row++) {
			for(int column = 0; column < board[row].length; column++) {
				int neighbors = 0;

				if(row - 1 >= 0) {
					// upward
					if(board[row - 1][column]) neighbors++;
					if(column - 1 >= 0 && board[row - 1][column - 1])neighbors++;
					if(column + 1 < board[row].length && board[row - 1][column + 1]) neighbors++;
				}

				if(row + 1 < board.length) {
					// downward
					if(board[row + 1][column]) neighbors++;
					if(column - 1 >= 0 && board[row + 1][column - 1]) neighbors++;
					if(column + 1 < board[row].length && board[row + 1][column + 1]) neighbors++;
				}

				// left
				if(column - 1 >= 0 && board[row][column - 1]) neighbors++;
				// right
				if(column + 1 < board[row].length && board[row][column + 1]) neighbors++;

				// life and death logic
				if(board[row][column]) { // if alive
					if(neighbors == 2 || neighbors == 3) { // if still alive
						nextBoard[row][column] = true;
					}
				} else {
					if(neighbors == 3) nextBoard[row][column] = true;
				}
			}
		}
		board = nextBoard;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();

		for(int row = 0; row < board.length; row++) {
			for(int column = 0; column < board[row].length; column++) {
				if(board[row][column]) {
					buff.append((new ColoredString("# ")).green());
				} else {
					buff.append((new ColoredString(". ")).red());
				}
			}
			buff.append("\n");
		}

		return buff.toString();
	}
}
