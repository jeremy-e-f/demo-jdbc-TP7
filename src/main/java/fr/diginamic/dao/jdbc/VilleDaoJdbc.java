package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.dao.VilleDao;
import fr.diginamic.jdbc.ConnectionJDBC;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Region;

public class VilleDaoJdbc implements VilleDao {

	private static final Logger LOG = LoggerFactory.getLogger("");

	@Override
	public List<Ville> extraire() {
		Connection maConnexion= ConnectionJDBC.getInstance();
		ArrayList<Ville> listeVilles= new ArrayList<Ville>();
		HashMap<Integer,Departement> listeDepartements= new HashMap<Integer,Departement>();
		HashMap<Integer,Region> listeRegions= new HashMap<Integer,Region>();
		Statement monStatement= null;
		ResultSet curseur= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT * FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id);");
			while(curseur.next()){
				int id_reg= curseur.getInt("r.id");
				String code_reg= curseur.getString("r.code");
				String nom_reg= curseur.getString("r.nom");
				
				int id_dpt= curseur.getInt("d.id");
				String code_dpt= curseur.getString("d.code");
				
				int id_ville= curseur.getInt("v.id");
				String code_ville= curseur.getString("v.code");
				String nom_ville= curseur.getString("v.nom");
				int pop_ville= curseur.getInt("v.population");
				
				Region region= null;
				if(!listeRegions.containsKey(id_reg)){
					region= new Region(id_reg, code_reg, nom_reg);
					listeRegions.put(id_reg, region);
				}else{	
					region= listeRegions.get(id_reg); 
				}
				
				Departement dpt= null;
				if(!listeDepartements.containsKey(id_dpt)){
					dpt= new Departement(id_dpt, code_dpt, "", region);
					listeDepartements.put(id_dpt, dpt);
				}else{	
					dpt= listeDepartements.get(id_dpt); 
				}
				
				Ville ville= new Ville(id_ville, code_ville, nom_ville, pop_ville, dpt);
				listeVilles.add(ville);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				curseur.close();
				monStatement.close();
				maConnexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return listeVilles;
	}
	
	@Override
	public Ville getByNom(String nom) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement= null;
		ResultSet curseur= null;
		Ville ville= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT * FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id) WHERE v.nom LIKE '"+nom.replaceAll("'", "''")+"' LIMIT 1;");
			if(curseur.next()){
				int id_reg= curseur.getInt("r.id");
				String code_reg= curseur.getString("r.code");
				String nom_reg= curseur.getString("r.nom");
				
				int id_dpt= curseur.getInt("d.id");
				String code_dpt= curseur.getString("d.code");
				
				int id_ville= curseur.getInt("v.id");
				String code_ville= curseur.getString("v.code");
				String nom_ville= curseur.getString("v.nom");
				int pop_ville= curseur.getInt("v.population");
				
				Region region= new Region(id_reg, code_reg, nom_reg);
				Departement dpt= new Departement(id_dpt, code_dpt, "", region);
				ville= new Ville(id_ville, code_ville, nom_ville, pop_ville, dpt);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				curseur.close();
				monStatement.close();
				maConnexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return ville;
	}
	
	@Override
	public List<Ville> getByDepartement(String codeDepartement) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement= null;
		ResultSet curseur= null;
		List<Ville> villes= new ArrayList<Ville>();
		Region region= null;
		Departement dpt= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT * FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id) WHERE d.code LIKE '"+codeDepartement.replaceAll("'", "''")+"';");
			while(curseur.next()){
				int id_reg= curseur.getInt("r.id");
				String code_reg= curseur.getString("r.code");
				String nom_reg= curseur.getString("r.nom");
				
				int id_dpt= curseur.getInt("d.id");
				String code_dpt= curseur.getString("d.code");
				
				int id_ville= curseur.getInt("v.id");
				String code_ville= curseur.getString("v.code");
				String nom_ville= curseur.getString("v.nom");
				int pop_ville= curseur.getInt("v.population");
				
				if(region == null){
					region = new Region(id_reg, code_reg, nom_reg);
					dpt = new Departement(id_dpt, code_dpt, "", region);
				}
				villes.add(new Ville(id_ville, code_ville, nom_ville, pop_ville, dpt));
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				curseur.close();
				monStatement.close();
				maConnexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return villes;
	}

	@Override
	public void insert(Ville ville) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(ville== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			monStatement.executeUpdate("INSERT INTO villes(code,nom,population,id_dpt) VALUES('"+ville.getCode()+
					"','"+ville.getNom().replaceAll("'", "''")+
					"','"+ville.getPopulation()+
					"','"+ville.getDepartement().getId()+"');");
			
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			try {
				maConnexion.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage());
			}
		} finally {
			try {
				monStatement.close();
				maConnexion.commit();
				maConnexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	@Override
	public int update(Ville ville) {
		int nbLigne= 0;
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(ville== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			nbLigne= monStatement.executeUpdate("UPDATE villes SET nom = '"+ville.getNom().replaceAll("'", "''")+
					"', code = '"+ville.getCode().replaceAll("'", "''")+
					"', population = '"+ville.getPopulation()+
					"', id_dpt = '"+ville.getDepartement().getId()+
					"' WHERE id = '"+ville.getId()+"' ;");
			
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			try {
				maConnexion.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage());
			}
		} finally {
			try {
				monStatement.close();
				maConnexion.commit();
				maConnexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return nbLigne;
	}

	@Override
	public boolean delete(Ville ville) {
		int nbLigne= 0;
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(ville== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			nbLigne= monStatement.executeUpdate("DELETE FROM villes WHERE id = '"+ville.getId()+"';");
			
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			try {
				maConnexion.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage());
			}
		} finally {
			try {
				monStatement.close();
				maConnexion.commit();
				maConnexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return nbLigne == 0;
	}

}
