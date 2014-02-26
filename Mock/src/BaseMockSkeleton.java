

import java.net.URL;
import java.util.logging.Logger;

/**
 * Classe usada para padronizar a implementacao padrao para Skeletons que representam mocks. 
 * @author hazevedo
 *
 */
public abstract class BaseMockSkeleton {
	
	private static final String EMPTY_STRING = "";
	private static final String LINUX = "LINUX";
	private static final String FILE_LINUX = "file:";
	private static final String FILE_WINDOWS = "file:/";
	private static final String WINDOWS = "WINDOWS";
	private static final String OS_NAME = "os.name";
	private Logger log = Logger.getLogger(BaseMockSkeleton.class.getName());
	
	/**
	 * Devolve o caminho absoluto do arquivo no disco. Considerando o sistema operacional em que se encontra.
	 * @param fileName
	 * @return
	 */
	protected String retrieveFilePath(String fileName){
		
    	log.info("Retrieving filepath: "+fileName);
    	URL xmlFileCertificarAyncOut = getClass().getClassLoader().getResource(fileName);
    	log.info("xmlFileCertificarAyncOut: "+xmlFileCertificarAyncOut);
    	
    	String filePath = xmlFileCertificarAyncOut.toString();
    	log.info("filePath: "+filePath);
    	
    	log.info("Systema atual: "+System.getProperties().get(OS_NAME).toString());
    	//Verifica se o sistema é Windows
    	if(verificaSistemaAtual(WINDOWS)){
    		filePath = xmlFileCertificarAyncOut.toString().replace(FILE_WINDOWS, EMPTY_STRING);
    	}
    	//Verifica se o sistema é Linux
    	else if(verificaSistemaAtual(LINUX)){
    		filePath = xmlFileCertificarAyncOut.toString().replace(FILE_LINUX, EMPTY_STRING);
    	}else{
    		//TODO implementar outros sistemas mais tarde.
    		log.info("Nao encontrou o sistema operacional adequado.Considerando Windows");
        	filePath = xmlFileCertificarAyncOut.toString().replace(FILE_WINDOWS, EMPTY_STRING);
    	}
    	
    	log.info("filePath: "+filePath);
    	return filePath;
    }
	
	/**
	 * Retorna true se o sistema atual contiver o parametro passado.
	 * @param sistema
	 * @return
	 */
	private Boolean verificaSistemaAtual(String sistema){
		return System.getProperties().get(OS_NAME).toString().toUpperCase().contains(sistema);
	}
}
