package MyCollection;

import java.lang.reflect.Array;
import java.util.*;

public class MyQueue implements Queue {

    private Object[] entry;
    private int elements = 0;

    public MyQueue() {
        entry = new Object[16];
    }

    @Override
    public int size() {
        return elements;
    }

    @Override
    public boolean isEmpty() {
        return elements == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o != null) {
            for (Object element : entry) {
                if (element.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new InnerIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resultArr = new Object[entry.length];
        System.arraycopy(entry, 0, resultArr, 0, elements);
        return resultArr;
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return (Object[]) Array.newInstance(objects.getClass(), objects.length);
    }

    @Override
    public boolean add(Object o) {
        if (o != null) {
            if (elements + 1 > entry.length) {
                increaseSize(1);
            }
            entry[elements] = o;
            elements++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            return deleteElement(o);
        }
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        if (collection != null) {
            if (collection.size() + elements > entry.length) {
                increaseSize(collection.size());
            }
            for (Object o : collection) {
                add(o);
            }
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        entry = new Object[entry.length];
    }

    @Override
    public boolean retainAll(Collection collection) {
        boolean isChanged = false;
        for (Object o : entry) {
            if (!collection.contains(o)) {
                deleteElement(o);
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection collection) {
        boolean isChanged = false;
        for (Object o : entry) {
            if (collection.contains(o)) {
                deleteElement(o);
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean containsAll(Collection collection) {
        boolean result = true;
        for (Object o : entry) {
            if (!collection.contains(o)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean offer(Object o) {
        if (o != null) {
            if (elements + 1 > entry.length) {
                increaseSize(1);
            }
            entry[elements] = o;
            elements++;
            return true;
        }
        return false;
    }

    @Override
    public Object remove() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Object o = returnHeadElement();
        deleteElement(o);
        return o;
    }

    @Override
    public Object poll() {
        if (this.isEmpty()) {
            return null;
        }
        Object o = returnHeadElement();
        deleteElement(o);
        return o;
    }

    @Override
    public Object element() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Object o = returnHeadElement();
        return o;
    }

    @Override
    public Object peek() {
        if (this.isEmpty()) {
            return null;
        }
        Object o = returnHeadElement();
        return o;
    }

    private boolean deleteElement(Object o) {
        int index = findElementIndex(o);
        if (index >= 0) {
            Object[] tempArr = new Object[entry.length];
            System.arraycopy(entry, 0, tempArr, 0, index);
            System.arraycopy(entry, index + 1, tempArr, index, tempArr.length - index - 1);
            entry = tempArr;
            elements--;
            return true;
        }
        return false;
    }

    private int findElementIndex(Object o) {
        int index = 0;
        for (Object element : entry) {
            if (element.equals(o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private Object returnHeadElement() {
        return entry[0];
    }

    private void increaseSize(int needSpace) {
        Object[] tempArr = new Object[needSpace + (entry.length * 2)];
        System.arraycopy(entry, 0, tempArr, 0, entry.length);
        entry = tempArr;
    }

    private class InnerIterator implements Iterator {
        int position = 0;
        int remainingElements = elements;

        public boolean hasNext() {
            return remainingElements > 0;
        }

        public Object next() {
            if (remainingElements <= 0) {
                throw new NoSuchElementException();
            }
            int prevPosition = position;
            remainingElements--;
            position++;
            return entry[prevPosition];
        }
    }
}
