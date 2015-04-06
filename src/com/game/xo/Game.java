package com.game.xo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Semaphore;

import com.game.xo.matrix.Matrix;
import com.game.xo.logic.Logic;
import com.game.xo.logic.LogicHorizontal;
import com.game.xo.logic.LogicRandomize;
import com.game.xo.player.Player;
import com.game.xo.player.PlayerO;
import com.game.xo.player.PlayerX;

final class Sync {
	public Semaphore semFirst;
	public Semaphore semSecond;
}

class ActionPlayerBase extends Thread {
	
	public ActionPlayerBase( Sync sync, Player player ) {
		this.sync   = sync;
		this.player = player;
		start();
	} 
	
	protected Sync   sync;
	protected Player player;
	
	public static volatile boolean stop = false;
}

class ActionFirstPlayer extends ActionPlayerBase {
	
	public ActionFirstPlayer( Sync sync, Player player) {
		super( sync, player );
	}
	
	
	public void run() {

		while ( true ) {
			try {
				sync.semFirst.acquire();

				if ( ActionPlayerBase.stop ) {
					break;
				}
				
				if ( player.makeMove() ) {
					ActionPlayerBase.stop = true;
					break;
				}
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sync.semSecond.release();
			}
			
		}
	}
	
}

class ActionSecondPlayer extends ActionPlayerBase {
	
	public ActionSecondPlayer( Sync sync, Player player) {
		super( sync, player );
	}
	
	@Override
	public void run() {
		while ( true ) {
			
			try {
				sync.semSecond.acquire();
				
				if ( ActionPlayerBase.stop ) {
					break;
				}

				if ( player.makeMove() ) {
					ActionPlayerBase.stop = true;
					break;
				}
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sync.semFirst.release();
			}			
		}
	}
	
}

public class Game {
	
	private static final String PROPERTIES_FILE = "game.properties";
	private static final String KEY_PLAYER_1_NAME = "player.1.name";
	private static final String KEY_PLAYER_2_NAME = "player.2.name";
	private static final String KEY_PLAYER_1_LOGIC = "player.1.logic";
	private static final String KEY_PLAYER_2_LOGIC = "player.2.logic";
	
	private static final String LOGIC_NAME_HORIZONTAL = "horizontal";
	private static final String LOGIC_NAME_RANDOMIZE  = "randomize";
	private static final String DEFAULT_PLAYER_1_NAME = "XXX";
	private static final String DEFAULT_PLAYER_2_NAME = "OOO";
	private static final String DEFAULT_LOGIC_NAME    = LOGIC_NAME_HORIZONTAL;


	private static Logic loadLogic(final String logicName) {
		
		Logic logic = null;
	
		Matrix matrix = Matrix.getInstance();	

		switch ( logicName ) {
		
			case LOGIC_NAME_HORIZONTAL:
				logic = new LogicHorizontal(matrix);
				break;
				
			case LOGIC_NAME_RANDOMIZE:
				logic = new LogicRandomize(matrix);
				break;
		}
		
		return logic;
	}
	
	public static void main(String[] args) {
		
		//Matrix matrix = Matrix.getInstance();
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(PROPERTIES_FILE));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String namePlayer1 = properties.getProperty(KEY_PLAYER_1_NAME, DEFAULT_PLAYER_1_NAME);
		String namePlayer2 = properties.getProperty(KEY_PLAYER_2_NAME, DEFAULT_PLAYER_2_NAME);
		
		String logicName1 = properties.getProperty(KEY_PLAYER_1_LOGIC, DEFAULT_LOGIC_NAME);
		Logic logicPlayer1 = loadLogic(logicName1);
		
		String logicName2 = properties.getProperty(KEY_PLAYER_2_LOGIC, DEFAULT_LOGIC_NAME);
		Logic logicPlayer2 = loadLogic(logicName2);
		
		Player player1 = new PlayerX(namePlayer1, logicPlayer1);
		Player player2 = new PlayerO(namePlayer2, logicPlayer2);
		
		Sync sync = new Sync();
		sync.semFirst = new Semaphore(0);
		sync.semSecond = new Semaphore(1);
		
		Thread actionFirstPlayer  = new ActionFirstPlayer( sync, player1 );
		Thread actionSecondPlayer = new ActionSecondPlayer( sync, player2 );
		
		//sync.semFirst.release();
		
		try {
			actionFirstPlayer.join();
			actionSecondPlayer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Game finished!!!");
	}

}
