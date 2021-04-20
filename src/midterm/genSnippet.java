package midterm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class genSnippet {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String line=null;
		ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();
		File input = new File("input.txt");
		FileInputStream fileInputStream = new FileInputStream(input);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");;
		BufferedReader inFile= new BufferedReader(inputStreamReader);
		try {
			while((line=inFile.readLine())!=null) {
				String[] str=line.split(" ");
				ArrayList<String> array= new ArrayList<String>();
				for(int i=0;i<str.length;i++) {
					array.add(str[i]);
				}
				list.add(array);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<ArrayList<String>,Integer> Qid=new HashMap<ArrayList<String>,Integer>();
		ArrayList<Integer> sum=new ArrayList<Integer>();
		String[] inputtxt=args[5].split(" ");
		for(int i=0;i<list.size();i++) {
			int sumnum=0;
				for(int j=0;j<list.get(i).size();j++) {
					for(int k=0;k<inputtxt.length;k++) {
						if(inputtxt.equals(list.get(i).get(j))) {
							sumnum++;
						}
					}
					
				}
				sum.add(sumnum);
		}
		for(int i=0;i<5;i++) {
			Qid.put(list.get(i),sum.get(i));
		}
		List<Integer> keySet = new ArrayList(Qid.keySet());
		Collections.sort(keySet,(o1,o2)->(Qid.get(o2).compareTo(Qid.get(o1))));
		System.out.println(keySet.get(0));
		
	}

}
