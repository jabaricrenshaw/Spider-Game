package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class ExitCmd extends Command {
	private GameWorld gw;
	
	public ExitCmd(GameWorld gw, String command) {
		super(command);
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		if(Dialog.show("Exit", "Are you sure you'd like to quit?", "OK", "Cancel")) {
			gw.quit();
		}
	}

}
