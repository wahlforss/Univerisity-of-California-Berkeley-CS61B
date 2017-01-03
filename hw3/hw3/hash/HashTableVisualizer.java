package hw3.hash;

import java.util.HashSet;
import java.util.Set;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        double scale = 0.1;
        int N = 1000;
        int M = 100;

        HashTableDrawingUtility.setScale(scale);
        Set<Oomage> oomies = new HashSet<Oomage>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(oomies, M, scale);
    }

    public static void visualize(Set<Oomage> set, int M, double scale) {
        HashTableDrawingUtility.setScale(scale);
        HashTableDrawingUtility.drawLabels(M);

        /* TODO: Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */
        int[] bucketPos = new int[M];
        for (int i = 0; i < M; i++) {
            bucketPos[i] = 0;
        }

        for (Oomage x: set) {
            int hash = (x.hashCode() & 0x7FFFFFFF) % M;

            x.draw(HashTableDrawingUtility.xCoord(bucketPos[hash]), HashTableDrawingUtility.yCoord(hash,M));
            bucketPos[hash] += 1;
        }

        /* When done with visualizer, be sure to try 
           scale = 0.5, N = 2000, M = 100. */           
    }
} 
