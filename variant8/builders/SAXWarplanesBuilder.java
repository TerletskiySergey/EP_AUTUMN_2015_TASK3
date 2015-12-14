package tasks.task3.variant8.builders;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import tasks.task3.variant8.Warplane;
import tasks.task3.variant8.WarplanesEnum;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Class provides SAX approach for parsing instances
 * of Warplane class from XML document.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public class SAXWarplanesBuilder extends AbstractWarplanesBuilder {

    /**
     * Class provides implementation of ContentHandler and ErrorHandler
     * interfaces.
     */
    public static class WarplanesHandler extends DefaultHandler {

        /**
         * Instance to store parsed Warplane objects in.
         */
        private AbstractWarplanesBuilder builder;
        /**
         * Set of WarplanesEnum instances, that correspond only to those XML-document elements,
         * which text content is to be extracted.
         */
        private EnumSet<WarplanesEnum> constSet;

        /**
         * Represents an array of attributes, associated with currently processing XML-element.
         */
        private Attributes curAttr;
        /**
         * Represents name of a currently processing XML-element.
         */
        private WarplanesEnum curElement;
        /**
         * Represents currently initializing instance of Warplane class.
         */
        private Warplane curPlane;

        public WarplanesHandler(AbstractWarplanesBuilder builder) {
            constSet = EnumSet.range(WarplanesEnum.MODEL, WarplanesEnum.PRICE);
            this.builder = builder;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch, start, length).trim();
            if (curElement != null) {
                switch (curElement) {
                    case MODEL:
                        curPlane.setModel(content);
                        break;
                    case ORIGIN:
                        curPlane.setOrigin(content);
                        break;
                    case TYPE:
                        curPlane.getTechChars()
                                .setType(Warplane.Type.valueOf(content.toUpperCase()));
                        break;
                    case CREW:
                        curPlane.getTechChars()
                                .setCrew(Integer.parseInt(content));
                        break;
                    case MISSILE:
                        curPlane.getTechChars()
                                .setMissiles(Integer.parseInt(content));
                        break;
                    case LENGTH:
                        curPlane.getTechChars().getGeomParams()
                                .setLength(calcValue(content));
                        break;
                    case SPAN:
                        String[] spans = content.split("\\p{Space}");
                        Warplane.GeomParams geomParams = curPlane.getTechChars().getGeomParams();
                        geomParams.setMinSpan(calcValue(spans[0]));
                        geomParams.setMaxSpan((spans.length == 1)
                                ? geomParams.getMinSpan()
                                : calcValue(spans[1]));
                        break;
                    case HEIGHT:
                        curPlane.getTechChars().getGeomParams()
                                .setHeight(calcValue(content));
                        break;
                    case PRICE:
                        curPlane.setPrice(calcValue(content));
                        break;
                    default:
                        throw new EnumConstantNotPresentException(curElement.getDeclaringClass()
                                , curElement.name());
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (localName.equals(WarplanesEnum.PLANE.getValue())) {
                builder.warplanes.add(curPlane);
                curPlane = null;
                return;
            }
            if (constSet.contains(WarplanesEnum.fromString(localName))) {
                curElement = null;
                curAttr = null;
            }
        }

        @Override
        public void error(SAXParseException ex) throws SAXException {
            throw ex;
        }

        @Override
        public void fatalError(SAXParseException ex) throws SAXException {
            throw ex;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if (localName.equals(WarplanesEnum.PLANE.getValue())) {
                curPlane = new Warplane();
                return;
            }
            if (localName.equals(WarplanesEnum.RADAR.getValue())) {
                curPlane.getTechChars().setRadar(Boolean.parseBoolean(atts.getValue(0).trim()));
                return;
            }
            WarplanesEnum temp;
            if (constSet.contains(temp = WarplanesEnum.fromString(localName))) {
                curAttr = atts;
                curElement = temp;
            }
        }

        @Override
        public void warning(SAXParseException ex) throws SAXException {
            throw ex;
        }

        /**
         * Method for conversion of XML-element text content into a double value.
         * Method is called when element's content interpretation depends on the value
         * of element's attribute.
         *
         * @param dim String content of the XML-element to be converted.
         *            of the attribute, on which depends the logic of conversion.
         * @return converted value.
         */
        private double calcValue(String dim) {
            WarplanesEnum units = WarplanesEnum.fromString(curAttr.getValue(0).trim());
            double res = Double.parseDouble(dim);
            return builder.convert(res, units);
        }
    }

    /**
     * SAX implementation for parsing Warplane class instances from XML-documents without
     * document validating against XSD-schema.
     *
     * @param xmlFilePath XML document file path to be parsed.
     */
    @Override
    public void buildWarplanesSet(String xmlFilePath) {
        buildWarplanesSet(xmlFilePath, null);
    }

    /**
     * SAX implementation for parsing Warplane class instances from XML-documents including
     * document validating against XSD-schema while parsing.
     *
     * @param xmlFilePath XML-document file path to be parsed.
     * @param xsdFilePath XSD-schema file path to be validated according to.
     */
    @Override
    public void buildWarplanesSet(String xmlFilePath, String xsdFilePath) {
        SAXParserFactory spFactory = SAXParserFactory.newInstance();
        try {
            initSchema(xsdFilePath);
            spFactory.setSchema(schema);
            spFactory.setNamespaceAware(true);
            SAXParser parser = spFactory.newSAXParser();
            parser.parse(xmlFilePath, new WarplanesHandler(this));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println(e);
        }
    }
}