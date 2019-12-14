package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.recensement.entites.Departement;

public interface DepartementDao {
	
	/**
	 * Extrait la liste de tous les départements
	 * @return List<Departement>
	 */
	List<Departement> extraire();
	
	/**
	 * Récupère un département à partir de son code
	 * @return Departement
	 */
	Departement getByCode(String code);
	
	/**
	 * Insère un nouveau département
	 * @param Departement
	 */
	void insert(Departement departement);
	
	/**
	 * Met à jour la département passé en paramètre
	 * @param département
	 * @return
	 */
	int update(Departement departement);
	
	/**
	 * Supprime le département passé en paramètre
	 * @param département
	 * @return
	 */
	boolean delete(Departement departement);
	
	/**
	 * Renvoie la population du département
	 * @return population
	 */
	int getPopulation();
}