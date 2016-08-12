/**
 * The class that handles the mutation of the random string into the given one.
 * This is done in steps to achieve the best fitness.
 * 
 * @author Virat Singh, svirat@gmail.com
 */

import java.util.Random;

class Chromosome extends GeneticBound implements Comparable<Chromosome> {
	
	//the list of characters between random and target strings
	private char[] data;
	//the number determining how close the random string is to the target
	private int fitness;

	//how quickly the random string is being converted
	private static final double MUTATION_RATE = 0.05;
	//how quickly characters will be picked for conversion
	private static final double CROSSOVER_RATE = 0.9;
	//how many characters will remain constant
	private static final double ASEXUAL_RATE = 0.15;

	private static Random rand = new Random();

	/**
	 * Constructor to create the chromosome
	 * @param data the current string
	 * @param fitness current fitness
	 */
	public Chromosome(String data, int fitness) {
		this.data = data.toCharArray();
		this.fitness = fitness;
	}

	/**
	 * Constructor to create chromosome using random values
	 * @param data the current string
	 * @param fitness current fitness
	 */
	public Chromosome(char[] data, int fitness) {
		this.data = data;
		this.fitness = fitness;
	}

	/**
	 * Converting the random characters closer to the target characters
	 */
	public void mutate() {
		if (Chromosome.rand.nextDouble() < MUTATION_RATE) {
			int index = Chromosome.rand.nextInt(this.data.length);
			int replacementIndex = Chromosome.rand
					.nextInt(validCharacters.length());
			this.data[index] = validCharacters.charAt(replacementIndex);
		}
	}

	/**
	 * Checks which characters have been successfully converted, using the
	 * previous random string
	 * @param other the parent string chromosome
	 * @return the child string chromosome
	 */
	public Chromosome breed(Chromosome other) {
		Chromosome child = null;

		if (Chromosome.rand.nextDouble() < CROSSOVER_RATE) {
			int crossoverPoint = Chromosome.rand.nextInt(data.length);
			char[] childData = new char[data.length];

			for (int i = 0; i < crossoverPoint; i++) {
				childData[i] = this.data[i];
			}

			for (int i = crossoverPoint; i < data.length; i++) {
				childData[i] = other.getDataArray()[i];
			}

			int fitness = fitness(new String(childData), getTarget());

			child = new Chromosome(childData, fitness);
		} else {
			if (Chromosome.rand.nextDouble() < ASEXUAL_RATE) {
				child = new Chromosome(this.getData(), this.fitness);
			} else {
				child = new Chromosome(other.getData(), other.getFitness());
			}
		}
		return child;
	}

	/**
	 * Accessor for the data
	 * @return data
	 */
	public String getData() {
		return new String(data);
	}

	/**
	 * Accessor for the character array data
	 * @return data
	 */
	public char[] getDataArray() {
		return data;
	}

	/**
	 * Accessor for the chromosome's fitness
	 * @return
	 */
	public int getFitness() {
		return fitness;
	}

	@Override
	public int compareTo(Chromosome o) {
		return Integer.compare(this.fitness, o.getFitness());
	}

	@Override
	public String toString() {
		return (new String(data)) + " (Fitness: " + fitness + ")";
	}
	
}
