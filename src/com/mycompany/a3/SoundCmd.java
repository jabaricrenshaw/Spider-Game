package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCmd extends Command {
	private GameWorld gw;
	
	public SoundCmd(GameWorld gw, String command) {
		super(command);
		this.gw = gw;
	}

	public void actionPerformed(ActionEvent ev) {
		//Toggle Sound
		String value = gw.getSoundStatus();
		if(value == "ON") {
			gw.setSoundStatus(false);
		}else if(value == "OFF"){
			gw.setSoundStatus(true);
		}
	}

}
