package tasks.task3.variant8.builders;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Validator;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Class provides StAX approach for parsing instances
 * of Warplane class from XML-document. While parsing methods of
 * DefaultHandler implementation class are invoked i.e. StaX parsing
 * is implemented by the aims of SAX logic.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public class StAXWarplanesBuilder extends AbstractWarplanesBuilder {

    /**
     * Class-adapter, that adapts invocation of XMLStreamReader method
     * next() to the interface of ContentHandler, allowing XML-processing
     * by the aims of SAX logic. Classes' overridden method next() is
     * an intermediary between XMLStreamReader and Validator methods invocation.
     * It allows to perform validating of XML-document against XSD-schema while
     * parsing.
     */
    private class StreamWarplanesReader extends StreamReaderDelegate {

        /**
         * Instance of DefaultHandler interface, to which XML-processing is delegated.
         */
        private SAXWarplanesBuilder.WarplanesHandler handler;

        StreamWarplanesReader(XMLStreamReader in) {
            super(in);
            this.handler = new SAXWarplanesBuilder.WarplanesHandler(StAXWarplanesBuilder.this);
        }

        @Override
        public int next() throws XMLStreamException {
            int token = super.next();
            try {
                switch (token) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement();
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        characters();
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        endElement();
                        break;
                }
            } catch (Exception ex) {
            }
            return token;
        }

        /**
         * Parses XML-document, extracting instances of Warplane class without
         * XML-validation. Extracted instances are gathered in the internal Set
         * of Warplane instances of enclosing class.
         *
         * @throws XMLStreamException
         */
        public void parse() throws XMLStreamException {
            while (hasNext()) {
                next();
            }
        }

        /**
         * Intermediary method adapting StAX interface for invoking of ContentHandler
         * method characters().
         *
         * @throws SAXException in the cases, provided by ContentHandler method characters()
         *                      invocation.
         */
        private void characters() throws SAXException {
            handler.characters(getTextCharacters(), getTextStart(), getTextLength());
        }

        /**
         * Intermediary method adapting StAX interface for invoking of ContentHandler
         * method endElement().
         *
         * @throws SAXException in the cases, provided by ContentHandler method endElement()
         *                      invocation.
         */
        private void endElement() throws SAXException {
            handler.endElement(null, getLocalName(), null);
        }

        /**
         * Method adapts array of available attributes for currently processing XML-element
         * to the Attributes interface. It allows to implement further XML-processing by the
         * aims of SAX logic.
         *
         * @return instance of Attributes,
         * containing available attributes for currently processing XML-element.
         */
        private Attributes getAttributes() {
            AttributesImpl res = new AttributesImpl();
            int attrCount = getAttributeCount();
            for (int i = 0; i < attrCount; i++) {
                res.addAttribute(getAttributeNamespace(i)
                        , getAttributeLocalName(i)
                        , getAttributeName(i).toString()
                        , getAttributeType(i)
                        , getAttributeValue(i));
            }
            return res;
        }

        /**
         * Intermediary method adapting StAX interface for invoking of ContentHandler
         * method startElement().
         *
         * @throws SAXException in the cases, provided by ContentHandler method startElement()
         *                      invocation.
         */
        private void startElement() throws SAXException {
            handler.startElement(null, getLocalName(), null, getAttributes());
        }
    }

    /**
     * StAX implementation for parsing Warplane class instances from XML-documents without
     * document validating against XSD-schema.
     *
     * @param xmlFilePath XML document file path to be parsed.
     */
    @Override
    public void buildWarplanesSet(String xmlFilePath) {
        buildWarplanesSet(xmlFilePath, null);
    }

    /**
     * StAX implementation for parsing Warplane class instances from XML-documents including
     * document validating against XSD-schema while parsing.
     *
     * @param xmlFilePath XML-document file path to be parsed.
     * @param xsdFilePath XSD-schema file path to be validated according to.
     */
    @Override
    public void buildWarplanesSet(String xmlFilePath, String xsdFilePath) {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try {
            initSchema(xsdFilePath);
            Reader fileReader = new FileReader(xmlFilePath);
            XMLStreamReader reader = factory.createXMLStreamReader(fileReader);
            reader = new StreamWarplanesReader(reader);
            if (xsdFilePath == null) {
                ((StreamWarplanesReader) reader).parse();
            } else {
                Validator validator = schema.newValidator();
                validator.validate(new StAXSource(reader));
            }
        } catch (IOException | XMLStreamException | SAXException e) {
            System.err.println(e);
        }
    }
}