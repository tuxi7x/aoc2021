package solution.dayseven;

import solution.daysix.DaySix;
import util.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

public class DaySeven extends AbstractSolution {

    private final List<Integer> positions;

    public DaySeven(String filename) {
        loadInput(filename);
        positions = new ArrayList<>();
        mapInputToNumbers();
    }

    public int partOne() {
        return totalDistanceFromPosition(findSmallestDistance());
    }

    public int partTwo() {
        return totalDistanceFromPosition2(findSmallestDistance2());
    }

    private int totalDistanceFromPosition(int position) {
        return positions.stream().reduce(0, (acc, curr) -> acc += (Math.abs(curr - position)));
    }

    private int findSmallestDistance() {
        int max = positions.stream().reduce(0, (acc, curr) -> acc > curr ? acc : curr);
        int smallest = totalDistanceFromPosition(0);
        for (int i=1; i<max; i++) {
            if (totalDistanceFromPosition(smallest) > totalDistanceFromPosition(i)) {
                smallest = i;
            }
        }
        return smallest;
    }

    private int totalDistanceFromPosition2(int position) {
        int sum = 0;
        for (Integer integer : positions) {
            for (int j = 0; j < Math.abs(position - integer) + 1; j++) {
                sum += j;
            }
        }
        return sum;
    }

    private int findSmallestDistance2() {
        int max = positions.stream().reduce(0, (acc, curr) -> acc > curr ? acc : curr);
        int smallest = totalDistanceFromPosition2(0);
        for (int i=1; i<max; i++) {
            if (totalDistanceFromPosition2(smallest) > totalDistanceFromPosition2(i)) {
                smallest = i;
            }
        }
        return smallest;
    }

    private void mapInputToNumbers() {
        for (String s : input.get(0).split(",")) {
            positions.add(Integer.parseInt(s));
        }
    }

    public static void main(String[] args) {
        DaySeven d = new DaySeven("input.txt");
        // System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
