package fr.umlv.modes;

import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.board.GameBoard;
import fr.umlv.items.Bonus;
import fr.umlv.items.Cloth;
import fr.umlv.party.Party;
import fr.umlv.tools.Point;
/**
 * A generic interface used to gather all the game mode.
 * @author natew
 *
 */
public interface Mode {
	/**
	 * This method enables to fill the field "BoutiquePiece" 
	 * with clothes. It uses the file method.
	 * @throws IOException 
	 * @param p1
	 * the party that will receive the cloth.
	 */
	public void fillClothShopByDefautWithFile(Party p1) throws IOException;
	
	/**
	 * 
	 * This method fills a game board
	 * @param p1
	 * the board that will be filled
	 */
	public void fillBoard(GameBoard p1);
	/**
	 * 
	 * This method add the Bonus card by default to the Game
	 * @param p1
	 * the boutique that will receive the bonus cards.
	 */
	public void DefaultBonusCard(Party p1);

}
