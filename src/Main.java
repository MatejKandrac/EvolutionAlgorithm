import implementation.GeneticAlgorithm;
import models.Configuration;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration configuration = Configuration.loadConfiguration("map.txt");
        Scanner sc = new Scanner(System.in);

        do {
            runSimulation(configuration);
            System.out.println("Type END to finish");
        } while (!sc.nextLine().equals("END"));

        System.out.println("TERMINATING");
    }

    static void runSimulation(Configuration configuration) {
        GeneticAlgorithm algorithm = new GeneticAlgorithm(configuration);
        int[] fitnessData = algorithm.launchSimulation();

        SwingUtilities.invokeLater(() -> {
            Visualizer mainPanel = new Visualizer(fitnessData);
            JFrame frame = new JFrame("Results");
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }

}