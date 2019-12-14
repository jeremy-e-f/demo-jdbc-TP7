package fr.diginamic.recensement;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.jdbc.ConnectionJDBC;
import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.Ville;

/** Classe permettant de lire le contenu d'un fichier
 * @author DIGINAMIC
 */
public class IntegrationRecensement {
	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger("");

	/**
	 * Point d'entrée
	 * 
	 * @param args
	 *            arguments (non utilisés ici)
	 */
	public static void main(String[] args) {
		
		//Scanner scanner = new Scanner(System.in);
		Long debut= System.currentTimeMillis();
		IntegrationRecensement.renseignerBD("D:/Work/Programmation/JAVA/java-poo-j6/src/main/resources/recensement.csv");
		Long fin= System.currentTimeMillis();
		LOG.info("L'opération d'intégration s'est achevé avec succès en "+(fin-debut)+" ms.");
	}
	
	/** Lit le contenu du fichier en paramètre, contenant des données de recensement avec le format attendu,
	 * et l'inscrit dans la base de données
	 * @param cheminFichier chemin d'accés au fichier sur le disque dur
	 * @return void
	 */
	public static void renseignerBD(String cheminFichier){
		
		List<String> lignes = null;
		ArrayList<Ville> listeVilles= new ArrayList<Ville>();
		HashMap<String,Departement> listeDepartements= new HashMap<String,Departement>();
		HashMap<String,Region> listeRegions= new HashMap<String,Region>();
		
		try {
			File file = new File(cheminFichier);
			lignes = FileUtils.readLines(file, "UTF-8");
			
			// On supprime la ligne d'entéte avec les noms des colonnes
			lignes.remove(0);
			int id_ville= 1, id_dpt= 1, id_reg= 1;
			
			// On parse les lignes afin de récupérer les informations sur les villes
			for (String ligne: lignes){
				String[] morceaux = ligne.split(";");
				String codeRegion = morceaux[0].trim();
				String nomRegion = morceaux[1].trim();
				String codeDepartement = morceaux[2].trim();
				String codeCommune = morceaux[3].trim();
				String nomCommune = morceaux[4].trim();
				int population = Integer.parseInt(morceaux[5].replace(" ", "").trim());
				
				
				// On cree nos objets à insérer à partir des données extraites du fichier
				Region region= null;
				if(!listeRegions.containsKey(codeRegion)){
					region= new Region(id_reg++, codeRegion, nomRegion);
					listeRegions.put(codeRegion, region);
				}else{	
					region= listeRegions.get(codeRegion);
				}
				
				Departement dpt= null;
				if(!listeDepartements.containsKey(codeDepartement)){
					dpt= new Departement(id_dpt++, codeDepartement, "", region);
					listeDepartements.put(codeDepartement, dpt);
				}else{	
					dpt= listeDepartements.get(codeDepartement); 
				}
				
				Ville ville= new Ville(id_ville++, codeCommune, nomCommune, population, dpt);
				listeVilles.add(ville);
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		/** On vide les tables avant les insertions */
		viderTables();
		
		/** Insertion des données en base */
		// Insertion des régions
		String requete= "INSERT INTO regions(id,code,nom) VALUES(?,?,?);";
		// On prépare les paramètres
		ArrayList<Object[]> listeParams= new ArrayList<Object[]>();
		for(Region region: listeRegions.values()){
			Object[] params= {Integer.valueOf(region.getId()),region.getCode(),region.getNom()};
			listeParams.add(params);
			//System.err.println(region);
		}
		inscrireLignesBase(requete, listeParams);
		
		// Insertion des départements
		requete= "INSERT INTO departements(id,code,nom,id_reg) VALUES(?,?,?,?);";
		// On prépare les paramètres
		listeParams= new ArrayList<Object[]>();
		for(Departement dpt: listeDepartements.values()){
			Object[] params= {Integer.valueOf(dpt.getId()),dpt.getCode(),dpt.getNom(),Integer.valueOf(dpt.getRegion().getId())};
			listeParams.add(params);
			//System.err.println(dpt);
		}
		inscrireLignesBase(requete, listeParams);
		
		// Insertion des villes
		requete= "INSERT INTO villes(id,code,nom,population,id_dpt) VALUES(?,?,?,?,?);";
		// On prépare les paramètres
		listeParams= new ArrayList<Object[]>();
		for(Ville ville: listeVilles){
			Object[] params= {Integer.valueOf(ville.getId()),ville.getCode(),ville.getNom(),Integer.valueOf(ville.getPopulation()),Integer.valueOf(ville.getDepartement().getId())};
			listeParams.add(params);
		}
		inscrireLignesBase(requete, listeParams);
	}
	
	/**
	 * Permet de réinitialiser les tables
	 */
	public static void viderTables(){
		Connection connexion= ConnectionJDBC.getInstance();
		Statement req=  null;
		
		try {
			req = connexion.createStatement();
			req.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
			req.executeUpdate("TRUNCATE TABLE villes;");
			req.executeUpdate("TRUNCATE TABLE departements;");
			req.executeUpdate("TRUNCATE TABLE regions;");
			req.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				req.close();
				connexion.commit();
				connexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * Permet d'inscrire les données dans la base en fonction de la requête et des paramètres passés en paramètre
	 * @param requete requête préformatée
	 * @param listeParams liste des valeurs à ajouter à la requête
	 */
	public static void inscrireLignesBase(String requete, List<Object[]> listeParams){
		Connection connexion= ConnectionJDBC.getInstance();
		PreparedStatement preReq=  null;
		
		try {
			preReq = connexion.prepareStatement(requete);
			for(Object[] params: listeParams){
				int i= 1;
				for(Object param: params){
					preReq.setObject(i,param);
					i++;
				}
				//System.err.println(preReq.toString());
				preReq.executeUpdate();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				preReq.close();
				connexion.commit();
				connexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				throw new RuntimeException("L'initialisation de la base de données a échoué.");
			}
		}
	}
	
	
}
