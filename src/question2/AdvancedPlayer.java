/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.io.Serializable;
/**
 *
 * @author 100116544
 */
public class AdvancedPlayer extends IntermediatePlayer implements Serializable{
    private final String NAME = "Advanced Player";
    
    /**
     * Return the players name
     * @return Players name as string
     */
    @Override
    public String getName(){
        return NAME;
    }
    
    /**
     * The advanced bases their bets off of card counting and so they
     * override this method
     * @return The calculated bet
     */
    @Override
    public int makeBet(){
        int cardCount = getCardCount();
        int bet;
        if(cardCount > 0){
            bet = getDefaultBet() * cardCount;
        } else {
            bet = getDefaultBet();
        }
        setBet(bet);
        return bet;
    }
}
