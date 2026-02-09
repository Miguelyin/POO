package StochasticSimulation;

public class ImprovedSimulationObserver extends SimulationObserver {
    private int restartCounter = 0;

    @Override
    public void update(int observationNumber, float currentTime, int eventCount, int epidemicCount, Population population) {
        super.update(observationNumber, currentTime, eventCount, epidemicCount, population);
        System.out.println("                Number of restarts: " + restartCounter);
    }

    @Override
    public void update_restart_counter(int numRestarts) {
        this.restartCounter = numRestarts;
    }
}
