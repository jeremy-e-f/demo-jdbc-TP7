package fr.diginamic.recensement.entites;

/** Représente un département
 * @author DIGINAMIC
 *
 */
public class Departement implements ObjectDB, EnsemblePop {
	
	/** Identifiant unique */
	private int id;
	/** Code : String*/
	private String code;
	/** Nom : String*/
	private String nom;
	/** Population : int */
	private int population;
	/** Région d'appartenance */
	private Region region;
	
	/** Constructeur
	 * @param code code
	 */
	public Departement(String code) {
		this.code = code;
	}
	
	public Departement(String code, String nom, Region region) {
		this.code = code;
		this.nom = nom;
		this.region = region;
	}

	public Departement(int id, String code, String nom, Region region) {
		this(code, nom, region);
		this.id = id;
	}
	
	public Departement(int id, String code, String nom, int population, Region region) {
		this(id, code, nom, region);
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
	 * @return region
	 */	
	public Region getRegion() {
		return region;
	}
	/** Setter
	 * @param region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	@Override
	public String toString() {
		return "Departement [code=" + code + ", nom=" + nom + ", region=" + region + "]";
	}
	
	/**
	 * Equals
	 * Deux départements sont considérés comme représentant le même département si ils ont le même code
	 */
	@Override
	public boolean equals(Object o){
		return o instanceof Departement && ((Departement)o).getCode().equals(this.getCode());  
	}

}
