package org.matsim.tutorial.class2016;

import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.ActivityStartEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.network.Network;

public class PtTravelDistanceEvaluator implements ActivityStartEventHandler, PersonDepartureEventHandler {

	private final Network network;
	
	private int[] distanceClasses = new int[30];
	//one distance class per km 
	
	public PtTravelDistanceEvaluator(Network network) {
		this.network = network;
	}
	
	public int[] getDistanceClasses() {
		return distanceClasses;
	}


	@Override
	public void handleEvent(PersonDepartureEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(ActivityStartEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reset(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
