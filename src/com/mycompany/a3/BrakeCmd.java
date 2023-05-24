package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCmd extends Command{
	private GameWorld gw;
	public BrakeCmd(GameWorld gw, String command) {
		super(command);
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gw.brake();
	}
}
