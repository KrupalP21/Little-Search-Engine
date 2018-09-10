package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		if (docFile == null) {
			throw new FileNotFoundException();
		}
		Scanner scan = new Scanner(new File(docFile));
		HashMap <String, Occurrence> hasher = new HashMap <String, Occurrence>();
		while (scan.hasNext() == true) {
			String keyWord = scan.next();
			keyWord = this.getKeyword(keyWord);
			if (hasher.containsKey(keyWord)) {
				Occurrence occurs = hasher.get(keyWord);
				occurs.frequency = occurs.frequency + 1;
			} else {
				Occurrence occurs = new Occurrence(docFile , 1);
				hasher.put(keyWord, occurs);
			}
			
		}
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return hasher;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		for (String key : kws.keySet()) {
			ArrayList<Occurrence> Occur = new ArrayList<Occurrence>();
			if (keywordsIndex.containsKey(key) == true) {
				Occur = keywordsIndex.get(key);
			}
			Occur.add(kws.get(key));
			insertLastOccurrence(Occur);
			keywordsIndex.put(key, Occur);
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		if (word == null) {
			return null;
		}
		// lower case
		word = word.toLowerCase();
		// gets rid of all punctuation at the END
		if (Character.isLetter(word.charAt(word.length() - 1)) == false) {
			word = hasEndingPuncDelete(word);
		}
		// loops through with all ending punctuation gone then sees if there
		// are any non alphabetic characters in the word
		for (int i = 0; i < word.length(); i++) {
			if (Character.isLetter(word.charAt(i)) == false) {
				return null;
			}	
		}	
		// check if resulting word is a noise word
		if (noiseWords.contains(word)) {
			return null;
		}
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return word;
	}
	private String hasEndingPuncDelete(String word) {
		//if (Character.isLetter(word.charAt(word.length() - 1)) == false) {
			//word = word.substring(0, word.length() - 1);
		//	hasEndingPuncDelete(word);
		//}
		//word = word.replaceAll(".,;!?", "");
		//return word;
		int count = 0;
		while (count < word.length())
		{
			char currentChar = word.charAt(count);
			if ((Character.isLetter(currentChar) == false)) {
				break;
			}
			count = count + 1;
		}
		word = word.substring(0, count);
		return word;
	}

	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Integer> Occur = new ArrayList<Integer>(); 
		for (int i = 0; i < occs.size() - 1; i++)
		{
			Occur.add(occs.get(i).frequency); 
		}
		int lastVal = occs.get(occs.size() - 1).frequency; 
		ArrayList<Integer> Mids = new ArrayList<Integer>();
		int max = Occur.size() - 1;
		int min = 0;
		while (min <= max) {
			int mid = 0;
			mid = (max + min) / 2;
			Mids .add(mid);
			if (Occur.get(mid) > lastVal) {
				min = mid + 1;
			} 
			if (Occur.get(mid) < lastVal) {
				max = mid - 1;
			}
			if (Occur.get(mid) == lastVal) {
				break;
			}
		}
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return Mids;
	}
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Occurrence> L1 = keywordsIndex.get(kw1);
		ArrayList<Occurrence> L2 = keywordsIndex.get(kw2);
		ArrayList<String> Result = new ArrayList<String>();
		int x = 0;
		int y = 0;
		int count = 0;
		if (L1 == null && L2 == null) {
			return Result;
		} else if (L1 != null && L2 != null) {
			while (count < 5 && (x < L1.size() || y < L2.size())) {
				if ((!Result.contains(L2.get(y).document)) && (L2.get(y).frequency > L1.get(x).frequency)) {
					Result.add(L2.get(y).document);
					count = count + 1;
					y = y + 1;
				} else if (L2.get(y).frequency < L1.get(x).frequency && (!Result.contains(L1.get(x).document))) {
					Result.add(L1.get(x).document);
					count = count + 1;
					x = x + 1;
				} else {
					if ((!Result.contains(L2.get(y).document))) {
						if (count < 5) {
							Result.add(L2.get(y).document);
							count = count + 1;
							y = y + 1;
						} else {
						y = y + 1; 
					}
					}
					if (!Result.contains(L1.get(x).document)) {
						Result.add(L1.get(x).document);
						count = count + 1;
						x = x + 1;
					} else {
						x = x + 1;
					}
				}
			}
		} else if (L2 == null) {
			while (count < 5 && (x < L1.size())) {
				Result.add(L1.get(x).document);
				count = count + 1;
				x = x + 1;
			}
		} else if (L1 == null) {
			while (count < 5 && (y < L2.size())) {
				Result.add(L2.get(y).document);
				count = count + 1;
				y = y + 1;
		}
		}
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return Result;
	
	}
}
