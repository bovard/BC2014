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

    public boolean contains(int n) {
        for (int i = length - 1; i >= 0; i--) {
            if (arr[i] == n) {
                return true;
            }
        }
        return false;
    }

}