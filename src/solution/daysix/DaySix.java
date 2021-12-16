package solution.daysix;

import util.AbstractSolution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DaySix extends AbstractSolution {
    private final List<Integer> fish;

    private final HashMap<Integer, BigInteger> map;

    public DaySix(String filename) {
        loadInput(filename);
        fish = new ArrayList<>();
        map = new HashMap<>();
        mapInputToFish();
        prepareMap();
    }

    public BigInteger partOne() {
        return simulateForDays(80);
    }

    public BigInteger partTwo() {
        return simulateForDays(256);
    }

    private void prepareMap() {
        for (int i=0; i<9; i++) {
            map.put(i, BigInteger.valueOf(0));
        }
        for (Integer i : fish) {
            BigInteger curr = map.get(i);

            map.replace(i, curr.add(BigInteger.valueOf(1)));
        }
    }

    private BigInteger simulateForDays(int days) {
        final HashMap<Integer, BigInteger> mapCopy = new HashMap<>(map);
        for (int i=0; i<days; i++) {
            BigInteger zeros = mapCopy.get(0);
            BigInteger[] v = new BigInteger[9];
            mapCopy.values().toArray(v);
            for (int j=1; j<9;j++) {
                BigInteger curr = v[j];
                mapCopy.replace(j, BigInteger.valueOf(0));
                mapCopy.replace(j - 1,curr);
            }
            BigInteger sixes = mapCopy.get(6);
            mapCopy.replace(6, sixes.add(zeros));
            mapCopy.replace(8, zeros);
        }
        return mapCopy.values().stream().reduce(BigInteger.valueOf(0), (acc, curr) -> acc = acc.add(curr));
    }

    private void mapInputToFish() {
        for (String s : input.get(0).split(",")) {
            fish.add(Integer.parseInt(s));
        }
    }

    public static void main(String[] args) {
        DaySix d = new DaySix("input.txt");
        System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
