package hw4.puzzle;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int N;
    private final int hammingNumber;
    private final int manhattanNumber;

    public Board(int[][] tiles) {
        N = tiles[0].length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        hammingNumber = getHammingNumber();
        manhattanNumber = getManhattanNumber();
    }
    public int tileAt(int i, int j) {
        if (i > N - 1 || j > N - 1 || i < 0 || j < 0) throw new java.lang.IndexOutOfBoundsException();
        return tiles[i][j];
    }
    public int size() {
        return tiles.length;
    }

    private int getHammingNumber() {
        int row = 0;
        int hamNumber = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (tileAt(i, j) != j + row + 1 && tileAt(i, j) != 0) hamNumber += 1;
            }
            row += N;
        }
        return hamNumber;
    }

    public int hamming() {
        return hammingNumber;
    }

    private int getManhattanNumber() {
        int manNumber = 0;
        for (int row = 0; row < N; row += 1) {
            for (int col = 0; col < N; col += 1) {
                int currentNumber = tileAt(row, col);
                if (currentNumber != 0) {
                    int desRow = desiredRow(currentNumber);
                    int desCol = desiredCol(currentNumber);
                    int currentManNumber = Math.abs(desRow - row) + Math.abs(desCol - col);
                    manNumber += currentManNumber;

                }

            }
        }
        return manNumber;
    }

    private int desiredRow(int x) {
        if (x % N == 0) return x / N - 1;
        return x / N;
    }

    private int desiredCol(int x) {
        if (x % N == 0) return N - 1;
        return x % N - 1;
    }



    public int manhattan() {
        return manhattanNumber;

    }
    public boolean isGoal() {
        if (hammingNumber == 0) return true;
        return false;

    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board otherBoard = (Board) y;
        return Arrays.deepEquals(otherBoard.tiles, tiles);
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    public static void main(String[] args) {
        int[][] tileTest = new int[3][3];
        int extra = 1;
        for (int i = 0; i < 3; i += 1) {
            for (int j = 0; j < 3; j += 1) {
                tileTest[i][j] = j + extra;
                if (i == 2 && j == 2 ) tileTest[i][j] = 0;
            }
            extra += 3;
        }
        int[][] tileTest2 = new int[6][6];
        int extra2 = 1;
        for (int i = 0; i < 6; i += 1) {
            for (int j = 0; j < 6; j += 1) {
                tileTest2[i][j] = j + extra2;
                if (i == 5 && j == 5 ) tileTest2[i][j] = 0;
            }
            extra2 += 6;
        }


        //System.out.println(tileTest[2][1]);

        Board testBoard = new Board(tileTest);
        Board testBoard2 = new Board(tileTest2);
        System.out.println("manhattan: " + testBoard.manhattan());
        System.out.println(testBoard);
        System.out.println(testBoard2);
        System.out.println(testBoard2.hamming());
    }
}
