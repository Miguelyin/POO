package StochasticSimulation;

import StochasticSimulation.Events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Parser.SimulationParameters;

public class Simulation {
    private float tau;
    private float mu;
    private float rho;
    private float delta;
    private int v;
    private int vMax;
    private Population population;
    private List<Observer> observers;
    private Random random;
    private boolean improved;
    private int Gr; // Limite de estagnação
    private int restartCount; // Contador de reinícios

    public Simulation(SimulationParameters params, Random random, boolean improved) {
        this.tau = params.getTau();
        this.mu = params.getMu();
        this.rho = params.getRho();
        this.delta = params.getDelta();
        this.v = params.getV();
        this.vMax = params.getVMax();
        this.random = random;
        this.population = new Population(v, vMax, params.getMatrix(), random);
        this.observers = new ArrayList<>();
        this.improved = improved;
        this.Gr = 10; // Valor padrão para o limite de estagnação
        this.restartCount = 0; // Inicializar contador de reinícios
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(int observationNumber, float currentTime, int eventCount, int epidemicCount) {
        for (Observer observer : observers) {
            observer.update_restart_counter(restartCount);
            observer.update(observationNumber, currentTime, eventCount, epidemicCount, population);
        }
    }

    public void run() {
        float currentTime = 0;
        int eventCount = 0;
        int epidemicCount = 0;
        float observationInterval = tau / 20;
        float nextObservationTime = observationInterval;
        int observationCount = 0;

        List<EvolutionEvent> events = new ArrayList<>();
        events.add(new DeathEvent(mu));
        events.add(new ReproductionEvent(rho));
        events.add(new MutationEvent(delta));
        EpidemicEvent epidemicEvent = new EpidemicEvent(population.getMaxPopulationSize());
        events.add(epidemicEvent);

        RestartEvent restartEvent = new RestartEvent(Gr);

        while (currentTime < tau && population.getPopulationSize() > 0) {
            Individual bestIndividual = population.getBestIndividual();

            if (bestIndividual.getComfortLevel() == 1.0f) {
                observationCount++;
                notifyObservers(observationCount, currentTime, eventCount, epidemicCount);
                return;
            }

            EvolutionEvent nextEvent = null;
            float minEventTime = Float.MAX_VALUE;

            for (EvolutionEvent event : events) {
                float eventTime = event.getNextEventTime(bestIndividual);
                if (eventTime < minEventTime) {
                    minEventTime = eventTime;
                    nextEvent = event;
                }
            }

            if (nextEvent == null) break;

            float nextEventTime = minEventTime;

            while (currentTime + nextEventTime > nextObservationTime && currentTime < tau) {
                float timeToNextObservation = nextObservationTime - currentTime;
                currentTime = nextObservationTime;
                observationCount++;
                notifyObservers(observationCount, currentTime, eventCount, epidemicCount);
                nextObservationTime += observationInterval;
                nextEventTime -= timeToNextObservation;
            }

            currentTime += nextEventTime;
            eventCount++;
            if (currentTime >= tau) {
                break;
            }

            nextEvent.execute(population, random);



            // Check and execute epidemic event if needed
            if (population.getPopulationSize() > vMax) {
                epidemicEvent.execute(population, random);
                epidemicCount++;
            }

            // Trigger Restart event periodically
            if (improved) {
                restartEvent.execute(population, random);
                restartCount++;
            }

            if (population.getPopulationSize() == 0) {
                return;
            }
        }

        if (currentTime < tau) {
            observationCount++;
            notifyObservers(observationCount, tau, eventCount, epidemicCount);
        }
    }
}
