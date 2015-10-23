package org.IEMProjectEJBModule.beans;

import javax.persistence.*;

@Entity
public class Personne {
	
	//private static int sequence=1;
	@Id @GeneratedValue
	private int id;
	private String nom;
	private String prenom;
	private String motDePasse;
	private int recommandation=0;

	public Personne (){
		super();
	}
	
	public Personne (String newNom, String newPrenom, String motDePasse){
		this();
		//this.id=sequence++;
		this.nom=newNom;
		this.prenom=newPrenom;
		this.motDePasse=motDePasse;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getRecommandation() {
		return recommandation;
	}

	public void setRecommandation(int recommandation) {
		this.recommandation = recommandation;
	}
	public int getId() {
		return id;
	}	
	public void setId(int id) {
		this.id=id;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}	
}
