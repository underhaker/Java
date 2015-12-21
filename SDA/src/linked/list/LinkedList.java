package linked.list;

import java.util.HashSet;
import java.util.Set;

import sequiental.list.SequentialList;

public class LinkedList implements LinkedListInterface {
    private Node head, tail;

    public void add(int element) {
        Node node = new Node(element);

        if (this.tail == null) {
            this.head = node;
            this.tail = node;
            return;
        }
        node.setLeft(this.tail);
        this.tail.setRight(node);
        this.tail = node;
    }

    public Node getNode(int index) {
        Node node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.getRight();
        }
        return node;
    }

    public void insert(int element, Node leftNeighbour) {
        Node node = new Node(element);
        if (this.tail == leftNeighbour) {
            leftNeighbour.setRight(node);
            node.setLeft(leftNeighbour);
            this.tail = node;
        } else if (leftNeighbour == null) {
            this.head.setLeft(node);
            node.setRight(this.head);
            this.head = node;
        } else {
            Node rightNeighbour = leftNeighbour.getRight();
            leftNeighbour.setRight(node);
            rightNeighbour.setLeft(node);
            node.setRight(rightNeighbour);
            node.setLeft(leftNeighbour);
        }
    }

    public void print() {
        Node node = this.head;
        while (node != null) {
            System.out.println(node.getElement());
            node = node.getRight();
        }
    }

    public int getSize() {
        Node node = this.head;
        int size = 0;
        while (node != null) {
            size += 1;
            node = node.getRight();
        }
        return size;
    }

    public Node nodeOf(int element) {
        Node node = this.head;
        while (node != null) {
            if (node.getElement() == element)
                return node;
            node = node.getRight();
        }
        return null;
    }

    @Override
    public void removeAt(Node node) {
        if (node.getLeft() == null) {
            this.head = node.getRight();
            node.getRight().setLeft(null);
        }
        if (node.getRight() == null) {
            this.tail = node.getLeft();
            node.getLeft().setRight(null);
        } else {
            Node prevNode = this.head;
            while (prevNode != null) {
                if (prevNode.getRight() == node)
                    break;
                prevNode = prevNode.getRight();
            }

            prevNode.setRight(node.getRight());
            node.getRight().setLeft(prevNode);
        }
        node = null;
    }

    @Override
    public boolean removeElement(int element) {
        Node node = this.head;
        while (node != null) {
            if (node.getElement() == element)
                break;
            node = node.getRight();
        }

        if (node == null)
            return false;
        else {
            this.removeAt(node);
            return true;
        }
    }

    @Override
    public LinkedList copy() {
        LinkedList listCopy = new LinkedList();
        Node node = this.head;
        while (node != null) {
            listCopy.add(node.getElement());
            node = node.getRight();
        }
        return listCopy;
    }

    @Override
    public LinkedList reverse() {
        LinkedList listReversed = new LinkedList();
        Node node = this.tail;
        while (node != null) {
            listReversed.add(node.getElement());
            node = node.getLeft();
        }
        return listReversed;
    }

    @Override
    public boolean equals(LinkedList otherList) {
        if (this.getSize() != otherList.getSize())
            return false;
        Node firstNode = this.head;
        Node secondNode = otherList.getNode(0);
        while (firstNode != null) {
            if (firstNode.getElement() != secondNode.getElement())
                return false;
            firstNode = firstNode.getRight();
            secondNode = secondNode.getRight();
        }
        return true;
    }

    @Override
    public LinkedList removeDuplicates() {
        Set<Integer> set = new HashSet<Integer>();
        Node node = this.head;
        while (node != null) {
            set.add(node.getElement());
            node = node.getRight();
        }
        LinkedList ll = new LinkedList();
        for (int element : set) {
            ll.add(element);
        }
        return ll;
    }

    @Override
    public void splice(LinkedList otherList, Node start, Node end) {
        Node currentNode = otherList.nodeOf(start.getElement());
        Node endNode = otherList.nodeOf(end.getElement());
        while (currentNode != endNode) {
            this.add(currentNode.getElement());
            currentNode = currentNode.getRight();
        }

    }

    @Override
    public LinkedList splitAt(Node node) {
        LinkedList newList = new LinkedList();
        while (node != null) {
            newList.add(node.getElement());
            this.removeAt(node);
            node = node.getRight();
        }
        return newList;
    }
}