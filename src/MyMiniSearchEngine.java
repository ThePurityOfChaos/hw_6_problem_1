import java.util.*;

public class MyMiniSearchEngine {
    // default solution. OK to change.
    // do not change the signature of index()
    private Map<String, List<List<Integer>>> indexes;

    // disable default constructor
    private MyMiniSearchEngine() {
        indexes = new HashMap<>();
        List<Integer> innerList = new ArrayList<>();
        List<List<Integer>> outerList = new ArrayList<>();
        outerList.add(innerList);
        indexes.put("key", outerList);
    }

    public MyMiniSearchEngine(List<String> documents) {
        index(documents);
    }
    // each item in the List is considered a document.
    // assume documents only contain alphabetical words separated by white spaces.
    private void index(List<String> texts) {
        indexes = new HashMap<>();
        for (int i=0; i<texts.size(); i++) {
            String[] words = texts.get(i).toLowerCase().split(" ");
            for(int j=0; j<words.length; j++) {
                if(!indexes.containsKey(words[j])) {
                    List<List<Integer>> outerList = new ArrayList<>();
                    for(int k=0; k<texts.size(); k++) {
                        outerList.add(new ArrayList<>());
                    }
                    indexes.put(words[j], outerList);
                }
                indexes.get(words[j]).get(i).add(j);
            }
        }
    }
    // search(key) return all the document ids where the given key phrase appears.
    // key phrase can have one or two words in English alphabetic characters.
    // return an empty list if search() finds no match in all documents.
    public List<Integer> search(String keyPhrase) {
        List<Integer> result = new ArrayList<>();
        String[] keyWords = keyPhrase.toLowerCase().split(" ");
        if (keyWords.length == 1) {
            if(indexes.containsKey(keyWords[0])) {
                for (int i = 0; i < indexes.get(keyWords[0]).size(); i++) {
                    if (indexes.get(keyWords[0]).get(i).size() > 0) {
                        result.add(i);
                    }
                }
            }
            else{
                return null;
            }
            return result;
        }else{ //had help from Kevin but did work myself :D
           for(int i=0; i<keyWords.length-1; i++) { //< the total number of key words
               if (indexes.containsKey(keyWords[i])) { //if it contains the word at i
                   if (indexes.containsKey(keyWords[i + 1])) { //if it also contains the word at i+1
                       for (int j = 0; j < indexes.get(keyWords[i]).size(); j++) { //keyWords[i] is a List<List<Integer>> so its size is the number of documents
                           if (indexes.get(keyWords[i]).get(j).size() > 0 && indexes.get(keyWords[i + 1]).get(j).size() > 0) { //.get(keyWords[i]).get(j).size() is the size of an individual document
                               for (int k = 0; k < indexes.get(keyWords[i]).get(j).size(); k++) { //k is the index of each word
                                   if (indexes.get(keyWords[i]).get(j).get(k) == indexes.get(keyWords[i + 1]).get(j).get(k) - 1) { //.get(keyWords[i]).get(j).get(k) is the index of the word(i) in the document(j).
                                       if (!result.contains(j)) //if it's not already there
                                           result.add(j);
                                   }
                               }
                           }
                       }
                   } else { //necessary null
                       return null;
                   }
               }
               else { //necessary null
                   return null;
               }
           }
        return result;
        }

    }
}
