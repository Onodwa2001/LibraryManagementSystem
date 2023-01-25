package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Utility {

    public static void main(String[] args) {

        int[] arr = {3, 6, 8, 12, 29};

        int v = binarySearch(arr, 12);
        System.out.println(v);

    }

    public static int binarySearch(int[] inputArray, int searchValue) {

        int min = 0;
        int max = inputArray.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;
            if (searchValue < inputArray[mid]) {
                max = mid - 1;
            } else if (searchValue > inputArray[mid]) {
                min = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;

    }

}
