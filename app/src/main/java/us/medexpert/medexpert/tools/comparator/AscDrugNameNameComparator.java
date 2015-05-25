package us.medexpert.medexpert.tools.comparator;

import java.util.Comparator;

import us.medexpert.medexpert.db.entity.Product;

/**
 * Created by user on 25.05.15.
 */
public class AscDrugNameNameComparator implements Comparator<Product> {

    @Override
    public int compare(Product lhs, Product rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }
}
