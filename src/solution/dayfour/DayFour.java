package solution.dayfour;

import util.AbstractSolution;

import java.util.*;

public class DayFour extends AbstractSolution {
    private final List<Integer> numbers;
    private final List<GridElement[][]> grid;
    private final List<Victory> victories;

    public DayFour(String filename) {
        loadInput(filename);
        numbers = new ArrayList<>();
        grid = new ArrayList<>();
        victories = new ArrayList<>();
        mapInputToNumbers();
        mapInputToGrids();
    }

    private enum State {
        marked,
        unmarked
    }

    private static class GridElement {
        private State state;
        private final int number;

        private GridElement(int number) {
            this.state = State.unmarked;
            this.number = number;
        }

        public void mark() {
            this.state = State.marked;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return "GridElement{" +
                    "state=" + state +
                    ", number=" + number +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GridElement)) return false;

            GridElement that = (GridElement) o;

            return number == that.number;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }

    private static class Victory {
        private final int startingPoint;
        private final boolean horizontal;
        private final int unmarkedSum;
        private final int gridNumber;

        private Victory(int startingPoint, boolean horizontal, int unmarkedSum, int gridNumber) {
            this.startingPoint = startingPoint;
            this.horizontal = horizontal;
            this.unmarkedSum = unmarkedSum;
            this.gridNumber = gridNumber;
        }

        @Override
        public String toString() {
            return "Victory{" +
                    "startingPoint=" + startingPoint +
                    ", horizontal=" + horizontal +
                    ", unmarkedSum=" + unmarkedSum +
                    ", gridNumber=" + gridNumber +
                    ", solSum=" + unmarkedSum*startingPoint +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Victory)) return false;

            Victory victory = (Victory) o;

            return gridNumber == victory.gridNumber;
        }

        @Override
        public int hashCode() {
            return gridNumber;
        }
    }

    public int partOne() {
        return bingo(true);
    }

    public int partTwo() {
        return bingo(false);
    }

    private int bingo(boolean findFirst) {
        for (Integer i : numbers) {
            for (int j = 0; j<grid.size(); j++) {
                GridElement ge = getGridElement(grid.get(j), i);
                if (ge != null) {
                    ge.mark();
                }
                if (checkVictory(grid.get(j), j, i)) {
                    Victory last = victories.get(victories.size() - 1);
                    if (findFirst) {
                        return last.unmarkedSum * last.startingPoint;
                    }
                }
            }
        }
        Victory last = victories.get(victories.size() - 1);
        return last.unmarkedSum * last.startingPoint;
    }

    private boolean checkVictory(GridElement[][] g, int index, int num) {
        boolean rowMarked = checkRows(g, true, index, num);
        if (rowMarked) {
            return true;
        }
        GridElement[][] transposed = new GridElement[5][5];
        for(int i=0;i<g.length;i++){
            for(int j=0;j<g[0].length;j++){
                transposed[i][j]=g[j][i];
            }
        }
        return checkRows(transposed, false, index, num);
    }

    private boolean checkRows(GridElement[][] g, boolean horizontal, int index, int num) {
        boolean rowMarked;
        for (GridElement[] gridElements : g) {
            rowMarked = Arrays.stream(gridElements).filter(e -> e.state == State.marked).count() == g[0].length;
            if (rowMarked) {
                Victory v = new Victory(num, horizontal, unmarkedSum(g), index);
                if (!victories.contains(v)) {
                    victories.add(v);
                }
                return true;
                }
            }
        return false;
    }

    private int unmarkedSum(GridElement[][] g) {
        int sum = 0;
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if (g[i][j].state == State.unmarked) {
                    sum += g[i][j].number;
                }
            }
        }
        return sum;
    }

    private GridElement getGridElement(GridElement[][] g, int number) {
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if (g[i][j].number == number) {
                    return g[i][j];
                }
            }
        }
        return null;
    }

    private void mapInputToNumbers() {
        for (String s : input.get(0).split(",")) {
            numbers.add(Integer.parseInt(s));
        }
    }

    private void mapInputToGrids() {
        List<String> copy = new ArrayList<>(input);
        GridElement[][] gr = new GridElement[5][5];
        GridElement[] row;
        copy.remove(1);
        copy.remove(0);
        int i;
        int j = 0;
        for (String s : copy) {
            if (s.equals("")) continue;
            row = new GridElement[5];
            i = 0;
            String[] strings = s.strip().split(" ");
            for (String st : strings) {
                if (st.equals("")) continue;
                row[i] = new GridElement(Integer.parseInt(st));
                i++;
            }
            gr[j] = row;
            j++;
            if (j == 5) {
                grid.add(gr);
                gr = new GridElement[5][5];
                j = 0;
            }
        }
    }

    public static void main(String[] args) {
        DayFour d = new DayFour("input.txt");
        System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
