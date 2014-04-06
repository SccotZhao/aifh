package com.heatonresearch.aifh.examples.operations;

import com.heatonresearch.aifh.evolutionary.population.BasicPopulation;
import com.heatonresearch.aifh.evolutionary.population.Population;
import com.heatonresearch.aifh.evolutionary.train.EvolutionaryAlgorithm;
import com.heatonresearch.aifh.evolutionary.train.basic.BasicEA;
import com.heatonresearch.aifh.genetic.genome.DoubleArrayGenome;
import com.heatonresearch.aifh.genetic.genome.DoubleArrayGenomeFactory;
import com.heatonresearch.aifh.genetic.genome.IntegerArrayGenome;
import com.heatonresearch.aifh.genetic.genome.IntegerArrayGenomeFactory;
import com.heatonresearch.aifh.genetic.mutate.MutatePerturb;
import com.heatonresearch.aifh.genetic.mutate.MutateShuffle;
import com.heatonresearch.aifh.learning.MLMethod;
import com.heatonresearch.aifh.learning.score.CalculateScore;
import com.heatonresearch.aifh.randomize.GenerateRandom;
import com.heatonresearch.aifh.randomize.MersenneTwisterGenerateRandom;

import java.util.Arrays;

/**
 * This example shows how two different mutate operators create an offspring from a genome.
 *
 * Sample output from this example:
 *
 * Mutate shuffle
 * Parent: [1, 2, 3, 4, 5]
 * Offspring: [1, 3, 2, 4, 5]
 * Mutate peterb
 * Parent: [1.0, 2.0, 3.0, 4.0, 5.0]
 * Offspring: [0.9684564148017776, 2.0231188741090955, 3.200690276405833, 4.050125858385886, 4.531099177190473]
 */
public class MutateExample {

    /**
     * Demonstrate the mutate shuffle operator.  An offspring will be created by swapping two
     * individual genes.
     */
    public static void mutateShuffle() {
        System.out.println("Mutate shuffle");

        // Create a random number generator
        GenerateRandom rnd = new MersenneTwisterGenerateRandom();

        // Create a new population.
        Population pop = new BasicPopulation();
        pop.setGenomeFactory(new IntegerArrayGenomeFactory(5));

        // Create a trainer with a very simple score function.  We do not care
        // about the calculation of the score, as they will never be calculated.
        EvolutionaryAlgorithm train = new BasicEA(pop,new CalculateScore() {
            @Override
            public double calculateScore(MLMethod method) {
                return 0;
            }

            @Override
            public boolean shouldMinimize() {
                return false;
            }

            @Override
            public boolean requireSingleThreaded() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        // Create a shuffle operator.  Use it 1.0 (100%) of the time.
        MutateShuffle opp = new MutateShuffle();
        train.addOperation(1.0,opp);

        // Create a single parent, the genes are set to 1,2,3,4,5.
        IntegerArrayGenome[] parents = new IntegerArrayGenome[1];
        parents[0] = (IntegerArrayGenome) pop.getGenomeFactory().factor();
        for(int i=1;i<=5;i++) {
            parents[0].getData()[i-1] = i;
        }

        // Create an array to hold the offspring.
        IntegerArrayGenome[] offspring = new IntegerArrayGenome[1];
        offspring[0] = new IntegerArrayGenome(5);

        // Perform the operation
        opp.performOperation(rnd,parents,0,offspring,0);

        // Display the results
        System.out.println("Parent: " + Arrays.toString(parents[0].getData()));
        System.out.println("Offspring: " + Arrays.toString(offspring[0].getData()));

    }

    /**
     * Demonstrate the mutate peterb operator.  An offspring will be created by randomly changing each
     * gene.
     */
    public static void mutatePeterb() {
        System.out.println("Mutate peterb");

        GenerateRandom rnd = new MersenneTwisterGenerateRandom();

        // Create a new population.
        Population pop = new BasicPopulation();
        pop.setGenomeFactory(new DoubleArrayGenomeFactory(5));

        // Create a trainer with a very simple score function.  We do not care
        // about the calculation of the score, as they will never be calculated.
        EvolutionaryAlgorithm train = new BasicEA(pop,new CalculateScore() {
            @Override
            public double calculateScore(MLMethod method) {
                return 0;
            }

            @Override
            public boolean shouldMinimize() {
                return false;
            }

            @Override
            public boolean requireSingleThreaded() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });


        MutatePerturb opp = new MutatePerturb(0.1);
        train.addOperation(1.0,opp);


        // Create a peterb operator.  Use it 1.0 (100%) of the time.
        DoubleArrayGenome[] parents = new DoubleArrayGenome[1];
        parents[0] = (DoubleArrayGenome) pop.getGenomeFactory().factor();
        parents[0].setPopulation(pop);

        for(int i=1;i<=5;i++) {
            parents[0].getData()[i-1] = i;
        }

        // Create an array to hold the offspring.
        DoubleArrayGenome[] offspring = new DoubleArrayGenome[1];
        offspring[0] = new DoubleArrayGenome(5);

        // Perform the operation
        opp.performOperation(rnd,parents,0,offspring,0);

        // Display the results
        System.out.println("Parent: " + Arrays.toString(parents[0].getData()));
        System.out.println("Offspring: " + Arrays.toString(offspring[0].getData()));

    }

    public static void main(String[] args) {
        mutateShuffle();
        mutatePeterb();
    }
}
