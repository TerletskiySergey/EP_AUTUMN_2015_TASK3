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

public class SAXWarplanesBuilder extends AbstractWarplanesBuilder {

    private class WarplanesHandler extends DefaultHandler {

        private EnumSet<WarplanesEnum> constSet;
        private Attributes curAttr;
        private WarplanesEnum curElement;
        private Warplane curPlane;

        WarplanesHandler() {
            constSet = EnumSet.range(WarplanesEnum.MODEL, WarplanesEnum.PRICE);
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
                SAXWarplanesBuilder.this.warplanes.add(curPlane);
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

        private double calcValue(String dim) {
            WarplanesEnum units = WarplanesEnum.fromString(curAttr.getValue(0).trim());
            double res = Double.parseDouble(dim);
            return convert(res, units);
        }

        private double footToMeter(double foot) {
            return foot / 0.3048;
        }

        private double yardToMeter(double yard) {
            return yard / 0.9144;
        }
    }

    @Override
    public void buildWarplanesSet(String xmlFilePath) {
        buildWarplanesSet(xmlFilePath, null);
    }

    @Override
    public void buildWarplanesSet(String xmlFilePath, String xsdFilePath) {
        SAXParserFactory spFactory = SAXParserFactory.newInstance();
        try {
            initSchema(xsdFilePath);
            spFactory.setSchema(schema);
//            Validator v = schema.newValidator();
//            v.setErrorHandler(new WarplanesHandler());
//            v.validate(new StreamSource(xmlFilePath));
            spFactory.setNamespaceAware(true);
            SAXParser parser = spFactory.newSAXParser();
            parser.parse(xmlFilePath, new WarplanesHandler());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}