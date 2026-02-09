package StochasticSimulation;

import java.util.Random;

/**
 * This interface represents an evolution event in a stochastic simulation.
 *
 * The EvolutionEvent interface defines methods for executing an event on a population,
 * and for calculating the next event time for an individual. Implementing classes will
 * provide the specific behavior for different types of evolution events.
 *
 * @version 1.0
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 */
public interface EvolutionEvent {
    /**
     * This method executes the evolution event on the given population using the provided random object.
     *
     * @param population the population on which the event is to be executed
     * @param random the random object used for stochastic processes
     */
    void execute(Population population, Random random);
    
    /**
     * This method calculates the next event time for the given individual.
     *
     * @param individual the individual for whom the next event time is to be calculated
     * @return the time until the next event for the given individual
     */
    float getNextEventTime(Individual individual);
}
