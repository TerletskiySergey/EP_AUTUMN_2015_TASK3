package tasks.task3.variant8;

public class Warplane {
    public enum Type {
        CHASE_PLANE, FIGHTER, INTERCEPTOR, SPY_PLANE, SUPPORT_AIRCRAFT
    }

    public static class GeomParams {

        private double height;
        private double length;
        private double maxSpan;
        private double minSpan;

        public GeomParams() {
        }

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

    public static class TechChars {

        private int crew;
        private GeomParams geomParams;
        private int missiles;
        private boolean radar;
        private Type type;

        public TechChars() {
            this.geomParams = new GeomParams();
        }

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

        public GeomParams getGeomParams() {
            return geomParams;
        }

        public int getMissiles() {
            return missiles;
        }

        public Type getType() {
            return type;
        }

        public boolean isRadar() {
            return radar;
        }

        public void setCrew(int crew) {
            this.crew = crew;
        }

        public void setMissiles(int missiles) {
            this.missiles = missiles;
        }

        public void setRadar(boolean radar) {
            this.radar = radar;
        }

        public void setType(Type type) {
            this.type = type;
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

    public Warplane() {
        this.techChars = new TechChars();
    }

    public Warplane(String model, String origin, double price, TechChars techChars) {
        this.model = model;
        this.origin = origin;
        this.techChars = techChars;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getOrigin() {
        return origin;
    }

    public double getPrice() {
        return price;
    }

    public TechChars getTechChars() {
        return techChars;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPrice(double price) {
        this.price = price;
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