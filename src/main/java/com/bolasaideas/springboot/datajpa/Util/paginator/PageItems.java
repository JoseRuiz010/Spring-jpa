package com.bolasaideas.springboot.datajpa.Util.paginator;

public class PageItems {

	private int numero;
	private boolean actual;

	public PageItems(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isActual() {
		return actual;
	}
}
