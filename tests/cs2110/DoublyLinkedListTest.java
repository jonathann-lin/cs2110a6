package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class DoublyLinkedListTest extends ListTest {


    @Override
    public <T> CS2110List<T> constructList() {
        return new DoublyLinkedList<T>();
    }


    @DisplayName("reverseIterator()")
    @Nested
    class DoublyLinkedListReverseIteratorTest {

        @DisplayName(
                "WHEN we construct a reverse iterator over an empty list and call `hasNext()`, "
                        + "THEN the iterator should return `false`.")
        @Test
        public void testReverseIteratorEmpty() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            Iterator<Integer> it = list.reverseIterator();
            assertFalse(it.hasNext());
        }

        @DisplayName(
                "WHEN we construct a reverse iterator over a list with one element, THEN `hasNext()`"
                        + "should initially be `true`, calling `next()` should return this element, and "
                        + "after this `hasNext()` should return `false`.")
        @Test
        public void testReverseIterator1Element() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(3);
            Iterator<Integer> it = list.reverseIterator();
            assertTrue(it.hasNext());
            assertEquals(3, it.next());
            assertFalse(it.hasNext());
        }

        @DisplayName(
                "WHEN we construct a reverse iterator over a list with multiple elements, THEN the "
                        + "elements are produced in the correct (decreasing index) order, with each being "
                        + "returned exactly once.")
        @Test
        public void testReverseIteratorMultipleElements() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            Iterator<Integer> it = list.reverseIterator();
            assertTrue(it.hasNext());
            assertEquals(4, it.next());
            assertTrue(it.hasNext());
            assertEquals(3, it.next());
            assertTrue(it.hasNext());
            assertEquals(2, it.next());
            assertTrue(it.hasNext());
            assertEquals(1, it.next());
            assertFalse(it.hasNext());
        }

        @DisplayName(
                "WHEN we call `hasNext()` on a reverse iterator multiple times in succession, THEN"
                        + "the return values are consistent.")
        @Test
        public void testReverseIteratorRepeatedHasNext() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            Iterator<Integer> it = list.reverseIterator();
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            assertEquals(2, it.next());
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());
            assertEquals(1, it.next());
            assertFalse(it.hasNext());
            assertFalse(it.hasNext());
        }

        @DisplayName(
                "WHEN we create multiple reverse iterators over the same list, THEN they both separately "
                        + "keep track of which elements they have returned.")
        @Test
        public void testReverseIteratorMultipleIterators() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            list.add(3);

            Iterator<Integer> it1 = list.reverseIterator();
            assertTrue(it1.hasNext());
            assertEquals(3, it1.next());

            Iterator<Integer> it2 = list.reverseIterator();
            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(3, it2.next());

            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(2, it2.next());

            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(1, it2.next());

            assertTrue(it1.hasNext());
            assertFalse(it2.hasNext());
            assertEquals(2, it1.next());

            assertTrue(it1.hasNext());
            assertFalse(it2.hasNext());
            assertEquals(1, it1.next());

            assertFalse(it1.hasNext());
            assertFalse(it2.hasNext());
        }
    }

    /*
    @DisplayName("isMirrored2()")
    @Nested
    class testIsMirrored2 {

        @DisplayName("WHEN we create mirrored arrays, THEN isMirrored2() should return true.")
        @Test
        public void testMirroredTrue() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(1);
            list.add(0);
            assertTrue(ListUtilities.isMirrored2(list));

            DoublyLinkedList<String> list1 = new DoublyLinkedList<>();
            list1.add("One");
            list1.add("Two");
            list1.add("Three");
            list1.add("Two");
            list1.add("One");
            assertTrue(ListUtilities.isMirrored2(list1));

            DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
            list2.add(5);
            list2.add(10);
            list2.add(5);
            assertTrue(ListUtilities.isMirrored2(list2));

            DoublyLinkedList<Integer> list3 = new DoublyLinkedList<>();
            list3.add(7);
            assertTrue(ListUtilities.isMirrored2(list3)); // single-element list is mirrored

            DoublyLinkedList<Integer> list4 = new DoublyLinkedList<>();
            // empty list
            assertTrue(ListUtilities.isMirrored2(list4)); // empty list is mirrored

            DoublyLinkedList<String> list5 = new DoublyLinkedList<>();
            list5.add("Hello");
            list5.add("World");
            list5.add("World");
            list5.add("Hello");
            assertTrue(ListUtilities.isMirrored2(list5));

            DoublyLinkedList<String> list6 = new DoublyLinkedList<>();
            list6.add("Hi");
            list6.add("Hi");
            assertTrue(ListUtilities.isMirrored2(list6));
        }

        @DisplayName("WHEN we create non-mirrored arrays, THEN isMirrored2() should return false.")
        @Test
        public void testMirroredFalse() {
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(0);
            assertFalse(ListUtilities.isMirrored2(list));

            DoublyLinkedList<String> list1 = new DoublyLinkedList<>();
            list1.add("One");
            list1.add("Two");
            list1.add("Three");
            list1.add("Four");
            list1.add("One");
            assertFalse(ListUtilities.isMirrored2(list1));

            DoublyLinkedList<Integer> list2 = new DoublyLinkedList<>();
            list2.add(5);
            list2.add(10);
            list2.add(15);
            assertFalse(ListUtilities.isMirrored2(list2));

            DoublyLinkedList<Integer> list3 = new DoublyLinkedList<>();
            list3.add(7);
            list3.add(8);
            assertFalse(ListUtilities.isMirrored2(list3)); // two-element non-mirrored list

            DoublyLinkedList<String> list4 = new DoublyLinkedList<>();
            list4.add("Hello");
            list4.add("World");
            list4.add("Hi");
            list4.add("Hello");
            assertFalse(ListUtilities.isMirrored2(list4));

            DoublyLinkedList<String> list5 = new DoublyLinkedList<>();
            list5.add("A");
            list5.add("B");
            list5.add("C");
            list5.add("B");
            list5.add("D");
            assertFalse(ListUtilities.isMirrored2(list5));
        }

        @DisplayName("WHEN we edit lists with various methods, THEN isMirrored2() should update correctly.")
        @Test
        public void testMirroredAfterEdits() {
            // 1. Using add() to create a mirrored list
            DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(2);
            list.add(1);
            assertTrue(ListUtilities.isMirrored2(list));

            // 2. Using insert() to break the mirror
            list.insert(2, 99); // insert in the middle
            assertFalse(ListUtilities.isMirrored2(list));

            // 3. Using set() to restore mirror
            list.set(2, 3); // set the middle element back
            assertTrue(ListUtilities.isMirrored2(list));

            // 4. Using remove(int index) to break mirror
            list.remove(1); // remove second element
            assertFalse(ListUtilities.isMirrored2(list));

            // 5. Using delete(T elem) to restore mirror
            list.delete(2); // delete the element that broke symmetry
            assertTrue(ListUtilities.isMirrored2(list));

            // 6. Using add() on strings
            DoublyLinkedList<String> list1 = new DoublyLinkedList<>();
            list1.add("A");
            list1.add("B");
            list1.add("C");
            list1.add("B");
            list1.add("A");
            assertTrue(ListUtilities.isMirrored2(list1));

            // 7. Using insert() to break mirror
            list1.insert(0, "X"); // add at start
            assertFalse(ListUtilities.isMirrored2(list1));

            // 8. Using add() to restore mirror
            list1.add("X"); // add 'X' to end to restore mirror
            assertTrue(ListUtilities.isMirrored2(list1));

            // 9. Using remove() to break mirror
            list1.remove(0); // remove first element
            assertFalse(ListUtilities.isMirrored2(list1));

            // 10. Using delete() to restore mirror
            list1.delete("X"); // delete last element
            assertTrue(ListUtilities.isMirrored2(list1));
        }
    }
    */
}
