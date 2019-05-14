package courseWork;

public class Dyffur {

    private final double x0 = 0;
    private final double x1 = 1;
    private final double h = 1.0 / 10;
    private final double t0 = 0;
    private final double t1 = 1;
    private final double tau = 1.0 / 300;
    private final double a = -1;
    private final double b = 0.00008;
    private final double C = 5.0;

    public double getX0() {
        return x0;
    }


    public double getH() {
        return h;
    }

    public double getT0() {
        return t0;
    }


    public double getTau() {
        return tau;
    }

    public int getTPointsQuantity() {
        return (int) Math.ceil((t1 - t0) / tau) + 1;
    }

    public int getHPointsQuantity() {
        return (int) Math.ceil((x1 - x0) / h) + 1;
    }

    public double calculateTrueSolution(double x, double t) {
        return Math.pow(-Math.sqrt(-b / a) + C * Math.exp((-5 * a * t) / 6 + Math.sqrt((a + 1) / 6) * x), -2);
    }

    public double calculateBottomBorder(double x) {
        return Math.pow(-Math.sqrt(-b / a) + C * Math.exp(Math.sqrt((a + 1) / 6) * x), -2);
    }

    public double calculateLeftBorder(double t) {
        return Math.pow(-Math.sqrt(-b / a) + C * Math.exp((-5 * a * t) / 6), -2);
    }

    public double calculateRightBorder(double t) {
        double x = 1;
        return Math.pow(-Math.sqrt(-b / a) + C * Math.exp((-5 * a * t) / 6 + Math.sqrt((a + 1) / 6) * x), -2);
    }

    public double calculateApproximateSolution(double wLeft, double wCurrent, double wRight) {
        double sigma = tau / Math.pow(h, 2);
        return wCurrent + sigma * (wRight - 2 * wCurrent + wLeft) + a * tau * wCurrent + b * tau * Math.pow(wCurrent, 2);
    }

    public void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                System.out.print(String.format("%.7f\t", matrix[i][j]));
            }
            System.out.println();
        }
    }

}
