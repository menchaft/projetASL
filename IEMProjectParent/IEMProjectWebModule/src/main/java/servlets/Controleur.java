package servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.IEMProjectEJBModule.beans.*;
import org.IEMProjectEJBModule.ejbs.*;

@SuppressWarnings("unused")
@WebServlet("/Controleur")
public class Controleur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int compteur=1;
	//private List<String> passe=new ArrayList<String>();
 	
 	private MonEjbBean jojo=null ; 	
 	
	
    public Controleur() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doIt(request, response);
	}
	
	protected void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//request pour ne pas perdre la liste
		jojo = (MonEjbBean) request.getSession().getAttribute("jojo");
		
		if(jojo==null){
		 	try{
		 		Context ctx = new InitialContext();
		 		jojo=(MonEjbBean) ctx.lookup("java:app/MonPremierProjetEJB/MonEjbBean");
		 		request.getSession().setAttribute("jojo",jojo);
		 	} catch( NamingException e){
		 		e.printStackTrace();
		 	}	
		}
		
		Logger.getAnonymousLogger().severe(jojo.hello());
		
		String todo =request.getParameter("todo");
		
		if( todo !=null){
			switch(todo){
			case "nouveau":
				String nom = request.getParameter("nom");
				String prenom = request.getParameter("prenom");
				String mdp = request.getParameter("motDePasse");
				
				if ((nom!=null && nom.length()>0) || (prenom!=null && prenom.length()>0)){
					Logger.getAnonymousLogger().severe("Identité : " + nom + " "+ prenom);
					Personne p = jojo.ajoutPersonne(nom, prenom,mdp);
					if(p!=null){
						request.getSession().setAttribute("current", p.getId());
						affichage(request, response);
					}
					else{
						request.getRequestDispatcher("accueil.jsp").forward(request, response);
					}
				}
				break;
			case "login" :
				nom = request.getParameter("nom");
				mdp = request.getParameter("motDePasse");
				Personne p = jojo.authentifierPersonne(nom, mdp);
				if(p!=null){
					request.getSession().setAttribute("current", p.getId());
					affichage(request, response);
				}
				else{
					request.getRequestDispatcher("accueil.jsp").forward(request, response);
				}
				break;
			case "recommande":
				int id = Integer.parseInt(request.getParameter("id"));
				jojo.recommande(id);
				affichage(request, response);
				break;
			case "message":
				String contenu = request.getParameter("contenu");
				int fromId = (Integer) request.getSession().getAttribute("current");
				int toId = Integer.parseInt(request.getParameter("id"));
				jojo.envoiMessage(fromId, toId, contenu);
				affichage(request, response);
			case "deconnexion" :
				request.getSession().setAttribute("current", null);
				request.getRequestDispatcher("accueil.jsp").forward(request, response);
			default :
				break;
			}
		}else{
			request.getRequestDispatcher("accueil.jsp").forward(request, response);
		}	
	}
	
	protected void affichage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("cpt", compteur);
		compteur++;
		int id = (Integer) request.getSession().getAttribute("current");
		if (jojo.toutLeMonde(id).size()>0){
			request.setAttribute("noms", jojo.toutLeMonde(id));
		}
		request.setAttribute("messages", jojo.messagesRecus(id));
		request.getRequestDispatcher("mapage.jsp").forward(request, response);
	}

}
