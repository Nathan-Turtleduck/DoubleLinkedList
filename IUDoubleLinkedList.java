import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Double-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented and
 * ListIterator is supported.
 * 
 * @author Nathan Maroko
 * 
 * @param <T> type to store
 */

public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

	private DoubleNode<T> head, tail;
	private int size;
	private int modCount;
	
	/**
	 * Creates an empty list
	 */
	public IUDoubleLinkedList() {
		
		head = tail = null;
		size = 0;
		modCount = 0;
		
	}
	
	@Override
	public void addToFront(T element) {
		DoubleNode<T> newNode = new DoubleNode<T>(element);
		
		if(isEmpty()) {
			head = tail = newNode;
		}  else {
			head.setPrevious(newNode);
			newNode.setNext(head);
			head = newNode;
		}
		
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		DoubleNode<T> newNode = new DoubleNode<T>(element);
		if(isEmpty()) {
			head = tail = newNode;
		} else {
			tail.setNext(newNode);
			newNode.setPrevious(tail);
			tail = newNode;
		}

		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		DoubleNode<T> newNode = new DoubleNode<T>(element);
		if(isEmpty()) {
			head = tail = newNode;
		} else {
			tail.setNext(newNode);
			newNode.setPrevious(tail);
			tail = newNode;
		}

		size++;
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		DoubleNode<T> newNode = new DoubleNode<T>(element);
		
		boolean found = false;
		DoubleNode<T> current = head;

		while(found != true && current != null) {
			if(current.getElement() == target) {
				found = true;
			}else {
				current = current.getNext();
			}
		}
		
		if(found == false) {
			throw new NoSuchElementException();
		}
		
		if(current == tail) {
			current.setNext(newNode);
			newNode.setPrevious(current);
			tail = newNode;
		} else {
			newNode.setNext(current.getNext());
			current.getNext().setPrevious(current);
			current.setNext(newNode);
			newNode.setPrevious(current);
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		DoubleNode<T> newNode = new DoubleNode<T>(element);
		DoubleNode<T> indexNode = head;
		
		for(int i = 0; i < index; i++) {
			indexNode = indexNode.getNext();
		}

		if (indexNode == head) {
			addToFront(element);
		}else if(indexNode == null) {
			addToRear(element);
		} else {
			indexNode.getPrevious().setNext(newNode);
			newNode.setPrevious(indexNode.getPrevious());
			indexNode.setPrevious(newNode);
			newNode.setNext(indexNode);
			size++;
			modCount++;
		}
	}

	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		DoubleNode<T> retVal = head;
		
		if(size == 1) {
			head = tail = null;
		}else {
			head = head.getNext();
			head.setPrevious(null);
		}
		
		
		size--;
		modCount++;
		return retVal.getElement();
	}

	@Override
	public T removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		DoubleNode<T> retVal = tail;
		
		if(size == 1) {
			head = tail = null;
		}else {
			tail = tail.getPrevious();
			tail.setNext(null);
		}
		
		
		size--;
		modCount++;
		return retVal.getElement();
	}

	@Override
	public T remove(T element) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		boolean found = false;
		DoubleNode<T> nodeSort = head;
		
		while(found != true && nodeSort != null) {
			if(nodeSort.getElement() == element) {
				found = true;
			} else {
				nodeSort = nodeSort.getNext();
			}
		}
		
		if(found == false) {
			throw new NoSuchElementException();
		}
		
		if(nodeSort == head) {
			removeFirst();
		} else if(nodeSort == tail) {
			removeLast();
		} else {
			nodeSort.getPrevious().setNext(nodeSort.getNext());
			nodeSort.getNext().setPrevious(nodeSort.getPrevious());
			size--;
			modCount++;
		}
		
		return nodeSort.getElement();
	}

	@Override
	public T remove(int index) {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		DoubleNode<T> indexNode = head;
		
		for(int i = 0; i < index; i++) {
			indexNode = indexNode.getNext();
		}
		
		if(indexNode == head) {
			removeFirst();
		} else if(indexNode == tail) {
			removeLast();
		}else {
			indexNode.getPrevious().setNext(indexNode.getNext());
			indexNode.getNext().setPrevious(indexNode.getPrevious());
			size--;
			modCount++;
		}

		
		return indexNode.getElement();
	}

	@Override
	public void set(int index, T element) {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		DoubleNode<T> indexNode = head;
		
		for(int i = 0; i < index; i++) {
			indexNode = indexNode.getNext();
		}
		
		indexNode.setElement(element);

		modCount++;
	}

	@Override
	public T get(int index) {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		DoubleNode<T> indexNode = head;
		
		for(int i = 0; i < index; i++) {
			indexNode = indexNode.getNext();
		}
		
		return indexNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		if(isEmpty()) {
			return -1;
		}
		
		boolean found = false;
		DoubleNode<T> indexNode = head;
		int currentIndex = 0;
		
		while(found != true && indexNode != null) {
			if(indexNode.getElement() == element) {
				found = true;
			} else {
				indexNode = indexNode.getNext();
				currentIndex++;
			}
		}
		
		if(found == false) {
			return -1;
		}
		
		return currentIndex;
	}

	@Override
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		if(isEmpty()) {
			return false;
		}
		
		boolean found = false;
		DoubleNode<T> indexNode = head;
		
		while(found != true && indexNode != null) {
			if(indexNode.getElement() == target) {
				found = true;
			} else {
				indexNode = indexNode.getNext();
			}
		}
		
		return found;
	}

	@Override
	public boolean isEmpty() {
		
		return (size == 0);
	}

	@Override
	public int size() {

		return size;
	}

	public String toString() {
		String output = "[";
		
		DoubleNode<T> sortNode = head;
		
		while(sortNode != null) {
			if(sortNode == tail) {
				output += sortNode.getElement();
				sortNode = sortNode.getNext();
			} else {
				output += sortNode.getElement() + ", ";
				sortNode = sortNode.getNext();
			}
		}
		
		output += "]";
		return output;
	}
	
	@Override
	public Iterator<T> iterator() {
		DLLIterator iter = new DLLIterator();
		
		return iter;
	}

	private class DLLIterator implements Iterator<T>{
		private DoubleNode<T> next, previous, lastReturned;
		private int iterModCount, currentIndex, lastCalled; // 0 if remove, 1 if next
		
		public DLLIterator() {
			lastReturned = null;
			previous = null;
			next = head;
			iterModCount = modCount;
			currentIndex = 0;
			lastCalled = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			if(isEmpty()) {
				return false;
			}
			
			return (next != null);
		}

		@Override
		public T next() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			previous = next;
			next = next.getNext();
			currentIndex++;
			lastReturned = previous;
			lastCalled = 1;
			
			return previous.getElement();
		}
		
		public void remove() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastCalled == 0) {
				throw new IllegalStateException();
			}
			if(currentIndex == 0) {
				throw new IllegalStateException();
			}
			
			remove(lastReturned.getElement());
			currentIndex--;
			lastCalled = 0;
			
		}
		
		private void remove(T element) {
			if(isEmpty()) {
				throw new NoSuchElementException();
			}
			
			boolean found = false;
			DoubleNode<T> nodeSort = head;
			
			while(found != true && nodeSort != null) {
				if(nodeSort.getElement() == element) {
					found = true;
				} else {
					nodeSort = nodeSort.getNext();
				}
			}
			
			if(found == false) {
				throw new NoSuchElementException();
			}
			
			if(nodeSort == head) {
				removeFirst();

			} else if(nodeSort == tail) {
				removeLast();

			} else {
				nodeSort.getPrevious().setNext(nodeSort.getNext());
				nodeSort.getNext().setPrevious(nodeSort.getPrevious());
				size--;
			}
		}
		
		private void removeFirst() {
			if(isEmpty()) {
				throw new NoSuchElementException();
			}
			
			if(size == 1) {
				head = tail = null;
			}else {
				head = head.getNext();
				head.setPrevious(null);
			}
			
			
			size--;
		}
		
		private void removeLast() {
			if(isEmpty()) {
				throw new NoSuchElementException();
			}
			
			DoubleNode<T> retVal = tail;
			
			if(size == 1) {
				head = tail = null;
			}else {
				tail = tail.getPrevious();
				tail.setNext(null);
			}
			size--;
		}
	}
	
	@Override
	public ListIterator<T> listIterator() {
		DLListIterator iter = new DLListIterator();
		
		return iter;
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		DLListIterator iter = new DLListIterator(startingIndex);
		
		return iter;
	}
	
	private class DLListIterator implements ListIterator<T>{
		
		private DoubleNode<T> next, previous, lastReturned;
		private int iterModCount, currentIndex, startingIndex, lastCalled; // 0 if remove is called, 2 if add is called, 1 if next is called, -1 if previous is called
		// 3 if a starting Index is given
		private boolean givenIndex;
		public DLListIterator() {
			lastReturned = null;
			previous = null;
			next = head;
			iterModCount = modCount;
			currentIndex = 0;
			lastCalled = 0;
			startingIndex = 0;
			givenIndex = false;
		}
		
		public DLListIterator(int startingIndex) {
			if(startingIndex < 0 || startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}else {
				this.startingIndex = startingIndex;
				currentIndex = startingIndex;
				next = head;
				for(int i = 0; i < startingIndex; i++) {
					previous = next;
					next = next.getNext();
				}
				lastReturned = previous;
			}
			givenIndex = true;
//			lastCalled = 1;
			iterModCount = modCount;
		}
		
		@Override
		public boolean hasNext() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			if(isEmpty()) {
				return false;
			}
			
			return (next != null);
		}

		@Override
		public T next() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			previous = next;
			next = next.getNext();
			currentIndex++;
			lastCalled = 1;
			lastReturned = previous;
			
			return previous.getElement();
		}

		@Override
		public boolean hasPrevious() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			if(isEmpty()) {
				return false;
			}
			
			return (previous != null);
		}

		@Override
		public T previous() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			
			next = previous;
			previous = next.getPrevious();
			currentIndex--;
			lastCalled = -1;
			lastReturned = next;
			
			return next.getElement();
		}

		@Override
		public int nextIndex() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			return currentIndex;
		}

		@Override
		public int previousIndex() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			return currentIndex-1;
		}

		@Override
		public void remove() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
//			if(givenIndex == true) {
//				remove(currentIndex);
//				currentIndex--;
//				lastCalled = 0;
//			givenIndex = false;
//				return;
//			}
			
			if(lastCalled == 0) {
				throw new IllegalStateException();
			}
			if(lastCalled == 1) {
				remove(currentIndex-1);
				currentIndex--;
				lastCalled = 0;
				return;
			}
			if(lastCalled == -1) {
				remove(currentIndex);
				lastCalled = 0;
				return;
			}
			if(lastCalled == 2) {
				throw new IllegalStateException();
			}
//			if(startingIndex > 0) {
//				remove(currentIndex);
//				currentIndex--;
//				lastCalled = 0;
//				return;
//			}
			

		}

		@Override
		public void set(T e) {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(lastCalled == -1) {
				lastReturned.setElement(e);
			}
			if(lastCalled == 1) {
				lastReturned.setElement(e);
			}
//			if(givenIndex == true) {
//				lastReturned.setElement(e);
//				return;
//			}
			if(lastCalled == 0) {
				throw new IllegalStateException();
			}
			if(lastCalled == 2) {
				throw new IllegalStateException();
			}
			
		}

		@Override
		public void add(T e) {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			DoubleNode<T> newNode = new DoubleNode<T>(e);
			
			if(lastCalled == 1) {
				add(currentIndex, newNode);
				currentIndex++;
				}else if(lastCalled == -1) {
					add(currentIndex, newNode);
					currentIndex++;
				}

			
			lastCalled = 2;
			
			
		}
		
		private void remove(int index) {
			if(isEmpty()) {
				throw new NoSuchElementException();
			}
			
			if(index > size || index < 0) {
				throw new IndexOutOfBoundsException();
			}
			
			DoubleNode<T> indexNode = head;
			
			for(int i = 0; i < index; i++) {
				indexNode = indexNode.getNext();
			}
			
			if(indexNode == head) {
				removeFirst();
				modCount--;
			} else if(indexNode == tail) {
				removeLast();
				modCount--;
			}else {
				indexNode.getPrevious().setNext(indexNode.getNext());
				indexNode.getNext().setPrevious(indexNode.getPrevious());
				size--;
			}
		}
		
		private void add(int index, DoubleNode<T> newNode) {
			if(index > size || index < 0) {
				throw new IndexOutOfBoundsException();
			}
			
			DoubleNode<T> indexNode = head;
			
			for(int i = 0; i < index; i++) {
				indexNode = indexNode.getNext();
			}
			
			if (indexNode == head) {
				addToFront(newNode.getElement());
				modCount--;
			}else if(indexNode == null) {
				addToRear(newNode.getElement());
				modCount--;
			} else {
				indexNode.getPrevious().setNext(newNode);
				newNode.setPrevious(indexNode.getPrevious());
				indexNode.setPrevious(newNode);
				newNode.setNext(indexNode);
				size++;

			}
		}
		
	}
}

	


