import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author Nathan Maroko
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head, tail;
	private int size;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		
		if(isEmpty()) {
			head = tail = newNode;
		}else if(size == 1) {
			newNode.setNext(head);
			tail = newNode.getNext();
			head = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
		size++;
		modCount++;
		
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		
		if(isEmpty()) {
			head = tail = newNode;
		} else if(size == 1) {
			head.setNext(newNode);
			tail = newNode;
		} else {
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
		
	}

	@Override
	public void add(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		
		if(isEmpty()) {
			head = tail = newNode;
		}else if(size == 1) {
			head.setNext(newNode);
			tail = newNode;
		} else{
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
		
	}

	@Override
	public void addAfter(T element, T target) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		LinearNode<T> targetTemp = head;
		boolean found = false;
		
		while(targetTemp != null && !found) {
			if(targetTemp.getElement().equals(target)) {
				found = true;
			}else {
				targetTemp = targetTemp.getNext();
			}
		}
		
		if(found == false) {
			throw new NoSuchElementException();
		}
		
		if(targetTemp == tail) {
			targetTemp.setNext(newNode);
			tail = newNode;
		}else {
			newNode.setNext(targetTemp.getNext());
			targetTemp.setNext(newNode);
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) { 
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>(element);
		LinearNode<T> prevTemp = null;
		LinearNode<T> nodeSort = head;
		int currentIndex = 0;
		
			for(int i = 0; i < index; i++) {
				prevTemp = nodeSort;
				nodeSort = nodeSort.getNext();
			}
			
			if(size == 0) {
				
				head = tail = newNode;
				
			}else if(prevTemp == null) {
				
				newNode.setNext(nodeSort);
				head = newNode;
				
			}else if(nodeSort == null) {
				
				prevTemp.setNext(newNode);
				tail = newNode;
				
				}else {
			
			prevTemp.setNext(newNode);
			newNode.setNext(nodeSort);
		}
		
//		if(prevTemp == null) {
//			throw new NoSuchElementException();
//		}
		
		size++;
		modCount++;
		
	}

	@Override
	public T removeFirst() {
		LinearNode<T> tempHead = head;
		
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(size == 1) {
			head = tail = null;
		}
		
		if(size != 0 && size != 1) {
			head = tempHead.getNext();
		}
		modCount++;
		size--;
		
		return tempHead.getElement();
	}

	@Override
	public T removeLast() {
		LinearNode<T> tempTail = tail;
		if(size == 0) {
			
			throw new NoSuchElementException();
		}else if(size == 1) {
			
			head = tail = null;
		}else {
			LinearNode<T> prevNode = null;
			LinearNode<T> currentNode = head;
			
			while(currentNode.getNext() != null) {
				prevNode = currentNode;
				currentNode = currentNode.getNext();
			}
			
			prevNode.setNext(null);
			tail = prevNode;
			
		}
		size--;
		modCount++;
		
		return tempTail.getElement();
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		boolean found = false;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;
		
		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		
		if (!found) {
			throw new NoSuchElementException();
		}
		
		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> prevNode = null;
		LinearNode<T> sortNode = head;
		
		for(int i = 0; i < index; i++) {
			prevNode = sortNode;
			sortNode = sortNode.getNext();
		}
		
		if(size == 1) {
			head = tail = null;
		}
		else if(sortNode == head) {
			head = sortNode.getNext();
		}
		else if(sortNode == tail) {
			tail = prevNode;
			prevNode.setNext(null);
		}else {
			prevNode.setNext(sortNode.getNext());
		}
		size--;
		modCount++;
		
		return sortNode.getElement();
	}

	@Override
	public void set(int index, T element) {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		LinearNode<T> newNode = new LinearNode<T>(element);
		LinearNode<T> prevNode = null;
		LinearNode<T> sortNode = head;
		
		for(int i = 0; i < index; i++) {
			prevNode = sortNode;
			sortNode = sortNode.getNext();
		}
		
		if(size == 1) {
			head = tail = newNode;
		} else if(sortNode == head) {
			head = newNode;
			newNode.setNext(sortNode.getNext());
		} else if(sortNode == tail) {
			prevNode.setNext(newNode);
			tail = newNode;
		}else {
			prevNode.setNext(newNode);
			newNode.setNext(sortNode.getNext());
		}

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
		
		LinearNode<T> targetNode = head;
		
		for(int i = 0; i < index; i++) {
			targetNode = targetNode.getNext();
		}
		
		return targetNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		if (isEmpty()) {
			return -1;
		}
		
		int currentIndex = 0;
		boolean found = false;
		LinearNode<T> current = head;
		
		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				current = current.getNext();
				currentIndex++;
			}
		}
		
		if (!found) {
			return -1;
		}else {
			return currentIndex;
		}
		
		
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
		LinearNode<T> sortNode = head;
		while(found == false && sortNode != null) {
			if(sortNode.getElement().equals(target)) {
				found = true;
			}else {
				sortNode = sortNode.getNext();
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
		LinearNode<T> sortNode = head;
		
		while(sortNode!= null) {
			if(sortNode.getNext() == null) {
				output+= sortNode.getElement();
				break;
			}
			output += sortNode.getElement() +", ";
			sortNode = sortNode.getNext();
		}
		output += "]";
		return output;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private LinearNode<T> nextNode, prevNode;//,prevOG;
		private int iterModCount, currentIndex;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
//			prevOG = null;
			prevNode = null;
			nextNode = head;
			iterModCount = modCount;
			currentIndex = 0;
		}

		@Override
		public boolean hasNext() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			
			if(isEmpty()) {
				return false;
			}
			
			return (nextNode != null);
		}

		@Override
		public T next() {
			if(iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
//				prevOG = prevNode;
				prevNode = nextNode;
				nextNode = nextNode.getNext();
				currentIndex++;

			return prevNode.getElement();
		}
		
		@Override
		public void remove() {
			if(prevNode == null) {
				throw new IllegalStateException();
			}
//			if(size == 1) {
//				head = tail = null;
//			} else if(prevNode == head) {
//				head = nextNode;
//			} else if(prevNode == tail) {
//				tail = prevOG;
//			}else {
//				prevOG.setNext(prevNode.getNext());
//			}
//			
//			
//			size--;
			
			this.remove(currentIndex-1);
		}
		
		private void remove(int index) {
			
			LinearNode<T> prevNode = null;
			LinearNode<T> sortNode = head;
			
			for(int i = 0; i < index; i++) {
				prevNode = sortNode;
				sortNode = sortNode.getNext();
			}
			
			if(size == 1) {
				head = tail = null;
			}
			else if(sortNode == head) {
				head = sortNode.getNext();
			}
			else if(sortNode == tail) {
				tail = prevNode;
				prevNode.setNext(null);
			}else {
				prevNode.setNext(sortNode.getNext());
			}
			currentIndex--;
			size--;
		}
	}
}
