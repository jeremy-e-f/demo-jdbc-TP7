package fr.diginamic.dao;

import java.util.List;
import fr.diginamic.recensement.entites.Ville;

public interface VilleDao {
	
	/**
	 * Extrait la liste de toutes les villes
	 * @return List<Ville>
	 */
	List<Ville> extraire();
	
	/**
	 * Récupère une ville à partir de son nom
	 * @return Ville
	 */
	Ville getByNom(String nom);
	
	/**
	 * Récupère les villes d'un département donné
	 * @return Ville
	 */
	List<Ville> getByDepartement(String codeDepartement);
	
	/**
	 * Insère une nouvelle ville
	 * @param Ville
	 */
	void insert(Ville ville);
	
	/**
	 * Met à jour la ville passée en paramètre
	 * @param ville
	 * @return
	 */
	int update(Ville ville);
	
	/**
	 * Supprime la ville passée en paramètre
	 * @param ville
	 * @return
	 */
	boolean delete(Ville ville);

}
