package tasks.task3.variant8;

import tasks.task3.variant8.builders.AbstractWarplanesBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class WarplanesRunner {

    private static final String PROPS_FILEPATH = "src/tasks/task3/variant8/resources/conf.properties";
    private static final String XML_FILEPATH_PROP = "xml.filePath";
    private static final String XSD_FILEPATH_PROP = "xsd.filePath";

    private static Properties prop = initProp();

    private static Properties initProp() {
        Properties res = new Properties();
        try {
            res.load(new FileInputStream(PROPS_FILEPATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        String xmlFilePath = prop.getProperty(XML_FILEPATH_PROP);
        String xsdFilePath = prop.getProperty(XSD_FILEPATH_PROP);
        AbstractWarplanesBuilder builder = WarplanesBuilderFactory.newInstance(WarplanesBuilderFactory.Type.StAX);
        builder.buildWarplanesSet(xmlFilePath, xsdFilePath);
        List<Warplane> warplanes = new ArrayList<>(builder.getWarplanesSet());
        Comparator<Warplane> comp = new WarplanesComparator(WarplanesEnum.MODEL);
        Collections.sort(warplanes, comp);
        System.out.println(warplanes);
    }
}