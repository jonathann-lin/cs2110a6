package cs2110;

public class DoublyLinkedTest extends ListTest{


    @Override
    public <T> CS2110List<T> constructList() {
        return new DoublyLinkedList<T>();
    }
}
