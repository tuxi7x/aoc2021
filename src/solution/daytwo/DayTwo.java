package solution.daytwo;

import util.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

public class DayTwo extends AbstractSolution {
    private final List<Path> path = new ArrayList<>();

    private enum Direction {
        forward, down, up
    }
    private static class Path {
        private final Direction direction;
        private final int distance;

        public Path(Direction direction, int distance) {
            this.direction = direction;
            this.distance = distance;
        }

        public Direction getDirection() {
            return direction;
        }

        public int getDistance() {
            return distance;
        }
    }

    public DayTwo(String filename) {
        loadInput(filename);
        mapInputToPath();
    }


    public int partOne() {
        int horizontal = 0;
        int depth = 0;
        for (Path p : path) {
            switch (p.direction) {
                case forward -> horizontal += p.distance;
                case up -> depth -= p.distance;
                case down -> depth += p.distance;
            }
        }
        // int horizontal = path.stream().filter(e -> e.direction == Direction.forward).mapToInt(e -> e.distance).reduce(0, (acc,curr) -> acc += curr);
        // int depth = path.stream().filter(e -> e.direction != Direction.forward).mapToInt(e -> e.direction == Direction.up ? -e.distance : e.distance).reduce(0, (acc,curr) -> acc += curr);
        return horizontal * depth;
    }

    public int partTwo() {
        int horizontal = 0;
        int depth = 0;
        int aim = 0;
        for (Path p : path) {
            switch (p.direction) {
                case forward -> {
                    horizontal += p.distance;
                    depth += aim * p.distance;
                }
                case up -> aim -= p.distance;
                case down -> aim += p.distance;
            }
        }
        return horizontal * depth;
    }

    private void mapInputToPath() {
        input.forEach(e -> {
            String[] row = e.split(" ");
            path.add(new Path(Direction.valueOf(row[0]), Integer.parseInt(row[1])));
        });
    }

    public static void main(String[] args) {
        DayTwo d = new DayTwo("input.txt");
        System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
