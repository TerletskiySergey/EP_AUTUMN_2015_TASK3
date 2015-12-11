package tasks.task3.variant8.builders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import tasks.task3.variant8.Warplane;
import tasks.task3.variant8.WarplanesEnum;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DOMWarplanesBuilder extends AbstractWarplanesBuilder {

    private static class ErrorHandlerImpl implements ErrorHandler {

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }

    @Override
    public void buildWarplanesSet(String xmlFilePath) {
        buildWarplanesSet(xmlFilePath, null);
    }

    @Override
    public void buildWarplanesSet(String xmlFilePath, String xsdFilePath) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            initSchema(xsdFilePath);
            dbFactory.setSchema(schema);
            dbFactory.setNamespaceAware(true);
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            builder.setErrorHandler(xsdFilePath == null ? null : new ErrorHandlerImpl());
            Document doc = builder.parse(xmlFilePath);
            Element root = doc.getDocumentElement();
            this.warplanes = extractWarplanes(root);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private int extractCrew(Element plane) {
        Element crew = (Element) plane.getElementsByTagName(WarplanesEnum.CREW.getValue()).item(0);
        return Integer.parseInt(crew.getFirstChild().getNodeValue().trim());
    }

    private double extractHeight(Element plane) {
        Element hi = (Element) plane.getElementsByTagName(WarplanesEnum.HEIGHT.getValue()).item(0);
        return calcValue(hi, WarplanesEnum.UNITS);
    }

    private double extractLength(Element plane) {
        Element len = (Element) plane.getElementsByTagName(WarplanesEnum.LENGTH.getValue()).item(0);
        return calcValue(len, WarplanesEnum.UNITS);
    }

    private double calcValue(Element dim, WarplanesEnum attrName) {
        String attr = dim.getAttribute(attrName.getValue()).trim();
        WarplanesEnum units = WarplanesEnum.fromString(attr);
        double res = Double.parseDouble(dim.getFirstChild().getNodeValue().trim());
        return convert(res, units);
    }

    private int extractMissile(Element plane) {
        Element missile = (Element) plane.getElementsByTagName(WarplanesEnum.MISSILE.getValue()).item(0);
        return (missile == null)
                ? 0
                : Integer.parseInt(missile.getFirstChild().getNodeValue().trim());
    }

    private String extractModel(Element plane) {
        Element model = (Element) plane.getElementsByTagName(WarplanesEnum.MODEL.getValue()).item(0);
        return model.getFirstChild().getNodeValue().trim();
    }

    private String extractOrigin(Element plane) {
        Element origin = (Element) plane.getElementsByTagName(WarplanesEnum.ORIGIN.getValue()).item(0);
        return origin.getFirstChild().getNodeValue().trim();
    }

    private double extractPrice(Element plane) {
        NodeList priceList = plane.getElementsByTagName(WarplanesEnum.PRICE.getValue());
        if (priceList.getLength() == 0) {
            return 0;
        }
        Element price = (Element) priceList.item(0);
        return calcValue(price, WarplanesEnum.ORDER);
    }

    private boolean extractRadar(Element plane) {
        Element radar = (Element) plane.getElementsByTagName(WarplanesEnum.RADAR.getValue()).item(0);
        String avail = radar.getAttribute(WarplanesEnum.AVAILABLE.getValue()).trim();
        return Boolean.parseBoolean(avail);
    }

    private double[] extractSpan(Element plane) {
        Element curElem = (Element) plane.getElementsByTagName(WarplanesEnum.SPAN.getValue()).item(0);
        String[] spans = curElem.getFirstChild().getNodeValue().trim().split("\\p{Space}+");

        return (spans.length == 1)
                ? new double[]{Double.parseDouble(spans[0]), Double.parseDouble(spans[0])}
                : new double[]{Double.parseDouble(spans[0]), Double.parseDouble(spans[1])};
    }

    private Warplane.TechChars extractTechChars(Element plane) {
        Warplane.Type type = extractType(plane);
        int crew = extractCrew(plane);
        int missile = extractMissile(plane);
        boolean radar = extractRadar(plane);
        double length = extractLength(plane);
        double height = extractHeight(plane);
        double[] spans = extractSpan(plane);
        double minSpan = spans[0];
        double maxSpan = spans[1];
        Warplane.GeomParams geoms = new Warplane.GeomParams(length, minSpan, maxSpan, height);
        return new Warplane.TechChars(type, crew, missile, radar, geoms);
    }

    private Warplane.Type extractType(Element plane) {
        Element type = (Element) plane.getElementsByTagName(WarplanesEnum.TYPE.getValue()).item(0);
        return Warplane.Type.valueOf(type.getFirstChild().getNodeValue().trim().toUpperCase());
    }

    private Warplane extractWarplane(Element plane) {
        String model = extractModel(plane);
        String origin = extractOrigin(plane);
        double price = extractPrice(plane);
        Warplane.TechChars techChars = extractTechChars(plane);
        return new Warplane(model, origin, price, techChars);
    }

    private Set<Warplane> extractWarplanes(Element root) {
        NodeList planes = root.getElementsByTagName(WarplanesEnum.PLANE.getValue());
        Set<Warplane> result = new HashSet<>();
        for (int i = 0; i < planes.getLength(); i++) {
            result.add(extractWarplane((Element) planes.item(i)));
        }
        return result;
    }
}