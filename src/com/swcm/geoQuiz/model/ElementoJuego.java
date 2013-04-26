package com.swcm.geoQuiz.model;

public class ElementoJuego {
	private int ID;
	private String Ciudad;
	private String Pais;
	private int Capital;

	public ElementoJuego() {
		setID(0);
		setCiudad("");
		setPais("");
		setCapital(0);
	}

	public ElementoJuego(String Ciudad, String Pais, int Capital) {
		setID(0);
		setCiudad(Ciudad);
		setPais(Pais);
		setCapital(Capital);
	}

	public ElementoJuego(int ID, String Ciudad, String Pais, int Capital) {
		setID(ID);
		setCiudad(Ciudad);
		setPais(Pais);
		setCapital(Capital);
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the Ciudad
	 */
	public String getCiudad() {
		return this.Ciudad;
	}

	/**
	 * @param Ciudad
	 *            the Ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.Ciudad = ciudad;
	}

	/**
	 * @return the Pais
	 */
	public String getPais() {
		return this.Pais;
	}

	/**
	 * @param Pais
	 *            the Pais to set
	 */
	public void setPais(String pais) {
		this.Pais = pais;
	}

	/**
	 * @return the Capital
	 */
	public int getCapital() {
		return this.Capital;
	}

	/**
	 * @param Capital
	 *            the Capital to set
	 */
	public void setCapital(int capital) {
		this.Capital = capital;
	}

}