package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.recensement.entites.Region;

public interface RegionDao {
	/**
	 * Extrait la liste de toutes les régions
	 * @return List<Region>
	 */
	List<Region> extraire();
	
	/**
	 * Récupère une région à partir de son code
	 * @return Region
	 */
	Region getByCode(String code);
	
	/**
	 * Insère une nouvelle région
	 * @param Region
	 */
	void insert(Region region);
	
	/**
	 * Met à jour la région passée en paramètre
	 * @param région
	 * @return
	 */
	int update(Region region);
	
	/**
	 * Supprime la région passée en paramètre
	 * @param région
	 * @return
	 */
	boolean delete(Region region);
	
}
