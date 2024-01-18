
package fr.umlv.items;

import java.util.ArrayList;

import fr.umlv.participants.Participant;
import fr.umlv.tools.Point;

/**
*
* @author nathan billingi da fonseca ayrton
* A class made to handle bonus items
*
*/

public class Bonus {
	
	
	/**
	 * 
	 * This method tests if a player filled a 7x7 grid
	 * @return boolean
	 * it indicates with true if the player has filled a 7x7
	 * @param p1 type participant
	 * @param i type int, abscice of the first case no empty
	 * @param j type int, ordinate of the first case no empty
	 */
	public static boolean bonus7(Participant p1, int i, int j) {
		int[] choice = {0,1,2};
		int[] choice2 = {6,7,8};
		
		 /*1 remplis 0 si pas remplis*/
	    var maxCoord = new Point(choice2[i], choice2[j]);
	    /*Clé est un point*/
	    for(var x = choice[i]; x < maxCoord.x(); x++) {
	      for(var y = choice[j]; y < maxCoord.y(); y++) {
	        var test = new Point(x, y);
	        if(p1.playerBoard().get(test) == 0) {
	          return false;
	        }
	      }
	    }
	    return true;
		
	}
	/**
	 * Check if a player can take the bonus
	 * @param p1 type player
	 * @return boolean
	 */
	
	public static boolean checkBonus(Participant p1) {
		if(bonus7(p1, 0,0)) 
			return true;
		else {
			if(bonus7(p1,0,1))
				return true;
			else {
				if(bonus7(p1,0,2)) 
					return true;
				else {
					if(bonus7(p1,1,0)) 
						return true;
					else {
						if(bonus7(p1,1,1))
							return true;
						else {
							if(bonus7(p1,1,2))
								return true;
							else {
								if(bonus7(p1,2,0))
									return true;
								else {
									if(bonus7(p1,2,1))
										return true;
									else{
										if(bonus7(p1,2,2))
											return true;
									}
								}
							}
						}
					}
		
				}
		
			}
		}
		return false;

	}
}
