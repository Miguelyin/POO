package StochasticSimulation.Events;

import StochasticSimulation.*;
import java.util.*;
/**
 * This class represents a mutation event in a stochastic simulation.
 * It implements the EvolutionEvent interface.
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin 
 */
public class MutationEvent implements EvolutionEvent {
    /**
     * Calculates the time until the next epidemic event for a given individual.
     * Since epidemics are triggered by population size and not by time, this method always returns Float.MAX_VALUE.
     * @param individual the individual for whom the next event time is calculated
     * @return the time until the next epidemic event (always Float.MAX_VALUE)
     */ 
    private float delta;

    /**
     * Constructs a new MutationEvent with the specified mutation rate.
     * @param delta the mutation rate
     */
    public MutationEvent(float delta) {
        this.delta = delta;
    }

    /**
     * Executes the mutation event on the given population.
     * The best individual is selected and a mutation is performed on its distribution.
     * A system is randomly removed from one patrol and added to another.
     * The mutated individual is then added to the population.
     * @param population the population on which the event is executed
     * @param random a random number generator
     */
    @Override
    public void execute(Population population, Random random) {
        Individual individual = population.getBestIndividual();
        if (individual != null) {
            List<List<Integer>> newDistribution = new ArrayList<>();
            for (List<Integer> patrol : individual.getDistribution()) {
                newDistribution.add(new ArrayList<>(patrol));
            }

            int patrolIndex1 = random.nextInt(newDistribution.size());
            int patrolIndex2 = random.nextInt(newDistribution.size());
            while (patrolIndex1 == patrolIndex2) {
                patrolIndex2 = random.nextInt(newDistribution.size());
            }

            List<Integer> patrol1 = newDistribution.get(patrolIndex1);
            List<Integer> patrol2 = newDistribution.get(patrolIndex2);

            if (!patrol1.isEmpty()) {
                int system = patrol1.remove(random.nextInt(patrol1.size()));
                patrol2.add(system);
            }

            population.addIndividual(new Individual(individual.getMatrix(), newDistribution));
        }
    }

     /**
     * Calculates the time until the next mutation event for a given individual.
     * The time is calculated based on the individual's comfort level and the mutation rate.
     * @param individual the individual for whom the next event time is calculated
     * @return the time until the next mutation event
     */ 
    @Override
    public float getNextEventTime(Individual individual) {
        float comfortLevel = individual.getComfortLevel();
        
        return (1 - (float) Math.log(comfortLevel)) * delta;
    }
}
