package fr.umlv.display;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import fr.umlv.input.Input;
import fr.umlv.items.Cloth;
import fr.umlv.participants.Participant;
import fr.umlv.party.Party;

/**
 * This interface represents all the possibilities of displaying for the game : terminal or Graphic
 * @author natew
 *
 */
public interface Display {
	/**
	 * 
	 * This method displays a message that announces to the player the party has started.
	 * 
	 */

	public void entranceMessage();
	
	/**
	 * 
	 * This method displays a message that enables to know if someone wants a human or an AI.
	 * @param nb
	 * It indicates the number of the player
	 * 
	 */
	default public void playerChoice(int nb) {
		if(nb <= 0)
			throw new IllegalArgumentException("You must give a number for the "
					+ "next player which is 1 or more");
	}
	/**
	 * 
	 * This method displays a message that enable to the player to choose their name.
	 */
	default public void nameMessage() {
		
	}
	/**
	 * 
	 * This method displays a message that enable to the player to choose their age.
	 */
	default public void ageMessage() {
		
	}
	/**
	This function print a message that summarize the player involved in the party.
	@param p1
	The current party state.
	*/
	default public void showParticipant(Party p1) {
		
		Objects.requireNonNull(p1, "The party for which we will show"
				+"the players who are involved cannot be null");
		
	}
	
	/**
	 * 
	 * this method show the leader player information.
	 * @param p1
	 * the current party state
	 */
	public void infoPlayer(Party p1);
	
	
	/**
	 * This method display message in order to indicate to the lead-player which action they can do
	 * @param p1
	 * The party state, in order to know who is the leader.
	 */
	default public void messageTurnBeginning(Party p1){
		Objects.requireNonNull(p1, "The party associated to this turn"
				+ "cannot be null");
		
	}
	
	/**
	 * 
	 * This method display a message in order to confirm the player action to walk.
	 * @param p1
	 * The party state, in order to know who is the leader and the distance between the leader and the other player.
	 *
	 */
	default public void messageWalk(Party p1){
		Objects.requireNonNull(p1, "The party associated to this turn"
				+ "cannot be null");
		
	
	}
	
	/**
	 * 
	 * This method show a message that warn the player that their turn is canceled.
	 * The player will redo an action.
	 */
	
	default public void messageCanceledAction() {
		
	}
	
	/**
	 * 
	 * This method show a message that warn the player their action succeed
	 */
	
	default public void messageActionSucceed() {
		
		
	}
	/**
	 * 
	 * This method display the game shop and illustrate the clothes.
	 * 
	 * @param p1
	 * the game state
	 * 
	 */
	default public void messageWholeShop(Party p1) {
	
	}
	
	/**
	 * 
	 * This method display the game shop and illustrate the clothes.
	 * 
	 * @param p1
	 * the game state 
	 * 
	 * 
	 * 
	 */
	default public void displayShopEntrance(Party p1) {
		
		
	}
	/**
	 * This method enables to enter an integer that matches with a value in the "tab"
	 * @param tab
	 * a tab that contains the value allowed.
	 * @param scanner
	 * a scanner that retrieves user's input.
	 * @return
	 * the value chosen by the user
	 */
	public int inputIntTab(int []tab, Scanner scanner);
	
	/**
	 * This method display a message that enables the player to know how they can move their cloth on the player board
	 */
	default public void messagePawnMovement() {
		
	}
	/**
	 * This method display a message that enables the player to know they are putting a cloth on a place that already
	 * has a cloth.
	 */
	default public void errorMessageOverlappedPiece() {
		
	}
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	default public void validMessageClothPosition() {
		
	}
	
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	default public void expensiveCostMessage() {
		
	}
	/**
	 * A message that explains to the player which game modes they can choose.
	 */
	default public void choiceMessageMode() {
		
	}
	/**
	 * This method explains the meaning of the elements on the board. 
	 * 
	 */
	default public void boardExplanationMessage() {
		
	}
	/**
	 * This method explains display an error message because a cloth is mispositioned.
	 * 
	 */
	default public void errorMessageClothMispositioned() {
		
	}
	
	/**
	 * This method display a message that proposes to the player which game mode they wqant.
	 */
	public void messageChoiceDisplay();
	/**
	 * A message that explains to the player which game modes they can choose.
	 */
	public void messageChoiceMode();
	
	/**
	 * 
	 * This method displays a message that enables to know if someone wants a human or an AI.
	 * @param nb
	 * It indicates the number of the player
	 * 
	 */
	public void choixJoueur(int nb);
	
	/**
	 * 
	 * This method displays a message that enable to the player to choose their name.
	 */
	default public void messagePrenom() {
		System.out.println("Quel est votre prénom ? \n\n");
	}
	
	/**
	 * 
	 * This method displays a message that enable to the player to choose their age.
	 */
	public void messageAge() ;
	
	/**
	 * 
	 * the representation of the game board in a graphic viewpoint.
	 * 
	 * @param p1
	 * the party state
	 */
	 public void showGameBoard(Party p1);
	 
	 /**
		 * 
		 * This method create a podium with the game winner's and display the winner
		 * @param p1
		 * the party state.
		 */
		public void showRanking(Party p1); 
		
		/**
		 * This method enables to enter a integer between an interval of two numbers. the both number are into the interval.
		 * @param x
		 * the integer which represents the beginning of the interval allowed.
		 * @param y
		 * the integer which represents the ending of the interval allowed.
		 * @param messages 
		 * the list of message that are associated to the button.
		 * @param scanner
		 * a scanner that enables to use user input.
		 * @return
		 * It returns an int which is the int chosen by the user.
		 */
		public int intInter(int x, int y, ArrayList<String> messages, Scanner scanner);
		
		/**
		 * This method display a message that enables the player to know how they can move their cloth on the player board
		 */
		public void messageDeplacementPiece();
		
		/**
		 * This method display a message that enables the player to confirm the cloth position
		 */
		public void messageConfirmerPositionnementPiece() ;
		
		/**
		 * This method display the cloth on the player board.
		 * @param t1
		 * a cloth that the method displays
		 */
		public void showClothOnBoard(Cloth t1, Participant p1);
		
		/**
		 * This method display a message that enables the player to know they are putting a cloth on a place that already
		 * has a cloth.
		 */
		public void messageErreurPieceSuperposee();
		
		/**
		 * 
		 * Move the cloth on the right, the left, up, down.
		 * And enable to turn it.
		 * 
		 * @param p1
		 * the player with who is associated the quilt
		 * 
		 * @param scanner
		 * a scanner that retrieves user's input
		 * 
		 * @param t1
		 * the cloth the player want to put on their quilt.
		 * 
		 * @return 
		 * 1 if the player succeed to set up a new cloth.
		 * 0 if the player failed.
		 * 
		 */
		public boolean moveTurnCloth(Cloth t1, Participant p1, Scanner scanner);
		
		/**
		 * this method show a message that precises to the player that they have
		 * to go back to the graphic part
		 */
		public void messageContinueMode();
		
		/**
		 * this method shows a message that explains to the player the AI is not
		 * available
		 */
		public void messageErreurIA();
		
		/**
     * This method print a message in order to redirect the player to the console
     */
    
    public void messageRedirectionConsole();
    
    public void messageExpensiveCost();
}
