package assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	private double N;
	private DocumentBuilder docBuilder;
	private File index;
	private HashMap<String, ArrayList<ArrayList<String>>> WeightMap= new HashMap<String, ArrayList<ArrayList<String>>>();
	public indexer(File index,DocumentBuilder docBuilder) {
		this.index=index;
		this.docBuilder=docBuilder;
	}
	
	public void weightAnalyze() {
		org.w3c.dom.Document xml=null;
		try {
			xml=docBuilder.parse(index);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		Element root = xml.getDocumentElement();
		
		NodeList n_list=root.getElementsByTagName("body");
		N=n_list.getLength();
		ArrayList<ArrayList<String[]>> total_list=new ArrayList<ArrayList<String[]>>();
		for(int i=0;i<n_list.getLength();i++) {
			String[] str=n_list.item(i).getFirstChild().getTextContent().split("#");
			ArrayList<String[]> key_sum_list = new ArrayList<String[]>();
			for(int j=0;j<str.length;j++) {
				key_sum_list.add(str[j].split(":"));
			}
			total_list.add(key_sum_list);
		}
		for(int i=0;i<total_list.size();i++) {
			ArrayList<String[]> key_sum_list=total_list.get(i);
			for(int j=0;j<key_sum_list.size();j++) {
				Double appear_sum=0.0;
				for(int k=0;k<total_list.size();k++) {
					for(int l=0;l<total_list.get(k).size();l++) {
						if(total_list.get(k).get(l)[0].equals(key_sum_list.get(j)[0])) {
							appear_sum+=1.0;
							break;
						}
					}
				}
				if(WeightMap.containsKey(key_sum_list.get(j)[0])) {
					ArrayList<String> array=new ArrayList<String>();
					array.add(n_list.item(i).getParentNode().getAttributes().getNamedItem("id").getNodeValue());
					array.add(this.caculation(Double.parseDouble(key_sum_list.get(j)[1]),appear_sum).toString());
					//꼬꼬마 형태소 예외 처리 한문서에 단어 두번 등장 할때
					if(WeightMap.get(key_sum_list.get(j)[0]).get(WeightMap.get(key_sum_list.get(j)[0]).size()-1).get(0)==n_list.item(i).getParentNode().getAttributes().getNamedItem("id").getNodeValue()) {
						Double change1=Double.parseDouble(WeightMap.get(key_sum_list.get(j)[0]).get(WeightMap.get(key_sum_list.get(j)[0]).size()-1).get(1));
						Double change2=this.caculation(Double.parseDouble(key_sum_list.get(j)[1]),appear_sum);
						Double set_this=change1+change2;
						System.out.println(appear_sum);
						WeightMap.get(key_sum_list.get(j)[0]).get(WeightMap.get(key_sum_list.get(j)[0]).size()-1).set(1,set_this.toString());
					}else {
						WeightMap.get(key_sum_list.get(j)[0]).add(array);
					}
					
					
					
				}else {
					ArrayList<String> array=new ArrayList<String>();
					ArrayList<ArrayList<String>> arrayAdd=new ArrayList<ArrayList<String>>();
					array.add(n_list.item(i).getParentNode().getAttributes().getNamedItem("id").getNodeValue());
					array.add(this.caculation(Double.parseDouble(key_sum_list.get(j)[1]),appear_sum).toString());
					arrayAdd.add(array);
					WeightMap.put(key_sum_list.get(j)[0],arrayAdd);
				}
				
			}
		}
		
	}
	public void makeFile() throws IOException {
		FileOutputStream fileStream = new FileOutputStream("index.post");
		ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileStream);
		objectOutputStream.writeObject(WeightMap);
		objectOutputStream.close();
	}
	public Double caculation(Double tf,Double df) {
		Double d=tf*Math.log(N/df);
		Double a=Math.round(d*100.0)/100.0;
		return a; 
	}
}
