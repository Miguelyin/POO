package StochasticSimulation;

/**
 * This abstract class represents an observer in a stochastic simulation.
 *
 * The Observer class defines an abstract method update which must be implemented
 * by subclasses to handle updates during the simulation. Observers can be used
 * to monitor the state of the simulation at various points.
 *
 * @version 1.0
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 */
public abstract class Observer {
    
    /**
     * This method is called to update the observer with the current state of the simulation.
     *
     * @param observationNumber the number of the observation
     * @param currentTime the current time in the simulation
     * @param eventCount the number of events that have occurred
     * @param epidemicCount the number of epidemics that have occurred
     * @param population the current population in the simulation
     */
    public abstract void update(int observationNumber, float currentTime, int eventCount, int epidemicCount, Population population);
    public abstract void update_restart_counter(int restartNum);
}
