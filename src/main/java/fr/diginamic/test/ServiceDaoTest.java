package fr.diginamic.test;

import java.util.List;

import org.junit.Test;

import fr.diginamic.dao.DepartementDao;
import fr.diginamic.dao.RegionDao;
import fr.diginamic.dao.VilleDao;
import fr.diginamic.dao.jdbc.DepartementDaoJdbc;
import fr.diginamic.dao.jdbc.RegionDaoJdbc;
import fr.diginamic.dao.jdbc.VilleDaoJdbc;
import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.Ville;

public class ServiceDaoTest {
	
	/**
	 * On teste la m�thode de r�cup�ration d'une ville par son nom
	 */
	@Test
	public void villeDaoGetByNom(){
		String nom= "Montpellier";
		
		VilleDao villeDao = new VilleDaoJdbc();
		Ville ville= villeDao.getByNom(nom);
		
		System.out.println(ville);
		
		assert ville != null && ville.getNom().trim().toLowerCase().equals(nom.toLowerCase());
	}
	
	/**
	 * On teste la m�thode de r�cup�ration d'un ensemble de villes par d�partement
	 */
	@Test
	public void villeDaoGetByDepartement(){
		String codeDpt= "34";
		
		VilleDao villeDao = new VilleDaoJdbc();
		List<Ville> villes= villeDao.getByDepartement(codeDpt);
		
		for(Ville ville : villes){
			System.out.println(ville);
		}
		
		assert !villes.isEmpty() && villes.size()== 343;
	}
	
	/**
	 * On teste la m�thode de r�cup�ration d'un d�partement par son code
	 */
	@Test
	public void departementDaoGetByCode(){
		String code= "34";
		
		DepartementDao dptDao = new DepartementDaoJdbc();
		Departement dpt= dptDao.getByCode(code);
		
		System.out.println(dpt);
		
		assert dpt != null && dpt.getCode().equals(code);
	}
	
	/**
	 * On teste la m�thode de r�cup�ration d'une r�gion par son code
	 */
	@Test
	public void regionDaoGetByCode(){
		String code= "76";
		
		RegionDao regDao = new RegionDaoJdbc();
		Region region= regDao.getByCode(code);
		
		System.out.println(region);
		
		assert region != null && region.getCode().equals(code);
	}

}
