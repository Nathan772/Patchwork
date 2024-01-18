package fr.umlv.display;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import fr.umlv.input.Input;
import fr.umlv.items.Cloth;
import fr.umlv.participants.Participant;
import fr.umlv.party.Party;

/**
 * This class represents the possibility of displaying on the console.
 * @author natew
 *
 */
public class ConsoleDisplay implements Display {
	/**
	 * 
	 * This method displays a message that announces to the player the party has started.
	 * 
	 */
	@Override
	public void entranceMessage() {
		System.out.println("Bonjour vous venez de démarrer une partie de Patchwork.");
		
	}
	/**
	 * 
	 * This method displays a message that enables to know if someone wants a human or an AI.
	 * @param nb
	 * It indicates the number of the player
	 * 
	 */
	@Override
	public void playerChoice(int nb) {
		if(nb <= 0)
			throw new IllegalArgumentException("You must give a number for the "
					+ "next player which is 1 or more");
		System.out.println("Pour le joueur  "+nb +"  Voulez-vous faire jouer : \n\n"
				+ "1- un humain \n"
				+ "2- une IA \n");
	}
	/**
	 * 
	 * This method displays a message that enable to the player to choose their name.
	 */
	@Override
	public void nameMessage() {
		System.out.println("Quel est votre prénom ? \n\n");
	}
	/**
	 * 
	 * This method displays a message that enable to the player to choose their age.
	 */
	@Override
	public void ageMessage() {
		System.out.println("Quel est l'âge du joueur ? \n\n");
	}
	/**
	This function print a message that summarize the player involved in the party.
	@param p1
	The current party state.
	*/
	@Override
	public void showParticipant(Party p1) {
		
		Objects.requireNonNull(p1, "The party for which we will show"
				+"the players who are involved cannot be null");
		var bd =  new StringBuilder();
		
		for(var j1:p1.part()) {
			
			/* on ajoute les noms des participants */
			
			bd.append(j1.name());
			
			bd.append(", ");
		}
		
		System.out.print("Les participants de cette partie sont : "+bd+" \n\n\n");
		
	}
	
	/**
	 * A message that explains to the player which game modes they can choose.
	 */
	public void messageChoiceMode() {
		System.out.println("Dans quel mode du jeu voulez vous jouer : 1- mode simple, 2- mode complet");
	}
	/**
	 * This method display a message that proposes to the player which game mode they wqant.
	 */
	public void messageChoiceDisplay() {
		System.out.println(" Quel type d'affichage voulez-vous utilisez : (1) terminal, (2) Graphique ? \n\n");
	}
	
	
	/**
	 * This method display message in order to indicate to the lead-player which action they can do
	 * @param p1
	 * The party state, in order to know who is the leader.
	 */
	@Override
	public void messageTurnBeginning(Party p1){
		Objects.requireNonNull(p1, "The party associated to this turn"
				+ "cannot be null");
		
		System.out.println("Début du tour du joueur : "+p1.leader().name()+"\n\n");
		System.out.println("Voici vos informations \n\n");
		p1.leader().displayInfo();
		System.out.println(" \n Vous pouvez soit : \n"
				+ "1- acheter une pièce \n"
				+ "2- avancer jusqu'à une case après votre adversaire,\n en sachant que la distance qui vous sépare est de : "+p1.distance()+"\n"+
				"3- regarder l'ensemble des éléments présents dans la boutique de pièce \n\n");
	}
	/**
	 * 
	 * this method show the leader player information.
	 * @param p1
	 * the current party state
	 */
	public void infoPlayer(Party p1) {
		System.out.println("Voici vos informations \n\n");
		p1.leader().displayInfo();
	}
	/**
	 * 
	 * This method displays a message that enable to the player to choose their name.
	 */
	 public void messagePrenom() {
		System.out.println("Quel est votre prénom ? \n\n");
	}
	 
	 /**
		 * This method display a message that enables the player to confirm the cloth position
		 */
		public void messageConfirmerPositionnementPiece() {
			System.out.println("Êtes-vous sûr de vouloir positionner votre pièce ici ? ('entrée' = oui, n = non) \n\n");
		}
		
		/**
		 * This method display a message that enables the player to know they are putting a cloth on a place that already
		 * has a cloth.
		 */
		public void messageErreurPieceSuperposee() {
			System.out.println("Erreur, vous superposez 2 pièces \n\n");
		}
		
		/**
		 * This method display a message that enables the player to know how they can move their cloth on the player board
		 */
		public void messageDeplacementPiece() {
			System.out.println("Vous pouvez déplacer votre pièce avec les touches 1 (gauche), 2(bas), 3(droite), 5(haut).\n");
			System.out.println("'l':  pour la rotation gauche. \n");
			System.out.println("'r':  pour la rotation droite. \n");
			System.out.println("Pour confirmer votre choix, appuyez sur 'entrée'. Si vous renoncez, appuyez sur 0. \n");
		}
		
		/**
		 * 
		 * This method display the cloth on the player board.
		 * @param t1
		 * The cloth that we want to display
		 * 
		 */
		public void showClothOnBoard(Cloth t1, Participant p1) {
			System.out.println(p1.visualClothOnBoard(t1));
		}

	 
	 /**
		 * 
		 * This method displays a message that enables to know if someone wants a human or an AI.
		 * @param nb
		 * It indicates the number of the player
		 * 
		 */
		public void choixJoueur(int nb) {
			if(nb <= 0)
				throw new IllegalArgumentException("You must give a number for the "
						+ "next player which is 1 or more");
			System.out.println("Pour le joueur  "+nb +"  Voulez-vous faire jouer : \n\n"
					+ "1- un humain \n"
					+ "2- une IA \n");
		}
	 
	 /** 
	  * This method displays a message that enable to the player to choose their age.
	 */
	public void messageAge() {
		System.out.println("Quel est l'âge du joueur ? \n\n");
	}
	
	/**
	 * 
	 * This method display a message in order to confirm the player action to walk.
	 * @param p1
	 * The party state, in order to know who is the leader and the distance between the leader and the other player.
	 *
	 */
	@Override
	public void messageWalk(Party p1){
		Objects.requireNonNull(p1, "The party associated to this turn"
				+ "cannot be null");
		
		System.out.println("Vous avez choisi d'avancer votre pion, vous gagnerez alors "+(p1.distance()+1)+" boutons bleus.\n\n"
				+ " et votre temps passera de "+ p1.leader().time()+" à : "+(p1.leader().time()-(p1.distance()+1))+ "\n"
				+"Êtes-vous sûr de votre choix ? : \n"
				+"1- Oui \n"
				+"2- Non \n");
	}
	
	/**
	 * 
	 * This method show a message that warn the player that their turn is canceled.
	 * The player will redo an action.
	 */
	@Override
	public void messageCanceledAction() {
		
		System.out.println("Votre action a été annulée, vous allez donc rejouer !\n");
		
	}
	
	/**
	 * 
	 * This method show a message that warn the player their action succeed
	 */
	@Override
	public void messageActionSucceed() {
		
		System.out.println("Votre action a été prise en compte et elle a aboutie !\n");
		
	}
	/**
	 * 
	 * This method display the game shop and illustrate the clothes.
	 * 
	 * @param p1
	 * the game state
	 * 
	 */
	@Override
	public void messageWholeShop(Party p1) {
		
		var bd = new StringBuilder();
		
		p1.clothShop().forEach(
		/* on copie l'association clé-valeur :
		 * 	
		 * en associant à chaque clé la représentation graphique sa valeur.
		 */
			(k,v)-> bd.append("code : ").append(k).append("\n\n").append(v.showClothGraphical()).append("\n\n")
				
		);
		
		System.out.println("Voici la liste des pièces qui compose toute la boutique : \n\n"+bd);
		
	
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
	@Override
	public void displayShopEntrance(Party p1) {
		
		var bd = new StringBuilder();
		/* on copie l'association clé-valeur :
		 * 	
		 * en associant à chaque clé la représentation graphique sa valeur.
		 */
		//cas numéro 1 : 2 pièce ou + après le pion neutre
		if(p1.neutralPawn()+2 <= p1.clothShop().size()){
			p1.clothShop().entrySet().stream().skip(p1.neutralPawn()-1).limit(3).
			forEach(el->bd.append("code : ").append(el.getKey()).append("\n").append(el.getValue().showClothGraphical()));
		}
		//cas numéro 2 : 1 pièce après le pion neutre puis on recommence la boucle
		else if(p1.neutralPawn()+1 <= p1.clothShop().size()) {
			// on ajoute les deux derniers tissus
			p1.clothShop().entrySet().stream().skip(p1.neutralPawn()-1).limit(2).
			forEach(el->bd.append("code : ").append(el.getKey()).append("\n").append(el.getValue().showClothGraphical()));
			//puis on renvient au débnut de la boucle, on retourne au tissu1 
			p1.clothShop().entrySet().stream().limit(1).
			forEach(el->bd.append("code : ").append(el.getKey()).append("\n").append(el.getValue().showClothGraphical()));
		}
		//cas numéro 3 : 0 pièce après le pion neutre on recommence à partir de 1 et 2.
		else {
			//on ajoute le dernier tissu
			p1.clothShop().entrySet().stream().skip(p1.neutralPawn()-1).limit(1).
			forEach(el->bd.append("code : ").append(el.getKey()).append("\n").append(el.getValue().showClothGraphical()));
			//puis on recommence le tour de boucle on ne récupère que 2 tissus
			p1.clothShop().entrySet().stream().limit(2).
			forEach(el->bd.append("code : ").append(el.getKey()).append("\n").append(el.getValue().showClothGraphical()));
		}
		
		System.out.println(" Appuyez 0- pour annuler votre choix. \n"
				+ "Sinon, il vous suffit"
				+ " d'écrire le code associé "
				+ "à une pièce pour l'acheter.\n"
				+ "Voici la liste des pièces que vous pouvez acheter : \n\n"+bd);
		
	}
	
	/**
	 * This method display a message that enables the player to know how they can move their cloth on the player board
	 */
	@Override
	public void messagePawnMovement() {
		System.out.println("Vous pouvez déplacer votre pièce avec les touches 1 (gauche), 2(bas), 3(droite), 5(haut).\n");
		System.out.println("'l':  pour la rotation gauche. \n");
		System.out.println("'r':  pour la rotation droite. \n");
		System.out.println("Pour confirmer votre choix, appuyez sur 'entrée'. Si vous renoncez, appuyez sur 0. \n");
	}
	/**
	 * This method display a message that enables the player to know they are putting a cloth on a place that already
	 * has a cloth.
	 */
	@Override
	public void errorMessageOverlappedPiece() {
		System.out.println("Erreur, vous superposez 2 pièces \n\n");
	}
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	@Override
	public void validMessageClothPosition() {
		System.out.println("Êtes-vous sûr de vouloir positionner votre pièce ici ? ('entrée' = oui, n = non) \n\n");
	}
	
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	@Override
	public void expensiveCostMessage() {
		System.out.println("Nous sommes désolés, vous n'avez pas les moyens d'acheter ce tissu. \n\n");
	}
	/**
	 * A message that explains to the player which game modes they can choose.
	 */
	@Override
	public void choiceMessageMode() {
		System.out.println("Bonjour veuillez choisir le mode du jeu : 1- mode simple, 2- mode complet");
	}
	/**
	 * This method explains the meaning of the elements on the board. 
	 * 
	 */
	@Override
	public void boardExplanationMessage() {
		System.out.println("Le pion du joueur 1 est représenté en rouge.\nLe pion du joueur 2 est représenté en vert.\n");
		System.out.println("En bleu apparaissent les boutons 'b' qui permettent d'utiliser vos ressources. \n");
		System.out.println("En jaune les 't' représentent les tissus que vous pouvez collecter. \n");
	}
	
	/**
   * This method display a message that enables the player to confirm the cloth position
   */
  @Override
  public void messageExpensiveCost() {
      System.out.println("Nous sommes désolés, vous n'avez pas les moyens d'acheter ce tissu. \n\n");
  }
  
	/**
	 * This method explains display an error message because a cloth is mispositioned.
	 * 
	 */
	@Override
	public void errorMessageClothMispositioned() {
		System.out.println(" //!\\ Attention, vous devez obligatoirement placer le tissu sur une case valide //!\\ \n");
	}
	
	/**
	 * 
	 * the representation of the game board in a graphic viewpoint.
	 * 
	 * @param p1
	 * the party state
	 */
	 @Override
	 public void showGameBoard(Party p1) {
		Objects.requireNonNull(p1, "the party cannot be null");
		System.out.println(p1.board().graphicalRepresentation());
		System.out.println("\n\n");
		boardExplanationMessage();
	}
	 
	 /**
		 * 
		 * This method create a podium with the game winner's and display the winner
		 * @param p1
		 * the party state.
		 */
		public void showRanking(Party p1){
			System.out.println("Les gagnants dans l'ordre : \n");
			for(var joueur:p1.part()){
				System.out.println(joueur);
			}
		}
		
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
		@Override
		public int intInter(int x, int y, ArrayList<String> messages, Scanner scanner){
			Objects.requireNonNull(scanner);
			Objects.requireNonNull(messages);
			return Input.intInter(x,y,scanner);
			
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
		public int inputIntTab(int []tab, Scanner scanner) {
			Objects.requireNonNull(scanner);
			Objects.requireNonNull(tab);
			return Input.inputIntTab(tab, scanner);
		}
		
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
		public boolean moveTurnCloth(Cloth t1, Participant p1, Scanner scanner) {
			Objects.requireNonNull(scanner);
			Objects.requireNonNull(t1);
			Objects.requireNonNull(p1);
			return Input.moveTurnCloth(this, t1, p1, scanner);
		}
		
		/**
		 * this method show a message that precises to the player that if they have
		 * to go back to the graphic part
		 */
		public void messageContinueMode() {
			System.out.println("Le reste de la partie se déroule ici \n\n");
		}
		
		/**
		 * this method shows a message that explains to the player the AI is not
		 * available
		 */
		public void messageErreurIA() {
			System.out.println("Nous sommes désolé l'IA n'est pas disponible !");
		}
		
		/**
     * This method print a message in order to redirect the player to the console
     */
    @Override
    public void messageRedirectionConsole() {}

}
