package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public abstract class Fixed extends GameObject implements ISelectable {
	
	private boolean isSelected = false;
	
	public abstract void draw(Graphics g, Point pCmpRelPrnt);
	
	public String toString() { return super.toString(); }
	public void setSelected(boolean b) { this.isSelected = b; }
	public boolean isSelected() { return this.isSelected; }
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = (int)pPtrRelPrnt.getX();
		int py = (int)pPtrRelPrnt.getY();
		
		int xLoc = (int)(pCmpRelPrnt.getX() + this.getLocation().getX());
		int yLoc = (int)(pCmpRelPrnt.getY() + this.getLocation().getY());
		
		
		if( (px >= xLoc) && (px <= xLoc + this.getSize()) && (py >= yLoc) && py <= (yLoc + this.getSize()) ) {
			return true;
		}else {
			return false;
		}
	}
	
}
