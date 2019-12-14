package fr.diginamic.recensement.services;

import java.text.NumberFormat;
import java.util.Scanner;

import fr.diginamic.dao.RegionDao;
import fr.diginamic.dao.jdbc.RegionDaoJdbc;
import fr.diginamic.exceptions.FonctionnalException;
import fr.diginamic.recensement.entites.Region;

/** Recherche et affichage de la population d'une région
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationRegionService extends MenuService {

	@Override
	public void traiter(Scanner scanner) throws FonctionnalException {
		
		/** Saisie des paramètres */
		System.out.println("Quel est le code de la région recherchée ? ");
		String choix = scanner.nextLine().trim();
		
		/** On récupère la région */
		RegionDao regDao= new RegionDaoJdbc();
		Region region= regDao.getByCode(choix);
		
		/** On affiche les résultats */
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
		if(region== null){
			throw new FonctionnalException("La région de code \""+choix+"\" n'a pas été trouvée.");
		}else{
			System.out.println("Population de la région de code \""+region.getCode()+"\" : "+(NumberFormat.getInstance().format(region.getPopulation()))+"\n("+region+")");
		}
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
	}

}
