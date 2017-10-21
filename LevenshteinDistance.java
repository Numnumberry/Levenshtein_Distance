//Levenshtein distance
//program accepts a single String, checks spelling, then gives possible suggestions based on 1 edit distance

import java.io.*;
import java.util.Scanner;

public class LevenshteinDistance {

	private static String[] arrayWords = new String[58116];		//create array to contain words from text file

	public static void main(String[] args) throws IOException {
		fillArray();
		for (;;) {
			String string = getString();
			System.out.print(checkSpelling(string));
			suggestions(string);
		}
	}

	public static void suggestions(String string) {
		String info;
		int dist;
		boolean availableSuggestions = false;

		System.out.print("Suggestions: ");
		for (int x = 0; x < arrayWords.length; x++) {
			info = arrayWords[x];
			dist = minDistance(string, info);
			if (dist == 1) {
				System.out.print("\n" + arrayWords[x]);
				availableSuggestions = true;
			}
		}
		if (availableSuggestions == false) {
			System.out.print("NONE");
		}
		System.out.println("\n");
	}

	public static String checkSpelling(String string) {		//searches array for word using binary search
		int low = 0;
		int high = arrayWords.length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (arrayWords[mid].compareTo(string) > 0) {
				high = mid - 1;
			} else if (arrayWords[mid].compareTo(string) < 0) {
				low = mid + 1;
			} else
				return "The word is spelled correctly.\n\n";
		}
		return "\n";
	}

	public static void fillArray() throws IOException {
		File myFile = new File("Words.txt");
		Scanner inputFile = new Scanner(myFile);
		int x = 0;

		while (inputFile.hasNext()) {		//loop fills array with words
			arrayWords[x] = inputFile.next();
			x++;
		}
		inputFile.close();
	}

	public static String getString() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter a String: ");
		String string = sc.next();
		return string;
	}

	public static int minDistance(String s, String t) {
		int d[][];
		int n = s.length();
		int m = t.length();
		int i;
		int j;
		char s_i;
		char t_j;
		int cost;

		d = new int[n + 1][m + 1];		//creates table with one more row and column

		for (i = 0; i <= n; i++) {		//fills first column with integers starting at 0
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) {		//fills first row with integers starting at 0
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) {
			s_i = s.charAt(i - 1);
			for (j = 1; j <= m; j++) {
				t_j = t.charAt(j - 1);

				if (s_i == t_j) {		//if characters are same, the cost is 0
					cost = 0;
				} else {				//if not the same, cost is 1, so 1 is added to the min
					cost = 1;
				}
				d[i][j] = minimum(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost); //places min value in that cell plus cost if any
			}
		}
		return d[n][m];
	}

	public static int minimum(int a, int b, int c) {	//finds minimum between cell above, to left, and left/upper diagonal
		int min;

		min = a;
		if (b < min) {
			min = b;
		}
		if (c < min) {
			min = c;
		}
		return min;
	}

	public static void print() {
		for (int x = 0; x < arrayWords.length; x++) {
			System.out.println(arrayWords[x]);
		}
	}
}

