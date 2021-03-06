package textgen;

import java.util.AbstractList;
import java.util.ArrayList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// an element will be added to the end of the list
		// 1. throw out exception if the element is null
		add(size, element);
		return true;
		
		/*
		if (element == null)
			throw new NullPointerException();
		// 2. check if it is an empty list
		LLNode<E> curNode = new LLNode<E>(element);
		if (size == 0) {
			size++;
			curNode.next = head.next;
			curNode.prev = tail.prev;
			head.next = curNode;
			tail.prev = curNode;
		}
		else { // add to an existing list
			size++;
			LLNode<E> prevLast = tail.prev;
			curNode.prev = prevLast;
			curNode.next = tail;
			prevLast.next = curNode;
			tail.prev = curNode;
		}
		return true;
		*/
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// check if the index is valid index or not
		if ((index < 0) || (index >= size)) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> curNode = head;
		for (int k=0; k<=index; k++) {
			curNode = curNode.next;
		}
		
		return curNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// 1. check if element is null
		if (element == null)
			throw new NullPointerException();
		// 2. if the index is a valid index
		if ((index < 0) || (index > size))
			throw new IndexOutOfBoundsException();
		
		if (index == size) {
			LLNode<E> prevNode = tail.prev;
			LLNode<E> nextNode = tail;
			LLNode<E> newNode = new LLNode<E>(element, prevNode, nextNode);
			size++;
		}
		else {
			LLNode<E> curNode = head;
			for (int k=0; k<= index; k++) {
				curNode = curNode.next;
			}
			LLNode<E> prevNode = curNode.prev;
			LLNode<E> nextNode = curNode.next;
			LLNode<E> newNode = new LLNode<E>(element, prevNode, nextNode);
			size++;
		}
		/*
		LLNode<E> newNode = new LLNode<E>(element);

		if (index == size) { // add to last existing list
			size++;
			LLNode<E> prevLast = tail.prev;
			newNode.prev = prevLast;
			newNode.next = tail;
			prevLast.next = newNode;
			tail.prev = newNode;
		}
		else {
			LLNode<E> curNode = head;
			for (int k=0; k<= index; k++) {
				curNode = curNode.next;
			}
			LLNode<E> prevNode = curNode.prev;
			newNode.next = curNode;
			newNode.prev = prevNode;
			prevNode.next = newNode;
			curNode.prev = newNode;
			size++;
		}
		*/
		
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		// check if the index is valid index or not
		if ((index < 0) || (index >= size)) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> curNode = head;
		for (int k=0; k<=index; k++) {
			curNode = curNode.next;
		}
		LLNode<E> prevNode = curNode.prev;
		LLNode<E> nextNode = curNode.next;
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
		size--;
		curNode.prev = null;
		curNode.next = null;
		return curNode.data;
	}
	
	public String toString() 
	{
		StringBuilder theList = new StringBuilder();
		LLNode<E> curNode = head.next;
		for (int k=0; k<size; k++) {
			theList.append(", " +curNode.data);
			curNode = curNode.next;
		}
		return theList.toString();
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		E prevData;
		// 1. check validity
		if (element == null)
			throw new NullPointerException();
		if ((index < 0 ) || (index >= size))
			throw new IndexOutOfBoundsException();
		LLNode<E> curNode = head;
		for (int k=0; k<= index; k++) {
			curNode = curNode.next;
		}
		prevData = curNode.data;
		curNode.data = element;
		return prevData;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> prevNode, LLNode<E> nextNode)
	{
		this(e);
		this.next = nextNode;
		this.prev = prevNode;
		prevNode.next = this;
		nextNode.prev = this;
		
	}

}
