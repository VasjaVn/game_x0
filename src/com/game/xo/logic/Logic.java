package com.game.xo.logic;

import com.game.xo.except.FinishGameException;
import com.game.xo.except.WinnerGameException;
import com.game.xo.matrix.Matrix;

public abstract class Logic {
	
	Logic(Matrix matrix) {
		this.matrix = matrix;
	}
	
	public abstract void algorithm(char fillElement) throws WinnerGameException, FinishGameException;
	
	protected Matrix matrix;
}
