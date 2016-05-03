package org.matsim.tutorial.class2016;

import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.network.Network;

public class TeleportedModeTravelDistanceEvaluator implements PersonDepartureEventHandler, PersonArrivalEventHandler
{

	
	private final double beelineDistanceFactor;
	private final String mode;
	private final Network network;
	
	private int[] distanceClasses = new int[30];
	//one distance class per km 
	
	public TeleportedModeTravelDistanceEvaluator(String mode, double modeSpecificBeelineDistanceFactor, Network network) {
		this.beelineDistanceFactor = modeSpecificBeelineDistanceFactor;
		this.mode=mode;
		this.network = network;
	}

	@Override
	public void handleEvent(PersonArrivalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(PersonDepartureEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(int arg0) {
		// TODO Auto-generated method stub
		// for post-processing events, this method can be ignored.
	}
	
	public int[] getDistanceClasses() {
		return distanceClasses;
	}
}
