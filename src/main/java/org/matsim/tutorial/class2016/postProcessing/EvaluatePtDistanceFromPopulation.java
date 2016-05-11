package org.matsim.tutorial.class2016.postProcessing;

import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;

public class EvaluatePtDistanceFromPopulation {
	
	
	private final Population population;
	private int[] distanceClasses = new int[30];

	
	public EvaluatePtDistanceFromPopulation(Population population) {
		this.population = population;
	}

	public int[] getDistanceClasses() {
		return distanceClasses;
	}
	public void evaluate(){
		for (Person p : this.population.getPersons().values()){
			boolean currentLegIsRelevantLeg = false;
			double distance = 0;
			Plan plan = p.getSelectedPlan();
			
			for (PlanElement pe : plan.getPlanElements()){
				if (pe instanceof Activity){
					Activity act = (Activity) pe;
					if (!act.getType().equals("pt interaction")){
						if (currentLegIsRelevantLeg){
							currentLegIsRelevantLeg = false;
							int km = (int) ( distance / 1000.0);
							if (km>29) km = 29;
							this.distanceClasses[km]++;
							distance = 0;
						}
					}
				}
				else if (pe instanceof Leg){
					Leg leg = (Leg) pe;
					String mode = leg.getMode();
					if ((mode.equals(TransportMode.pt))||(mode.equals(TransportMode.transit_walk))){
						currentLegIsRelevantLeg = true;
						distance += leg.getRoute().getDistance();
					}
				}
				
			}
		}
	}
}
