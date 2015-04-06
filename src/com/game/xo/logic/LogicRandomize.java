package com.game.xo.logic;

import java.util.Random;

import com.game.xo.except.FinishGameException;
import com.game.xo.except.WinnerGameException;
import com.game.xo.matrix.Matrix;

public class LogicRandomize extends Logic {

	public LogicRandomize(Matrix matrix) {
		super(matrix);
	}

	@Override
	public void algorithm(char fillElement) throws WinnerGameException, FinishGameException {
		
		int countEmptyCellOfMatrix = matrix.getCountEmptyCell();
		int randomNumber;
		
		if ( countEmptyCellOfMatrix != 0 ) {

			if ( countEmptyCellOfMatrix == 1 ) {
				randomNumber = 1;
			} else {
				randomNumber = new Random().nextInt(countEmptyCellOfMatrix);
			}
			
			int count = 0;

			exit: for( int i = 0; i < Matrix.MATRIX_SIZE; i++ ) {
				
				for( int j = 0; j < Matrix.MATRIX_SIZE; j++ ) {
					
					if ( matrix.getCellMatrix(i, j) == Matrix.MATRIX_NULL_ELEMENT ) {
						count++;

						if ( count == randomNumber ) {
							//System.err.println("[ " + (i + 1) + " ][ " + (j + 1) + " ]");
							matrix.setCellMatrix(i, j, fillElement);
							break exit;							
						}
					}
				}
			}

			
		} else {
			throw new FinishGameException();
		}
	}

}
