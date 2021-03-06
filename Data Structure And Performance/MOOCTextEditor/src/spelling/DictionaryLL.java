package spelling;

import java.util.LinkedList;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	
    // TODO: Add a constructor


    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
	public DictionaryLL()
	{
		dict = new LinkedList<String>();
	}
	
    public boolean addWord(String word) {
    	// TODO: Implement this method
    	String lowerWord = new String(word.toLowerCase());
    	boolean isNewWord = true;
    	for (String dictWord: dict) {
    		if (dictWord.equals(lowerWord)) {
    			isNewWord = false;
    			break;
    		}
    	}
    	if (isNewWord)
    		dict.add(lowerWord);
        return isNewWord;
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        // TODO: Implement this method
        return dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
    	String lowerWord = new String(s.toLowerCase());
    	boolean found = false;
    	for (String dictWord: dict) {
    		if (dictWord.equals(lowerWord)) {
    			found = true;
    			break;
    		}
    	}
        return found;
    }

    
}
