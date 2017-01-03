package hw4.puzzle;
import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;
import java.util.LinkedList;

public class Solver {
    private MinPQ<SearchNode> pq;
    private LinkedList<Board> solution2;
    private Board latestBoard;
    private SearchNode latestSearchNode;

    public Solver(Board initial) {
        pq = new MinPQ<>();
        latestBoard = initial;
        solution2 = new LinkedList<>();
        solve(initial);
    }

    public void solve(Board initial) {
        pq.insert(new SearchNode(initial, null, 0));
        while (latestSearchNode == null || !latestSearchNode.currentBoard.isGoal()) {
            enq();
        }

        while (latestSearchNode != null) {
            solution2.add(latestSearchNode.currentBoard);
            latestSearchNode = latestSearchNode.previous;
        }

        if (solution2.size() == 0) {
            solution2.add(initial);
        }
    }

    private void enq() {
        SearchNode currentMin = pq.delMin();
        latestSearchNode = currentMin;
        Iterable<Board> neighbours = BoardUtils.neighbors(currentMin.currentBoard);
        for (Board x : neighbours) {
            if (currentMin.previous == null || !x.equals(currentMin.previous.currentBoard)) {
                pq.insert(new SearchNode(x, currentMin, currentMin.moves + 1));
            }
        }
    }

    public int moves() {
        return solution2.size() - 1;
    }
    public Iterable<Board> solution() {
        return Lists.reverse(solution2);
    }

    private class SearchNode<T> implements Comparable<T> {
        private SearchNode previous;
        private int manhattan;
        private int moves;
        private int priority;
        private Board currentBoard;

        public SearchNode(Board currentBoard, SearchNode previous, int moves) {
            this.previous = previous;
            this.currentBoard = currentBoard;
            this.moves = moves;
            manhattan = currentBoard.manhattan();
            priority = moves + manhattan;
        }

        public int compareTo(T o) {
            if (o.getClass() != this.getClass()) throw new IndexOutOfBoundsException("wrong");
            SearchNode other = (SearchNode) o;
            if (other.priority > priority) return -1;
            if (other.priority == priority) return 0;
            return 1;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
       }
    }

}
