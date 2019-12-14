package fr.diginamic.dao;

import java.util.List;

import fr.diginamic.recensement.entites.Departement;

public interface DepartementDao {
	
	/**
	 * Extrait la liste de tous les d�partements
	 * @return List<Departement>
	 */
	List<Departement> extraire();
	
	/**
	 * R�cup�re un d�partement � partir de son code
	 * @return Departement
	 */
	Departement getByCode(String code);
	
	/**
	 * Ins�re un nouveau d�partement
	 * @param Departement
	 */
	void insert(Departement departement);
	
	/**
	 * Met � jour la d�partement pass� en param�tre
	 * @param d�partement
	 * @return
	 */
	int update(Departement departement);
	
	/**
	 * Supprime le d�partement pass� en param�tre
	 * @param d�partement
	 * @return
	 */
	boolean delete(Departement departement);
	
	/**
	 * Renvoie la population du d�partement
	 * @return population
	 */
	int getPopulation();
}