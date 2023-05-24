package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;

public class HelpCmd extends Command {
	
	public HelpCmd(String command) {
		super(command);
	}
	
	public void actionPerformed(ActionEvent ev) {
		Dialog d = new Dialog("Help");
		d.setLayout(BoxLayout.y());
		
		TextArea ta = new TextArea("This game can be played using the titled buttons on the main screen or using the available keybindings.\n\n"
				+ "Available Keybindings for commands:\n"
				+ "a -> Accelerate (Increases the speed of the Ant until maximum speed.)\n"
				+ "b -> Brake (Reduces the speed of the Ant until minimum speed.)\n"
				+ "l -> Turn Left\n"
				+ "r -> Turn Right\n");
		
		d.add(ta);
		d.add(new Button(new Command("OK")));
		
		d.showDialog();
	}

}
