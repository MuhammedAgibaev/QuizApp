package com.agibaev.quizapp.util;

import org.angmarch.views.NiceSpinner;

import java.util.LinkedList;
import java.util.List;

public class ViewHelperUtil {
    public static void show(List<String> dataList, NiceSpinner niceSpinner) {
        List<String> data = new LinkedList<>(dataList);
        niceSpinner.attachDataSource(data);
    }
}
