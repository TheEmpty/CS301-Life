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

import com.reliablerabbit.colors.Colors;
import com.reliablerabbit.colors.ColoredString;

public class Game {
	public static void main(String[] args) {
		boolean[][] start = {
				{false, true,  true,  true,  true,  true,  false, false, false, true,  false, false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false, false, true,  true,  true,  true,  true,  false, false, false, true,  true,  true,  true,  true,  false},
				{true,  false, false, false, false, false, true,  false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, true,  false, false, false, false, false, true,  false, false, true,  false, false, false, false, true },
				{true,  false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, true,  false, false, false, false, false, false, false, false, true,  false, false, false, false, true },
				{true,  false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, true,  true,  true,  true,  true,  false, false, false, true,  true,  true,  true,  true,  false},
				{true,  false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, true,  false, false, false, false, false},
				{true,  false, false, false, false, false, true,  false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, true,  false, false, false, false, false, true,  false, false, true,  false, false, false, false, false},
				{false, true,  true,  true,  true,  true,  false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false, true,  true,  true,  true,  true,  true,  true,  false, false, false, true,  true,  true,  true,  true,  false, false, false, true,  false, false, false, false, false},
		};
		Board b = new Board(start);

		int i = 0;
		while(true) {
			long startTime = System.nanoTime();
			i++;

			System.out.print(Colors.CLEAR_SCREEN);
			System.out.println((new ColoredString("Conway's Game of Life")).green());
			System.out.println();
			System.out.println(b);
			System.out.println();
			System.out.println((new ColoredString("Generation: " + i).cyan()));

			b.nextGeneration();
			if(i == 1) {
				try { System.in.read(); } catch (Exception ex) {}
			} else {
				long result = System.nanoTime() - startTime;
				long wait   = 500 - (result/1000000);
				if(wait > 0) {
					try { Thread.sleep(wait); } catch (Exception ex) {}
				}
			}
		}
	}
}
