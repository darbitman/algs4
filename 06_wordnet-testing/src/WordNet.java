import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.ST;
import java.util.ArrayList;

public class WordNet{
    private Digraph g;
    
    // maps IDs -> Nouns
    private ST<Integer, String> synsetIDmap;
    
    //maps Nouns -> IDs
    private ST<String, Integer> synsetNounMap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new java.lang.IllegalArgumentException("constructor argument null");
        }
        In synsetInput = new In(synsets);
        In hypernymInput = new In(hypernyms);
        while(synsetInput.hasNextLine()) {
            String[] tokens = synsetInput.readLine().split(",");
            String[] nouns = tokens[1].split(" ");
            int id = Integer.parseInt(tokens[0]);
            synsetIDmap.put(id, tokens[1]);
            for (String n : nouns) {
                synsetNounMap.put(n, id);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        
    }
 }