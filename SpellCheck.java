/**
Create words to solve a jumble problem of one sequence
of characters.
@author DAMIAN DOBROWOLSKI
@date 11.10.2023
*/

import java.io.*;   // for IO
import java.util.ArrayList;
import java.util.Arrays;

public class SpellCheck {
	public static final int NOT_FOUND = -1;
// default values for dictionary and document
	private static String dictFilename = "./";
	private static String docFilename = "dictionary.txt";
	private String [] dictionary; // Array of words in dictionary.

/*
Load dictionary used for spell checking
File must contain one word per line.
*/
	public SpellCheck(String filename) {
		ArrayList<String> words = new ArrayList<String>();
		FileReader filereader = null;

		try {
			filereader = new FileReader(filename);
		} catch (FileNotFoundException ex) {
			System.err.println(ex);
			return;
		}
		BufferedReader reader = new BufferedReader(filereader);
		String s;
		try {
			while ( (s = reader.readLine()) != null ) {
				words.add( s );
			}
	// convert arraylist to an array
			this.dictionary = words.toArray(new String [1]);
		} catch (IOException ex) {
			System.err.println("Error reading " + filename);
			return;
		}
	}

	/**
	@return ith element from dictionary
	@param i index to get String value of
	*/
	public String get(int i) {
		return dictionary[i];
	}

	/**
	* @param key word to search for in dictionary
	* @return index in list of words of spell checker
	*/
	public int indexOf(String key) {
		if (key == null)
			throw new IllegalArgumentException("argument to indexOf() is null"); 
		return indexOf(dictionary, key, 0, dictionary.length-1);
	}

	/**
	 * @param dictionary to search through
	@param key word to search for in dictionary
	@param lo lower search bound
	@param hi upper search bound
	@return index in array of key or NOT_FOUND if not found
	Uses String compareTo method
	*/
	private static int indexOf(String[] dictionary, String key, int lo, int hi) {
	// TODO
		int mid = lo + (hi - lo) / 2;
		if (lo > hi){
			return NOT_FOUND;
		} 
		if (key.compareTo(dictionary[mid]) < 0){
			return indexOf(dictionary, key, lo, mid - 1);
		}
		if (key.compareTo(dictionary[mid]) > 0){
			return indexOf(dictionary, key, mid + 1, hi);
		}
		return mid;
	}

	/**
	Places all permutations of s into strings.

	Uses recursion to take out first character and add rest of word to it and then
	call it again with the first plus next character.

	@param prefix will be concatenated to the permutations of s to
	form new strings
	@param strings will contain all strings of length
	(prefix.length + s.length). They are added in the base case of
	perm.

	*/
	private static void perm(String prefix, String s, ArrayList<String> strings) {
		int n = s.length();
		if (n == 0) { // base case
			strings.add(prefix + s);
		}
		else {
			// TODO
			for (int i = 0; i < s.length(); i++){
				String ch = Character.toString(s.charAt(i));
				String rem = s.substring(0,i) + s.substring(i+1, n);
				perm(prefix + ch, rem, strings);
			}
		}
	}

	/**
	Return ArrayList of all permutations of s.
	*/
	public static ArrayList<String> permutations(String s) {
		ArrayList<String> strings = new ArrayList<String>();
		perm("",s,strings);
		return strings;
	}


	/**
	Looks up all permutations of a string in the dictionary.
	USAGE: java SpellCheck dictionaryFile jumbled_letters
	*/
	public static void main(String[] args) throws FileNotFoundException, IOException {
		int minlen;
		String dictionaryFile = args[0];
		String letters = args[1];

		SpellCheck checker = new SpellCheck(dictionaryFile);

		int indexOf = checker.indexOf(letters);
		if (indexOf == SpellCheck.NOT_FOUND)
			System.out.println("Not found: "  + letters);
		else
			System.out.println("Position of " +  letters + " in dictionary " +
				indexOf + " " + checker.get(indexOf));

		for (String s : checker.permutations(letters)) {
			indexOf = checker.indexOf(s);
			if (indexOf != SpellCheck.NOT_FOUND)
				System.out.println(s);
		}
	}
}
