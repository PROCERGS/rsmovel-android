package br.com.procergs.model;

import java.io.Serializable;

public class Destaque implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titulo;
	private String url;

	public Destaque() {
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
