package components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.commandbutton.CommandButtonRenderer;

@FacesRenderer(componentFamily = CustomComponent.COMPONENT_FAMILY, rendererType = CustomCommandButtonRenderer.RENDERER_TYPE)
public class CustomCommandButtonRenderer extends CommandButtonRenderer{

	public static final String RENDERER_TYPE = "components.CustomCommandButtonRenderer";
	
	public static final boolean POSSUI_IMAGEM = true;
	public static final String ICON_CLASS_DEFAULT = "sispagIconClass";
	public static final String ICON_DEFAULT = "sispagIcon";
	public static final String IMAGE_WIDTH = "23";
	
	
	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent arg1) throws IOException {
		// TODO Auto-generated method stub
		
		CustomComponent c = (CustomComponent)arg1;
		
		if(POSSUI_IMAGEM){
			adicionarImagem(c,facesContext);
		}
		
		super.encodeBegin(facesContext, c);
		
	}
	
	private void adicionarImagem(CustomComponent c, FacesContext facesContext) {
		
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
		String contextPath = request.getContextPath();
		
		c.setIcon(ICON_DEFAULT);
		
		if(c.getStyleClass() != null && !c.getStyleClass().isEmpty())
			c.setStyleClass(String.format("%s %s", c.getStyleClass(),ICON_CLASS_DEFAULT));
		else
			c.setStyleClass(ICON_CLASS_DEFAULT);
		
//		background: url('/my-webapp-eclipse/images/pdf.png') no-repeat top center background-size: contain;
		String imagemSize = IMAGE_WIDTH;
//		if(parametroImagem != null){
//		imagemSize = parametroImagem;	
//		}
		
		
		String styleImagem = String.format("background: url('%s%s') no-repeat center center; width: %spx;background-size:contain;", contextPath,"/images/pdf.png",imagemSize);
		if(c.getStyle() != null && !c.getStyle().isEmpty())
			c.setStyle(String.format("%s %s", c.getStyle(),styleImagem));
		else
			c.setStyle(styleImagem);
		
	}

	@Override
	public void encodeChildren(FacesContext arg0, UIComponent arg1) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(arg0, arg1);
	}
	
	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
	}
	
}
