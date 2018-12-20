package alf;

import java.util.AbstractMap;
import java.util.Objects;

public class Pair<L, R> extends AbstractMap.SimpleImmutableEntry<L, R> implements Comparable<Pair> {

    public Pair(L left, R right) {
        super(left, right);
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    public L left() {
        return this.getKey();
    }

    public R right() {
        return this.getValue();
    }

    public String toString() {
        return String.format("[%s,%s]", left(), right());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair other = (Pair) o;
        return left() == other.left() &&
                right() == other.right();
    }

    @Override
    public int hashCode() {
        return Objects.hash(left(), right());
    }

    @Override
    public int compareTo(Pair other) {
        return this.hashCode() - other.hashCode();
    }
}
