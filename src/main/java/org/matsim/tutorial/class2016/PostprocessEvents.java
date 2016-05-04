package org.matsim.tutorial.class2016;

import java.io.BufferedWriter;
import java.io.IOException;

import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.utils.io.IOUtils;

public class PostprocessEvents {

	public static void main(String[] args) {
		String outputFolder = "output/cottbus/output/cb02/";
		String eventsFile = outputFolder + "output_events.xml.gz";
		String networkFile = outputFolder + "output_network.xml.gz";
		Network network = NetworkUtils.createNetwork();
		new MatsimNetworkReader(network).readFile(networkFile);
		EventsManager events = EventsUtils.createEventsManager();
		
		
		Config config = ConfigUtils.createConfig();
		double beelineFactorBike = config.plansCalcRoute().getBeelineDistanceFactors().get(TransportMode.bike);
		System.out.println(beelineFactorBike);
		TeleportedModeTravelDistanceEvaluator bikeDistanceEvaluator = new TeleportedModeTravelDistanceEvaluator(
				TransportMode.bike, beelineFactorBike, network);

		double beelineFactorWalk = config.plansCalcRoute().getBeelineDistanceFactors().get(TransportMode.walk);
		System.out.println(beelineFactorWalk);
		TeleportedModeTravelDistanceEvaluator walkDistanceEvaluator = new TeleportedModeTravelDistanceEvaluator(
				TransportMode.walk, beelineFactorWalk, network);

		events.addHandler(bikeDistanceEvaluator);
		events.addHandler(walkDistanceEvaluator);

		new MatsimEventsReader(events).readFile(eventsFile);

		writeHistogramData(outputFolder+"bikedistances.csv", bikeDistanceEvaluator.getDistanceClasses());
		writeHistogramData(outputFolder+"walkdistances.csv", walkDistanceEvaluator.getDistanceClasses());
	}
	
	
	
	

	static void writeHistogramData(String filename, int[] distanceClasses) {
		BufferedWriter bw = IOUtils.getBufferedWriter(filename);
		try {
			bw.write("distance;rides");
			for (int i = 0; i < distanceClasses.length; i++) {
				bw.newLine();
				bw.write(i + ";" + distanceClasses[i]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
