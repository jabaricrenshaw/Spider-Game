package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends Fixed {
	
	private int sequenceNumber;
	
	//This method is used to create flags with no attributes "empty body"
	public Flag() { }
	
	public Flag(GameWorld gw, int n, Point location) {
		
		this.sequenceNumber = n;
		this.setSize(10*10);
		this.setColor(ColorUtil.YELLOW);
		this.setLocation(location);
		this.setGW(gw);
		
		//We would use this statement to create flags at random locations
		//this.setLocation(new Point( new Random().nextFloat() * (1000.0f), new Random().nextFloat() * (1000.0f) ));
		
	}
	
	@Override
	public String toString() { return super.toString() + " seqNum=" + this.sequenceNumber; }
	public int getSequenceNumber() { return this.sequenceNumber; }
	
	@Override
	public void setColor(int color) {
		if(this.getColor() == 0) {
			super.setColor(color);
		}
	}
	
	//Shape - Filled isosceles triangles
	//Size - indicates the base length and height of the triangle
	//Flags include text showing their number -> drawString()
	public void draw(Graphics g, Point pCmpRelPrnt) {
		int triX = (int)(pCmpRelPrnt.getX() + this.getLocation().getX());
		int triY = (int)(pCmpRelPrnt.getY() + this.getLocation().getY());
		
		if(this.isSelected() && this.getGW().isPaused()) {
			g.setColor(this.getColor());
			g.drawPolygon(new int[] {triX + this.getSize()/2, triX + this.getSize(), triX},
					new int[] {triY + this.getSize(), triY, triY}, 3);
			
			g.setColor(ColorUtil.WHITE);
			g.drawString(String.valueOf(this.sequenceNumber), triX+this.getSize()/2-10, triY+this.getSize()/2-30);
		}else{
			g.setColor(this.getColor());
			g.fillPolygon(new int[] {triX + this.getSize()/2, triX + this.getSize(), triX},
					new int[] {triY + this.getSize(), triY, triY}, 3);
			
			g.setColor(ColorUtil.BLACK);
			g.drawString(String.valueOf(this.sequenceNumber), triX+this.getSize()/2-10, triY+this.getSize()/2-30);
		}
		/*
		System.out.println("Parent -> X:" + pCmpRelPrnt.getX() + "Y:" + pCmpRelPrnt.getY()
				+ "\nTri -> X:" + this.getLocation().getX() + "Y:" + this.getLocation().getY());
		*/
	}
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		return super.contains(pPtrRelPrnt, pCmpRelPrnt);
	}
}
