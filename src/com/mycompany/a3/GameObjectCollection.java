package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	
	private ArrayList<GameObject> collection;
	
	public GameObjectCollection() {
		this.collection = new ArrayList<GameObject>();
	}
	
	@Override
	public void add(Object newObject) {
		
		if(newObject instanceof Ant) {
			collection.add((Ant)newObject);
		}else if(newObject instanceof Spider) {
			collection.add((Spider)newObject);
		}else if(newObject instanceof Flag) {
			collection.add((Flag)newObject);
		}else if(newObject instanceof FoodStation) {
			collection.add((FoodStation)newObject);
		}
		
	}

	@Override
	public IIterator getIterator() {
		
		return new GameObjectListIterator();

	}
	
	private class GameObjectListIterator implements IIterator {
		
		private int currElementIndex;
		
		public GameObjectListIterator() {
			this.currElementIndex = -1;
		}
		
		public boolean hasNext() {
			
			if( collection.size() <= 0 || currElementIndex == collection.size() - 1) { 
				return false;
			}
			
			return true;
			
		}

		@Override
		public Object getNext() {
			return collection.get(++currElementIndex);
		}
		
	}
	
}
