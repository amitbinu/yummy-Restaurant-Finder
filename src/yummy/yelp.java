package yummy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Coordinates;
import com.yelp.fusion.client.models.SearchResponse;

public class yelp {
	private static String price;
	
	public yelp(String name, String address) throws IOException{
		YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
		YelpFusionApi yelpFusioinApi = apiFactory.createAPI("EKQF7reSwACI45yGJiBhlg", "FqRhQWJCvmzontKsjalqqd7eb9EnnNv95TEO3hVGdRfkH3CoMSBlUR0NWHbnAEyi");
		
		Map<String, String> params = new HashMap<>();
		
		
		//	Double start = (double) System.currentTimeMillis();
			
			params.put("term", name);
			params.put("location", address);
			params.put("sort_by", "best_match");
			//params.put("longitude", "-79.716575");
			
			retrofit2.Call<SearchResponse> call = yelpFusioinApi.getBusinessSearch(params);
			
			retrofit2.Response<SearchResponse> response = call.execute();
			
			SearchResponse searchResponse = response.body();
			
		//	int total = searchResponse.getTotal();
			
			ArrayList<Business> business = searchResponse.getBusinesses();
			
			price = business.get(0).getPrice();
		//	System.out.println((double)System.currentTimeMillis() - start );
		}
	
	
	public String  Prices(){
		return price;
	}
}
