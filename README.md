# Little-Search-Engine
Scan through documents to find key words and their frequencies, input these values into a hash map, where the word is the key, and frequency and document name are the value. 

## Objective
Use a scanner to look through words within a document. Identify words that are "Noise Words" by seeing if they are contained in a preloaded Noise Words hash map. Words that are not noise words are added to a hashmap where the key is the word, and value is the frequency and document name. Then merge key words from each hash map into a single grand hashmap. For this hashmap, the key is again the word, and the value is an array of frequency and document name pairs. If a keyword is found in multiple documents, the keyword and frequency will be placed into the arraylist. Use binary search to insert word by occurrence and finally a method to find documents with the top 5 documents that return certain strings.
 Methods:
 ```
 public LittleSearchEngine                                 //Creates new Hashmaps of keywords and noise words
 public void makeIndex(String docsFile, String noiseFile)  //indexes all keywords found in all documents            
 public HashMap loadKeyWords(String docFile)               //Loads all keywords from into hashmap
 public void mergeKeyWords(Hashmap kws)                    //Merges keywords from single document into master keywordsIndex
 public void getKeyword(String word)                       //Returns the keyword (without punctuation) if it passes the keyword test
 public ArrayList insertLastOccurrence(ArrayList occs)     //Insert word and occurence into correct location in list using binary search
 public ArrayList top5search(Strign kw1, String kw2)       //Returns top 5 documents containing either kw1 or kw2 in order
 ```

### License

All use of this code must comply with the Rutgers University Code of Student Conduct.
