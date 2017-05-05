package com.example.android.yummy;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
				//	price = business.get(0).getPrice();
				//	phone = business.get(0).getDisplayPhone();
				//String[] result = new String[2];
				//result[0] = price;
				//	result[1] = phone;
				//	return result;
			}
			catch (Exception e){
				Log.e("ERROR", "HOLY MOTHER OF GOD", e);
				//return null;
			}
			if (response != null){
				Log.e("Business", response.body().getBusinesses().toString());
			}
			else{
				Log.e("LITIFG ", "sbvisvbjvbfdjvbdfvbjdfvbFBBVIBIFESVBEIUVBEIFBDIFVBELKRVBALV BFVJEBLVBA F VJ");
			}
			return null;
		}
	}




	public String  Prices(){
		return price;
	}
	public ArrayList<Business> Business(){
		return busy;
	}
}
