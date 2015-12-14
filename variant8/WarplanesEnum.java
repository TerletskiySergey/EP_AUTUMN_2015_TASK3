package tasks.task3.variant8;

/**
 * Enumeration contains the list of constants associated with
 * tag-names and attribute-names of XML-document,
 * in which instances of Warplane class are stored.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public enum WarplanesEnum {
    PLANES("planes")
    , PLANE("plane")
    , MODEL("model")
    , ORIGIN("origin")
    , TYPE("type")
    , CREW("crew")
    , MISSILE("missile")
    , SPAN("span")
    , HEIGHT("height")
    , LENGTH("length")
    , PRICE("price")
    , CHARACTERISTICS("tech-chars")
    , ARMAMENT("armament")
    , RADAR("radar")
    , AVAILABLE("avail")
    , GEOM_PARAMETERS("geom-pars")
    , UNITS("units")
    , METER("meter")
    , YARD("yard")
    , FOOT("foot")
    , ORDER("order")
    , HUNDRED("hundred")
    , THOUSAND("thousand")
    , MILLION("million");

    /**
     * Name of the XML-tag or XML-attribute,
     * with which current constant is associated.
     */
    private String str;

    WarplanesEnum(String str) {
        this.str = str;
    }

    /**
     * Allows to receive enumeration constant depending
     * on the name of XML-tag or XML-attribute, with which
     * the constant is associated. Case of the name is ignored.
     *
     * @param from name of XML-tag or XML-attribute, with which
     *             the constant is associated.
     * @return enumeration constant.
     */
    public static WarplanesEnum fromString(String from) {
        WarplanesEnum[] values = values();
        from = from.toLowerCase();
        for (WarplanesEnum value : values) {
            if (value.str.equals(from)) {
                return value;
            }
        }
        return null;
    }

    public String getValue() {
        return this.str;
    }

}