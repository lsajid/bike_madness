package Utilities;
import java.util.HashMap;

public class LevelLoader {
	public static HashMap<String, Integer> Objects = new HashMap<String, Integer>() {{
	    put("Rectangle", 0);
	    put("Line", 1);
	    put("Ring", 2);
	}};
	
	public Object[] getObjInfo(String s) {
		/**
		 * Returns an array where index 0 is the type of Object (Rectangle, Line , etc)
		 * index 1 is the list of parameters for the Object
		 * */
		String[] sArr= s.split(" ");
		
		
		int type = Objects.get(sArr[0]);
		int[] params = new int [sArr.length-1];
		for (int i =0;i< sArr.length -1; i++) {
			params[i] = Integer.parseInt(sArr[i+1]);
		}
		
		return new Object[] {type,params} ;
	}
}
