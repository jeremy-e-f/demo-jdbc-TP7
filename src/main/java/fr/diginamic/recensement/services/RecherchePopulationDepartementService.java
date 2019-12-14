package fr.diginamic.recensement.services;

import java.text.NumberFormat;
import java.util.Scanner;

import fr.diginamic.dao.DepartementDao;
import fr.diginamic.dao.jdbc.DepartementDaoJdbc;
import fr.diginamic.exceptions.FonctionnalException;
import fr.diginamic.recensement.entites.Departement;

/** Recherche et affichage de la population d'un département
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationDepartementService extends MenuService {

	@Override
	public void traiter(Scanner scanner) throws FonctionnalException {
		/** Saisie des paramètres */
		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine().trim();
		DepartementDao dptDao= new DepartementDaoJdbc();
		Departement dpt= dptDao.getByCode(choix);
		
		/** On affiche les résultats */
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
		if(dpt== null){
			throw new FonctionnalException("Le département de code \""+choix+"\" n'a pas été trouvée.");
		}else{
			System.out.println("Population du département \""+dpt.getCode()+"\" : "+(NumberFormat.getInstance().format(dpt.getPopulation()))+"\n("+dpt+")");
		}
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
	}

}
