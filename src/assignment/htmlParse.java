package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class htmlParse {

	public static void main(String[] args)   {
		// TODO Auto-generated method stub
		
		File input= new File("2주차 실습 html/2주차 실습 html");
		File inputList[]= input.listFiles();
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=null;
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		
		org.w3c.dom.Document total_doc=docBuilder.newDocument();
		org.w3c.dom.Element docs =total_doc.createElement("docs");
		total_doc.appendChild(docs);
		
		for(int i=0;i<inputList.length;i++) {
			//parsing
			Document parse_doc = null;
			try {
				parse_doc = Jsoup.parse(inputList[i],"UTF-8");
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
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer=null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		DOMSource source=new DOMSource(total_doc);
		StreamResult result=null;
		try {
			result = new StreamResult(new FileOutputStream(new File("collection.xml")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transformer.transform(source, result);
			System.out.println("success");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

