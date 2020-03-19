package Lists;

import java.util.*;

import Data.Data_Lists;


public class CopyList04<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable, Data_Lists
{
    private transient Entry<E> header = new Entry<E>(null, null, null);
    private transient int size = 0;

    // elementList : Entry 객체에 저장할 element
    // elementListSize : elementList에 적재된 공간
    // elementListCapacity : 한 elementList의 최대 공간
    private ArrayList<E> elementList = new ArrayList<E>();
    private int elementListSize = 0;
    private int elementListCapacity = 10;
    private int limit = 0;
    
    public CopyList04() {
        header.next = header.previous = header;
    }
    
	public CopyList04(int elementListCapacity) {
        header.next = header.previous = header;
        this.elementListCapacity = elementListCapacity;
    }

    public CopyList04(Collection<? extends E> c) {
		this();
		addAll(c);
    }

    // 첫번째 객체에서 elementList의 첫번째 리턴 리턴
    public E getFirst() {
		if (size==0)
		    throw new NoSuchElementException();

		return header.next.element.get(0);
    }

    // 마지막 객체에서 elementList의 마지막 값 리턴
    public E getLast()  {
		if (size==0)
		    throw new NoSuchElementException();
	
		return header.previous.element.get(9);
    }

    public E removeFirst() {
    	return remove(header.next);
    }

    public E removeLast() {
    	return remove(header.previous);
    }

    //header.next.element에 남는 공간이 있으면 맨앞에 넣고, 뒤로 한칸씩 밀기
	//header.next.element에 남는 공간이 없으면 맨앞에 새로운 arrayList 넣기
    public void addFirst(E e) {
    	ArrayList<E> firstElement = header.next.element;
    	
    	if(firstElement != null && firstElement.size() < elementListCapacity) {
    		firstElement.add(0,e);
    	}else {
    		ArrayList<E> element = new ArrayList<E>();
        	element.add(e);
    		addBefore(element, header.next);
    	}
    }

  
    //header.previous에 남는 공간이 있으면 맨뒤에 넣기
	//header.previous에 남는 공간이 없으면 맨뒤에 새로운 arrayList 넣기
    public void addLast(E e) {
    	ArrayList<E> lastElement = header.previous.element;

    	if(lastElement != null && lastElement.size() < elementListCapacity) {
    		lastElement.add(e);
    	}else {
    		ArrayList<E> elementList = new ArrayList<E>();
        	elementList.add(e);
    		addBefore(elementList, header);
    	}
    }

    
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

  
    public int size() {
    	return size;
    }


    /**
     * elementListSize가 elementListCapacity의 배수일 때마다 새로운 노드 생성
     */
    public boolean add(E e) {
    	if(elementListSize % elementListCapacity == 0) {
    		// node Change!
    		ArrayList<E> elementList = new ArrayList<E>();
    		addBefore(elementList, header);
    	}
    	elementListSize++;
    	// 새로 생성한 노드의 element에 e 저장
    	header.previous.element.add(e);
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
        return addAll(size, c);
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        Entry<E> e = header.next;
        while (e != header) {
            Entry<E> next = e.next;
            e.next = e.previous = null;
            e.element = null;
            e = next;
        }
        header.next = header.previous = header;
        size = 0;
        modCount++;
    }


    // index / elementListCapacity번째에 있는 entry의 element에서
    // index % elementListCapacity번째의 데이터를 가져옴
    public E get(int index) {
          return entry(index / elementListCapacity).element.get(index % elementListCapacity);
    }

    
    
    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element) {
        Entry<E> e = entry(index / elementListCapacity);
        E oldVal = e.element.get(index % elementListCapacity);
        e.element.set(index % elementListCapacity, element);
        return oldVal;
    }

    /**
     * change
     */
    public void add(int index, E element) {
    	Entry<E> e = entry(index / elementListCapacity);
		e.element.add(index % elementListCapacity, element);
    }

    /**
     * Removes the element at the specified position in this list.  Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
        return remove(entry(index));
    }

    /**
     * Returns the indexed entry.
     */
    private Entry<E> entry(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+size);
        Entry<E> e = header;
        if (index <= (size >> 1)) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = size; i > index; i--)
                e = e.previous;
        }
        return e;
    }


    // Search Operations

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
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

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
    public int lastIndexOf(Object o) {
        int index = size;
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

    // Queue operations.

    /**
     * Retrieves, but does not remove, the head (first element) of this list.
     * @return the head of this list, or <tt>null</tt> if this list is empty
     * @since 1.5
     */
    public E peek() {
        if (size==0)
            return null;
        return getFirst();
    }

    /**
     * Retrieves, but does not remove, the head (first element) of this list.
     * @return the head of this list
     * @throws NoSuchElementException if this list is empty
     * @since 1.5
     */
    public E element() {
        return getFirst();
    }

    /**
     * Retrieves and removes the head (first element) of this list
     * @return the head of this list, or <tt>null</tt> if this list is empty
     * @since 1.5
     */
    public E poll() {
        if (size==0)
            return null;
        return removeFirst();
    }

    /**
     * Retrieves and removes the head (first element) of this list.
     *
     * @return the head of this list
     * @throws NoSuchElementException if this list is empty
     * @since 1.5
     */
    public E remove() {
        return removeFirst();
    }

    /**
     * Adds the specified element as the tail (last element) of this list.
     *
     * @param e the element to add
     * @return <tt>true</tt> (as specified by {@link Queue#offer})
     * @since 1.5
     */
    public boolean offer(E e) {
        return add(e);
    }

    // Deque operations
    /**
     * Inserts the specified element at the front of this list.
     *
     * @param e the element to insert
     * @return <tt>true</tt> (as specified by {@link Deque#offerFirst})
     * @since 1.6
     */
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    /**
     * Inserts the specified element at the end of this list.
     *
     * @param e the element to insert
     * @return <tt>true</tt> (as specified by {@link Deque#offerLast})
     * @since 1.6
     */
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    /**
     * Retrieves, but does not remove, the first element of this list,
     * or returns <tt>null</tt> if this list is empty.
     *
     * @return the first element of this list, or <tt>null</tt>
     *         if this list is empty
     * @since 1.6
     */
    public E peekFirst() {
        if (size==0)
            return null;
        return getFirst();
    }

    /**
     * Retrieves, but does not remove, the last element of this list,
     * or returns <tt>null</tt> if this list is empty.
     *
     * @return the last element of this list, or <tt>null</tt>
     *         if this list is empty
     * @since 1.6
     */
    public E peekLast() {
        if (size==0)
            return null;
        return getLast();
    }

    /**
     * Retrieves and removes the first element of this list,
     * or returns <tt>null</tt> if this list is empty.
     *
     * @return the first element of this list, or <tt>null</tt> if
     *     this list is empty
     * @since 1.6
     */
    public E pollFirst() {
        if (size==0)
            return null;
        return removeFirst();
    }

    /**
     * Retrieves and removes the last element of this list,
     * or returns <tt>null</tt> if this list is empty.
     *
     * @return the last element of this list, or <tt>null</tt> if
     *     this list is empty
     * @since 1.6
     */
    public E pollLast() {
        if (size==0)
            return null;
        return removeLast();
    }

    /**
     * Pushes an element onto the stack represented by this list.  In other
     * words, inserts the element at the front of this list.
     *
     * <p>This method is equivalent to {@link #addFirst}.
     *
     * @param e the element to push
     * @since 1.6
     */
    public void push(E e) {
        addFirst(e);
    }

    /**
     * Pops an element from the stack represented by this list.  In other
     * words, removes and returns the first element of this list.
     *
     * <p>This method is equivalent to {@link #removeFirst()}.
     *
     * @return the element at the front of this list (which is the top
     *         of the stack represented by this list)
     * @throws NoSuchElementException if this list is empty
     * @since 1.6
     */
    public E pop() {
        return removeFirst();
    }

    /**
     * Removes the first occurrence of the specified element in this
     * list (when traversing the list from head to tail).  If the list
     * does not contain the element, it is unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if the list contained the specified element
     * @since 1.6
     */
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    /**
     * Removes the last occurrence of the specified element in this
     * list (when traversing the list from head to tail).  If the list
     * does not contain the element, it is unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if the list contained the specified element
     * @since 1.6
     */
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
	    if (index < 0 || index > size)
		throw new IndexOutOfBoundsException("Index: "+index+
						    ", Size: "+size);
	    if (index < (size >> 1)) {
		next = header.next;
		for (nextIndex=0; nextIndex<index; nextIndex++)
		    next = next.next;
	    } else {
		next = header;
		for (nextIndex=size; nextIndex>index; nextIndex--)
		    next = next.previous;
	    }
	}

	public boolean hasNext() {
	    return nextIndex != size;
	}

	public E next() {
	    checkForComodification();
	    if (nextIndex == size)
		throw new NoSuchElementException();

	    lastReturned = next;
	    next = next.next;
	    nextIndex++;
	    return lastReturned.element.get(0);
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
	    return lastReturned.element.get(0);
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
                CopyList04.this.remove(lastReturned);
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
	    lastReturned.element.set(0, e);
	}

	public void add(E e) {
	    checkForComodification();
	    lastReturned = header;
	    
	    if(elementListSize < elementListCapacity) {
    		elementList.add(e);
    		elementListSize++;
    	} else {
    		addBefore(elementList, next);
    		elementListSize = 0;
    	}
	    nextIndex++;
	    expectedModCount++;
	}

	final void checkForComodification() {
	    if (modCount != expectedModCount)
		throw new ConcurrentModificationException();
	}
    }

    // element를 ArrayList로 구현
    private static class Entry<E> {
    	ArrayList<E> element;
    	Entry<E> next;
    	Entry<E> previous;

    	Entry(ArrayList<E> element, Entry<E> next, Entry<E> previous) {
    		this.element = element;
    		this.next = next;
    		this.previous = previous;
		}
    }

    private Entry<E> addBefore(ArrayList<E> e, Entry<E> entry) {
		Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
		newEntry.previous.next = newEntry;
		newEntry.next.previous = newEntry;
		
		size++;
		modCount++;
		return newEntry;
    }

    private E remove(Entry<E> e) {
	if (e == header)
	    throw new NoSuchElementException();

        E result = e.element.get(0);
        e.previous.next = e.next;
        e.next.previous = e.previous;
        e.next = e.previous = null;
        e.element = null;
        size--;
        modCount++;
        return result;
    }

    /**
     * @since 1.6
     */
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    /** Adapter to provide descending iterators via ListItr.previous */
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


    public Object[] toArray() {
	Object[] result = new Object[size];
        int i = 0;
        for (Entry<E> e = header.next; e != header; e = e.next)
            result[i++] = e.element;
	return result;
    }


    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);
        int i = 0;
	Object[] result = a;
        for (Entry<E> e = header.next; e != header; e = e.next)
            result[i++] = e.element;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    private static final long serialVersionUID = 876323262645176354L;

    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
	// Write out any hidden serialization magic
	s.defaultWriteObject();

        // Write out size
        s.writeInt(size);

	// Write out all elements in the proper order.
        for (Entry e = header.next; e != header; e = e.next)
            s.writeObject(e.element);
    }


    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
	// Read in any hidden serialization magic
	s.defaultReadObject();

        // Read in size
        int size = s.readInt();

        // Initialize header
        header = new Entry<E>(null, null, null);
        header.next = header.previous = header;

	// Read in all elements in the proper order.
	for (int i=0; i<size; i++) {
		ArrayList<E> readObject = new ArrayList<E>();
		readObject.add((E)s.readObject());
        addBefore(readObject, header);
	}
			
    }

	@Override
	public int getType() {
		return 4;
	}

	@Override
	public void printManual() {
		System.out.println(" : Entry의 element를 ArrayList로 변경, ArrayList 객체 생성");
	}
	
	@Override
	public void addSetting(){
		// Change Sangyun
		Scanner scan = new Scanner(System.in);

		System.out.print("----------------------------------\n1.[최대 10]"
				+ "  2.[최대 100]  3.[최대1000]\n----------------------------------...");
					
		do{
			while(!scan.hasNextInt()){
				System.out.print("잘못된 입력입니다. 다시 입력하세요 : ");
				scan.next();
			}
			limit = scan.nextInt();
			
			if(limit == 1){
				elementListCapacity = 10;
			}
			else if(limit == 2){
				elementListCapacity = 100;
			}
			else if(limit == 3){
				elementListCapacity = 1000;
			}
			else{
				System.out.println("잘못된 입력입니다 >>> 기본값 10 실험 진행");
				limit = 1;
			}
			
		}while(limit == 0);
	}
}
