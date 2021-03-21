package assignment;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {
	DocumentBuilder docBuilder;
	org.w3c.dom.Document total_doc;
	File analyze;
	public makeKeyword(File analyze,DocumentBuilder docBuilder) {
		this.total_doc=null;
		this.analyze=analyze;
		this.docBuilder=docBuilder;
	}
	public void chageBody() {
		org.w3c.dom.Document xml=null;
		KeywordExtractor ke =new KeywordExtractor();
		KeywordList kl=null;
		try {
			xml = docBuilder.parse(this.analyze);
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
		
		for(int i=0;i<n_list.getLength();i++) {
			String body_index="";
			kl=ke.extractKeyword(n_list.item(i).getFirstChild().toString(),true);
			for(int j=0;j<kl.size();j++) {
				Keyword k = kl.get(j);
				body_index+=k.getString()+":"+k.getCnt()+"#";
			}
			xml.getElementsByTagName("body").item(i).setTextContent(body_index);	
		}
		this.total_doc=xml;
	}

}
