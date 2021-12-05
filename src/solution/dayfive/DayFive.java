package solution.dayfive;

import util.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

public class DayFive extends AbstractSolution {
    private final List<Connection> connections;
    private final int[][] board;

    public DayFive(String filename) {
        loadInput(filename);
        connections = new ArrayList<>();
        mapInputToConnections();
        int n = largestNum();
        board = new int[n+1][n+1];
    }

    public int partOne() {
        resetBoard();
        for (Connection c : connections) {
            if (c.x1 == c.x2) {
                int dist = c.y1 - c.y2;
                int starting = dist < 0 ? c.y1 : c.y2;
                int i = 0;
                while (i <= Math.abs(dist)) {
                    board[c.x1][starting + i] += 1;
                    i++;
                }
            } else if (c.y1 == c.y2) {
                int dist = c.x1 - c.x2;
                int starting = dist < 0 ? c.x1 : c.x2;
                int i = 0;
                while (i <= Math.abs(dist)) {
                    board[starting + i][c.y1] += 1;
                    i++;
                }
            }
        }
        return numberOfAtLeastTwo();
    }

    public int partTwo() {
        resetBoard();
        for (Connection c : connections) {
            if (c.x1 == c.x2) {
                int dist = c.y1 - c.y2;
                int starting = dist < 0 ? c.y1 : c.y2;
                int i = 0;
                while (i <= Math.abs(dist)) {
                    board[c.x1][starting + i] += 1;
                    i++;
                }
            } else if (c.y1 == c.y2) {
                int dist = c.x1 - c.x2;
                int starting = dist < 0 ? c.x1 : c.x2;
                int i = 0;
                while (i <= Math.abs(dist)) {
                    board[starting + i][c.y1] += 1;
                    i++;
                }
            } else {
                int i = c.x1;
                int j = c.y1;

                if (c.x1 < c.x2) {
                    if (c.y1 < c.y2) {
                        while (i <= c.x2 && j <= c.y2) {
                            board[i][j] += 1;
                            j++;
                            i++;
                        }
                    } else {
                        while (i <= c.x2 && j >= c.y2) {
                            board[i][j] += 1;
                            j--;
                            i++;
                        }
                    }
                } else {
                    if (c.y1 < c.y2) {
                        while (i >= c.x2 && j <= c.y2) {
                            board[i][j] += 1;
                            j++;
                            i--;
                        }
                    } else {
                        while (i >= c.x2 && j >= c.y2) {
                            board[i][j] += 1;
                            j--;
                            i--;
                        }
                    }
                }
            }
        }
        return numberOfAtLeastTwo();
    }

    private static class Connection {
        private final int x1;
        private final int x2;
        private final int y1;
        private final int y2;

        private Connection(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "Connection-{(" + x1 + "," + y1 + ") -> (" + x2 + "," + y2 + ")}";
        }
    }

    private void mapInputToConnections() {
        for (String s : input) {
            String[] parts = s.split(" -> ");
            String[] part1 = parts[0].split(",");
            String[] part2 = parts[1].split(",");
            connections.add(new Connection(Integer.parseInt(part1[0]), Integer.parseInt(part2[0]), Integer.parseInt(part1[1]), Integer.parseInt(part2[1])));
        }
    }

    private int largestNum() {
        int max = 0;
        for (Connection c : connections) {
            List<Integer> nums = new ArrayList<>(List.of(c.x1, c.x2, c.y1, c.y2));
            int localMax = nums.stream().reduce(0, (acc, curr) -> acc > curr ? acc : curr);
            if (localMax > max) {
                max = localMax;
            }
        }
        return max;
    }

    private void resetBoard() {
        for (int i=0; i<board.length;i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0;
            }
        }
    }

    private int numberOfAtLeastTwo() {
        int s = 0;
        for (int[] ints : board) {
            for (int j = 0; j < board.length; j++) {
                if (ints[j] >= 2) {
                    s++;
                }
            }
        }
        return s;
    }

    public static void main(String[] args) {
        DayFive d = new DayFive("input.txt");
        System.out.println(d.partOne());
        System.out.println(d.partTwo());
    }
}
