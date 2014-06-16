package kaist.tap.kaf.io;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.output.XMLOutputter;

import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.views.ComponentRepository;

public class XMLWriter {
	protected Document doc;

	public XMLWriter(String t) {
		doc = new Document();
		Element root = new Element("KAFDOC");
		root.setAttribute("title", t);
		doc.setRootElement(root);
	}

	public void addView(ComponentRepository repo) {
		if (repo == null)
			return;

		Element view = new Element("VIEW");
		view.setAttribute("title", repo.getName());

		Element components = new Element("COMPONENTS");
		view.addContent(components);
		components.setAttribute("num",
				Integer.toString(repo.getNumberOfComponents()));

		for (int i = 0; i < repo.getNumberOfComponents(); ++i) {
			Component component = repo.get(i);
			Element el = component.getXMLElement(i);
			components.addContent(el);
		}

		doc.getRootElement().addContent(view);
	}

	public void write(String file) {
		try {
			FileOutputStream out = new FileOutputStream(file);

			XMLOutputter output = new XMLOutputter();
			Format f = output.getFormat();
			f.setEncoding("UTF-8");
			f.setIndent("   ");
			f.setLineSeparator("\r\n");
			f.setTextMode(TextMode.TRIM);
			output.setFormat(f);

			doc.getRootElement().setAttribute("title", file);
			output.output(doc, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
