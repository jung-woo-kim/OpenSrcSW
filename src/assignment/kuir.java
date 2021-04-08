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



public class kuir {


	public static void main(String[] args) throws IOException, ClassNotFoundException   {
		// TODO Auto-generated method stub
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=null;
		try {
		docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		
		if(args.length>1) {
			if(args[0].equals("-c")) {
				File input= new File(args[1]);
				File inputList[]= input.listFiles();
				makeCollection parser= new makeCollection(inputList,docBuilder);
				parser.parsing();
				makeXML(parser.total_doc,"collection.xml");
				}else if(args[0].equals("-k")) {
					File collection= new File(args[1]);
					makeKeyword analyze=new makeKeyword(collection,docBuilder);
					analyze.chageBody();
					makeXML(analyze.total_doc,"index.xml");
				}else if(args[0].equals("-i")) {
					File input= new File(args[1]);
					indexer weight = new indexer(input,docBuilder);
					weight.weightAnalyze();
					weight.makeFile();
				}else if(args[0].equals("-s")) {
					File input= new File(args[1]);
					if(args[2].equals("-q")) {
						searcher search = new searcher(args[3],docBuilder,input);
						search.CalcSim();
					}else {
						System.out.println("세번째 인자값이 잘못되었습니다.");
					}
					
				}
				else {
					System.out.println("만들 수 없는 파일입니다.");
				}
		}
		else {
			System.out.println("인자값을 넘겨주어야 합니다");
		}
		
	}
	
	static void makeXML(org.w3c.dom.Document doc,String fileName) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer=null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		DOMSource source=new DOMSource(doc);
		StreamResult result=null;
		try {
			result = new StreamResult(new FileOutputStream(new File(fileName)));
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


