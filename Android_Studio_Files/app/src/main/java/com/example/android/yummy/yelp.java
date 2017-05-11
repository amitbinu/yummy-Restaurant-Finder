package com.example.android.yummy;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.maps.model.PlacesSearchResponse;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;


public class yelp{
	private static retrofit2.Call<SearchResponse> call;
	private static String price = "";
	private static String phone = "";
	private static String name = "";
	private static String address = "";
	private static ArrayList<Business> busy = new ArrayList<Business>();
	public yelp(String name, String address) throws IOException{

		this.name = name;
		this.address = address;

		//	System.out.println((double)System.currentTimeMillis() - start );
		new hope().execute();
	}



	class hope extends AsyncTask<String, Void, String[]>{

		@Override
		protected String[] doInBackground(String... Params) {
            Log.e("RUNNING", "Still Running");
			String[] result = new String[2];
			retrofit2.Response<SearchResponse> response  = null;
			try{
				YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
				YelpFusionApi yelpFusioinApi = apiFactory.createAPI("EKQF7reSwACI45yGJiBhlg", "FqRhQWJCvmzontKsjalqqd7eb9EnnNv95TEO3hVGdRfkH3CoMSBlUR0NWHbnAEyi");

				Map<String, String> params = new HashMap<>();


				//	Double start = (double) System.currentTimeMillis();

				params.put("term", name);
				params.put("location", address);
				params.put("sort_by", "best_match");
				//params.put("longitude", "-79.716575");

				call = yelpFusioinApi.getBusinessSearch(params);
				response = call.execute();

				//	busy = business;
					price = response.body().getBusinesses().get(0).getPrice();
					phone = response.body().getBusinesses().get(0).getDisplayPhone();

				result[0] = price;
					result[1] = phone;
				//	return result;
			}
			catch (Exception e){
				Log.e("ERROR", "HOLY MOTHER OF GOD", e);
				//return null;
			}
			if (response != null){
				price = response.body().getBusinesses().get(0).getPrice();
				phone = response.body().getBusinesses().get(0).getDisplayPhone();
				Log.e("Business", response.body().getBusinesses().toString());
			}

			return result;
		}

		@Override
		protected void onPostExecute(String[] strings) {
			//Result.result.setText(strings[0] + " " + strings[1]);
			Result.setter(price);
			super.onPostExecute(strings);
		}
	}




	public String  Prices(){
		return price;
	}
	public String Phone() {return phone;}
	}


