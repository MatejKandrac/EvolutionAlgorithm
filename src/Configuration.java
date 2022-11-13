import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {

    public static final int GENERATIONS = 2000;
    public static final int GENERATION_SIZE = 100;
    public static final int TREASURE_FITNESS_MULTIPLIER = 50;
    public static final double KEEP_ELITES_PERCENTAGE = 0.1; //%
    public static final double MUTATION_CHANCE = 0.5; //%
    public static final int MAX_STEPS = 500;
    public static final int MEMORY_SIZE = 64;
    public static final int TOURNAMENT_CHILD_COUNT = 5;

    private final Position[] treasures;
    private final int mapSize;
    private final int treasuresCount;
    private final Position startPosition;

    public Configuration(Position[] treasures, int mapSize, int treasuresCount, Position startPosition) {
        this.treasures = treasures;
        this.mapSize = mapSize;
        this.treasuresCount = treasuresCount;
        this.startPosition = startPosition;
    }

    public Position[] getTreasures() {
        return treasures;
    }

    public int getMapSize() {
        return mapSize;
    }

    public int getTreasuresCount() {
        return treasuresCount;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    static Configuration loadConfiguration(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int size = Integer.parseInt(br.readLine());
        Position start = null;
        List<Position> treasures = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String line = br.readLine();
            for (int j = 0; j < size; j++) {
                if (line.charAt(j) == 'x') {
                    treasures.add(new Position(j, i));
                } else if (line.charAt(j) == 's') {
                    start = new Position(j, i);
                }
            }
        }
        br.close();
        Position[] treasuresArr = new Position[treasures.size()];
        return new Configuration(treasures.toArray(treasuresArr), size, treasures.size(), start);
    }
}
