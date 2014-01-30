package com.example.proyectohosteleria;

public class Pedidos {
	
	public Pedidos(int idl,int idm,int cantidad,	String descripcion){
		this.cantidad=cantidad;
		this.descripcion=descripcion;
		this.idm=idm;
		this.idl=idl;
			
	}
	int idl;
	public int getIdt() {
		return idl;
	}
	public void setIdt(int idl) {
		this.idl = idl;
	}
	public int getIdm() {
		return idm;
	}
	public void setIdm(int idm) {
		this.idm = idm;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	int idm;
	int cantidad;
	String descripcion;

}
