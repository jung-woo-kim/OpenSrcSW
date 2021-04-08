package assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;

import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {
	
	String str;
	DocumentBuilder docBuilder;
	File post;
	KeywordExtractor ke =new KeywordExtractor();
	KeywordList kl=null;
	public searcher(String str,DocumentBuilder docBuilder,File post) {
		this.str=str;
		this.docBuilder=docBuilder;
		this.post=post;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<Integer,Double> InnerProduct() throws IOException, ClassNotFoundException{

		kl=ke.extractKeyword(str, true);
		FileInputStream fileStream=new FileInputStream(post);
		ObjectInputStream objectInputStream= new ObjectInputStream(fileStream);
		Object obj= objectInputStream.readObject();
		objectInputStream.close();
		HashMap hashMap=(HashMap)obj;
		HashMap<Integer,Double> Qid=new HashMap<Integer,Double>();
		for(int i=0;i<5;i++) {
			double inner=0.0;
			for(int j=0;j<kl.size();j++) {
				if(hashMap.containsKey(kl.get(j).getString())) {
					ArrayList<ArrayList<String>> value=(ArrayList<ArrayList<String>>)hashMap.get(kl.get(j).getString());
					for(int k=0;k<value.size();k++) {
						String str=value.get(k).get(0);
						int val=Integer.parseInt(str);
						if(val==i) {
							Double d=Double.parseDouble(value.get(k).get(1));
							
							inner+=d;
							
						}
					}
				}
			}
			inner=Math.round(inner*100.0)/100.0;
			Qid.put(i, inner);
		}
		return Qid;
		}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void CalcSim() throws IOException, ClassNotFoundException{
		HashMap<Integer,Double> Qid=this.InnerProduct();

		FileInputStream fileStream=new FileInputStream(post);
		ObjectInputStream objectInputStream= new ObjectInputStream(fileStream);
		Object obj= objectInputStream.readObject();
		objectInputStream.close();
		HashMap hashMap=(HashMap)obj;
		

		HashMap<Integer,Double> SimQid=new HashMap<Integer,Double>();

		
		for(int i=0;i<5;i++) {
			double weightQuery=0.0;
			double weightDoc=0.0;
			for(int j=0;j<kl.size();j++) {
				if(hashMap.containsKey(kl.get(j).getString())) {
					ArrayList<ArrayList<String>> value=(ArrayList<ArrayList<String>>)hashMap.get(kl.get(j).getString());
					for(int k=0;k<value.size();k++) {
						String str=value.get(k).get(0);
						int val=Integer.parseInt(str);
						if(val==i) {
							weightQuery+=1.0;
							weightDoc+=Math.pow(Double.parseDouble(value.get(k).get(1)),2);
						}
					}
				}
			}
			if(Qid.get(i)==0) {
				SimQid.put(i, 0.0);
			}else {
				double value=Math.round(Qid.get(i)/Math.sqrt(weightDoc*weightQuery)*100.0)/100.0;
				SimQid.put(i, value);
			}
			
			
		}
		for(int i=0;i<5;i++) {
			System.out.println("id :"+i+"->"+"SimQid :"+SimQid.get(i));
		}
		
		List<Integer> keySet= new ArrayList<>(SimQid.keySet());
		Collections.sort(keySet,(o1,o2)->(SimQid.get(o2).compareTo(SimQid.get(o1))));

		
		File collection= new File("./collection.xml");
		org.w3c.dom.Document xml=null;
		try {
			xml = docBuilder.parse(collection);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		Element root = xml.getDocumentElement();
		NodeList n_list=root.getElementsByTagName("title");
		for(int i=0;i<3;i++) {
			if(keySet.get(i)==0.0) {
				break;
			}else {
				System.out.println(n_list.item(keySet.get(i)).getTextContent());
			}
		
		}
	}
	
	
	
}
