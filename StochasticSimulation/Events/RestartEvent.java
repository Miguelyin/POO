package StochasticSimulation.Events;

import StochasticSimulation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RestartEvent implements EvolutionEvent {
    private int Gr;
    private int countmak;

    public RestartEvent(int Gr) {
        this.Gr = Gr;
        this.countmak = 0;
    }

    @Override
    public void execute(Population population, Random random) {
        if (countmak > Gr) {
            restartPopulation(population, random);
            countmak = 0;
        }
    }

    private void restartPopulation(Population population, Random random) {
        List<Individual> individuals = population.getIndividuals();
        Collections.sort(individuals);

        int skip = (int) (individuals.size() * 0.2);
        int replaceCount = (int) (individuals.size() * 0.8);

        List<Individual> newIndividuals = new ArrayList<>(individuals.subList(0, skip));
        List<Individual> bestIndividuals = new ArrayList<>(newIndividuals);

        for (int i = 0; i < replaceCount; i++) {
            Individual newIndividual;
            if (i < replaceCount * 0.5) {
                newIndividual = shiftMutation(bestIndividuals.get(random.nextInt(skip)), random);
            } else if (i < replaceCount * 0.75) {
                newIndividual = new Individual(population.getIndividuals().get(0).getMatrix(), random);
            } else {
                newIndividual = randomNewIndividual(population.getIndividuals().get(0).getMatrix(), random);
            }
            newIndividuals.add(newIndividual);
        }

        population.setIndividuals(newIndividuals);
    }

    private Individual shiftMutation(Individual individual, Random random) {
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

        return new Individual(individual.getMatrix(), newDistribution);
    }

    private Individual randomNewIndividual(int[][] matrix, Random random) {
        return new Individual(matrix, random);
    }

    @Override
    public float getNextEventTime(Individual individual) {
        return 1;
    }
}
