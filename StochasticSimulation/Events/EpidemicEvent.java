package StochasticSimulation.Events;

import StochasticSimulation.*;
import java.util.*;
/**
 * This class represents an epidemic event in a stochastic simulation.
 * It implements the EvolutionEvent interface.
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin 
 */
public class EpidemicEvent implements EvolutionEvent {
    /**
     * The maximum population size before an epidemic occurs.
     */ 
    private int maxPopulationSize;

    /**
     * Constructs a new EpidemicEvent with the specified maximum population size.
     * @param maxPopulationSize the maximum population size before an epidemic occurs
     */ 
    public EpidemicEvent(int maxPopulationSize) {
        this.maxPopulationSize = maxPopulationSize;
    }

    /**
     * Executes the epidemic event on the given population.
     * If the population size is greater than the maximum population size, an epidemic occurs.
     * The first 5 individuals always survive, and the rest have a 2/3 chance of survival based on their comfort level.
     * @param population the population on which the event is executed
     * @param random a random number generator
     */ 
    @Override
    public void execute(Population population, Random random) {
        if (population.getPopulationSize() > maxPopulationSize) {
            List<Individual> survivors = new ArrayList<>();
            
            for (int i = 0; i < 5 && i < population.getPopulationSize(); i++) {
                survivors.add(population.getIndividuals().get(i));
            }
            
            for (int i = 5; i < population.getPopulationSize(); i++) {
                
                if (random.nextFloat() < (2.0 / 3.0) * population.getIndividuals().get(i).getComfortLevel()) {
                    survivors.add(population.getIndividuals().get(i));
                }
            }

            population.setIndividuals(survivors);
        }
    }

    /**
     * Calculates the time until the next epidemic event for a given individual.
     * Since epidemics are triggered by population size and not by time, this method always returns Float.MAX_VALUE.
     * @param individual the individual for whom the next event time is calculated
     * @return the time until the next epidemic event (always Float.MAX_VALUE)
     */  
    @Override
    public float getNextEventTime(Individual individual) {
        return Float.MAX_VALUE;
    }
}
