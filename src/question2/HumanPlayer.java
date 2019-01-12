/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author 100116544
 */
public class HumanPlayer extends BasicPlayer implements Serializable {
    private final String NAME = "Human Player";
    
    /**
     * Get the players name
     * @return Name as string
     */
    @Override
    public String getName(){
        return NAME;
    }
    
    /**
     * A console based input for bet to be made
     * @return The bet made by the user
     */
    @Override
    public int makeBet(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please make a bet: ");
        int inputBet = scan.nextInt();
        setBet(inputBet);
        return inputBet;
    }
    
    /**
     * Hit based on user input
     * @return True if player wishes to hit, false if not
     */
    @Override
    public boolean hit(){
        Scanner scan = new Scanner(System.in);
        String choice;
        do{
            // if bust do not allow user to hit
            if(isBust()){
                System.out.println("BUST\n");
                return false;
            }else {
                System.out.print("Would you like to hit(h) or stick(s)?: ");
                choice = scan.nextLine();
                System.out.println("");
                switch(choice){
                    case "h":
                        return true;
                    case "s":
                        return false;
                    default:
                        System.out.println("Please either chose h or s.");
                        break;
                }
            }
        // validate to make sure they enter h or s
        }while (!choice.equalsIgnoreCase("h") && !choice.equalsIgnoreCase("s"));
        System.out.println("No choice made.\n STICK");
        return false;
    }
    
    
}
