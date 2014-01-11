package team009.utils;

import team009.bt.Node;

public class SmartNodeArray {

    private Node[] arr = new Node[1000];
    private int length = 0;

    public SmartNodeArray() {

    }

    public void add(Node n) {
        arr[length] = n;
        length++;
    }

    public Node get(int index) {
        if (index < length) {
            return arr[index];
        }
        return null;
    }

    public int size() {
        return length;
    }
}
