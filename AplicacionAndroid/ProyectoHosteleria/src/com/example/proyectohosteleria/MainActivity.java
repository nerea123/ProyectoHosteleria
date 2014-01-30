package com.example.proyectohosteleria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	
	static class ViewHolder{
        TextView mesa;
        TextView nombre;   
}
	
		
	    private Thread hilo;
		Adaptador adapter;
	
		private ArrayList<Pedidos> datos=new ArrayList<Pedidos>();

		private ProgressDialog pDialog;

		private static String ip="192.168.1.134";
		// objeto JSON Parser
		JSONParser jParser = new JSONParser();

		ArrayList<HashMap<String, String>> productsList;

		// url donde esta get_pedidos y delete_pedido 192.168.1.129
		private static String url_all_products = "http://"+ip+"/PHP/get_pedidos.php";
		private static String delete_product = "http://"+ip+"/PHP/delete_pedido.php";
	
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_PRODUCTS = "products";
		private static final String TAG_IDL = "IDL";
		private static final String TAG_IDM = "IDM";
		private static final String TAG_DESC = "DESCRIPCION";
		private static final String TAG_CANTIDAD = "CANTIDAD";
		
		private String idl;
		
		JSONArray products = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ServicioRed.getActivity(this);
		ip= ServicioRed.getIp();
		
		System.out.println(ip);
		adapter=new Adaptador(this);
		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		if(!ip.equals(""))
			new LoadAllProducts().execute();
		ListView lista=getListView();
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				idl=String.valueOf(datos.get(arg2).getIdt());
				 creaDialogo();
			}
		});
		
		hilo=new Thread(new ServicioRed());
		hilo.start();
		
		
		
	}
	
	public void refresh(){
		Intent i = new Intent(MainActivity.this,MainActivity.class);
		startActivity(i);
		finish();
	}
	
	private void creaDialogo(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		 
		dialog.setMessage("¿Eliminar?");
		dialog.setCancelable(false);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
		 
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
			  if(!ip.equals(""))
				  new DeleteProduct().execute();
			  
		  }
		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
		 
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		      dialog.cancel();
		   }
		});
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_settings:
				ServicioRed.setEjecucion(false);
				finish();
				break;
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		/*if (resultCode == 100) {
			// if result code 100 is received 
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}*/
		ip= ServicioRed.getIp();

	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			
			//ver los productos en el log
			Log.d("All Products: ", json.toString());

			try {
				// comprobar k no hay errores
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						int idl = c.getInt(TAG_IDL);
						
						int idm = c.getInt(TAG_IDM);
					
						String name = c.getString(TAG_DESC);
						
						int cantidad = c.getInt(TAG_CANTIDAD);
						
						
						datos.add(new Pedidos(idl,idm,cantidad,name));
						
					

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_IDL, String.valueOf(idl));
						map.put(TAG_DESC, name);
						//map.put(TAG_PRE, precio);

						// adding HashList to ArrayList
						productsList.add(map);
					}
				} else {
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					
					// updating listview
					setListAdapter(adapter);
				}
			});

		}
		

	}
	
	 class Adaptador extends ArrayAdapter{

         Activity context;
         @SuppressWarnings("unchecked")
         public Adaptador(Activity context) {
                 super(context, R.layout.list_item,datos);
                 this.context=context;
                 
         }
         
         
         public View getView(int posicion,View convertView,ViewGroup parent){
                 View item=convertView;
                 ViewHolder holder;
                 
                         LayoutInflater inflater=context.getLayoutInflater();
                         item=inflater.inflate(R.layout.list_item,null);
                         
                         holder=new ViewHolder();
                     
                         holder.mesa=(TextView)item.findViewById(R.id.mesa);
                         holder.nombre=(TextView)item.findViewById(R.id.name);
                       
                         item.setTag(holder);
                         
                
                 
                 holder.mesa.setText("Mesa: "+String.valueOf(datos.get(posicion).getIdm()));                 
                 holder.nombre.setText("Mesa: "+String.valueOf(datos.get(posicion).getIdm())+"\n"+
                		 String.valueOf(datos.get(posicion).getCantidad())+" x "+datos.get(posicion).getDescripcion());
                 
                 
                 return item;
         }
         
 }

	 class DeleteProduct extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(MainActivity.this);
				pDialog.setMessage("Deleting Product...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Deleting product
			 * */
			protected String doInBackground(String... args) {

				JSONParser jsonParser = new JSONParser();
				// Check for success tag
				int success;
				try {
					// Building Parameters
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("IDL", idl));

					// getting product details by making HTTP request
					JSONObject json = jsonParser.makeHttpRequest(
							delete_product , "POST", params);
					System.out.println(json.toString());
					// check your log for json response
					Log.d("Delete Product", json.toString());
					
					// json success tag
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						// product successfully deleted
						// notify previous activity by sending code 100
						Intent i = getIntent();
						// send result code 100 to notify about product deletion
						setResult(100, i);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog once product deleted
				pDialog.dismiss();
				refresh();

			}

		}
	 
	 //finish();

}
