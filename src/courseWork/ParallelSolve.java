package courseWork;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelSolve {

    private int TPointsQuantity;
    private int HPointsQuantity;
    private double x0;
    private double h;
    private double t0;
    private double tau;
    private Dyffur dyffur;

    public ParallelSolve(Dyffur dyffur) {
        this.dyffur = dyffur;
        this.TPointsQuantity = dyffur.getTPointsQuantity();
        this.HPointsQuantity = dyffur.getHPointsQuantity();
        this.x0 = dyffur.getX0();
        this.h = dyffur.getH();
        this.t0 = dyffur.getT0();
        this.tau = dyffur.getTau();
    }

    public double[][] solve() {
        double t = t0 + tau;
        double x = x0;
        double w[][] = new double[TPointsQuantity][HPointsQuantity];
        for (int j = 0; j < HPointsQuantity; j++, x += h) {
            w[0][j] = dyffur.calculateBottomBorder(x);
        }

        for (int i = 1; i < TPointsQuantity; ++i, t += tau) {
            w[i][0] = dyffur.calculateLeftBorder(t);
            AtomicInteger ai = new AtomicInteger(i);
            IntStream.range(1, dyffur.getHPointsQuantity()-1).parallel().forEach(j -> {
                int m=ai.get();
                w[m][j] = dyffur.calculateApproximateSolution(w[m-1][j-1],w[m-1][j],w[m-1][j+1]);
            });
            w[i][HPointsQuantity - 1] = dyffur.calculateRightBorder(t);
        }
        return w;
    }
}
