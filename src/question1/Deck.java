
package question1;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

/**
 * This class represents a Deck of cards
 * @version 1.0
 * @author 100116544
 */
public class Deck implements Serializable, Iterable{
    static final long serialVersionUID = 112;
    private ArrayList<Card> deck = new ArrayList<>();
    
    /**
     * A constructor that creates all possible cards in a standard
     * deck and adds each card to an ArrayList named deck
     */
    public Deck() {
        for(Card.Suit s: Card.Suit.values()){
            for(Card.Rank r : Card.Rank.values()){
                deck.add(new Card(r,s));
            }
        }
        
        ArrayList<Card> serialDeck = new ArrayList<>();
        SecondCardIterator it = new SecondCardIterator(deck);
        while(it.hasNext()){
            Card card = (Card) it.next();
            serialDeck.add(card);
        }

        try {
            FileOutputStream file = new FileOutputStream("deck.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            
            out.writeObject(serialDeck);
            out.close();
            file.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
         
    /**
     *  Opens a given file and deserializes the object within, 
     *  in this case a deck
     * @param file Filename of the file to be deserialized
     * @return The deck from the file
    */
    public static ArrayList<Card> deserializeDeck(String file){
        ArrayList<Card> tmp = new ArrayList<>();
        
        try {
            FileInputStream fis = new FileInputStream("deck.ser");
            ObjectInputStream o = new ObjectInputStream(fis);
            tmp = (ArrayList<Card>) o.readObject();
            fis.close();
            o.close();
        } catch (Exception ex){
            ex.printStackTrace();    
        }
        
        return tmp;        
    }
    
    /**
     * Shuffles the deck resulting in a random order for the cards
     */
    public void shuffleDeck(){
        int sizeOfDeck = deck.size();
        Random r = new Random();
        r.nextInt();
        for(int i = 0; i < sizeOfDeck; i++){
            int x = i + r.nextInt(sizeOfDeck - i);
            Card temp = deck.get(i);
            deck.set(i, deck.get(x));
            deck.set(x, temp);
        }
    }
    
    /**
     * Remove the top card of the deck
     * @return Card - The top card of the deck
     */
    public Card deal(){
        return deck.remove(0);
    }

    /**
     * Return the current size of the deck
     * @return int - The current size of the deck
     */
    public int size(){
        return deck.size();
    }

    /**
     * Reinitialises the deck
     */
    public final void newDeck(){
        ArrayList<Card> newDeck = new ArrayList<>();
        for(Card.Suit s: Card.Suit.values()){
            for(Card.Rank r : Card.Rank.values()){
                newDeck.add(new Card(r,s));
            }
        }        
        deck = newDeck;
    }
   
    /**
     * An iterator for Card
     * @return Iterator - iterates over deck
     */ 
    @Override
    public Iterator<Card> iterator() {
        return deck.iterator();
    }
  
    /**
     * An inner class that traverses Cards by going over every other card
     */
    public static class SecondCardIterator implements Iterator<Card> {
        private int next;
        private ArrayList<Card> deck = new ArrayList();
        
        /**
         * A constructor that takes a deck of cards and declares a 
         * starting counter
         * @param d A deck of cards
         */
        public SecondCardIterator(ArrayList<Card> d){
            this.deck = d;
            this.next = -2;
        }
        
        /**
         * Checks to see if the current card has a card after it
         * @return boolean - true if there is a next card, false if not
         */
        @Override
        public boolean hasNext(){
            return next < deck.size()-2; 
        }
        
        /**
         * Gets the next card in the deck only if there is a next card
         * @return Card - the next card in the deck
         */
        @Override
        public Card next() {
            if(hasNext()){
                return deck.get(next+=2);
            } else {
                return null;
            }
        }
    }
    
    /**
     * Get the current deck
     * @return ArrayList - the current deck
     */
    public ArrayList<Card> getDeck(){
        return deck;
    }
    
    /**
     * A toString method that returns all cards in the deck
     * @return String - displaying the suit and rank of all cards
     */
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(Card card : deck){
            output.append(card).append("\n");
        }
        return output.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("--------------------");
        System.out.println("Testing class Deck:");
        System.out.println("--------------------");
        
        System.out.println("New deck created:");
        /** Checking card constructor by creating a new deck with
         *  all possible cards in the deck
        */
        Deck deck = new Deck();
        
        System.out.println("");
        System.out.println("Checking toString for deck:");
        System.out.println(deck.toString());
        
        System.out.println("Randomly shuffling the deck.");
        deck.shuffleDeck();
        
        System.out.println("");
        System.out.println("Checking that the deck has been shuffled:");
        System.out.println(deck.toString()); 
        
        
        System.out.println("Randomly shuffling the deck once "
                            + "more to check randomness");
        deck.shuffleDeck();
        
        System.out.println("");
        System.out.println("Checking that the deck has been shuffled:");
        System.out.println(deck.toString()); 
        
        System.out.println("Removing the top card from the deck:");
        System.out.println(deck.deal().toString());
        
        System.out.println("");
        System.out.println("The size of the deck shoud now be 51:");
        System.out.println("Deck size is: " + deck.size());
        
        System.out.println("");
        System.out.println("Reinitialising the deck.");
        deck.newDeck();
        System.out.println("Checking that the deck has been reintialised:");
        System.out.println(deck.toString());
                
        System.out.println("");
        System.out.println("Checking normal iterator:");
        Iterator it = deck.iterator();
        
        while(it.hasNext()){
            Object card = it.next();
            System.out.println(card);
        }
        
        System.out.println("");
        System.out.println("Deserializing the original deck to check \n"
                            + "the second card iterator:");
        String file = "deck.ser";
        
        ArrayList<Card> dDeck = deserializeDeck(file);
        for(Card card : dDeck){
            System.out.println(card);
        }
        
    }
}
