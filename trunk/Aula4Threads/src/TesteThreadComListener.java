import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TesteThreadComListener implements ActionListener{

	Long parametro = 200L;
	
	public TesteThreadComListener() {

		
		ExecutadorDaTarefa tarefa = new ExecutadorDaTarefa(100L);
		tarefa.addActionListener(this);
		Thread t = new Thread(tarefa);
		t.start();
		
	
	}
	
	
	
	public static void main(String[] args) {
		new TesteThreadComListener();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		ExecutadorDaTarefa executador = (ExecutadorDaTarefa)e;
		
		System.out.println("Chefe verificando a tarefa, Empregado diz: "+executador.getTextoAtual());
		
	}
}
