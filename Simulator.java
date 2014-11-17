package LMCSimulator;

import java.util.Scanner;

/**
 * LMC Simulator - Takes the input of a LMC program and runs it real-time.
 * Copyright (C) 2014 Joseph Owen
 *
This program is free software; you can redistribute it and/or modify it under
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

// This is an example of how to use the LMC class to create the simulation.
public class Simulator {
    public static void main(String args[]){
        // Initiates the scanner so we are able to obtain input.
        Scanner sc = new Scanner(System.in);
        // Hints the user to input the LMC program.
        System.out.println("Please enter your LMC program with each instruction separated by ',' (no spaces): ");
        // Gets the input for the user.
        String program = sc.nextLine();
        // Creates a new LMC program.
        LMC lmc = new LMC(program);
        // Tells the user that the program is going to run.
        System.out.println("LMC is about to run...");
        // Runs the LMC program.
        lmc.run();
    }
}
