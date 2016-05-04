package org.matsim.tutorial.class2016;

import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.utils.geometry.CoordUtils;

public class TeleportedModeTravelDistanceEvaluator implements PersonDepartureEventHandler, PersonArrivalEventHandler
{

	
	private final double beelineDistanceFactor;
	private final String mode;
	private final Network network;
	
	private Map<Id<Person>,Coord> agentDepartureLocations = new HashMap<>();
	
	private int[] distanceClasses = new int[30];
	//one distance class per km 
	
	public TeleportedModeTravelDistanceEvaluator(String mode, double modeSpecificBeelineDistanceFactor, Network network) {
		this.beelineDistanceFactor = modeSpecificBeelineDistanceFactor;
		this.mode=mode;
		this.network = network;
	}

	@Override
	public void handleEvent(PersonArrivalEvent event) {
		if (event.getLegMode().equals(mode)){
			Id<Link> linkId = event.getLinkId();
			Coord endcoord = network.getLinks().get(linkId).getCoord();
			Coord startCoord = this.agentDepartureLocations.remove(event.getPersonId());
			double beelineDistance = CoordUtils.calcEuclideanDistance(startCoord, endcoord);
			double distance = beelineDistance * beelineDistanceFactor;
			int km = (int) (distance/1000.0);
			if (km>29) km = 29;
			distanceClasses[km]++;
		}
	}

	@Override
	public void handleEvent(PersonDepartureEvent event) {
		if (event.getLegMode().equals(mode))
		{
			Id<Link> linkId = event.getLinkId();
			Coord coord = network.getLinks().get(linkId).getCoord();
			this.agentDepartureLocations.put(event.getPersonId(), coord);
			
		}
	}

	@Override
	public void reset(int event) {
		// TODO Auto-generated method stub
		// for post-processing events, this method can be ignored.
	}
	
	public int[] getDistanceClasses() {
		return distanceClasses;
	}
}
