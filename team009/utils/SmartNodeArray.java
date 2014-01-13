package team009.utils;

import team009.bt.Node;

public class SmartNodeArray {

    public Node[] arr = new Node[1000];
    public int length = 0;

    public SmartNodeArray() {

    }

    public void add(Node n) {
        arr[length] = n;
        length++;
    }

    public Node get(int index) {
        return arr[index];
    }

    public int size() {
        return length;
    }
}
