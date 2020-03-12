package Lists;

import java.util.*;

public class CopyList06<E> extends AbstractSequentialList<E> implements
		List<E>, Deque<E>, Cloneable, java.io.Serializable {

	private transient Entry<E> header = new Entry<E>(null, null, null);
	private transient int size = 0;
	private ArrayList<StdEntry> stdList = new ArrayList<StdEntry>();
	private int std;

	// 리스트의 제한성을 통일 시키기 위해 static
	private static int limit = -1;

	// Change-DH
	class StdEntry<E> {
		private int stdIndex;
		private Entry<E> stdEntry;

		public StdEntry() {
			stdIndex = 0;
			stdEntry = null;
		}

		public StdEntry(int index, Entry<E> entry) {
			stdIndex = index;
			stdEntry = entry;
		}
	}

	public CopyList06() {
		header.next = header.previous = header;
		std = 8;

		if (limit == -1) {
			Scanner scan = new Scanner(System.in);

			System.out.print("----------------------\n0.[제한 X]  1.[제한O]\n----------------------:");
			limit = scan.nextInt();
		}
	}

	public CopyList06(int std) {
		header.next = header.previous = header;
		this.std = std;
	}

	public CopyList06(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	public E getFirst() {
		if (size == 0)
			throw new NoSuchElementException();

		return header.next.element;
	}

	public E getLast() {
		if (size == 0)
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
		if (o == null) {
			for (Entry<E> e = header.next; e != header; e = e.next) {
				if (e.element == null) {
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
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size);
		Object[] a = c.toArray();
		int numNew = a.length;
		if (numNew == 0)
			return false;
		modCount++;

		Entry<E> successor = (index == size ? header : entry(index));
		Entry<E> predecessor = successor.previous;
		for (int i = 0; i < numNew; i++) {
			Entry<E> e = new Entry<E>((E) a[i], successor, predecessor);
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
		addBefore(element, (index == size ? header : entry(index)));
	}

	public E remove(int index) {
		return remove(entry(index));
	}

	// Change-DH
	private Entry<E> entry(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size);
		
		Entry<E> e = header;
		if(index == 0)
			return e.next;
		
		// 기준 리스트의 길이
		int stdLength = stdList.size();
		
		// 기준 리스트에 값과 비교
		for(int i = 1; i < stdLength; i++) {
			if (stdList.get(i).stdIndex >= index) {
				// 이전 기준점과 다음 기준점과의 거리를 비교해서 이전 기준점이 더 가까우면 이전 기준점부터 앞으로 탐색
				if(Math.abs(stdList.get(i).stdIndex - index) > Math.abs(stdList.get(i - 1).stdIndex - index)) {
					e = stdList.get(i - 1).stdEntry;
					for(int j = 0; j < Math.abs(stdList.get(i - 1).stdIndex - index); j++) {
						e = e.next;
					}
				}
				// 이전 기준점과 다음 기준점과의 거리를 비교해서 다음 기준점이 더 가까우면 다음 기준점부터 뒤로 탐색
				else {
					e = stdList.get(i).stdEntry;
					for(int j = 0; j < Math.abs(stdList.get(i).stdIndex - index); j++) {
						e = e.previous;
					}
				}
				return e;
			}
		}
		
		// 기준 리스트의 마지막과 꼬리의 위치를 비교해서 더 가까운 쪽에서부터 탐색
		if(Math.abs(stdList.get(stdLength - 1).stdIndex - index) > (size - index) ) {;
			for(int j = 0; j < size - index; j++) {
				e = e.previous;
			}
		}
		else {
			e = stdList.get(stdLength - 1).stdEntry;
			// 기준 리스트에 기준이 될만한 인덱스가 없으므로 꼬리에서부터 
			for(int i = 0; i < index - stdList.get(stdLength - 1).stdIndex; i++) {
				e = e.next;
			}
		}

	
		return e;
	}

	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Entry e = header.next; e != header; e = e.next) {
				if (e.element == null)
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
		if (o == null) {
			for (Entry e = header.previous; e != header; e = e.previous) {
				index--;
				if (e.element == null)
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
		if (size == 0)
			return null;
		return getFirst();
	}

	public E element() {
		return getFirst();
	}

	public E poll() {
		if (size == 0)
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
		if (size == 0)
			return null;
		return getFirst();
	}

	public E peekLast() {
		if (size == 0)
			return null;
		return getLast();
	}

	public E pollFirst() {
		if (size == 0)
			return null;
		return removeFirst();
	}

	public E pollLast() {
		if (size == 0)
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
		if (o == null) {
			for (Entry<E> e = header.previous; e != header; e = e.previous) {
				if (e.element == null) {
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
				throw new IndexOutOfBoundsException("Index: " + index
						+ ", Size: " + size);
			if (index < (size >> 1)) {
				next = header.next;
				for (nextIndex = 0; nextIndex < index; nextIndex++)
					next = next.next;
			} else {
				next = header;
				for (nextIndex = size; nextIndex > index; nextIndex--)
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
			return nextIndex - 1;
		}

		public void remove() {
			checkForComodification();
			Entry<E> lastNext = lastReturned.next;
			try {
				CopyList06.this.remove(lastReturned);
			} catch (NoSuchElementException e) {
				throw new IllegalStateException();
			}
			if (next == lastReturned)
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

	// Change-DH
	private Entry<E> addBefore(E e, Entry<E> entry) {
		Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
		newEntry.previous.next = newEntry;
		newEntry.next.previous = newEntry;

		if (size % std == 0) {
			stdList.add(new StdEntry<E>(size, newEntry));
		}
		size++;

		// 제한이 없는 버전
		if (limit == 0) {
			if (size >= std * 16)
				std *= 16;
		}
		// 제한 있는 버전
		else if (limit == 1)
			if (size >= std * 16 && std < 1000)
				std *= 16;

		modCount++;
		return newEntry;
	}

	private E remove(Entry<E> e) {
		if (e == header)
			throw new NoSuchElementException();

		E result = e.element;
		e.previous.next = e.next;
		e.next.previous = e.previous;
		for (int i = 0; i < stdList.size(); i++) {
			if (stdList.get(i).stdEntry.equals(e)) {
				stdList.remove(i);
				break;
			}
		}
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
		CopyList06<E> clone = null;
		try {
			clone = (CopyList06<E>) super.clone();
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
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass()
					.getComponentType(), size);
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
		for (int i = 0; i < size; i++)
			addBefore((E) s.readObject(), header);
	}
}
