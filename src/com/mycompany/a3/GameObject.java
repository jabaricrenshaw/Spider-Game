package com.mycompany.a3;

import java.util.Vector;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public abstract class GameObject implements IDrawable, ICollider {

	private int size;
	private Point location;
	private int color;
	private GameWorld gw;
	private Vector<GameObject> collisions = new Vector<>();

	public GameObject() {
	}

	public GameObject(int size, Point location, int color) {
		this.size = size;
		this.location = location;
		this.color = color;
		this.setGW(gw);
	}

	public abstract void draw(Graphics g, Point pCmpRelPrnt);

	public void setGW(GameWorld gw) {
		this.gw = gw;
	}

	public GameWorld getGW() {
		return this.gw;
	};

	public int getColor() {
		return this.color;
	}

	public int getSize() {
		return this.size;
	}

	public Point getLocation() {
		return this.location;
	}

	public void setColor(int color) {
		if (!(this instanceof Flag) || (this instanceof Flag && this.getColor() == 0)) {
			this.color = color;
		}
	}

	public void setSize(int size) {
		if (this instanceof FoodStation || this.getSize() <= 0) {
			this.size = size;
		}
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String toString() {
		return this.getClass().getSimpleName() + ": loc=" + Math.round((this.getLocation().getX() * 100.0) / 100.0)
				+ "," + Math.round((this.getLocation().getY() * 100.0) / 100.0) + " color=["
				+ ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]" + " size=" + this.getSize();

	}

	public Vector<GameObject> getCollisions() {
		return this.collisions;
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {

		boolean result = false;
		if (!(this instanceof Ant || otherObject instanceof Ant)) {
			return result;
		}

		int thisRadius = this.getSize() / 2;
		int otherRadius = otherObject.getSize() / 2;

		int thisCenterX = (int) this.getLocation().getX() + thisRadius;
		int thisCenterY = (int) this.getLocation().getY() + thisRadius;

		int otherCenterX = (int) otherObject.getLocation().getX() + otherRadius;
		int otherCenterY = (int) otherObject.getLocation().getY() + otherRadius;

		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int distBetweenCentersSqr = (dx * dx + dy * dy);

		int radiiSqr = thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius;

		if (distBetweenCentersSqr <= radiiSqr) {
			result = true;
			System.out.println("... Collision detected ...");
		}

		return result;
	}

	public void handleNonCollision(GameObject otherObject) {
		if (this.collisions.contains(otherObject)) {
			this.collisions.remove(otherObject);
		}
	}

	@Override
	public void handleCollision(GameObject otherObject) {

		boolean[] possibleColl = {
				// antFood
				(this instanceof Ant && otherObject instanceof FoodStation)
						|| (this instanceof FoodStation && otherObject instanceof Ant),
				// antFlag
				(this instanceof Ant && otherObject instanceof Flag)
						|| (this instanceof Flag && otherObject instanceof Ant),
				// antSpider
				(this instanceof Ant && otherObject instanceof Spider)
						|| (this instanceof Spider && otherObject instanceof Ant) };

		if (this.getCollisions() == null
				|| (!this.getCollisions().contains(otherObject) && !otherObject.getCollisions().contains(this))) {
			if (possibleColl[0]) {
				if (this instanceof FoodStation) {
					gw.collideFood(((FoodStation) this));
				} else {
					gw.collideFood(((FoodStation) otherObject));
				}
			} else if (possibleColl[1]) {
				if (this instanceof Flag) {
					gw.collideFlag(((Flag) this).getSequenceNumber());
				} else {
					gw.collideFlag(((Flag) otherObject).getSequenceNumber());
				}
			} else if (possibleColl[2]) {
				gw.collideSpider();
			}

			this.getCollisions().add(otherObject);
			otherObject.getCollisions().add(this);
		}
		/*
		 * if( (!(this instanceof Ant) && !(otherObject instanceof Ant)) ||
		 * this.getCollisions().contains(otherObject) &&
		 * otherObject.getCollisions().contains(this) ) { return; }
		 */
	}

}
