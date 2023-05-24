package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;

public class AboutCmd extends Command {
	
	public AboutCmd(String command) {
		super(command);
	}
	
	public void actionPerformed(ActionEvent ev) {
		Dialog.show("About", "Author: Jabari Crenshaw\nCourse: CSC 133, Fall '22\nVer. 1.3", "Ok", null);
	}

}
