package com.project.stress.models;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
    public static String name = "";
    public static List<ListElement> ratingList = new ArrayList<>();
    public static int numOfElements;
    public static int numMarked = 0;

    public static Dataset dataset = new DefaultDataset();

}
