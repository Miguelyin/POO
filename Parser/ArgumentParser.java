package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class represents a parser for command-line arguments and input files
 * to configure simulation parameters.
 * The ArgumentParser class provides methods to interpret arguments for
 * random matrix generation or file-based matrix input, and generates the
 * appropriate SimulationParameters object.
 *
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 * @version 1.0
 */
public class ArgumentParser {

    /**
     * This method reads the command-line arguments and parses them into a
     * SimulationParameters object.
     *
     * @param args the command-line arguments passed to the program
     * @return a SimulationParameters object if parsing is successful, or null if it fails
     */
    public static SimulationParameters parseArguments(String[] args) {
        
        if (args.length < 1) {
            System.out.println("Insufficient number of arguments.");
            return null;
        }

        boolean improved = false;
        if (args[args.length - 1].equals("--improved")) {
            improved = true;
            args = java.util.Arrays.copyOf(args, args.length - 1); // Remove the last element
        }

        SimulationParameters params = null;
        if (args[0].equals("-r")) {
            params = parseRandomMatrixArguments(args);
        } else if (args[0].equals("-f")) {
            if (args.length != 2) {
                System.out.println("Invalid number of arguments for file input.");
                return null;
            }
            params = parseFileInputArguments(args[1]);
        } else {
            System.out.println("Invalid command.");
            return null;
        }

        if (params != null) {
            params.setImproved(improved);
        }

        return params;
    }


    /**
     * This method parses arguments for generating a random matrix.
     *
     * @param args the command-line arguments specifying random matrix parameters
     * @return a SimulationParameters object if parsing is successful, or null if it fails
     */
    private static SimulationParameters parseRandomMatrixArguments(String[] args) {
        
        if (args.length < 9) {
            System.out.println("Insufficient number of arguments for random matrix.");
            return null;
        }

        int n, m, tau, v, vMax, mu, rho, delta;
        
        try {
            //Parses each argument as an integer
            n = Integer.parseInt(args[1]);  //Number n of patrols
            m = Integer.parseInt(args[2]);  //Number m of planetary systems
            tau = Integer.parseInt(args[3]);    //Final instant of evolution tau
            v = Integer.parseInt(args[4]);  //Initial population v
            vMax = Integer.parseInt(args[5]);   //Maximum population vMax
            mu = Integer.parseInt(args[6]); //Parameter mu of Death
            rho = Integer.parseInt(args[7]);    //Parameter rho of Reproduction
            delta = Integer.parseInt(args[8]);  //Parameter delta of Mutation
            
            //Verifies the arguments
            if (n <= 0 || m <= 0) {
                System.out.println("Number of patrols and planetary systems must be positive.");
                return null;
            }
            if (tau <= 0) {
                System.out.println("Final instant of evolution (τ) must be greater than 0.");
                return null;
            }
            if (v >= vMax) {
                System.out.println("Initial population (v) must be less than maximum population (vMax).");
                return null;
            }
            if (mu < 0 || rho < 0 || delta < 0) {
                System.out.println("Parameters µ, ρ, δ must be greater than zero.");
                return null;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid format for numerical parameters.");
            return null;
        }

        // Generate a random matrix
        MatrixGenerator generator = new MatrixGenerator();
        int[][] matrix = generator.generateRandomMatrix(n, m, 10); // Using 10 as a placeholder max value

        return new SimulationParameters(n, m, tau, v, vMax, mu, rho, delta, matrix);
    }



    /**
     * This method parses arguments from a file to create a SimulationParameters object.
     *
     * @param filePath the path to the input file containing the simulation parameters and matrix
     * @return a SimulationParameters object if parsing is successful, or null if it fails
     */
    private static SimulationParameters parseFileInputArguments(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            
            if (line == null) {
                System.out.println("Input file is empty.");
                return null;
            }

            String[] params = line.trim().split("\\s+");
            
            if (params.length != 8) {
                System.out.println("Invalid number of parameters in the first line of the file.");
                return null;
            }

            int n, m, tau, v, vMax, mu, rho, delta;
            
            try {
                n = Integer.parseInt(params[0]);
                m = Integer.parseInt(params[1]);
                tau = Integer.parseInt(params[2]);
                v = Integer.parseInt(params[3]);
                vMax = Integer.parseInt(params[4]);
                mu = Integer.parseInt(params[5]);
                rho = Integer.parseInt(params[6]);
                delta = Integer.parseInt(params[7]);

                if (n <= 0 || m <= 0) {
                    System.out.println("Number of patrols and planetary systems must be positive.");
                    return null;
                }
                if (tau <= 0) {
                    System.out.println("Final instant of evolution (τ) must be greater than 0.");
                    return null;
                }
                if (v >= vMax) {
                    System.out.println("Initial population (v) must be less than maximum population (vMax).");
                    return null;
                }
                if (mu < 0 || rho < 0 || delta < 0) {
                    System.out.println("Parameters µ, ρ, δ must be greater than zero.");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid format for numerical parameters in the first line.");
                return null;
            }

            int[][] matrix = new int[n][m];
            
            for (int i = 0; i < n; i++) {
                line = br.readLine();
                
                if (line == null) {
                    System.out.println("Insufficient number of matrix rows in the input file.");
                    return null;
                }

                String[] matrixRow = line.trim().split("\\s+");
                
                if (matrixRow.length != m) {
                    System.out.println("Invalid number of elements in matrix row " + (i + 1) + ".");
                    return null;
                }

                try {
                    for (int j = 0; j < m; j++) {
                        matrix[i][j] = Integer.parseInt(matrixRow[j]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for matrix elements in row " + (i + 1) + ".");
                    return null;
                }
            }

            return new SimulationParameters(n, m, tau, v, vMax, mu, rho, delta, matrix);

        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
            return null;
        }
    }
}
