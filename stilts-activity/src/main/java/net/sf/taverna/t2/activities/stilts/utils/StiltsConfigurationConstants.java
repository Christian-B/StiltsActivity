package net.sf.taverna.t2.activities.stilts.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class StiltsConfigurationConstants implements Serializable {
 
    //Sourced from http://www.star.bristol.ac.uk/~mbt/stilts/sun256/inFormats.html
    public static final String[] VALID_INPUT_FORMATS_ARRAY = {
        "fits",
        "colfits",
        "votable",
        "cdf",
        "ascii",
        "csv",
        "tst",
        "ipac",
        "wdc"};
    public static final List<String> VALID_INPUT_FORMATS_LIST = Arrays.asList(VALID_INPUT_FORMATS_ARRAY);
    //Sourced from http://www.star.bristol.ac.uk/~mbt/stilts/sun256/outFormats.html
    public static final String[] VALID_OUTPUT_FORMATS_ARRAY = {
        "fits-plus","fits-basic",
        "colfits-plus","colfits-basic",
        "votable-tabledata","votable-binary-inline","votable-binary-href","votable-fits-href","votable-fits-inline",
        "ascii",
        "text",
        "csv","csv-noheader",
        "ipac",
        "tst",
        "html","html-element",
        "latex","latex-document",
        "mirage"};
    public static final List<String> VALID_OUTPUT_FORMATS_LIST = Arrays.asList(VALID_OUTPUT_FORMATS_ARRAY);
 
    public static final String FILE_PATH_TYPE = "filePath";
    public static final String STRING_TYPE = "String";
    public static final String URI_TYPE = "URI";
    public static final String[] VALID_INPUT_TYPE_ARRAY = {FILE_PATH_TYPE, STRING_TYPE, URI_TYPE};
    public static final List<String> VALID_INPUT_TYPE_LIST = Arrays.asList(VALID_INPUT_TYPE_ARRAY);
    public static final String[] VALID_OUTPUT_TYPE_ARRAY = {FILE_PATH_TYPE, STRING_TYPE};
    public static final List<String> VALID_OUTPUT_TYPE_LIST = Arrays.asList(VALID_OUTPUT_TYPE_ARRAY);
    
}
