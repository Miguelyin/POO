package Parser;

public class SimulationParameters {
    private int n;
    private int m;
    private int tau;
    private int v;
    private int vMax;
    private int mu;
    private int rho;
    private int delta;
    private int[][] matrix;
    private boolean improved;

    public SimulationParameters(int n, int m, int tau, int v, int vMax, int mu, int rho, int delta, int[][] matrix) {
        this.n = n;
        this.m = m;
        this.tau = tau;
        this.v = v;
        this.vMax = vMax;
        this.mu = mu;
        this.rho = rho;
        this.delta = delta;
        this.matrix = matrix;
        this.improved = false;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getTau() {
        return tau;
    }

    public int getV() {
        return v;
    }

    public int getVMax() {
        return vMax;
    }

    public int getMu() {
        return mu;
    }

    public int getRho() {
        return rho;
    }

    public int getDelta() {
        return delta;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean isImproved() {
        return improved;
    }

    public void setImproved(boolean improved) {
        this.improved = improved;
    }
}
