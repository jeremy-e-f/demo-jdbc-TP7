package fr.diginamic.recensement.entites;

/** Représente une ville
 * @author DIGINAMIC
 *
 */
public class Ville implements EnsemblePop, ObjectDB {
	
	/** identifiant unique */
	private int id;
	/** code INSEE de la ville */
	private String code;
	/** nom de la ville */
	private String nom;
	/** population totale */
	private int population;
	/** departement : Département */
	private Departement departement;
	
	
	/** Constructeur
	 * @param département
	 * @param codeVille code INSEE de la ville
	 * @param nom nom de la ville
	 * @param population population totale
	 */
	public Ville(String code, String nom, int population, Departement departement) {
		this.departement = departement;
		this.code = code;
		this.nom = nom;
		this.population = population;
	}
	
	public Ville(int id, String code, String nom, int population, Departement departement) {
		this(code, nom, population, departement);
		this.id = id;
	}

	@Override
	public String toString(){
		return nom + " " + population + " hab.";
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
	 * @return the codeDepartement
	 */
	public Departement getDepartement() {
		return departement;
	}

	/** Setter
	 * @param codeDepartement the codeDepartement to set
	 */
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	/** Getter
	 * @return the codeVille
	 */
	public String getCode() {
		return code;
	}

	/** Setter
	 * @param codeVille the code to set
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
	
	/**
	 * Equals
	 * Deux villes sont considérées comme représentant la même ville si elles ont le même code et le même nom
	 */
	@Override
	public boolean equals(Object o){
		return o instanceof Ville && ((Ville)o).getCode().equals(this.getCode()) && ((Ville)o).getNom().equals(this.getNom());  
	}

}
