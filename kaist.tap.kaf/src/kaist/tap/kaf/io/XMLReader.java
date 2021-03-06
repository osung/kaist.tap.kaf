package kaist.tap.kaf.io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import kaist.tap.kaf.component.Arrow;
import kaist.tap.kaf.component.Component;
import kaist.tap.kaf.component.Group;
import kaist.tap.kaf.component.Line;
import kaist.tap.kaf.component.Parallelogram;
import kaist.tap.kaf.component.Rectangle;
import kaist.tap.kaf.component.Text;
import kaist.tap.kaf.views.ComponentRepository;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLReader {
	protected Document docu = null;
	protected List<Element> views;

	public XMLReader(String file) {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(file);

		try {
			docu = builder.build(xmlFile);
			Element root = docu.getRootElement();
			views = root.getChildren("VIEW");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	protected void addConnection(Element el, ComponentRepository repo,
			Rectangle rect) {
		Element conn = el.getChild("CONNECTION");
		if (conn != null) {
			String[] lines = conn.getText().split(" ");

			for (int k = 0; k < lines.length; ++k) {
				int lidx = Integer.parseInt(lines[k]);
				if (lidx < repo.getNumberOfComponents()) {
					Component c = repo.get(lidx);
					if (c instanceof Line) {
						Line l = (Line) c;
						rect.addConnection(l);
						Point p = rect.getConnectedPoint(l);
						Point sp = l.getPosition();
						Point ep = l.getEndPosition();
						if (p.x == sp.x && p.y == sp.y) {
							System.out.println("set start component");
							l.setStartComponent(rect);
						} else if (p.x == ep.x && p.y == ep.y) {
							System.out.println("set end component");
							l.setEndComponent(rect);
						} else {
							System.out.println("Invalid component");
						}

					} else {
						System.out.println("Invalid line connection");
					}
				}
			}
		}
	}

	protected void addPort(Element el, Rectangle rect) {
		Element ports = el.getChild("PORTS");
		if (ports != null) {
			List<Element> ps = ports.getChildren("PORT");
			for (int i = 0; i < ps.size(); ++i) {
				Element p = ps.get(i);
				Element pos = p.getChild("POSITION");
				rect.addPort(Integer.parseInt(pos.getChildText("X")),
						Integer.parseInt(pos.getChildText("Y")));
			}
		}
	}

	public Vector<ComponentRepository> getRepositories(Canvas canvas) {
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

				if (name.compareTo("RECT") == 0) {
					Rectangle rect = new Rectangle(el);
					rect.setDrawn(true);
					rect.setCanvas(canvas);
					repo.register(rect);
					addPort(el, rect);
					addConnection(el, repo, rect);
				} else if (name.compareTo("TEXT") == 0) {
					Text text = new Text(el);
					text.setDrawn(true);
					text.setCanvas(canvas);
					repo.register(text);
					addPort(el, text);
					addConnection(el, repo, text);
				} else if (name.compareTo("PARALLELOGRAM") == 0) {
					Parallelogram parallel = new Parallelogram(el);
					parallel.setDrawn(true);
					parallel.setCanvas(canvas);
					repo.register(parallel);
					addPort(el, parallel);
					addConnection(el, repo, parallel);
				} else if (name.compareTo("LINE") == 0) {
					Line line = new Line(el);
					line.setDrawn(true);
					line.setCanvas(canvas);
					repo.register(line);

					Element conn = el.getChild("CONNECTION");
					if (conn != null) {
						String sc = conn.getChildText("START");
						if (sc != null) {
							int sidx = Integer.parseInt(sc);
							if (sidx < repo.getNumberOfComponents()) {
								Component c = repo.get(sidx);
								line.setStartComponent(c);
								if (c instanceof Rectangle) {
									c.addConnection(line);
									((Rectangle) c).getConnectedPoint(line);
								}
							}
						}

						String ec = conn.getChildText("END");
						if (ec != null) {
							int eidx = Integer.parseInt(ec);
							if (eidx < repo.getNumberOfComponents()) {
								Component c = repo.get(eidx);
								line.setEndComponent(c);
								if (c instanceof Rectangle) {
									c.addConnection(line);
									((Rectangle) c).getConnectedPoint(line);
								}
							}
						}
					}
				} else if (name.compareTo("ARROW") == 0) {
					Arrow arrow = new Arrow(el);
					arrow.setDrawn(true);
					arrow.setCanvas(canvas);
					repo.register(arrow);

					Element conn = el.getChild("CONNECTION");
					if (conn != null) {
						String sc = conn.getChildText("START");
						if (sc != null) {
							int sidx = Integer.parseInt(sc);
							if (sidx < repo.getNumberOfComponents()) {
								Component c = repo.get(sidx);
								arrow.setStartComponent(c);
								c.addConnection(arrow);
							}
						}

						String ec = conn.getChildText("END");
						if (ec != null) {
							int eidx = Integer.parseInt(ec);
							if (eidx < repo.getNumberOfComponents()) {
								Component c = repo.get(eidx);
								arrow.setEndComponent(c);
								c.addConnection(arrow);
							}
						}
					}
				} else if (name.compareTo("GROUP") == 0) {
					Group group = new Group(el);

					String members = el.getChildText("MEMBERS");
					String[] comps = members.split(" ");
					for (int k = 0; k < comps.length; ++k) {
						Component c = repo.get(Integer.parseInt(comps[k]));
						group.addComponent(c);
					}
					group.setDrawn(true);
					repo.register(group);
				}
			}

			repos.add(repo);
		}

		return repos;
	}
}
