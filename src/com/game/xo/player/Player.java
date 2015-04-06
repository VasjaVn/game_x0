package com.game.xo.player;

import com.game.xo.logic.Logic;

public abstract class Player {
	
	public Player(String name, Logic logic) {
		this.name  = name;
		this.logic = logic;
	}
	
	public abstract boolean makeMove();
	
	public String getName() {
		return name;
	}

	protected String name;
	protected Logic  logic;

}
