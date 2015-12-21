package sequiental.list;

public class Main {
    public static void main(String[] args) {
        SequentialList sl = new SequentialList();
        sl.add(3);
        sl.add(1);
        sl.add(6);
        sl.add(6);
        sl.add(2);
        sl.add(1);
        SequentialList reversed = new SequentialList();
        reversed = sl.reverse();
        sl.print();
        System.out.println("----");
        reversed.print();
        System.out.println("----");
        sl = sl.removeDuplicates();
        sl.print();
    }
}
