package StochasticSimulation;

import java.util.List;
import java.util.Random;

public class ImprovedIndividual extends Individual {
    public ImprovedIndividual(int[][] matrix, Random random) {
        super(matrix, random);
    }

    public ImprovedIndividual(int[][] matrix, List<List<Integer>> distribution) {
        super(matrix, distribution);
    }

    @Override
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
    }

    public float calculateDiversityScore() {
        float diversityScore = 0.0f;
        for (List<Integer> patrol : distribution) {
            diversityScore += patrol.size();
        }
        return diversityScore / distribution.size();
    }
}
