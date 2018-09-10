package lse;

import java.io.FileNotFoundException;


public class SearchDriver {

	public static void main(String[] args) throws FileNotFoundException{
		LittleSearchEngine test = new LittleSearchEngine();
		String noise = "noisewords.txt";
		String docs = "docs.txt";
		test.makeIndex(docs, noise);
		System.out.println(test.getKeyword("Hello...."));

		
		
		System.out.println(test.keywordsIndex.get("little"));
		System.out.println(test.keywordsIndex.get("jumped"));
		System.out.println(test.top5search("color","strange"));
		System.out.println(test.loadKeywordsFromDocument("Lots of noise"));
	}

}