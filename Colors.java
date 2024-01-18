package fr.umlv.tools;
/**
 * A class made to use the different colors possible and display them on the terminal.
 * @author natew
 *
 */
public class Colors {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_WHITE_BG ="\u001B[47m";
	private static final String ANSI_YELLOW_BG ="\u001B[43m";
	private static final String ANSI_BLUE_BG ="\u001B[44m";
	private static final String ANSI_RED_BG = "\033[41m";    // RED
	private static final String ANSI_CYAN_BG = "\u001B[46m"; // CYAN
	private static final String GREEN = "\033[0;32m";   // GREEN
	private static final String RED = "\033[0;31m";     // RED
	private static final String BLUE = "\033[0;34m";    // BLUE
	private static final String CYAN = "\033[0;36m";    // CYAN
	private static final String YELLOW = "\033[0;33m";  // YELLOW
	   
	
	
	/**
	 * 
	 * This method add a colored string to a builder.
	 * @param chaine
	 * The String the person wants to write
	 * @param bd
	 * the builder that will append word "chaine"
	 * @param couleur
	 * the color code that the person wants to add
	 */
	public static void ajoutCouleurBD(StringBuilder bd, String chaine,String couleur, String couleurBG ) {
		bd.append(couleur);
		bd.append(couleurBG);
		bd.append(chaine);
		bd.append(ANSI_RESET);
	}
	/**
	 * gives the ascii code for a white background
	 * @return
	 * the ascii code for a white background
	 */
	public static String ANSI_WHITE_BG() {
		return ANSI_WHITE_BG;
	}
	
	/**
	 * gives the ascii code for a yellow background
	 * @return
	 * the ascii code for a yellow background
	 */
	public static String ANSI_YELLOW_BG() {
		return ANSI_YELLOW_BG;
	}
	
	/**
	 * gives the ascii code for a blue background
	 * @return
	 * the ascii code for a blue background
	 */
	public static String ANSI_BLUE_BG() {
		return ANSI_BLUE_BG;
	}
	
	/**
	 * gives the ascii code for a red background
	 * @return
	 * the ascii code for a red background
	 */
	public static String ANSI_RED_BG() {
		return ANSI_RED_BG;
	}
	
	/**
	 * gives the ascii code for a cyan background
	 * @return
	 * the ascii code for a cyan background
	 */
	public static String ANSI_CYAN_BG() {
		return ANSI_CYAN_BG;
	}
	
	/**
	 * gives the ascii code for a green string
	 * @return
	 * the ascii code for a green string
	 */
	public static String GREEN() {
		return GREEN;
	}
	
	/**
	 * gives the ascii code for a red string
	 * @return
	 * the ascii code for a red string
	 */
	public static String RED() {
		return RED;
	}
	
	/**
	 * gives the ascii code for a BLUE string
	 * @return
	 * the ascii code for a BLUE string
	 */
	public static String BLUE() {
		return BLUE;
	}
	
	/**
	 * gives the ascii code for a CYAN string
	 * @return
	 * the ascii code for a CYAN string
	 */
	public static String CYAN() {
		return CYAN;
	}
	
	/**
	 * gives the ascii code for a YELLOW string
	 * @return
	 * the ascii code for a YELLOW string
	 */
	public static String YELLOW() {
		return YELLOW;
	}

}
