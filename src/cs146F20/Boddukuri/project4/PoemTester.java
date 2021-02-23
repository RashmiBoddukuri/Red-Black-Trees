package cs146F20.Boddukuri.project4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This JUnit tester creates a dictionary as a dictionary and spell checks given poem using it.
 * @author Rashmi Boddukuri
 * @version 10/5/2020
 */
class PoemTester {
	static RedBlackTree wordDictionary;
	
	/**
	 * Creates a red black tree to work with.
	 */
	@BeforeAll
	public static void setUp()
	{
		wordDictionary = new RedBlackTree(); //a red black tree is created before the following test methods
	}
	
	/**
	 * Tests the making of the dictionary red black tree where the tree serves as a dictionary for later use.
	 * @throws IOException
	 */
	@Test
	public void testCreateRBTDictionary() throws IOException 
	{
		BufferedReader In = new BufferedReader(new FileReader("./data/dictionary.txt")); //a reader made and reads the dictionary text file
		String line;
		long startMakeDictionaryTime = System.nanoTime(); //start time for creation of dictionary red black tree
		while ((line = In.readLine()) != null) 
		{
			wordDictionary.insert(line); // each line in the dictionary input file is added to the red black tree made earlier
		}
		long endMakeDictionaryTime = System.nanoTime(); //end time for creation of dictionary red black tree
		System.out.println("Time taken to create the dictionary: " + (endMakeDictionaryTime - startMakeDictionaryTime) + " nanoseconds.");
		System.out.println();
		In.close();
	}
	
	/**
	 * Tests the reading of a poem and looking its words up in the dictionary created for spell check purposes.
	 * Words are not found if misspelled or not in the dictionary.
	 * Poem: Autumn by T. E. Hulme
	 * @throws IOException
	 */
	@Test
	public void testSpellCheck1() throws IOException
	{
		BufferedReader In = new BufferedReader(new FileReader("./data/poem.txt")); //a reader made and reads the poem file
		String line;
		int wordsNotFound = 0; //counter that keeps track of number of words that were not found in dictionary
		ArrayList<String> notFoundWordsArray = new ArrayList<>(); //array list of words not found
		
		long startSpellCheckTime = System.nanoTime(); //start time for reading words in poem and looking them up in dictionary red black tree
		while ((line = In.readLine()) != null) 
		{
			String[] words = line.split(" "); //contains words from splitting each line to get individual words
			
			for(int i = 0; i < words.length; i++)
			{
				String thisWord = words[i].replaceAll("[^a-zA-Z]", "").toLowerCase(); //word in array words is removed of all punctuation and turned to lower case
				if (wordDictionary.lookup(thisWord) == null) //searches for this word in dictionary, if null updates wordsNotFound counter and adds to notFoundWordsArray
				{
					wordsNotFound++;
					notFoundWordsArray.add(thisWord);
				}
			}
		}
		long endSpellCheckTime = System.nanoTime(); //end time for reading words in poem and looking them up in dictionary red black tree
		System.out.println("Poem: Autumn by T. E. Hulme");
		System.out.println("Time taken to lookup and spellcheck words: " + (endSpellCheckTime - startSpellCheckTime) + " nanoseconds.");
		System.out.println("Number of words not found/misspelled: " + wordsNotFound);
		System.out.println("The words not in dictionary: " + notFoundWordsArray.toString());
		System.out.println();
		In.close();
	}
	
	/**
	 * Tests the reading of a poem and looking its words up in the dictionary created for spell check purposes.
	 * Words are not found if misspelled or not in the dictionary.
	 * Poem: Dreams by Langston Hughes
	 * @throws IOException
	 */
	@Test
	public void testSpellCheck2() throws IOException
	{
		BufferedReader In = new BufferedReader(new FileReader("./data/poemtwo.txt")); //a reader made and reads the poem file
		String line;
		int wordsNotFound = 0; //counter that keeps track of number of words that were not found in dictionary
		ArrayList<String> notFoundWordsArray = new ArrayList<>(); //array list of words not found
		
		long startSpellCheckTime = System.nanoTime(); //start time for reading words in poem and looking them up in dictionary red black tree
		while ((line = In.readLine()) != null) 
		{
			String[] words = line.split(" "); //contains words from splitting each line to get individual words
			
			for(int i = 0; i < words.length; i++)
			{
				String thisWord = words[i].replaceAll("[^a-zA-Z]", "").toLowerCase(); //word in array words is removed of all punctuation and turned to lower case
				if (wordDictionary.lookup(thisWord) == null) //searches for this word in dictionary, if null updates wordsNotFound counter and adds to notFoundWordsArray
				{
					wordsNotFound++;
					notFoundWordsArray.add(thisWord);
				}
			}
		}
		long endSpellCheckTime = System.nanoTime(); //end time for reading words in poem and looking them up in dictionary red black tree
		System.out.println("Poem: Dreams by Langston Hughes");
		System.out.println("Time taken to lookup and spellcheck words: " + (endSpellCheckTime - startSpellCheckTime) + " nanoseconds.");
		System.out.println("Number of words not found/misspelled: " + wordsNotFound);
		System.out.println("The words not in dictionary: " + notFoundWordsArray.toString());
		In.close();
	}

}
