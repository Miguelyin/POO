package StochasticSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Population {
    private List<Individual> individuals;
    private int maxPopulationSize;
    private Individual bestIndividual;
    private Individual bestEverIndividual; // Melhor distribuição de sempre
    private List<Individual> topDistributions;
    private Set<String> allTimeTopDistributionsSet;
    private List<Individual> allTimeTopDistributions;

    public Population(int initialSize, int maxPopulationSize, int[][] matrix, Random random) {
        this.maxPopulationSize = maxPopulationSize;
        this.individuals = new ArrayList<>();
        this.topDistributions = new ArrayList<>();
        this.allTimeTopDistributionsSet = new HashSet<>();
        this.allTimeTopDistributions = new ArrayList<>();

        for (int i = 0; i < initialSize; i++) {
            addIndividual(new Individual(matrix, random));
        }
        updateTopDistributions();
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
        sortIndividuals();
        updateTopDistributions();
    }

    public int getPopulationSize() {
        return individuals.size();
    }

    public void addIndividual(Individual individual) {
        int index = Collections.binarySearch(individuals, individual);
        if (index < 0) {
            index = -index - 1;
        }
        individuals.add(index, individual);
        updateTopDistributions();

        // Atualiza o melhor indivíduo de sempre
        if (bestEverIndividual == null || individual.getComfortLevel() > bestEverIndividual.getComfortLevel()) {
            bestEverIndividual = individual;
        }
    }

    public void removeIndividual(int index) {
        individuals.remove(index);
        updateTopDistributions();
    }

    public void sortIndividuals() {
        Collections.sort(individuals);
    }

    public Individual getBestIndividual() {
        return bestIndividual;
    }

    public Individual getBestEverIndividual() {
        return bestEverIndividual;
    }

    public List<Individual> getTopDistributions() {
        return topDistributions;
    }

    public List<Individual> getAllTimeTopDistributions() {
        return allTimeTopDistributions;
    }

    public int getMaxPopulationSize() {
        return maxPopulationSize;
    }

    private void updateTopDistributions() {
        if (!individuals.isEmpty()) {
            bestIndividual = individuals.get(0);
        }

        Set<String> seenDistributions = new HashSet<>();
        List<Individual> uniqueDistributions = new ArrayList<>();

        for (Individual ind : individuals) {
            String distributionString = SimulationObserver.formatDistribution(ind.getDistribution());
            if (!seenDistributions.contains(distributionString)) {
                seenDistributions.add(distributionString);
                uniqueDistributions.add(ind);
            }
            if (uniqueDistributions.size() == 5) {
                break;
            }
        }
        topDistributions = uniqueDistributions;

        // Atualizar as top 5 distribuições de todos os tempos
        for (Individual ind : topDistributions) {
            String distributionString = SimulationObserver.formatDistribution(ind.getDistribution());
            if (!allTimeTopDistributionsSet.contains(distributionString)) {
                allTimeTopDistributionsSet.add(distributionString);
                allTimeTopDistributions.add(ind);
            }
        }

        Collections.sort(allTimeTopDistributions, (a, b) -> Float.compare(b.getComfortLevel(), a.getComfortLevel()));
        if (allTimeTopDistributions.size() > 5) {
            allTimeTopDistributions = allTimeTopDistributions.subList(0, 5);
        }
    }
}
