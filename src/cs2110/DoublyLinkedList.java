package cs2110;

import java.util.Iterator;

/**
 * A list of elements of type `T` implemented using a doubly-linked chain. `null` may not be stored
 * as an element in this list.
 */
public class DoublyLinkedList<T> implements CS2110List<T> {

    /**
     * A node of a doubly-linked list whose elements have type `T`.
     */
    private class DNode {

        /**
         * The element in this node.
         */
        T data;

        /**
         * The next node in the chain (or null if this is the last node).
         */
        DNode next;

        /**
         * The previous node in the chain (or null if this is the first node).
         */
        DNode prev;

        /**
         * Create a Node containing element `elem`, pointing backward to node 'prev' (may be null),
         * and pointing forward to node `next` (may be null).
         */
        DNode(T elem, DNode prev, DNode next) {
            data = elem;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * The number of elements in the list.  Equal to the number of linked nodes reachable from
     * `head` (including `head` itself) using `next` arrows.
     */
    private int size;

    /**
     * The first node of the doubly-linked list (null for empty list). `head.prev` must be null
     */
    private DNode head;

    /**
     * The last node of the doubly-linked list (null for empty list). `tail.next` must be null.
     */
    private DNode tail;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert size >= 0;
        if (size == 0) {
            assert head == null;
            assert tail == null;
        } else {
            assert head != null;
            assert head.prev == null;
            assert tail != null;

            int lengthSoFar = 0;
            DNode current = head;
            DNode prevNode = null;
            while (current != null) {
                assert current.data != null;
                assert current.prev == prevNode;
                if (current != tail) {
                    assert current.next.prev == current;
                }
                if (current != head) {
                    assert current.prev.next == current;
                }
                prevNode = current;
                current = current.next;
                lengthSoFar++;
            }
            assert prevNode == tail;
            assert lengthSoFar == size();

        }
    }

    /**
     * Constructs an empty list.
     */
    public DoublyLinkedList() {
        size = 0;
        head = null;
        tail = null;
        assertInv();
    }

    /**
     * Returns the DNode at a given index position Preconditions: index >= 0 && index < size
     *
     */
    private DNode findDNode(int index) {
        assert index >= 0 && index < size();
        DNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    @Override
    public void add(T elem) {
        assert elem != null;
        insert(size, elem);
        assertInv();
    }

    @Override
    public void insert(int index, T elem) {
        assert elem != null;
        assert index >= 0 && index <= size();

        if (index == 0){ //adding to beginning
            DNode newNode = new DNode(elem, null, head);
            if (head != null){ //list is not empty
                head.prev = newNode;
            }
            else{ //need to update tail if the list was empty
                tail = newNode;
            }
            head = newNode;
        }
        else if(index == size){ //adding to end
            DNode newNode = new DNode(elem, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        else {//regular case, not adding to front or end
            //current = first DNode that will be shifted to the right
            DNode current = findDNode(index);

            DNode newNode = new DNode(elem,current.prev, current);
            current.prev.next = newNode;
            current.prev = newNode;

        }
        size++;
        assertInv();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        assert index >= 0 && index < size();
        return findDNode(index).data;

    }

    @Override
    public boolean contains(T elem) {
        // TODO 2d: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(T elem) {
        // TODO 2e: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, T elem) {
        // TODO 2f: Implement this method according to its specifications.
    }

    @Override
    public T remove(int index) {
        // TODO 2g: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(T elem) {
        // TODO 2h: Implement this method according to its specifications.
    }

    /**
     * Return an iterator over the elements of this list (in forward order). To ensure the
     * functionality of this iterator, this list should not be mutated while the iterator is in
     * use.
     */
    @Override
    public Iterator<T> iterator() {
        return new ForwardIterator();
    }


    /**
     * A forward iterator over a doubly-linked list. Guarantees correct behavior (visits each
     * element exactly once in the correct order) only if the list is not mutated during the
     * lifetime of this iterator.
     */
    private class ForwardIterator implements Iterator<T> {

        /**
         * The node whose value will next be returned by the iterator, or null once the iterator
         * reaches the end of the list.
         */
        private DNode nextToVisit;

        /**
         * Constructs a new ForwardIterator over this list
         */
        public ForwardIterator() {
            nextToVisit = head;
        }

        @Override
        public boolean hasNext() {
            return nextToVisit != null;
        }

        @Override
        public T next() {
            T elem = nextToVisit.data;
            nextToVisit = nextToVisit.next;
            return elem;
        }
    }

}
