package fr.umlv.items;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import fr.umlv.display.Display;
import fr.umlv.participants.Participant;
import fr.umlv.party.Party;
import fr.umlv.tools.Colors;
import fr.umlv.tools.Point;
/**
 * 
 * It is used to create items of type "cloth".
 * 
 * @author natew
 *
 */
public class Cloth {
	
	private static final int PLAYERBOARDSIZE = 9;
	/*this is a list of coordinates that enables to know where you put a cloth on the
	 * player board :
	 * the key are the absolute coordinates
	 * the value are the relative coordinates chosen by the user on his board*/
	private HashMap<Point,Point> coord = new HashMap<Point,Point>();
	/* this field indicates the quantity of button that the player have to pay
	 * in order to have this cloth */
	private final int buttonPrice;
	/* this field indicates the ressource that this player gives to te player */
	private final int ressourceGain;
	/* this field indicates the time necessary to sew this cloth*/
	private final int time;
	/* la ArrayList<Point> coordAbsliste contient l'ensemble 
	 * des points qui constituent les coordonnées absolues
	 * du tissu.
	 */
	/**
	 * Constructor of the type "Tissu"
	 * @param buttonPrice
	 * 	a cloth cost in button
	 * @param ressourceGain
	 * 	a ressources benefits enabled by this cloth
	 * @param coordAbs
	 * 	a cloth coordinates
	 * @param time
	 *  a cloth cost in time
	 * 	
	 */
	public Cloth(int buttonPrice, int ressourceGain, ArrayList<Point> coordAbs, int time){
		Objects.requireNonNull(coord);
		if(buttonPrice < 0) {
			throw new IllegalArgumentException("You cannot add a negative number"
					+ " of button");
		}
		if(time < 0) {
			throw new IllegalArgumentException("You cannot add a negative time"
					+ "necessary to sew a cloth");
		}
		if(ressourceGain < 0) {
			throw new IllegalArgumentException("Your cloth cannot give"
					+ "to the player a negative quantity of ressources");
		}
		this.buttonPrice = buttonPrice;
		
		this.ressourceGain = ressourceGain;
		
		/*
		 * On fait une copie profonde des coordonnées.
		 * Leurs coordoonées relatives sont aussi placées au même endroit temporairement car le joueur ne les a pas encore placés
		*/
		for(var point:coordAbs) {
			this.coord.put(new Point(point.x(), point.y()), new Point(point.x(), point.y()));
		}
		this.time = time;
	}
	
	
	@Override
	/**
	 * The representation of the the cloth according to a String.
	 * @return 
	 * A string which a representation of this item.
	 * 
	 */
	public String toString() {
		var bd = new StringBuilder();
		coord.forEach((key,val)-> bd.append( key.toString()) );
		return "Ce vêtement rapporte "+ressourceGain+" ressources, coûte "+time+" time et "+buttonPrice+" boutons"+
		" ses coordonnées sont : "+bd.toString();
	}
	
	
	/**
	 * this method shows with a graphical draw a cloth
	 * @return 
	 * A string that represents graphically a cloth
	 */
	
	public String showClothGraphical() {
		var bd = new StringBuilder();
		//on parcourt le plateau
		for(var i = 0 ; i < PLAYERBOARDSIZE ; i++) {
			bd.append("\n");
			for(var j = 0; j<PLAYERBOARDSIZE ;j++) {
				var po1 = new Point(j,i);
				if(coord.containsKey(po1)) {
					// un 'o' indique que l'espace est occupé par la pièce
					Colors.ajoutCouleurBD(bd, "o", Colors.BLUE(), Colors.ANSI_BLUE_BG());
				}	
				else {
					// un x indique que l'espace est vide
					bd.append("x"); 
				}
			}
			
		}
		
		return "\n Ce vêtement coûte "+buttonPrice+" boutons, " +time+ " time "+
	"il rapporte "+ressourceGain+ " buttons (en ressource) "+" sa représentation graphique est : \n\n"+bd.toString()+"\n\n";
	}
	
	@Override
	/**
	 * @param o
	 * An object that we will compare with the current cloth.
	 * @return 
	 * A boolean that indicates either two clothes are equals or not.
	 */
	public boolean equals(Object o) {
		return o instanceof Cloth t1 && t1.buttonPrice == buttonPrice && 
				t1.time == time &&
				t1.coordAbs() == coordAbs() && t1.ressourceGain == ressourceGain;
	}
	/**
	 * A getter for the coordinates.
	 * 
	 * @return 
	 * the hashMap that represents the absolute and relative coordinates.
	 */
	public HashMap<Point,Point> coord() {
		return coord;
	}
	/**
	 * @return abscice
	 * It returns the object absolute coordinates, which represent the coordinates of this
	 * object if it was at the center of the player board.
	 * 
	 */
	public ArrayList<Point> coordAbs() {
		
		var abs = new ArrayList<Point>();
		/* on fait une liste avec les coordonnées absolues*/
		coord.forEach((key,val)-> abs.add(key) );
		
		return abs;
	}
	
	
	/**
	 * @return
	 * An int which represents the cloth hashCode.
	 */
	@Override
	public int hashCode() {
		/* on ajoute les coordonnées absolues aux hashCode car c'est une arrayList
		 * non-mutable qui contient les coordonnées absolues de l'objet
		 */
		return Integer.hashCode(buttonPrice)^Integer.hashCode(time)^
				coordAbs().hashCode()^Integer.hashCode(ressourceGain);
	}
	/**
	 * 
	 * 
	 * @return 
	 * an int which is the quantity of button that this cloth costs.
	 */
	public int buttonPrice() {
		return buttonPrice;
	}
	
	/**
	 * 
	 * 
	 * @return 
	 * an int which is the quantity of button that this cloth gives a
	 * each time a player accross a space with a button on the board
	 */
	public int ressourceGain() {
		return ressourceGain;
	}
	
	/**
	 * 
	 * 
	 * @return 
	 * an int which is the time necessary to add this cloth to the 
	 * board.
	 */
	public int time() {
		return time;
	}
	/**
	 * This function do a deep copy for a cloth.
	 * 
	 * @return
	 * an object which is a deep clone of the original item.
	 * 
	 */
	public Object clone()  {
		
		/* on copie les valeurs absolues de façon profonde*/
		
		Cloth copie = new Cloth(buttonPrice, ressourceGain, coordAbs(), time);
		
		var iterator = copie.coord.entrySet().iterator();
		
		/* on copie les valeurs relatives de façon profonde*/
		
		while(iterator.hasNext()) {
			
			var value = iterator.next();
			
			copie.coord.put(value.getKey(),
				(Point)(coord.get(value.getValue()).clone()));
			
		}
		
		return copie;
		
	}
	
		/**
		 * 
		 * This method launch the cloth's effect on the player "p1".
		 * @param p1
		 * The player for which the effect will apply
		 * @param display
		 * Display the game state
		 * @return
		 * A int that express either the action succeed (1) or not (0)
		 * 
		 * 
		 */
		public int clothEffect(Participant p1, Display display){
			Objects.requireNonNull(p1, "you cannot add an empty participant");
			//le joueur peut payer
			if(p1.canPay(this)) {
				p1.updateRessource(ressourceGain());
				p1.timeLoss(time);
				p1.buttonLoss(buttonPrice);
				p1.updateBoard(this);
				return 1;
			}
			//le joueur ne peut pas payer
			else {
				display.messageExpensiveCost();
			}
			return 0;
		}
		
		/**this method enable to load a file's data that contain 
		 *  a group of cloth's coordinates.
		 *  @param chemin
		 *  The path until the file you want to loaded.
		 *  @return 
		 *  The list of cloth that has been loaded.
		 *  */
		public static List<Cloth> load(Path chemin) throws IOException {
			Objects.requireNonNull(chemin, "the path to the file cannot be null.");
			return load(chemin,StandardCharsets.UTF_8); // on donne utf-8 par défaut
		}
		/**
		 * 
		 * 
		 * @param text
		 * It's the text that represents the coordinates. 
		 * They are on the format [(x,y);(z,e),...]
		 * @return
		 * An arraylist of point that represents the coordinates for the cloth
		 */
		public static ArrayList<Point> fromTextToList(String text){
			Objects.requireNonNull(text, "You cannot use a void text as list of point coordinates");
			ArrayList <String> items = new ArrayList<String>();
			items.addAll(Arrays.asList(text.split(";")));
			if(items.size()>0) {
				//on retire les crochets si ils sont présents autour des coordonnées
				String nouv = items.get(0).replace("[", "");
				nouv = nouv.replace("]", "");
				items.set(0, nouv);
				
			}
			if(items.size()>1) {
				// on retire les crochets si ils sont présents autour des coordonnées
				String nouv2 = items.get(items.size()-1).replace("]", "");
				items.set(items.size()-1, nouv2);
				
			}
			
			var res = new ArrayList<Point>();
			for(var coor:items) {
				/*on ajoute chaque String qui représente un point sous forme de vrais Point */
				res.add(Point.fromTextToPoint(coor));
			}
			
			return res;
			
			
		}
		
		/**
		 * this method enable to load a file's data that contain 
		 *  a group of cloth's coordinates.
		 *  @param chemin
		 *  The path until the file you want to loaded.
		 *  @param c1
		 *  The data format.
		 *  @return 
		 *  The list of cloth that has been loaded.
		 *  
		  */
		public static List<Cloth> load(Path chemin, Charset c1) {
			Objects.requireNonNull(chemin);
			Objects.requireNonNull(c1);
			var listTissu = new ArrayList<Cloth>();
			try(var reader = Files.newBufferedReader(chemin, c1)){
				String line;
				while( (line = reader.readLine()) != null ) {
					var tab1 = line.split(":");
					if(tab1[0].equals("tissu") && tab1.length==9) {
						listTissu.add(new Cloth(Integer.parseInt(tab1[4]),Integer.parseInt(tab1[8]),fromTextToList(tab1[2]),
								Integer.parseInt(tab1[6])));
					}
				}	
			}
			catch(IOException e){
				System.err.println(e.getMessage());
			}
			
			return listTissu;
		}
		/**
		 * 
		 * This method updates the cloth coordinates by moving its relative coordinates to the left.
		 * 
		 */
		public void deplaceGauche() {
			boolean valide;
			valide = true;
			// pair correspond à la pair <Point, Point>
			valide = coord.entrySet().stream().allMatch((pair)->pair.getValue().x()-1 >= 0);
			//on accepte de changer les coordonées seulement si le déplacement est valide
			if(valide) {
				coord.replaceAll((key,oldpoint)->new Point(oldpoint.x()-1, oldpoint.y()));
			}

		}
		
		/**
		 * 
		 * This method updates the cloth coordinates by moving its relative coordinates to the right.
		 * 
		 */
		public void deplaceDroite() {
			boolean valide;
			valide = true;
			// pair correspond à la pair <Point, Point>
			valide = coord.entrySet().stream().allMatch((pair)->pair.getValue().x()+1 <= 8);
			//on accepte de changer les coordonées seulement si le déplacement est valide
			if(valide) {
				coord.replaceAll((key,oldpoint)->new Point(oldpoint.x()+1, oldpoint.y()));
			}

		}
		
		/**
		 * 
		 * This method updates the cloth coordinates by moving its relative coordinates to the up.
		 * 
		 */
		public void deplaceHaut() {
			boolean valide;
			valide = true;
			// pair correspond à la pair <Point, Point>
			valide = coord.entrySet().stream().allMatch((pair)->pair.getValue().y()-1 >= 0);
			//on accepte de changer les coordonées seulement si le déplacement est valide
			if(valide) {
				coord.replaceAll((key,oldpoint)->new Point(oldpoint.x(), oldpoint.y()-1));
			}

		}
		
		/**
		 * 
		 * This method updates the cloth coordinates by moving its relative coordinates to the up.
		 * 
		 */
		public void deplaceBas() {
			boolean valide;
			valide = true;
			// pair correspond à la pair <Point, Point>
			valide = coord.entrySet().stream().allMatch((pair)->pair.getValue().y()+1 <= 8);
			//on accepte de changer les coordonées seulement si le déplacement est valide
			if(valide) {
				coord.replaceAll((key,oldpoint)->new Point(oldpoint.x(), oldpoint.y()+1));
			}

		}
		/**
		 * 
		 * @param p1
		 * the coordinates that we want to check if they match with the item coordinates.
		 * @return
		 * true if the Point is one of the object coordinates
		 */
		public boolean containsValue(Point p1) {
			return this.coord.entrySet().stream().anyMatch(p2->p2.getValue().equals(p1));
		}
		
		/**
		 * reset the relative coordinates to their original values.
		 */
		public void resetCoord(){
			coord.replaceAll((key,oldpoint)->new Point(key.x(), key.y()));
		}
		/**
		 * This method make do a rotation to a cloth.
		 * A rotation in the natural clock sense (90°C)
		 */
		public void RotationGauche(){
			coord.replaceAll((key,oldpoint)->new Point(oldpoint.y(), 8-oldpoint.x()));
		}
		
		/**
		 * This method make do a rotation to a cloth.
		 * A rotation in the natural clock sense (90°C)
		 */
		public void RotationDroite(){
			coord.replaceAll(
					(key,oldpoint)->
						new Point(8-oldpoint.y(),oldpoint.x())
					
					);
		}
		/**
		 * update the cloth position depending on the direction chosen.
		 * @param direction
		 * an int which indicates the direction chosen
		 * 
		 */
		public void miseAjourPosition(String direction) {
			switch(direction) {
				case "1"://valeur qui représente "gauche"
					this.deplaceGauche();
					break;
				case "5"://valeur qui représente "haut"
					this.deplaceHaut();
					break;
				case "3"://valeur qui représente "droite"
					this.deplaceDroite();
					break;	
				case "2"://valeur qui représente "bas"
					this.deplaceBas();
					break;
				case "r": //la valeur "r" permet de faire tourner la pièce de 90°
					this.RotationDroite();
					break;
				case "l": //la valeur "g" permet de faire une rotation de 180°
					this.RotationGauche();
					break;
				case "0": // cas où le joueur renonce à l'idée d'acheter la pièce
					this.resetCoord();
					break;
			}
		}
	

	
	

}
