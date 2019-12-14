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

import fr.diginamic.dao.DepartementDao;
import fr.diginamic.jdbc.ConnectionJDBC;
import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.EnsemblePop;
import fr.diginamic.recensement.entites.Region;

public class DepartementDaoJdbc implements DepartementDao, EnsemblePop {

	private static final Logger LOG = LoggerFactory.getLogger("");

	@Override
	public List<Departement> extraire() {
		Connection maConnexion= ConnectionJDBC.getInstance();
		ArrayList<Departement> listeDepartements= new ArrayList<Departement>();
		HashMap<Integer,Region> listeRegions= new HashMap<Integer,Region>();
		Statement monStatement= null;
		ResultSet curseur= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT *, SUM(v.population) as pop_dpt FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id) GROUP BY d.id");
			while(curseur.next()){
				int id_reg= curseur.getInt("r.id");
				String code_reg= curseur.getString("r.code");
				String nom_reg= curseur.getString("r.nom");
				
				int id_dpt= curseur.getInt("d.id");
				int pop_dpt= curseur.getInt("pop_dpt");
				String code_dpt= curseur.getString("d.code");
				
				Region region= null;
				if(!listeRegions.containsKey(id_reg)){
					region= new Region(id_reg, code_reg, nom_reg);
					listeRegions.put(id_reg, region);
				}else{	
					region= listeRegions.get(id_reg); 
				}
				
				Departement departement= new Departement(id_dpt, code_dpt, "", pop_dpt, region);
				listeDepartements.add(departement);
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
		return listeDepartements;
	}
	
	@Override
	public Departement getByCode(String code) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement= null;
		ResultSet curseur= null;
		Departement dpt= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT *, SUM(v.population) as pop_dpt FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id) WHERE d.code LIKE '"+code.replaceAll("'", "''")+"' GROUP BY d.id;");
			if(curseur.next()){
				int id_reg= curseur.getInt("r.id");
				String code_reg= curseur.getString("r.code");
				String nom_reg= curseur.getString("r.nom");
				
				int id_dpt= curseur.getInt("d.id");
				int pop_dpt= curseur.getInt("pop_dpt");
				String code_dpt= curseur.getString("d.code");
				
				Region region= new Region(id_reg, code_reg, nom_reg);
				dpt= new Departement(id_dpt, code_dpt, "", pop_dpt, region);
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
		return dpt;
	}

	@Override
	public void insert(Departement departement) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(departement== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			monStatement.executeUpdate("INSERT INTO departements(code,nom,id_reg) VALUES('"+departement.getCode()+
					"','"+departement.getNom().replaceAll("'", "''")+
					"','"+departement.getRegion().getCode().replaceAll("'", "''")+"');");
			
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
	public int update(Departement departement) {
		int nbLigne= 0;
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(departement== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			nbLigne= monStatement.executeUpdate("UPDATE departements SET nom = '"+departement.getNom().replaceAll("'", "''")+
					"', code = '"+departement.getCode().replaceAll("'", "''")+
					"', id_reg = '"+departement.getRegion().getId()+
					"' WHERE id = '"+departement.getId()+"';");
			
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
	public boolean delete(Departement departement) {
		int nbLigne= 0;
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(departement== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			nbLigne= monStatement.executeUpdate("DELETE FROM departements WHERE id = "+departement.getId()+";");
			
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

	@Override
	public int getPopulation() {
		// TODO Auto-generated method stub
		return 0;
	}
}
