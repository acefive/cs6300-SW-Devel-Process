package edu.gatech.coffeecartrewards.utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class Utils {

    public static <In, Out> List<Out> map(List<In> in, Func<In, Out> f) {
        List<Out> out = new ArrayList<Out>(in.size());
        for (In inObj : in) {
            out.add(f.apply(inObj));
        }
        return out;
    }
    

    public static List<Integer> range(final int begin, final int end) {
        return new AbstractList<Integer>() {
                @Override
                public Integer get(int index) {
                    return begin + index;
                }

                @Override
                public int size() {
                    return end - begin;
                }
            };
    }
}
