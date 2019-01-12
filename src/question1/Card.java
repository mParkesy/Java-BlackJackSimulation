package question1;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class represents a Card in a pack of decks
 * @version 1.0
 * @author 100116544
 */
public class Card implements Serializable, Comparable<Card> {
    static final long serialVersionUID = 111;
    private Rank rank;
    private Suit suit;
    
    /**
     * The four suits in a standard pack of cards
     */
    public enum Suit {
        CLUBS,
        HEARTS,
        DIAMONDS,
        SPADES
    }
    
    /**
     * The ranks with numerical values in a standard pack of cards
     */
    public enum Rank {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10),
        ACE(11);
        private final int value;
        
        /**
         * A constructor to set a constant value for each rank
         * @param i Takes an integer as a parameter to store a value
         */
        private Rank(int i){
            value = i;
        }
        
        /**
         * Get the value for a Rank
         * @return int - The card value
         */
        public int getValue(){
            return value;
        }
        
        /**
         * Get the previous Rank in the list of the Rank that was called on
         * @return Rank - The previous rank of the one that was called on
         */
        public Rank getPrevious(){
            if(this.equals(Rank.TWO)){
                return Rank.ACE;
            } else {
                return Rank.values()[this.ordinal() -1];
            }
        }  
    }
    
   /**
   * A constructor for class Card taking a rank and suit field as values
   * @param r  Rank to be passed to the constructor
   * @param s  Suit to be passed to the constructor 
   */
    public Card(Rank r, Suit s) {
        this.rank = r;
        this.suit = s;
        
    }

    /**
     * Get rank of card
     * @return Rank - The rank
     */
    public Rank getRank() {
        return this.rank;      
    }

    /**
     * Get suit of card
     * @return Suit - The suit
     */
    public Suit getSuit() {
        return this.suit;
    }
    
    /**
     * Get the rank or value of the current card
     * @return The rank of the card
     */
    public int getCardValue(){
        return rank.getValue();
    }
       
    /**
     * Sums two cards together
     * @param a Takes a card as a parameter which is to be summed
     * @param b Takes a second card as a parameter which is to be summed
     * @return int - The value of each card summed together
     */
    public static int sum(Card a, Card b){
        int value1 = a.getCardValue(); 
        int value2 = b.getCardValue();
        return value1 + value2;
    }
    
    /**
     * Checks to see whether two cards make blackjack
     * @param a Takes a card as a parameter
     * @param b Takes a second card as a parameter
     * @return boolean - True if two cards sum to 21, false if not
     */
    public static boolean isBlackjack(Card a, Card b){
        //long method
        //int value1 = a.getCardValue(); 
        //int value2 = b.getCardValue();
        //return value1 == 10 && value2 == 11 || value1 == 11 && value2 == 10;
        
        //short method
        return sum(a,b) == 21;
    }
    
    /**
     * Used to sort the cards into descending order when sorting occurs
     * @param card Takes a card as a parameter to be sorted
     * @return int - 0 if equal, -1 less than and 1 greater than
     */
    @Override
    public int compareTo(Card card){
        int comparison = card.rank.compareTo(this.rank);
        if(comparison == 0){
            comparison = card.suit.compareTo(this.suit);
        }
        return comparison;
    }
    
    /**
     * A comparator inner class that will sort cards to ascending order by rank
     */
    public static class CompareAscending implements Comparator<Card> {
            /**
             * Compares two cards by their rank and sorts into ascending order
             * @param a Takes a card to be compared as a parameter
             * @param b Takes a card to be compared as a parameter
             * @return int - 0 if equal, -1 less than and 1 greater than
             */
            @Override
            public int compare(Card a, Card b){
                int comparison = a.rank.ordinal() - b.rank.ordinal();
                return comparison;
            }
    }
    
    /**
     * A comparator inner class that will sort by suit then rank
     */
    public static class CompareSuit implements Comparator<Card> {
        /**
         * Compares two cards by their suit and then their rank and sorts into
         * ascending order
         * @param a Takes a card to be compared as a parameter
         * @param b Takes a card to be compared as parameter
         * @return int - 0 if equal, -1 less than and 1 greater than 
         */
        @Override
        public int compare(Card a, Card b){
            int comparison = a.suit.compareTo(b.suit);
            if(comparison == 0){
                comparison = a.rank.ordinal()- b.rank.ordinal();
            }
            return comparison;
            
        }
    }   
      
    /**
     * A toString method that returns the suit and rank of a card
     * @return String - displaying the suit and rank of a card
     */
    @Override
    public String toString(){
        return this.getRank() + " of " + this.getSuit();
    }
    
    /**
     * Testing the Card class
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");
        System.out.println("Testing class Card:");
        System.out.println("-------------------------------------------------");
        // intialise three test cards
        System.out.println("Checking Card constructors:");
        System.out.println("Create a card a with Rank two and suit hearts.");
        Card a = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        System.out.println("Create a card b with Rank king and suit clubs.");
        Card b = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        System.out.println("Create a card c with Rank ace and suit diamonds.");
        Card c = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        System.out.println("Create a card d with Rank five and suit spades.");
        Card d = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        System.out.println("Create a card c with Rank jack and suit diamonds.");
        Card e = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);        
        System.out.println("Create a card e with Rank four and suit diamonds.");
        Card f = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);                  
        
        System.out.println("");
        
        // checking enum methods
        System.out.println("Checking Enum methods:");
        System.out.println("Card c value: " + c.getCardValue());
        System.out.println("Get previous card of a: " 
                            + a.getRank().getPrevious());
        System.out.println("Get previous card of c: " 
                            + c.getRank().getPrevious());
        
        System.out.println("");
        
        // checking Card methosd
        System.out.println("Checking Card methods:");
        System.out.println("Card a suit: " + a.getSuit());
        System.out.println("Card b rank: " + b.getRank());
        System.out.println("Card a and b sum: " + Card.sum(a,b));
        System.out.println("Card b and c isBlackjack? " 
                            + Card.isBlackjack(b,c));
        
        System.out.println("");
        
        // check toString
        System.out.println("Checking toString for card a:");
        System.out.println(a.toString());
        
        Card[] cards=new Card[6];
        cards[0]= a;
        cards[1]= b;       
        cards[2]= c;
        cards[3]= d;  
        cards[4]= e;
        cards[5] = f;

        System.out.println("");
        System.out.println("Before sort with compareTo:");
        System.out.println(Arrays.toString(cards).replace("[","").replace("]",""));
        Arrays.sort(cards);
        System.out.println("After sort with compareTo:");
        System.out.println(Arrays.toString(cards).replace("[","").replace("]",""));
        
        System.out.println("");
        
        System.out.println("Before sort with compareAscending:");
        System.out.println(Arrays.toString(cards).replace("[","").replace("]",""));
        Card.CompareAscending comp = new Card.CompareAscending();
        Arrays.sort(cards, comp);
        System.out.println("After sort with compareAscending:");
        System.out.println(Arrays.toString(cards).replace("[","").replace("]",""));        

        System.out.println("");
        
        System.out.println("Before sort with compareSuit:");
        System.out.println(Arrays.toString(cards).replace("[","").replace("]",""));
        Card.CompareSuit comp2 = new Card.CompareSuit();
        Arrays.sort(cards, comp2);
        System.out.println("After sort with compareSuit:");
        System.out.println(Arrays.toString(cards).replace("[","").replace("]",""));

        System.out.println("");
        System.out.println("-------------------------------------------------");
    }
}
