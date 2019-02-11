package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.List;
/*
Author Ethan Baumgartner with fragments from book and Isaac Griffith
 */
public class DoublyLinkedList<E> implements List<E> {
    // Class from book examples
    private class Node<E> {

        private E data;
        private Node<E> next;
        private Node<E> prev;
        public Node(E e, Node<E> p, Node<E> n){
            data = e;
            prev = p;
            next = n;
        }
        public E getData() {
            return data;
        }
        public Node<E> getNext() {return next;}
        public Node<E> getPrev() {return prev;}
        public void setNext(Node<E> n) {next = n;}
        public void setPrev(Node<E> p) {prev = p;}
    }
    private Node<E> header;
    private Node<E> trailer;
    private  int size = 0;
    // From the book examples
    public DoublyLinkedList(){
        header = new Node<>(null,null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }
    /**
     * @return first element in the list or null if the list is empty.
     */
    public E first(){
        if (isEmpty()) return null;
        return header.getNext().getData();
    }
    /**
     * @return last element in the list or null if the list is empty.
     */
    public E last(){
        if (isEmpty()) return null;
        return trailer.getPrev().getData();
    }
    /**
     * Adds the provided element to the end of the list, only if the element is
     * not null.
     * Code based off Book examples for doublyLinkedList
     * @param element Element to be added to the end of the list.
     */
    public void addLast(E element){
        if (element != null){
            addBetween(element,trailer.getPrev(), trailer);
        }
    }
    /**
     * Adds the provided element to the front of the list, only if the element
     * is not null.
     * Code based off book examples for doublylinked lists
     * @param element Element to be added to the front of the list.
     */
    public void addFirst(E element) {
        if (element != null) {
            addBetween(element, header, header.getNext());
        }
    }
    /**
     * Removes the element at the front of the list.
     * Example from the book
     * @return Element at the front of the list, or null if the list is empty.
     */
    public E removeFirst(){
        if (isEmpty()){
            return null;
        }
        return remove(header.getNext());
    }

    /**
     * Removes the element at the end of the list.
     *
     * @return Element at the end of the list, or null if the list is empty.
     * Example from the book
     */
    public E removeLast(){
        if (isEmpty()){
            return null;
        }
        return remove(trailer.getPrev());
    }

    /**
     * Inserts the given element into the list at the provided index. The
     * element will not be inserted if either the element provided is null or if
     * the index provided is less than 0. If the index is greater than or equal
     * to the current size of the list, the element will be added to the end of
     * the list.
     *
     * @param element Element to be added (as long as it is not null).
     * @param index Index in the list where the element is to be inserted.
     */
    public void insert(E element, int index) {
        if (element == null || index < 0)
            return;
        if (index >= size){
            addLast(element);
            return;
        }
        else {
            Node<E> insert = header;
            for (int j = 0; j < index -1 ; j++) {
                insert = insert.getNext();
            }
            Node<E> newInsert = new Node<>(element, null,null);
            newInsert.setNext(insert.getNext());
            insert.setNext(newInsert);
            size++;
        }
    }

    /**
     * Removes the element at the given index and returns the value.
     * Code based off in-class example made to work with doublylinkedlist
     * @param node Node of element to remove
     * @return The value of the element at the given index, or null if the index
     * is greater than or equal to the size of the list or less than 0.
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<E> current = header;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        Node<E> nodeRemove = current.getNext();
        Node<E> nodeNext = nodeRemove.getNext();
        current.setNext(nodeNext);
        nodeNext.setPrev(current);
        size--;
        return nodeRemove.getData();
    }
    /**
     * Retrieves the value at the specified index. Will return null if the index
     * provided is less than 0 or greater than or equal to the current size of
     * the list.
     * Same as singlylinkedlist code
     * @param index Index of the value to be retrieved.
     * @return Element at the given index, or null if the index is less than 0
     * or greater than or equal to the list size.
     */
    public E get(int index){
        if(isEmpty() || index < 0 || index >= size)
            return null;
        Node<E> getNode = header;
        for (int i = 0; i < index; i++){
            getNode = getNode.getNext();
        }
        return  getNode.getNext().getData();
    }
    /**
     * @return The current size of the list. Note that 0 is returned for an
     * empty list.
     */
    public int size(){return size;}

    /**
     * @return true if there are no items currently stored in the list, false
     * otherwise.
     */
    public boolean isEmpty(){
        if(size == 0)
            return true;
        else{
            return false;
        }
    }
    /**
     * Prints the contents of the list each item on its own line
     */
    public void printList(){
        if (isEmpty()) return;
        Node<E> text = header;
        for(int i = 0; i < size; i++ ){
            text = text.getNext();
            System.out.println(text.getData().toString());
        }
    }
    // Exaample from the book
    public void addBetween(E e, Node<E> predecessor,Node<E> successor){
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
    }
    // Example from book
    private E remove(Node<E> node){
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        return node.data;
    }
}
