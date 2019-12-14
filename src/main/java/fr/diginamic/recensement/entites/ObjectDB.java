package fr.diginamic.recensement.entites;

/**
 * Repr�sente un objet li� � la base
 * @author J�r�my
 */
public interface ObjectDB {
	
	/** Renvoie l'Id auquel cet objet est li� dans la base de donn�es */
	int getId();
	
	/** Permet d'affecter un Id � cet objet */
	void setId(int Id);
	
}
