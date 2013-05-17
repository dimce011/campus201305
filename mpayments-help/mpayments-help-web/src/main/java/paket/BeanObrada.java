package paket;

public class BeanObrada {
	
	
	
	public BeanObrada(String adresa, String telefon, String firma, String html) {
		super();
		this.adresa = adresa;
		this.telefon = telefon;
		this.firma = firma;
		this.html = html;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
	private String adresa;
	private String telefon;
	private String firma;
	private String html;
	

}
