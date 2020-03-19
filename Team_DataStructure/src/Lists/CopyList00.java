package Lists;

import java.util.*;

import Data.Data_Lists;

public class CopyList00<E> extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable, Data_Lists{
	
    private transient Entry<E> header = new Entry<E>(null, null, null);
    private transient int size = 0;

    public CopyList00() {
        header.next = header.previous = header;
    }

    public CopyList00(Collection<? extends E> c) {
	this();
	addAll(c);
    }

    public E getFirst() {
	if (size==0)
	    throw new NoSuchElementException();

	return header.next.element;
    }

    public E getLast()  {
	if (size==0)
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
	return size;
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
        return addAll(size, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+size);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew==0)
            return false;
	modCount++;

        Entry<E> successor = (index==size ? header : entry(index));
        Entry<E> predecessor = successor.previous;
	for (int i=0; i<numNew; i++) {
            Entry<E> e = new Entry<E>((E)a[i], successor, predecessor);
            predecessor.next = e;
            predecessor = e;
        }
        successor.previous = predecessor;

        size += numNew;
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
        size = 0;
	modCount++;
    }

    public E get(int index) {
        return entry(index).element;
    }

    public E set(int index, E element) {
        Entry<E> e = entry(index);
        E oldVal = e.element;
        e.element = element;
        return oldVal;
    }

    public void add(int index, E element) {
        addBefore(element, (index==size ? header : entry(index)));
    }

    public E remove(int index) {
        return remove(entry(index));
    }

    private Entry<E> entry(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+size);
        Entry<E> e = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = size; i > index; i--)
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

    public E peek() {
        if (size==0)
            return null;
        return getFirst();
    }
    
    // Change-DH
    public E sequentialGet() {
    	if(size == 0)
    		return null;
    	// 원래 리스트의 헤더를 꼬리로 보냄
    	E e = HeaderToTail(this);
    	
    	return e;
    }   
    
    // Change-DH
    /* 리스트를 인덱스 만큼 변경시켜서 값을 얻고 다시 원 상태로 돌리는 함수
     * 헤더의 다음 위치를 인덱스 수 만큼 꼬리로 보내서 그 위치의 값을 얻은 후에
     * 다시 꼬리를 헤더의 다음 위치로 회전시켜서 데이터의 변경을 막는다 
     */
    public E rotationGet(int index) {
    	if(size == 0)
    		return null;
    	
    	E e = null;
    	
    	if(index == 0)
    		return header.next.element;
    	
    	for(int i = 0; i <= index; i++) 
        	e = HeaderToTail(this);   	
    	
    	for(int i = 0; i <= index; i++)
    		TailToHeader(this);
    	return e;
    }
    
    // Change-DH
    /* 원래 리스트를 복사한 후에 복사된 리스트를 회전시켜서 값을 얻음
     * 그 후에 복사된 리스트는 버리고 값만을 얻기 때문에 기존 리스트의 데이터의 변경이 없음
     * 복사하는 시간 때문에 반복문에 사용 불가능 
     */
    public E copyRotationGet(int index) {
    	if(size == 0)
    		return null;
    	// 현재 리스트 깊은 복사
    	CopyList00<E> tempList = (CopyList00<E>) this.clone();
    	
    	// 인덱스가 0이면 바로 헤더의 다음 요소 반환
    	if(index == 0)
    		return tempList.header.next.element;
    	 	
    	E e = null;
    	
    	for(int i = 0; i <= index; i++) {
    		e = HeaderToTail(tempList);
    	}  	
    	return e;
    }
 
    // Change-DH
    /* 매개변수로 받은 객체의 리스트를 돌림
     * 헤더의 다음 요소를 꼬리로 회전시키는 함수
     * 헤더, 다음 노드(인덱스 0), 그 다음 노드(인덱스 1)..... 꼬리(마지막 인덱스)
     */
    public E HeaderToTail(CopyList00<E> tempList) {
    	
    	/* 헤더와 인덱스1 노드 사이에 있는 인덱스0 노드를 빼내고
    	 * 헤더와 인덱스1 노드를 연결하는 과정
    	 */
    	// 임시 노드에 인덱스0 노드 저장
    	Entry<E> temp = tempList.header.next;
    	// 리스트의 헤더의 다음 주소에  인덱스1 저장
    	tempList.header.next = temp.next;
    	// 인덱스1의 이전 주소에 헤더 저장
    	temp.next.previous = tempList.header;   	
    	
    	/* 헤더와 꼬리 사이에 위에서 빼낸 
    	 * 인덱스0 노드를 추가하는 과정
    	 */
    	// 인덱스0의 다음 위치는 헤더
    	temp.next = tempList.header;
    	// 리스트의 기존 꼬리 뒤에 인덱스0 노드 저장
    	tempList.header.previous.next = temp;
    	// 인덱스0 노드의 이전 주소에 기존 꼬리 주소 저장
    	temp.previous = tempList.header.previous;
    	// 리스트의 꼬리에 인덱스0 노드 저장
    	tempList.header.previous = temp;
    	
    	// 인덱스0의 값 반환(위치는 꼬리인 상태임)
    	return temp.element;
    }
    

    // Change-DH
    /* 위의 함수의 정 반대과정
     * 꼬리 노드를 헤더의 다음 노드로 변경해주는 함수
     * 설명은 위의 정 반대 과정이니 생략
     */
    public E TailToHeader(CopyList00<E> tempList) {
    	Entry<E> temp = tempList.header.previous;
    	tempList.header.previous = temp.previous;
		temp.previous.next = tempList.header;

		temp.next = tempList.header.next;
		tempList.header.next = temp;
		temp.next.previous = temp;
		temp.previous = tempList.header;
		
		return temp.element;
    }
       
    public E element() {
        return getFirst();
    }

    public E poll() {
        if (size==0)
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
        if (size==0)
            return null;
        return getFirst();
    }

    public E peekLast() {
        if (size==0)
            return null;
        return getLast();
    }

    public E pollFirst() {
        if (size==0)
            return null;
        return removeFirst();
    }


    public E pollLast() {
        if (size==0)
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
                CopyList00.this.remove(lastReturned);
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
	size++;
	modCount++;
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
	size--;
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
        CopyList00<E> clone = null;
	try {
	    clone = (CopyList00<E>) super.clone();
	} catch (CloneNotSupportedException e) {
	    throw new InternalError();
	}

        // Put clone into "virgin" state
        clone.header = new Entry<E>(null, null, null);
        clone.header.next = clone.header.previous = clone.header;
        clone.size = 0;
        clone.modCount = 0;

        // Initialize clone with our elements
        for (Entry<E> e = header.next; e != header; e = e.next)
            clone.add(e.element);

        return clone;
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
	for (int i=0; i<size; i++)
            addBefore((E)s.readObject(), header);
    }

    @Override
	public void printManual() {
		System.out.println(" : LinkedList.class");
	}

	@Override
	public void addSetting() {
		// TODO Auto-generated method stub
	}

}
