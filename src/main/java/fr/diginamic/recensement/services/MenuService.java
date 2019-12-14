package fr.diginamic.recensement.services;

import java.util.Scanner;

import fr.diginamic.exceptions.FonctionnalException;

/** Classe représentant un service
 * @author DIGINAMIC
 *
 */
public abstract class MenuService {

	/** Méthode abstraite de traitement que doivent posséder toutes les méthodes de services 
	 * @param lignes lignes du fichier
	 * @param scanner scanner
	 * @throws FonctionnalException 
	 */
	public abstract void traiter(Scanner scanner) throws FonctionnalException ;
}
