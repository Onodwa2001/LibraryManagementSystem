package org.factory;

import java.util.Scanner;

public class Main {

    public static void doStuff() {
        Factory factory = new Factory();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose vehicle: (Car/Plane/Boat): ");
        String value = scanner.nextLine();
        String result = factory.checkInstance(value) == null ? "No such vehicle" : factory.checkInstance(value).start();

        System.out.println(result);
    }

    public static void main(String[] args) {

        int[] arr = {4, 6, 7, 9, 11};

        int v = binarySearch(arr, 3);
        System.out.println(v);

    }

    public static int binarySearch(int[] arr, int searchValue) {

        int last = arr.length - 1;
        int first = 0;
        int res = 0;

        int mid = (last) / 2;

        while(!(last < 1)) {
            if (searchValue < arr[mid]) {
                last = mid - 1;
                res = last;
            } else if (searchValue > arr[mid]) {
                first = mid + 1;
                res = first;
            }
        }

        return res;
    }

}


class Node {

}