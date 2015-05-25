package us.medexpert.medexpert.tools.comparator;

import java.util.Comparator;

import us.medexpert.medexpert.db.entity.Product;

/**
 * Created by user on 25.05.15.
 */
public class AscDrugDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product lhs, Product rhs) {
        long diff = lhs.getDate_v() - rhs.getDate_v();
        if(diff > 0) {
            return 1;
        } else if(diff < 0) {
            return  -1;
        } else {
            return 0;
        }
    }
}
