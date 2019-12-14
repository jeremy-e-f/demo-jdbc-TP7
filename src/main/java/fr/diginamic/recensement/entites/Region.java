package fr.diginamic.recensement.entites;

/** Représente une région
 * @author DIGINAMIC
 *
 */
public class Region implements ObjectDB, EnsemblePop {

	/** identifiant unique */
	private int id;
	/** code : String*/
	private String code;
	/** nom : String*/
	private String nom;
	/** population : int */
	private int population;
	
	/** Constructeur
	 * @param code code
	 * @param nom nom
	 */
	public Region(String code, String nom) {
		this.code = code;
		this.nom = nom;
	}
	
	public Region(int id, String code, String nom) {
		this(code, nom);
		this.id = id;
	}
	
	public Region(int id, String code, String nom, int population) {
		this(id, code, nom);
		this.population = population;
	}
	
	/** Getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Setter
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/** Getter
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/** Setter
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/** Getter
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}
	
	/** Setter
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	@Override
	public String toString() {
		return "Region [code=" + code + ", nom=" + nom + "]";
	}
	
	/**
	 * Equals
	 * Deux régions sont considérées comme représentant la même région si elles ont le même code
	 */
	@Override
	public boolean equals(Object o){
		return o instanceof Region && ((Region)o).getCode().equals(this.getCode());  
	}

}
