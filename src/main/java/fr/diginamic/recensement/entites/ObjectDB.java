package fr.diginamic.recensement.entites;

/**
 * Représente un objet lié à la base
 * @author Jérémy
 */
public interface ObjectDB {
	
	/** Renvoie l'Id auquel cet objet est lié dans la base de données */
	int getId();
	
	/** Permet d'affecter un Id à cet objet */
	void setId(int Id);
	
}
