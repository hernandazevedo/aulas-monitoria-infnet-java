import java.util.Observable;
import java.util.Observer;


public class TesteThread implements Observer{

	public TesteThread() {
		//Executando um trecho
		ClasseImplementandoRunnable implementacaoDaTarefa = new ClasseImplementandoRunnable(50L);
		implementacaoDaTarefa.addObserver(this);
		Thread t = new Thread(implementacaoDaTarefa);
		t.start();
				
		
		System.out.println("Estou em uma reuniao");
				
		//Quero continar abaixo em paralelo
		System.out.println("Acabou a reunião");
	}
	
	public static void main(String[] args) {
		
		new TesteThread();
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		String texto = (String)arg;
		
		System.out.println("Chefe verificando a tarefa, Empregado diz:"+texto);
	}
	
}
