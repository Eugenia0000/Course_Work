package courseWork;

public class SerialSolve {

    private int TPointsQuantity;
    private int HPointsQuantity;
    private double x0;
    private double h;
    private double t0;
    private double tau;
    private Dyffur dyffur;

    public SerialSolve(Dyffur dyffur) {
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
            for (int j = 1; j < HPointsQuantity - 1; j++) {
                w[i][j] = dyffur.calculateApproximateSolution(w[i-1][j-1],w[i-1][j],w[i-1][j+1]);
            }
            w[i][HPointsQuantity - 1] = dyffur.calculateRightBorder(t);
        }
        return w;
    }
}
