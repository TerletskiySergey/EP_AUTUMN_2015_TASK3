package tasks.task3.variant8;

import tasks.task3.variant8.builders.AbstractWarplanesBuilder;
import tasks.task3.variant8.builders.DOMWarplanesBuilder;
import tasks.task3.variant8.builders.SAXWarplanesBuilder;
import tasks.task3.variant8.builders.StAXWarplanesBuilder;

public class WarplanesBuilderFactory {
    public enum Type {
        DOM, SAX, StAX
    }

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