package com.gmail.jbosworth2.japanese_studies.xml;

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
	
	public void read(StringBuilder sb, String fn){
		this.readable.clear();
		ArrayList<String> ID = new ArrayList<String>();
		ArrayList<String> Level = new ArrayList<String>();
		ArrayList<String> Character = new ArrayList<String>();//Also could hold Vocab
		ArrayList<String> Meaning = new ArrayList<String>();//Also could hold Japanese Sentences
		ArrayList<String> Kana = new ArrayList<String>();//Also could hold English Sentences
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
			doc.getDocumentElement().normalize();
			
			NodeList n = null;
			NodeList n1 = null;
			NodeList n2 = null;
			NodeList n3 = null;
			NodeList n4 = null;
			if(fn == "kanji.xml" || fn == "vocab.xml"){
				n = doc.getElementsByTagName("ID");
				n1 = doc.getElementsByTagName("Level");
				n2 = doc.getElementsByTagName("Character");
				n3 = doc.getElementsByTagName("Meaning");
				n4 = doc.getElementsByTagName("Kana");
			}else if(fn == "context_sentences.xml"){
				n = doc.getElementsByTagName("ID");
				n1 = doc.getElementsByTagName("Level");
				n2 = doc.getElementsByTagName("Vocab");
				n3 = doc.getElementsByTagName("Japanese_Sentence");
				n4 = doc.getElementsByTagName("English_Sentence");
			}
			Node an, an2;
			if(n != null && n1 != null && n2 != null && n3 != null && n4 != null){
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
				    an = n2.item(i);//Character or Vocab
				    an2 = an.getFirstChild();
				    Character.add(an2.getNodeValue());
				}
				for (int i=0; i < n3.getLength(); i++) {
				    an = n3.item(i);//Meaning or Japanese Sentence
				    an2 = an.getFirstChild();
				    Meaning.add(an2.getNodeValue());
				}
				for (int i=0; i < n4.getLength(); i++) {
				    an = n4.item(i);//Kana or English Sentence
				    an2 = an.getFirstChild();
				    Kana.add(an2.getNodeValue());
				}
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