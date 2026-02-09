package StochasticSimulation.Events;

import StochasticSimulation.*;
import java.util.*;

/**
 * This class represents a reproduction event in a stochastic simulation.
 * It implements the EvolutionEvent interface.
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin 
 */
public class ReproductionEvent implements EvolutionEvent {
    /**
     * The reproduction rate parameter.
     */
    private float rho;

    /**
     * Constructs a new ReproductionEvent with the specified reproduction rate.
     * @param rho the reproduction rate
     */ 
    public ReproductionEvent(float rho) {
        this.rho = rho;
    }


    /**
     * Executes the reproduction event on the given population.
     * The best individual is selected and a copy is made of its distribution.
     * A number of systems are removed from the distribution and then randomly re-added.
     * The new individual is then added to the population.
     * @param population the population on which the event is executed
     * @param random a random number generator
     */ 
    @Override
    public void execute(Population population, Random random) {
        Individual parent = population.getBestIndividual();
        if (parent != null) {
            List<List<Integer>> newDistribution = new ArrayList<>();
            
            for (List<Integer> patrol : parent.getDistribution()) {
                newDistribution.add(new ArrayList<>(patrol));
            }

            int numSystemsToRemove = (int) Math.floor((1 - parent.getComfortLevel()) * parent.getDistribution().size());
            List<Integer> removedSystems = new ArrayList<>();

            for (int i = 0; i < numSystemsToRemove; i++) {
                int patrolIndex = random.nextInt(newDistribution.size());
                List<Integer> patrol = newDistribution.get(patrolIndex);
                
                if (!patrol.isEmpty()) {
                    int system = patrol.remove(random.nextInt(patrol.size()));
                    removedSystems.add(system);
                }
            }

            Collections.shuffle(removedSystems, random);

            for (int system : removedSystems) {
                int patrolIndex = random.nextInt(newDistribution.size());
                newDistribution.get(patrolIndex).add(system);
            }

            population.addIndividual(new Individual(parent.getMatrix(), newDistribution));
        }
    }

    /**
     * Calculates the time until the next reproduction event for a given individual.
     * The time is calculated based on the individual's comfort level.
     * @param individual the individual for whom the next event time is calculated
     * @return the time until the next reproduction event
     */
    @Override
    public float getNextEventTime(Individual individual) {
        float comfortLevel = individual.getComfortLevel();
        return (1 - (float) Math.log(comfortLevel)) * rho;
    }
}
