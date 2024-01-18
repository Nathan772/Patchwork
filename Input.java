package fr.umlv.input;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import fr.umlv.display.Display;
import fr.umlv.items.Cloth;
import fr.umlv.participants.Participant;

public class Input {
	/**
	 * This method enables to enter a integer between an interval of two numbers. the both number are into the interval.
	 * @param x
	 * the integer which represents the beginning of the interval allowed.
	 * @param y
	 * the integer which represents the ending of the interval allowed.
	 * 
	 * @return
	 * It returns an int which is the int chosen by the user.
	 */
	public static int intInter(int x, int y, Scanner scanner){
		//Scanner scan = new Scanner(System.in);
		String tmp;
		do{
			tmp = scanner.nextLine();
			if(!isANumber(tmp))
				System.out.println("Erreur veuillez entrer un nombre entre "+x+" et "+y+" \n");
		}while(!isANumber(tmp) || Integer.parseInt(tmp) < x || Integer.parseInt(tmp) >y );
		return Integer.parseInt(tmp);
	}
	/**
	 * This method checks if a String represents a number
	 * @param chaine
	 * The String that we want to test.
	 * @return
	 * true if it's a number, false otherwise.
	 */
	public static boolean isANumber(String chaine) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		if(chaine == null) {
			return false;
		}
		return pattern.matcher(chaine).matches();
		
	}
	
	/**
	 * This method enables to enter a String.
	 * 
	 * @return
	 * It returns the String which is the String chosen by the user.
	 */
	public static String inputString(Scanner scanner){
		//Scanner scan = new Scanner(System.in);
		String mot = scanner.nextLine();
		while(mot.equals("")) {
			mot = scanner.nextLine();
			mot.replaceAll("\\s+", ""); /*on supprime tous les espaces blancs de la chaine de caractère*/
			if(mot.equals(""))
				System.out.println("Erreur veuillez entrer une chaine de caractère valide !");
		}
		return mot;
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
	public static int inputIntTab(int []tab, Scanner scanner){
		//Scanner scan = new Scanner(System.in);
		boolean contains = false;
		String tmp;
		do{
			System.out.println("Veuillez entrer une touche incluse parmi celles qui vous avaient été proposées.");
			tmp = scanner.nextLine();
			if(isANumber(tmp)) {
				final int nb = Integer.parseInt(tmp);
				contains = IntStream.of(tab).anyMatch(x-> x == nb);
			}
		//tant qu'aucun nombre ne correspond à ceux qui sont autorisés, on relance la boucle
		}while(!isANumber(tmp) || contains == false);
		return Integer.parseInt(tmp);
	}
	
	/**
	 * This method enables to enter a character that matches with a value in the "tab"
	 * @param tab
	 * a tab that contains the value allowed
	 * @return
	 * the value chosen by the user
	 */
	public static String inputCharTab(String []tab, Scanner scanner){
		//Scanner scan = new Scanner(System.in);
		String chara = (scanner.nextLine());
		chara = chara.toLowerCase();
		var bd = new StringBuilder();
		final String tmp = chara;
		boolean contains = Arrays.stream(tab).anyMatch(x-> x.equals(tmp));
		while(contains == false ) {
			System.out.println("Veuillez entrer une touche incluse parmi celles qui vous avaient été proposées.");
			chara = scanner.nextLine();
			final String tmp2 = chara;
			contains = Arrays.stream(tab).anyMatch(x-> x.equals(tmp2));
		}
		return chara;
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
	 * @param display
	 * a variable that handle the displaying of informations.
	 * 
	 * @return 
	 * 1 if the player succeed to set up a new cloth.
	 * 0 if the player failed.
	 * 
	 */
	public static boolean moveTurnCloth(Display display, Cloth t1, Participant p1, Scanner scanner) {
		//Scanner scan = new Scanner(System.in);
		//remarque : la chaine vide correspond à entrée
		// les chaines de ce tableau correspondent aux différentes flèches directionnels.
		String tab[] = {"","r","l","5","3","2","0","1"};
		//o et n correspondent à "oui" et "non".
		String tab2[] = {"", "n"};
		/*Message.messageDeplacementPiece();
		p1.montrerPieceSurPlateau(t1);
		String touche = SaisiecharaTab(tab);*/
		boolean valide = false;
		
		var touche = new String();
		do{
			display.messageDeplacementPiece();
			display.showClothOnBoard(t1, p1);
			touche = inputCharTab(tab, scanner);
			/* la touche "0" correspond à la touche d'arrêt*/
			switch(touche) {
				case "1","5","3","2","r","l","0"://valeur qui représente "gauche"
					t1.miseAjourPosition(touche);
					break;
				case "":{// cas où le joueur valide son choix
					display.messageConfirmerPositionnementPiece();
					var choixConfirme =  inputCharTab(tab2, scanner); //on demnande au joueur de confirmer son choix
					if(choixConfirme.equals("")) { 
					// si le joueur confirme son choix, on teste le positionnement de l'élément
						valide = p1.validPositioning(t1);
						if(valide == false)
							display.messageErreurPieceSuperposee();
					}
				}	
			}	
			
		}while((!touche.equals("0") && valide == false));
		// si le joueur a annulé son action, on remet le tissu à sa position initiale
		if(touche.equals("0"))
			t1.resetCoord();
		return valide;
		
	}
}

