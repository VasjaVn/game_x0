package com.game.xo.player;

import com.game.xo.except.FinishGameException;
import com.game.xo.except.WinnerGameException;
import com.game.xo.logic.Logic;
import com.game.xo.matrix.Matrix;

public class PlayerX extends Player {
	
	public PlayerX(String name, Logic logic) {
		super(name, logic);
	}

	@Override
	public boolean makeMove() {

		//System.err.print("Player " + name + " is put \"" + Matrix.MATRIX_X_ELEMENT + "\" on position: ");

		try {
			logic.algorithm(Matrix.MATRIX_X_ELEMENT);
		} catch (WinnerGameException e) {
			System.err.println("Player " + name + "(" + Matrix.MATRIX_X_ELEMENT + ")" + " is winner!!!");
			return true;
		} catch (FinishGameException e) {
			return true;
		}
		return false;
	}

}
