package DesignMode;

public class Binary {

    /**
     * 使用递归的二分查找
     *
     * @param arr 有序数组
     * @param key 待查找关键字
     * @return 找到的位置
     */
    public static int recursionBinarySearch(int[] arr, int key, int low, int high) {
        if (key < arr[low] || key > arr[high]) {
            return -1;
        }
        int middle = (low + high) / 2;
        if (key > arr[middle]) {
            recursionBinarySearch(arr, key, middle + 1, high);
        } else if (key < arr[middle]) {
            recursionBinarySearch(arr, key, low, middle - 1);
        } else {
            return middle;
        }
        return -1;
    }

    /**
     * 常见的二分查找
     *
     * @param arr
     * @param key
     * @return 关键字位置
     */
    public static int commonBinarySearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int middle;

        if (key < arr[low] || key > arr[high]) {
            return -1;
        }
        while (low <= high) {
            middle = (low + high) / 2;
            if (key > arr[middle]) {
                low = middle + 1;
            } else if (key < arr[middle]) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }
}
