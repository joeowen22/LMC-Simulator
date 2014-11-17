package LMCSimulator;

import java.util.Scanner;

/**
 * LMC Simulator - Takes the input of a LMC program and runs it real-time.
 * Copyright (C) 2014 Joseph Owen
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
public class LMC {

    // This is the array which will store the instructions
    private int[] mailboxes;
    // Counter will act as the instruction counter for the LMC
    private int counter;
    // calculatorDisplay will hold the value that is currently in the
    // calculator's display
    private int calculatorDisplay;
    // stepsTaken stores an array of mailboxes that have been used when running
    // the program
    private int[] stepsTaken;
    // lmcRunCounter stores the number of steps taken when running the LMC
    // program.
    private int lmcRunCounter;

    public LMC(String program) {
        // Set mailbox to store up 100 instructions
        mailboxes = new int[100];
        // Set the lmc counter to 0.
        counter = 0;
        // Sets the calculator display to zero.
        calculatorDisplay = 0;
        // sets the run counter to 0.
        lmcRunCounter = 0;
        // sets the max steps taken to 100, although it could do more steps.
        stepsTaken = new int[100];
        // Parse the program so it is readble.
        parseInstructions(program);
    }

    /*
     * This method will take a String input, which will comprise of instructions
     * for the LMC program where each instruction is separated by a ",". This
     * will then convert each instruction value into an integer.
     */
    private void parseInstructions(String program) {
        // strArr will hold the string values of the instructions that have
        // been separated.
        String[] strArr = program.split(",", 100);
        // The for loop will loop through the strArr and convert the instruction
        // from String to Integer and assign it to the relevant mailbox.
        for (int i = 0; i < strArr.length; i++) {
            // Assigns the instruction to relevant mailbox once its converted to
            // an integer.
            mailboxes[i] = Integer.parseInt(strArr[i]);
        }
    }
    /*
    * Run is a fundamental part of the LMC simulator as it will execute the LMC
    * program and will eventually produce results, whic will be outputted later
    * in the simulation.
    */
    public void run() {
        // Creates an infinite loop waiting for the LMC to reset or end.
        do {
            // To collect the instruction from the mailbox we need the first
            // digit. This can be achieved by dividing the value by 100
            // excluding the remainder, which will return one digit and relates
            // to an LMC instruction.
            // Example: instruction 567 will return 5 as 567/100 = 5
            int instruction = mailboxes[counter] / 100;
            // Similar to retrieving the instruction we can retrieve the mailbox
            // part of the instruction. This is done by retrieving the modulus
            // of the instruction divided by 100. This will return a two-digit
            // mailbox. Example: 567 returns 67, so 567 % 100 = 67
            int mailbox = mailboxes[counter] % 100;
            // Inserts the current mailbox into the stepsTaken array
            stepsTaken[lmcRunCounter] = counter;
            // Increments the run counter as another instruction is about to be
            // executed.
            lmcRunCounter++;
            // The switch case use the instruction retrieved earlier to
            // determine which function to perform.
            switch (instruction) {
                case 0:
                    lmcReset();
                    break;
                case 1:
                    lmcAdd(mailbox);
                    break;
                case 2:
                    lmcSubtract(mailbox);
                    break;
                case 3:
                    lmcStore(mailbox);
                    break;
                case 4:
                    lmcAddr(mailbox);
                    break;
                case 5:
                    lmcLoad(mailbox);
                    break;
                case 6:
                    lmcInstr(mailbox);
                    break;
                case 7:
                    lmcBrZ(mailbox);
                    break;
                case 8:
                    lmcBrP(mailbox);
                    break;
                case 9:
                    if (mailbox == 1) {
                        lmcInput();
                    } else if (mailbox == 2) {
                        lmcOutput();
                    }
                    break;
                default:
                    lmcReset();
                    break;
            }

        } while (true);
    }
    // Will reset the LMC and effectively end the program.
    private void lmcReset() {
        System.out.print("Order of execution: ");
        // Outputs the steps that have been taken while running the program.
        for (int i = 0; i < lmcRunCounter; i++) {
            System.out.print(stepsTaken[i] + ", ");
        }
        // Exits the simulation
        System.exit(0);
    }
    
    // Adds the value from mailbox to calculatorDisplay and then sets it to the
    // current calculatorDisplay.
    private void lmcAdd(int mailbox) {
        calculatorDisplay = calculatorDisplay + mailboxes[mailbox];
        // Increments the counter to the next instruction.
        counter++;
    }
    
    // Subtracts the value from mailbox from the calculatorDisplay and the sets
    // it to the current calculatorDisplay/
    private void lmcSubtract(int mailbox) {
        calculatorDisplay = calculatorDisplay - mailboxes[mailbox];
        counter++;
    }

    // Stores the value of the calculatorDisplay into the mailbox provided.
    private void lmcStore(int mailbox) {
        // Sets the mailbox provided to store the calculatorDisplay.
        mailboxes[mailbox] = calculatorDisplay;
        // Resets the calculatorDisplay to 0.
        calculatorDisplay = 0;
        // Increments the counter to the next instruction.
        counter++;
    }

    // Stores the last two digits of the calculatorDisplay to represent a
    // mailbox address, then will store it to the mailbox provided in the
    // function.
    private void lmcAddr(int mailbox) {
        // Gets the last two digits of the calculator display by getting the modulus of 100.
        int value = calculatorDisplay % 100;
        // Assigns te value to the mailbox provided.
        mailboxes[mailbox] = value;
        // Resets the calculator display.
        calculatorDisplay = 0;
        // Increments the counter to the next instruction.
        counter++;
    }
    
    // Loads a value from a mailbox to the calculatorDisplay.
    private void lmcLoad(int mailbox) {
        // Sets the calculator display to the given mailbox value.
        calculatorDisplay = mailboxes[mailbox];
        // Increments the counter to the next instruction.
        counter++;
    }
    // Branches the counter to the mailbox provided, so it will perform the
    // instruction in that mailbox next.
    private void lmcInstr(int mailbox) {
        // Sets the counter to the mailbox provided.
        counter = mailbox;
        // Resets the calculatorDisplay.
        calculatorDisplay = 0;
    }
    
    // If the calculatorDisplay is equal to zero it will branch to the mailbox
    // provided.
    private void lmcBrZ(int mailbox) {
        // Checks whether calculatorDisplay is equal to zero
        if (calculatorDisplay == 0) {
            // If it is equal to zero then it will branch to the mailbox
            // provided.
            counter = mailbox;
        } else {
            // If it is not equal to zero, it increments the counter to the next
            // instruction.
            counter++;
        }
        // Resets the calculatorDisplay.
        calculatorDisplay = 0;
    }

    // If the calculatorDisplay is positive (including zero) it will branch to 
    // the mailbox provided.
    private void lmcBrP(int mailbox) {
        // Checks whether calculatorDisplay is greater than or equal to zero.
        if (calculatorDisplay >= 0) {
            // If the calculatorDisplay is greater than or equal to zero then it
            // will branch to the mailbox provided.
            counter = mailbox;
        } else {
            // If less than 0, increment the counter to the next instruction.
            counter++;
        }
        // Resets the calculatorDisplay.
        calculatorDisplay = 0;
    }

    // Takes an input in the LMC program.
    private void lmcInput() {
        // Initialize a scanner to take in an input.
        Scanner sc = new Scanner(System.in);
        // Hints the user to make an input
        System.out.println("Please enter an integer between 0 and 999:");
        // Collects the input
        String input = sc.nextLine();
        // Sets the value input to the calculatorDisplay and parses it to an
        // integer.
        calculatorDisplay = Integer.parseInt(input);
        // Increment the counter to the next instruction.
        counter++;
    }

    // Outputs the current calculatorDisplay.
    private void lmcOutput() {
        // Prints the output.
        System.out.println("Output: " + calculatorDisplay);
        // Resets the calculatorDisplay.
        calculatorDisplay = 0;
        // Increment the counter to the next instruction.
        counter++;
    }
}
