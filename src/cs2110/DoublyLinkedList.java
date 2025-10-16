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

        if (index == 0) { //adding to beginning
            DNode newNode = new DNode(elem, null, head);
            if (head != null) { //list is not empty
                head.prev = newNode;
            } else { //need to update tail if the list was empty
                tail = newNode;
            }
            head = newNode;
        } else if (index == size) { //adding to end
            DNode newNode = new DNode(elem, tail, null);
            tail.next = newNode;
            tail = newNode;
        } else {//regular case, not adding to front or end
            //current = first DNode that will be shifted to the right
            DNode current = findDNodeAtIndex(index);

            DNode newNode = new DNode(elem, current.prev, current);
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
        return findDNodeAtIndex(index).data;

    }


    @Override
    public boolean contains(T elem) {
        return indexOfElem(elem) < size;
    }

    @Override
    public int indexOf(T elem) {
        assert contains(elem);
        return indexOfElem(elem);
    }

    @Override
    public void set(int index, T elem) {
        assert index >= 0 && index < size();
        assert elem != null;
        findDNodeAtIndex(index).data = elem;
    }

    @Override
    public T remove(int index) {
        assert index >= 0 && index < size();
        DNode node = findDNodeAtIndex(index);

        if (index == 0) {
            head = head.next;
            if (size() != 1) {
                node.next.prev = null;
            } else {
                tail = null;
            }
            node.next = null;
        } else if (index == size() - 1) {
            tail = tail.prev;
            if (size() != 1) {
                node.prev.next = null;
            } else {
                head = null;
            }
            node.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
        }
        size--;
        assertInv();
        return node.data;
    }

    @Override
    public void delete(T elem) {
        assert contains(elem);
        remove(indexOfElem(elem));
    }

    /**
     * Returns the DNode at a given index position Preconditions: index >= 0 && index < size
     *
     */
    private DNode findDNodeAtIndex(int index) {
        assert index >= 0 && index < size();
        DNode current = head;

        /*
        Loop invariant: current is the DNode at index i
         */
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Returns the smallest index `i` at which `elem` is stored in this list. If 'elem' is not
     * stored in this list, return size of this list.
     */
    private int indexOfElem(T elem) {
        int i = 0;

        /*
        Loop invariant: all elements in [0,i) are not equal to elem. element is at index i.
         */
        for (T element : this) {
            if (element.equals(elem)) {
                return i;
            }
            i++;
        }
        return i;
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
     * Return an iterator over the elements of this list (in reverse order). To ensure the
     * functionality of this iterator, this list should not be mutated while the iterator is in
     * use.
     */
    public Iterator<T> reverseIterator() {
        return new ReverseIterator();
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

    /**
     * A reverse iterator over a doubly-linked list. Guarantees correct behavior (visits each
     * element exactly once in the correct order) only if the list is not mutated during the
     * lifetime of this iterator.
     */
    private class ReverseIterator implements Iterator<T> {

        /**
         * The node whose value will next be returned by the iterator, or null once the iterator
         * reaches the beginning of the list.
         */
        private DNode nextToVisit;

        public ReverseIterator() {
            nextToVisit = tail;
        }

        @Override
        public boolean hasNext() {
            return (nextToVisit != null);
        }

        @Override
        public T next() {
            T temp = nextToVisit.data;
            nextToVisit = nextToVisit.prev;
            return temp;
        }
    }

}
