package com.gmail.jbosworth2.japanese_studies;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

public class XMLFileReader {
	//List that contains all data read from file
	private ArrayList<ArrayList<String>> readable = new ArrayList<ArrayList<String>>();
	
	//Singleton XMLFileReader- only one reader necessary
	private static final XMLFileReader INSTANCE = new XMLFileReader();
	private XMLFileReader() {}
	public static XMLFileReader getInstance(){
		return INSTANCE;
	}
	
	public ArrayList<ArrayList<String>> getReadable(){
		return readable;
	}
	
	public void read(StringBuilder sb){
		this.readable.clear();
		ArrayList<String> ID = new ArrayList<String>();
		ArrayList<String> Level = new ArrayList<String>();
		ArrayList<String> Character = new ArrayList<String>();
		ArrayList<String> Meaning = new ArrayList<String>();
		ArrayList<String> Kana = new ArrayList<String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
			doc.getDocumentElement().normalize();
			
			
			NodeList n = doc.getElementsByTagName("ID");
			NodeList n1 = doc.getElementsByTagName("Level");
			NodeList n2 = doc.getElementsByTagName("Character");
			NodeList n3 = doc.getElementsByTagName("Meaning");
			NodeList n4 = doc.getElementsByTagName("Kana");
			Node an, an2;
			for (int i=0; i < n.getLength(); i++) {
			    an = n.item(i);//ID
			    an2 = an.getFirstChild();
			    ID.add(an2.getNodeValue());
			}
			for (int i=0; i < n1.getLength(); i++) {
			    an = n1.item(i);//Level
			    an2 = an.getFirstChild();
			    Level.add(an2.getNodeValue());
			}
			for (int i=0; i < n2.getLength(); i++) {
			    an = n2.item(i);//Character
			    an2 = an.getFirstChild();
			    Character.add(an2.getNodeValue());
			}
			for (int i=0; i < n3.getLength(); i++) {
			    an = n3.item(i);//Meaning
			    an2 = an.getFirstChild();
			    Meaning.add(an2.getNodeValue());
			}
			for (int i=0; i < n4.getLength(); i++) {
			    an = n4.item(i);//Kana
			    an2 = an.getFirstChild();
			    Kana.add(an2.getNodeValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		readable.add(ID);
		readable.add(Level);
		readable.add(Character);
		readable.add(Meaning);
		readable.add(Kana);
	}
}