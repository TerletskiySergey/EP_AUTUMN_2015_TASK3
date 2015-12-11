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

public abstract class AbstractWarplanesBuilder {

    protected Set<Warplane> warplanes;
    protected Schema schema;

    protected void initSchema(String xsdFilePath) throws SAXException {
        if (xsdFilePath == null) {
            this.schema = null;
            return;
        }
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = schemaFactory.newSchema(new File(xsdFilePath));
    }

    protected double convert(double val, WarplanesEnum type){
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

    protected double footToMeter(double foot) {
        return foot / 0.3048;
    }

    protected double yardToMeter(double yard) {
        return yard / 0.9144;
    }

    public AbstractWarplanesBuilder() {
        this.warplanes = new HashSet<>();
    }

    public abstract void buildWarplanesSet(String xmlFilePath);

    public abstract void buildWarplanesSet(String xmlFilePath, String xsdFilePath);

    public Set<Warplane> getWarplanesSet() {
        return this.warplanes;
    }
}