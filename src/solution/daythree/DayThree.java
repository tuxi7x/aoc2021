package solution.daythree;

import util.AbstractSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DayThree extends AbstractSolution {

    public DayThree(String filename) {
        loadInput(filename);
    }

    public int partOne() {
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        int zeros;
        int ones;
        for (int i=0; i<input.get(0).length(); i++) {
            zeros = 0;
            ones = 0;
            for (String s : input) {
                switch (s.charAt(i)) {
                    case '0' -> zeros++;
                    case '1' -> ones++;
                }
            }
            if (ones > zeros) {
                gamma.append('1');
                epsilon.append('0');
            } else if (ones < zeros) {
                gamma.append('0');
                epsilon.append('1');
            }
        }
        return Integer.parseInt(gamma.toString(), 2) * Integer.parseInt(epsilon.toString(),2);
    }

    public int partTwo() {
        String oxygen = filteredBinaryNumber(input, true);
        String co2 = filteredBinaryNumber(input, false);
        return Integer.parseInt(oxygen, 2) * Integer.parseInt(co2, 2);
    }

    private String filteredBinaryNumber(List<String> input, boolean isOxygen) {
        List<String> copy = new ArrayList<>(input);
        int ones;
        int zeros;
        for (int i=0; i<copy.get(0).length(); i++) {
            zeros = 0;
            ones = 0;
            for (String s : copy) {
                switch (s.charAt(i)) {
                    case '0' -> zeros++;
                    case '1' -> ones++;
                }
            }
            int finalI = i;
            if (ones > zeros) {
                if (copy.size() > 1) {
                    copy = copy.stream().filter(o -> o.charAt(finalI) == (isOxygen ? '1' : '0')).collect(Collectors.toList());
                }

            } else if (ones < zeros) {
                if (copy.size() > 1) {
                    copy = copy.stream().filter(o -> o.charAt(finalI) == (isOxygen ? '0' : '1')).collect(Collectors.toList());
                }
            } else {
                if (copy.size() > 1) {
                    copy = copy.stream().filter(o -> o.charAt(finalI) == (isOxygen ? '1' : '0')).collect(Collectors.toList());
                }
            }
            if (copy.size() == 1) {
                break;
            }
        }
        return copy.stream().findFirst().orElse("");
    }

    public static void main(String[] args) {
        DayThree d = new DayThree("input.txt");
        System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
