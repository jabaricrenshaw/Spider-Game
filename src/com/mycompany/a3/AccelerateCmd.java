package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCmd extends Command {
	private GameWorld gw;
	public AccelerateCmd(GameWorld gw, String command) {
		super(command);
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gw.accelerate();
	}
}
