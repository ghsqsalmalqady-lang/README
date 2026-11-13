import java.util.Arrays;
import java.util.Random;

public class SortingBenchmark {

    private static final int N = 100_000;

    public static void main(String[] args) {
        
        int[] originalRandom = generateRandomArray(N);
        int[] originalReversed = generateReverselySortedArray(N);
        int[] originalPartial = generatePartiallySortedArray(N, 0.80);
        int[] originalSorted = generateFullySortedArray(N);

        String[] casesNames = {"Random", "Sorted Reversely", "Sorted Partially", "Sorted Fully"};
        
      
        long[][] results = new long[5][4];

        
        String[] algorithms = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"};

       
        for (int c = 0; c < 4; c++) {
            int[] currentOriginal = switch (c) {
                case 0 -> originalRandom;
                case 1 -> originalReversed;
                case 2 -> originalPartial;
                default -> originalSorted;
            };

            
            results[0][c] = benchmarkAlgorithm(currentOriginal, "Selection");
            results[1][c] = benchmarkAlgorithm(currentOriginal, "Insertion");
            results[2][c] = benchmarkAlgorithm(currentOriginal, "Bubble");
            results[3][c] = benchmarkAlgorithm(currentOriginal, "Merge");
            results[4][c] = benchmarkAlgorithm(currentOriginal, "Quick");
        }

        printFormattedTable(algorithms, casesNames, results);
    }

 
    private static long benchmarkAlgorithm(int[] original, String type) {
        int[] testArray = Arrays.copyOf(original, original.length);
        
        long startTime = System.nanoTime();
        
        switch (type) {
            case "Selection" -> selectionSort(testArray);
            case "Insertion" -> insertionSort(testArray);
            case "Bubble" -> bubbleSort(testArray);
            case "Merge" -> mergeSort(testArray, 0, testArray.length - 1);
            case "Quick" -> quickSort(testArray, 0, testArray.length - 1);
        }
        
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000; 

    


    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

  
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

   
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, m + 1, R, 0, n2);

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
      
        int randomIndex = low + new Random().nextInt(high - low + 1);
        int temp = arr[randomIndex];
        arr[randomIndex] = arr[high];
        arr[high] = temp;

        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = swapTemp;
        return i + 1;
    }

 

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1_000_000);
        }
        return arr;
    }

    private static int[] generateReverselySortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }

    private static int[] generatePartiallySortedArray(int size, double sortedRatio) {
        int[] arr = generateFullySortedArray(size);
        Random rand = new Random();
        int swapCount = (int) (size * (1.0 - sortedRatio));
        for (int i = 0; i < swapCount; i++) {
            int idx1 = rand.nextInt(size);
            int idx2 = rand.nextInt(size);
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        return arr;
    }

    private static int[] generateFullySortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }

  
    private static void printFormattedTable(String[] algorithms, String[] casesNames, long[][] results) {
        System.out.printf("%-18s | %-18s | %-18s | %-18s | %-18s%n", 
                "الخوارزمية", casesNames[0], casesNames[1], casesNames[2], casesNames[3]);
 
        for (int i = 0; i < algorithms.length; i++) {
            System.out.printf("%-18s | %12d μs     | %12d μs     | %12d μs     | %12d μs     %n",
                    algorithms[i], results[i][0], results[i][1], results[i][2], results[i][3]);
        }
    }
}
