import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.ST;
//import java.util.ArrayList;

public class WordNet {
    private Digraph g;
    private SAP sap;
    
    // maps IDs -> Nouns
    private ST<Integer, String> synsetIDmap;
    
    // maps Nouns -> IDs
    private ST<String, Integer> synsetNounMap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new java.lang.IllegalArgumentException("constructor argument null");
        }
        In synsetInput = new In(synsets);
        In hypernymInput = new In(hypernyms);
        
        synsetIDmap = new ST<Integer, String>();
        synsetNounMap = new ST<String, Integer>();
        
        // read in noun synset
        // generate Noun -> ID map
        // and      ID -> Noun map
        while (synsetInput.hasNextLine()) {
            String[] tokens = synsetInput.readLine().split(",");
            String[] nouns = tokens[1].split(" ");
            int id = Integer.parseInt(tokens[0]);
            synsetIDmap.put(id, tokens[1]);
            for (String n : nouns) {
                if (!synsetNounMap.contains(n)) {
                    synsetNounMap.put(n, id);
                }
            }
        }
        
        g = new Digraph(synsetIDmap.size());
        while (hypernymInput.hasNextLine()) {
            String[] tokens = synsetInput.readLine().split(",");
            int tail = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                g.addEdge(tail, Integer.parseInt(tokens[i]));
            }
        }
        
        sap = new SAP(g);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetNounMap;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new java.lang.IllegalArgumentException("word argument to isNoun() is null");
        }
        return synsetNounMap.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException("a noun argument to distance() is not in WordNet");
        }
        int idA = synsetNounMap.get(nounA);
        int idB = synsetNounMap.get(nounB);
        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException("a noun argument to distance() is not in WordNet");
        }
        int idA = synsetNounMap.get(nounA);
        int idB = synsetNounMap.get(nounB);
        int ancestor = sap.ancestor(idA, idB);
        String nounAncestor = synsetIDmap.get(ancestor);
        return nounAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        
    }
 }