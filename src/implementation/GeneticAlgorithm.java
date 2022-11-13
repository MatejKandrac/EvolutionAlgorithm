package implementation;

import models.Configuration;
import models.Gene;

import java.util.*;

public class GeneticAlgorithm {

    private final Random rn = new Random();
    private final VirtualMachine virtualMachine;

    public GeneticAlgorithm(Configuration configuration) {
        virtualMachine = new VirtualMachine(configuration);
    }
    private Gene tournament(List<Gene> population) {
        LinkedList<Gene> priorityQueue = new LinkedList<>();
        for (int i = 0; i < Configuration.TOURNAMENT_CHILD_COUNT; i++) {
            priorityQueue.add(population.get(rn.nextInt(Configuration.GENERATION_SIZE)));
        }
        priorityQueue.sort(Comparator.comparingDouble(Gene::getFitness));
        return priorityQueue.getLast();
    }

    private short[] crossover(Gene one, Gene two) {
        int splitIndex = rn.nextInt(Configuration.MEMORY_SIZE);
        short[] newData = new short[Configuration.MEMORY_SIZE];
        for (int i = 0; i < Configuration.MEMORY_SIZE; i++) {
            if (i < splitIndex) newData[i] = one.getData()[i];
            else newData[i] = two.getData()[i];
        }
        return newData;
    }

    private Gene mutate(short[] geneData) {
        if (rn.nextInt(100) < Configuration.MUTATION_CHANCE * 100) {
            geneData[rn.nextInt(Configuration.MEMORY_SIZE)] = (short) rn.nextInt(255);
        }
        return new Gene(Integer.MIN_VALUE, geneData);
    }

    private Gene getNewChild(List<Gene> population) {
        Gene one = tournament(population);
        Gene two = tournament(population);
        short[] crossedOver = crossover(one, two);
        return mutate(crossedOver);
    }

    public int[] launchSimulation() {
        // Initialize fields
        int[] fitnessData = new int[Configuration.GENERATIONS];
        final int keepCount = (int) (Configuration.GENERATION_SIZE * Configuration.KEEP_ELITES_PERCENTAGE);
        List<Gene> ratedPopulation = new LinkedList<>();
        Queue<Gene> population = new ArrayDeque<>();

        // Create random population
        populateRandom(population);

        for (int i = 0; i < Configuration.GENERATIONS; i++) {

            for (int j = 0; j < Configuration.GENERATION_SIZE; j++) {
                Gene original = population.poll();
                if (original.getFitness() == Integer.MIN_VALUE){
                    Gene currentGene = original.copy();
                    virtualMachine.launch(currentGene);
                    original.setFitnessData(currentGene.getFitness(), currentGene.getPath());
                }
                ratedPopulation.add(original);
            }

            // Keep best
            ratedPopulation.sort(Comparator.comparingDouble(Gene::getFitness));

            for (int j = 0; j < keepCount; j++) {
                population.add(ratedPopulation.get(ratedPopulation.size()-1-j));
            }

            // Save best fitness
            fitnessData[i] = ratedPopulation.get(ratedPopulation.size()-1).getFitness();

            while (population.size() < Configuration.GENERATION_SIZE) {
                population.add(getNewChild(ratedPopulation));
            }
            ratedPopulation.clear();
        }
        System.out.println(population.poll());
        return fitnessData;
    }

    void populateRandom(Queue<Gene> population) {
        for (int i = 0; i < Configuration.GENERATION_SIZE; i++) {
            population.add(new Gene());
        }
    }

}
