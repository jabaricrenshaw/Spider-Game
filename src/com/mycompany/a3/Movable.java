package com.mycompany.a3;

public abstract class Movable extends GameObject {
	
	private int heading;
	private int speed;
	
	public abstract void move(int elapsedMilliSecs);
	
	public int getSpeed() { return speed; }
	public int getHeading() { return heading; }
	public void setSpeed(int speed) { this.speed = speed; }
	
	public void setHeading(int heading) {
		if(heading >= 360) {
			this.heading = heading - 360;
		}else if(heading < 0) {
			this.heading = 360 + heading;
		}else {
			this.heading = heading;
		}
	}

	public String toString() {
		return super.toString() + " heading=" + this.getHeading() +
				" speed=" + this.getSpeed();
	}
	
}
