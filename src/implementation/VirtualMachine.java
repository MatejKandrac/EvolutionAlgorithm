package implementation;

import models.Configuration;
import models.Gene;
import models.Position;

public class VirtualMachine {

    private short[] currentMemory;
    private Position currentPosition;
    private int instructionPointer;

    private boolean[] collectedTreasures;
    private String steps = "";
    private int stepCount;

    private final Configuration configuration;

    public VirtualMachine(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setData(short[] data) {
        currentMemory = data;
        collectedTreasures = new boolean[configuration.getTreasuresCount()];
        currentPosition = new Position(configuration.getStartPosition().getX(), configuration.getStartPosition().getY());
        instructionPointer = 0;
        stepCount = 0;
        steps = "";
    }

    void launch(Gene data) {
        setData(data.getData());
        for (int i = 0; i < Configuration.MAX_STEPS; i++) {
            if (!step()) break;
        }
        data.setFitnessData(getFitness(), steps);
    }
    public int getFitness() {
        int base = 0;
        for (boolean collectedTreasure : collectedTreasures) {
            if (collectedTreasure) base += 1;
        }
        return base * Configuration.TREASURE_FITNESS_MULTIPLIER - stepCount;
    }

    private boolean isOffScreen() {
        return  currentPosition.getY() >= configuration.getMapSize() ||
                currentPosition.getX() >= configuration.getMapSize() ||
                currentPosition.getX() < 0 ||
                currentPosition.getY() < 0;
    }

    private boolean collectTreasures() {
        int counter = 0;
        for (int i = 0; i < configuration.getTreasuresCount(); i++) {
            if (configuration.getTreasures()[i].isEqual(currentPosition)) {
                collectedTreasures[i] = true;
            }
            if (collectedTreasures[i]) counter++;
        }
        return counter == configuration.getTreasuresCount();
    }
    public boolean step() {
        short memData = currentMemory[instructionPointer];
        int instruction = memData >> 6;
        int address = memData & 0b111111;
        instructionPointer++;
        switch (instruction) {
            case 0b00: {
                if (currentMemory[address] == 0b11111111)
                    currentMemory[address] = 0;
                else
                    currentMemory[address]++;
                break;
            }
            case 0b01: {
                if (currentMemory[address] == 0)
                    currentMemory[address] = (short) 0b11111111;
                else
                    currentMemory[address]--;
                break;
            }
            case 0b10: {
                instructionPointer = address;
                break;
            }
            case 0b11: {
                int data = currentMemory[address];
                char counter = 0;
                while (data > 0){
                    if ((data & 1) == 1)  counter++;
                    data = data >> 1;
                }
                if (counter <= 2) {
                    steps += "H ";
                    currentPosition.updateY(-1);
                } else if (counter <= 4) {
                    steps += "D ";
                    currentPosition.updateY(1);
                } else if (counter <= 6) {
                    steps += "P ";
                    currentPosition.updateX(1);
                } else {
                    steps += "L ";
                    currentPosition.updateX(-1);
                }
                stepCount++;
                if (isOffScreen()) return false;
                if (collectTreasures()) return false;
            }
            default: {}
        }
        if (instructionPointer == Configuration.MEMORY_SIZE) {
            instructionPointer = 0;
        }
        return true;
    }

}
