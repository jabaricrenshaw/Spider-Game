package com.mycompany.a3;

/* 
 * Steerable - Other objects can request a change
 * in the heading of steerable objects.
 */
public interface ISteerable {
	int getHeading();
	void setHeading(int newHeading);
}
