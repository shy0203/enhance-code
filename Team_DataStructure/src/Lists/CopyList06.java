package Lists;

import java.util.*;

import Data.Data_Lists;

// 자동으로 기준을 생성하는 리스트
public class CopyList06<E> extends AbstractSequentialList<E> 
	implements List<E>, Deque<E>, Cloneable, java.io.Serializable, Data_Lists {

	private transient Node<E> header = new Node<E>(null, null, null);
	private transient int size = 0;
	private ArrayList<StdNode<E>> stdList = new ArrayList<StdNode<E>>();
	private int std;

	// 리스트의 제한성을 통일 시키기 위해 static
	private int limit = -1;

	// Change-DH
	class StdNode<E> {
		private int stdIndex;
		private Node<E> stdNode;

		public StdNode() {
			stdIndex = 0;
			stdNode = null;
		}

		public StdNode(int index, Node<E> Node) {
			stdIndex = index;
			stdNode = Node;
		}
	}

	public CopyList06() {
		header.next = header.previous = header;
		std = 8;
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
			for (Node<E> e = header.next; e != header; e = e.next) {
				if (e.element == null) {
					remove(e);
					return true;
				}
			}
		} else {
			for (Node<E> e = header.next; e != header; e = e.next) {
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

	// Change
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size);
		Object[] a = c.toArray();
		int numNew = a.length;
		if (numNew == 0)
			return false;
		modCount++;

		Node<E> successor = (index == size ? header : Node(index));
		Node<E> predecessor = successor.previous;

		// 매개변수로 컬렉션이 들어오는 경우
		for (int i = 0, std = 8; i < numNew; i++) {
			Node<E> e = new Node<E>((E) a[i], successor, predecessor);

			// 비워있는 경우라면 그냥 i만 해도 되겠지만
			// 기존에 있던 리스트 뒤에 리스트가 추가 되는 경우를 위해서
			// (size + i)를 사용
			if ((size + i) % std == 0)
				stdList.add(new StdNode<E>((size + i), e)); // 새로운 기준 추가

			// 제한이 없는 버전
			if (limit == 1) {
				if (i >= std * 16)
					std *= 16;
			}
			// 제한 있는 버전
			else if (limit == 2)
				if (i >= std * 16 && std < 1000)
					std *= 16;

			predecessor.next = e;
			predecessor = e;
		}
		successor.previous = predecessor;

		size += numNew;
		return true;
	}

	// Change
	public void clear() {
		Node<E> e = header.next;
		while (e != header) {
			Node<E> next = e.next;

			// next와 같은 주소를 참조하는 객체 검색 후 배열에서 제거
			// for(int i = 0, length = stdList.size(); i < length; i++) {
			// if(stdList.get(i).stdNode.equals(next)) {
			// stdList.remove(i);
			// break;
			// }
			// }
			e.next = e.previous = null;
			e.element = null;
			e = next;
		}

		// 이게 더 편함 null 값된 배열 비우는 함수 호출
		// 어차피 clear() 함수는 전부 다 지우는거니까 지울때마다 stdList를 탐색하는 것보단
		// null 값을 가진 stdList를 나중에 비워버리는게 편함
		stdList.clear();
		header.next = header.previous = header;
		size = 0;
		modCount++;
	}

	public E get(int index) {
		return Node(index).element;
	}

	public E set(int index, E element) {
		Node<E> e = Node(index);
		E oldVal = e.element;
		e.element = element;
		return oldVal;
	}

	public void add(int index, E element) {
		addBefore(element, (index == size ? header : Node(index)));
	}

	public E remove(int index) {
		return remove(Node(index));
	}

	// Change-DH
	private Node<E> Node(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size);

		Node<E> e = header;
		if (index == 0)
			return e.next;

		// 기준 리스트의 길이
		int stdLength = stdList.size();

		// 기준 리스트에 값과 비교
		for (int i = 1; i < stdLength; i++) {
			if (stdList.get(i).stdIndex >= index) {
				// 이전 기준점과 다음 기준점과의 거리를 비교해서 이전 기준점이 더 가까우면 이전 기준점부터 앞으로 탐색
				if (Math.abs(stdList.get(i).stdIndex - index) > Math
						.abs(stdList.get(i - 1).stdIndex - index)) {
					e = stdList.get(i - 1).stdNode;
					for (int j = 0; j < Math.abs(stdList.get(i - 1).stdIndex
							- index); j++) {
						e = e.next;
					}
				}
				// 이전 기준점과 다음 기준점과의 거리를 비교해서 다음 기준점이 더 가까우면 다음 기준점부터 뒤로 탐색
				else {
					e = stdList.get(i).stdNode;
					for (int j = 0; j < Math.abs(stdList.get(i).stdIndex
							- index); j++) {
						e = e.previous;
					}
				}
				return e;
			}
		}

		// 기준 리스트의 마지막과 꼬리의 위치를 비교해서 더 가까운 쪽에서부터 탐색
		if (Math.abs(stdList.get(stdLength - 1).stdIndex - index) > (size - index)) {
			;
			for (int j = 0; j < size - index; j++) {
				e = e.previous;
			}
		} else {
			e = stdList.get(stdLength - 1).stdNode;
			// 기준 리스트에 기준이 될만한 인덱스가 없으므로 꼬리에서부터
			for (int i = 0; i < index - stdList.get(stdLength - 1).stdIndex; i++) {
				e = e.next;
			}
		}

		return e;
	}

	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Node<E> e = header.next; e != header; e = e.next) {
				if (e.element == null)
					return index;
				index++;
			}
		} else {
			for (Node<E> e = header.next; e != header; e = e.next) {
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
			for (Node<E> e = header.previous; e != header; e = e.previous) {
				index--;
				if (e.element == null)
					return index;
			}
		} else {
			for (Node<E> e = header.previous; e != header; e = e.previous) {
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
			for (Node<E> e = header.previous; e != header; e = e.previous) {
				if (e.element == null) {
					remove(e);
					return true;
				}
			}
		} else {
			for (Node<E> e = header.previous; e != header; e = e.previous) {
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
		private Node<E> lastReturned = header;
		private Node<E> next;
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
			Node<E> lastNext = lastReturned.next;
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

	private static class Node<E> {
		E element;
		Node<E> next;
		Node<E> previous;

		Node(E element, Node<E> next, Node<E> previous) {
			this.element = element;
			this.next = next;
			this.previous = previous;
		}
	}

	// Change-DH
	private Node<E> addBefore(E e, Node<E> Node) {
		Node<E> newNode = new Node<E>(e, Node, Node.previous);
		newNode.previous.next = newNode;
		newNode.next.previous = newNode;

		// 리스트의 일정 구간마다 기준 추가
		if (size % std == 0) {
			stdList.add(new StdNode<E>(size, newNode));
		}
		size++;

		// 제한이 없는 버전(기준이 되는 수가 계속 늘어남)
		if (limit == 1) {
			if (size >= std * 16)
				std *= 16;
		}
		// 제한 있는 버전 (기준이 되는 수가 일정 수치에서 멈춤)
		else if (limit == 2)
			if (size >= std * 16 && std < 1000)
				std *= 16;

		modCount++;
		return newNode;
	}

	private E remove(Node<E> e) {
		if (e == header)
			throw new NoSuchElementException();

		E result = e.element;
		e.previous.next = e.next;
		e.next.previous = e.previous;

		// 지우고자 하는 기준 탐색해서 제거 후 반복문 종료
		for (int i = 0, length = stdList.size(); i < length; i++) {
			if (stdList.get(i).stdNode.equals(e)) {
				stdList.remove(i);
				break;
			}
		}

		// 전체 배열의 길이가 1 줄기 때문에 모든 기준 인덱스 1 낮춤
		for (int i = 0, length = stdList.size(); i < length; i++) {
			stdList.get(i).stdIndex--;
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
		clone.header = new Node<E>(null, null, null);
		clone.header.next = clone.header.previous = clone.header;
		clone.size = 0;
		clone.modCount = 0;

		// Initialize clone with our elements
		for (Node<E> e = header.next; e != header; e = e.next)
			clone.add(e.element);

		return clone;
	}

	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (Node<E> e = header.next; e != header; e = e.next)
			result[i++] = e.element;
		return result;
	}

	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass()
					.getComponentType(), size);
		int i = 0;
		Object[] result = a;
		for (Node<E> e = header.next; e != header; e = e.next)
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
		for (Node<E> e = header.next; e != header; e = e.next)
			s.writeObject(e.element);
	}

	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		// Read in any hidden serialization magic
		s.defaultReadObject();

		// Read in size
		int size = s.readInt();

		// Initialize header
		header = new Node<E>(null, null, null);
		header.next = header.previous = header;

		// Read in all elements in the proper order.
		for (int i = 0; i < size; i++)
			addBefore((E) s.readObject(), header);
	}

	@Override
	public int getType() {
		return 6;
	}

	@Override
	public void printManual() {
		System.out.println(" : 특정 기준을 자동으로 잡고 탐색하는 구조");
	}
	
	@Override
	public void addSetting() {
		// Change Sangyun
		Scanner scan = new Scanner(System.in);

		System.out.print("---------------------\n1.[제한 X]"
				+ "  2.[제한O]\n---------------------...");
					
		do{
			while(!scan.hasNextInt()){
				System.out.print("잘못된 입력입니다. 다시 입력하세요 : ");
				scan.next();
			}
			limit = scan.nextInt();
						
			if(limit != 1 && limit != 2){
				System.out.println("잘못된 입력입니다  >>> 제한 없는 버전 실험 진행");
				limit = 1;
			}
		}while(limit == -1);
	}
}
