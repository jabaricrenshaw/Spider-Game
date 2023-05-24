package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class ScoreView extends Container implements Observer {
	
	private ArrayList<Label> labels;
	
	//public ScoreView() { }
	
	public ScoreView(Observable observable) {
		//Adding ScoreView as observer to "observable" in method parameter.
		//"observable" will be GameWorld
		observable.addObserver(this);
		
		this.getAllStyles().setFgColor(ColorUtil.WHITE, true);
		this.setLayout(new FlowLayout(Component.CENTER));
		this.getAllStyles().setBorder(Border.createUnderlineBorder(5, ColorUtil.WHITE));
		
		GameWorld gw = (GameWorld)observable;
		
		//Adding labels
		if(labels == null) {
			
			labels = new ArrayList<Label>();
			
			labels.add( new Label("Time: " + gw.getClock(), "Time" ));
			labels.add( new Label("Lives Left: " + gw.getLivesRemaining(), "Lives" ));
			labels.add( new Label("Last Flag Reached: " + Ant.getAnt(gw).getLastFlagReached(), "Flag" ));
			labels.add( new Label("Food level: " + (int)Ant.getAnt(gw).getFoodLevel(), "Food" ));
			labels.add( new Label("Health level: " + Ant.getAnt(gw).getHealthLevel(), "Health" ));
			labels.add( new Label("Sound: " + gw.getSoundStatus(), "Sound" ));
			
			for(Label l : labels) {
				this.addComponent(l);
			}
		}
		
		this.revalidate();
	}
	
	@Override
	public void update(Observable observable, Object data) {
		
		GameWorld gw = (GameWorld)observable;
		
		if(labels != null) {
			labels.get(0).setText("Time: " + String.valueOf(gw.getClock()) );
			labels.get(1).setText("Lives Left: " + String.valueOf(gw.getLivesRemaining()) );
			labels.get(2).setText("Last Flag Reached: " + String.valueOf(Ant.getAnt(gw).getLastFlagReached()) );
			labels.get(3).setText("Food level: " + String.valueOf((int)Ant.getAnt(gw).getFoodLevel()) );
			labels.get(4).setText("Health level: " + String.valueOf(Ant.getAnt(gw).getHealthLevel()) );
			labels.get(5).setText("Sound: " + String.valueOf(gw.getSoundStatus()) );
		}
		
		this.revalidate();
		
	}

}
