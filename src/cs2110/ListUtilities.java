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

        for (int i = 0; i<list.size(); i++){
            T elem1 = it1.next();
            T elem2 = elem1;
            Iterator <T> it2 = list.iterator();
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
        // TODO 7: Implement this method according to its specifications.

    }  throw new UnsupportedOperationException();
}
