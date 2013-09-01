package pl.pecet.spblibrary.mobile.xmlparser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class XMLParser {

	public Document getDocument(final InputStream xmlFile) {
		final Document document;
		final DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		try {
			final DocumentBuilder db = factory.newDocumentBuilder();
			document = db.parse(xmlFile);
		} catch (final ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (final SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (final IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} finally {
			try {
				xmlFile.close();
			} catch (final IOException e) {
				Log.e("Error: ", e.getMessage());
				return null;
			}
		}

		return document;
	}

	public String getValue(final Element item, final String tagName) {
		final NodeList nodes = item.getElementsByTagName(tagName);
		return getElementValue(nodes.item(0));
	}

	public final String getElementValue(final Node node) {
		if ((node != null) && node.hasChildNodes()) {
			Node childNode = node.getFirstChild();
			while (childNode != null) {
				if (childNode.getNodeType() == Node.TEXT_NODE) {
					return childNode.getNodeValue();
				}
				childNode = childNode.getNextSibling();
			}
		}
		return null;
	}
}
