package courseWork;

public class Main {

    public static void main(String[] args) {
        System.out.println("SERIAL RESULT");
        Dyffur dyffur = new Dyffur();
        long startTimeSerial = System.nanoTime();
        double[][] serialSolve = new SerialSolve(dyffur).solve();
        long endTimeSerial = System.nanoTime();
        long executeTimeSerial = endTimeSerial - startTimeSerial;
        System.out.println("Approximate result");
        dyffur.printMatrix(serialSolve);


        System.out.println();
        System.out.println("\nPARALLEL RESULT");
        long startTimeParallel = System.nanoTime();
        double[][] parallelSolve = new ParallelSolve(dyffur).solve();
        long endTimeParallel = System.nanoTime();
        long executeTimeParallel = endTimeParallel - startTimeParallel;
        for (int i = 0; i < dyffur.getTPointsQuantity(); ++i) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); ++j) {
                //	parallelSolve[i][j] = Math.abs(parallelSolve[i][j] - serialSolve[i][j]);

                System.out.print(String.format("%.7f\t", parallelSolve[i][j]));
            }
            System.out.println();
        }


        System.out.println("\nExecuted time for serial result " + executeTimeSerial + " ns");
        System.out.println("\nExecuted time for parallel result " + executeTimeParallel + " ns");


        printResult(dyffur, serialSolve, calculateExactResult(dyffur));
    }

    private static double[][] calculateExactResult(Dyffur dyffur) {
        double trueMatrix[][] = new double[dyffur.getTPointsQuantity()][dyffur.getHPointsQuantity()];
        double t = dyffur.getT0();
        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            double x = dyffur.getX0();
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                trueMatrix[i][j] = dyffur.calculateTrueSolution(x, t);
                x += dyffur.getH();
            }
            t += dyffur.getTau();
        }
        System.out.println("\nExact result");
        dyffur.printMatrix(trueMatrix);

        return trueMatrix;
    }

    private static void printResult(Dyffur dyffur, double[][] serialSolve, double[][] trueMatrix) {
        System.out.println("Average absolute error: ");
        System.out.println(averageAbsoluteError(serialSolve, trueMatrix, dyffur));
        System.out.println("Max Absolute Error: ");
        System.out.println(maxAbsoluteError(serialSolve, trueMatrix, dyffur));
        System.out.println("Average Relative Error: ");
        System.out.println(averageRelativeError(serialSolve, trueMatrix, dyffur));
        System.out.println("Max Relative Error: ");
        System.out.println(maxRelativeError(serialSolve, trueMatrix, dyffur));
    }


    private static double averageAbsoluteError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTPointsQuantity()][dyffur.getHPointsQuantity()];
        double error = 0;
        double errorResult = 0;

        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                matrix[i][j] = Math.abs(approximateResult[i][j] - exactMatrix[i][j]);
            }
        }

        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                error += matrix[i][j];
            }
        }
        errorResult = error / (dyffur.getTPointsQuantity() * dyffur.getHPointsQuantity());

        return errorResult;
    }

    private static double maxAbsoluteError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTPointsQuantity()][dyffur.getHPointsQuantity()];
        double maxError = 0;

        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                matrix[i][j] = Math.abs(approximateResult[i][j] - exactMatrix[i][j]);
            }
        }

        maxError = matrix[0][0];
        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                if (maxError < matrix[i][j]) {
                    maxError = matrix[i][j];
                }
            }
        }


        return maxError;
    }


    private static double averageRelativeError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTPointsQuantity()][dyffur.getHPointsQuantity()];
        double error = 0;
        double errorResult = 0;

        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                matrix[i][j] = (Math.abs(approximateResult[i][j] - exactMatrix[i][j]) / exactMatrix[i][j]) * 100;
                error += matrix[i][j];
            }
        }

        errorResult = error / (dyffur.getTPointsQuantity() * dyffur.getHPointsQuantity());

        return errorResult;
    }

    private static double maxRelativeError(double[][] approximateResult, double[][] exactMatrix, Dyffur dyffur) {
        double[][] matrix = new double[dyffur.getTPointsQuantity()][dyffur.getHPointsQuantity()];
        double maxError = 0;

        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                matrix[i][j] = (Math.abs(approximateResult[i][j] - exactMatrix[i][j]) / exactMatrix[i][j]) * 100;

            }
        }

        maxError = matrix[0][0];
        for (int i = 0; i < dyffur.getTPointsQuantity(); i++) {
            for (int j = 0; j < dyffur.getHPointsQuantity(); j++) {
                if (maxError < matrix[i][j]) {
                    maxError = matrix[i][j];
                }
            }
        }


        return maxError;
    }


}