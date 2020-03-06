package Lists;

import java.util.*;

public class CopyList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
    private transient Entry<E> header = new Entry<E>(null, null, null);
    
//    Change!!
    protected Object[] elementData;
    protected int elementCount;
    protected int capacityIncrement;
    
    public CopyList() {
        header.next = header.previous = header;
//      Change!!
        elementData = new Object[10];
        capacityIncrement = 0;
    }

    public CopyList(Collection<? extends E> c) {
		this();
		addAll(c);
//		Change!!
		elementData = c.toArray();
		elementCount = elementData.length;
		if (elementData.getClass() != Object[].class)
		    elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
    }

    public E getFirst() {
		if (elementCount==0)
		    throw new NoSuchElementException();	
		return header.next.element;
    }

    public E getLast()  {
		if (elementCount==0)
		    throw new NoSuchElementException();
		return header.previous.element;
    }

    public E removeFirst() {
    	return remove(header.next);
    }

    public E removeLast() {
    	return remove(header.previous);
    }

    public void addFirst(E e) {
    	addBefore(e, header.next);
    }
  
    public void addLast(E e) {
    	addBefore(e, header);
    }
   
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
  
    public int size() {
    	return elementCount;
    }
   
    public boolean add(E e) {
		addBefore(e, header);
	        return true;
    }

    public boolean remove(Object o) {
        if (o==null) {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (e.element==null) {
                    remove(e);
                    return true;
                }
            }
        } else {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(elementCount, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > elementCount)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+elementCount);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew==0)
            return false;
        modCount++;

        Entry<E> successor = (index==elementCount ? header : entry(index));
        Entry<E> predecessor = successor.previous;
		for (int i=0; i<numNew; i++) {
	            Entry<E> e = new Entry<E>((E)a[i], successor, predecessor);
	            predecessor.next = e;
	            predecessor = e;
	        }
        successor.previous = predecessor;

        elementCount += numNew;
        return true;
    }

    public void clear() {
        Entry<E> e = header.next;
        while (e != header) {
            Entry<E> next = e.next;
            e.next = e.previous = null;
            e.element = null;
            e = next;
        }
        header.next = header.previous = header;
        elementCount = 0;
	modCount++;
    }

    public E get(int index) {
    	
    	if (index >= elementCount)
    	    throw new ArrayIndexOutOfBoundsException(index);
//    	Change!!
    	return (E)elementData[index];
//        return entry(index).element;
    }

    public E set(int index, E element) {
        Entry<E> e = entry(index);
        E oldVal = e.element;
        e.element = element;
        return oldVal;
    }

    public void add(int index, E element) {
        addBefore(element, (index==elementCount ? header : entry(index)));
    }
    
    public E remove(int index) {
        return remove(entry(index));
    }

    private Entry<E> entry(int index) {
        if (index < 0 || index >= elementCount)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+elementCount);
        Entry<E> e = header;
        if (index < (elementCount >> 1)) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = elementCount; i > index; i--)
                e = e.previous;
        }
        return e;
    }

    public int indexOf(Object o) {
        int index = 0;
        if (o==null) {
            for (Entry e = header.next; e != header; e = e.next) {
                if (e.element==null)
                    return index;
                index++;
            }
        } else {
            for (Entry e = header.next; e != header; e = e.next) {
                if (o.equals(e.element))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int index = elementCount;
        if (o==null) {
            for (Entry e = header.previous; e != header; e = e.previous) {
                index--;
                if (e.element==null)
                    return index;
            }
        } else {
            for (Entry e = header.previous; e != header; e = e.previous) {
                index--;
                if (o.equals(e.element))
                    return index;
            }
        }
        return -1;
    }

    public E peek() {
    	if (elementCount==0)
            return null;
        return getFirst();
    }

    public E element() {
        return getFirst();
    }

    public E poll() {
        if (elementCount==0)
            return null;
        return removeFirst();
    }

    public E remove() {
        return removeFirst();
    }

    public boolean offer(E e) {
        return add(e);
    }

    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    public E peekFirst() {
        if (elementCount==0)
            return null;
        return getFirst();
    }

    public E peekLast() {
        if (elementCount==0)
            return null;
        return getLast();
    }

    public E pollFirst() {
        if (elementCount==0)
            return null;
        return removeFirst();
    }

    public E pollLast() {
        if (elementCount==0)
            return null;
        return removeLast();
    }

    public void push(E e) {
        addFirst(e);
    }

    public E pop() {
        return removeFirst();
    }

    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    public boolean removeLastOccurrence(Object o) {
        if (o==null) {
            for (Entry<E> e = header.previous; e != header; e = e.previous) {
                if (e.element==null) {
                    remove(e);
                    return true;
                }
            }
        } else {
            for (Entry<E> e = header.previous; e != header; e = e.previous) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }
        }
        return false;
    }


    public ListIterator<E> listIterator(int index) {
	return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
	private Entry<E> lastReturned = header;
	private Entry<E> next;
	private int nextIndex;
	private int expectedModCount = modCount;

	ListItr(int index) {
	    if (index < 0 || index > elementCount)
		throw new IndexOutOfBoundsException("Index: "+index+
						    ", Size: "+elementCount);
	    if (index < (elementCount >> 1)) {
		next = header.next;
		for (nextIndex=0; nextIndex<index; nextIndex++)
		    next = next.next;
	    } else {
		next = header;
		for (nextIndex=elementCount; nextIndex>index; nextIndex--)
		    next = next.previous;
	    }
	}

	public boolean hasNext() {
	    return nextIndex != elementCount;
	}

	public E next() {
	    checkForComodification();
	    if (nextIndex == elementCount)
		throw new NoSuchElementException();

	    lastReturned = next;
	    next = next.next;
	    nextIndex++;
	    return lastReturned.element;
	}

	public boolean hasPrevious() {
	    return nextIndex != 0;
	}

	public E previous() {
	    if (nextIndex == 0)
		throw new NoSuchElementException();

	    lastReturned = next = next.previous;
	    nextIndex--;
	    checkForComodification();
	    return lastReturned.element;
	}

	public int nextIndex() {
	    return nextIndex;
	}

	public int previousIndex() {
	    return nextIndex-1;
	}

	public void remove() {
            checkForComodification();
            Entry<E> lastNext = lastReturned.next;
            try {
                CopyList.this.remove(lastReturned);
            } catch (NoSuchElementException e) {
                throw new IllegalStateException();
            }
	    if (next==lastReturned)
                next = lastNext;
            else
		nextIndex--;
	    lastReturned = header;
	    expectedModCount++;
	}

	public void set(E e) {
	    if (lastReturned == header)
		throw new IllegalStateException();
	    checkForComodification();
	    lastReturned.element = e;
	}

	public void add(E e) {
	    checkForComodification();
	    lastReturned = header;
	    addBefore(e, next);
	    nextIndex++;
	    expectedModCount++;
	}

	final void checkForComodification() {
	    if (modCount != expectedModCount)
		throw new ConcurrentModificationException();
	}
    }

    private static class Entry<E> {
	E element;
	Entry<E> next;
	Entry<E> previous;

	Entry(E element, Entry<E> next, Entry<E> previous) {
	    this.element = element;
	    this.next = next;
	    this.previous = previous;
	}
    }

    private Entry<E> addBefore(E e, Entry<E> entry) {
    	Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
    	newEntry.previous.next = newEntry;
    	newEntry.next.previous = newEntry;
//    	Change!!
    	modCount++;
    	ensureCapacityHelper(elementCount + 1);
    	elementData[elementCount++] = e;
    	return newEntry;
        }

    private E remove(Entry<E> e) {
	if (e == header)
	    throw new NoSuchElementException();

        E result = e.element;
	e.previous.next = e.next;
	e.next.previous = e.previous;
        e.next = e.previous = null;
        e.element = null;
        elementCount--;
	modCount++;
        return result;
    }

    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    private class DescendingIterator implements Iterator {
        final ListItr itr = new ListItr(size());
	public boolean hasNext() {
	    return itr.hasPrevious();
	}
	public E next() {
            return itr.previous();
        }
	public void remove() {
            itr.remove();
        }
    }


    public Object clone() {
        CopyList<E> clone = null;
	try {
	    clone = (CopyList<E>) super.clone();
	} catch (CloneNotSupportedException e) {
	    throw new InternalError();
	}
        clone.header = new Entry<E>(null, null, null);
        clone.header.next = clone.header.previous = clone.header;
        clone.elementCount = 0;
        clone.modCount = 0;

        for (Entry<E> e = header.next; e != header; e = e.next)
            clone.add(e.element);

        return clone;
    }

    public Object[] toArray() {
	Object[] result = new Object[elementCount];
        int i = 0;
        for (Entry<E> e = header.next; e != header; e = e.next)
            result[i++] = e.element;
	return result;
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < elementCount)
            a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), elementCount);
        int i = 0;
	Object[] result = a;
        for (Entry<E> e = header.next; e != header; e = e.next)
            result[i++] = e.element;

        if (a.length > elementCount)
            a[elementCount] = null;

        return a;
    }

    private static final long serialVersionUID = 876323262645176354L;

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
    	
	s.defaultWriteObject();

        s.writeInt(elementCount);

        for (Entry e = header.next; e != header; e = e.next)
            s.writeObject(e.element);
    }


    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
    	s.defaultReadObject();
	
        int size = s.readInt();
        
        header = new Entry<E>(null, null, null);
        header.next = header.previous = header;

        for (int i=0; i<size; i++)
            addBefore((E)s.readObject(), header);
    }
//    Change!!
    private void ensureCapacityHelper(int minCapacity) {
    	int oldCapacity = elementData.length;
    	if (minCapacity > oldCapacity) {
    	    Object[] oldData = elementData;
    	    int newCapacity = (capacityIncrement > 0) ?
    		(oldCapacity + capacityIncrement) : (oldCapacity * 2);
        	    if (newCapacity < minCapacity) {
    		newCapacity = minCapacity;
    	    }
                elementData = Arrays.copyOf(elementData, newCapacity);
    	}
        }
}
