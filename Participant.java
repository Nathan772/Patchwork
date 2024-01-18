package fr.umlv.participants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import fr.umlv.display.Display;
import fr.umlv.items.Bonus;
import fr.umlv.items.Cloth;
import fr.umlv.tools.Point;
/**
 * A general interface for every kind of players.
 * 
 * @author natew
 *
 */
public interface Participant {
	
	/**
	 * nom field getter.
	 * @return String
	 * the name of the player
	 */
	default String name() {
		return this.name();
	}
	
	/**
	 * It's a getter that return a player's age
	 * 
	 * @return int
	 * 
	 * the player age
	 */
		default int age() {
		return age();
	}
		
		/**
		 * 
		 * This method decrease the player time and increase their button in exchange.
		 * @param ajout
		 * The parameter is the quantity of button which is add.
		 * It also represents the quantity of time which is lost.
		 * 
		 */
		public void addButtonTimeLoss(int ajout);
		
		/**
		 * 
		 * This method displays the player informations.
		 */
		public void displayInfo();
		
		/**
		 * This method increase the quantity of ressource for the player by "ressources" new ressources.
		 * 
		 * @param ressources
		 * the quantity of ressource that the player earn.
		 * 
		 * 
		 * 
		 */
		public void updateRessource(int ressources);
		
		/**
		 * 
		 * This method decrease the player time.
		 * @param perte
		 * It represents the quantity of time which is lost.
		 * 
		 */
		public void timeLoss(int perte) ;
		
		/**
		 * This method take off "cout" bouton to the player because they paid this sum to buy a new cloth.
		 * @param cout
		 * The cloth's price.
		 */
		
		public void buttonLoss(int cout);
		
		/**
		 * 
		 * This method enables to add a cloth to the board.
		 * @param tissu
		 * the cloth that the player wants to add to his board
		 * 
		 * @param display
		 * a variable that handle the displaying.
		 * 
		 * @param scan
		 * a scanner that retrieves user's input.
		 * 
		 * @return 
		 * 1 in order to say that everything has happened correctly
		 * 0 in order to say the user has failed to put the new cloth on their board
		 * 
		 */
		public int addClothBoard(Cloth tissu, Scanner scan, Display display);
		
		
		
		/**
		 * 
		 * This method create a string which is the representation
		 * of the cloth on the player on the player board.
		 * @param t1
		 * The cloth that we want to display
		 * 
		 * @return
		 * a string which is the graphic representation of the cloth on 
		 * the player board.
		 * 
		 */
		public String visualClothOnBoard(Cloth t1);
		/**
		 * 
		 * @param t1
		 * the cloth for which the method tests if its position if validate.
		 * @return
		 * a boolean which indicates with true if the position is acceptable and false if no.
		 */
		public boolean validPositioning(Cloth t1);
		
		/**
		 * This method indicates either a player can pay a cloth or not
		 * @param t1
		 * @return
		 * true if they cannot
		 * false if they cannot
		 */
		public boolean canPay(Cloth t1);
		
		/**
		 * 
		 * @param t1
		 * the cloth that the player want to set up.
		 */
		public void updateBoard(Cloth t1);
		
		/**
		 * It's a getter that return the Pawn number's associated with the player.
		 * 
		 * @return int
		 * 
		 * the player's pawn number
		 */
		public int nbPawn();
		
		/**
		 *
		 *This method updates the player's score.
		 *
		 */
		public void computeScore();
		
		/**
		 * It's a getter that return a player's score
		 * 
		 * @return int
		 * 
		 * the player score
		 */
		public long score();
		/**
		 * Do a deep copy of a player
		 * @return
		 * the player copied
		 */
		public Object clone();
		
		/**
		 * This method increase the quantity of button that a player has.
		 * @param gain
		 * The quantity of button the player gain.
		 */
		public void increaseButton(int gain);
		
		/**
		 * give quantity of ressource that the player have.
		 * @return
		 * give the gamer's ressource.
		 */
		public int ressource();
		
		
		/**
		 * It's a getter that return the player's board
		 * 
		 * @return the player's board
		 * 
		 */
		
		public LinkedHashMap<Point,Integer> playerBoard();
		
		/**
		 * a method that gives to the player a bonus card.
		 */
		public void WinBonusCard();
		
		/**
		 * It's a getter that return a player's number of button
		 * 
		 * @return int
		 * 
		 * the player number of button.
		 */
		public int button();
		
		/**
		 * It's a getter that return the player's leftover time.
		 * 
		 * @return int
		 * 
		 * the player's leftover time
		 */
		public int time();
		

		

}
