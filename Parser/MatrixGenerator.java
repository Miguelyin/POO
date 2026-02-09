package Parser;

import java.util.Random;

/**
 * This class represents a generator for random matrices.
 *
 * The MatrixGenerator class provides methods to generate a matrix with
 * specified dimensions and random values within a given range.
 *
 * @version 1.0
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 */
public class MatrixGenerator {
    private final Random random;

    /**
     * Constructor for MatrixGenerator.
     * Initializes a new Random object for generating random numbers.
     */
    public MatrixGenerator() {
        this.random = new Random();
    }

    /**
     * This method generates a random matrix with the specified dimensions
     * and maximum value.
     *
     * @param n the number of rows in the matrix
     * @param m the number of columns in the matrix
     * @param maxValue the maximum value for any element in the matrix
     * @return a 2D array representing the generated random matrix
     */
    public int[][] generateRandomMatrix(int n, int m, int maxValue) {
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = random.nextInt(maxValue) + 1;
            }
        }
        return matrix;
    }
}
