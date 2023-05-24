package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer{
	private GameWorld gw;
	
	public MapView(Observable observable) {
		//Adding MapView as observer to "observable" in method parameter.
		//"observable" will be GameWorld
		observable.addObserver(this);
		gw = (GameWorld)observable;
		
		this.getAllStyles().setBgColor(ColorUtil.rgb(0,0,0));
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLUE));
	}
	
	@Override
	public void update(Observable observable, Object data) {
		IIterator gOI = gw.getCollection().getIterator();
		
		while(gOI.hasNext()) {
			System.out.println( ((GameObject)gOI.getNext()).toString() );
		}
		
		this.repaint();
	}
	
	/*
	 * MapView should override paint(), which will be invoked as a result
	 * of calling repaint(). It is then the duty of paint() to iterate through
	 * the game objects invoking draw().
	 */
	
	/*
	 * ...the reference to the GameWorld must be saved when MapView is constructed,
	 * or alternatively the update() method must save it prior to calling repaint().
	 */
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		IIterator gOI = gw.getCollection().getIterator();
		while(gOI.hasNext()) {
			((GameObject)gOI.getNext()).draw(g, new Point((int)this.getX(), (int)this.getY()));
		}
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		if(gw.getMove() && gw.isPaused()) {
			gw.reposition(new Point(x-getX(),y-getY()));
		}else {
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();
			
			Point pPtrRelPrnt = new Point(x,y);
			Point pCmpRelPrnt = new Point(getX(), getY());
			IIterator gOI = gw.getCollection().getIterator();
			while(gOI.hasNext()) {
				GameObject object = (GameObject)gOI.getNext();
				if(object instanceof ISelectable) {
					if( ((ISelectable)object).contains(pPtrRelPrnt, pCmpRelPrnt) ){
						((ISelectable)object).setSelected(true);
					}else {
						((ISelectable)object).setSelected(false);
					}
				}
			}
		}
		
		this.repaint();
	}
}
