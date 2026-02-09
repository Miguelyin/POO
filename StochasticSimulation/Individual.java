package StochasticSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents an individual in a stochastic simulation.
 * 
 * The Individual class encapsulates the properties and behaviors of an individual,
 * including the matrix of patrol times, the distribution of systems among patrols,
 * the comfort level, and the optimal patrol time. It implements the Comparable
 * interface to allow comparison based on comfort level.
 *
 * @version 1.0
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 */
public class Individual implements Comparable<Individual> {
    protected int[][] matrix;
    protected List<List<Integer>> distribution;
    protected float comfortLevel;
    protected float optimalPatrolTime;
    protected float tmin;

    /**
     * Constructor for Individual.
     * Initializes the individual with a given matrix and randomizes the initial distribution of systems among patrols.
     *
     * @param matrix the matrix representing patrol times
     * @param random the Random object used for randomizing the distribution
     */
    public Individual(int[][] matrix, Random random) {
        this.matrix = matrix;
        this.distribution = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            distribution.add(new ArrayList<>());
        }
        this.tmin = calculateTmin(matrix);
        randomizeDistribution(random);
        computeMetrics();
    }

    /**
     * Constructor for Individual.
     * Initializes the individual with a given matrix and a specific distribution.
     *
     * @param matrix the matrix representing patrol times
     * @param distribution the specific distribution of systems among patrols
     */ 
    public Individual(int[][] matrix, List<List<Integer>> distribution) {
        this.matrix = matrix;
        this.distribution = distribution;
        this.tmin = calculateTmin(matrix);
        computeMetrics();
    }

    /**
     * This method randomly assigns systems to patrols.
     *
     * @param random the Random object used for randomizing the distribution
     */
    protected void randomizeDistribution(Random random) {
        int numSystems = matrix[0].length;
        int numPatrols = matrix.length;
        for (int system = 0; system < numSystems; system++) {
            int patrol = random.nextInt(numPatrols);
            distribution.get(patrol).add(system);
        }
    }

    /**
     * This method computes the metrics for the individual, including the optimal patrol time and comfort level.
     */
    public void computeMetrics() {
        int numPatrols = matrix.length;
        float[] patrolTimes = new float[numPatrols];

        for (int patrol = 0; patrol < numPatrols; patrol++) {
            float totalTime = 0;
            for (int system : distribution.get(patrol)) {
                totalTime += matrix[patrol][system];
            }
            patrolTimes[patrol] = totalTime;
        }

        float maxPatrolTime = 0;
        for (float time : patrolTimes) {
            if (time > maxPatrolTime) {
                maxPatrolTime = time;
            }
        }

        this.optimalPatrolTime = maxPatrolTime;
        this.comfortLevel = tmin / maxPatrolTime;
        if (this.comfortLevel > 1.0) {
            this.comfortLevel = 1.0f;
        }
    }

    /**
     * This method computes the minimum average time (tmin) by finding the minimum time each system can be handled by any patrol and summing these minimum times.
     *
     * @param matrix the matrix representing patrol times
     * @return the minimum average time (tmin)
     */
    protected float calculateTmin(int[][] matrix) {
        int numPatrols = matrix.length;
        int numSystems = matrix[0].length;
        float tmin = 0;

        for (int system = 0; system < numSystems; system++) {
            float minTime = Float.MAX_VALUE;
            for (int patrol = 0; patrol < numPatrols; patrol++) {
                float time = matrix[patrol][system];
                if (time < minTime) {
                    minTime = time;
                }
            }
            tmin += minTime;
        }

        return tmin / numPatrols;
    }

    /**
     * This method returns the comfort level of the individual.
     *
     * @return the comfort level
     */
    public float getComfortLevel() {
        return comfortLevel;
    }

    /**
     * This method returns the optimal patrol time of the individual.
     *
     * @return the optimal patrol time
     */
    public float getOptimalPatrolTime() {
        return optimalPatrolTime;
    }

    /**
     * This method returns the distribution of systems among patrols.
     *
     * @return the distribution of systems among patrols
     */
    public List<List<Integer>> getDistribution() {
        return distribution;
    }

    /**
     * This method returns the matrix representing patrol times.
     *
     * @return the matrix representing patrol times
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Compares this individual with another based on comfort level.
     *
     * @param other the other individual to compare with
     * @return a negative integer, zero, or a positive integer as this individual's comfort level is less than, equal to, or greater than the specified individual
     */
    @Override
    public int compareTo(Individual other) {
        return Float.compare(other.getComfortLevel(), this.comfortLevel);
    }

    /**
     * Returns a string representation of the individual.
     *
     * @return a string representation of the individual
     */
    @Override
    public String toString() {
        return formatDistribution();
    }

    private String formatDistribution() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (List<Integer> patrol : distribution) {
            sb.append("{");
            for (int system : patrol) {
                sb.append(system + 1).append(",");
            }
            if (!patrol.isEmpty()) {
                sb.setLength(sb.length() - 1);
            }
            sb.append("},");
        }
        if (!distribution.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}
