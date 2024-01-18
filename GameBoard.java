package fr.umlv.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import fr.umlv.modes.Mode;
import fr.umlv.participants.Participant;
import fr.umlv.party.Party;
import fr.umlv.tools.Colors;
import fr.umlv.tools.Point;
/**
 * This class represents the game board
 * @author natew
 *
 */
public class GameBoard {
	/* this field represents a room on the game board. I
	 * Its coordinates are represent by two manner : 
	 * a Integer and a Point with two coordinates*/
	
	
	/* this array represents the map : 
	 * a 0 indicates a void space
	 * a 1 indicates a button
	 * a 2 indicates a cloth
	 * a 3,4,5,... indicates a player
	 */
	private static final int boardSize = 55 ;	
	private static final int nbLine = 6;
	private static final int nbColumn = 10;
	private LinkedHashMap<Integer, ArrayList<Integer>> room = 
	new LinkedHashMap<Integer,ArrayList<Integer>>();
	/* this field represents the number of button that the game board have initially.*/
	private static final int nbBoutton = 9;
	/* this field represents the number of cloth that the game board have initially.*/
	private static final int nbCloth = 4;
	/** this is the constructor for a Board 
	 * @param temps
	 * it is the time, which represents the size for the board.
	 * @param mode
	 * it's the game mode it enables to specify the presence or the absence of items on the board like
	 * button and cloth.
	 * 
	 * */
	public GameBoard(Mode mode){
		Objects.requireNonNull(mode, "game mode cannot be null");
		
		//on remplit le plateau avec les bouttons et les vêtements et les espaces vides
		mode.fillBoard(this);
	}
	
	/**
	 * 
	 * The representation of the board.
	 * 
	 * @return
	 * 
	 * A string which is the representation of the board.
	 * 
	 */
	@Override
	public String toString() {
		var bd = new StringBuilder();
		room.forEach(
				(room, list)->{
						bd.append(room);
						bd.append(":");
						bd.append(list);
						bd.append("\n");
				}
		);
		return bd.toString();
	}
	/**
	 * 
	 * The graphical representation of the board.
	 * 
	 * @return
	 * 
	 * A string which is the representation of the board.
	 * 
	 */
	public String graphicalRepresentation() {
		var bd = new StringBuilder();
		room.forEach(
				(nb, list)->{
					if(nb % 10 == 0) 
						bd.append("\n"); // cas où on commence une nouvelle ligne
					//affichage du joueur1
					if(list.contains(3))
						Colors.ajoutCouleurBD(bd, "o", Colors.RED(), "");
					//affichage du joueur2
					else if(list.contains(4))
						Colors.ajoutCouleurBD(bd, "o", Colors.GREEN(), "");
					// cas d'un bouton
					else if(list.contains(1)) 
						Colors.ajoutCouleurBD(bd, "b", Colors.BLUE(), "");
					// cas du tissu
					else if(list.contains(2)) {
						Colors.ajoutCouleurBD(bd, "t", Colors.YELLOW(), "");
					}
					// cas d'un espace vide
					else 
						bd.append("e");		
				}
		);
		return bd.toString();
	}
	
	/**
	 * This method indicates if two board are alike.
	 * @return
	 * An boolean that indicates with true if the both objects are equals
	 * or false if not.
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		return o instanceof GameBoard && o.equals(this);
	}
	
	
	/**
	 * This method returns a board hashCode
	 * @return
	 * an int which represents the hashCode
	 */
	@Override
	public int hashCode(){
		return room.hashCode();
	}
	/**
	 * This method return the different rooms which are present on the board
	 * @return
	 * The rooms associated with their informations.
	 */
	public LinkedHashMap<Integer, ArrayList<Integer>>room(){
		return room;
	}
	
	/**
	 * 
	 * This method update the leader coordinates on the board.
	 * @param p1
	 * the player for which we want to update the coordinates.
	 */
	public void updatePositionPionPlateau(Participant p1) {
		var pionNumber = p1.nbPawn();
		//on supprime l'ancienne position où se trouvait le pion.
		this.room().forEach(
			(pos, list)-> {
				var ite = list.iterator();
				while(ite.hasNext()) {
					var el = ite.next();
						if(el == pionNumber) {
							ite.remove();
						}
				}
			}
				
		);
		//on ajoute la nouvelle position où se trouve le joueur sur le plateau
		this.room().forEach(
				(pos, list)-> {
						if(pos == p1.time())
							list.add(pionNumber);	
					});
	}
	/**
	 * A getter to the field boardSize.
	 * @return
	 * The size of the board.
	 */
	public static int boardSize() {
		return boardSize;
	}
	/**
	 * This method returns the number of button presents on the board
	 * @return
	 * the number of button
	 */
	public static int nbButton() {
		return nbBoutton;
	}
	
	/**
	 * This method returns the number of cloth presents on th board
	 * @return
	 * the number of cloths
	 */
	public static int nbCloth() {
		return nbCloth;
	}
	/**
	 * this method return the number of column of the board
	 * @return
	 * its value.
	 */
	public static int nbColumn() {
		return nbColumn;
	}
	/**
	 * this method return the number of line of the board
	 * @return
	 * its value.
	 */
	public static int nbLine() {
		return nbLine;
	}
	/**
	 * this method indicates if the board contains a button at the specific space
	 * "space"
	 * @param space
	 * the space for which we want to know if there's a button in
	 * @return
	 * true if there is a button
	 * false if no.
	 */
	public boolean containsButton(int space) {
		if(room.get(space) != null)
			return room.get(space).contains(1);
		return true;
	}
	
	/**
	 * this method indicates if the board contains a cloth at the specific space
	 * "space"
	 * @param space
	 * the space for which we want to know if there's a cloth in
	 * @return
	 * true if there is a cloth
	 * false if no.
	 */
	public boolean containsCloth(int space) {
		if(room.get(space) != null)
			return room.get(space).contains(2);
		return false;
	}

	
	
	
	
	

}
