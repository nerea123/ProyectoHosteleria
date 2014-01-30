package com.example.proyectohosteleria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.ListActivity;

public class ServicioRed implements Runnable{

	private ServerSocket socket;
	private static String ip="";
	private static volatile boolean ejecucion = true;
	
	static MainActivity act;
	@Override
	public void run() {
		
		//BufferedReader reader;
		try {
			socket=new ServerSocket(1234);
			while(ejecucion){
				Socket socketAccept =socket.accept();
				
				//reader = new BufferedReader (new InputStreamReader (socketAccept.getInputStream()));
				ObjectInputStream reader=new ObjectInputStream(socketAccept.getInputStream());
				try {
					setIp(reader.readObject().toString());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(ip);
				act.refresh();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public static void getActivity(MainActivity act){
		ServicioRed.act=act;
	}
	public boolean isEjecucion() {
		return ejecucion;
	}
	public static void setEjecucion(boolean ejecucion) {
		ServicioRed.ejecucion = ejecucion;
	}

}
