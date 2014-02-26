
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestMockService {

	private static final String INDICADOR_INNER_CLASS = "$";
	private static final String TEST_CASE = "testCase";
	private static final String TEST = "test";
	private static final String NAME = "name";
	private static final String SEPARADOR_PACOTE = ".";
	private static final String INDICADOR_ARRAY = "[]";
	private static final String DEFAUL_DATE_TIME_FORMAT = "yyyy-dd-MM HH:mm:ss";
	private static final String ACTIVE_TEST_CASE_NAME = "activeTestCaseName";
	private static String CLASSE = "classe";
	private static String METODO = "metodo";
	private static String VALOR = "valor";
	private static String ATRIBUTO_SIMPLES = "atributoSimples";
	private static String ATRIBUTO_COMPLEXO = "atributoComplexo";
	
	
	public static Object montaObjeto(String xmlFile) {
		Object o = null;
		String nomeCenarioAtivo = null;
		Node activeTestCase = null;
		try {
			File file = retriveFile(xmlFile);
			
			DOMManager parserManager = new DOMManager();
			Node nodeToMount = null;
			parserManager.parserFile(file);
			NodeList testNodeList = parserManager.getDocument().getElementsByTagName(TEST);
			//Procurando o nome do cenario ativo para este cenario de teste
			for (int i = 0; i < testNodeList.getLength(); i++) {
				Node node = testNodeList.item(i);
				if(node.getNodeType() != Node.ELEMENT_NODE){
					continue;
				}else{
					nomeCenarioAtivo = node.getAttributes().getNamedItem(ACTIVE_TEST_CASE_NAME).getNodeValue();
				}
				
			}
			
			NodeList testCases = parserManager.getDocument().getElementsByTagName(
					TEST_CASE);
			
			//Encontrando qual testCase vai ser usado para montar o objeto
			for (int i = 0; i < testCases.getLength(); i++) {
				Node node = testCases.item(i);
				if(node.getNodeType() != Node.ELEMENT_NODE){
					continue;
				}
				if(node.getAttributes().getNamedItem(NAME).getNodeValue().equals(nomeCenarioAtivo)){
					activeTestCase = node;
					break;
				}
			}
			//Encontrando o atributoComplexo que vai ser montado desse testCase.
			NodeList nodesActiveTest = activeTestCase.getChildNodes();
			for (int i = 0; i < nodesActiveTest.getLength() && nodeToMount == null; i++) {
				Node node = nodesActiveTest.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					nodeToMount = node;
					break;
				}
			}
			
			o =	mountObjectChilds(nodeToMount);
		}catch(NullPointerException npe){
			System.out
			.println("NAO FOI POSSIVEL ENCONTRAR O ARQUIVO DE CONFIGURACAO OU UMA DAS PROPRIEDADES DELE");
			npe.printStackTrace();
		} catch (DOMException e) {
			System.out.println("ERRO AO EXECUTAR O PARSER XML");
			e.printStackTrace();
		
		} catch (SecurityException e) {
			System.out.println("METODO ESPECIFICADO NAO PODE SER INVOCADO");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("ERRO AO INVOCAR O METODO ESPECIFICADO");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("NAO FOI POSSIVEL ENCONTRAR A CLASSE ESPECIFICADA");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("ERRO AO INSTANCIAR A CLASSE ESPECIFICADA");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("ERRO AO INSTANCIAR A CLASSE ESPECIFICADA");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("METODO ESPECIFICADO NAO ENCONTRADO");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.println("ERRO AO INVOCAR O METODO ESPECIFICADO");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("ERRO AO PARSEAR A DATA, VERIFIQUE O FORMATO yyyy-dd-MM HH:mm:ss");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}
		
	private static File retriveFile(String path) throws NullPointerException {
		return new File(path);
	}
	
	
	private static Object mountObjectChilds(Node root)
	throws org.w3c.dom.DOMException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, SecurityException,
	NoSuchMethodException, IllegalArgumentException,
	InvocationTargetException, ParseException {
	// Montar os objetos do Parametro
	/*
	 * Quando se faz loadClass de uma innerClass, esta nao é encontrada, pois
	 * o nome de uma innerClass fica com $ no ultimo ponto "." Exemplo
	 * br.com.activia.activiafs.ws.server.SetOSStub$SetOSIn
	 */
	String className = root.getAttributes().getNamedItem(CLASSE)
			.getNodeValue();
	
	Class<?> c = loadClass(className);
	Object obj = c.newInstance();
	
	NodeList rootChilds = root.getChildNodes();
	for (int idx = 1; idx <= rootChilds.getLength(); idx++) {
		Node child = rootChilds.item(idx);
	
		if (child == null || child.getNodeType() != Node.ELEMENT_NODE) {
			continue;
		}
	
		// Verificar se eh simple ou complex
		String childType = child.getNodeName();
	
		String childMethodName = child.getAttributes().getNamedItem(METODO)
				.getNodeValue();
		String childClassName = (child.getAttributes().getNamedItem(CLASSE) != null ? child
				.getAttributes().getNamedItem(CLASSE).getNodeValue()
				: String.class.getName());// opcional
		String childValue = (child.getAttributes().getNamedItem(VALOR) != null ? child
				.getAttributes().getNamedItem(VALOR).getNodeValue()
				: null);// opcional
	
		Object o = null;
	
		Class<?> childClass = loadClass(childClassName);
	
		if (childClassName.contains(INDICADOR_ARRAY)) {
			NodeList childNodes = child.getChildNodes();
			int arrayLength = 0;
			//Este for serve para pegar o tamanho real do array, somente os Elements representam itens do array.
			for (int i = 0; i < childNodes.getLength(); i++) {
				if(childNodes.item(i).getNodeType() != Node.ELEMENT_NODE){
					continue;
				}
				arrayLength++;
			}
			//Necessario para criar um array de int[] ao inves de Integer[]
			if(childClassName.contains(Integer.class.getName())){
			    childClass = int.class;
			}
			o = Array.newInstance(childClass, arrayLength);
			childClass = o.getClass();
			//O j aqui é apenas para os itens ficarem nos primeiros indices do array. Isso pode melhorar a performance.
			for (int i = 0,j=0; i < childNodes.getLength(); i++) {
				Node childFromChild = childNodes.item(i);
				if (childFromChild.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}
				Object arrayArg = null;
				if (childClassName.contains(String.class.getName())) {
					arrayArg = childFromChild.getAttributes().getNamedItem(
							VALOR).getNodeValue();
				}else if(childClassName.contains(Integer.class.getName())){
					//Evitando o autoboxing que iria prejudicar o tipo, e consequentemente dando erro.
					int primitiveInt = Integer.parseInt(childFromChild.getAttributes().getNamedItem(
							VALOR).getNodeValue());
					Array.set(o, j++, primitiveInt);
					//Evitando que a variavel arrayArg seja setada no array.Vai para o proximo valor int
					continue;
				}else if (childClassName.contains(Boolean.class.getName())) {
					arrayArg = Boolean.parseBoolean(childFromChild.getAttributes().getNamedItem(
							VALOR).getNodeValue());
				}
				else {
					arrayArg = mountObjectChilds(childFromChild);
				}
	
				Array.set(o, j++, arrayArg);
			}
	
		} else if (ATRIBUTO_SIMPLES.equalsIgnoreCase(childType)) {
			// TODO prever outros tipos primitivos
			if (childClassName.equals(Long.class.getName())) {
				childClass = long.class;
				o = Long.parseLong(childValue.toString());
			} else if (childClassName.equals(Integer.class.getName())) {
				childClass = int.class;
				o = Integer.parseInt(childValue.toString());
			} else if (childClassName.equals(Boolean.class.getName())) {
				childClass = boolean.class;
				o = Boolean.parseBoolean(childValue.toString());
			} else if (childClassName.equals(Date.class.getName())) {
				childClass = java.util.Date.class;
				// TODO: futuramente passar o pattern da data para arquivo
				// de propriedades e colocar o parse de data em um metodo
				SimpleDateFormat sdf = new SimpleDateFormat(
						DEFAUL_DATE_TIME_FORMAT);
				o = sdf.parse(childValue.toString());
			} else if (childClassName.equals(Calendar.class.getName())) {
				childClass = java.util.Calendar.class;
				SimpleDateFormat sdf = new SimpleDateFormat(
						DEFAUL_DATE_TIME_FORMAT);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(childValue.toString()));
				o = calendar;
			} else {
				o = childValue;
			}
		} else if (ATRIBUTO_COMPLEXO.equalsIgnoreCase(childType)) {
			// No caso de atributo complexo usar recursao
			o = mountObjectChilds(child);
		}
		
		//No caso de ser um metodo herdado de uma superclasse.
		try{
			Method m = c.getDeclaredMethod(childMethodName, childClass);
			m.invoke(obj, o);
		}catch(NoSuchMethodException e){
			Method m = c.getSuperclass().getDeclaredMethod(childMethodName, childClass);
			m.invoke(obj, o);
		}
	
	
	}
	
	return obj;
	}
	/**
	 * Metodo que retorna uma Class<?>, contemplando tambem o caso de ser uma
	 * innerClass
	 * 
	 * @param className
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static Class<?> loadClass(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Class<?> c = null;
		className = className.replace(INDICADOR_ARRAY, "");
		try {
			// OuterClass
			c = Class.forName(className);

		} catch (ClassNotFoundException cnfe) {
			// Caso seja uma innerClass
			c = Class.forName(
					replaceLastOccurence(className, SEPARADOR_PACOTE,
							INDICADOR_INNER_CLASS));
		}

		return c;
	}

	/**
	 * Metodo que faz replace na ultima ocorrencia de uma substring.
	 * 
	 * @author hazevedo
	 * @param word
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	private static String replaceLastOccurence(String word, String oldValue,
			String newValue) {
		return word.substring(0, word.lastIndexOf(oldValue)).concat(newValue)
				.concat(
						word.substring(word.lastIndexOf(oldValue) + 1, word
								.length()));
	}

}
