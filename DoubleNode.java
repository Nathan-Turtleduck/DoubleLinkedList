/**
 * DoubleNode represents a double linked node in a linked list
 * 
 * @author Nathan Maroko
 */
public class DoubleNode<E> {
	private DoubleNode<E> previous, next;
	private E element;
	
	/**
	 * Constructor for a DoubleNode
	 */
	public DoubleNode() {
		next = previous = null;
		element = null;
	}
	
	/**
	 * Creates a DoubleNode with element passed into it
	 * @param element
	 */
	public DoubleNode(E element) {
		next = previous = null;
		this.element = element;
	}
	
	/**
	 * Returns the next node in the list
	 * @return The next node in the linked list
	 */
	public DoubleNode<E> getNext() {
		return next;
	}
	
	/**
	 * Sets the next Node in the linked list
	 * @param DoubleNode next
	 */
	public void setNext(DoubleNode<E> next) {
		this.next = next;
	}
	
	/**
	 * Returns the previous node in the list
	 * @return DoubleNode previous
	 */
	public DoubleNode<E> getPrevious() {
		return previous;
	}
	
	/**
	 * Sets the previous node in the list
	 * @param DoubleNode previous
	 */
	public void setPrevious(DoubleNode<E> previous) {
		this.previous = previous;
	}
	
	/**
	 * Returns the element the node is storing
	 * @return E element
	 */
	public E getElement() {
		return element;
	}
	
	/**
	 * Sets the current element
	 * @param E element
	 */
	public void setElement(E element) {
		this.element = element;
		
	}
	
	
}
