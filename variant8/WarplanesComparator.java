package tasks.task3.variant8;

import java.util.*;

/**
 * Class provides a universal way to compare Warplane class instances
 * depending on the values of WarplaneEnum passed to the class constructor.
 * Each passed WarplaneEnum instance has to be associated with the field
 * of Warplane instance, otherwise such WarplaneEnum values are ignored.
 * Class allows to sort collections containing Warplane instances by some
 * parameters at the same time in an ascending order.
 * If the logic of comparison doesn't meet the requirements it can be overridden.
 *
 * @author Sergey Terletskiy
 * @version 1.0 12/12/2015
 */
public class WarplanesComparator implements Comparator<Warplane> {
    /**
     * Constant for double values comparison.
     */
    protected static final double DOUBLE_EPS = 1e-7;

    /**
     * Array of enum constants, with which appropriate fields are associated.
     */
    private WarplanesEnum[] fields;

    /**
     * Creates initialized class instance
     */
    public WarplanesComparator(WarplanesEnum... fields) {
        this.fields = fields;
    }

    @Override
    public int compare(Warplane o1, Warplane o2) {
        int res = 0;
        for (WarplanesEnum field : fields) {
            res = compare(o1, o2, field);
            if (res != 0) {
                return res;
            }
        }
        return res;
    }

    /**
     * Implementation of comparison logic depending on the value of
     * WarplanesEnum constant.
     *
     * @param o1    first Warplane class instance to be compared.
     * @param o2    second Warplane class instance to be compared.
     * @param field flag, that defines logic of comparison.
     * @return result of comparison.
     */
    protected int compare(Warplane o1, Warplane o2, WarplanesEnum field) {
        switch (field) {
            case MODEL:
                return o1.getModel().compareTo(o2.getModel());
            case ORIGIN:
                return o1.getOrigin().compareTo(o2.getOrigin());
            case TYPE:
                return o1.getTechChars().getType().name()
                        .compareTo(o2.getTechChars().getType().name());
            case CREW:
                return o1.getTechChars().getCrew()
                        - o2.getTechChars().getCrew();
            case MISSILE:
                return o1.getTechChars().getMissiles()
                        - o2.getTechChars().getMissiles();
            case HEIGHT:
                return compare(o1.getTechChars().getGeomParams().getHeight()
                        , o2.getTechChars().getGeomParams().getHeight());
            case LENGTH:
                return compare(o1.getTechChars().getGeomParams().getLength()
                        , o2.getTechChars().getGeomParams().getLength());
            case PRICE:
                return compare(o1.getPrice(), o2.getPrice());
        }
        return 0;
    }

    /**
     * Compares two double values. As the threshold for comparison class constant
     * DOUBLE_EPS is used.
     *
     * @param d1 first double value to be compared.
     * @param d2 second double value to be compared.
     * @return result of comparison.
     */
    protected int compare(double d1, double d2) {
        double delta = d1 - d2;
        return (delta < 0)
                ? -1
                :
                (delta <= DOUBLE_EPS)
                        ? 0
                        : 1;
    }
}
