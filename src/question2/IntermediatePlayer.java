/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.io.Serializable;
import question1.*;

/**
 *
 * @author 100116544
 */
public class IntermediatePlayer extends BasicPlayer implements Serializable{
    private final String NAME = "Intermediate Player";
    
    /**
     * Return the plauyers name
     * @return Name as string
     */
    @Override
    public String getName(){
        return NAME;
    }

    /**
     * Hit based on the dealers first card
     * @return True if player wishes to hit, false if not
     */
    @Override
    public boolean hit() {
        Hand hand = getHand();
        int dealerCardValue = dealerCardValue();
        int playerSoftValue = hand.getSoftValue();
        
        // if player is bust
        if(isBust()){
            System.out.println("BUST\n");
            return false;        
        // if dealers card is 7 or greater
        }else if(dealerCardValue >= 7){
            // while player is not bust and value is less than 17
            while(!isBust() && getHandTotal()<17){
                System.out.println("HIT\n");
                return true;
            }
        // if dealer card is 6 or less and is not an ace
        }else if(dealerCardValue <= 6 && dealerCardValue != 11){
            // while player is not bust and value is less than 12
            while(!isBust() && getHandTotal()<12){
                System.out.println("HIT\n");
                return true;
            }         
        // if player does not have an ace and there soft value is 9 or 10    
        }else if(hand.containsAce() && (playerSoftValue == 9 ||
                playerSoftValue == 10)){
            System.out.println("STICK\n");
            return false;
        //  if players soft value is 8 or less  
        }else if(playerSoftValue <= 8){
            System.out.println("HIT\n");
            return true;
        }
        System.out.println("BUST\n");
        return false;
    }

}
