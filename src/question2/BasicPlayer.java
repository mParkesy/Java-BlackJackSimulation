/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import question1.*;
import java.util.List;


/**
 *
 * @author 100116544
 */
public class BasicPlayer implements Player, Serializable {
    private int balance;
    private int currentBet;
    private final int DEFAULT = 10;
    private Hand hand;
    private Card dealerCard;
    private int cardCount;
    private final String NAME = "Basic Player";
    
    /**
     * Constructor for Basic Player
     */
    public BasicPlayer(){
        balance = 200;
        hand = new Hand();
    }
    
    /**
     * Get the value of the dealers card
     * @return The card value
     */
    public int dealerCardValue(){
        return dealerCard.getCardValue();
    }
    
    /**
     * Get card count for use with advanced player
     * @return The card count
     */
    public int getCardCount(){
        return cardCount;
    }
    
    /**
     * Get the default bet for a player
     * @return The default bet as integer
     */
    public int getDefaultBet(){
        return DEFAULT;
    }
    
    /**
     * Set the bet for the current player
     * @param b The integer that will set the bet
     */
    public void setBet(int b){
        currentBet = b;
    }
    
    /**
     * Called when a new hand is played
     * @return The old hand, this will be useful in advanced player
     */
    @Override
    public Hand newHand() {
        Hand oldHand = hand;
        hand = new Hand();
        return oldHand;
    }
    
    /**
     * When a bet is made, standard bet should be returned
     * @return The bet made
     */
    @Override
    public int makeBet() {
        if(balance >= DEFAULT){
            currentBet = 10;
        }else{
            currentBet = 0;
        }
        return currentBet;
    }

    /**
     * Get current bet
     * @return The bet
     */
    @Override
    public int getBet() {
        return currentBet;
    }

    /**
     * Get the current players balance
     * @return The players balance as an integer
     */
    @Override
    public int getBalance() {
        return balance;
    }

    /**
     * Hit for a basic player
     * @return True if player should hit, false if not
     */
    @Override
    public boolean hit() {
        if(getHandTotal()< 17){
            System.out.println("HIT\n");
            return true;
        }else if(isBust()){
            System.out.println("BUST\n");
            return false;
        }else{
            System.out.println("STICK\n");
            return false;
        }
    }

    /**
     * Pass a card to the hand of the player
     * @param c The card to be added to the hand
     */
    @Override
    public void takeCard(Card c) {
        hand.add(c);
    }

    /**
     * Settle the bet for the current player at the end of a hand
     * @param p The amount the player has lost or won
     * @return True if there balance is greater than 0, false if not
     */
    @Override
    public boolean settleBet(int p) {
        balance += p;
        return balance > 0;
    }

    /**
     * Returns the hand total for the current player
     * @return Hand total as an integer
     */
    @Override
    public int getHandTotal() {
        return hand.checkHandValues();
    }

    /**
     * Checks to see whether the current player has blackjack
     * @return True if player has blackjack, false if not
     */
    @Override
    public boolean blackjack() {
        if(hand.handSize() == 2){
            return Card.isBlackjack(hand.get(0), hand.get(1));
        }    
        return false;
    }

    /**
     * Checks to see whether the current player is bust or not
     * @return True if  player is bust, false if not
     */
    @Override
    public boolean isBust() {
        return getHandTotal()> 21;
    }

    /**
     * Returns the players current hand
     * @return The hand
     */
    @Override
    public Hand getHand() {
       return hand;
    }

    /**
     * Stores the dealers first card for the player to access
     * @param c The dealers first card that was dealt to them
     */
    @Override
    public void viewDealerCard(Card c) {
        dealerCard = c;
    }

    /**
     * Looks over a list of old used cards and keeps a count based on low
     * and high cards
     * @param cards The list of old cards that have been played from the deck
     */
    @Override
    public void viewCards(List<Card> cards) {
        for(Card c : cards){
            int cardValue = c.getCardValue();
            if(cardValue <= 6){
                cardCount++; 
            }else if(cardValue >= 10){
                cardCount--;
            }
        }    
    }

    /**
     * This is called if the dealer has requested a new deck
     * It outputs to the console but also sets cardCount to zero so the
     * Advanced player does not base their bet on cards played from an old deck
     */
    @Override
    public void newDeck() {
        System.out.println("=====================");
        System.out.println("      New Deck!      ");
        System.out.println("=====================");
        cardCount = 0;
    }
  
    /**
     * Return the players name
     * @return Players name as string
    */
    @Override
    public String getName(){
        return NAME;
    }
    
}
