package by.giava.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.model.Father;
import by.giava.repository.FatherRepository;

@ViewScoped
@ManagedBean
public class ComponentBeanTest implements Serializable {

	private static final String VIEW = "/viewFather.xhtml";
	public static final String LIST = "/listFathers.xhtml";
	private static final String MOD = "/addModFather.xhtml";

//	@Inject
//	private Conversation conversation;

	private boolean buttonRendered = true;
	private String style;
	
	@Inject
	FatherRepository fatherRepository;

	private Father father;
	private DataModel<Father> model;

	public ComponentBeanTest() {
	}
	
//	public void beginConversation() {
//		System.out.println("START CONVERSATION");
//		conversation.begin();
//	}
//
//	public String startConversation() {
//		System.out.println("START CONVERSATION");
//		conversation.begin();
//		return LIST;
//	}
//
//	public String stopConversation() {
//		System.out.println("STOP CONVERSATION");
//		conversation.end();
//		return LIST;
//	}

	public Father getFather() {
		return father;
	}

	public void setFather(Father father) {
		this.father = father;
	}

	public DataModel<Father> getModel() {
		if (model == null) {
			List<Father> list = fatherRepository.getList();
			this.model = new ListDataModel<Father>(list);
		}
		return model;
	}

	public void setModel(DataModel<Father> model) {
		this.model = model;
	}

	public String view() {
		this.father = getModel().getRowData();
		return VIEW;
	}

	public String modify() {
		this.father = getModel().getRowData();
		return MOD;
	}

	public String add() {
		this.father = new Father();
		return MOD;
	}
	
	public void testeMetodo(){
		
		System.out.println("ok testeMetodo");
	}

	public void testeMetodo(String parametro){
		
		System.out.println("ok testeMetodo com parametro "+parametro);
	}

	
	public String save() {
		fatherRepository.save(father);
		this.model = null;
		return LIST;
	}

	public String update() {
		fatherRepository.merge(father);
		this.model = null;
		return LIST;
	}

	public String delete() {
		fatherRepository.delete(father);
		this.model = null;
		return LIST;
	}

	public boolean isButtonRendered() {
		return buttonRendered;
	}

	public void setButtonRendered(boolean buttonRendered) {
		this.buttonRendered = buttonRendered;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
}
