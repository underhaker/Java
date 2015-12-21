package sequiental.list;

import java.util.HashSet;
import java.util.Set;

public class SequentialList implements ListInterface {

    private static final int INITIAL_SIZE = 2;
    private static final double GROWING_FACTOR = 2;
    private static final double SHRINKING_FACTOR = 2;
    private int elementsCount;
    private int[] array;

    private void tryGrow() {
        if (this.elementsCount == this.array.length) {
            int[] newArray = new int[(int) (this.array.length * this.GROWING_FACTOR)];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        }
    }

    private void tryShrink() {
        if (this.elementsCount == this.array.length / 4) {
            int[] newArray = new int[(int) (this.array.length / this.SHRINKING_FACTOR)];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        }
    }

    public SequentialList() {
        this.array = new int[INITIAL_SIZE];
        this.elementsCount = 0;
    }

    public void add(int newElement) {
        this.tryGrow();
        this.array[this.elementsCount] = newElement;
        this.elementsCount++;
    }

    public int get(int index) {
        return this.array[index];
    }

    public void insert(int newElement, int index) {
        if (index >= this.elementsCount) {
            this.add(newElement);
            return;
        }
        this.tryGrow();
        for (int i = this.elementsCount - 1; i >= index; i--) {
            this.array[i + 1] = this.array[i];
        }
        this.array[index] = newElement;
        this.elementsCount++;
    }

    public int getSize() {
        return this.elementsCount;
    }

    public int indexOf(int element) {
        for (int i = 0; i < this.elementsCount; i++) {
            if (element == this.array[i]) {
                return i;
            }
        }
        return -1;
    }

    public SequentialList copy() {
        SequentialList listCopy = new SequentialList();
        for (int i = 0; i < this.elementsCount; i++) {
            listCopy.add(this.array[i]);
        }
        return listCopy;
    }

    public void print() {
        for (int i = 0; i < this.elementsCount; i++) {
            System.out.println(this.array[i]);
        }
    }

    @Override
    public void deleteAt(int index) {
        int[] newArray = new int[this.elementsCount - 1];
        for (int i = 0; i < this.elementsCount; i++) {
            if (i == index)
                continue;
            newArray[i] = this.array[i];
        }
        this.elementsCount--;
        tryShrink();
        this.array = newArray;
    }

    @Override
    public boolean removeElement(int element) {
        boolean isDeleted = false;
        int tempElementsCount = this.elementsCount;
        for (int i = 0; i < this.elementsCount; i++) {
            if (this.array[i] == element) {
                this.deleteAt(i);
                break;
            }
        }
        if (tempElementsCount != this.elementsCount)
            isDeleted = true;
        return isDeleted;
    }

    @Override
    public SequentialList reverse() {
        SequentialList sl = new SequentialList();
        for (int i = 0; i < this.elementsCount; i++) {
            sl.add(this.array[this.elementsCount - i - 1]);
        }
        return sl;
    }

    @Override
    public boolean equals(SequentialList otherList) {
        if (this.getSize() != otherList.getSize())
            return false;
        for (int i = 0; i < this.getSize(); i++) {
            if (this.array[i] != otherList.array[i])
                return false;
        }
        return true;
    }

    @Override
    public SequentialList removeDuplicates() {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < this.elementsCount; i++) {
            set.add(this.array[i]);
        }
        SequentialList sl = new SequentialList();
        for (int element : set) {
            sl.add(element);
        }
        return sl;
    }

    @Override
    public void splice(SequentialList otherList, int start, int end) {
        for (int i = 0; i < otherList.getSize(); i++) {
            if (i >= start && i < end)
                this.insert(otherList.array[i], i);
        }
    }

    @Override
    public SequentialList splitAt(int index) {
        SequentialList sl = new SequentialList();
        for (int i = index; i < this.elementsCount; i++) {
            sl.add(this.array[i]);
            this.removeElement(this.array[i]);
        }
        return sl;
    }

}
