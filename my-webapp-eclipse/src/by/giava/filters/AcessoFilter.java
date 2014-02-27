package by.giava.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AcessoFilter implements Filter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		if(request.getRequestURI().endsWith("/faces/login.xhtml")){
			chain.doFilter(req, res);
			return;
		}
		
		String caminhoInicialDaURL = request.getContextPath();
		HttpSession session = request.getSession();
		
		/*
		 * Se usuario estiver na sessao, esta logado - > Continuar o request 
		 */
		
		
		
		if(session.getAttribute("user") != null){
			//Usuario esta logado 
			chain.doFilter(req, res);//Continuando o request
			return;
		}else{
			response.sendRedirect(caminhoInicialDaURL+"/faces/login.xhtml");
			return;
		}
		

		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
