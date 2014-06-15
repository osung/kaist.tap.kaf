package kaist.tap.kaf.io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import kaist.tap.kaf.component.Arrow;
import kaist.tap.kaf.component.Line;
import kaist.tap.kaf.component.Parallelogram;
import kaist.tap.kaf.component.Rectangle;
import kaist.tap.kaf.component.Text;
import kaist.tap.kaf.views.ComponentRepository;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLReader {
	protected Document docu = null;
	protected List<Element> views;
	
	public XMLReader(String file) {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(file);
		
		try {
			docu = (Document) builder.build(xmlFile);
			Element root = docu.getRootElement();
			views = root.getChildren("VIEW");		
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}
	
	public Vector<ComponentRepository> getRepositories() {
		Vector<ComponentRepository> repos = new Vector<ComponentRepository>();
		
		for (int i = 0; i < views.size(); ++i) {
			Element v = views.get(i);
			Element comp = v.getChild("COMPONENTS");
			List<Element> els = comp.getChildren();
			String title = v.getAttributeValue("title");
			ComponentRepository repo = new ComponentRepository(title);
			
			for (int j = 0; j < els.size(); ++j) {
				Element el = els.get(j);
				String name = el.getName();
				
				if (name.compareTo("RECT")==0) {
					Rectangle rect = new Rectangle(el);
					rect.setDrawn(true);
					repo.register(rect);
				}
				else if (name.compareTo("TEXT")==0) {
					Text text = new Text(el);
					text.setDrawn(true);
					repo.register(text);
				}
				else if (name.compareTo("PARALLELOGRAM")==0) {
					Parallelogram parallel = new Parallelogram(el);
					parallel.setDrawn(true);
					repo.register(parallel);
				}
				else if (name.compareTo("LINE")==0) {
					Line line = new Line(el);
					line.setDrawn(true);
					repo.register(line);
				}
				else if (name.compareTo("ARROW")==0) {
					Arrow arrow = new Arrow(el);
					arrow.setDrawn(true);
					repo.register(arrow);
				}
				else if (name.compareTo("GROUP")==0) {
					
				}
			}

			repos.add(repo);
		}
		
		return repos;
	}
}
