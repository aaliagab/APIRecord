package com.api.record.entities;

public class Jugador {
	private String nombre;
	private int record;
	
	public Jugador() {
		this.nombre = "";
		this.record = 0;
	}
	public Jugador(String nombre, int record) {
		super();
		this.nombre = nombre;
		this.record = record;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	
}
