package fr.umlv.display;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import fr.umlv.board.GameBoard;
import fr.umlv.input.Input;
import fr.umlv.items.Cloth;
import fr.umlv.modes.Mode;
import fr.umlv.modes.ModeI;
import fr.umlv.participants.Participant;
import fr.umlv.party.Party;
import fr.umlv.tools.Colors;
import fr.umlv.tools.Point;
import fr.umlv.tools.PointGraphic;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
/**
 * This class represents the possibility of displaying graphically.
 * @author natew
 *
 */
public class GraphicDisplay implements Display {
	/**
	 * Context, thaht represents the Application context of the type AffichageGraphique
	 */
	private ApplicationContext context; 
	
	/**
	 * It represents all the actions of play
	 */
	private ArrayList<String> listActions;
	
	/**
	 * Number of instructions shown in the Instruction box
	 */
	private int nb_instructions;
	
	/**
	 * Representation of the value of a click on the screen
	 */
	private int key;
	
	/* this field represents a button's width*/
	private final static int buttonWidth = 20;
	
	/* this field represents a button's height*/
	private final static int buttonHeight = 20;
	
	//the width of the player screen
	private float screenWidth;
	//the height of the player screen
	private float screenHeight;
	//the size of the font which is mostly used
	private final static int basicFontSize = 20;
	
	
	/**
	 * Constructor of the type AffichageGraphique. It represents a type of display
	 * a graphic display using Zen5
	 */
	public GraphicDisplay() {
		
		//au donne au contexte par défait la couleur de fond bleu cyan
		Application.run(Color.CYAN, context -> {
			this.context = context;
		});
		ScreenInfo screenInfo = context.getScreenInfo();
		screenWidth = screenInfo.getWidth();
		screenHeight = screenInfo.getHeight();
		this.listActions = new ArrayList<String>();
		this.nb_instructions = -1;
		this.key = -1;
		
		for(int i =0; i < 3 ; i++) {
			this.listActions.add("");
		}
		
	};
	/**
	 * 
	 * This method display a message on the screen
	 * @param message
	 * a message that one wants to display
	 * @param fontColor
	 * the text color
	 * @param x
	 * the text abscissa coordinate
	 * @param y
	 * the text ordinate coordinate
	 * @param fontSize
	 * the font size
	 * 
	 */
	public void writeMessage(String message, int fontSize, Color fontColor, float x, float y){
			Objects.requireNonNull(message, "your message cannot be null");
			Objects.requireNonNull(fontColor, "your font color cannot be null");
			if(fontSize<0) {
				throw new IllegalArgumentException("your font size cannot be under 0");
			}
			if(x < 0 || y < 0) {
				throw new IllegalArgumentException("your text begins out of the screen");
			}
			context.renderFrame(graphics -> {
				graphics.setColor(fontColor);
				graphics.setFont(new Font("SansSerif", Font.BOLD, fontSize));
			    graphics.drawString(message,
			    x, y);
				
			});
	}
	
	/**
	 * 
	 * this method show the leader player information.
	 * @param p1
	 * the current party state
	 */
	public void infoPlayer(Party p1) {
		//System.out.println("Voici vos informations \n\n");
		//p1.leader().displayInfo();
		eraseAll();
		summarizeLeaderInfo(p1);
		showLeaderQuilt(p1);
		waitAction();
		
	}
	
	/**
	 * 
	 * This method displays a message that announces to the player the party has started.
	 * 
	 */
	@Override
	public void entranceMessage() {
		
		//on récupère les infos du contedxte
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();
		      
		// get the size of the screen
		      
		      
		context.renderFrame(graphics -> {
			//affiche un fond par défaut de couleur cyan
			graphics.setColor(Color.CYAN);
			graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			       
			//écrit le message d'accueil
			writeMessage("Bonjour, vous venez de démarrer une partie de Patchwork ! ", 
					20, Color.BLACK, width/3, height/2);
			waitAction();
			//affiche un fond par défaut de couleur cyan
			graphics.setColor(Color.CYAN);
		});		
	
	}
	/**
	 * This method erase the content of the screen to replace it with 
	 * the background color.
	 */
	public void eraseAll() {
		//on récupère les infos du conte
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();
				      
		// get the size of the screen
				      
		//erase the former content
		context.renderFrame(graphics -> {
			//affiche un fond par défaut de couleur cyan
			graphics.setColor(Color.CYAN);
			
			graphics.fill(new Rectangle2D.Float(0, 0, width, height));
					      
		});		
		
	}
	/**
	 * 
	 * this method wait for the user next click or button pressed to do a transition
	 * 
	 */
	public void waitAction() {
		Event event = null;
		Action action = null; 
		writeMessage("Veuillez cliquer ou appuyer sur une touche", 15,Color.red,screenWidth/2+screenWidth/4,
				screenHeight/2+(screenHeight/4));
		while(event == null ||  (action != Action.POINTER_DOWN &&
			action != Action.KEY_PRESSED)){
			event = context.pollOrWaitEvent(10);
			if (event == null) {  // no event
		          continue;
		    }
			action = event.getAction();
		}
	}
	/** 
	 * this method create a button at the location given as paramer
	 * @param x
	 * the x coordinate of the button left corner.
	 * @param y
	 * the y coordinate of the button left corner.
	 */
	public void createButton(int x, int y, String message) {
		Objects.requireNonNull(message, "your message cannot be null");
		if(x  < 0 || y < 0) {
			throw new IllegalArgumentException("your button is out of the screen");
		}
		ScreenInfo screenInfo = context.getScreenInfo();
			context.renderFrame(graphics -> {
			//affiche une couleur rouge pour le carré
			graphics.setColor(Color.RED);
			
			//choix 1 : on fait un carré 3D ROUGE rouge
			//le bouton lui-même
			graphics.fill3DRect(x, y, buttonWidth,buttonHeight,
                    true);
			//le message associé au bouton
			writeMessage(message, 
					20, Color.BLACK, x+22 , y+12);
					      
		});	
		
	}
	
	/**
	 * This method show a message that enable the player to choose
	 * between two mode : graphic or console
	 */
	public void messageChoiceDisplay() {
		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();
		eraseAll();
		
		writeMessage("Quel type d'affichage voulez-vous utilisez ? ", 
				20, Color.BLACK, width/3, height/2);
		//coordonnée du bouton
		int xButton = (int)width/4;
		int yButton = (int)(height/2)+(int)(height/4);
		
		context.renderFrame(graphics -> {
			//affiche une couleur rouge pour le carré
			graphics.setColor(Color.RED);
					      
		});	
		
		
		
	
	}
	
	/**
	 * A message that explains to the player which game modes they can choose.
	 */
	public void messageChoiceMode() {
		eraseAll();
		writeMessage("Dans quel mode de jeu voulez faire votre partie ?", 20, Color.black, screenWidth/3, screenHeight/2);
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
		eraseAll();
		//message qui indique au joueur d'aller 
		writeMessage("Veuillez vous diriger vers le terminal pour saisir \n"
				+ "les informations des joueurs",
		20, Color.black, screenWidth/3, screenHeight/2);
		//message qui indique au joueur ses possibilités
		System.out.println("Pour le joueur  "+nb +"  Voulez-vous faire jouer : \n\n"
				+ "1- un humain \n"
				+ "2- une IA \n");
	}
	
	/**
   * This method print a message in order to redirect the player to the console
   */
  @Override
  public void messageRedirectionConsole() {
      eraseAll();
      writeMessage("Vous avez choisi le mode console, veuillez vous diriger vers"
              + " la console ! ", 20,Color.black,screenWidth/4, screenHeight/2);
  }
	
	/**
	 * 
	 * This method displays a message that enable to the player to choose their age.
	 */
	public void messageAge() {
		System.out.println("Quel est l'âge du joueur ? \n\n");
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
	public int intInter(int x, int y, ArrayList<String> messages, Scanner scanner){
		Objects.requireNonNull(messages, "your message cannot be null"); Objects.requireNonNull(scanner, "your scanner cannot be null");
		//on représente dans une partie les boites cliquables pour le user
		int nbButton = (y+1)-x; int xButton = (int) (screenWidth/4); 
		int yButton = (int)(screenHeight/2)+(int)(screenHeight/3);
		/*this variable contains all the combination between a button
		 * and its coordinates*/
		var pointPossible = new HashMap<Integer,PointGraphic>();
		//it indicates the step between two buttons
		int step = ((int)screenWidth-xButton)/nbButton;
		for(int i=0;i<nbButton;i++) {
			createButton(xButton+(i*step), yButton, messages.get(i));
			pointPossible.put(i+x, new PointGraphic(xButton+(i*step), yButton));
		}
		//on récupère la saisie du user si elle est dans une des boites
		Event event = null; Action action = null; int choix = -1;
		while(event == null || choix == -1){
			event = context.pollOrWaitEvent(10);
			if (event == null) {  // no event
		          continue;
		    }
			action = event.getAction();
			if(action == Action.POINTER_DOWN) {
				/*on récupère l'endroit où l'utilisateur a cliqué et on le compare aux 
				emplacements associés à des boutons*/
				Point2D.Float location = event.getLocation();
				choix = 
				matchingPointButton(location,pointPossible);
			}
		}
		return choix;
		
		
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
		Objects.requireNonNull(scanner); Objects.requireNonNull(tab);
		int xButton = (int) (screenWidth/4); int yButton = (int)(screenHeight/2)+(int)(screenHeight/3);
		/*this variable contains all the combination between a button
		 * and its coordinates*/
		var pointPossible = new HashMap<Integer,PointGraphic>();
		//it indicates the step between two buttons
		int step = ((int)screenWidth-xButton)/tab.length;
		for(int i=0;i<tab.length;i++) {
			createButton(xButton+(i*step), yButton, " "+tab[i] );
			pointPossible.put(tab[i], new PointGraphic(xButton+(i*step), yButton));
		}
		//on récupère la saisie du user si elle est dans une des boites
		Event event = null; Action action = null; int choix = -1;
		while(event == null || choix == -1){
			event = context.pollOrWaitEvent(10);
			if (event == null) {  // no event
		          continue;
		    }
			action = event.getAction();
			if(action == Action.POINTER_DOWN) {
				/*on récupère l'endroit où l'utilisateur a cliqué et on le compare aux 
				emplacements associés à des boutons*/
				Point2D.Float location = event.getLocation();
				choix = 
				matchingPointButton(location,pointPossible);
			}
		}
		return choix;
		
	}
	/**
	 * @param location
	 * the location of the user click.
	 * @param buttons
	 * all the buttons available with their coordinates
	 * @return
	 * the int associated to the selected button : 
	 * -1 if none match and the button selected otherwise
	 */
	public static int matchingPointButton(Point2D.Float location,HashMap<Integer,PointGraphic> buttons){
		Objects.requireNonNull(location, "your location cannot be null");
		Objects.requireNonNull(buttons, "your list of button cannot be null");
		//on créé un vrai point à partir de la localisation
		var p1 = new PointGraphic((int)location.x, (int)location.y);
		
		var ite = buttons.entrySet().iterator();
		while(ite.hasNext()) {
			var ele = ite.next();
			// si l'utilisateur a cliqué sur l'emplacement d'un bouton alors on
			//retoune l'entier associé à cet emplacement
			if(p1.approximatelyEqualsButton(ele.getValue(),buttonWidth,buttonHeight))  {
				return ele.getKey();
			}
		}
		return -1;
		
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
   * This method display a message that enables the player to confirm the cloth position
   */
  @Override
  public void messageExpensiveCost() {
              eraseAll();
      writeMessage(" Nous sommes désolés, vous n'avez pas les moyens d'acheter ce tissu. \n",
              20,Color.red,screenWidth/3, screenHeight/2+screenHeight/3);
  }
  
	/**
	 * this method enables to load a new image.
	 * @param image
	 * the image ones wants to load.
	 * @param path
	 * the path to the image ones wants to load.
	 */
	public void LoadImage(BufferedImage image, String path) {
		Objects.requireNonNull(path, "You cannot add a null image");
		try {
		//on charge la nouvelle image
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This method draw an image which is given as an argument
	 * @param path
	 * The path to the file associated to an image.
	 * @param x
	 * an int which represents the position depending on the axis of abscissa of the image upper-left corner
	 * @param y
	 * an int which represents the position depending on the axis of the ordinate of the image upper-left corner
	 */
	public void drawImage(String path, int x, int y){
		Objects.requireNonNull(path, "You cannot add a null image");
		if(x < 0 || x > screenWidth) {
			throw new IllegalArgumentException("your image is out of the screen size");
		}
		if(y < 0 || y > screenHeight) {
			throw new IllegalArgumentException("your image is out of the screen size");
		}
		context.renderFrame(graphics -> {
	        BufferedImage image = null;
	        try {
	    		//on charge la nouvelle image
	    			image = ImageIO.read(new File(path));
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    	}
	        graphics.drawImage(image, null, x, y);
		});
		
	}
	/**
	 * this method show the leader quilt.
	 * @param p1
	 * the party state
	 */
	public void showLeaderQuilt(Party p1) {
		Objects.requireNonNull(p1, "The party cannot be null");	
		String image1Path = "src/fr/umlv/items/imgs/cloth2.jpg";	
		String imageEmpty = "src/fr/umlv/items/imgs/emptyRoom.png";
		context.renderFrame(graphics -> {
	        
			p1.leader().playerBoard().forEach((key, value) -> { 
				/* empty room : on affiche l'image d'un carré noir*/
				if(value == 0) {
					/*try {
			    		//on charge la nouvelle image
			    			final BufferedImage image = ImageIO.read(new File("source/fr/umlv/items/imgs/emptyRoom.png"));
			    			graphics.drawImage(image, null, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
			    		} catch (IOException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    	}*/
					drawImage(imageEmpty, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					
				}
				else {
					/* ANCIEN CODE à supprimer si la version raccourcie fonctionne try {
				    		//on charge la nouvelle image
				        	final BufferedImage image2 = ImageIO.read(new File("source/fr/umlv/items/imgs/cloth1.jpeg"));
				        	 //On affiche l'image du tissu
							graphics.drawImage(image2, null, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
				    		} catch (IOException e) {
				    			// TODO Auto-generated catch block
				    			e.printStackTrace();
				    }*/
					
					drawImage(image1Path, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					
				}
				
			});
	
			
		});
		
		
	}
	/**
	 * This method shows all the informations of the leader except their quilt
	 * on the upper-left corner.
	 * @param p1
	 * the current party state
	 * 
	 */
	public void summarizeLeaderInfo(Party p1) {
		Objects.requireNonNull(p1, "The party cannot be null");
		writeMessage("Prénom : "+p1.leader().name(), 10,Color.black,0,20);
		//le score
		drawImage("src/fr/umlv/items/imgs/info_user/score.png",0,25);
		writeMessage("Score : "+p1.leader().score(), 10,Color.black,60,40);
		//les boutons
		drawImage("src/fr/umlv/items/imgs/info_user/button.png",0,65);
		writeMessage("Bouton : "+p1.leader().button(), 10,Color.black,60,80);
		//le temps
		drawImage("src/fr/umlv/items/imgs/info_user/sablier.png",0,125);
		writeMessage("Temps restant : "+p1.leader().time(), 10,Color.black,60,140);
		//les ressources
		drawImage("src/fr/umlv/items/imgs/info_user/argent.png",0,185);
		writeMessage("Ressources : "+p1.leader().ressource(), 10,Color.black,60,200);
		//le numéro du joueur
		writeMessage("Numéro de joueur : "+(p1.leader().nbPawn()-2), 10,Color.black,60,225);
	}
	
	/**
	 * This method shows all the informations of the a player except their quilt
	 * on the upper-left corner.
	 * @param p1
	 * the current party state
	 * 
	 */
	public void summarizeLeaderInfo(Participant p1) {
    Objects.requireNonNull(p1, "The party cannot be null");
    writeMessage("Prénom : "+p1.name(), 10,Color.black,0,20);
    //le score
    drawImage("src/fr/umlv/items/imgs/info_user/score.png",0,25);
    writeMessage("Score : "+p1.score(), 10,Color.black,60,40);
    //les boutons
    drawImage("src/fr/umlv/items/imgs/info_user/button.png",0,65);
    writeMessage("Bouton : "+p1.button(), 10,Color.black,60,80);
    //le temps
    drawImage("src/fr/umlv/items/imgs/info_user/sablier.png",0,125);
    writeMessage("Temps restant : "+p1.time(), 10,Color.black,60,140);
    //les ressources
    drawImage("src/fr/umlv/items/imgs/info_user/argent.png",0,185);
    writeMessage("Ressources : "+p1.ressource(), 10,Color.black,60,200);
    //le numéro du joueur
    writeMessage("Numéro de joueur : "+(p1.nbPawn()-2), 10,Color.black,60,225);
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
		eraseAll();
		writeMessage("Début du tour du joueur : "+p1.leader().name(),20,Color.black,screenWidth/3,screenHeight/2);
		
		//on attend
		waitAction();
		eraseAll();
		/* on affiche les infos du joueur dans un coin de l'écran*/
		summarizeLeaderInfo(p1);
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
		String msg = 
	"Vous avez choisi d'avancer votre pion, vous gagnerez alors "+(p1.distance()+1)+" boutons bleus.\n\n"
	+ " et votre temps passera de "+ p1.leader().time()+" à : "+(p1.leader().time()-(p1.distance()+1))+ ".\n";
		
	String msg2 = "Êtes-vous sûr de votre choix ? : \n";
		
		eraseAll();
		summarizeLeaderInfo(p1);
		writeMessage(msg,20,Color.black,screenWidth/6, screenHeight/2);
		writeMessage(msg2,20,Color.black,screenWidth/6, screenHeight/2+ screenHeight/5);
	}

	
	/**
	 * 
	 * This method show a message that warn the player that their turn is canceled.
	 * The player will redo an action.
	 */
	@Override
	public void messageCanceledAction() {
		eraseAll();
		//System.out.println("Votre action a été annulée, vous allez donc rejouer !\n");
		writeMessage("Votre action a été annulée, vous allez donc rejouer !", 20, Color.black,screenWidth/2,screenHeight/2);
		waitAction();
		
		
	}
	
	/**
	 * 
	 * This method show a message that warn the player their action succeed
	 */
	@Override
	public void messageActionSucceed() {
		eraseAll();
		writeMessage("Votre action a été prise en compte et elle a aboutie !", 20, Color.black,screenWidth/2,screenHeight/2);
		waitAction();
		//System.out.println("Votre action a été prise en compte et elle a aboutie !\n");
		
	}
	/**
	 * this method increase the value of the int called "position"
	 * depending on the arrow chosen by "fact"
	 * 
	 * @param pos
	 * the int that will be increased
	 * @param arrow
	 * a direction chosen by the user that indicates if they want to increase or decrease
	 * @param fact
	 * the value by which pos is increased
	 * @param limitDown
	 * The minimum value of position
	 * @param limitUp
	 * the maximum value of position
	 * 
	 * @return 
	 * the new int value for pos
	 * 
	 */
	public int updateIntDependingArrow(int pos, KeyboardKey arrow, int fact, int limitUp, int limitDown) {
		Objects.requireNonNull(arrow);
		if(pos < limitDown || pos > limitUp) {
			throw new IllegalArgumentException("Your position must be between "+limitUp+" and "+limitDown);
		}
		//on met à jour position selon la flèche choisie
		if(arrow == KeyboardKey.LEFT) {
			if(pos-fact <= limitDown)
				return pos = limitDown;
			else 
				return pos -=fact;
		}
		else if(arrow == KeyboardKey.RIGHT) {
			if(pos+fact >= limitUp)
				return pos = limitUp-fact;
			else {
				return pos +=fact;
			}
		}
		//pas de changement de valeur
		return pos;
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
		Objects.requireNonNull(p1);
		
		//cette variable permet de savoir quels tissus on souhaite afficher en retenant la position du dernier tissu
		//qu'on avait affiché au user.
		int position = 1;
		int step = (int)screenWidth/4;
		//images des flèches
		String image2Path = "src/fr/umlv/items/imgs/fleche_gauche2.png";	
		String image1Path = "src/fr/umlv/items/imgs/fleche_droite2.png";
		var totalCloth = p1.clothShop().size();
		Event event = null;
		KeyboardKey key = null;
		
		while(key != KeyboardKey.A) {
			//indique le nombre de cloth traité
			var i = 0;
			eraseAll();
			writeMessage("Vous êtes dans la boutique. "
					+ "Pour la quitter, appuyez sur 'A' ",20,Color.black,screenWidth/2,screenHeight/6);
			//on affiche les infos du leader
			summarizeLeaderInfo(p1);
			//on dessine deux flèches pour indiquer au joueur qu'il peut se déplacer dans la boutique
			drawImage(image2Path, (int)(10), (int)screenHeight/2);
			drawImage(image1Path, (int) (screenWidth- screenWidth/6), (int)screenHeight/2);
			{
				var list3 = new HashMap<Integer, Cloth>();
				list3 = (HashMap<Integer, Cloth>) p1.clothShop().entrySet().stream()
						.skip(position-1).limit(3).collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
						
				//on transforme la hashmap en liste pour pouvoir incrémenter la valeur de i lors de l'affichage
				var list1= list3.entrySet().stream().toList(); 
				//On affiche les tissus de la boutique 
				for(var el:list1) {
						showClothWithGraphicDisplay(el.getValue(), 50+(i*step), (int)screenHeight/2);
						writeMessage("code : "+el.getKey()+"\n temps : "+el.getValue().time()+"\n bouton : "+el.getValue().buttonPrice(), 20, Color.black,  20+(i*step), (int)screenHeight/2-30);i++;
				}	
			}
			//on laisse le joueur entrer une touche pour mettre à jour les vêtements qu'il souhaite voir
			do{
				event = context.pollOrWaitEvent(1000);
				if(event != null) {
					key = event.getKey();
				}
			}while(event == null ||  event.getKey() == null || event.getAction() != Action.KEY_PRESSED );
			//On met à jour l'endroit où le joueur se trouve dans la boutique
			position = updateIntDependingArrow(position, key, 3, totalCloth-3, 1);
			
		}
		
	
	}
	
	
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	public void messageConfirmerPositionnementPiece() {
		System.out.println("Êtes-vous sûr de vouloir positionner votre pièce ici ? \n\n");
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
		writeMessage("vous pouvez tourner votre pièce avec les flèches"
				+ " directionnelles."+" Vous pouvez lui faire faire une rotation \n"
				+"gauche ou droite avec les touches 'l' et 'r'." ,20,Color.black,screenWidth/2, screenHeight/4);
		
		writeMessage("Et pour confirmer votre choix appuyez sur 'barre espace' \n pour abandonner, appuyez sur 'a' ",
				20,Color.black,screenWidth/2, screenHeight/4+30);
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
		
		
		int step = (int)screenWidth/4;
		//indique le nombre de cloth traité
		int i = 0;
		
		var list3 = new HashMap<Integer, Cloth>();
		eraseAll();
		summarizeLeaderInfo(p1);
		/* on copie l'association clé-valeur :
		 * 	
		 * en associant à chaque clé la représentation graphique sa valeur.
		 */
		//cas numéro 1 : 2 pièce ou + après le pion neutre
		
		//indique le décalage (en pixel) sur l'axe horizontal entre chaque cloth
		if(p1.neutralPawn()+2 <= p1.clothShop().size()){
			//on met les éléments que l'on veut dans une hashmap
			list3 = (HashMap<Integer, Cloth>) p1.clothShop().entrySet().stream().skip(p1.neutralPawn()-1).limit(3).collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
			
		}
		//cas numéro 2 : 1 pièce après le pion neutre puis on recommence la boucle
		else if(p1.neutralPawn()+1 <= p1.clothShop().size()) {
			//on met les éléments que l'on veut dans une liste
			list3 = (HashMap<Integer, Cloth>) p1.clothShop().entrySet().stream().skip(p1.neutralPawn()-1).limit(2).collect (Collectors.toMap(e->e.getKey(), e->e.getValue()));
			
			//puis on renvient au début de la boucle, on retourne au tissu1 et on l'ajoute
			var el1 = p1.clothShop().entrySet().stream().limit(1).toList().get(0);
			list3.put(el1.getKey(),el1.getValue());
		}
		//cas numéro 3 : 0 pièce après le pion neutre on recommence à partir de 1 et 2.
		else {
			//on ajoute le dernier tissu
			 list3 = (HashMap<Integer, Cloth>) p1.clothShop().entrySet().stream().skip(p1.neutralPawn()-1).limit(1).collect (Collectors.toMap(e->e.getKey(), e->e.getValue()));
			//puis on recommence le tour de boucle on ne récupère que 2 tissus
			var list2 = p1.clothShop().entrySet().stream().limit(2).toList();
			//On ajoute les deux tissus à la liste de départ
			for(var el:list2) {
				list3.put(el.getKey(), el.getValue());
			}
		}
		
		//on transforme la hashmap en liste pour pouvoir incrémenter la valeur de i lors de l'affichage
		var list1= list3.entrySet().stream().toList(); 
		
		for(var el:list1) {
			showClothWithGraphicDisplay(el.getValue(), 20+(i*step), (int)screenHeight/2);
			writeMessage("code : "+el.getKey()+"\n temps : "+el.getValue().time()+"\n bouton : "+el.getValue().buttonPrice(), 20, Color.black,  20+(i*step), (int)screenHeight/2-30);i++;
		}	
		
		//writeMessage("Appuyez sur 0 pour annuler l'action ", 20, Color.black,  20+(i*step), (int)screenHeight/3+screenHeight/2);
		
		writeMessage("Appuyez sur 0 pour annuler l'action", 15,Color.red,screenWidth/2+screenWidth/4,
				screenHeight/2+(screenHeight/4));
	}
	
	/**
	 * this method shows a cloth in the graphic part.
	 * 
	 * @param c1
	 * a cloth that one want to show.
	 * @param x
	 * the coordinates of the top-left corner of the cloth
	 * depending on the axis of abscissa.
	 * @param y 
	 * the coordinates of the top-left corner of the cloth depending 
	 * on the axis of ordinate.
	 */
	
	/*par rapport au point de départ faire un "* taille d'image" par rapport à la valeur
	 * associé à x ou à y
	 */
	public void showClothWithGraphicDisplay(Cloth cloth, int x , int y){
		Objects.requireNonNull(cloth);
		if(x < 0 || y < 0) {
			throw new IllegalArgumentException("you cannot show a"
					+" cloth out of the screen zone");
		}
		//les tissus affiché à l'écran ont comme taille par défaut 35x35
		int clothSize = 35;
		context.renderFrame(graphics -> {
			cloth.coord().forEach((abs, relat) -> { 
				/* empty room : on affiche l'image d'un carré noir*/
			    //on charge la nouvelle image
			    BufferedImage image;
				try {
					image = ImageIO.read(new File("src/fr/umlv/items/imgs/cloth1.jpeg"));
					graphics.drawImage(image, null, (int)x+(abs.x()*clothSize), y+abs.y()*clothSize);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			});
		
		});
	}
	/**
	 * This method display a message that enables the player to know how they can move their cloth on the player board
	 */
	@Override
	public void messagePawnMovement() {

		writeMessage("Vous pouvez déplacer votre pièce avec les flèches directionnelles, annuler \n\n\n avec "
				+ "la touche 'A' et valider avec la touche 'espace'. \n", 20,Color.black,screenWidth/6/*+screenWidth/4*/,
				screenHeight/2+(screenHeight/4));
		
		writeMessage("Tourner la pièce avec les touches 'r' pour rotation droite et 'l' pour rotation gauche.\" ",
				20,Color.black,screenWidth/6, screenHeight/4+30);
	}
	/**
	 * This method display a message that enables the player to know they are putting a cloth on a place that already
	 * has a cloth.
	 */
	@Override
	public void errorMessageOverlappedPiece() {
		writeMessage("Erreur, vous superposez 2 pièces \n",
				20,Color.red,screenWidth/3, screenHeight/2+screenHeight/3);
	}
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	@Override
	public void validMessageClothPosition() {
		writeMessage("Êtes-vous sûr de vouloir positionner votre pièce ici ? \n",
				20,Color.black,screenWidth/3, screenHeight/2+screenHeight/3);
	}
	
	/**
	 * This method display a message that enables the player to confirm the cloth position
	 */
	@Override
	public void expensiveCostMessage() {
		writeMessage(" Nous sommes désolés, vous n'avez pas les moyens d'acheter ce tissu. \n",
				20,Color.red,screenWidth/3, screenHeight/2+screenHeight/3);
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
		/*System.out.println("Le pion du joueur 1 est représenté en rouge.\nLe pion du joueur 2 est représenté en vert.\n");
		System.out.println("En bleu apparaissent les boutons 'b' qui permettent d'utiliser vos ressources. \n");
		System.out.println("En jaune les 't' représentent les tissus que vous pouvez collecter. \n");*/
		writeMessage("Le pion du j1 est en rouge, Le pion du j2 est en vert. Les boutons sont en bleus et les tissus"
				+ "sont les objets à motifs",
				20,Color.black,screenWidth/6, screenHeight/2+ screenHeight/5);
	}
	/**
	 * This method explains display an error message because a cloth is mispositioned.
	 * 
	 */
	@Override
	public void errorMessageClothMispositioned() {
		//System.out.println(" //!\\ Attention, vous devez obligatoirement placer le tissu sur une case valide //!\\ \n");
		writeMessage(" Attention, vous devez obligatoirement placer le tissu sur une case valide \n",
				20,Color.red,screenWidth/3, screenHeight/2+screenHeight/3);
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
		
		eraseAll();
		this.summarizeLeaderInfo(p1);
		/*System.out.println(p1.board().graphicalRepresentation());
		System.out.println("\n\n");*/
		boardExplanationMessage();
		int i;
		int j;
		// position des joueurs 1 et 2
		var j1pos = p1.accessPlayer(1).time();
		var j2pos = p1.accessPlayer(2).time();
		//10 par ligne
		int compteur = GameBoard.boardSize();
		//on charge différentes images
		String imageEmpty = "src/fr/umlv/items/imgs/board/sol3.jpg";
		String imageFlag = "src/fr/umlv/items/imgs/board/flag.png";
		String imageCloth = "src/fr/umlv/items/imgs/board/cloth2_mini.jpg";
		String imageButton = "src/fr/umlv/items/imgs/board/button_short.png";
		String imagePionJ1 = "src/fr/umlv/items/imgs/board/pionJ1.png";
		String imagePionJ2 = "src/fr/umlv/items/imgs/board/pionJ2.png";
		for(i =0;i<GameBoard.nbLine();i++) {
		//on arrête plus tôt l'affichage du board pour la dernière ligne
			if(compteur <= 0)
				break;
			for(j = 0;j<GameBoard.nbColumn();j++) {
			//tant qu'on est pas à la dernière ligne, continue l'affichage du board
				if(compteur > 0) {
					drawImage(imageEmpty, (int)(screenWidth/2.5)+(35*j), (int)screenHeight/3+(i*35));
					//on affiche le drapeau pour indiquer la fin de la map
					if(compteur == 0) {
						drawImage(imageFlag, (int)(screenWidth/2.5)+(35*j), (int)screenHeight/3+(i*35));
					}
					
					//case du pion du joueur 1 = on l'affiche
					if(compteur == j1pos) {
						drawImage(imagePionJ1, (int)(screenWidth/2.5)+(20*j), (int)screenHeight/3+(i*35));
					}
					//case du pion du joueur 2 = on l'affiche si le joueur 1 n'a pas déjà été affiché
					else if(compteur == j2pos) {
						drawImage(imagePionJ2, (int)(screenWidth/2.5)+(20*j), (int)screenHeight/3+(i*35));
					}
					//l'emplacement contient un tissu : on l'affiche
					if(p1.board().containsCloth(compteur) && compteur != 55) {
						//on place le tissu entre 2 cases
						drawImage(imageCloth, (int)(screenWidth/2.5)+(20*j), (int)screenHeight/3+(i*35));
					}
					//l'emplacement contient un bouton : on l'affiche
					if(p1.board().containsButton(compteur) && compteur != 55) {
						//on place le bouton entre 2 cases
						drawImage(imageButton, (int)(screenWidth/2.5)+(20*j), (int)screenHeight/3+(i*35));
						
					}
				}
				compteur--;
				
			}
		}
		
			
	}
					
	
	 
	 /**
		 * 
		 * This method create a podium with the game winner's and display the winner
		 * @param p1
		 * the party state.
		 */
		public void showRanking(Party p1){
			eraseAll();
			int i = 1;
			writeMessage("Les gagnants dans l'ordre", 15,Color.black ,screenHeight/3, screenWidth/2);
			System.out.println("Les gagnants dans l'ordre : \n");
			for(var joueur:p1.part()){
				writeMessage(joueur.name()+" : score : "+joueur.score(), 15,Color.black ,screenHeight/3+(i*20), screenWidth/2);
				i++;
			}
		}
		/**
		 * This method transform a key to a string and returns this new value as string.
		 * @param k
		 * the key pressed
		 * 
		 * @return
		 * it returns a string that will contain the representation 
		 * of the key pressed as string
		 */
		public String fromKeyToString(KeyboardKey key) {
			Objects.requireNonNull(key);
			/*KeyboardKey tab[] = {KeyboardKey.UP,
					KeyboardKey.DOWN, KeyboardKey.LEFT,
					KeyboardKey.RIGHT,
					KeyboardKey.R, KeyboardKey.L, KeyboardKey.SPACE,KeyboardKey.A};
			//o et n correspondent à "oui" et "non".
			KeyboardKey tab2[] = {KeyboardKey.O, KeyboardKey.N};*/
			
			if(key == KeyboardKey.UP) 
				return "5";
			else if(key == KeyboardKey.DOWN) 
				return "2";
			else if(key == KeyboardKey.LEFT)
				return "1";
			else if(key == KeyboardKey.RIGHT)
				return "3";
			else if(key == KeyboardKey.R)
				return "r";
			else if(key == KeyboardKey.L)
				return "l";
			//cas de l'abandon
			else if(key == KeyboardKey.A)
				return "0";
			//validation choix
			else if(key == KeyboardKey.SPACE)
				return " ";
			
			return "-1";
			
		}
		
		/**
		 * This method display the cloth on the player board.
		 * @param t1
		 * a cloth that the method displays
		 */
		public void showClothOnBoard(Cloth t1, Participant p1) {
			Objects.requireNonNull(p1, "The party cannot be null"); Objects.requireNonNull(t1);
			String image2Path = "src/fr/umlv/items/imgs/cloth1.jpeg";	
			String image1Path = "src/fr/umlv/items/imgs/cloth2.jpg";	
			String imageEmpty = "src/fr/umlv/items/imgs/emptyRoom.png";
			String imageBoth = "src/fr/umlv/items/imgs/cloth3.jpg";
			
			context.renderFrame(graphics -> {
		        
				p1.playerBoard().forEach((key, value) -> { 
					
					//deux éléments à la fois : on affiche un carré rouge
					if(value == 1 && t1.containsValue(key)) {
						drawImage(imageBoth, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					}
					//juste un ancien élment du user
					else if(value == 1) {
					    		//on charge la nouvelle image
					        	/*final BufferedImage image2 = ImageIO.read(new File("source/fr/umlv/items/imgs/cloth1.jpeg"));
					        	 /* On affiche l'image du tissu */
								/*graphics.drawImage(image2, null, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					    		} catch (IOException e) {
					    			// TODO Auto-generated catch block
					    			e.printStackTrace();*/
					    		drawImage(image1Path, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					}
					else if(t1.containsValue(key)) {
						drawImage(image2Path, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					}
					/* empty room : on affiche l'image d'un carré noir*/
					else {
						drawImage(imageEmpty, (int)(screenWidth/2.5)+(35*key.x()), (int)screenHeight/3+(key.y()*35));
					}
					
				});
		
				
			});

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
			// les chaines de ce tableau correspondent aux différentes flèches directionnels.
			//il y a aussi L ET R pour les rotations left et right
			KeyboardKey tab[] = {KeyboardKey.UP,KeyboardKey.DOWN, KeyboardKey.LEFT,
					KeyboardKey.RIGHT,KeyboardKey.R, KeyboardKey.L, KeyboardKey.SPACE,KeyboardKey.A};
			//o et n correspondent à "oui" et "non".
			var tab2 = new ArrayList<String>(); tab2.add("Oui");tab2.add("Non");
			boolean valide = false;
			Event event;
			
			
			KeyboardKey key = null;
			do{
				
				//cette variable permet de vérifier qu'aucune action n'a été émise
				//var noAction = 1;
				eraseAll();
				messagePawnMovement();
				//on affiche les infos du joueur dans le coin haut-gauche
				summarizeLeaderInfo(p1);
				
				//p1.showClothOnBoard(t1); il faudra afficher le vêtement sur le board
				showClothOnBoard(t1, p1);
				//on remet event à null sinon l'action risque d'être comptabilisée à l'infini
				event = null;

				do{
					event = context.pollOrWaitEvent(1000000);
					if(event != null)
						key = event.getKey();
				}while(event == null ||  event.getKey() == null || event.getAction() != Action.KEY_PRESSED );
				
				
				if(event != null || key != null) {
					//on convertit l'action en une touche reconnue
					var touche = fromKeyToString(key);
					/* la touche "0" correspond à la touche d'arrêt*/
					switch(touche) {
						case "1","5","3","2","r","l","0"://valeur qui représente "gauche"
							t1.miseAjourPosition(touche);
							break;
						case " ":{
							// cas où le joueur valide son choix
							//Message.messageConfirmerPositionnementPiece();
							validMessageClothPosition();
							//on demande au joueur de confirmer son choix
							//var choixConfirme =  inputCharTab(tab2, scanner);
							var choixConfirme = intInter(0, 1, tab2, scanner);
							if(choixConfirme == 0) { 
								// si le joueur confirme son choix, on teste le positionnement de l'élément
								valide = p1.validPositioning(t1);
								if(valide == false) {
									eraseAll();
									//Message.messageErreurPieceSuperposee();
									
									errorMessageOverlappedPiece();
									
								}
							}
						}	
					}	
				}
					
			}while(key != KeyboardKey.A && valide == false);
			
			return valide;
		}
		/**
		 * this method show a message that precises to the player that they have
		 * to go back to the graphic part
		 */
		public void messageContinueMode() {
			System.out.println("Veuillez vous diriger vers la partie graphique pour le reste du jeu.\n\n");
		}
		
		/**
		 * this method shows a message that explains to the player the AI is not
		 * available
		 */
		public void messageErreurIA() {
			System.out.println("Nous sommes désolé l'IA n'est pas disponible !");
		}

}
