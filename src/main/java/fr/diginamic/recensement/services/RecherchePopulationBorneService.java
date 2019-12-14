package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import fr.diginamic.dao.VilleDao;
import fr.diginamic.dao.jdbc.VilleDaoJdbc;
import fr.diginamic.exceptions.FonctionnalException;
import fr.diginamic.recensement.entites.Ville;

/** Recherche et affichage de toutes les villes d'un département dont la population
 * est comprise entre une valeur min et une valeur max renseignées par l'utilisateur.
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Scanner scanner) throws FonctionnalException {
		
		/** Saisie des paramètres */
		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine().trim();
		
		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine().trim();
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine().trim();
		
		/** On vérifie les paramètres saisis */
		int min;
		try{
			min = Integer.parseInt(saisieMin) * 1000;
		}catch(Exception e){
			throw new FonctionnalException("Le minimum doit être un nombre");
		}
		if(min< 0){
			throw new FonctionnalException("Le minimum doit être un nombre positif");
		}
		
		int max;
		try{
			max = Integer.parseInt(saisieMax) * 1000;
		}catch(Exception e){
			throw new FonctionnalException("Le maximum doit être un nombre");
		}
		if(max< 0){
			throw new FonctionnalException("Le maximum doit être un nombre positif");
		}
		if(min> max){
			throw new FonctionnalException("Le maximum saisi doit être supérieur au minimum saisi");
		}
		
		/** On récupère les villes du département */
		VilleDao villeDao= new VilleDaoJdbc();
		List<Ville> villes= villeDao.getByDepartement(choix);
		
		/** On affiche les résultats */
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
		if(villes.isEmpty()){
			throw new FonctionnalException("Aucune ville du département de code: \""+choix+"\" n'a été trouvée.");
		}else{
			/** On filtre les villes selon leur population et les paramètres saisis */
			List<Ville> villesFiltrees= villes.stream().filter(ville -> ville.getPopulation()>=min && ville.getPopulation()<=max).collect(Collectors.toList());
			if(villesFiltrees.isEmpty()){
				throw new FonctionnalException("Aucune ville du département de code: \""+choix+"\" dont la population est comprise entre "+min+" et "+max+" n'a été trouvée.");
			}else{
				System.out.println(villesFiltrees.size()+" ville(s) du département de code: \""+choix+"\" dont la population est comprise entre "+min+" et "+max+": ");
				for(Ville ville : villesFiltrees){
					System.out.println(ville);
				}
			}
		}
		System.out.println("\n\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\\_\n");
	}

}
