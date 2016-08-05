/**
 * The class that handles whether the random string has the fitness of the
 * given string.
 * 
 * @author Virat Singh, svirat@gmail.comW
 */

import java.util.ArrayList;
import java.util.Random;

public class GeneticBound {

	//creating a random set of numbers to create the random string
	public Random rand = new Random();

	//the list of all valid characters in the random string
	protected static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !.,?";
	//the string to be converted to
	private static String target = "Hello, world!";

	//the default increments of generations
	public static final int DEFAULT_SIZE = 100;

	//the population of chromosomes
	public static ArrayList<Chromosome> population =
			new ArrayList<Chromosome>(DEFAULT_SIZE);
	//the new set of chromosomes
	public ArrayList<Chromosome> newGeneration = 
			new ArrayList<Chromosome>(DEFAULT_SIZE);
	//the list of changes
	private static ArrayList<String> log = 
			new ArrayList<String>(DEFAULT_SIZE * 10);

	//the current generation
	private static int generation = 0;

	/**
	 * The accessor of the target string
	 * @return target string
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * The accessor of population
	 * @return population
	 */
	public ArrayList<Chromosome> getPopulation() {
		return population;
	}

	/**
	 * The mutator of the population
	 * @param arr the new list of chromosomes for the population
	 */
	public void setPopulation(ArrayList<Chromosome> arr) {
		population = arr;
	}

	/**
	 * The mutator of the next generation
	 * @param arr the new list of chromosomes for the next generation
	 */
	public void setGeneration(ArrayList<Chromosome> arr) {
		newGeneration = arr;
	}

	/**
	 * The accessor for  the list of changes in the population
	 * @return log list of changes in population
	 */
	public ArrayList<String> getLog() {
		return log;
	}

	/**
	 * The accessor for the current generation number
	 * @return current generation
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * Mates the current population with child to produce a result closer to
	 * the target string
	 * 
	 * @param weights the list of values to handle mating
	 * @param max the greatest limit of generation before reseting
	 * @return the next random values, closer to required fitness
	 */
	public int weightedRandom(double[] weights, int max) {
		double sum = 0;
		for (double weight : weights) {
			sum += weight;
		}

		if (sum > 1) {
			double s2 = 0.0;
			for (int i = 0; i < weights.length; i++) {
				double adjusted = weights[i] / sum;
				s2 += adjusted;
				weights[i] = s2;
			}
		}

		double start = rand.nextDouble();
		int step = max / weights.length;

		for (int i = 0; i < weights.length; i++) {
			double weight = weights[i];
			int lBound = step * i;
			int uBound = lBound + step;
			if (start <= weight) {
				return rand.nextInt((uBound - lBound) + 1) + lBound;
			}
		}
		return -1;
	}

	/**
	 * Creates the random string using the legal alphabets
	 * @param length the length of the random string
	 * @return the random string
	 */
	public String randomString(int length) {
		StringBuilder word = new StringBuilder();
		for (int i = 0; i < length; i++) {
			word.append(alphabet.charAt(rand.nextInt(alphabet.length())));
		}
		return word.toString();
	}

	/**
	 * Calculates the fitness of the random string with the target string
	 * If fitness is 0, the random string has been successfully converted
	 * @param s1 the random string
	 * @param s2 the target string
	 * @return fitness
	 */
	public int fitness(String s1, String s2) {
		int type = 1;

		if (type == 0) {
			if (s1.length() != s2.length()) {
				return -1;
			}

			int fitness = 0;
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) != s2.charAt(i))
					fitness++;
			}
			return fitness;
		} else if (type == 1) {
			if (s1.length() != s2.length()) {
				return -1;
			}

			int fitness = 0;
			for (int i = 0; i < s1.length(); i++) {
				fitness += Math.abs(alphabet.indexOf(s1.charAt(i))
						- alphabet.indexOf(s2.charAt(i)));
			}
			return fitness;
		} else {
			return -1;
		}

	}

}