package fr.umlv.modes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import fr.umlv.board.GameBoard;
import fr.umlv.items.Bonus;
import fr.umlv.items.Cloth;
import fr.umlv.party.Party;
import fr.umlv.tools.Point;
/**
 * A class which is used to represents the game modeI.
 * It's a game version which is more simple than the complet version.
 * @author natew
 *
 */
public class ModeI implements Mode {
	/**
	 * This method enables to fill the field "clothShop" 
	 * with clothes. It uses the file method.
	 * @throws IOException 
	 * 
	 * @param p1
	 * The party that will receive the shop data
	 * 
	 */
	@Override
	public void fillClothShopByDefautWithFile(Party p1) throws IOException {
		var listCloth = new ArrayList<Cloth>();
		var compteur = 1;
		listCloth = (ArrayList<Cloth>) Cloth.load(Path.of("src/fr/umlv/items/listeTissuV1.txt"));
		Collections.shuffle(listCloth);
		for(var elem: listCloth) {
			/* on ajoute le vêtements présents dans le fichier listTissuV1*/
			p1.clothShop().put(compteur, elem);
			compteur++;
		}
		
	}
	/**
	 * 
	 * This method fills a game board
	 * @param p1
	 * the board that will be filled
	 */
	@Override
	public void fillBoard(GameBoard p1) {
		/*on commence par la fin car 
		 * c'est le temps du joueur qui sert de référence.
		 * On commence à temps "boardsize" et on finit à temps 0
		 */
		for(var i = p1.boardSize(); i>0;i--) {
				p1.room().put(i,new ArrayList<Integer>());
				p1.room().get(i).add(0); // on ajoute les espaces blancs	
		}
		
	}
	
	/**
	 * 
	 * This method add the Bonus card by default to the Game
	 * @param p1
	 * the party that will receive the bonus card.
	 * 
	 */
	public void DefaultBonusCard(Party p1) {
		return;
	}

}
