package org.cuacfm.members.model.exceptions;

/** The Class MaximumCapacityException. */
@SuppressWarnings("serial")
public class MaximumCapacityException extends Exception{
	
	/** The max places. */
	private int maxPlaces;
	
	/**
	 * Instantiates a new maximum capacity exception.
	 *
	 * @param maxPlaces the max places
	 */
	public MaximumCapacityException(int maxPlaces) {
			super("The capacity of the training is " + maxPlaces + " and already full.");

			this.maxPlaces=maxPlaces;
	}

	/**
	 * Gets the max places.
	 *
	 * @return the max places
	 */
	public int getMaxPlaces() {
		return maxPlaces;
	}

}
