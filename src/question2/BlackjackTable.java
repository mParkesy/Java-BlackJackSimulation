/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

/**
 *
 * @author 100116544
 */
public class BlackjackTable implements Serializable {
    static final long serialVersionUID = 222;
    private List<Player> players;
    private Dealer dealer;
    private int numHands;
    
    public static final int MAX_PLAYERS = 8;
    public static final int MIN_BET = 1;
    public static final int MAX_BET = 500;
    
    /**
     * Constructor for BlackjackTable which takes a list of players
     * and a number of hands a parameters and creates a dealer
     * @param list The list of players for this table
     * @param hands The number of hands to be played on this table
     */
    public BlackjackTable(List<Player> list, int hands){
        this.players = list;
        this.dealer = new BlackjackDealer();
        this.numHands = hands;
    }
    
    /**
     * An empty constructor for BlackjackTable that creates an empty
     * player list and a dealer
     */
    public BlackjackTable(){
        this.players = new ArrayList<>();
        this.dealer = new BlackjackDealer();
    }
    
    /**
     * If a new list of players need to be added to the tables player list
     * @param p The list of players to be added
     */
    public void add(List<Player> p){
        players.addAll(p);
    }
    
    /**
     * Get the current list of players on the table
     * @return The list of players
     */
    public List<Player> getPlayerList(){
        return players;
    }
    
    /**
     * Get the current tables dealer
     * @return The dealer
     */
    public Dealer getDealer(){
        return dealer;
    }
    
    /**
     * Get the number of hands to be played on this table
     * @return The number of hands as an integer
     */
    public int getNumberHands(){
        return numHands;
    }
       
    /**
     * Saves the table passed as a parameter so that it can be 
     * loaded at a different stage
     * @param table The table which will be saved
     */
    static void saveGame(BlackjackTable table){
        try {
            FileOutputStream file = new FileOutputStream("table.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            // write table to file
            out.writeObject(table);
            out.close();
            file.close();
            System.out.println("Game saved!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Loads a saved BlackjackTable from a file
     * @return The loaded table
     */
    static BlackjackTable loadGame(){
        BlackjackTable newTable = new BlackjackTable();
        try {    
            FileInputStream fis = new FileInputStream("table.ser");
            ObjectInputStream o = new ObjectInputStream(fis);
            // gets the table from file
            newTable = (BlackjackTable) o.readObject();
            fis.close();
            o.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        // if the table is empty do not allow it to be played
        if(newTable.getPlayerList().isEmpty()){
            System.out.println("\nEither no game exists or the game you loaded "
                    + "has no remaining players.\n");
            menu();
        }
        return newTable;
    }
    
    /**
     * Asks the user how many hands they would like to be played
     * @return The number entered by the user
     */
    static int numberHands(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of hands you would "
                         + "like to play: ");
        return scan.nextInt();        
    }
    
    /**
     * Validation and case statement for the menu which is outputted to 
     * the console when the program is run
     */
    static void start(){
        Scanner scan = new Scanner(System.in);
        boolean input = false;
        int choice;
        while(!input){
            if(scan.hasNextInt()){
                choice = scan.nextInt();

                switch(choice){
                    case 1:
                        basicGame();
                        input = true;
                        break;
                    case 2:
                        intermediateGame();
                        input = true;
                        break;
                    case 3:
                        humanGame();
                        input = true;
                        break;
                    case 4:
                        advancedGame();
                        input = true;
                        break;
                    case 5:
                        loadedGame(loadGame());
                        input = true;
                        break;
                    default:
                        System.out.println("Invalid menu choice");
                        input = true;
                        break;
                }
            }else{
                System.out.println("Invalid menu choice");
                input = true;
            }
        }
        menu();
    }
    
    /**
     * A menu to be displayed when the program is run
     */
    static void menu(){
        System.out.println("=======================================");
        System.out.println("                Welcome                ");
        System.out.println("=======================================");
        System.out.println("1. Basic Game");
        System.out.println("2. Intermediate Game");
        System.out.println("3. Human Player Game");
        System.out.println("4. With all three players");
        System.out.println("5. Load last saved game");
        System.out.println("=======================================");
        System.out.println("");
        System.out.print("Please chose an option: ");     
        start();
    }
    
    /**
     * Create number of basic players to be used for a game
     * @param n The number of players to create
     * @return The list of players
     */
    public static List<Player> getBasicPlayers(int n){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < n; i++){
            players.add(new BasicPlayer());
        }   
        return players;
    }
    
    /**
     * A game with 4 basic players
     */
    static void basicGame(){
        Scanner scan = new Scanner(System.in);
        // get players
        List<Player> basicPlayers = getBasicPlayers(4);
        int numHands = numberHands();
        int dealerValue;
        int numSteps;
        String choice;         
        int handTotal;
        
        System.out.print("How many hands would you like to step over: ");
        numSteps = scan.nextInt(); 
        while(numSteps > numHands){
        System.out.print("Too many steps for number of hands selected. "
                + "Please try again: ");
                numSteps = scan.nextInt(); 
        }
        //create new table
        BlackjackTable table = new BlackjackTable(basicPlayers, numHands);
        // pass list of players to dealer
        table.getDealer().assignPlayers(basicPlayers);   
        
        // run all hands
        for(int n = 1; n <= table.getNumberHands(); n++){       
            List<Player> removePlayers = new ArrayList<>();
            System.out.println("");
            System.out.println("=========================");
            System.out.println("New Hand - Number " + n);
            System.out.println("=========================");
            
            // get bets from players and deal first cards
            table.getDealer().takeBets();
            table.getDealer().dealFirstCards();
                      
            // for all plays do the following
            for(Player p: table.getPlayerList()){
                
                System.out.println("\n=======================");
                System.out.println(p.getName() +
                        "'s turn");
                System.out.println("=======================");
                int playerBalance = p.getBalance();
                // if player does not have enough funds
                if(playerBalance <= 0){
                    System.out.println("Player does not have enough funds"
                            + " to continue.");
                    System.out.println("Player will be removed...");
                    System.out.println("");
                    removePlayers.add(p);
                } else {
                    System.out.println("Balance: " + playerBalance);
                    System.out.println("Current bet: " + p.getBet());
                    System.out.println("\nPlayers current hand: ");
                    System.out.println(p.getHand().toString());
                    handTotal = table.getDealer().play(p);
                    System.out.println("Player Hand total is: " + handTotal);
                    
                }
            }
            /**
             * if a player has run out of funds then they would have been
             * added to this list, they will then be removed from the table list
            */
            if(removePlayers.size() > 0){
                // for all removed players
                for(Player p : removePlayers){
                    // if they are in table list
                    if(table.getPlayerList().contains(p)){
                        // remove the player
                       table.getPlayerList().remove(p);
                    }
                }
                // re assign players to table
                table.getDealer().assignPlayers(table.getPlayerList());
            }
            
            // if table player list is not empty
            if(!table.getPlayerList().isEmpty()){
                // output dealers details
                dealerValue = table.getDealer().playDealer();
                System.out.println("Dealer Hand total is: " + dealerValue);
                System.out.println("");
                // settle all bets for remaining players
                table.getDealer().settleBets();                
            }
            // if reached step user entered
            if(n == numSteps){   
                // validate to make sure y or n inputted
                do{
                    System.out.print("\nDo you wish to continue? "
                            + "(y)es, (n)o: ");
                    choice = scan.next();
                    switch (choice) {
                        case "n":
                            // validation to ask for l or s
                            String loadOrSave;
                            do{
                                System.out.print("Do you want to (s)ave this"
                                        + " game or (l)oad) a game?: ");
                                loadOrSave = scan.next();
                            } while (!loadOrSave.equalsIgnoreCase("s") 
                                    && !loadOrSave.equalsIgnoreCase("l"));
                            if(loadOrSave.equals("s")){
                                saveGame(table);
                                menu();
                                break;
                            } else{
                                table = loadGame();
                                break;
                            }
                        case "y":
                            // if all hands have been played
                            if(numSteps == table.getNumberHands()){
                                System.out.println("Hands have finised.\n");
                                menu();
                            }else{
                                int x;
                                // if more hands are to be played
                                System.out.print("How many hands would you "
                                        + "like to step over: ");
                                x = scan.nextInt();
                                /**
                                 * make sure user inputs correct number of hands
                                 * that are still to be played
                                */
                                while(x + numSteps > table.getNumberHands()){ 
                                    System.out.print("The amount you entered "
                                            + "is over the amount of hands "
                                            + "still to step over. "
                                            + "Please try again: ");
                                    x = scan.nextInt();
                                }
                                numSteps += x;
                                break;
                            }
                        default:
                            System.out.println("Please either chose "
                                    + "y or n");
                            break;
                    }
                }while(!choice.equalsIgnoreCase("n") 
                        && !choice.equalsIgnoreCase("y"));
                // if list is empty
            }else if(basicPlayers.isEmpty()){
                System.out.println("All players have run out of money.");
                do {
                    System.out.print("\nDo you wish to add four new players "
                            + "and continue? (y)es, (n)o: ");
                    choice = scan.next();
                    switch (choice) {
                        case "n":                            
                            menu();
                            break;
                        case "y":
                            // create 4 new players and continue hands
                            System.out.println("4 new players created.");
                            table.getPlayerList().add
                                            ((Player) getBasicPlayers(4));
                            table.getDealer().assignPlayers
                                            (table.getPlayerList());
                            break;
                        default:
                            System.out.println("Please either chose y or n");
                            break;
                    }
                // validate for y or n to be inputted
                } while (!choice.equalsIgnoreCase("n") && 
                        !choice.equalsIgnoreCase("y"));
            }
        }
    }
    
    /**
     * Create a list of intermediate players
     * @param n The number of players to be created
     * @return The list of players
     */
    public static List<Player> getIntermediatePlayers(int n){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < n; i++){
            players.add(new IntermediatePlayer());
        }   
        return players;
    }
    
    /**
     * A game with 4 intermediate players
     * Comments from basic player apply to the below
     */
    static void intermediateGame(){
        Scanner scan = new Scanner(System.in);
        
        List<Player> intermediatePlayers = getIntermediatePlayers(4);
        int numHands = numberHands();
        int dealerValue;
        int numSteps;
        String choice;         
        int handTotal;
      
        System.out.print("How many hands would you like to step over: ");
        numSteps = scan.nextInt(); 
        while(numSteps > numHands){
        System.out.print("Too many steps for number of hands selected. "
                + "Please try again: ");
                numSteps = scan.nextInt(); 
        }
        BlackjackTable table = 
                            new BlackjackTable(intermediatePlayers, numHands);
        table.getDealer().assignPlayers(table.getPlayerList());
        
        for(int n = 1; n <= table.getNumberHands(); n++){       
            List<Player> removePlayers = new ArrayList<>();
            System.out.println("");
            System.out.println("=========================");
            System.out.println("New Hand - Number " + n);
            System.out.println("=========================");
            
            table.getDealer().takeBets();
            table.getDealer().dealFirstCards();
                      
            for(Player p: table.getPlayerList()){
                System.out.println("\n=======================");
                System.out.println(p.getName() +
                        "'s turn");
                System.out.println("=======================");
                int playerBalance = p.getBalance();
                if(playerBalance <= 0){
                    System.out.println("Player does not have enough funds"
                            + " to continue.");
                    System.out.println("Player will be removed...");
                    System.out.println("");
                    removePlayers.add(p);
                } else {
                    System.out.println("Balance: " + playerBalance);
                    System.out.println("Current bet: " + p.getBet());
                    System.out.println("\nPlayers current hand: ");
                    System.out.println(p.getHand().toString());
                    handTotal = table.getDealer().play(p);
                    System.out.println("Player Hand total is: " + handTotal);
                }
            }
            if(removePlayers.size() > 0){
                for(Player p : removePlayers){
                    if(table.getPlayerList().contains(p)){
                       table.getPlayerList().remove(p);
                    }
                }
                table.getDealer().assignPlayers(table.getPlayerList());
            }
            
            if(!table.getPlayerList().isEmpty()){
                dealerValue = table.getDealer().playDealer();
                System.out.println("Dealer Hand total is: " + dealerValue);
                System.out.println("");
                table.getDealer().settleBets();                
            }
            
            if(n == numSteps){   
                do{
                    System.out.print("\nDo you wish to continue? "
                            + "(y)es, (n)o: ");
                    choice = scan.next();
                    switch (choice) {
                        case "n":
                            String loadOrSave;
                            do{
                                System.out.print("Do you want to (s)ave this"
                                        + " game or (l)oad) a game?: ");
                                loadOrSave = scan.next();
                            } while (!loadOrSave.equalsIgnoreCase("s") 
                                    && !loadOrSave.equalsIgnoreCase("l"));
                            if(loadOrSave.equals("s")){
                                saveGame(table);
                                menu();
                                break;
                            } else{
                                table = loadGame();
                                break;
                            }
                        case "y":
                            if(numSteps == table.getNumberHands()){
                                System.out.println("Hands have finised.\n");
                                menu();
                                
                            }else{
                                int x;
                                System.out.print("How many hands would you "
                                        + "like to step over: ");
                                x = scan.nextInt();
                                while(x + numSteps > table.getNumberHands()){ 
                                    System.out.print("The amount you entered "
                                            + "is over the amount of hands "
                                            + "still to step over. "
                                            + "Please try again: ");
                                    x = scan.nextInt();
                                }
                                numSteps += x;
                                break;
                            }
                        default:
                            System.out.println("Please either chose "
                                    + "y or n");
                            break;
                    }
                }while (!choice.equalsIgnoreCase("y") &&
                        !choice.equalsIgnoreCase("n"));
            }else if(intermediatePlayers.isEmpty()){
                System.out.println("All players have run out of money.");
                do {
                    System.out.print("Do you wish to add four new players "
                            + "and continue? (y)es, (n)o: ");
                    choice = scan.next();
                    switch (choice) {
                        case "n":
                            menu();
                            break;
                        case "y":
                            System.out.println("4 new players created.");
                            table.getPlayerList().add
                                    ((Player) getIntermediatePlayers(4));
                            table.getDealer().assignPlayers
                                    (table.getPlayerList());
                            break;
                        default:
                            System.out.println("Please either chose y or n");
                            break;
                    }
                } while (!choice.equalsIgnoreCase("n") &&
                        !choice.equalsIgnoreCase("y"));
            }
        }
    }
    
    /**
     * A human game with user input from console along with a basic player
    */
    static void humanGame(){        
        Scanner scan = new Scanner(System.in);
        List<Player> players = new ArrayList<>();
        Player human = new HumanPlayer();
        Player basic = new BasicPlayer();
        players.add(human);
        players.add(basic);
        
        int dealerValue;
        String choice;         
        int handTotal;
        int numHands = 0;
        boolean play = true;
        
        BlackjackTable table = new BlackjackTable(players, numHands);
        table.getDealer().assignPlayers(table.getPlayerList());
        
        // while player has funds and they wish to play
        while(human.getBalance() > 0 && play){
            numHands++;
            List<Player> removePlayers = new ArrayList<>();
            System.out.println("");
            System.out.println("=========================");
            System.out.println("New Hand - Number " + numHands);
            System.out.println("=========================");
            
            table.getDealer().takeBets();
            table.getDealer().dealFirstCards();
            
            for(Player p: table.getPlayerList()){
                // check player has bet correct amount
                while(p.getBet() > MAX_BET || p.getBet() < MIN_BET){
                    System.out.print("Your bet was either under 1 or over 500."
                            + " Please bet again.\n");
                    p.makeBet();
                }
                System.out.println("\n=======================");
                System.out.println(p.getName() +
                        "'s turn");
                System.out.println("=======================");
                int playerBalance = p.getBalance();
                if(playerBalance <= 0){
                    System.out.println("Player does not have enough funds"
                            + " to continue.");
                    System.out.println("Player will be removed...");
                    System.out.println("");
                    removePlayers.add(p);
                } else {
                    System.out.println("Balance: " + playerBalance);
                    System.out.println("Current bet: " + p.getBet());
                    System.out.println("\nCurrent hand: ");
                    System.out.println(p.getHand().toString());
                    handTotal = table.getDealer().play(p);
                    System.out.println("Hand total is: " + handTotal);
                }
            }
            if(removePlayers.size() > 0){
                for(Player p : removePlayers){
                    if(table.getPlayerList().contains(p)){
                       table.getPlayerList().remove(p);
                    }
                }
                table.getDealer().assignPlayers(table.getPlayerList());
            }
            
            if(!table.getPlayerList().isEmpty()){
                dealerValue = table.getDealer().playDealer();
                System.out.println("Dealer Hand total is: " + dealerValue);
                System.out.println("");
                table.getDealer().settleBets();                
            }
                          
            do{
                System.out.print("\nDo you wish to continue? "
                        + "(y)es, (n)o: ");
                choice = scan.next();
                switch (choice) {
                    case "n":
                        play = false;
                        break;
                    case "y":
                        play = true;
                        break;
                    default:
                        System.out.println("Please either chose "
                                + "y or n");
                        break;
                }
            }while(!choice.equalsIgnoreCase("n") &&
                    !choice.equalsIgnoreCase("y"));
            if(table.getPlayerList().isEmpty()){
                System.out.println("All players have run out of money.");
                System.out.println("Game Over!\n");
                menu();
            }
        }
    }
    
    /**
     * A game with one of each AI player
     */
    static void advancedGame(){
        List<Player> players = new ArrayList<>();
        int dealerValue;
        int numHands = numberHands();        
        players.add(new BasicPlayer());
        players.add(new IntermediatePlayer());
        players.add(new AdvancedPlayer());
        
        BlackjackTable table = new BlackjackTable(players, numHands);
        table.getDealer().assignPlayers(table.getPlayerList());
        for(int n = 1; n <= table.getNumberHands(); n++){
            List<Player> removePlayers = new ArrayList<>();
            System.out.println("");
            System.out.println("=========================");
            System.out.println("New Hand - Number " + n);
            System.out.println("=========================");
            
            table.getDealer().takeBets();
            table.getDealer().dealFirstCards();
                      
            for(Player p: table.getPlayerList()){
                System.out.println("\n=======================");
                System.out.println(p.getName() +
                        "'s turn");
                System.out.println("=======================");
                int playerBalance = p.getBalance();
                if(playerBalance <= 0){
                    System.out.println("Player does not have enough funds"
                            + " to continue.");
                    System.out.println("Player will be removed...");
                    System.out.println("");
                    removePlayers.add(p);
                } else {
                    System.out.println("Balance: " + playerBalance);
                    System.out.println("Current bet: " + p.getBet());
                    System.out.println("\nPlayers current hand: ");
                    System.out.println(p.getHand().toString());
                    int handTotal = table.getDealer().play(p);
                    System.out.println("Player Hand total is: " + handTotal);
                }
            }
            if(removePlayers.size() > 0){
                for(Player p : removePlayers){
                    if(table.getPlayerList().contains(p)){
                        table.getPlayerList().remove(p);
                    }
                }
                table.getDealer().assignPlayers(table.getPlayerList());
            }
            
            if(table.getPlayerList().isEmpty()){
                System.out.println("All players have run out of money.\n");    
                menu();
            }else{
                dealerValue = table.getDealer().playDealer();
                System.out.println("Dealer Hand total is: " + dealerValue);
                System.out.println("");
                table.getDealer().settleBets(); 
            }
        }  
    }
    
    /**
     * A game based on a loaded table
     * @param table The table that was loaded
     */
    static void loadedGame(BlackjackTable table){
        int dealerValue;       
        int handTotal;
        System.out.println("=========================");        
        System.out.println("       Game Loaded       ");
        System.out.println(" Playing remaining hands ");
        System.out.println("=========================");
        table.getDealer().assignPlayers(table.getPlayerList());   
        
        for(int n = 1; n <= table.getNumberHands(); n++){       
            List<Player> removePlayers = new ArrayList<>();
            System.out.println("");
            System.out.println("=========================");
            System.out.println("New Hand - Number " + n);
            System.out.println("=========================");
            
            table.getDealer().takeBets();
            table.getDealer().dealFirstCards();
                      
            for(Player p: table.getPlayerList()){
                
                System.out.println("\n=======================");
                System.out.println(p.getName() +
                        "'s turn");
                System.out.println("=======================");
                int playerBalance = p.getBalance();
                if(playerBalance <= 0){
                    System.out.println("Player does not have enough funds"
                            + " to continue.");
                    System.out.println("Player will be removed...");
                    System.out.println("");
                    removePlayers.add(p);
                } else {
                    System.out.println("Balance: " + playerBalance);
                    System.out.println("Current bet: " + p.getBet());
                    System.out.println("\nPlayers current hand: ");
                    System.out.println(p.getHand().toString());
                    handTotal = table.getDealer().play(p);
                    System.out.println("Player Hand total is: " + handTotal);
                    
                }
            }
            if(removePlayers.size() > 0){
                for(Player p : removePlayers){
                    if(table.getPlayerList().contains(p)){
                        table.getPlayerList().remove(p);
                    }
                }
                table.getDealer().assignPlayers(table.getPlayerList());
            }
            
            if(table.getPlayerList().isEmpty()){
                System.out.println("All players have run out of money.\n");    
                menu();
            }else{
                dealerValue = table.getDealer().playDealer();
                System.out.println("Dealer Hand total is: " + dealerValue);
                System.out.println("");
                table.getDealer().settleBets(); 
            }
        }        
    }
    
    public static void main(String[] args) {
        menu();
    }
}
