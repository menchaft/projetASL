package org.IEMProjectEJBModule.ejbs;

//import java.util.ArrayList;
import java.util.List;

import javax.ejb.*;
import javax.persistence.*;

import org.IEMProjectEJBModule.beans.Message;
import org.IEMProjectEJBModule.beans.Personne;

/**
 * Session Bean implementation class MonEjbBean
 */
@Stateful
@LocalBean
public class MonEjbBean {

 	//private List<Personne> passe=new ArrayList<Personne>();
	
 	@PersistenceContext(name="monUnite")
 	EntityManager em;
 	
 	
    /**
     * Default constructor. 
     */
 	
    public MonEjbBean() {
    }
    
    public String hello(){
    	return "Bonjour !";
    }

	public Personne ajoutPersonne(String nom, String prenom, String motDePasse){
		Query q=em.createQuery("From Personne p where nom=?1");
		q.setParameter(1,nom);
		if (q.getResultList().size() != 0){
			return null;
		}
		else{
			Personne pers=new Personne(nom, prenom, motDePasse);
			//passe.add(pers);
			em.persist(pers);
			
			//for (Personne p:passes){
			//	System.out.println(p.getNom());
			//}
			
			return pers;
		}
	}
	
	public Personne authentifierPersonne(String nom, String motDePasse){
		//Le passage en parametre evite les injections SQL
		Query q = em.createQuery("From Personne p where nom=?1 and motDePasse=?2");
		q.setParameter(1, nom);
		q.setParameter(2, motDePasse);
		try{
			return (Personne)q.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Personne> toutLeMonde(int id){
		Query q = em.createQuery("from Personne p where id not in ?1");
		q.setParameter(1, id);
		return q.getResultList();
		//return passe;
	}
	public void recommande(int id){
		Personne p=em.find(Personne.class, id);
		p.setRecommandation(p.getRecommandation()+1);
		
	/*	for(Personne p : passe){
			if(p.getId()==id){
				p.setRecommandation(p.getRecommandation()+1);
				break;
			}
		}*/
	}
	
	public void envoiMessage(int fromId, int toId, String txt){
		Personne emetteur=em.find(Personne.class, fromId);
		Personne destinataire=em.find(Personne.class, toId);
		
		Message m =new Message();
		m.setEmetteur(emetteur);
		m.setDestinataire(destinataire);
		m.setContenu(txt);
		em.persist(m);
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> messagesRecus(int id){
		Query q = em.createQuery("from Message m where m.destinataire.id=?1");
		q.setParameter(1, id);
		return q.getResultList();
	}
}
