package Utilities;
import java.util.HashMap;

public class LevelLoader {
	public static HashMap<String, Integer> Objects = new HashMap<String, Integer>() {{
	    put("Rectangle", 0);
	}};
	
	public int [] getObjInfo(String s) {
		
		String[] sArr= s.split(" ");
		
		
		int type = Objects.get(sArr[0]);
		int x = Integer.parseInt(sArr[2]);
		int y = Integer.parseInt(sArr[4]);
		int w = Integer.parseInt(sArr[6]);
		int h = Integer.parseInt(sArr[8]);
//		for (int i =0; i < sArr.length;i++) {
//		System.out.println("index: " + i + " =" +  sArr[i] );
//		}
//		return new String [] {};
//		//System.out.println(String.join(" ,", type,x,y,w,h) );
		return new int [] {type,x,y,w,h} ;
	}
}
