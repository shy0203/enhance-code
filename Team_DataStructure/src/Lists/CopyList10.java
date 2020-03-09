package Lists;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CopyList10<E> extends AbstractSequentialList<E> implements
		List<E>, Deque<E>, Cloneable, java.io.Serializable {

	// 첫번째 노드를 가리키는 필드
	public Node<E> head;
	public Node<E> tail;

	public static class Node<E> {
		// 데이터가 저장될 필드
		public E data;
		// 다음 노드를 가리키는 필드
		public Node<E> next;
		public Node<E> prev;

		public Node(E input) {
			this.data = input;
			this.next = null;
			this.prev = null;
		}

		// 노드의 내용을 쉽게 출력해서 확인해볼 수 있는 기능
		public String toString() {
			return String.valueOf(this.data);
		}
	}

	@Override
	public void addFirst(E input) {
		// 노드를 생성합니다.
		Node<E> newNode = new Node<E>(input);
		// 새로운 노드의 next 값은 헤드가 지정한 값을 저장하여 다음 노드 값을 가리킨다.
		newNode.next = head;
		// 헤드에 새로운 노드값을 저장하여 새로운 노드를 가리킨다.
		head = newNode;
		size++;
		// tail의 값을 지정하는 구문; 첫 노드 생성 시 head.next값이 없기 때문에 null 판단으로 head의 값을
		// tail에도 준다.
		// toString()으로 값 비교시 head와 tail의 값은 같게 나온다.
		if (head.next == null) {
			tail = head;
		}
	}

	@Override
	public void addLast(E input) {
		// 노드를 생성합니다.
		Node<E> newNode = new Node<E>(input);
		// 리스트의 노드가 없다면 첫번째 노드를 추가하는 메소드를 사용합니다.
		if (size == 0) {
			addFirst(input);
		} else {
			// 마지막 노드의 다음 노드로 생성한 노드를 지정합니다.
			tail.next = newNode;
			// 마지막 노드를 갱신합니다.
			tail = newNode;
			// 노드 개수 증가
			size++;
		}
	}

	public Node<E> node(int index) {
		Node<E> x = head;
		for (int i = 0; i < index; i++) {
			x = x.next;
		}
		return x;
	}

	@Override
	public void add(int k, E input) {
		// 만약 k가 0이라면 첫번째 노드에 추가하는 것이기 때문에 addFirst를 사용합니다.
		if (k == 0) {
			addFirst(input);
		} else {
			Node<E> temp1 = node(k - 1);
			// k 번째 노드를 temp2로 지정합니다.
			Node<E> temp2 = temp1.next;
			// 새로운 노드를 생성합니다.
			Node<E> newNode = new Node<E>(input);
			// temp1의 다음 노드로 새로운 노드를 지정합니다.
			temp1.next = newNode;
			// 새로운 노드의 다음 노드로 temp2를 지정합니다.
			newNode.next = temp2;
			size++;
			// 새로운 노드의 다음 노드가 없다면 새로운 노드가 마지막 노드이기 때문에 tail로 지정합니다.
			if (newNode.next == head) {
				tail = newNode;
				// 순환 : 마지막 노드를 첫 노드로 잡는다
			}
		}
	}

	@Override
	public String toString() {
		// 노드가 없다면 []를 리턴합니다.
		if (head == null) {
			return "[]";
		}
		// 탐색을 시작합니다.
		Node<E> temp = head;
		String str = "[";
		// 다음 노드가 없을 때까지 반복문을 실행합니다.
		// 마지막 노드는 다음 노드가 없기 때문에 아래의 구문 마지막 노드는 제외됩니다.
		while (temp.next != null) {
			str += temp.data + ",";
			temp = temp.next;
		}
		// 마지막 노드를 출력결과에 포함시킵니다.
		str += temp.data;
		return str + "]";
	}

	@Override
	public E removeFirst() {
		// 첫번째 노드를 temp로 지정하고 head의 값을 두번째 노드로 변경합니다.
		Node<E> temp = head;
		head = temp.next;
		// 데이터를 삭제하기 전에 리턴할 값을 임시 변수에 담습니다.
		E returnData = temp.data;
		temp = null;
		size--;
		return returnData;
	}

	@Override
	public E remove(int k) {
		if (k == 0)
			return removeFirst();
		// k-1 번째 노드를 temp의 값으로 지정합니다.
		Node<E> temp = node(k - 1);
		// 삭제 노드를 todoDeleted에 기록해 둡니다.
		// 삭제 노드를 지금 제거하면 삭제 앞 노드와 삭제 뒤 노드를 연결할 수 없습니다.
		Node<E> todoDeleted = temp.next;
		// 삭제 앞 노드의 다음 노드로 삭제 뒤 노드를지정합니다.
		temp.next = temp.next.next;
		// 삭제된 데이터를 리턴하기 위해서 returnData에 데이터를 저장합니다.
		E returnData = todoDeleted.data;
		if (todoDeleted == tail) {
			tail = temp;
		}
		// cur.next를 삭제 합니다.
		todoDeleted = null;
		size--;
		return returnData;
	}

	@Override
	public E get(int k) {
		return node(k).data;
	}

	@Override
	public int indexOf(Object data) {
		// 탐색 대상이 되는 노드를 temp로 지정합니다.
		Node<E> temp = head;
		// 탐색 대상이 몇번째 엘리먼트에 있느지를 의마하는 변수로 index를 사용합니다.
		int index = 0;
		// 탐색 값과 탐색 대상의 값을 비교합니다.
		while (temp.data != data) {
			temp = temp.next;
			index++;
			// temp의 값이 null이라는 것은 더 이상 탐색 대상이 없다는 것을 의미합니다. 이 때 -1을 리턴합니다.
			if (temp == null)
				return -1;
		}
		return index;
	}

	public void printR(List<E> list, int size) {
		for (int i = 0; i < size; i++) {
			System.out.println("/// index : " + i);
			if (list instanceof CopyList7
					|| list instanceof CopyList8) {
				System.out.println("prev: " + node(i).prev);
			}
			System.out.println("value: " + get(i));
			System.out.println("next: " + node(i).next);
			System.out.println("-----------");
		}
	}

	private transient Entry<E> header = new Entry<E>(null, null, null);
	private transient int size = 0;

	/**
	 * Constructs an empty list.
	 */
	public CopyList10() {
		header.next = header.previous = header;
	}

	/**
	 * Constructs a list containing the elements of the specified collection, in
	 * the order they are returned by the collection's iterator.
	 *
	 * @param c
	 *            the collection whose elements are to be placed into this list
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
	public CopyList10(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	/**
	 * Returns the first element in this list.
	 *
	 * @return the first element in this list
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	public E getFirst() {
		if (size == 0)
			throw new NoSuchElementException();

		return header.next.element;
	}

	/**
	 * Returns the last element in this list.
	 *
	 * @return the last element in this list
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	public E getLast() {
		if (size == 0)
			throw new NoSuchElementException();

		return header.previous.element;
	}

	/**
	 * Removes and returns the first element from this list.
	 *
	 * @return the first element from this list
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	// public E removeFirst() {
	// return remove(header.next);
	// }

	/**
	 * Removes and returns the last element from this list.
	 *
	 * @return the last element from this list
	 * @throws NoSuchElementException
	 *             if this list is empty
	 */
	// public E removeLast() {
	// return remove(header.previous);
	// }

	/**
	 * Inserts the specified element at the beginning of this list.
	 *
	 * @param e
	 *            the element to add
	 */
	// public void addFirst(E e) {
	// addBefore(e, header.next);
	// }

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * <p>
	 * This method is equivalent to {@link #add}.
	 *
	 * @param e
	 *            the element to add
	 */
	// public void addLast(E e) {
	// addBefore(e, header);
	// }

	/**
	 * Returns <tt>true</tt> if this list contains the specified element. More
	 * formally, returns <tt>true</tt> if and only if this list contains at
	 * least one element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 *
	 * @param o
	 *            element whose presence in this list is to be tested
	 * @return <tt>true</tt> if this list contains the specified element
	 */
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list
	 */
	public int size() {
		return size;
	}

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * <p>
	 * This method is equivalent to {@link #addLast}.
	 *
	 * @param e
	 *            element to be appended to this list
	 * @return <tt>true</tt> (as specified by {@link Collection#add})
	 */
	public boolean add(E e) {
//		addBefore(e, header);
	    	// 노드를 생성합니다.
			Node<E> newNode = new Node<E>(e);
			// 새로운 노드의 next 값은 헤드가 지정한 값을 저장하여 다음 노드 값을 가리킨다.
			newNode.next = head;
			// 헤드에 새로운 노드값을 저장하여 새로운 노드를 가리킨다.
			head = newNode;
			size++;
			// tail의 값을 지정하는 구문; 첫 노드 생성 시 head.next값이 없기 때문에 null 판단으로 head의 값을
			// tail에도 준다.
			// toString()으로 값 비교시 head와 tail의 값은 같게 나온다.
			if (head.next == null) {
				tail = head;
			}
	        return true;
	    }

	/**
	 * Removes the first occurrence of the specified element from this list, if
	 * it is present. If this list does not contain the element, it is
	 * unchanged. More formally, removes the element with the lowest index
	 * <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
	 * (if such an element exists). Returns <tt>true</tt> if this list contained
	 * the specified element (or equivalently, if this list changed as a result
	 * of the call).
	 *
	 * @param o
	 *            element to be removed from this list, if present
	 * @return <tt>true</tt> if this list contained the specified element
	 */
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

	/**
	 * Appends all of the elements in the specified collection to the end of
	 * this list, in the order that they are returned by the specified
	 * collection's iterator. The behavior of this operation is undefined if the
	 * specified collection is modified while the operation is in progress.
	 * (Note that this will occur if the specified collection is this list, and
	 * it's nonempty.)
	 *
	 * @param c
	 *            collection containing elements to be added to this list
	 * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
	public boolean addAll(Collection<? extends E> c) {
		return addAll(size, c);
	}

	/**
	 * Inserts all of the elements in the specified collection into this list,
	 * starting at the specified position. Shifts the element currently at that
	 * position (if any) and any subsequent elements to the right (increases
	 * their indices). The new elements will appear in the list in the order
	 * that they are returned by the specified collection's iterator.
	 *
	 * @param index
	 *            index at which to insert the first element from the specified
	 *            collection
	 * @param c
	 *            collection containing elements to be added to this list
	 * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
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

	// Positional Access Operations

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	// public E get(int index) {
	// return entry(index).element;
	// }

	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 *
	 * @param index
	 *            index of the element to replace
	 * @param element
	 *            element to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	public E set(int index, E element) {
		Entry<E> e = entry(index);
		E oldVal = e.element;
		e.element = element;
		return oldVal;
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent
	 * elements to the right (adds one to their indices).
	 *
	 * @param index
	 *            index at which the specified element is to be inserted
	 * @param element
	 *            element to be inserted
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	// public void add(int index, E element) {
	// addBefore(element, (index==size ? header : entry(index)));
	// }

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left (subtracts one from their indices).
	 * Returns the element that was removed from the list.
	 *
	 * @param index
	 *            the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 */
	// public E remove(int index) {
	// return remove(entry(index));
	// }

	/**
	 * Returns the indexed entry.
	 */
	private Entry<E> entry(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ size);
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

	// Search Operations

	/**
	 * Returns the index of the first occurrence of the specified element in
	 * this list, or -1 if this list does not contain the element. More
	 * formally, returns the lowest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 *
	 * @param o
	 *            element to search for
	 * @return the index of the first occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 */
	// public int indexOf(Object o) {
	// int index = 0;
	// if (o==null) {
	// for (Entry e = header.next; e != header; e = e.next) {
	// if (e.element==null)
	// return index;
	// index++;
	// }
	// } else {
	// for (Entry e = header.next; e != header; e = e.next) {
	// if (o.equals(e.element))
	// return index;
	// index++;
	// }
	// }
	// return -1;
	// }

	/**
	 * Returns the index of the last occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element. More formally,
	 * returns the highest index <tt>i</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
	 * or -1 if there is no such index.
	 *
	 * @param o
	 *            element to search for
	 * @return the index of the last occurrence of the specified element in this
	 *         list, or -1 if this list does not contain the element
	 */
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

	// Queue operations.

	/**
	 * Retrieves, but does not remove, the head (first element) of this list.
	 * 
	 * @return the head of this list, or <tt>null</tt> if this list is empty
	 * @since 1.5
	 */
	public E peek() {
		if (size == 0)
			return null;
		return getFirst();
	}

	/**
	 * Retrieves, but does not remove, the head (first element) of this list.
	 * 
	 * @return the head of this list
	 * @throws NoSuchElementException
	 *             if this list is empty
	 * @since 1.5
	 */
	public E element() {
		return getFirst();
	}

	/**
	 * Retrieves and removes the head (first element) of this list
	 * 
	 * @return the head of this list, or <tt>null</tt> if this list is empty
	 * @since 1.5
	 */
	public E poll() {
		if (size == 0)
			return null;
		return removeFirst();
	}

	/**
	 * Retrieves and removes the head (first element) of this list.
	 *
	 * @return the head of this list
	 * @throws NoSuchElementException
	 *             if this list is empty
	 * @since 1.5
	 */
	public E remove() {
		return removeFirst();
	}

	/**
	 * Adds the specified element as the tail (last element) of this list.
	 *
	 * @param e
	 *            the element to add
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
	 * @param e
	 *            the element to insert
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
	 * @param e
	 *            the element to insert
	 * @return <tt>true</tt> (as specified by {@link Deque#offerLast})
	 * @since 1.6
	 */
	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	/**
	 * Retrieves, but does not remove, the first element of this list, or
	 * returns <tt>null</tt> if this list is empty.
	 *
	 * @return the first element of this list, or <tt>null</tt> if this list is
	 *         empty
	 * @since 1.6
	 */
	public E peekFirst() {
		if (size == 0)
			return null;
		return getFirst();
	}

	/**
	 * Retrieves, but does not remove, the last element of this list, or returns
	 * <tt>null</tt> if this list is empty.
	 *
	 * @return the last element of this list, or <tt>null</tt> if this list is
	 *         empty
	 * @since 1.6
	 */
	public E peekLast() {
		if (size == 0)
			return null;
		return getLast();
	}

	/**
	 * Retrieves and removes the first element of this list, or returns
	 * <tt>null</tt> if this list is empty.
	 *
	 * @return the first element of this list, or <tt>null</tt> if this list is
	 *         empty
	 * @since 1.6
	 */
	public E pollFirst() {
		if (size == 0)
			return null;
		return removeFirst();
	}

	/**
	 * Retrieves and removes the last element of this list, or returns
	 * <tt>null</tt> if this list is empty.
	 *
	 * @return the last element of this list, or <tt>null</tt> if this list is
	 *         empty
	 * @since 1.6
	 */
	public E pollLast() {
		if (size == 0)
			return null;
		return removeLast();
	}

	/**
	 * Pushes an element onto the stack represented by this list. In other
	 * words, inserts the element at the front of this list.
	 *
	 * <p>
	 * This method is equivalent to {@link #addFirst}.
	 *
	 * @param e
	 *            the element to push
	 * @since 1.6
	 */
	public void push(E e) {
		addFirst(e);
	}

	/**
	 * Pops an element from the stack represented by this list. In other words,
	 * removes and returns the first element of this list.
	 *
	 * <p>
	 * This method is equivalent to {@link #removeFirst()}.
	 *
	 * @return the element at the front of this list (which is the top of the
	 *         stack represented by this list)
	 * @throws NoSuchElementException
	 *             if this list is empty
	 * @since 1.6
	 */
	public E pop() {
		return removeFirst();
	}

	/**
	 * Removes the first occurrence of the specified element in this list (when
	 * traversing the list from head to tail). If the list does not contain the
	 * element, it is unchanged.
	 *
	 * @param o
	 *            element to be removed from this list, if present
	 * @return <tt>true</tt> if the list contained the specified element
	 * @since 1.6
	 */
	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	/**
	 * Removes the last occurrence of the specified element in this list (when
	 * traversing the list from head to tail). If the list does not contain the
	 * element, it is unchanged.
	 *
	 * @param o
	 *            element to be removed from this list, if present
	 * @return <tt>true</tt> if the list contained the specified element
	 * @since 1.6
	 */
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

	/**
	 * Returns a list-iterator of the elements in this list (in proper
	 * sequence), starting at the specified position in the list. Obeys the
	 * general contract of <tt>List.listIterator(int)</tt>.
	 * <p>
	 *
	 * The list-iterator is <i>fail-fast</i>: if the list is structurally
	 * modified at any time after the Iterator is created, in any way except
	 * through the list-iterator's own <tt>remove</tt> or <tt>add</tt> methods,
	 * the list-iterator will throw a <tt>ConcurrentModificationException</tt>.
	 * Thus, in the face of concurrent modification, the iterator fails quickly
	 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
	 * an undetermined time in the future.
	 *
	 * @param index
	 *            index of the first element to be returned from the
	 *            list-iterator (by a call to <tt>next</tt>)
	 * @return a ListIterator of the elements in this list (in proper sequence),
	 *         starting at the specified position in the list
	 * @throws IndexOutOfBoundsException
	 *             {@inheritDoc}
	 * @see List#listIterator(int)
	 */
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
				CopyList10.this.remove(lastReturned);
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

	/**
	 * Returns a shallow copy of this <tt>LinkedList</tt>. (The elements
	 * themselves are not cloned.)
	 *
	 * @return a shallow copy of this <tt>LinkedList</tt> instance
	 */
	public Object clone() {
		CopyList10<E> clone = null;
		try {
			clone = (CopyList10<E>) super.clone();
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

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element).
	 *
	 * <p>
	 * The returned array will be "safe" in that no references to it are
	 * maintained by this list. (In other words, this method must allocate a new
	 * array). The caller is thus free to modify the returned array.
	 *
	 * <p>
	 * This method acts as bridge between array-based and collection-based APIs.
	 *
	 * @return an array containing all of the elements in this list in proper
	 *         sequence
	 */
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (Entry<E> e = header.next; e != header; e = e.next)
			result[i++] = e.element;
		return result;
	}

	/**
	 * Returns an array containing all of the elements in this list in proper
	 * sequence (from first to last element); the runtime type of the returned
	 * array is that of the specified array. If the list fits in the specified
	 * array, it is returned therein. Otherwise, a new array is allocated with
	 * the runtime type of the specified array and the size of this list.
	 *
	 * <p>
	 * If the list fits in the specified array with room to spare (i.e., the
	 * array has more elements than the list), the element in the array
	 * immediately following the end of the list is set to <tt>null</tt>. (This
	 * is useful in determining the length of the list <i>only</i> if the caller
	 * knows that the list does not contain any null elements.)
	 *
	 * <p>
	 * Like the {@link #toArray()} method, this method acts as bridge between
	 * array-based and collection-based APIs. Further, this method allows
	 * precise control over the runtime type of the output array, and may, under
	 * certain circumstances, be used to save allocation costs.
	 *
	 * <p>
	 * Suppose <tt>x</tt> is a list known to contain only strings. The following
	 * code can be used to dump the list into a newly allocated array of
	 * <tt>String</tt>:
	 *
	 * <pre>
	 * String[] y = x.toArray(new String[0]);
	 * </pre>
	 *
	 * Note that <tt>toArray(new Object[0])</tt> is identical in function to
	 * <tt>toArray()</tt>.
	 *
	 * @param a
	 *            the array into which the elements of the list are to be
	 *            stored, if it is big enough; otherwise, a new array of the
	 *            same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws ArrayStoreException
	 *             if the runtime type of the specified array is not a supertype
	 *             of the runtime type of every element in this list
	 * @throws NullPointerException
	 *             if the specified array is null
	 */
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

	/**
	 * Save the state of this <tt>LinkedList</tt> instance to a stream (that is,
	 * serialize it).
	 *
	 * @serialData The size of the list (the number of elements it contains) is
	 *             emitted (int), followed by all of its elements (each an
	 *             Object) in the proper order.
	 */
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

	/**
	 * Reconstitute this <tt>LinkedList</tt> instance from a stream (that is
	 * deserialize it).
	 */
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

	@Override
	public E removeLast() {
		// TODO Auto-generated method stub
		return null;
	}
}
