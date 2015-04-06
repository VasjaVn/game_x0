package com.game.xo.matrix;

import com.game.xo.except.FinishGameException;
import com.game.xo.except.WinnerGameException;


public class Matrix {
	
	public static final int  MATRIX_SIZE = 3;
	
	public static final char MATRIX_NULL_ELEMENT = '_';
	public static final char MATRIX_X_ELEMENT    = 'X';
	public static final char MATRIX_O_ELEMENT    = 'O';	
	
	public static Matrix getInstance() {
		if ( instance == null ) {
			instance = new Matrix();
		}
		return instance;
	}


	private Matrix() {
		this.table = new char[MATRIX_SIZE][MATRIX_SIZE];
		this.clear();
	}
	
	public void clear() {
		for ( int i = 0; i < MATRIX_SIZE; i++ ) {
			for ( int j = 0; j < MATRIX_SIZE; j++ ) {
				this.table[i][j] = MATRIX_NULL_ELEMENT;
			}
		}
		this.countFillCell = 0;
	}
	
	public char getCellMatrix(int x, int y) {
		return this.table[x][y];
	} 
	
	public void setCellMatrix(int x, int y, char fillElement) throws WinnerGameException, FinishGameException {
		this.table[x][y] = fillElement;
		this.countFillCell++;
		
		printMatrix();
		
		if ( isWinnerGame(fillElement) ) {
			throw new WinnerGameException();
		}
		
		if ( isFinishGame() ) {
			throw new FinishGameException();
		}
	}
	
	public int getCountEmptyCell() {
		return ( ( MATRIX_SIZE * MATRIX_SIZE ) - this.countFillCell );
	}
	
	private boolean isFinishGame() {
		return ( this.countFillCell == ( MATRIX_SIZE * MATRIX_SIZE ) );
	}
	
	private boolean isWinnerGame(char fillElement) {
		boolean result = false;
		
		if ( ( table[0][0] == fillElement ) && ( table[0][1] == fillElement ) && ( table[0][2] == fillElement ) ) {
			result = true;
		} else if ( ( table[1][0] == fillElement ) && ( table[1][1] == fillElement ) && ( table[1][2] == fillElement ) ) {
			result = true;
		} else if ( ( table[2][0] == fillElement ) && ( table[2][1] == fillElement ) && ( table[2][2] == fillElement ) ) {
			result = true;
		} else if ( ( table[0][0] == fillElement ) && ( table[1][0] == fillElement ) && ( table[2][0] == fillElement ) ) {
			result = true;
		} else if ( ( table[0][1] == fillElement ) && ( table[1][1] == fillElement ) && ( table[2][1] == fillElement ) ) {
			result = true;
		} else if ( ( table[0][2] == fillElement ) && ( table[1][2] == fillElement ) && ( table[2][2] == fillElement ) ) {
			result = true;
		} else if ( ( table[0][0] == fillElement ) && ( table[1][1] == fillElement ) && ( table[2][2] == fillElement ) ) {
			result = true;
		} else if ( ( table[0][2] == fillElement ) && ( table[1][1] == fillElement ) && ( table[2][0] == fillElement ) ) {
			result = true;
		}
		
		return result;
	}

	private void printMatrix() {
		for ( int i = 0; i < MATRIX_SIZE; i++) {
			for ( int j = 0; j < MATRIX_SIZE; j++ ) {
				System.err.print(this.table[i][j] + " ");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	private static Matrix instance;
	
	private char table[][];
	private volatile int countFillCell;

}
