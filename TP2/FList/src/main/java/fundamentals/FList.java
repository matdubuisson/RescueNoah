package fundamentals;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Author: Pierre Schaus
 *
 * Functional Programming is an increasingly important programming paradigm.
 * In this programming paradigm, data structures are immutable.
 * We are interested here in the implementation of an immutable list
 * called FList allowing to be used in a functional framework.
 * Look at the main method for a small example using this functional list
 *
 * Complete the implementation to pass the tests
 *
 * @param <A>
 */
public abstract class FList<A> implements Iterable<A> {
    public static void main(String[] args) {
        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }

        list = list.map(i -> i+1);
        // will print 10,9,...,1
        for (Integer i: list) {
            System.out.println(i);
        }

        list = list.filter(i -> i%2 == 0);
        // will print 10,...,6,4,2
        for (Integer i: list) {
            System.out.println(i);
        }
    }

    // return true if the list is not empty, false otherwise
    public final boolean isNotEmpty() {
        return this instanceof Cons;
    }

    // return true if the list is empty (Nill), false otherwise
    public final boolean isEmpty() {
        return this instanceof Nil;
    }

    // return the length of the list
    public final int length() {
        // TODO
        int result = 0;

        for(A elem : this) result++;

        return result;
    }

    // return the head element of the list
    public abstract A head();

    // return the tail of the list
    public abstract FList<A> tail();

    // creates an empty list
    public static <A> FList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }


    public final FList<A> cons(final A a) {
        return new Cons(a, this);
    }

    // return a list on which each element has been applied function f
    public final <B> FList<B> map(Function<A,B> f) {
        // TODO
        if(this instanceof Nil){
            return FList.nil();
        } else {
            return new Cons<B>(f.apply(this.head()), this.tail().map(f));
        }
    }

    // return a list on which only the elements that satisfies predicate are kept
    public final FList<A> filter(Predicate<A> f) {
        // TODO
        if(this instanceof Nil){
            return FList.nil();
        } else if(f.test(this.head())){
            return new Cons<A>(this.head(), this.tail().filter(f));
        } else {
            return this.tail().filter(f);
        }
    }

    // return an iterator on the element of the list
    public Iterator<A> iterator() {
        return new Iterator<A>() {
            // complete this class

            FList<A> current = FList.this;

            public boolean hasNext() {
                return !(this.current instanceof FList.Nil);
            }

            public A next() {
                // TODO
                A elem = this.current.head();
                this.current = this.current.tail();
                return elem;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    private static final class Nil<A> extends FList<A> {
        public static final Nil<Object> INSTANCE = new Nil();

        @Override
        public A head() {
            throw new IllegalArgumentException();
        }

        @Override
        public FList<A> tail() {
            throw new IllegalArgumentException();
        }
    }

    private static final class Cons<A> extends FList<A> {

        // TODO add instance variables
        A elem;
        FList<A> tail;

        Cons(A a, FList<A> tail) {
            this.elem = a;
            this.tail = tail;
        }

        @Override
        public A head() {
            return this.elem;
        }

        @Override
        public FList<A> tail() {
            return this.tail;
        }
    }
}
