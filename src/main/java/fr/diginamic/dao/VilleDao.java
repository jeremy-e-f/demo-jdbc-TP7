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
	 * R�cup�re une ville � partir de son nom
	 * @return Ville
	 */
	Ville getByNom(String nom);
	
	/**
	 * R�cup�re les villes d'un d�partement donn�
	 * @return Ville
	 */
	List<Ville> getByDepartement(String codeDepartement);
	
	/**
	 * Ins�re une nouvelle ville
	 * @param Ville
	 */
	void insert(Ville ville);
	
	/**
	 * Met � jour la ville pass�e en param�tre
	 * @param ville
	 * @return
	 */
	int update(Ville ville);
	
	/**
	 * Supprime la ville pass�e en param�tre
	 * @param ville
	 * @return
	 */
	boolean delete(Ville ville);

}
