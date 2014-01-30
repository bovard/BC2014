
package team009.utils;

public class SmartIntArrayArray {
    public int[][] arr = new int[1000][2];
    public int length = 0;

    public SmartIntArrayArray() {

    }

    public void add(int[] n) {
        arr[length] = n;
        length++;
    }

    public int[] get(int index) {
        return arr[index];
    }

    public int size() {
        return length;
    }

}