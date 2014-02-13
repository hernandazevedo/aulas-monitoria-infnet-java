import java.util.Observable;


public class ClasseImplementandoRunnable extends Observable implements Runnable {

	private Long parametro;
	public ClasseImplementandoRunnable() {
		this.parametro = 20L;
	}
	
	
	public ClasseImplementandoRunnable(Long parametro) {
		this.parametro = parametro;
	}
	
	@Override
	public void run() {

		for(int i=0 ; i<parametro ; i++){
			setChanged();
			notifyObservers("Estou no passo "+i);
		}
		
		
	}
		
	
	
}
