import com.pessoa.Pessoa;


public class TesteMain {
 public static void main(String[] args) {
	Pessoa p = (Pessoa)TestMockService.montaObjeto("conf/certificarOut.xml");
	System.out.println(p.getNome());
	
	for (int i = 0; i < p.getListaDeContatos().length; i++) {
		System.out.println("Imprimindo os contatos");
		Pessoa pessoa = p.getListaDeContatos()[i];
		
		System.out.println(pessoa.getNome());
	}
}
}
