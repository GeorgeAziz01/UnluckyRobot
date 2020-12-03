/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author George Aziz
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int totalScore = 300;
        int itrCount = 0;
        char direction = ' ';
        int reward;
        int y = 0;
        int x = 0;
        x = moveHorizontal(x, direction);
        y = moveVertical(y, direction);
        
        while (!isGameOver(x, y, totalScore, itrCount)){
            displayInfo(x, y, itrCount, totalScore);
            direction = inputDirection();
            x = moveHorizontal(x, direction);
            y = moveVertical(y, direction);
            if (doesExceed(x, y, direction)){
                x = x + horizontalExceed(x, direction);
                y = y + verticalExceed(y, direction);
                totalScore = totalScore - 2000;
                System.out.println("Exceed boundary, -2000 damage applied");
            }
            reward = punishOrMercy(direction, reward());
            totalScore = totalScore + reward + payementRules(direction);
            System.out.println("");
            ++itrCount;
        }
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter a name (only two words): ");
        String str = console.nextLine();
        evaluation(totalScore, str);
    }
    /**
     * Prints a message in the console reporting the current information regarding 
     * the game 
     * @param x the current horizontal position
     * @param y the current vertical position
     * @param itrCount the current number of iteration made so far
     * @param totalScore  the total score of the player so far
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){
        System.out.printf("%s(%s%d, %s%d) %s: %d %s: %d\n", "For point", "X=", x, 
                "Y=", y, "at iteration", itrCount, "the " + "total score is", 
                totalScore);
    }
    /**
     * In case the user do not input a good letter for the direction, it will keep 
     * asks to enter a new char until he enters a valid one
     * @return the char that determines the direction
     */
    public static char inputDirection(){
        Scanner console = new Scanner(System.in);
        char direction;
        
        do{
           System.out.print("Please enter a valid direction: ");
           direction = console.next().charAt(0);
        } while (!isDirectionValid(direction));
        
        return direction;
    }
    /**
     * checks if the direction is valid or not
     * @param direction the char that determines the direction
     * @return a boolean value whether if the char is valid or not 
     */
    public static boolean isDirectionValid(char direction) {
        String validDirection = "udlr";
        return validDirection.contains("" + direction);
    }
    /**
     * It tells the vertical position of the robot based on the direction 
     * @param y the current vertical position
     * @param direction the char that will change the positioning
     * @return the vertical position of the robot
     */
    public static int moveVertical(int y,char direction){
        switch (direction) {
            case 'd':
                return --y; 
            case 'u':
                return ++y; 
            default:
                return y;
        }
    }
    /**
     * It tells the horizontal position of the robot based on the direction
     * @param x the current horizontal position
     * @param direction the char that will change the positioning
     * @return the horizontal position of the robot 
     */
    public static int moveHorizontal(int x, char direction) {
        switch (direction) {
            case 'r':
                return ++x;
            case 'l':   
                return --x;
            default:
                return x;
        }
    }
    /**
     * Checks if the game is over or not 
     * @param x the horizontal position
     * @param y the vertical position
     * @param totalScore the player's total score
     * @param itrCount the number of iteration so far 
     * @return a boolean value if whether the game is over or not 
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        if (itrCount > 20) 
            return true;
        else if (totalScore < -1000 || totalScore > 2000) 
            return true;
        else if (x == 4 && y == 4 || x == 4 && y == 0)
            return true;
        else 
            return false;
    }
    /**
     * It rewards the user either add points or subtract points 
     * @return the value of reward 
     */
    public static int reward(){
        Random rand = new Random();
        int randomNum = rand.nextInt(6) + 1;
        
        switch (randomNum) {
            case 1:
                System.out.println("Dice: 1, reward: -100");
                return -100;
            case 2:
                System.out.println("Dice: 2, reward: -200");
                return -200;
            case 3:
                System.out.println("Dice: 3, reward: -300");
                return -300;
            case 4:
                System.out.println("Dice: 4, reward: 300");
                return 300;
            case 5:
                System.out.println("Dice: 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice: 6, reward: 600");
                return 600;
        }
    }
    /**
     * Brings a string to title case 
     * @param str the string inputed by the user 
     * @return the string in title case
     */
    public static String toTitleCase(String str){
        str = str.toLowerCase();
        char firstCapLetter = str.charAt(0);
        firstCapLetter = Character.toUpperCase(firstCapLetter);
        String restWord1 = str.substring(1, str.indexOf(' '));
        char secondCapLetter = str.charAt(str.indexOf(' ') + 1);
        secondCapLetter = Character.toUpperCase(secondCapLetter);
        String restWord2 = str.substring(str.indexOf(' ') + 2, str.length());
        
        return firstCapLetter + restWord1 + " " + secondCapLetter + restWord2;
    }
    /**
     * It will tell if the robot exceeded or not 
     * @param x the horizontal place 
     * @param y the vertical place
     * @param direction the direction 
     * @return a boolean value if whether the robot exceeded the limit or not
     */
    public static boolean doesExceed(int x, int y, char direction){
        if (x > 4 && direction == 'r') 
            return true;
        else if (x < 0 && direction == 'l') 
            return true;
        else if (y < 0 && direction == 'd')
            return true;
        else if (y > 4 && direction == 'u')
            return true;
        else 
            return false;
    }
    /**
     * When the user exceed the grid it returns the same horizontal position as 
     * the last iteration
     * @param x the horizontal position
     * @param direction the direction
     * @return the horizontal position of the last iteration when the robot exceeds 
     * the grid
     */
    public static int horizontalExceed(int x, char direction){
        if (x > 4 && direction == 'r') 
            return -1;
        else if (x < 0 && direction == 'l') 
            return 1;
        else 
            return 0;
    }
    /**
     * When the user exceed the grid it returns the same horizontal position as 
     * the last iteration
     * @param y the vertical position
     * @param direction the direction
     * @return the vertical position of the last iteration when the robot exceeds
     * the grid 
     */
    public static int verticalExceed(int y, char direction){
        if (y > 4 && direction == 'u') 
            return -1;
        else if (y < 0 && direction == 'd') 
            return 1;
        else 
            return 0;
    }
    /**
     * It prints a statement based on the value of the total score 
     * @param totalScore the total score of the user
     * @param str the name of the user 
     */
    public static void evaluation(int totalScore, String str){
        if (totalScore >= 2000)
            System.out.printf("%s, %s, %s %d\n", "Victory", toTitleCase(str), 
                    "your score is", totalScore);
        else
            System.out.printf("%s, %s, %s %d\n", "Mission failed", toTitleCase(str), 
                    "your score is", totalScore);
    }
    /**
     * It determines the amount that the user will lose based on where he moves
     * @param direction the direction 
     * @return the amount that the user lost for each movement 
     */
    public static int payementRules(char direction){
        switch (direction) {
            case 'r':
            case 'l':
            case 'd':
                return -50;
            default:
                return -10;
        }
    }
    /**
     * Based on the reward and the direction the game will randomly get rid of 
     * the reward or not 
     * @param direction the direction 
     * @param reward the reward
     * @return an integer which will be the value of zero or the initial reward
     */
    public static int punishOrMercy(char direction, int reward){
        Random rand = new Random();
        int randomNum = rand.nextInt(2);

        if (reward < 0){
            if (direction == 'u'){
                if (randomNum == 0){
                    System.out.println("Coin: tail | Mercy, the negative reward "
                            + "is removed");
                    return 0;
                }    
                else{
                    System.out.println("Coin: head | No mercy, the negative "
                            + "reward is applied");
                    return reward;
                }
            }
        }
            return reward;     
    }
}