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

/**
 * Class provides DOM approach for parsing instances
 * of Warplane class from XML document.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public class DOMWarplanesBuilder extends AbstractWarplanesBuilder {

    /**
     * Implementation of ErrorHandler class, providing further throwing
     * of SAXPArseExceptions without their handling.
     */
    private static class ErrorHandlerImpl implements ErrorHandler {

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }

    /**
     * DOM implementation for parsing Warplane class instances from XML-documents without
     * document validating against XSD-schema.
     *
     * @param xmlFilePath XML-document file path to be parsed.
     */
    @Override
    public void buildWarplanesSet(String xmlFilePath) {
        buildWarplanesSet(xmlFilePath, null);
    }

    /**
     * DOM implementation for parsing Warplane class instances from XML-documents including
     * document validating against XSD-schema while parsing.
     *
     * @param xmlFilePath XML-document file path to be parsed.
     * @param xsdFilePath XSD-schema file path to be validated according to.
     *
     */
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
            System.err.println(ex);
        }
    }

    /**
     * Method for conversion of XML-element text content into double value.
     * Method is called when element's content interpretation depends on the value
     * of element's attribute.
     *
     * @param dim      Element instance, containing text information to be converted.
     * @param attrName WarplanesEnum instance, that determines the name
     *                 of the attribute, on which depends the logic of conversion.
     * @return converted value.
     */
    private double calcValue(Element dim, WarplanesEnum attrName) {
        String attr = dim.getAttribute(attrName.getValue()).trim();
        WarplanesEnum units = WarplanesEnum.fromString(attr);
        double res = Double.parseDouble(dim.getFirstChild().getNodeValue().trim());
        return convert(res, units);
    }

    /**
     * Extracts integer value, that will be assigned to the crew-field of Warplane.TechChars
     * class instance from the current Element instance, which is associated with the
     * distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private int extractCrew(Element plane) {
        Element crew = (Element) plane.getElementsByTagName(WarplanesEnum.CREW.getValue()).item(0);
        return Integer.parseInt(crew.getFirstChild().getNodeValue().trim());
    }

    /**
     * Extracts double value, that will be assigned to the height-field of
     * Warplane.GeomParams class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private double extractHeight(Element plane) {
        Element hi = (Element) plane.getElementsByTagName(WarplanesEnum.HEIGHT.getValue()).item(0);
        return calcValue(hi, WarplanesEnum.UNITS);
    }

    /**
     * Extracts double value, that will be assigned to the length-field of
     * Warplane.GeomParams class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private double extractLength(Element plane) {
        Element len = (Element) plane.getElementsByTagName(WarplanesEnum.LENGTH.getValue()).item(0);
        return calcValue(len, WarplanesEnum.UNITS);
    }

    /**
     * Extracts integer value, that will be assigned to the missile-field of
     * Warplane.TechChars class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private int extractMissile(Element plane) {
        Element missile = (Element) plane.getElementsByTagName(WarplanesEnum.MISSILE.getValue()).item(0);
        return (missile == null)
                ? 0
                : Integer.parseInt(missile.getFirstChild().getNodeValue().trim());
    }

    /**
     * Extracts String value, that will be assigned to the model-field of
     * Warplane class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private String extractModel(Element plane) {
        Element model = (Element) plane.getElementsByTagName(WarplanesEnum.MODEL.getValue()).item(0);
        return model.getFirstChild().getNodeValue().trim();
    }

    /**
     * Extracts String value, that will be assigned to the origin-field of
     * Warplane class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private String extractOrigin(Element plane) {
        Element origin = (Element) plane.getElementsByTagName(WarplanesEnum.ORIGIN.getValue()).item(0);
        return origin.getFirstChild().getNodeValue().trim();
    }

    /**
     * Extracts double value, that will be assigned to the price-field of
     * Warplane class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private double extractPrice(Element plane) {
        NodeList priceList = plane.getElementsByTagName(WarplanesEnum.PRICE.getValue());
        if (priceList.getLength() == 0) {
            return 0;
        }
        Element price = (Element) priceList.item(0);
        return calcValue(price, WarplanesEnum.ORDER);
    }

    /**
     * Extracts boolean value, that will be assigned to the radar-field of
     * Warplane.TechChars class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private boolean extractRadar(Element plane) {
        Element radar = (Element) plane.getElementsByTagName(WarplanesEnum.RADAR.getValue()).item(0);
        String avail = radar.getAttribute(WarplanesEnum.AVAILABLE.getValue()).trim();
        return Boolean.parseBoolean(avail);
    }

    /**
     * Extracts array of two double values, that will be assigned to the minSpan-field
     * and maxSpan-field respectiveky of Warplane.TechChars class instance from the current
     * Element instance, which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private double[] extractSpan(Element plane) {
        Element curElem = (Element) plane.getElementsByTagName(WarplanesEnum.SPAN.getValue()).item(0);
        String[] spans = curElem.getFirstChild().getNodeValue().trim().split("\\p{Space}+");

        return (spans.length == 1)
                ? new double[]{Double.parseDouble(spans[0]), Double.parseDouble(spans[0])}
                : new double[]{Double.parseDouble(spans[0]), Double.parseDouble(spans[1])};
    }

    /**
     * Extracts instance of Warplane.TechChars class, that will be assigned to the techChars-field
     * of Warplane class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
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

    /**
     * Extracts instance of Warplane.Type enumeration, that will be assigned to the type-field
     * of Warplane.TechChars class instance from the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private Warplane.Type extractType(Element plane) {
        Element type = (Element) plane.getElementsByTagName(WarplanesEnum.TYPE.getValue()).item(0);
        return Warplane.Type.valueOf(type.getFirstChild().getNodeValue().trim().toUpperCase());
    }

    /**
     * Extracts instance of Warplane class the current Element instance,
     * which is associated with the distinct plane.
     *
     * @param plane Element, that is associated with the distinct plane.
     * @return extracted value.
     */
    private Warplane extractWarplane(Element plane) {
        String model = extractModel(plane);
        String origin = extractOrigin(plane);
        double price = extractPrice(plane);
        Warplane.TechChars techChars = extractTechChars(plane);
        return new Warplane(model, origin, price, techChars);
    }

    /**
     * Extracts all the instance of Warplane class from the current Element instance,
     * which is associated with the XML document root element.
     *
     * @param root Element, that is associated with the XML document root element.
     * @return Set of extracted instances.
     */
    private Set<Warplane> extractWarplanes(Element root) {
        NodeList planes = root.getElementsByTagName(WarplanesEnum.PLANE.getValue());
        Set<Warplane> result = new HashSet<>();
        for (int i = 0; i < planes.getLength(); i++) {
            result.add(extractWarplane((Element) planes.item(i)));
        }
        return result;
    }
}