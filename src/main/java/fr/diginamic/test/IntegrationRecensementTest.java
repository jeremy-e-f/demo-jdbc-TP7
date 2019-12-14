package fr.diginamic.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.jdbc.ConnectionJDBC;
import fr.diginamic.recensement.IntegrationRecensement;

public class IntegrationRecensementTest {
	
	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	/**
	 * Test de l'int�gration des donn�es dans la Base de Donn�es
	 */
	@Test
	public void renseignerBD(){
		Long debut= System.currentTimeMillis();
		IntegrationRecensement.renseignerBD("D:/Work/Programmation/JAVA/java-poo-j6/src/main/resources/recensement.csv");
		Long fin= System.currentTimeMillis();
		System.out.println("L'op�ration d'int�gration s'est achev� avec succ�s en "+(fin-debut)+" ms.");
		
		int nb_villes= 0;
		int nb_departements= 0;
		int nb_regions= 0;
		
		/** On v�rifie que toutes les donn�es ont bien �t� inscrites en base */
		Connection connexion= ConnectionJDBC.getInstance();
		Statement req= null;
		try {
			req=  connexion.createStatement();
			ResultSet res= null;
		
			/** On extrait le nombre de villes inscrites */
			res= req.executeQuery("SELECT count(*) as nb FROM villes;");
			if(res.next()){
				nb_villes= res.getInt("nb");
			}
			
			/** On extrait le nombre de d�partements inscrits */
			res= req.executeQuery("SELECT count(*) as nb FROM departements;");
			if(res.next()){
				nb_departements= res.getInt("nb");
			}
			
			/** On extrait le nombre de r�gions inscrites */
			res= req.executeQuery("SELECT count(*) as nb FROM regions;");
			if(res.next()){
				nb_regions= res.getInt("nb");
			}
			
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try {
				req.close();
				connexion.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
				throw new RuntimeException("L'initialisation de la base de donn�es a �chou�.");
			}
		}
		
		assert nb_villes == 35382;
		assert nb_departements == 100;
		assert nb_regions == 17;
		
	}
	
	
}
