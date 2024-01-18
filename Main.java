package fr.umlv.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import fr.umlv.board.GameBoard;
import fr.umlv.display.GraphicDisplay;
import fr.umlv.input.Input;
import fr.umlv.items.Bonus;
import fr.umlv.items.Cloth;
import fr.umlv.modes.ModeI;
import fr.umlv.participants.Player;
import fr.umlv.party.Party;
import fr.umlv.tools.Point;
/**
 * 
 * The class main which is used to launch the game.
 * @author natew
 *
 */
public class Main {
	/**
	 * 
	 * @param args 
	 * the terminal input.
	 * @throws IOException
	 * the exception handled if the player's input is wrong.
	 * 
	 */
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		Party.launchParty(scanner);
	}

}
