package alf.exercises.sicp;

public class Fraction {

    private int numer;
    private int denom;

    public Fraction(int numer, int denom) {
        this.numer = numer;
        this.denom = denom;

        refactor();
    }

    private void refactor() {
//        int gcd = getGCD();
        int gcd = greatestCommonDivisor(numer, denom);
        if (gcd > 1) {
            numer = numer / gcd;
            denom = denom / gcd;
        }
    }

    private int getGCD() {
        int n = numer;
        while (!((denom % n == 0)
                && (numer % n == 0))
                && (n > 0)) {
            n--;
        }
        return n;
    }

    private int greatestCommonDivisor(int a, int b) {
        if (b == 0) return a;
        return greatestCommonDivisor(b, a % b);
    }

        @Override
    public String toString() {
        String prefix = "" + numer;
        if (denom == 1) {
            return prefix;
        } else {
            return prefix + "/" + denom;
        }
    }

    public double toDouble() {
        return ((double) numer / (double) denom);
    }

    public void pretty() {
        System.out.println(toString() + " --> " + toDouble());
    }

    public Fraction add(Fraction other) {
        return new Fraction(
                (this.numer * other.denom) + (other.numer * this.denom),
                this.denom * other.denom
        );
    }

    public Fraction subtract(Fraction other) {
        return new Fraction(
                (this.numer * other.denom) - (other.numer * this.denom),
                this.denom * other.denom
        );
    }

    public Fraction multiply(Fraction other) {
        return new Fraction(
                this.numer * other.numer,
                this.denom * other.denom
        );
    }

    public Fraction divide(Fraction other) {
        return new Fraction(
                this.numer * other.denom,
                this.denom * other.numer
        );
    }
}
