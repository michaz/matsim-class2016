package org.matsim.tutorial.class2016;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.vehicles.Vehicle;

public class CarTravelDistanceEvaluator implements PersonDepartureEventHandler, PersonArrivalEventHandler,
		LinkEnterEventHandler, LinkLeaveEventHandler {

	private final Network network;

	private int[] distanceClasses = new int[30];

	// one distance class per km
	public CarTravelDistanceEvaluator(Network network) {
		this.network = network;
	}

	@Override
	public void handleEvent(LinkLeaveEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(LinkEnterEvent arg0) {
		// TODO Auto-generated method stub

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
		// for post-processing events, this method can be ignored.
	}

	public int[] getDistanceClasses() {
		return distanceClasses;
	}

	private Id<Vehicle> personId2VehicleId(Id<Person> personId) {
		return Id.createVehicleId(personId.toString());
	}

}
