package models;

import java.util.Arrays;
import java.util.Random;

public class Gene {

    private int fitness = Integer.MIN_VALUE;
    private String path = "";
    private short[] data = new short[Configuration.MEMORY_SIZE];

    public Gene(){
        Random rn = new Random();
        for (int i = 0; i < Configuration.MEMORY_SIZE; i++) {
            data[i] = (short) rn.nextInt(255);
        }
    }

    public Gene(int fitness, short[] data) {
        this.fitness = fitness;
        this.data = data;
    }
    public Gene copy(){
        return new Gene(fitness, Arrays.copyOf(data, Configuration.MEMORY_SIZE));
    }

    public int getFitness() {
        return fitness;
    }

    public short[] getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public void setFitnessData(int fitness, String path) {
        this.fitness = fitness;
        this.path = path;
    }

    @Override
    public String toString() {
        return "PATH: " + path + "\nFITNESS: " + fitness;
    }
}
