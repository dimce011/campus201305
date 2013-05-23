package com.bluelotussoftware.example.jsf;

import java.io.Serializable;

public class Podatak implements Serializable {

	private String ime;
	private String link;

	public Podatak() {

	}

	public Podatak(String ime, String link) {
		this.ime = ime;
		this.link = link;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
