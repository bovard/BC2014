package flow.utils;

import java.util.Arrays;

public class ArrayBuilder {

    public static int[] addTo(int[] arr, int toAdd) {
        // TODO: Test against other methods to make sure this is bytecode optimal
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = toAdd;
        return newArr;
    }
}
