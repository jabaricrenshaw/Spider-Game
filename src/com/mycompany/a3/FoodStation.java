package com.mycompany.a3;
import java.util.Random;
import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class FoodStation extends Fixed {
	
	private int capacity;
	
	public FoodStation(GameWorld gw) {
		
		this.setSize( (new Random().nextInt(41) + 10)*3 );
		this.setLocation(new Point( new Random().nextFloat() *
				(1000.0f), new Random().nextFloat() * (1000.0f) ));
		
		this.setColor(ColorUtil.BLUE);
		this.capacity = this.getSize() / 2;
		this.setGW(gw);
		
	}
	
	public int getCapacity() { return this.capacity; }
	public void setCapacity(int newCapacity) { this.capacity = newCapacity; }
	
	@Override
	public String toString() { return super.toString() + " capacity=" + this.capacity; }
	
	//Shape - Filled square
	//Size - indicates the the length of square sides
	//Initial capacity of a food station is proportional to its size -> does not change.
	//Food stations include text showing food capacity -> drawString()
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int rectX = (int)(pCmpRelPrnt.getX() + this.getLocation().getX());
		int rectY = (int)(pCmpRelPrnt.getY() + this.getLocation().getY());
		
		if(this.isSelected() && this.getGW().isPaused()) {
			g.setColor(this.getColor());
			g.drawRect(rectX, rectY, this.getSize(), this.getSize());
			
			g.setColor(ColorUtil.WHITE);
			g.drawString(String.valueOf(this.getCapacity()), rectX+this.getSize()/4, rectY+this.getSize()/4);
		}else {
			g.setColor(this.getColor());
			g.fillRect(rectX, rectY, this.getSize(), this.getSize());
			
			g.setColor(ColorUtil.WHITE);
			g.drawString(String.valueOf(this.getCapacity()), rectX+this.getSize()/4, rectY+this.getSize()/4);
		}
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		return super.contains(pPtrRelPrnt, pCmpRelPrnt);
	}
}
