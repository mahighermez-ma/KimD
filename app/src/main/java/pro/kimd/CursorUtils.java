package pro.kimd;

import android.database.Cursor;

import java.util.Iterator;

public class CursorUtils {
    public static Iterable<Cursor> iterate(Cursor cursor) {
        return new IterableWithObject<Cursor>(cursor) {
            @Override
            public Iterator<Cursor> iterator() {
                return new IteratorWithObject<Cursor>(t) {
                    @Override
                    public boolean hasNext() {
                        t.moveToNext();
                        if (t.isAfterLast()) {
                            t.close();
                            return false;
                        }
                        return true;
                    }
                    @Override
                    public Cursor next() {
                        return t;
                    }
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("CursorUtils : remove : ");
                    }
                    @Override
                    protected void onCreate() {
                        t.moveToPosition(-1);
                    }
                };
            }
        };
    }

    private static abstract class IteratorWithObject<T> implements Iterator<T> {
        protected T t;
        public IteratorWithObject(T t) {
            this.t = t;
            this.onCreate();
        }
        protected abstract void onCreate();
    }

    private static abstract class IterableWithObject<T> implements Iterable<T> {
        protected T t;
        public IterableWithObject(T t) {
            this.t = t;
        }
    }
}