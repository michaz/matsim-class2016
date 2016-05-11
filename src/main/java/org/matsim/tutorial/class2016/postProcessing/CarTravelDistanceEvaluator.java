package org.matsim.tutorial.class2016.postProcessing;

import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.vehicles.Vehicle;

public class CarTravelDistanceEvaluator implements PersonDepartureEventHandler, PersonArrivalEventHandler,
		LinkEnterEventHandler{

	private final Network network;
	
	private Map<Id<Person>,Double> currentTravelDistance = new HashMap<>();
	private int[] distanceClasses = new int[30];

	// one distance class per km
	public CarTravelDistanceEvaluator(Network network) {
		this.network = network;
	}



	@Override
	public void handleEvent(LinkEnterEvent event) {
		Id<Person> personId = vehicleId2PersonId(event.getVehicleId());
		if (this.currentTravelDistance.containsKey(personId)){
			double distanceSoFar = this.currentTravelDistance.get(personId);
			double linkLength = network.getLinks().get(event.getLinkId()).getLength();
			this.currentTravelDistance.put(personId, distanceSoFar+linkLength);
		}

	}

	@Override
	public void handleEvent(PersonArrivalEvent event) {
		if (event.getPersonId().toString().startsWith("pt_")) return;
		if (this.currentTravelDistance.containsKey(event.getPersonId())){
			double distance = this.currentTravelDistance.remove(event.getPersonId());
			int km = (int) ( distance / 1000.0);
			if (km>29) km = 29;
			this.distanceClasses[km]++;
		}

	}

	@Override
	public void handleEvent(PersonDepartureEvent event) {
		if (event.getPersonId().toString().startsWith("pt_")) return;
		if (event.getLegMode().equals(TransportMode.car)){
			this.currentTravelDistance.put(event.getPersonId(), 0.0);
		}
	}

	@Override
	public void reset(int event) {
		// for post-processing events, this method can be ignored.
	}

	public int[] getDistanceClasses() {
		return distanceClasses;
	}

	private Id<Vehicle> personId2VehicleId(Id<Person> personId) {
		return Id.createVehicleId(personId.toString());
	}
	
	private Id<Person> vehicleId2PersonId(Id<Vehicle> vehicleId) {
		return Id.createPersonId(vehicleId.toString());
	}

}
