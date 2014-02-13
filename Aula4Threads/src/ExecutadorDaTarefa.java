import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ExecutadorDaTarefa extends ActionEvent implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ActionListener> ouvintes = new ArrayList<ActionListener>();
	
	private String textoAtual;
	
	private Long parametro;
	
	public ExecutadorDaTarefa() {
		super("", 1, "Executar o bolo");
	}
	
	public ExecutadorDaTarefa(Long parametro) {
		super("", 1, "Executar o bolo");
		this.parametro = parametro;
	}
	
	
	
	public void addActionListener(ActionListener actionListener){
		this.ouvintes.add(actionListener);
	}
	
	@Override
	public void run() {

		for(int i = 0 ; i < parametro ; i++){	
		
			textoAtual = "Estou no passo "+i;
			notifyActionListeners();
		}
		
	}
	
	public void notifyActionListeners(){
		for (ActionListener actionListener : ouvintes) {
			actionListener.actionPerformed(this);
		}
	}

	public String getTextoAtual() {
		return textoAtual;
	}
	
	public void setTextoAtual(String textoAtual) {
		this.textoAtual = textoAtual;
	}
	
}