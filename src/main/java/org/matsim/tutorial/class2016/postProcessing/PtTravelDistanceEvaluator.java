package org.matsim.tutorial.class2016.postProcessing;

import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.ActivityStartEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.utils.geometry.CoordUtils;


public class PtTravelDistanceEvaluator implements ActivityStartEventHandler, PersonDepartureEventHandler {

	private final Network network;
	private final Map<Id<Person>,Coord> startCoords = new HashMap<>();
	private int[] distanceClasses = new int[30];
	private final double beelineFactor;
	//one distance class per km 
	
	public PtTravelDistanceEvaluator(Network network, double beelineFactor) {
		this.network = network;
		this.beelineFactor = beelineFactor;
	}
	
	public int[] getDistanceClasses() {
		return distanceClasses;
	}


	@Override
	public void handleEvent(PersonDepartureEvent event) {
		String mode = event.getLegMode();
		if ((mode.equals(TransportMode.pt))||(mode.equals(TransportMode.transit_walk))){
			if (!startCoords.containsKey(event.getPersonId())){
				Coord startCoord = network.getLinks().get(event.getLinkId()).getCoord();
				startCoords.put(event.getPersonId(), startCoord);
			}
		}
	}

	@Override
	public void handleEvent(ActivityStartEvent event) {
		
		if (event.getActType().equals("pt interaction")) return;
		if (startCoords.containsKey(event.getPersonId())){
			Coord start = startCoords.remove(event.getPersonId());
			Coord end = network.getLinks().get(event.getLinkId()).getCoord();
			double distance = CoordUtils.calcEuclideanDistance(start, end) * beelineFactor ;
			int km = (int) ( distance / 1000.0);
			if (km>29) km = 29;
			this.distanceClasses[km]++;
		}
	}
	
	
	
	
	@Override
	public void reset(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
