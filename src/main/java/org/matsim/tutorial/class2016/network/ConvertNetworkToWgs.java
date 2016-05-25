/* *********************************************************************** *
 * project: org.matsim.*
 * ConvertOsmToMatsim.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.tutorial.class2016.network;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.network.NetworkWriter;
import org.matsim.core.network.NodeImpl;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;


/**
 * @author jbischoff
 *
 */
public class ConvertNetworkToWgs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String input = "C:/Users/Joschka/Desktop/network.xml";
		String output = "C:/Users/Joschka/Desktop/networkWGS.xml";
		
		/* Read the network. */
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		new MatsimNetworkReader(scenario.getNetwork()).parse(input);
		
		/* Transform each node. */
		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation("EPSG:25833",TransformationFactory.WGS84);

		for(Node node : scenario.getNetwork().getNodes().values()){
			((NodeImpl)node).setCoord(ct.transform(node.getCoord()));
		}
		
		/* Write the resulting network. */
		new NetworkWriter(scenario.getNetwork()).write(output);
		
	}

}
