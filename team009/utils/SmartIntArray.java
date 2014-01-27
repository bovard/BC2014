package team009.utils;

public class SmartIntArray {
    public int[] arr = new int[1000];
    public int length = 0;

    public SmartIntArray() {

    }

    public void add(int n) {
        arr[length] = n;
        length++;
    }

    public int get(int index) {
        return arr[index];
    }

    public int size() {
        return length;
    }

}