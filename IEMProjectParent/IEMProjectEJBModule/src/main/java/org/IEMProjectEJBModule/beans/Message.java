package org.IEMProjectEJBModule.beans;

import javax.persistence.*;
@Entity
public class Message {

	@Id @GeneratedValue
	private int msgId;
	@ManyToOne
	private Personne emetteur;
	@ManyToOne
	private Personne destinataire;
	private String contenu;
	
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public Personne getEmetteur() {
		return emetteur;
	}
	public void setEmetteur(Personne emetteur) {
		this.emetteur = emetteur;
	}
	public Personne getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(Personne destinataire) {
		this.destinataire = destinataire;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

}
