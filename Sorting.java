import java.util.Comparator;
import java.util.Random;
import java.util.List;

/**
 * Your implementation of various sorting algorithms.
 *
 * Your implementations must match what was taught in lecture and 
 * recitation to receive credit. Implementing a different sort or 
 * a different implementation for a sort will receive no credit even
 * if it passes comparison checks.
 *
 * @author Yusuf Bagwan
 * @version 1.0
 * @userid ybagwan3 (i.e. gburdell3)
 * @GTID 903891335 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 *
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, you will lose points.
 * 
 * Agree Here: I agree
 */
public class Sorting {

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null.");
        }
        if (arr.length < 2) {
            return;
        }
    
        int length = arr.length;
        int midIndex = length / 2;
    
        @SuppressWarnings("unchecked")
        T[] left = (T[]) new Object[midIndex];
        @SuppressWarnings("unchecked")
        T[] right = (T[]) new Object[length - midIndex];
    
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        for (int i = midIndex; i < length; i++) {
            right[i - midIndex] = arr[i];
        }
    
        mergeSort(left, comparator);
        mergeSort(right, comparator);
    
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }
    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * not stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,Random rand) {
            if (arr == null) {
                throw new IllegalArgumentException("Array cannot be null.");
            }
            if (comparator == null) {
                throw new IllegalArgumentException("Comparator cannot be null.");
            }
            if (rand == null) {
                throw new IllegalArgumentException("Random cannot be null.");
            }
            if (k < 1 || k > arr.length) {
                throw new IllegalArgumentException(
                    "k must be in the range [1, arr.length].");
            }
            return kthSelectHelper(k - 1, arr, 0, arr.length - 1, comparator, rand);
    }

    private static <T> T kthSelectHelper(int k, T[] arr, int left, int right, Comparator<T> comparator, Random rand) {
        
        int pivotIndex = rand.nextInt(right - left + 1) + left;
        T pivot = arr[pivotIndex];
        swap(arr, left, pivotIndex);

        int i = left + 1;
        int j = right;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        // Place pivot in its final sorted position.
        swap(arr, left, j);

        if (j == k) {
            return arr[j];
        } else if (j > k) {
            return kthSelectHelper(k, arr, left, j - 1, comparator, rand);
        } else {
            return kthSelectHelper(k, arr, j + 1, right, comparator, rand);
        }
    }

    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }
        if (arr.length <= 1) {
            return;
        }

        int maxDigits = 1;
        for (int v : arr) {
            long mag = Math.abs((long) v);
            int digits = 1;
            while (mag >= 10) {
                mag /= 10;
                digits++;
            }
            if (digits > maxDigits) {
                maxDigits = digits;
            }
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new java.util.ArrayList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new java.util.ArrayList<>();
        }

        int exp = 1;
        for (int d = 0; d < maxDigits; d++) {
            for (int value : arr) {
                int digit = (value / exp) % 10;
                int index = digit + 9;
                buckets[index].add(value);
            }

            int k = 0;
            for (int b = 0; b < 19; b++) {
                for (int value : buckets[b]) {
                    arr[k++] = value;
                }
                buckets[b].clear();
            }
            exp *= 10;
        }
    }
}
