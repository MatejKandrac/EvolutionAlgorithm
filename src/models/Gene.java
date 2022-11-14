package models;

import java.util.Arrays;
import java.util.Random;

public class Gene {

    private int fitness = Integer.MIN_VALUE;
    private String path = "";
    private int collectedTreasures = 0;
    private short[] data = new short[Configuration.MEMORY_SIZE];

    public Gene(){
        Random rn = new Random();
        for (int i = 0; i < Configuration.MEMORY_SIZE; i++) {
            data[i] = (short) rn.nextInt(255);
        }
    }

    public Gene(int collectedTreasures, int fitness, short[] data) {
        this.collectedTreasures = collectedTreasures;
        this.fitness = fitness;
        this.data = data;
    }
    public Gene copy(){
        return new Gene(0, fitness, Arrays.copyOf(data, Configuration.MEMORY_SIZE));
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

    public void setData(int collectedTreasures, int fitness, String path) {
        this.collectedTreasures = collectedTreasures;
        this.fitness = fitness;
        this.path = path;
    }

    public int getCollectedTreasures() {
        return collectedTreasures;
    }

    @Override
    public String toString() {
        return "PATH: " + path + "\nFITNESS: " + fitness + "\nCollected treasures: " + collectedTreasures;
    }
}
