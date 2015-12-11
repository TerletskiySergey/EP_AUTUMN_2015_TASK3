package tasks.task3.variant8;

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

    private String str;

    public String getValue() {
        return this.str;
    }

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

    WarplanesEnum(String str) {
        this.str = str;
    }

    public static void main(String[] args) {
        WarplanesEnum.CREW.str = null;
        System.out.println();
    }
}