import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.sax.XMLReaders;

public class CarRental {


 	public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File("CarRental.xml"));
            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }


	public static Document resetDocument() {
 		// Create the root element
        Element carElement = new Element("carrental");  
        Document myDocument = new Document(carElement);
        return myDocument;
	}

	public static void addToFile() {
		Document document = readDocument();
		Element carrental = document.getRootElement();
		carrental.addContent(askRental());
		outputDocumentToFile(document);
	}

	public static void outputDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void outputDocumentToFile(Document myDocument) {
    	//setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter();

            //output to a file
            FileWriter writer = new FileWriter("CarRental.xml");
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static Element askRental() {
    	Element rental = new Element("rental");
    	rental.setAttribute(new Attribute("id", "1234"));

    	
    	//MAKE
    	System.out.print("Make : ");
    	String makeText = System.console().readLine();
    	Element make = new Element("make");
    	make.addContent(makeText);
    	
    	//MODEL
    	System.out.print("Model : ");
    	String modelText = System.console().readLine();
    	Element model = new Element("model");
    	model.addContent(modelText);

    	//NUMBEROFDAYS
    	System.out.print("Number of days : ");
    	String nofdaysText = System.console().readLine();
    	Element nofdays = new Element("nofdays");
    	nofdays.addContent(nofdaysText);

    	//NUMBEROFUNITS
    	System.out.print("Number of units : ");
    	String nofunitsText = System.console().readLine();
    	Element nofunits = new Element("nofunits");
    	nofunits.addContent(nofunitsText);

		//DISCUNT
    	System.out.print("Discount : ");
    	String discountText = System.console().readLine();
    	Element discount = new Element("discount");
    	discount.addContent(discountText);

    	rental.addContent(make);
    	rental.addContent(model);
    	rental.addContent(nofdays);
    	rental.addContent(nofunits);
    	rental.addContent(discount);

    	return rental;
    }

     public static void executeXSLT(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("CarRental.xslt"));
			//Make the output result for the finished document
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
	}

	public static void validate() {
		SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
		try {
			Document doc = builder.build(new File("CarRental.xml"));
			System.out.println("Root: " + doc.getRootElement().getName());
		}
		catch(JDOMException e) {
			e.printStackTrace();
		}
		catch(java.io.IOException e) {
			e.printStackTrace();
		}
	}


    public static void main(String argv[]) {
        if(argv.length == 1) {
            String command = argv[0];
            if(command.equals("reset")) outputDocumentToFile(resetDocument());
            else if(command.equals("new")) addToFile();
            else if(command.equals("list")) outputDocument(readDocument());
            else if(command.equals("xslt")) executeXSLT(readDocument());
            else if(command.equals("validate")) validate();
            /*else if(command.equals("accessChild")) accessChildElement(createDocument());
            else if(command.equals("removeChild")) removeChildElement(createDocument());
            else if(command.equals("save")) outputDocumentToFile(createDocument());
            else if(command.equals("load")) outputDocument(readDocument());
            else if(command.equals("xslt")) executeXSLT(createDocument());*/
            else {
                System.out.println(command + " is not a valid option.");
                printUsage();
            }
        } else {
            printUsage();
        }
    }

    /**
     * Convience method to print the usage options for the class.
     */
    public static void printUsage() {
        System.out.println("Usage: Example [option] \n where option is one of the following:");
        System.out.println("  showDocument - create a new document in memory and print it to the console");
        System.out.println("  accessChild - create a new document and show its child element");
        System.out.println("  removeChild - create a new document and remove its child element");
        System.out.println("  save   - create a new document and save it to myFile.xml");
        System.out.println("  load   - read and parse a document from example.xml");
        System.out.println("  xslt    - create a new document and transform it to HTML with the XSLT stylesheet in example.xslt");
    }
	
}