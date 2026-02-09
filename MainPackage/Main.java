package MainPackage;

import StochasticSimulation.*;

import java.util.Random;

import Parser.ArgumentParser;
import Parser.SimulationParameters;


/**
 * This class represents the main entry point of the simulation program.
 *
 * The Main class is responsible for initializing and running the simulation. 
 * It parses command-line arguments, creates necessary objects, and manages 
 * the simulation execution and timing.
 * @version 1.0
 * @author António Jotta, David Martinho, Miguel Silva, Miguel Yin
 */
public class Main {
    /**
     * This method is the main entry point of the program. It initializes the simulation
     * environment, parses arguments, and runs the simulation while tracking its runtime.
     *
     * @param args the command-line arguments passed to the program
     */
    public static void main(String[] args) {
    	
        long startTime = System.currentTimeMillis();

        // Method to parse arguments
        SimulationParameters params = ArgumentParser.parseArguments(args);
        if (params == null) {
            System.out.println("Failed to parse arguments.");
            return;
        }

        // Create random object
        Random random = new Random();
        boolean improved = params.isImproved();
        Simulation simulation = new Simulation(params, random, improved);
        Observer observer = improved ? new ImprovedSimulationObserver() : new SimulationObserver();
        
        simulation.addObserver(observer);
        simulation.run();
        
        long endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;

        System.out.println("Program runtime: " + runtime + " milliseconds");
    }
}
