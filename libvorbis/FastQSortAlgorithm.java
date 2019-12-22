package libvorbis;

/**
 * @(#)QSortAlgorithm.java	1.3   29 Feb 1996 James Gosling
 *
 * Copyright (c) 1994-1996 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL or COMMERCIAL purposes and
 * without fee is hereby granted. 
 * Please refer to the file http://www.javasoft.com/copy_trademarks.html
 * for further important copyright and trademark information and to
 * http://www.javasoft.com/licensing.html for further important
 * licensing information for the Java (tm) Technology.
 * 
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * 
 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
 * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
 * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
 * NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
 * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
 * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
 * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").  SUN
 * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
 * HIGH RISK ACTIVITIES.
 */

/**
 * A quick sort demonstration algorithm
 * SortAlgorithm.java
 *
 * @author James Gosling
 * @author Kevin A. Smith
 * @version 	@(#)QSortAlgorithm.java	1.3, 29 Feb 1996
 * extended with TriMedian and InsertionSort by Denis Ahrens
 * with all the tips from Robert Sedgewick (Algorithms in C++).
 * It uses TriMedian and InsertionSort for lists shorts than 4.
 * <fuhrmann@cs.tu-berlin.de>
 */
final class FastQSortAlgorithm {
	private static void swap(int a[], int i, int j) {
		int T;
		T = a[i]; 
		a[i] = a[j];
		a[j] = T;
	}
	/** This is a generic version of C.A.R Hoare's Quick Sort 
	* algorithm.  This will handle arrays that are already
	* sorted, and arrays with duplicate keys.<BR>
	*
	* If you think of a one dimensional array as going from
	* the lowest index on the left to the highest index on the right
	* then the parameters to this function are lowest index or
	* left and highest index or right.  The first time you call
	* this function it will be with the parameters 0, a.length - 1.
	*
	* @param a	   an integer array
	* @param lo0	 left boundary of array partition
	* @param hi0	 right boundary of array partition
	*/
	private static void quickSort(int a[], int l, int r, Icomparator cmp) {
		final int M = 4;
		int i;
		int j;
		int v;

		if( (r - l) > M ) {
			i = (r + l) >>> 1;
			if( cmp.compare( a[l], a[i] ) > 0 ) swap( a, l, i );// Tri-Median Methode!
			if( cmp.compare( a[l], a[r] ) > 0 ) swap( a, l, r );
			if( cmp.compare( a[i], a[r] ) > 0 ) swap( a, i, r );

			j = r - 1;
			swap( a, i, j );
			i = l;
			v = a[j];
			for( ; ; ) {
				while( cmp.compare( a[++i], v ) < 0 );
				while( cmp.compare( a[--j], v ) > 0 );
				if (j < i ) break;
				swap( a, i, j );
			}
			swap( a, i , r - 1 );
			quickSort( a, l, j, cmp );
			quickSort( a, i + 1, r, cmp );
		}
	}

	private static void insertionSort(int a[], int lo0, int hi0, Icomparator cmp) {
		int i;
		int j;
		int v;

		for( i = lo0 + 1; i <= hi0; i++ ) {
			v = a[i];
			j = i;
			while( (j > lo0) && cmp.compare( a[j - 1], v ) > 0 ) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
		}
	}

	static void sort(int a[], int fromInclusive, int toExclusive, Icomparator cmp) {
		quickSort( a, fromInclusive, --toExclusive, cmp );
		insertionSort( a, fromInclusive, toExclusive, cmp );
	}
/*	// float inverse sort
	// used only in unused function Jfloor0.vorbis_lpc_to_lsp
	private static void swap(float a[], int i, int j) {
		float T;
		T = a[i]; 
		a[i] = a[j];
		a[j] = T;
	}
	private static void quickInverseSort(float a[], int l, int r) {
		final int M = 4;
		int i;
		int j;
		float v;

		if( (r - l) > M ) {
			i = (r + l) >>> 1;
			if( a[l] < a[i] ) swap( a, l, i );// Tri-Median Methode!
			if( a[l] < a[r] ) swap( a, l, r );
			if( a[i] < a[r] ) swap( a, i, r );

			j = r - 1;
			swap( a, i, j );
			i = l;
			v = a[j];
			for( ; ; ) {
				while( a[++i] > v );
				while( a[--j] < v );
				if (j < i ) break;
				swap( a, i, j );
			}
			swap( a, i , r - 1 );
			quickInverseSort( a, l, j );
			quickInverseSort( a, i + 1, r );
		}
	}

	private static void insertionInverseSort(float a[], int lo0, int hi0) {
		int i;
		int j;
		float v;

		for( i = lo0 + 1; i <= hi0; i++ ) {
			v = a[i];
			j = i;
			while( (j > lo0) && (a[j - 1] < v) ) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
		}
	}

	public static void inverseSort(float a[], int fromInclusive, int toExclusive) {
		quickInverseSort( a, fromInclusive, --toExclusive );
		insertionInverseSort( a, fromInclusive, toExclusive );
	}*/
}

