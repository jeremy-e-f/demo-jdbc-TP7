package fr.diginamic.recensement.services;

import java.text.NumberFormat;
import java.util.Scanner;

import fr.diginamic.dao.VilleDao;
import fr.diginamic.dao.jdbc.VilleDaoJdbc;
import fr.diginamic.exceptions.FonctionnalException;
import fr.diginamic.recensement.entites.Ville;

/** Recherche et affichage de la population d'une ville
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationVilleService extends MenuService {

	@Override
	public void traiter(Scanner scanner) throws FonctionnalException {
		
		/** Saisie des paramètres */
		System.out.println("Quel est le nom de la ville recherchée ? ");
		String choix = scanner.nextLine().trim();
		
		/** On récupère la ville */
		VilleDao villeDao= new VilleDaoJdbc();
		Ville ville= villeDao.getByNom(choix);
		
		/** On affiche les résultats */
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
		if(ville== null){
			throw new FonctionnalException("La ville de nom \""+choix+"\" n'a pas été trouvée.");
		}else{
			System.out.println("Population de la ville \""+ville.getNom()+"\" : "+(NumberFormat.getInstance().format(ville.getPopulation()))+"\n("+ville+")");
		}
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
	}

}
