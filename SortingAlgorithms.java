import java.util.* ; // random and time
public class SortingAlgorithms {

	public static void main(String[] args) {
		Random rand = new Random();
		long ms;
		int i, n;

		n = 50000; // size of the array, can be changed by user
		float[] arr = new float[n]; // the array
		float[] arr2sort = new float[n]; // the array that will be passed and sorted

		// array is randomly generated
		for (i = 0; i < n; i++) {
			float num = randNumGen(rand);
			arr[i] = num;
		}

		copy(arr, arr2sort);
		ms = bubble("bubble", arr2sort);
		System.out.println("BUBBLE: " + ms + " MS for " + n + " elements. ");
		checkSort(arr2sort) ;

		copy(arr, arr2sort);
		ms = selection("selection", arr2sort);
		System.out.println("SELECTION: " + ms + " MS for " + n + " elements. ");
		checkSort(arr2sort) ;

		copy(arr, arr2sort);
		ms = insertion("insertion", arr2sort);
		System.out.println("INSERTION: " + ms + " MS for " + n + " elements. ");
		checkSort(arr2sort) ;

		copy(arr, arr2sort);
		ms = merge("merge", arr2sort);
		System.out.println("MERGE: " + ms + " MS for " + n + " elements. ");
		checkSort(arr2sort) ;

		copy(arr, arr2sort);
		ms = quick("quick", arr2sort);
		System.out.println("QUICK: " + ms + " MS for " + n + " elements. ");
		checkSort(arr2sort) ;
	}

	// fcn to generate random nums
	private static int randNumGen(Random rand){
		int r ;

		r = rand.nextInt() ;
		if (r == Integer.MIN_VALUE){
			r = Integer.MAX_VALUE ;
		}
		if (r < 0 ){
			r = -r ;
		}
		return (r) ;
	}

	// fcn to copy the same array
	public static void copy(float[] here, float[] there) {
		int i, n;

		n = here.length;
		for (i = 0; i < n; i++) {
			there[i] = here[i];
		}
	}

	// BUBBLE: if value to the right is smaller, swap with use of temp variable.
	// O(n^2)
	public static long bubble(String bubble, float[] arr) {
		float temp;
		long tin;
		int i, j, n;

		tin = System.currentTimeMillis();
		n = arr.length;

		for (i = 1; i < n; i++) {
			for (j = 0; j < n - i; j++) {
				if (arr[j + 1] < arr[j]) {
					temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return (System.currentTimeMillis() - tin);
	}

	// SELECTION: find the smallest element of the array, and place it at
	// the front of the unsorted array
	// O(n^2)

	public static long selection(String selection, float[] arr) {
		float x;
		long tin;
		int i, j, n, small;

		tin = System.currentTimeMillis();
		n = arr.length;
		for (i = 0; i < n - 1; i++) {

			// value smallest element
			x = arr[i];
			// index of smallest element
			small = i;

			for (j = i + 1; j < n; j++) {
				if (arr[j] < x) {
					// new small
					x = arr[j];
					small = j;
				}
			}
			if (small != i) {
				arr[small] = arr[i];
				arr[i] = x;
			}

		}
		return (System.currentTimeMillis() - tin);
	}

	// INSERTION: place values from unsorted array into their
	// correct position in the sorted sub-array. Search method: Binary search
	// O(n^2)

	public static long insertion(String insertion, float[] arr) {
		float x;
		long tin;
		int bot, i, j, mid, n, top;

		tin = System.currentTimeMillis();
		n = arr.length;
		for (i = 1; i < n; i++) {
			bot = 0;
			top = i - 1;

			// binary search
			while (bot < top) {
				mid = ((bot + top) / 2);
				if (arr[mid] < arr[i]) {
					bot = mid + 1;
				} else {
					top = mid;
				}
			}
			while (top < i) {
				if (arr[top] <= arr[i]) {
					top++;
				} else {
					break;
				}
			}
			if (top < i) {
				for (x = arr[i], j = i; top < j; --j) {
					arr[j] = arr[j - 1];
				}
				arr[top] = x;
			}
		}
		return (System.currentTimeMillis() - tin);
	}


	// MERGE: divides the array into smaller sub-arrays, sorts each array,
	// and then merges them.
	// O(nlogn)
	public static long merge(String merge, float[] arr) {
		long tin;
		int i, j, k, mid, n;

		tin = System.currentTimeMillis();
		n = arr.length;
		if (n <= 1) {
			return (System.currentTimeMillis() - tin);
		}
		mid = (n / 2);

		// creation of sub-arrays
		float[] b1 = new float[mid];
		float[] b2 = new float[n - mid];
		for (j = 0; j < mid; j++) {
			b1[j] = arr[j];
		}
		for (k = 0; k < n - mid; k++) {
			b2[k] = arr[mid + k];
		}

		// recursive calls
		merge("merge", b1);
		merge("merge", b2);

		// merge of the sub-arrays
		i = j = k = 0;
		while ((j < mid) && (k < n - mid)) {
			arr[i++] = (b1[j] <= b2[k] ? b1[j++] : b2[k++]);
		}
		if (j < mid) {
			while (j < mid) {
				arr[i++] = b1[j++];
			}
		}
		if (k < n - mid) {
			while (k < n - mid) {
				arr[i++] = b2[k++];

			}
		}
		return (System.currentTimeMillis() - tin);
		}

	// first call of quicksort with original array.
	public static long quick(String quick, float[] arr){
		long tin ;

		tin = System.currentTimeMillis() ;
		qsort(arr, 0, arr.length - 1) ;
		return(System.currentTimeMillis() - tin) ;
	}

	// QUICKSORT: picks an element as a pivot and partitions the given array around
	// the picked pivot, by placing the pivot in its correct position in the array.
	// Then, the sub-arrays around the pivot are independently sorted recursively.
	// O(nlogn)

	public static void qsort(float[] arr, int left, int right) {
		Random rand = new Random() ;
		float pivot, x ;
		int i,j ;

		if (left < right){

			// random pivot in the range of possible indices in arr
			pivot = arr[(randNumGen( rand ) % (right - left + 1 )) + left] ;

			// quicksort going inwards
			i = left - 1 ;
			j = right + 1 ;
			do {
				while (arr[++i] < pivot) {
					;
				}
				while (pivot < arr[--j]) {
					;
				}
				if (i < j) {
					x = arr[i];
					arr[i] = arr[j];
					arr[j] = x;
				}
			} while (i < j) ;
				if( i == j){
					i++ ;
					--j ;
				}

				// recursive call
				qsort(arr, left, j) ;
				qsort(arr, i, right) ;
		}
	}

	// ensures the array is sorted
	public static void checkSort(float[] arr){
		int i, n ;
		n = arr.length ;

		for (i = 1; i < n ; i++){
			if(arr[i] < arr[i - 1]){
				System.out.println("** ARRAY NOT SORTED ** ") ;
				return ;
			}
		}
	}
}