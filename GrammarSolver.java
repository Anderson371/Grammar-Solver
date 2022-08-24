/*
Anderson Lu
Cse 143 AN with May Wang
Homework 5, GrammarSolver
This program takes in grammar in Backus-Naur Form from
the user's input file. The user is able to randomly generate
the content in the grammar list.
*/
import java.util.*;

public class GrammarSolver {
   //Holds the content in the grammer list.
   //Set non-Terminals as keys and terminals as values.
   private SortedMap<String, List<String[]>> holdGrammar;
   
   /*
   Takes in a List<String> "grammar" as a parameter.
   
   Pre: grammar.size() > 0 or grammar contains non duplicates.
   Throws an IllegalArgumentException if preconditions are not met.
   
   Post: Stores all the content in the list grammar to a SortedMap
   for easier access later.
   */
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      holdGrammar = new TreeMap<>();
      for (String element : grammar) {
         //Separates the terminals and nonterminals.
         String[] split = element.split("::=");
         //Gets the nonterminals and checks for duplicates.
         String key = split[0];
         //Checks if the map already contains the key.
         if (holdGrammar.containsKey(key)) {
            throw new IllegalArgumentException();
         }
         //Splits the terminals.
         String[] val = split[1].split("[|]");
         //Creates list to store terminals as values for SortedMap.
         List<String[]> vList = new ArrayList<>();
         //Adds the terminals to the list.
         createVal(vList, val);
         holdGrammar.put(key, vList);
      }
   }
   
   /*
   Takes in List<String[]> "vList" and String[] "val" as
   parameters and adds the values to the list. While removing
   the spacing between each value.
   */
   private List<String[]> createVal(List<String[]> vList, String[] val) {
      //Adds the terminals to the list.
      for (int i = 0; i < val.length; i++) {
         //Removes the spacing at the start and between terminals.
         val[i] = val[i].trim();
         //Adds each individual terminals, separated by "|".
         vList.add(val[i].split("[ \t]+"));
      }
      return vList;
   }
   
   /*
   Takes in a String "symbol" as a parameter and
   checks if the string is in the list of grammar.
   */
   public boolean grammarContains(String symbol) {
      return holdGrammar.containsKey(symbol);
   }
   
   /*
   Takes in a String "symbol" and an Integer "times" as parameters.
   
   Pre: grammar contains symbol or time >= 0
   Throws an IllegalArgumentException if preconditions are not met.
   
   Post: Randomly generates the amount of occurrences of the given
   symbol.
   */
   public String[] generate(String symbol, int times) {
      if (!holdGrammar.containsKey(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      //Creates a String array to hold the occurrences
      String[] occur = new String[times];
      //Randomly generates terminals based on the times requested.
      for (int i = 0; i < times; i++) {
         occur[i] = helpGenerate(symbol);
      }
      return occur;
   }
   
   /*
   Takes in a String "symbol" as a parameter.
   Recurs through the SortMap of the grammar to generate
   a random number of that symbol.
   */
   private String helpGenerate(String symbol) {
      String random = "";
      //Checks base case of having a terminal.
      if (!holdGrammar.containsKey(symbol)) {
         return symbol;
      } else {
         //Sets new list equal to the list of terminals.
         List<String[]> val = holdGrammar.get(symbol);
         //Creates a random object to hold the random value,
         //Ranging to the size of the terminal list.
         Random rand = new Random();
         int myRandom = rand.nextInt(val.size());
         //Recurs through the list of nonterminals until a,
         //random terminal is selected.
         String[] array = val.get(myRandom);
         for (int i = 0; i < array.length; i++) {
            random += helpGenerate(array[i]) + " ";
         }
      }
      return random.trim();
   }
   /*
   Returns the nonterminal symbols from the list
   of grammars in a sorted and separated with commas,
   inside of square brackets.
   */
   public String getSymbols() {
      return holdGrammar.keySet().toString();
   }
}