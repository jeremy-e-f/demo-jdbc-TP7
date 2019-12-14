package fr.diginamic.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.dao.RegionDao;
import fr.diginamic.jdbc.ConnectionJDBC;
import fr.diginamic.recensement.entites.EnsemblePop;
import fr.diginamic.recensement.entites.Region;

public class RegionDaoJdbc implements RegionDao, EnsemblePop {

	private static final Logger LOG = LoggerFactory.getLogger("");

	@Override
	public List<Region> extraire() {
		Connection maConnexion= ConnectionJDBC.getInstance();
		ArrayList<Region> listeRegions= new ArrayList<Region>();
		Statement monStatement= null;
		ResultSet curseur= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT *, SUM(v.population) as pop_reg FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id) GROUP BY r.id");
			while(curseur.next()){
				int id_reg= curseur.getInt("id");
				String code_reg= curseur.getString("code");
				String nom_reg= curseur.getString("nom");
				int pop_reg= curseur.getInt("pop_reg");
				
				Region region= new Region(id_reg, code_reg, nom_reg, pop_reg);
				listeRegions.add(region);
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
		return listeRegions;
	}
	
	@Override
	public Region getByCode(String code) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement= null;
		ResultSet curseur= null;
		Region region= null;
		
		try {
			monStatement = maConnexion.createStatement();
			curseur= monStatement.executeQuery("SELECT *, SUM(v.population) as pop_reg FROM villes v JOIN departements d ON(v.id_dpt= d.id) JOIN regions r ON(d.id_reg= r.id) WHERE r.code LIKE '"+code.replaceAll("'", "''")+"' LIMIT 1;");
			if(curseur.next()){
				int id_reg= curseur.getInt("r.id");
				String code_reg= curseur.getString("r.code");
				String nom_reg= curseur.getString("r.nom");
				int pop_reg= curseur.getInt("pop_reg");
				
				region= new Region(id_reg, code_reg, nom_reg, pop_reg);
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
		return region;
	}

	@Override
	public void insert(Region region) {
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(region== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			monStatement.executeUpdate("INSERT INTO regions(code,nom) VALUES('"+region.getCode()+
					"','"+region.getNom().replaceAll("'", "''")+"');");
			
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
	public int update(Region region) {
		int nbLigne= 0;
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(region== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			nbLigne= monStatement.executeUpdate("UPDATE regions SET nom = '"+region.getNom().replaceAll("'", "''")+
					"', code = '"+region.getCode().replaceAll("'", "''")+"' WHERE id = '"+region.getId()+"';");
			
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
	public boolean delete(Region region) {
		int nbLigne= 0;
		Connection maConnexion= ConnectionJDBC.getInstance();
		Statement monStatement = null;
		try {
			if(region== null){
				throw new SQLException("Valeur nulle!");
			}
			
			monStatement = maConnexion.createStatement();
			nbLigne= monStatement.executeUpdate("DELETE FROM regions WHERE id = '"+region.getId()+"';");
			
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
