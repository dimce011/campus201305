package paket;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controller")
@SessionScoped

public class Controller {
	
	  public Controller() {
		   
		  }
	
	public BeanObrada getBean() {
		return bean;
	}

	public void setBean(BeanObrada bean) {
		this.bean = bean;
	}

	private BeanObrada bean;
	
	public String getString(String node){
		//DocumentNode dNode = (DocumentNode) node.getData();
		//String s = dNode.getTitle();
		System.out.println("Ispis noda" +node);
		return node;
	}
	
	public String obradaOpcije1(){
		
		// data-model
		System.out.println("Ulaz");
				Map<String, Object> input = new HashMap<String, Object>();
				bean = new BeanObrada("blblbl","090390ff3", "imeeee", "");
				input.put("telefon", bean.getTelefon());
				input.put("adresa", bean.getAdresa());
				input.put("firma", bean.getFirma());
				
				
				FreeMarker fm = new FreeMarker();
				System.out.println("poziv proces");
				String s = fm.process(input);
				bean.setHtml(s);
				System.out.println("Zavsen proces");
				String opcija1 = "opcija1";
		return opcija1;
	}
	
	public String obradaOpcije2(){
		return "tree";
	}
	
	String webServiceRestString;
	
	public String obradaOpcije3(){
		RestClient<String> rs = new RestClient<String>();
		Map<String, String> params = new HashMap<String, String>();
		params = null;
		System.out.println("proso1");
		
		webServiceRestString = (String)rs.getRequest("http://localhost:8080/helprepo/testJSON", params, String.class);
		
		System.out.println("proso2");
		/*if(webServiceRestString.equals("Restful"))
				return "strana3";*/
		return "tree"; 
	}
	
	public String obradaOpcijeMPM(){
		return "treeBeanCvor";
	}
	


}
