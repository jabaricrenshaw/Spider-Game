package com.mycompany.a3;

import java.util.*;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.media.Media;
import com.codename1.ui.Dialog;
import com.codename1.ui.geom.Dimension;

public class GameWorld extends Observable {

	private GameObjectCollection gOC;
	private int livesRemaining = 3;
	private int clock = 0;
	private boolean sound = false;
	private Sound sFlag, sSpider, sFood;
	private BGSound sBG;
	private boolean pause = false;
	private boolean move;
	private int width;
	private int height;

	public void init() {
		// Game Object Collection using Iterator Design Pattern
		gOC = new GameObjectCollection();

		// 1 Ant using Singleton Design Pattern
		gOC.add(Ant.getAnt(this));

		// 4 Flags
		gOC.add(new Flag(this, 1, new Point(this.getWidth()/8, this.getHeight()/8)));
		gOC.add(new Flag(this, 2, new Point(this.getWidth()/8, 3*this.getHeight()/4)));
		gOC.add(new Flag(this, 3, new Point(3*this.getWidth()/4, this.getHeight()/8)));
		gOC.add(new Flag(this, 4, new Point(3*this.getWidth()/4, 3*this.getHeight()/4)));

		// 2 Spiders
		gOC.add(new Spider(this));
		gOC.add(new Spider(this));

		// 2 FoodStations
		gOC.add(new FoodStation(this));
		gOC.add(new FoodStation(this));
	}

	public void accelerate() {
		Ant ant = Ant.getAnt(this);
		if (ant.getSpeed() + 1 <= ant.getMaximumSpeed()) {
			ant.setSpeed(ant.getSpeed() + 1);
		}

		this.setChanged();
		this.notifyObservers();
	}

	public void brake() {
		Ant ant = Ant.getAnt(this);
		if (ant.getSpeed() - 1 >= 0) {
			ant.setSpeed(ant.getSpeed() - 1);
		}

		this.setChanged();
		this.notifyObservers();
	}

	public void headingLeft() {
		Ant ant = Ant.getAnt(this);
		ant.setHeading(ant.getHeading() - 5);

		this.setChanged();
		this.notifyObservers();
	}

	public void headingRight() {
		Ant ant = Ant.getAnt(this);
		ant.setHeading(ant.getHeading() + 5);

		this.setChanged();
		this.notifyObservers();
	}

	public void collideFlag(int seqNum) {
		Ant ant = Ant.getAnt(this);
		Flag flag = null;

		IIterator gOI = gOC.getIterator();
		while (gOI.hasNext()) {
			GameObject go = (GameObject) gOI.getNext();
			if (go instanceof Flag && ((Flag) go).getSequenceNumber() == seqNum) {
				flag = (Flag) go;
				break;
			}
		}

		if (sound != false) {
			sFlag.play();
		}

		ant.setLastFlagReached(flag);
		checkHealth();
		this.setChanged();
		this.notifyObservers();

	}

	public void collideFood(FoodStation fs) {
		Ant ant = Ant.getAnt(this);

		if (fs.getCapacity() != 0) {
			// Increase the ant's foodLevel to the capacity of the foodStation
			ant.setFoodLevel(ant.getFoodLevel() + fs.getCapacity());
			// Set the capacity of the foodStation after Ant eats
			fs.setCapacity(0);
			// Set the color of the foodStation to GRAY
			fs.setColor(ColorUtil.GRAY);
			// Create a new foodStation at a random location
			gOC.add(new FoodStation(this));

			if (sound != false) {
				sFood.play();
			}

			this.setChanged();
			this.notifyObservers();
		}
	}

	public void collideSpider() {
		Ant ant = Ant.getAnt(this);

		// No need for spider, since collisions will not affect spiders

		// Reduce ant health level by 1
		ant.setHealthLevel(ant.getHealthLevel() - 1);
		// Lighten color of the ant
		ant.setColor(ColorUtil.rgb(ColorUtil.red(ant.getColor()), ColorUtil.green(ant.getColor()) - 20,
				ColorUtil.blue(ant.getColor()) - 20));
		// Reduce the speed of the ant if necessary
		// We will reduce the speed of the ant by 1
		if (((Movable) ant).getSpeed() - 1 > 0) {
			((Movable) ant).setSpeed(((Movable) ant).getSpeed() - 1);
		}

		if (sound != false) {
			sSpider.play();
		}

		checkHealth();
		this.setChanged();
		this.notifyObservers();
	}

	public void clockTick(int elapsedMilliSecs) {
		/*
		 * Tell the game world that the clock has ticked... 1) Spiders update their
		 * heading... 2) Movable objects update their positions (according to heading
		 * and speed) 3) The ant's food level is reduced (see foodConsumptionRate) 4)
		 * The elapsed time "game clock" is incremented by one
		 */

		if (!isPaused()) {
			// 1 and 2 combined since...
			// Spiders will automatically update their heading based on their move() method
			IIterator gOI = gOC.getIterator();
			while (gOI.hasNext()) {
				GameObject go = (GameObject) gOI.getNext();
				if (go instanceof Movable) {
					((Movable) go).move(elapsedMilliSecs);
				}
			}

			// 3
			Ant ant = Ant.getAnt(this);
			ant.setFoodLevel(ant.getFoodLevel() - ant.getFoodConsumptionRate());

			// 4
			this.clock++;

			gOI = gOC.getIterator();
			while (gOI.hasNext()) {
				GameObject curObj = (GameObject) gOI.getNext();
				IIterator gOI2 = gOC.getIterator();
				while (gOI2.hasNext()) {
					GameObject otherObj = (GameObject) gOI2.getNext();
					if (otherObj != curObj && curObj.collidesWith(otherObj)) {
						curObj.handleCollision(otherObj);
					}else {
						curObj.handleNonCollision(otherObj);
					}
				}
			}
			
			checkHealth();
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getClock() {
		return this.clock;
	}

	public int getLivesRemaining() {
		return this.livesRemaining;
	}

	public String getSoundStatus() {
		if (sound == true) {
			return "ON";
		}
		return "OFF";
	}

	public void setSoundStatus(boolean b) {
		this.sound = b;

		this.setChanged();
		this.notifyObservers();
	}

	public void setSoundStatus(String str) {
		if (str == "ON") {
			this.sound = true;
		} else if (str == "OFF") {
			this.sound = false;
		}

		this.setChanged();
		this.notifyObservers();
	}

	public GameObjectCollection getCollection() {
		return this.gOC;
	}

	public void checkHealth() {
		Ant ant = Ant.getAnt(this);
		if (ant.getFoodLevel() <= 0 || ant.getHealthLevel() <= 0) {
			if (livesRemaining == 0) {
				Dialog.show("Game Over!", "You lost!\nTotal time: " + clock, "Ok", null);
				System.out.println("Game over, you lost!");
				System.exit(1);
			} else {
				livesRemaining--;
				Ant.resetAnt(this);
				init();
			}
		}

		if (ant.getLastFlagReached() == 4) {
			Dialog.show("Game Over!", "You win!\nTotal time: " + clock, "Ok", null);
			System.out.println("Game over, you win! Total time: " + clock);
			System.exit(1);
		}

	}

	public void reposition(Point newLocation) {
		if (this.isPaused()) {
			IIterator gOI = gOC.getIterator();
			while (gOI.hasNext()) {
				GameObject object = (GameObject) gOI.getNext();
				if (object instanceof ISelectable && ((ISelectable) object).isSelected()) {
					((GameObject) object)
							.setLocation(new Point(newLocation.getX(), newLocation.getY() - object.getSize()));
					((ISelectable) object).setSelected(false);
					this.setMove();
					this.setChanged();
					this.notifyObservers();
					break;
				}
			}
		}

	}

	public void setMove() {
		move = !move;
	};

	public boolean getMove() {
		return move;
	}

	public boolean isPaused() {
		return this.pause;
	}

	public void setPaused() {
		if (pause) {
			System.out.println("Game will now continue.");
		} else {
			System.out.println("Game is now paused.");
		}
		pause = !pause;
		this.setChanged();
		this.notifyObservers();
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public void setHeight(int h) {
		this.height = h;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	

	public BGSound getBGSound() {
		return sBG;
	}

	public void quit() {
		System.out.println("Thanks for playing!");
		System.exit(0);
	}

	public void createSounds() {
		sBG = new BGSound("bg_music.mp3");
		sFlag = new Sound("cheer.wav");
		sSpider = new Sound("grunt.wav");
		sFood = new Sound("eat.aiff");

	}
}
