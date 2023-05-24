package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCmd extends Command {
	private GameWorld gw;
	
	public PositionCmd(GameWorld gw, String command) {
		super(command);
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		gw.setMove();
		System.out.println("Move toggled! (You may need to click this again if pressed twice.)");
	}
}
