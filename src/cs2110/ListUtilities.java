package cs2110;

import java.util.Iterator;

/**
 * Contains static methods for checking whether a list is mirrored.
 */
public class ListUtilities {

    /**
     * Returns whether this list is "mirrored" meaning it produces the same sequence of elements
     * when iterated in the forward or reverse directions.
     */
    public static <T> boolean isMirrored1(CS2110List<T> list) {
        Iterator <T> it1 = list.iterator();

        /*
        Loop invariant: elem1 is the 'T' at index i, and elem2 is the 'T' at index list.size()-i-1.
        it1 points to the node at index i, and it2 points to the node at index list.size()-i-1.
        elem1 is equal to elem2 for [0,i). In other words, the first i elements from each end of the
        array are equal to the corresponding element on the other side.
         */
        for (int i = 0; i<list.size(); i++){
            T elem1 = it1.next();
            T elem2 = elem1;
            Iterator <T> it2 = list.iterator();

            /*
            Loop invariant: it2 points to node at index j and elem2 = 'T' at index j.
             */
            for(int j = 0; j<list.size()-i; j++){
                elem2 = it2.next();
            }
            if (!(elem1.equals(elem2))){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether this list is "mirrored" meaning it produces the same sequence of elements
     * when iterated in the forward or reverse directions.
     */
    public static <T> boolean isMirrored2(DoublyLinkedList<T> list) {
        Iterator <T> it1 = list.iterator();
        Iterator <T> it2 = list.reverseIterator();

        /*
        Loop invariant: it1 points to the node at index i. it2 points to node at index list.size()-i-1.
        In other words, it2 points to the node i nodes to the left from the end of the list.
        The first i elements from each end of the array are equal to the corresponding element
        on the other side.
          */
        for (int i = 0; i< list.size(); i++){
            if (!(it1.next().equals(it2.next()))){
                return false;
            }
        }
        return true;

    }
}
