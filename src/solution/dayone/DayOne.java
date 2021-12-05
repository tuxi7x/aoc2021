package solution.dayone;

import util.AbstractSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class DayOne extends AbstractSolution {
    private final List<Integer> numbers = new ArrayList<>();

    public DayOne(String filename) {
        loadInput(filename);
        mapInputToInteger();
    }

    public int partOne() {
        return numberOfGreaterNextElement(numbers);
    }

    public int partTwo() {
        return numberOfGreaterNextElement(produceThreeSums());
    }

    private List<Integer> produceThreeSums() {
        List<Integer> threeSums = new ArrayList<>();
        for (int i=0; i<numbers.size(); i++) {
            if (i>1) {
                threeSums.add((numbers.get(i) + numbers.get(i-1) + numbers.get(i-2)));
            }
        }
        return threeSums;
    }

    private int numberOfGreaterNextElement(List<Integer> list) {
        int sum = 0;
        for (int i=0; i<list.size(); i++) {
            if (i>0 && list.get(i) > list.get(i-1)) {
                sum++;
            }
        }
        return sum;
    }

    private void mapInputToInteger() {
        numbers.addAll(input.stream().map(Integer::parseInt).collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        DayOne d = new DayOne("input.txt");
        System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
