package question1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Comparator;


/**
 * This class represents a Hand of cards
 * @version 1.0
 * @author 100116544
 */
public class Hand implements Iterable, Serializable { 
    static final long serialVersionUID = 102;
    public ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Card> handCopy = new ArrayList<>();
    private int[] numRank;
    public ArrayList<Integer> handValues = new ArrayList<>();
    
    
    /**
     * A constructor to create an empty hand
     */
    public Hand(){
        this.numRank = new int[13];
        this.hand = new ArrayList();
        this.handCopy = new ArrayList(hand);
    }
        
    /**
     * A constructor that takes an array of cards and adds them to the hand
     * @param cards An array of cards to be added to the  hand
     */
    public Hand(Card[] cards){
        this.hand = new ArrayList();
        this.numRank  = new int[13];
        for(Card card : cards){
            hand.add(card);
            numRank[card.getRank().ordinal()]++;
        }
        this.handCopy = new ArrayList(hand);
    }
    
    /**
     * A constructor that takes a different hand and adds all the cards
     * to this hand
     * @param newHand A hand to add to this hand
     */
    public Hand(Hand newHand){
        this.hand = new ArrayList();
        this.numRank = new int[13];
        for (Object c : newHand) {
            Card card = (Card) c; 
            hand.add((Card) card);
            this.numRank[card.getRank().ordinal()]++;
        }
        this.handCopy = new ArrayList(hand);
    }
          
    /**
     * Calculate all possible hand values that exist with ace high and low
     * taken into consideration
     */
    public void possibleHandValues(){
        int aceCount =0;
        int total = 0;
        handValues.clear();
        for(Card card : hand){
            if(card.getCardValue() == 11){
                aceCount++;
            }
            total += card.getCardValue();
        }
            for(int i = 0; i <= aceCount; i++){
                handValues.add(total - (i*10));   
        }    
    }
    
    /**
     * Returns the hard value from the array of possible values
     * @return The hard value for the hand
     */
    public int getHardValue(){
        possibleHandValues();
        return Collections.max(handValues);   
    }
   
    /**
     * Returns the soft value from the array of possible values
     * @return The soft value for the hand
     */
    public int getSoftValue(){
        possibleHandValues();
        return Collections.min(handValues);
    }
    
    /**
     * If there are more than two possible values then the hardest non bust 
     * value could be in the middle of the array so all values need 
     * to be checked
     * @return The highest possible non bust value for the hand, if all values
     *         are bust then return smallest value
     */
    public int checkHandValues(){
        possibleHandValues();
        for(int value : handValues){
            if(value <= 21){
                return value;
            }
        }         
        return handValues.get(handValues.size()-1);
    }
    
    
    public ArrayList<Integer> getHandValues(){
        return handValues;
    }
    
    /**
     * Returns the number of each rank
     * @return The number of each rank array
     */
    public int[] getNumRank(){
        return numRank;
    }
        
    /**
     * Add a card to the hand
     * @param card A card being added to the hand 
     */
    public void add(Card card) {
        this.numRank[card.getRank().ordinal()]++;
        hand.add(card);
        handCopy.add(card);
        possibleHandValues();
    }
    
    /**
     * Add a collection of cards to the hand
     * @param collection A collection of cards that needs to be added to hand
     */
    public void add(Collection<Card> collection){
        for(Card card : collection){
            this.numRank[card.getRank().ordinal()]++;
            hand.add(card);
            handCopy.add(card);
            possibleHandValues();
        }
    }
    
    /**
     * Add another hand to the current hand
     * @param newH The hand to be added
     */
    public void add(Hand newH){
        for(Object card : newH){
            Card newCard = (Card) card;
            this.numRank[newCard.getRank().ordinal()]++;
            hand.add(newCard);
            handCopy.add(newCard);
            possibleHandValues();
        }
    }
    
    /**
     * Remove a single card from the hand if it is in the hand
     * @param removeCard The card to be removed from the hand if it exists
     * @return True if card was removed and false if not
     */
    public boolean remove(Card removeCard){
        for (Card card : hand){
            if(removeCard == card){
                numRank[removeCard.getRank().ordinal()]--;
                hand.remove(card);
                handCopy.remove(card);
                possibleHandValues();
                return true;
            }
        }
        return false;
    }

        
    /**
     * Remove all cards in the given hand from the current hand
     * @param removeHand The hand containing the cards to be removed
     * @return True if all cards in given hand were removed from current hand,
     *         false if not
     */
    public boolean remove(Hand removeHand){
        int numRemoved = 0;
        
        for(Object card : removeHand){
            Card removeCard = (Card) card;
            if(hand.contains(removeCard)){
                numRank[removeCard.getRank().ordinal()]--;;
                hand.remove(removeCard);
                handCopy.remove(removeCard);
                possibleHandValues();
                numRemoved++;
            }
        }
        
        return numRemoved == removeHand.handSize();
    }
    
    /**
     * Remove the card based on the position given
     * @param removePosition The position of the card to be removed
     * @return The card that was removed or null
     */
    public Card remove(int removePosition){
        int position = 0;
        for(Card card: hand){
            if(removePosition == position){
                numRank[card.getRank().ordinal()]--;
                hand.remove(card);
                handCopy.remove(card);
                possibleHandValues();
                return card;
            }  
            position++;
        }
        return null;
    }
    
    /**
     * Get the size of the current hand
     * @return The size of the given hand
     */
    public int handSize(){
        return hand.size();
    }
    
    /**
     * An iterator that traverses the cards in the hand in the order 
     * they were originally added
     * @return The iterator
     */
    @Override
    public Iterator<Card> iterator(){
        Iterator<Card> it = handCopy.iterator();
        // override normal methods here?
        
        return it;
    }
    
    /**
     * Sort the current hand into descending order using
     * Card classes comparable method
     */
    public void sortDescending(){
        Collections.sort((ArrayList<Card>) hand);
    }
    
    /**
     * Sort the current hand into ascending order using
     * Card classes comparator CompareAscending
     */
    public void sortAscending(){
        Comparator ascending = new Card.CompareAscending();
        Collections.sort((ArrayList<Card>) hand, ascending);
    }
     
    /**
     * Count the number of cards currently in the hand with the given suit
     * @param suit The given suit
     * @return The total number of cards with the suit
     */
    public int countSuit(Card.Suit suit){
        int total = 0;
        for(Card card : hand){
            Card.Suit checkSuit = card.getSuit();
            if(suit == checkSuit){
                total++;
            }
        }
        return total;
    }
    
    /**
     * Count the number of cards currently in the hand with the given rank
     * @param rank The given rank
     * @return The total number of cards with the rank
     */
    public int countRank(Card.Rank rank){
        int total = 0;
        for(Card card : hand){
            Card.Rank checkRank = card.getRank();
            if(rank == checkRank){
                total++;
            }
        }
        return total;
    }
   
    /**
     * Check to see whether the value given is smaller than all hand values
     * @param testValue The given value
     * @return True if the value is smaller, false if not
     */
    public boolean isOver(int testValue){
        int minValue = hand.iterator().next().getCardValue();
        for(Card card : hand){
            if(card.getCardValue() < minValue){
                minValue = card.getCardValue();
            }
        }
        return minValue > testValue;
    }
    
    /**
     * Reverses the current hand and returns it
     * @return A reversed hand
     */
    public Hand reverseHand(){
        Collection<Card> reverse = new ArrayList<>();
        for(int i = hand.size()-1; i >= 0; i--){
            reverse.add(hand.get(i));
        }
        Hand reverseHand = new Hand();
        reverseHand.add(reverse);
        return reverseHand;
    }
    
    /**
     * Get the original order cards were added to the hand.
     * @return ArrayList of cards.
     */
    public ArrayList<Card> getOrderAdded(){
        return handCopy;
    }
    
    /**
     * Get a card from a specific index (useful to check blackjack)
     * @param i The index of the card in the hand
     * @return The card in specific index
     */
    public Card get(int i){
        return hand.get(i);
    }
    
    /**
     * Check to see if a hand contains an ace
     * @return True if contains ace, false if not
     */
    public boolean containsAce(){
        for (Card c : hand) {
            if(c.getCardValue() == 11){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Adds every card in the hand to a string
     * @return The built string with cards from hand
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder( );
        for(Card card : hand){
            str.append(card).append("\n");
        }
        return str.toString();
    }    
    
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");
        System.out.println("Testing class Hand:");
        System.out.println("-------------------------------------------------");    
        System.out.println("Checking Hand constructors:");
        
        Hand empty = new Hand();
        System.out.println("Testing construction of empty hand:");
        System.out.println(empty.toString());
        System.out.println("");
        
        System.out.println("Testing construction by adding an array of cards:");
        Card a = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card b = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card c = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card d = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        System.out.println("Creating Cards...");
        Card[] cards=new Card[4];
        cards[0]= a;
        cards[1]= b;       
        cards[2]= c;
        cards[3]= d;  
        Hand newHand = new Hand(cards);
        System.out.println("Cards added to hand newHand:");
        System.out.println(newHand.toString());
        
        System.out.println("Testing construction by adding a "
                            + "hand to the new hand:");
        System.out.println("Hand constructed with newHand added to it:");
        Hand test = new Hand(newHand);
        System.out.println(test.toString());
        
        System.out.println("All possible values for newHand:");
        newHand.possibleHandValues();
        for(int x : newHand.getHandValues()){
            System.out.println(x);
        }
        
        System.out.println("");
        System.out.println("The hard value of the hand: "
                + newHand.getHardValue());
        System.out.println("The soft value of the hand: "
                + newHand.getSoftValue());
          
        System.out.println("");
        System.out.println("Getting lowest value card from hand with first "
                + "ace counting as high:");
        System.out.println(newHand.checkHandValues());
        System.out.println("");
        
        System.out.println("");
        System.out.println(newHand.toString());
        System.out.println("Testing that number of each rank has been"
                + "stored correctly:");
        int num[] = newHand.getNumRank();
        Card.Rank type[] = Card.Rank.values();
        for(int i = 0; i < num.length; i++){
            System.out.println("There are: "
                    + "" + num[i] + " " + type[i].toString());
        }
        
        
        Card g = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        System.out.println("\nAdd single card to hand: " + g.toString());
        newHand.add(g);
        System.out.println(newHand.toString());
        
        Collection<Card> collection = new ArrayList<>();
        Card e = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card f = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        collection.add(e);
        collection.add(f);
        System.out.println("Add collection to hand: " + collection.toString());
        newHand.add(collection);   
        System.out.println(newHand.toString());
        
        Hand test2 = new Hand();
        test2.add(new Card(Card.Rank.FIVE, Card.Suit.HEARTS));
        System.out.println("Add hand to hand: " + test2.toString());
        newHand.add(test2);
        System.out.println(newHand.toString());
        
        System.out.println("Remove single card from hand: " + g.toString());
        boolean x = newHand.remove(g);
        System.out.println(x);        
        System.out.println("");
        
        System.out.println("Remove hand from hand: " + test2.toString());
        newHand.remove(test2);
        System.out.println(newHand.toString());
        System.out.println("");
        
        System.out.println("Remove card based on positon 5: ");
        Card h = newHand.remove(5);
        System.out.println("Card removed is: " + h.toString());
        System.out.println(newHand.toString());
        
        System.out.println("Hand size should now be 5:");
        System.out.println(newHand.handSize());
        System.out.println("");
        
        System.out.println("Checking normal iterator:");
        Iterator it = newHand.iterator();
        
        while(it.hasNext()){
            Object card = it.next();
            System.out.println(card.toString());
        }
       
        System.out.println("\nAdding extra cards to hand for sorts...");
        Card i = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card j = new Card(Card.Rank.KING, Card.Suit.SPADES);
        newHand.add(i);
        newHand.add(j);
        System.out.println("New Hand: \n" + newHand.toString());
        System.out.println("");
        newHand.sortDescending();
        System.out.println("Descending: \n" + newHand.toString());
        System.out.println("");
        newHand.sortAscending();
        System.out.println("Ascending: \n" + newHand.toString());
        
        System.out.println("\nThe number of spades in the hand are: " 
                        + newHand.countSuit(Card.Suit.SPADES));
        System.out.println("\nThe number of aces in the hand are: " 
                        + newHand.countRank(Card.Rank.ACE));
        
        System.out.println("\nTesting isOver with value 2: " 
                            + newHand.isOver(2));
        
        System.out.println("\nThe hand reversed:\n" 
                            + newHand.reverseHand().toString());
        
        System.out.println("\nGet first card in hand: \n" + newHand.get(0));
        
        System.out.println("\nDoes the hand contain an ace: " 
                            + newHand.containsAce());
        
        System.out.println("\nHand toString: \n" + newHand.toString());
        System.out.println("\nHand in its orginally added order: ");
        for(Card card : newHand.getOrderAdded()){
            System.out.println(card);
        }
    }
    
    
}
