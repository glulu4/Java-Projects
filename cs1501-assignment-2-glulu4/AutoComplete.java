/**
 * An implementation of the AutoCompleteInterface using a DLB Trie.
 */

 public class AutoComplete implements AutoCompleteInterface {

  private DLBNode root;
  private StringBuilder currentPrefix;
  private DLBNode currentNode;
  //TODO: Add more instance variables if you need to

  public AutoComplete(){
    root = null;
    currentPrefix = new StringBuilder();
    currentNode = null;
  }

  /**
   * Adds a word to the dictionary in O(word.length()) time
   * @param word the String to be added to the dictionary
   * @return true if add is successful, false if word already exists
   * @throws IllegalArgumentException if word is the empty string
   */
    public boolean add(String word){
      // throws exception for empty string
      if (word == "") throw new IllegalArgumentException("calls add() with an empty string");

      // if it's already in the dictionary, we return false
      // commented this out because it wasnt adding a because it was there already
      if ( contains(word) )
        return false; 
      root = add(root, word, 0 ); // sending in root node, word to be added, and position 0 of the string
      return true;
    }

    private DLBNode add( DLBNode x, String word, int pos ){
      // need to set parent and prev sibling
      DLBNode result = x;
      if (x == null){ // if this level is empty
          //DLBNode prev = result; // storing previous node so we can link it up
          result = new DLBNode(word.charAt(pos));
          result.size++;
          //result.parent = prev; // new nod'es parent is the previous node
          //prev.child = result; // previous nod'es child is the new node!


          if( pos < word.length()-1 ){ // made in call of parent
            result.child = add(x, word, pos+1); // recursing on the child node
            //result.parent = x;
          } 
          else {
            result.isWord = true;
          }
      } 
      else if( x.data == word.charAt(pos) ){ // if the letter in the word is already there
          if( pos < word.length()-1 ){ // not at the end of the word 
            result.size++;
            result.parent = x.parent;
            result.child = add(x.child, word, pos+1); //recursing on the child node might update parent here 
            //result.parent = x;
          } 
          else {
            result.size++;
            result.isWord = true;
          }
      } 
      else { // we go to the next letter in the list and see if we have a macth, if not we add it
        result.previousSibling = x.previousSibling;
        result.nextSibling = add(x.nextSibling, word, pos); // recursing on the sibling node
        //result.previousSibling = x.previousSibling; // bf or after 

      }
      return result;
    }

      /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(length of the current prefix). 
   */
    public void add(){
      // if the prefix is a word, we make it a word!
      // checking if teh current node is marked as a word
      if (currentPrefix.toString() =="")
        return;
       if ( isWord() ){
        currentNode.isWord = true;
        return;
       }
       else{
        add(currentPrefix.toString());
       }

    }


  /**
   * appends the character c to the current prefix in O(1) time. This method 
   * doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word 
   * in the dictionary and false otherwise
   */
    // might be done child 
    public boolean advance(char c){

      currentPrefix = currentPrefix.append(c); // adds letter to current prefix in O(1) times :|
      // below line works, just not in sopecified runtime
      currentNode = getNode(root, currentPrefix.toString(), 0); // updating current node to the last char of current prefix

 

      // if ( currentNode == null ){
      //   currentNode = root;
        
      //   while( currentNode.data != c ) {
      //     currentNode = currentNode.nextSibling;
      //   }
      // }
      // else
      //   currentNode = currentNode.child;

      
      
    
      
      // if the prefix is in the dictionary.. 
      //return checkForPrefix(currentPrefix.toString());
      return contains(currentPrefix.toString());
        
    }

    

  /**
   * removes the last character from the current prefix in O(1) time. This 
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      if (currentPrefix.toString().equals( "" )){
        throw new IllegalStateException("Tried to remove a char from an empty string!");
      } 

      try{
        currentPrefix = currentPrefix.deleteCharAt(currentPrefix.length()-1 ); // removes last chaarcetr
      }
      catch(Exception e){
        throw new IllegalStateException("Tried to remove a char from an empty string!");
      }
      // below line works, just not in specified run time
      currentNode = getNode(root, currentPrefix.toString(), 0); // updating current node to the last char of current prefix
      //currentNode = currentNode.parent;
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      currentPrefix = new StringBuilder("");
      currentNode = null; 
    }
    
  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise
   */
    public boolean isWord(){
      if (currentNode == null )
        return false;
      else
        return currentNode.isWord;
    }

  /** 
   * @return the number of words in the dictionary that start with the current 
   * prefix (including the current prefix if it is a word). The running time is 
   * O(1).
   */
    // DONE
    public int getNumberOfPredictions(){
      if (currentNode == null )
        return 0;
      else
        return currentNode.size;
    }
  
  /**
   * retrieves one word prediction for the current prefix. The running time is 
   * O(prediction.length()-current prefix.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){
      // if the currentprefix is a word, return that! 
      if ( currentNode == null ){
        return null;
      }
      if ( currentNode.isWord )
        return currentPrefix.toString();

      // if that doenst work, we need to search tree to find the nedt possible word find children nodes
      String result = currentPrefix.toString();
      for ( DLBNode i = currentNode.child; i != null || i.isWord; i = i.child){
        // if the isWord flag is true, add that letter and return it
        if ( i.isWord ){
          return result + i.data; 
        }
        else{
          result += i.data; 
        }
      }
      return null;
    }

    private boolean contains( String key ){
      return (getNode(root, key, 0) != null );
    }


  /* ==============================
   * Helper methods for debugging.
   * ==============================
   */

  //print the subtrie rooted at the node at the end of the start String
  public void printTrie(String start){
    System.out.println("==================== START: DLB Trie Starting from \""+ start + "\" ====================");
    if(start.equals("")){
      printTrie(root, 0);
    } else {
      DLBNode startNode = getNode(root, start, 0);
      if(startNode != null){
        printTrie(startNode.child, 0);
      }
    }
    
    System.out.println("==================== END: DLB Trie Starting from \""+ start + "\" ====================");
  }

  //a helper method for printTrie
  private void printTrie(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
      System.out.println(" (" + node.size + ")");
      printTrie(node.child, depth+1);
      printTrie(node.nextSibling, depth);
    }
  }

  //return a pointer to the node at the end of the start String.
  private DLBNode getNode(DLBNode node, String start, int index){
    if(start.length() == 0){
      return node;
    }
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data == start.charAt(index))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data == start.charAt(index))) {
          result = node;
      } else {
          result = getNode(node.nextSibling, start, index);
      }
    }
    return result;
  }
  
  private boolean checkForPrefix(String prefix ){
    DLBNode node = root;

    StringBuilder result = new StringBuilder("");


    for ( int position = 0; position < prefix.length(); position++ )
    {
      if ( node.data == prefix.charAt(position) ){
        result.append(node.data);

        
        if ( node.child != null )
          node = node.child;
        else
          node = node.nextSibling;
      }

    }

    if (result.toString().equals(prefix))
      return true;
    else
      return false;
  }

  //The DLB node class
  private class DLBNode{
    private char data;
    private int size;
    private boolean isWord;
    private DLBNode nextSibling;
    private DLBNode previousSibling;
    private DLBNode child;
    private DLBNode parent;

    private DLBNode(char data){
        this.data = data;
        size = 0;
        isWord = false;
        nextSibling = previousSibling = child = parent = null;
    }
  }
}
