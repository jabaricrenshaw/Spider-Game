package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

/*
 *  (4) some sort of appropriate background sound that loops continuously during animation. 
 *  (1) when the ant collides with a spider (such as a squeaking sound), 
 *  (3) when the ant collides with a flag (such as a cheering sound), 
 *  (2) when the ant collides with a food station (such as a crunching sound), 
 */
public class Game extends Form implements Runnable {

	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private boolean pause = false;
	private UITimer timer;

	private Command cAccelerate;
	private Command cBrake;
	private Command cRight;
	private Command cLeft;
	private Command cPosition;

	private Button bAccelerate;
	private Button bBrake;
	private Button bRight;
	private Button bLeft;
	private Button bPause;
	private Button bPosition;

	private ArrayList<Button> playBtnList = new ArrayList<Button>();

	public Game() {

		gw = new GameWorld();
		mv = new MapView(gw);
		sv = new ScoreView(gw);
		// Two GameWorld Observers (MapView, ScoreView)
		gw.addObserver(mv);
		gw.addObserver(sv);

		this.setLayout(new BorderLayout());
		this.getStyle().setBgColor(ColorUtil.rgb(117, 121, 130));

		Container westCont = new Container(BoxLayout.y());
		Container eastCont = new Container(BoxLayout.y());
		Container southCont = new Container(new FlowLayout(Component.CENTER));
		westCont.getAllStyles().setBorder(Border.createUnderlineBorder(5, ColorUtil.WHITE));
		eastCont.getAllStyles().setBorder(Border.createUnderlineBorder(5, ColorUtil.WHITE));

		this.add(BorderLayout.WEST, westCont);
		this.add(BorderLayout.EAST, eastCont);
		this.add(BorderLayout.SOUTH, southCont);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);

		Toolbar tb = new Toolbar();
		this.setToolbar(tb);
		tb.setTitle("WalkIt Game");

		Command cSound = new SoundCmd(gw, "Toggle Sound");
		Command cAbout = new AboutCmd("About");
		Command cHelp = new HelpCmd("Help");
		Command cExit = new ExitCmd(gw, "Exit");
		Command cPause = new PauseCmd(this, "Pause");
		cPosition = new PositionCmd(gw, "Position");
		cAccelerate = new AccelerateCmd(gw, "Accelerate");
		cBrake = new BrakeCmd(gw, "Brake");
		cLeft = new LeftCmd(gw, "Turn Left");
		cRight = new RightCmd(gw, "Turn Right");

		CheckBox cbSound = new CheckBox();
		cbSound.setCommand(cSound);

		ArrayList<Button> btnList = new ArrayList<Button>();
		bAccelerate = new Button(cAccelerate);
		btnList.add(bAccelerate);
		playBtnList.add(bAccelerate);
		bBrake = new Button(cBrake);
		btnList.add(bBrake);
		playBtnList.add(bBrake);
		bLeft = new Button(cLeft);
		btnList.add(bLeft);
		playBtnList.add(bLeft);
		bRight = new Button(cRight);
		btnList.add(bRight);
		playBtnList.add(bRight);
		bPause = new Button(cPause);
		btnList.add(bPause);
		bPosition = new Button(cPosition);
		btnList.add(bPosition);

		for (Button btn : btnList) {
			btn.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLUE));
			btn.getAllStyles().setBgColor(ColorUtil.GREEN);
			btn.getAllStyles().setBgTransparency(255);
			btn.getAllStyles().setFgColor(ColorUtil.BLACK);
			btn.setSize(new Dimension(50, 50));
			btn.setPreferredSize(new Dimension(290, 100));
		}
		bPause.getAllStyles().setBgColor(ColorUtil.CYAN);
		bPause.getAllStyles().setBgTransparency(255);

		this.addKeyListener('a', cAccelerate);
		this.addKeyListener('b', cBrake);
		this.addKeyListener('r', cRight);
		this.addKeyListener('l', cLeft);
		this.addKeyListener('x', cExit);

		tb.addCommandToSideMenu(cAccelerate);
		tb.addComponentToSideMenu(cbSound);
		tb.addCommandToSideMenu(cAbout);
		tb.addCommandToSideMenu(cExit);

		tb.addCommandToRightBar(cHelp);

		westCont.addComponent(bAccelerate);
		westCont.addComponent(bLeft);
		eastCont.addComponent(bBrake);
		eastCont.addComponent(bRight);
		southCont.addComponent(bPosition);
		southCont.addComponent(bPause);
		
		this.show();
		gw.setHeight(mv.getHeight());
		gw.setWidth(mv.getWidth());
		// GameWorld initialization
		gw.init();
		gw.createSounds();
		this.revalidate();
		timer = new UITimer(this);
		timer.schedule(20, true, this);
	}

	@Override
	public void run() {
		gw.clockTick(20);

		if (gw.getSoundStatus() == "ON") {
			gw.getBGSound().play();
		}
	}

	public void togglePause() {
		pause = !pause;
		gw.setPaused();
		if (pause == true) {
			timer.cancel();
			bPause.setText("Play");
			gw.getBGSound().pause();

			cAccelerate.setEnabled(false);
			cBrake.setEnabled(false);
			cRight.setEnabled(false);
			cLeft.setEnabled(false);

			for (Button btn : playBtnList) {
				btn.setEnabled(false);
				btn.getAllStyles().setBgColor(ColorUtil.LTGRAY);
				btn.getAllStyles().setBgTransparency(255);
			}
			bPause.getAllStyles().setBgColor(ColorUtil.CYAN);
			bPause.getAllStyles().setBgTransparency(255);
			bPosition.getAllStyles().setBgColor(ColorUtil.GREEN);
			bPosition.getAllStyles().setBgTransparency(255);
		} else if (pause == false) {
			timer.schedule(20, true, this);
			bPause.setText("Pause");
			if (gw.getSoundStatus() == "ON") {
				gw.getBGSound().play();
			}

			cAccelerate.setEnabled(true);
			cBrake.setEnabled(true);
			cRight.setEnabled(true);
			cLeft.setEnabled(true);

			for (Button btn : playBtnList) {
				btn.setEnabled(true);
				btn.getAllStyles().setBgColor(ColorUtil.GREEN);
				btn.getAllStyles().setBgTransparency(255);
			}
			bPause.getAllStyles().setBgColor(ColorUtil.CYAN);
			bPause.getAllStyles().setBgTransparency(255);
			bPosition.getAllStyles().setBgColor(ColorUtil.LTGRAY);
			bPosition.getAllStyles().setBgTransparency(255);
		}
	}

}
