package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.recensement.entites.Region;

public interface RegionDao {
	/**
	 * Extrait la liste de toutes les r�gions
	 * @return List<Region>
	 */
	List<Region> extraire();
	
	/**
	 * R�cup�re une r�gion � partir de son code
	 * @return Region
	 */
	Region getByCode(String code);
	
	/**
	 * Ins�re une nouvelle r�gion
	 * @param Region
	 */
	void insert(Region region);
	
	/**
	 * Met � jour la r�gion pass�e en param�tre
	 * @param r�gion
	 * @return
	 */
	int update(Region region);
	
	/**
	 * Supprime la r�gion pass�e en param�tre
	 * @param r�gion
	 * @return
	 */
	boolean delete(Region region);
	
}
