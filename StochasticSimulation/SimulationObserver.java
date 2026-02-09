package StochasticSimulation;

import java.util.List;

public class SimulationObserver extends Observer {
    @Override
    public void update(int observationNumber, float currentTime, int eventCount, int epidemicCount, Population population) {
        Individual bestIndividual = population.getBestIndividual();
        Individual bestEverIndividual = population.getBestEverIndividual();
        List<Individual> allTimeTopDistributions = population.getAllTimeTopDistributions();
        
        System.out.println("Observation " + observationNumber + ":");
        System.out.println("                Present instant: " + currentTime);
        System.out.println("                Number of realized events: " + eventCount);
        System.out.println("                Population size: " + population.getPopulationSize());
        System.out.println("                Number of epidemics: " + epidemicCount);
        System.out.println("                Best distribution of the patrols: " + formatDistribution(bestIndividual.getDistribution()));
        System.out.println("                Empire policing time: " + bestIndividual.getOptimalPatrolTime());
        System.out.println("                Comfort: " + bestIndividual.getComfortLevel());
        System.out.println("                Best ever distribution of the patrols: " + formatDistribution(bestEverIndividual.getDistribution()));
        System.out.println("                Empire policing time: " + bestEverIndividual.getOptimalPatrolTime());
        System.out.println("                Comfort: " + bestEverIndividual.getComfortLevel());

        for (Individual individual : allTimeTopDistributions) {
            System.out.println("                " + formatDistribution(individual.getDistribution()) + " : " + individual.getOptimalPatrolTime() + " : " + individual.getComfortLevel());
        }
    }

    public static String formatDistribution(List<List<Integer>> distribution) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (List<Integer> patrol : distribution) {
            sb.append("{");
            for (int system : patrol) {
                sb.append(system + 1).append(",");
            }
            if (patrol.size() > 0) {
                sb.setLength(sb.length() - 1);  // Remove trailing comma
            }
            sb.append("},");
        }
        if (distribution.size() > 0) {
            sb.setLength(sb.length() - 1);  // Remove trailing comma
        }
        sb.append("}");
        return sb.toString();
    }

	@Override
	public void update_restart_counter(int restartNum) {
		// TODO Auto-generated method stub
		
	}
}
