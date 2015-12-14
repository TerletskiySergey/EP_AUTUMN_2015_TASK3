package tasks.task3.variant8;

/**
 * Abstraction of the class represents a warplane.
 * Instance of the class contains such information about the warplane as:
 * model, origin, price, technical characteristics.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public class Warplane {
    /**
     * Enumerations represents types of warplanes.
     */
    public enum Type {
        CHASE_PLANE, FIGHTER, INTERCEPTOR, SPY_PLANE, SUPPORT_AIRCRAFT
    }

    /**
     * Class represents such geometric parameters of a warplane as:
     * height, length, minimal span, maximal span.
     */
    public static class GeomParams {

        private double height;
        private double length;
        private double maxSpan;
        private double minSpan;

        /**
         * Constructs a non-initialized instance.
         */
        public GeomParams() {
        }

        /**
         * Constructs an initialized instance.
         */
        public GeomParams(double length, double minSpan, double maxSpan, double height) {

            this.length = length;
            this.minSpan = minSpan;
            this.maxSpan = maxSpan;
            this.height = height;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public double getMaxSpan() {
            return maxSpan;
        }

        public void setMaxSpan(double maxSpan) {
            this.maxSpan = maxSpan;
        }

        public double getMinSpan() {
            return minSpan;
        }

        public void setMinSpan(double minSpan) {
            this.minSpan = minSpan;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("GEOMETRIC PARAMETERS: ").append("\n");
            sb.append("\t").append("length: ").append(length).append("\n");
            sb.append("\t").append("minSpan: ").append(minSpan).append("\n");
            sb.append("\t").append("maxSpan: ").append(maxSpan).append("\n");
            sb.append("\t").append("height: ").append(height).append("\n");
            return sb.toString();
        }
    }

    /**
     * Class represents such technical characteristics of a warplane as:
     * type of plane, crew, missiles quantity, availability of radar,
     * geometrical parameters.
     */
    public static class TechChars {

        private int crew;
        private GeomParams geomParams;
        private int missiles;
        private boolean radar;
        private Type type;

        /**
         * Constructs a non-initialized instance.
         */
        public TechChars() {
            this.geomParams = new GeomParams();
        }

        /**
         * Constructs initialized instance.
         */
        public TechChars(Type type, int crew, int missles, boolean radar, GeomParams geomParams) {

            this.type = type;
            this.crew = crew;
            this.missiles = missles;
            this.radar = radar;
            this.geomParams = geomParams;
        }

        public int getCrew() {
            return crew;
        }

        public void setCrew(int crew) {
            this.crew = crew;
        }

        public GeomParams getGeomParams() {
            return geomParams;
        }

        public int getMissiles() {
            return missiles;
        }

        public void setMissiles(int missiles) {
            this.missiles = missiles;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public boolean isRadar() {
            return radar;
        }

        public void setRadar(boolean radar) {
            this.radar = radar;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TECHNICAL CHARACTERISTICS: ").append("\n");
            sb.append("\t").append("type: ").append(type).append("\n");
            sb.append("\t").append("crew: ").append(crew).append("\n");
            sb.append("\t").append("missiles: ").append(missiles).append("\n");
            sb.append("\t").append("radar: ").append(radar).append("\n");
            sb.append(geomParams).append("\n");
            return sb.toString();
        }
    }

    private String model;
    private String origin;
    private double price;
    private TechChars techChars;

    /**
     * Constructs a non-initialized instance.
     */
    public Warplane() {
        this.techChars = new TechChars();
    }

    /**
     * Constructs initialized instance.
     */
    public Warplane(String model, String origin, double price, TechChars techChars) {
        this.model = model;
        this.origin = origin;
        this.techChars = techChars;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TechChars getTechChars() {
        return techChars;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WARPLANE: ").append("\n");
        sb.append("model: ").append(model).append("\n");
        sb.append("origin: ").append(origin).append("\n");
        sb.append("price: ").append(price).append("\n");
        sb.append(techChars);
        return sb.toString();
    }
}