package com.mycompany.a3;
import java.lang.Math;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable implements ISteerable {
	
	private static Ant ant;
	private int maximumSpeed;
	private double foodLevel;
	private double foodConsumptionRate; // indicates how much food the ant needs to consume each time the clock ticks.
	private int healthLevel;
	private int lastFlagReached;
	
	private Ant(GameWorld gw) {
		this.setSpeed(2);
		this.maximumSpeed = 5;
		this.healthLevel = 10;
		this.foodLevel = 10;
		this.foodConsumptionRate = 0.03;
		this.setHeading(0);
		this.setSize(10*5);
		this.setColor(ColorUtil.rgb(255, 200, 200));
		this.setGW(gw);
		this.setLocation(new Point(getGW().getWidth()/8, getGW().getHeight()/8));
	}
	
	public static Ant getAnt(GameWorld gw) {
		if(ant == null) {
			ant = new Ant(gw);
		}
		return ant;
	}
	
	public static void resetAnt(GameWorld gw) {
		ant = new Ant(gw);
	}
	
	public void move(int elapsedMilliSecs) {
		
		int headingModifier = 0;
		Point newLocation = null;
		
		if(this.foodLevel == 0 || this.healthLevel == 0) {
			setSpeed(0);
		}else {
			while( newLocation == null || newLocation.getX() > getGW().getWidth() - this.getSize() || newLocation.getX() < 0.0f ||
					newLocation.getY() > getGW().getHeight() - this.getSize() || newLocation.getY() < 0.0f) {
				int newHeading = (this.getHeading()+headingModifier+ new Random().nextInt(11) - 5);
				double theta = Math.toRadians(90 - (double)newHeading),
						deltaX = ( Math.cos(theta) * (double)this.getSpeed() * elapsedMilliSecs/20) * (double)this.healthLevel/10,
						deltaY = ( Math.sin(theta) * (double)this.getSpeed() * elapsedMilliSecs/20) * (double)this.healthLevel/10;
				newLocation = new Point((float)deltaX + this.getLocation().getX(), (float)deltaY + this.getLocation().getY());
				headingModifier += 45;
			}
			
			this.setHeading(this.getHeading()+headingModifier-45);
			this.setLocation(newLocation);
		}
		
		
	}

	public double getFoodLevel() { return this.foodLevel; }
	public int getHealthLevel() { return this.healthLevel; }
	public int getMaximumSpeed() { return this.maximumSpeed; }
	public int getLastFlagReached() { return this.lastFlagReached; }
	public double getFoodConsumptionRate() { return this.foodConsumptionRate; }

	public void setFoodLevel(double newFoodLevel) { this.foodLevel = newFoodLevel; }
	public void setHealthLevel(int newHealthLevel) { this.healthLevel = newHealthLevel; }
	public void setLastFlagReached(Flag flag) {
		if(this.lastFlagReached == 0 && flag.getSequenceNumber() == 1) {
			this.lastFlagReached = flag.getSequenceNumber();
		}else if(this.lastFlagReached == flag.getSequenceNumber() - 1) {
			this.lastFlagReached = flag.getSequenceNumber();
		}
	}
	
	@Override
	public String toString() {
		return super.toString() +
				" maxSpeed=" + this.maximumSpeed +
				" foodConsumptionRate=" + this.foodConsumptionRate +
				" foodLevel=" + (int)this.foodLevel +
				" healthLevel=" + this.healthLevel;
	}
	
	//Shape - Filled circle
	//Size - indicates the diameter of the circle -> shape
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int cirX = (int)(pCmpRelPrnt.getX() + this.getLocation().getX());
		int cirY = (int)(pCmpRelPrnt.getY() + this.getLocation().getY());
		
		g.setColor(this.getColor());
		g.fillArc( cirX, cirY, this.getSize(), this.getSize(), 0, 360 );
	}
}
