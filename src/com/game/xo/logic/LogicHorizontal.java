package com.game.xo.logic;

import com.game.xo.except.FinishGameException;
import com.game.xo.except.WinnerGameException;
import com.game.xo.matrix.Matrix;

public class LogicHorizontal extends Logic {

	public LogicHorizontal(Matrix matrix) {
		super(matrix);
	}

	@Override
	public void algorithm(char fillElement) throws WinnerGameException, FinishGameException {
		
		exit: for( int i = 0; i < Matrix.MATRIX_SIZE; i++ ) {
			
			for( int j = 0; j < Matrix.MATRIX_SIZE; j++ ) {
				
				if ( matrix.getCellMatrix(i, j) == Matrix.MATRIX_NULL_ELEMENT ) {	
					//System.err.println("[ " + (i + 1) + " ][ " + (j + 1) + " ]");
					matrix.setCellMatrix(i, j, fillElement);
					break exit;
				}
			}
		}
		
	}

}
