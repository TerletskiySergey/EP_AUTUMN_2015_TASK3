package tasks.task3.variant8;

import tasks.task3.variant8.builders.AbstractWarplanesBuilder;
import tasks.task3.variant8.builders.DOMWarplanesBuilder;
import tasks.task3.variant8.builders.SAXWarplanesBuilder;
import tasks.task3.variant8.builders.StAXWarplanesBuilder;

/**
 * Class providing creation of different implementations of AbstractWarplanesBuilder
 * by the aims of factory-method using.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public class WarplanesBuilderFactory {

    /**
     * Enumeration represents list of available implementations of AbstractWarplanesBuilder.
     */
    public enum Type {
        DOM, SAX, StAX
    }

    /**
     * Factory method, that creates different implementations of AbstractWarplanesBuilder
     * depending on the value of input parameter.
     *
     * @param type enumeration constant indicating the type of AbstractWarplanesBuilder
     *             to be created.
     * @return instance of AbstractWarplanesBuilder implementation.
     */
    public static AbstractWarplanesBuilder newInstance(Type type) {
        switch (type) {
            case DOM:
                return new DOMWarplanesBuilder();
            case SAX:
                return new SAXWarplanesBuilder();
            default:
                return new StAXWarplanesBuilder();
        }
    }
}