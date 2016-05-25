package org.matsim.tutorial.class2016.population;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.network.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

public class RunCreateDrivingAgents {

	public static void main(String[] args) {
		Config config = ConfigUtils.createConfig();

		config.controler().setLastIteration(0);

		ActivityParams home = new ActivityParams("home");
		home.setTypicalDuration(16 * 60 * 60);
		config.planCalcScore().addActivityParams(home);
		ActivityParams work = new ActivityParams("work");
		work.setTypicalDuration(8 * 60 * 60);
		config.planCalcScore().addActivityParams(work);
		ActivityParams shop = new ActivityParams("shop");
		shop.setTypicalDuration(1 * 60 * 60);
		config.planCalcScore().addActivityParams(shop);


		Scenario scenario = ScenarioUtils.createScenario(config);

		new MatsimNetworkReader(scenario.getNetwork()).readFile("cottbus-with-pt/input/network.xml");

		fillScenario(scenario);

		Controler controler = new Controler(scenario);
		controler.getConfig().controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);
		controler.run();


	}

	private static Population fillScenario(Scenario scenario) {
		Population population = scenario.getPopulation();

		for (int i = 0; i < 1000; i++) {
			Coord coord = new Coord((double) (454941 + i * 10), (double) (5737814 + i * 10));
			Coord coordWork = new Coord((double) (454941 - i * 10), (double) (5737814 - i * 10));
			createOnePerson(scenario, population, i, coord, coordWork);
		}

		return population;
	}

	private static void createOnePerson(Scenario scenario,
										Population population, int i, Coord coord, Coord coordWork) {
		Person person = population.getFactory().createPerson(Id.createPersonId("p_"+i));

		Plan plan = population.getFactory().createPlan();


		Activity home = population.getFactory().createActivityFromCoord("home", coord);
		home.setEndTime(9*60*60 + i);
		plan.addActivity(home);

		Leg hinweg = population.getFactory().createLeg("car");
		plan.addLeg(hinweg);

		Activity work = population.getFactory().createActivityFromCoord("work", coordWork);
		work.setEndTime(17*60*60);
		plan.addActivity(work);

		Leg rueckweg = population.getFactory().createLeg("car");
		plan.addLeg(rueckweg);

		if (i % 2 == 0) {
			Activity shop = population.getFactory().createActivityFromCoord("shop", new Coord(454941, 5737814));
			shop.setEndTime(18*60*60);
			plan.addActivity(shop);
			Leg wirklicherRueckweg = population.getFactory().createLeg("car");
			plan.addLeg(wirklicherRueckweg);
		}

		Activity home2 = population.getFactory().createActivityFromCoord("home", coord);
		plan.addActivity(home2);

		person.addPlan(plan);
		population.addPerson(person);
	}

}

