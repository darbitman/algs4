public class Outcast {
    private final WordNet wn;
    
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

   // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = 0;
        String maxNoun = nouns[0];
        for (String s: nouns) {
            int dist = 0;
            for (String noun: nouns) {
                dist += wn.distance(noun, s);
            }
            if (dist > maxDist) {
                maxDist = dist;
                maxNoun = s;
            }
        }
        return maxNoun;
    }

    // see test client below
    public static void main(String[] args) {
        // empty test code
   }
}