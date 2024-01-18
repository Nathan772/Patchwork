package fr.umlv.modes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import fr.umlv.board.GameBoard;
import fr.umlv.items.Bonus;
import fr.umlv.items.Cloth;
import fr.umlv.party.Party;
import fr.umlv.tools.Point;
/**
 * A type created for the game mode number II.
 * This class is used to implements the specificity of the second mode.
 * @author natew
 *
 */
public class ModeII implements Mode {
	/**
	 * This method enables to fill the field "clothShop" 
	 * with clothes. It uses the file method.
	 * @throws IOException 
	 * 
	 * @param
	 * The party that will receive the shop data
	 */
	@Override
	/**
	 * This method gives the cloth that will be used for a party.
	 * @param p1
	 * The party that will use the cloths.
	 * 
	 */
	public void fillClothShopByDefautWithFile(Party p1) throws IOException {
		Objects.requireNonNull(p1);
		var listCloth = new ArrayList<Cloth>();
		var compteur = 1;
		listCloth = (ArrayList<Cloth>) Cloth.load(Path.of("src/fr/umlv/items/listeTissuV2.txt"));
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
		Objects.requireNonNull(p1);
		int compteur = 0;
		/*on détermine le nombre de fois où on doit mettre un bouton et tous les 
		 * "modulos combien" pour être exact.*/
		var divB = p1.boardSize()/GameBoard.nbButton(); 
		/*on détermine le nombre de fois où on doit mettre un cloth et tous les 
		 "modulos combien" pour être exact.*/
		var divC = p1.boardSize()/GameBoard.nbCloth(); 
		//on fait dans l'ordre décroissant car les joueurs doivent commencer par temps "boardsize" et finir à temps 0.
		for(var i = p1.boardSize(); i>0;i--) {
			if(compteur % divB == 0) {
				p1.room().put(i,new ArrayList<Integer>());
				p1.room().get(i).add(1); // on ajoute les boutons
				p1.room().get(i).add(0); // on ajoute un espace blanc
			}
			else if(compteur % divC == 0) {
				p1.room().put(i,new ArrayList<Integer>());
				p1.room().get(i).add(2); // on ajoute les tissu
				p1.room().get(i).add(0); // on ajoute un espace blanc pour le moment où il n'y aura pl
			} 
			else { 
				p1.room().put(i,new ArrayList<Integer>());
				p1.room().get(i).add(0); // on ajoute les espaces blancs
			
			}
			compteur++;		
		}
		
	}
	
	/**
	 * 
	 * This method add the Bonus card by default to the Game
	 * 
	 */
	public void DefaultBonusCard(Party p1) {
		/*int i;
		int j;
		// on créé la carte bonus qui attend comme 
		// coordonnées les coordonnées 7x7
		ArrayList<Point> coord = new ArrayList<Point>();
		for(i = 0 ; i < 7;i++) {
			for(j =0;j<7;j++) {
				coord.add(new Point(i,j));
			}
		}
		var cloth1 = new Tissu(0,0,coord,0);
		var b4 = new Bonus(10,cloth1);
		p1.bonusCard().add(b4);*/
	}

}
