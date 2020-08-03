/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    int[] result;

    // perform indep trials on n-by-n grid
    public PercolationStats(int n, int trials) {
        result = new int[trials];
        int times = 0;
        boolean success = false;
        int row, col;
        while (times < trials) {
            Percolation myPercolate = new Percolation(n);
            int numOpen = 0;
            success = false;
            while (!success) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (myPercolate.isOpen(row, col) == false) {
                    myPercolate.open(row, col);
                    if (myPercolate.percolates() == true)
                        success = true;
                    numOpen++;
                }
            }
            result[times] = numOpen;
            times++;
        }
        System.out.println("The result is: ");
        for (int i = 0; i < trials; i++)
            System.out.println(result[i]);
    }

    // sample mean of pecolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double xBar = mean();
        double dev = stddev();
        return (xBar - 1.96 * dev / Math.sqrt(result.length));
    }

    public double confidenceHi() {
        double xBar = mean();
        double dev = stddev();
        return (xBar + 1.96 * dev / Math.sqrt(result.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);
        PercolationStats myStat = new PercolationStats(n, trial);
        System.out.println("input are: " + n + ", " + trial);
        System.out.println("mean" + "\t\t\t= " + myStat.mean());
        System.out.println("stddev" + "\t\t\t= " + myStat.stddev());
        System.out.println("95% confidence interval = [" + myStat.confidenceLo() +
                                   ", " + myStat.confidenceHi() + "]");
    }
}
