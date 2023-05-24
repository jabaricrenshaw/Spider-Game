package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCmd extends Command {
	private Game g;
	public PauseCmd(Game g, String command) {
		super(command);
		this.g = g;
	}
	
	public void actionPerformed(ActionEvent ev) {
		g.togglePause();
	}
}
