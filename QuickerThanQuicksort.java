
/* This program contains a "hybrid sort", which implements quick sort until a
certain cutoff point (the size of the sub-array), in which quicksort becomes inefficient.
After reaching this cutoff point, a quadratic method sorts the remaining array. In this program
it is Bubble Sort. The cutoff point in which bubble sort begins is when the sub-array
is of length 50 or less. README.md includes more information on the findings of this
cutoff point. */

import java.util.*;

public class QuickerThanQuicksort {

	public static void main (String [] args) {
		Random rand = new Random();
		long ms;
		int i, n;

		n = 500000; // size of array, can be changed by user
		double[] arr = new double[n]; // double arr

		// randomly generated double array
		for (i = 0; i < n; i++) {
			double num = randNumGen(rand);
			arr[i] = num;
		}

		ms = quick(arr);
		System.out.println("HYBRID: " + ms + " MS for " + n + " elements. ");
		checkSort(arr) ;

	}

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

	// first call of quick sort using original array
	public static long quick(double[] arr){
		long tin ;
		int n ;

		tin = System.currentTimeMillis() ;
		n = arr.length ;
		quicksort(arr, 0, n-1) ;
		return (System.currentTimeMillis() - tin) ;
	}

	// quicksort for when the array size is > 50
	public static void quicksort(double [] arr, int left, int right) {
		double pivot, temp;
		int i,j;

		if(right - left <= 50) {
			bubble(arr, left, right) ;
		}
		if (left < right){
			pivot = arr[left] ;
			i = left - 1 ;
			j = right + 1;
			do {
				while (arr[++i] < pivot) {
					;
				}
				while (pivot < arr[--j]) {
					;
				}
				if (i < j) {
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			} while( i < j );
			if (i == j){
				i++ ;
				--j;
			}
			quicksort(arr, left, j) ;
			quicksort(arr, i , right) ;
		}
	}

	// called when the sub-array's length is <= 50
	public static void bubble(double [] arr, int left, int right) {
		double temp;
		int i,j;

		for (i=left+1 ; i < right ; i++){
				for (j = left ; j < right-i ; j++){
					if (arr[j+1] < arr[j]) {
						temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;
					}
				}
			}
		}

	// ensures arr is sorted
	public static void checkSort(double[] arr){
		int i,n ;
		n = arr.length ;

		for (i = 1; i < n ; i++){
			if(arr[i] < arr[i - 1]){
				System.out.println("** ARRAY NOT SORTED ** ") ;
				return ;
			}
		}
	}
}
