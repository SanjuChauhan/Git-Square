package sanju.com.gitsquare.controller;

import java.util.Comparator;

import sanju.com.gitsquare.model.Contributor;

/**
 * Created by eheuristic on 23/08/2016.
 */
public class ContributionComparator implements Comparator<Contributor> {
    @Override
    public int compare(Contributor lhs, Contributor rhs) {

        if (lhs.getContributions() != null && rhs.getContributions() != null) {
            float r1 = Float.parseFloat(lhs.getContributions());
            float r2 = Float.parseFloat(rhs.getContributions());
                        /*For ascending order*/
            return (int) (r1 - r2);
            /*For descending order*/
            //r2-r1;
        } else {
            return 0;
        }
    }
}
