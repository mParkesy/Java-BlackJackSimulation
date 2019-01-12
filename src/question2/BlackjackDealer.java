/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.io.Serializable;
import java.util.ArrayList;
import question1.*;
import java.util.List;

/**
 * @author 100116544
 */
public class BlackjackDealer implements Dealer, Serializable {
    static final long serialVersionUID = 223;
    private Deck deck;
    private Hand dealerHand;
    private List<Player> players = new ArrayList<>();    
    
    /**
     * Blackjack dealer constructor, creates a new deck and shuffles it
     * Creates a new hand for the dealer
     */
    public BlackjackDealer(){
        deck = new Deck();
        deck.shuffleDeck();
        dealerHand = new Hand();
    }
    
    /**
     * Assigns players to the dealer list. It is cleared before players are
     * added any updated lists from the table will be added as duplicates
     * @param p List of players for update
    */
    @Override
    public void assignPlayers(List<Player> p) {
        players.clear();
        players.addAll(p);
    }

    /**
     * Gets bets from all players in player list
     */
    @Override
    public void takeBets() {
        int betAmount;
        for(Player p : players){
            betAmount = p.makeBet();
            if(p.getBalance() >0){
                System.out.println(p.getName() +" bet: " + betAmount);
            }
        }
    }

    /**
     * Deals first cards to players and dealer
     * Reveals dealers first card to player
     */
    @Override
    public void dealFirstCards() {
        dealerHand = new Hand();
        Card dealerCard = deck.deal();
        for(Player p : players){
            // dealers card revealed to player (not used by basic player)
            p.viewDealerCard(dealerCard);
            p.takeCard(deck.deal());
            p.takeCard(deck.deal());
        }
        dealerHand.add(dealerCard);
    }

    /**
     * Play a hand with the given play
     * @param p A player who wishes to play  a hand
     * @return The players hand value
     */
    @Override
    public int play(Player p) {
        if(deck.size() < (52/4)){
            deck.newDeck();
            deck.shuffleDeck();
            p.newDeck();
            
        }
        boolean hit;
        // will loop while player wishes to hit
        do{
            // check to see if player wishes to hit
            hit = p.hit();
            if(hit == true){
                Card card = deck.deal();
                // player takes card
                p.takeCard(card);
                System.out.println(card.toString() + " has been dealt.\n");
                // checks to see if player has blackjack and outputs to console
                if(p.blackjack()){
                    System.out.println("Blackjack!");
                    hit = false;
                }                
            }else{
                hit = false;
            }   
        }while(hit == true);
       
        return p.getHandTotal();
    }

    /**
     * Plays the dealers hand
     * @return The dealers hand total after hand played
     */
    @Override
    public int playDealer() {
        System.out.println("\n=======================");
        System.out.println("Dealers Play");
        System.out.println("=======================");
        System.out.println(dealerHand.toString()
                + " has been dealt to dealer.\n");
        Card card;
        // while the dealer has less than 17 then get new cards
        while(dealerHand.checkHandValues()< 17){
            System.out.println("HIT\n");
            card = deck.deal();
            dealerHand.add(card);
            System.out.println(card.toString()
                    + " has been dealt to dealer.\n");
        }
        // check if dealer has bust or stuck
        if(dealerHand.checkHandValues() > 21){
            System.out.println("BUST\n");
        }else{
            System.out.println("STICK\n");
        }
        int value = dealerHand.checkHandValues();
        return value;
    }

    /**
     * Totals up a hands value
     * @param h The hand to be totalled up
     * @return The hands best possible value
     */
    @Override
    public int scoreHand(Hand h) {
        return h.checkHandValues();
    }

    /**
     * Settles all bets for all remaining players
     */
    @Override
    public void settleBets() {
        // a list is created to store all old played cards
        ArrayList<Card> oldCards = new ArrayList<>();
        // the dealers old hand is added to the list
        for(Object c : dealerHand){
            Card oldCard = (Card) c;
            oldCards.add(oldCard);
        }
       
        // get the dealers hand total
        int dealerScore = scoreHand(dealerHand);
        boolean dealerBlackjack = false;
        // check to see if the dealer has blackjack
        if(dealerScore == 21 && dealerHand.handSize() == 2){
            dealerBlackjack = true;
        }
        // list through all players
        for(Player p : players){
            // get player hand
            Hand playerHand = p.getHand();
            // get the score for the players hand
            int playerScore = scoreHand(playerHand);
            boolean hasFunds = true;
            
            System.out.println("=======================\n");
            // if player is bust they lose
            if(p.isBust()){
                System.out.println(p.getName() 
                        +" is bust.");
                hasFunds = p.settleBet(-p.getBet());
            
            // if player has blackjack and dealer does not then player wins
            }else if (p.blackjack() && !dealerBlackjack){    
                System.out.println(p.getName()   
                        +" has won with a blackjack!");
                hasFunds = p.settleBet(p.getBet()*2);
            
            /**
             * if players does not have blackjack and dealer does
             * then player loses
            */
            }else if(!p.blackjack() && dealerBlackjack){    
                System.out.println("Dealer has Blackjack!\n");
                System.out.println(p.getName()  
                        +" has lost.");
                hasFunds = p.settleBet(-p.getBet());
            
            // if player is not bust and dealer is then player wins    
            } else if(!p.isBust() && dealerScore > 21) {
                System.out.println(p.getName()   
                        +" has won their betted amount!");
                hasFunds = p.settleBet(p.getBet());
                
            // if playerscore is better than dealer score then player wins    
            } else if(playerScore > dealerScore){
                System.out.println(p.getName()   
                        +" has won!.");
                hasFunds = p.settleBet(p.getBet());
                
            // if player score is lower then dealer score then player loses    
            }else if(playerScore < dealerScore){
                System.out.println(p.getName()   
                        +" has lost.");
                hasFunds = p.settleBet(-p.getBet());
                
            // if socres are the same then player retains there stake    
            }else if(playerScore == dealerScore || 
                    (p.isBust() && dealerScore > 21)){
                System.out.println(p.getName()   
                        +" has retained there stake.");
                hasFunds = p.settleBet(0); 
            }
            // output balance to console
            System.out.println("Balance: " + p.getBalance() + "\n");
            
            // get players old hand and call new Hand
            Hand oldHand = new Hand(p.newHand());
            // the players old hand is added to old card list
            for(Object c : oldHand){
                Card card = (Card) c;
                oldCards.add(card);
            }
            /**
             * The old card list is past to the player so they can card
             * count, this will only be used by the advanced player
            */
            p.viewCards(oldCards);
            // the list must be cleared for the next player
            oldCards.clear();
        }
        System.out.println("=======================");
    }    
    
}
