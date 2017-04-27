package yummy;

import java.util.ArrayList;

public class RemoveNull {
	public static Comparable[] correct(Comparable[] a){
		ArrayList<Comparable> temp = new ArrayList<Comparable>();
		
		for (int i =0; i < a.length; i++){
			if (a[i] != null ){
				temp.add(a[i]);
			}
		}
		
		Comparable[] result = new Comparable[temp.size()];
		for (int i =0; i < result.length; i++){
			result[i] = temp.get(i);
		}
		
		return result;
	}
}
