package com.mycompany.a3;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Spider extends Movable {
	
	public Spider(GameWorld gw) {
		
		this.setSpeed(2);
		this.setHeading(new Random().nextInt(360));
		this.setColor(ColorUtil.WHITE);
		this.setSize( (new Random().nextInt(20 - 10) + 10)*8 );
		this.setLocation(new Point( new Random().nextFloat() * (1000.0f), new Random().nextFloat() * (1000.0f) ));
		this.setGW(gw);
		
	}
	
	public void move(int elapsedMilliSecs) {
		
		int headingModifier = 0;
		Point newLocation = null;
		
		while( newLocation == null || newLocation.getX() > getGW().getWidth() - this.getSize() || newLocation.getX() < 0.0f ||
				newLocation.getY() > getGW().getHeight() - this.getSize() || newLocation.getY() < 0.0f) {
			int newHeading = (this.getHeading()+headingModifier + new Random().nextInt(11) - 5);
			double theta = Math.toRadians(90 - (double)newHeading),
				   deltaX = ( Math.cos(theta) * (double)this.getSpeed() * elapsedMilliSecs/20),
				   deltaY = (Math.sin(theta) * (double)this.getSpeed() * elapsedMilliSecs/20);
			newLocation = new Point((float)deltaX + this.getLocation().getX(), (float)deltaY + this.getLocation().getY());
			headingModifier += 45;
		}
		this.setHeading(this.getHeading()+headingModifier-45);
		this.setLocation(newLocation);
	}
	
	@Override
	public void setColor(int color) { super.setColor(color); }
	public String toString() { return super.toString(); }
	
	//Shape - Unfilled isosceles triangles
	//Size - indicates the base length and height of the triangle
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
		int spiX = (int)(pCmpRelPrnt.getX() + this.getLocation().getX());
		int spiY = (int)(pCmpRelPrnt.getY() + this.getLocation().getY());
		
		g.setColor(this.getColor());
		g.drawPolygon(new int[] {spiX + this.getSize()/2, spiX + this.getSize(), spiX},
				new int[] {spiY + this.getSize(), spiY, spiY}, 3);
		g.setColor(ColorUtil.GREEN);
		g.fillArc(spiX, spiY, 10, 10, 0, 360);
	}
	
}
