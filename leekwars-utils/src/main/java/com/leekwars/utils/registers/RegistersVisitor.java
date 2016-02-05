package com.leekwars.utils.registers;

import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.model.Farmer;
import com.leekwars.utils.model.KeyValueCouple;
import com.leekwars.utils.model.LeekSummary;

/**
 * Interface décrivant un visiteur pour le parcours des registres de tous les poireaux d'un éleveur.
 * Cas possibles :
 * onInit -> FIN
 * onInit -> onEnd -> FIN
 * onInit -> onEnd -> [ onLeek -> onRegisters ] -> FIN
 * onInit -> onEnd -> [ onLeek -> onRegisters -> onOverflow ] -> FIN
 * @author Bezout
 */
public interface RegistersVisitor {
	/**
	 * Tolérence.
	 * La taille totale est limitée à 100 éléments.
	 * Cette méthode doit retourner le nombres de places minimales acceptables. Exemple 10 (sur 100). 
	 * @return [0-100]
	 */
	int getLeniency();
	
	/**
	 * Déclenché au début du traitement
	 * @param pFarmer données de l'éleveur
	 */
	void onInit(final Farmer pFarmer);
	
	/**
	 * Déclenché lorsque l'on change de poireau
	 * @param pLeek
	 */
	void onLeek(final LeekSummary pLeek);
	
	/**
	 * Déclenché lorsque l'on vient de récupérer les registres du poireau.
	 * Toujours déclenché avant "onOverflow"
	 * @param pLeek
	 * @param pRegisters
	 */
	void onRegisters(final LeekSummary pLeek, final KeyValueCouple[] pRegisters);
	
	/**
	 * Déclenché lorsque l'on détecte un dépassement de la taille acceptable
	 * @param pLeek
	 * @throws LWException
	 */
	void onOverflow(final LeekSummary pLeek) throws LWException;
	
	/**
	 * Déclenché à la fin du traitement.
	 * Toujours déclenché si "onInit" n'a pas levé d'erreur.
	 */
	void onEnd();
}
