package StochasticSimulation.Events;

import StochasticSimulation.*;
import java.util.Random;

/**
 * This class represents a death event in a stochastic simulation.
 * It implements the EvolutionEvent interface.
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 */
public class DeathEvent implements EvolutionEvent {
    /**
     * The death rate parameter.
     */ 
    private float mu;
    /**
     * Constructs a new DeathEvent with the specified death rate.
     * @param mu the death rate
     */
    public DeathEvent(float mu) {
        this.mu = mu;
    }
    
    /**
     * Executes the death event on the given population.
     * If the population size is greater than 0, it removes an individual from the population.
     * @param population the population on which the event is executed
     * @param random a random number generator
     */
    @Override
    public void execute(Population population, Random random) {
        if (population.getPopulationSize() > 0) {
            population.removeIndividual(random.nextInt(population.getPopulationSize()));
        }
    }
    
    /**
     * Calculates the time until the next death event for a given individual.
     * The time is calculated based on the individual's comfort level and the death rate.
     * @param individual the individual for whom the next event time is calculated
     * @return the time until the next death event
     */ 
    @Override
    public float getNextEventTime(Individual individual) {
        float comfortLevel = individual.getComfortLevel();
        return (1 - (float) Math.log(1 - comfortLevel)) * mu;
    }
}
