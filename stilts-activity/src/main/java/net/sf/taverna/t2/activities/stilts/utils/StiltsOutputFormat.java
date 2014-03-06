/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/outFormats.html
 * @author christian
 */
public enum StiltsOutputFormat {

    FITS_PLUS("fits-plus","FITS file; primary HDU contains a VOTable representation of the metadata, "
            + "subsequent extensions contain one or more FITS binary tables (behaves the same as fits-basic for most purposes)"),
    FITS_BASIC("fits-basic","FITS file; primary HDU is data-less, subsequent extensions contain a FITS binary table"),
    COLFITS_PLUS("colfits-plus","FITS file containing a BINTABLE with a single row; "
            + "each cell of the row contains a whole column's worth of data. "
            + "The primary HDU also contains a VOTable representation of the metadata."),
    COLFITS_BASIC("colfits-basic","FITS file containing a BINTABLE with a single row; "
            + "each cell of the row contains a whole column's worth of data. The primary HDU contains nothing."),  
    VOTABLE_TABLEDATA("votable-tabledata","VOTable document with TABLEDATA (pure XML) encoding"),
    VOTABLE_BINARY_INLINE("votable-binary-inline","VOTable document with BINARY-encoded data inline within a STREAM element. "
            + "If VOTable 1.3 output is in force (see votable.version system property), votable-binary2-inline is provided instead."),
    VOTABLE_BINARY_HREF("votable-binary-href","VOTable document with BINARY-encoded data in a separate file (only if not writing to a stream). "
            + "If VOTable 1.3 output is in force (see votable.version system property), votable-binary2-href is provided instead."),
    VOTABLE_FITS_HREF("votable-fits-href","VOTable document with FITS-encoded data in a separate file (only if not writing to a stream)"),
    VOTABLE_FITS_INLINE("votable-fits-inline","VOTable document with FITS-encoded data inline within a STREAM element"),
    ASCII("ascii","Simple space-separated ASCII file format"),
    TEXT("text","Human-readable plain text (with headers and column boundaries marked out"),
    CSV("csv","Comma-Separated Value format. The first line is a header which contains the column names."),
    CSV_NOHEADER("csv-noheader","Comma-Separated Value format with no header line."),
    IPAC("ipac","IPAC Table Format."),
    TST("tst","Tab-Separated Table format."),
    HTML("html","Standalone HTML document containing a TABLE element"),
    HTMK_ELEMENT("html-element","HTML TABLE element"),
    LATEX("latex","LaTeX tabular environment"),
    LATEX_DOCUMENT("latex-document","LaTeX standalone document containing a tabular environment"),
    MIRAGE("mirage","Mirage input format"); 
    
    private final String stiltsName;
    private final String description;

    StiltsOutputFormat(String stiltsName, String description){
        this.stiltsName = stiltsName;  
        this.description = description;
    }

    /**
     * @return the stiltsName
     */
    public String getStiltsName() {
        return stiltsName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public static StiltsOutputFormat byStiltsName(String stiltsName){
        for (StiltsOutputFormat format:StiltsOutputFormat.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsOutputFormat known for " + stiltsName);
    }

}
