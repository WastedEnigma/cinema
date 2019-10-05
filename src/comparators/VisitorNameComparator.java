package comparators;

import cinema.people.Visitor;

import java.util.Comparator;

public class VisitorNameComparator implements Comparator<Visitor> {

    @Override
    public int compare(Visitor v1, Visitor v2) {
        return v1.getFirstName().compareTo(v2.getFirstName());
    }
}
