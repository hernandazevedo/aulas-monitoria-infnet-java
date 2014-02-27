package by.giava.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import by.giava.model.User;

@Named
@ConversationScoped
public class LoginConversation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject EntityManager em;
	
	@Inject
	private Conversation conversation;
	
	Logger logger = Logger.getLogger(LoginConversation.class);
	
	
	private User user = new User();
	
	@PostConstruct
	public void init(){
		if(conversation.isTransient()){
			conversation.begin();
		}
		
	}
	/**
	 * 1 - Verificar se o usuario passado existe no banco
	 * Colocar o usuario logado na session
	 * Se existir redirecionar para a tela inicial do sistema.
	 * @return
	 */
	public String login(){
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		
		try{
			User u = em.createQuery("select u from User u where u.user = :usuario and u.password = :senha",User.class)
			.setParameter("usuario", user.getUser()).setParameter("senha", user.getPassword()).getSingleResult();
			
			if(u != null){
				logger.info("Usuario encontrado");
				
				session.setAttribute("user", user);
				
				return "/index.xhtml";
				
			}
			
		}catch (NoResultException e) {
			logger.error("Usuario nao encontrado!!",e);			
		}
		catch (Exception e) {
			logger.error("Erro geral!!",e);
		}
		
		
		return "";//MESMA PAGINA
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
