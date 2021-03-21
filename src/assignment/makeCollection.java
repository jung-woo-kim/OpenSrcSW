package assignment;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class makeCollection {
	DocumentBuilder docBuilder;
	File inputList[];
	org.w3c.dom.Document total_doc;
	public makeCollection(File inputList[],DocumentBuilder docBuilder) {
		this.docBuilder=docBuilder;
		this.inputList=inputList;
		
	}
	
	public void parsing() {
		this.total_doc=docBuilder.newDocument();
		org.w3c.dom.Element docs =this.total_doc.createElement("docs");
		this.total_doc.appendChild(docs);
		
		for(int i=0;i<this.inputList.length;i++) {
			//parsing
			Document parse_doc = null;
			try {
				parse_doc = Jsoup.parse(this.inputList[i],"UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String str_title=parse_doc.getElementsByTag("title").text();
			String str_body=parse_doc.getElementById("content").text();
			//-----------------------xml파일 생성---------------------------------------------
			org.w3c.dom.Element doc =total_doc.createElement("doc");
			doc.setAttribute("id",""+i+"");
			docs.appendChild(doc);
			
			org.w3c.dom.Element title =total_doc.createElement("title");
			title.appendChild(total_doc.createTextNode(str_title));
			doc.appendChild(title);
			
			org.w3c.dom.Element body =total_doc.createElement("body");
			body.appendChild(total_doc.createTextNode(str_body));
			doc.appendChild(body);
		}
		
	}

	
}
