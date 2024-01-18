package fr.umlv.participants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import fr.umlv.tools.*;
import fr.umlv.display.Display;
import fr.umlv.input.Input;
import fr.umlv.items.Bonus;
import fr.umlv.items.Cloth;
import fr.umlv.party.Party;


/**
 * Declaration of the type "joueur". It is a player who belongs to the Patchwork game.
 * It's handled by a human user
 * 
 * @author Da Fonseca Ayrton Bilingi Nathan
 */

public class Player implements Participant {
	

			/* it represents the player board.
			 * The point represents the coordinate and the integer indicates
			 * with a 0, if it's empty, and 1 if it's filled with a cloth.
			 */
			private LinkedHashMap<Point,Integer> playerBoard;
			/* this variable represents the player age*/
			private final int age;
			/* it represents the name of the player*/
			private final String name;
			/* it represents the  leftover time before the end of a player participation*/
			private int time;
			/* it represent the quantity of button that a player possess at the moment*/
			private int button;
			/* this int represents the pawn number associated to the player*/
			private int nbPawn;
			/* this field represents the quantity of blue button 
			 * that the player earn after each turn he go across a space with 
			 * a blue button on a board*/
			private int ressource;
			/* this fields indicates the player score*/
			private long score;
			/*this field indicates the bonus card possessed by the player*/
			private boolean bonusCard;
			
			/* it represents the board size for the player*/
			private static final int BOARDSIZE = 9;
			
			/* initially, players have 5 buttons*/
			private final static int INITBUTTON = 5;
			
			/* initially, players have 55 times */
			
			private final static int defaultTime = 55;
			
			public static int nextPawnNumber = 3;
			
			/** 
			 *  first constructor for a player 
			 *  @param age1
			 *  the player age.
			 *  @param name1
			 *  the player name.
			 *  @param p1
			 *  The party which is used to obtain information like the time duration.
			 *  @param nbPion
			 *  the pawn value associated to the player.
			 *  
			 *  */
			public Player(int age1, String name1) {
				Objects.requireNonNull(name1,"You need a real name not an empty name");
				if(age1 <= 0)
					throw new IllegalArgumentException("The player can't be younger"
							+ "than '1 year old'. ");
				
				name = new String(name1); /* copy profonde du name du joueur*/
				age = age1;
				time = defaultTime; /* on donne au joueur le time par défaut d'une partie*/
				/* on donne au joueur le namebre de buttons par défaut d'une 
				partie*/
				button = INITBUTTON;
				ressource = 0;
				playerBoard = new LinkedHashMap<Point,Integer>();
				score = 0;
				
				/* on remplit le plateau avec des emplacements vides par défauts*/
				for(var y=0 ;y<BOARDSIZE;y++) {
					for(var x=0 ;x<BOARDSIZE;x++) {
							/* on créé un nouveau espace du plateau pour chaque
							 * emplacement vide. 
							 * On écrit "0" pour indiquer qu'il n'y a pas de tissu*/
							playerBoard.put(new Point(x,y), 0);
					}
					
				}
				
				this.nbPawn = nextPawnNumber;
				nextPawnNumber++;
				bonusCard = false;
				
				
				
			}
			
			/**
			 * This method represents the player's board.
			 * 
			 * @return a String which represents the player board state
			 */
			public String showBoard(){
				var bd = new StringBuilder();
				bd.append("Les pièces qui recouvrent votre "
							+ "plateau sont représentées en bleu .\n"
							+ "Les emplacements vides sont représentés par la lettre 'e' pour empty. \n\n");
				
				playerBoard.forEach((key, value) -> { 
					if(key.x() == 0) {
						/*on débute une nouvelle ligne*/
						bd.append("\n");
					}
					/* empty room*/
					if(value == 0)
						bd.append("e"); 
					/* emplacement rempli par une pièce */
					else {
					/* On affiche en bleu les emplacements 
					 * remplis si c'est le joueur 1. */
						Colors.ajoutCouleurBD(bd, "f",Colors.BLUE(), Colors.ANSI_BLUE_BG());
					}
				}
						);
				return bd.toString();
			}
			/* Constructor number 2 overload */
			public Player(Player j1) {
				this(j1.age, j1.name);
			}
			@Override
			/**
			 * 
			 * @param o
			 * it's an objects with which one wants to compare a player
			 * @return boolean
			 * it indicates it's the both object are alike or not.
			 * True indicate "yes", False, indicates "no".
			 * 
			 */
			public boolean equals(Object o) {
				Objects.requireNonNull(o,"you can't compare a player with an empty item");
				return o instanceof Player j1 && j1.button == button && j1.age == age
						&& j1.name.equals(name) && j1.time == time &&
						j1.playerBoard.keySet().equals(playerBoard.keySet());
			}
			
			@Override
			/**
			 * 
			 * @param o
			 * it's an objects with which one wants to compare a player
			 * @return boolean
			 * it indicates it's the both object are alike or not.
			 * True indicate "yes", False, indicates "no".
			 * 
			 */
			public int hashCode() {
				return Integer.hashCode(age)^name.hashCode();
			}
			
			@Override
			/**
			 * 
			 * @param o
			 * it's an objects with which one wants to compare a player
			 * @return boolean
			 * it indicates it's the both object are alike or not.
			 * True indicate "yes", False, indicates "no".
			 * 
			 */
			/*/!\  A modifier pour le faire sans le cast *!\ */
			public String toString(){
				//on met à jour le score du joueur
				computeScore();
				return "Le nom du joueur : "+name+ "\n âge : "+age
						+"\n temps restant : "+time+"\n ressources : "+ ressource
				+ "\n quantité de buttons : "+button+ "\n score : " +score +"\n L'état de son plateau :  "+
						showBoard();
			}
			/**
			 * It's a getter that return a player's age
			 * 
			 * @return int
			 * 
			 * the player age
			 */
			public int age() {
				return age;
			}
			
			/**
			 * It's a getter that return a player's score
			 * 
			 * @return int
			 * 
			 * the player score
			 */
			public long score() {
				return score;
			}
			
			/**
			 * It's a getter that return a player's number of button
			 * 
			 * @return int
			 * 
			 * the player number of button.
			 */
			public int button() {
				return button;
			}
			
			/**
			 * It's a getter that return a player's name.
			 * 
			 * @return String
			 * 
			 * the player name.
			 */
			public String name() {
				return name;
			}
			
			/**
			 * It's a getter that return the Pawn number's associated with the player.
			 * 
			 * @return int
			 * 
			 * the player's pawn number
			 */
			public int nbPawn() {
				return nbPawn;
			}
			
			/**
			 * It's a getter that return the player's leftover time.
			 * 
			 * @return int
			 * 
			 * the player's leftover time
			 */
			public int time() {
				return time;
			}
			
			/**
			 * It's a getter that return the player's board
			 * 
			 * @return the player's board
			 * 
			 */
			
			public LinkedHashMap<Point,Integer> playerBoard(){
				return playerBoard;
			}
			/**
			 * 
			 * This method decrease the player time and increase their button in exchange.
			 * @param ajout
			 * The parameter is the quantity of button which is add.
			 * It also represents the quantity of time which is lost.
			 * 
			 */
			public void addButtonTimeLoss(int ajout) {
				time -= ajout;
				button += ajout;
			}
			
			/**
			 * 
			 * This method decrease the player time.
			 * @param perte
			 * It represents the quantity of time which is lost.
			 * 
			 */
			public void timeLoss(int perte) {
				time -= perte;
			}
			/**
			 * 
			 * This method displays the player informations.
			 */
			public void displayInfo() {
				System.out.println(toString());
			}
			
			/**
			 * This method increase the quantity of ressource for the player by "ressources" new ressources.
			 * 
			 * @param ressources
			 * the quantity of ressource that the player earn.
			 * 
			 * 
			 * 
			 */
			public void updateRessource(int ressources) {
				this.ressource+=ressources;
			}
			
			/**
			 * This method take off "cout" button to the player because they paid this sum to buy a new cloth.
			 * @param cout
			 * The cloth's price.
			 */
			
			public void buttonLoss(int cout) {
				button-=cout;
			}
			
			/**
			 * This method increase the quantity of button that a player has.
			 * @param gain
			 * The quantity of button the player gain.
			 */
			public void increaseButton(int gain){
				this.button+=gain;
			}
			
			
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
			public int addClothBoard(Cloth tissu, Scanner scan, Display display) {
				boolean valide;
				Objects.requireNonNull(tissu, "You cannot add a null cloth. \n");
				valide = display.moveTurnCloth(tissu,this, scan);
				//le joueur renonce à placer la piece
				if(valide == false)
					return 0;	
				return 1;
			}
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
			public String visualClothOnBoard(Cloth t1) {
				Objects.requireNonNull(t1, "You cannot show a null cloth. \n");
				var bd = new StringBuilder();
				/* si il y a chevauchement, montrer l'emplacement avec la couleur rouge pour montrer qu'il y a un problème
				 * Si il n'y a qu'un seul élément afficher sa couleur : bleu cyan pour le nouvel élément avec un cercle, bleu foncé
				 * pour les éléments anciens sinon 
				 * noir avec une croix */
				
				playerBoard.forEach((key, value) -> { 
					if(key.x() == 0) {
						/*on débute une nouvelle ligne*/
						bd.append("\n");
					}
					/* empty room*/
					if(value == 0 && !t1.containsValue(key))
						bd.append("e");
					/* emplacement rempli par une pièce ancienne du joueur et une pièce nouvelle : on indique par la couleur rouge qu'il y a
					 * un problème*/
					else if(value == 1 && t1.containsValue(key)) {
						// 't' signifie "two" car il y a superposition
						Colors.ajoutCouleurBD(bd, "t", Colors.RED(),Colors.ANSI_RED_BG());	
					}
						
					/* emplacement rempli par une pièce */
					else {
						/*si c'est une ancienne pièce*/
						if(value == 1 && !t1.containsValue(key)){
							/* On affiche en bleu les emplacements 
							* remplis. */
							Colors.ajoutCouleurBD(bd, "f", Colors.BLUE(),Colors.ANSI_BLUE_BG());	
						}
						/* si c'est la pièce qu'on essaye de placer*/
						else {
							/* On affiche en bleu cyan la nouvelle pièce. */
							Colors.ajoutCouleurBD(bd, "n", Colors.CYAN(),Colors.ANSI_CYAN_BG());	
							
						}
					}
				});
				return bd.toString();
				
			}
			/**
			 * 
			 * @param t1
			 * the cloth that the player want to set up.
			 * @return
			 * a boolean that indicates either the player succeed to set their cloth or not.
			 */
			public boolean validPositioning(Cloth t1) {
				Objects.requireNonNull(t1, "You cannot put a null cloth. \n");
				return playerBoard.entrySet().stream().noneMatch(pair->t1.containsValue(pair.getKey()) && pair.getValue() == 1);
				//return playerBoard.entrySet().stream().noneMatch(pair->t1.containsValue(pair.getKey()));
			}
			/**
			 * This method indicates either a player can pay a cloth or not
			 * @param t1
			 * @return
			 * true if they cannot
			 * false if they cannot
			 */
			public boolean canPay(Cloth t1){
				//si le joueur a assez de button et de time, alors il peut payer.
				if(t1.buttonPrice() <= button() && t1.time() <= time())
					return true;
				return false;
			}
			
			/**
			 * 
			 * @param t1
			 * the cloth that the player want to set up.
			 */
			public void updateBoard(Cloth t1) {
				Objects.requireNonNull(t1, "You cannot put a null cloth. \n");
				//on remplace toutes les coordonnées relatives de l'objet par des emplacements remplis pour le plateau du joueur
				t1.coord().forEach((k,coordrel)->playerBoard.replace(coordrel,1));
			}
			
			/**
			 *
			 *This method updates the player's score.
			 *
			 */
			public void computeScore(){
				int score = button;
				//on comptes les emplacements vides
				var malus = playerBoard.entrySet().stream().filter(el-> el.getValue()==0).count();
				//on met à jour le score du joueur
				this.score = score-malus;
				
			}
			
			/**
			 * This function do a deep copy for a cloth.
			 * 
			 * @return
			 * an object which is a deep clone of the original item.
			 * 
			 */
			@Override
			public Object clone()  {
				
				/* on copie les valeurs absolues de façon profonde*/
				
				Player copie = new Player(age, new String(name));
				copie.button = button;
				copie.time = time;
				copie.nbPawn = nbPawn;
				copie.ressource = ressource;
				copie.score = score;
				copie.playerBoard = (LinkedHashMap<Point,Integer>)playerBoard.clone();
				
				return copie;
				
			}
			/**
			 * give quantity of ressource that the player have.
			 * @return
			 * give the gamer's ressource.
			 */
			public int ressource() {
				return ressource;
			}
			/**
			 * a method that gives to the player a bonus card.
			 */
			public void WinBonusCard(){
				bonusCard = true;
			}
			

	
}
