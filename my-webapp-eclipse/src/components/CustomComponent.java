package components;

import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.component.commandbutton.CommandButton;

@FacesComponent("components.CustomComponent")
public class CustomComponent extends UIComponentBase implements ActionListener {
 
    @Override
    public String getFamily() {        
        return "components.CustomComponent";
    }
 
    public CustomComponent() {
//    	UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
//    	root.subscribeToEvent(PreRenderViewEvent.class, this);
	}
    
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
		
		iniciarHtmlEstatico(context);
		
		if(!getFacesContext().isPostback()){
			adicionarConteudoDinamico();
		}
    }

	private void iniciarHtmlEstatico(FacesContext context) throws IOException {
		String style = (String) getAttributes().get("style");
		String styleClass = (String) getAttributes().get("styleClass");
		ResponseWriter writer = context.getResponseWriter();
        writer.write("<div id=\""+getClientId()+"\"");
        if(style != null)	            	
        writer.write(" style=\"" + style + "\"");
        
        if(styleClass != null)	            	
        writer.write(" class=\"" + styleClass + "\"");
        
        writer.write(" >");
	}

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
    	super.encodeEnd(context);
    	
	    	terminarHtmlEstatico(context);
    }

	private void terminarHtmlEstatico(FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.write("</div>");
	}
    
    @Override
    public void encodeChildren(FacesContext arg0) throws IOException {
    	// TODO Auto-generated method stub
    	super.encodeChildren(arg0);
    }
    
    
    
//	@Override
//	public boolean isListenerForSource(Object source) {
//		// TODO Auto-generated method stub
//		return source instanceof UIViewRoot;
//	}
//
//	@Override
//	public void processEvent(SystemEvent arg0) throws AbortProcessingException {
//		
//		if(!getFacesContext().isPostback()){
//			
//			
////			
//		}
//		
//	}
	
	
	private void adicionarConteudoDinamico(){
		
		 String value = (String) getAttributes().get("value");
		 String componenteId = (String) getAttributes().get("componenteId");
		 String update = (String) getAttributes().get("update");
//	        
	        if (value != null) {    
	        	CommandButton commandButton = new CommandButton();
	        	if(componenteId != null)
	        	commandButton.setId(componenteId);
	        	
	        	commandButton.setValue(value);
	        	
//	        	MethodExpression methodExpression = new MethodExpressionValueExpressionAdapter(getValueExpression("listenerAction"));
	        	commandButton.setActionExpression(createMethodExpression(getValueExpression("listenerAction").getExpressionString(), null));
//	        	commandButton.addActionListener(this);
	        	
	        	if(update != null)
	        	commandButton.setUpdate(update);
	        	
	            getChildren().add(commandButton);
	        }
	}
	
	public static MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createMethodExpression(
            facesContext.getELContext(), expression, returnType, parameterTypes);
    }

	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		
		System.out.println("ok");
	}
}