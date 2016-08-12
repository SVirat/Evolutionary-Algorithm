/**
 * A program that converts any random string into a target string, and defines
 * the time taken to achieve it.
 * 
 * @author Virat Singh, svirat@gmail.com
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class EvolutionaryAlgorithm {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		GeneticBound gene = new GeneticBound();
		//the random values creating the first string
		Random rand = gene.rand;

		//the number of generations after which log is updated
		final int GENERATION_STEP = 100;
		//the current generation
		int generation = gene.getGeneration();
		//the population of chromosomes
		ArrayList<Chromosome> population = gene.getPopulation();

		//Target string to convert random string into
		System.out.print("Enter the target word: " );
		String target = input.nextLine();
		gene.setTarget(target);

		//creating the list of random chromosomes comprising the population
		for (int i = 0; i < gene.DEFAULT_SIZE; i++) {
			String data = gene.randomString(target.length());
			gene.getPopulation().add(new Chromosome(data,
					gene.fitness(target, data)));
		}

		//keep converting until the random string is the same as target string
		while (population.get(0).getFitness() != 0) {
			gene.getLog().add("Generation " + generation + ": " 
					+ gene.getPopulation().get(0));
			//update the log after certain number of generations
			if (generation % GENERATION_STEP == 0)
				System.out.println("Generation " + generation + ": fittest  = "
						+ population.get(0));
			//update the chromosomes to change the random string
			for (int x = 0; x < gene.DEFAULT_SIZE; x++) {
				Chromosome parent = population.get(rand.nextInt(population
						.size()));
				Chromosome child = parent.breed(population.get(
						gene.weightedRandom(new double[] { 
								20.0, 5.0, 2.0, 1.0 }, 99)));
				child.mutate();
				gene.newGeneration.add(child);
			}
			//update generation and population
			generation++;
			population = gene.newGeneration;
			gene.setGeneration(new ArrayList<Chromosome>(gene.DEFAULT_SIZE));
			Collections.sort(population);
			//once the target string is acquired, exit
			if (population.get(0).getFitness() == 0) {
				System.out.println("Generation " + generation + ": fittest  = "
						+ population.get(0));
				return;
			}
		}
		input.close();	
	}

}
