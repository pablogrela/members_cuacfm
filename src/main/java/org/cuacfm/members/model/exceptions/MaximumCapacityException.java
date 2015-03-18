package org.cuacfm.members.model.exceptions;

@SuppressWarnings("serial")
public class MaximumCapacityException extends Exception{
	
	private int maxPlaces;
	public MaximumCapacityException(int maxPlaces) {
			super("The capacity of the training is " + maxPlaces + " and already full.");

			this.maxPlaces=maxPlaces;
	}

	public int getMaxPlaces() {
		return maxPlaces;
	}

}
