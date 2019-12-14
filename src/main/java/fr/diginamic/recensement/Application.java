package fr.diginamic.recensement;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.exceptions.FonctionnalException;
import fr.diginamic.recensement.services.RecherchePopulationBorneService;
import fr.diginamic.recensement.services.RecherchePopulationDepartementService;
import fr.diginamic.recensement.services.RecherchePopulationRegionService;
import fr.diginamic.recensement.services.RecherchePopulationVilleService;

/**
 * Application de traitement des données de recensement de population
 * 
 * @param args
 */
public class Application {

	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	/**
	 * Point d'entrée
	 * 
	 * @param args
	 *            arguments (non utilisés ici)
	 */
	public static void main(String[] args) {
		Scanner scanner= new Scanner(System.in); 

		// Menu
		int choix = 0;
		do {

			// Affichage du menu
			afficherMenu();

			// Poser une question à l'utilisateur
			String choixMenu = scanner.nextLine();

			// Conversion du choix utilisateur en int
			choix = Integer.parseInt(choixMenu);

			// On exécute l'option correspondant au choix de l'utilisateur
			try {
				switch (choix) {
				// On recherche la population d'une ville donnée
				case 1: 
					RecherchePopulationVilleService rechercheVille = new RecherchePopulationVilleService();
					rechercheVille.traiter(scanner);
					break;
				// On recherche la population d'un département donnée
				case 2:
					RecherchePopulationDepartementService rechercheDept = new RecherchePopulationDepartementService();
					rechercheDept.traiter(scanner);
					break;
				// On recherche la population d'une région donnée
				case 3:
					RecherchePopulationRegionService rechercheRegion = new RecherchePopulationRegionService();
					rechercheRegion.traiter(scanner);
					break;
				// On recherche l'ensemble des villes d'un département donné dont la population est comprise entre le minimum et le maximum saisis
				case 4:
					RecherchePopulationBorneService recherchePopBorne = new RecherchePopulationBorneService();
					recherchePopBorne.traiter(scanner);
					break;
				}
			} catch (FonctionnalException e1) {
				System.err.println(e1.getMessage());
			} catch (Exception e){
				LOG.error(e.getMessage());
			}
		} while (choix != 99);

		scanner.close();

	}

	/**
	 * Affichage du menu
	 */
	private static void afficherMenu() {
		System.out.println("***** Recensement population *****");
		System.out.println("1. Rechercher la population d'une ville");
		System.out.println("2. Rechercher la population d'un département");
		System.out.println("3. Rechercher la population d'une région");
		System.out.println("4. Rechercher la population des villes d'un dept entre min et max");
		System.out.println("99. Sortir\n");
	}
}
