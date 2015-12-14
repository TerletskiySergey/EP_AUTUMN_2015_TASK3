package tasks.task3.variant8.builders;

import org.xml.sax.SAXException;
import tasks.task3.variant8.Warplane;
import tasks.task3.variant8.WarplanesEnum;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class providing an interface for parsing instances
 * of Warplane class from XML document.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public abstract class AbstractWarplanesBuilder {

    /**
     * Schema instance that is used for XML validating against XSD schema.
     */
    protected Schema schema;
    /**
     * Collection of parsed Warplane class instances.
     */
    protected Set<Warplane> warplanes;

    public AbstractWarplanesBuilder() {
        this.warplanes = new HashSet<>();
    }

    /**
     * Abstract method to parse Warplane class instances from XML-documents without
     * document validating against XSD-schema.
     *
     * @param xmlFilePath XML document file path to be parsed.
     */
    public abstract void buildWarplanesSet(String xmlFilePath);

    /**
     * Abstract method to parse Warplane class instances from XML-documents including
     * document validating against XSD-schema while parsing.
     *
     * @param xmlFilePath XML-document file path to be parsed.
     * @param xsdFilePath XSD-schema file path to be validated according to.
     */
    public abstract void buildWarplanesSet(String xmlFilePath, String xsdFilePath);

    /**
     * Method to receive parsed Warplane class instances.
     *
     * @return Set of parsed warplane instances.
     */
    public Set<Warplane> getWarplanesSet() {
        return this.warplanes;
    }

    /**
     * Utility method used for conversion of input double value.
     * Logic of conversion depends on the value of WarplanesEnum.
     * Method is helpful while parsing values required for Warplane
     * class instances initialization.
     *
     * @param val  value to be converted.
     * @param type enumeration value, that determines the logic of conversion.
     * @return converted double value.
     */
    protected double convert(double val, WarplanesEnum type) {
        switch (type) {
            case METER:
                return val;
            case YARD:
                return yardToMeter(val);
            case FOOT:
                return footToMeter(val);
            case HUNDRED:
                return val * 1e2;
            case THOUSAND:
                return val * 1e3;
            case MILLION:
                return val * 1e6;
            default:
                return 0;
        }
    }

    /**
     * Utility method used for conversion of input double value in foot units into
     * meter units. Method is helpful while parsing values required for
     * Warplane class instances initialization.
     *
     * @param foot value to be converted.
     * @return converted double value.
     */
    protected double footToMeter(double foot) {
        return foot / 0.3048;
    }


    /**
     * Initializes private Schema field, that is used for validation of
     * XML document against XSD schema.
     *
     * @param xsdFilePath XSD schema file path.
     * @throws SAXException if the initialization wasn't successful.
     */
    protected void initSchema(String xsdFilePath) throws SAXException {
        if (xsdFilePath == null) {
            this.schema = null;
            return;
        }
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = schemaFactory.newSchema(new File(xsdFilePath));
    }

    /**
     * Utility method used for conversion of input double value in yard units into
     * meter units. Method is helpful while parsing values required for
     * Warplane class instances initialization.
     *
     * @param yard value to be converted.
     * @return converted double value.
     */
    protected double yardToMeter(double yard) {
        return yard / 0.9144;
    }
}