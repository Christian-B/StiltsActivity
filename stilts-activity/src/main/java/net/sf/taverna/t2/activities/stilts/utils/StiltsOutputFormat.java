package net.sf.taverna.t2.activities.stilts.utils;

/**
 * Specifies the format of the output table.
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/outFormats.html
 * @author christian
 */
public enum StiltsOutputFormat implements DescribableInterface {

    FITS_PLUS("fits-plus","FITS plus format",
            "FITS file; primary HDU contains a VOTable representation of the metadata, "
            + "subsequent extensions contain one or more FITS binary tables (behaves the same as fits-basic for most purposes)"),
    FITS_BASIC("fits-basic","FITS basic format",
            "FITS file; primary HDU is data-less, subsequent extensions contain a FITS binary table"),
    COLFITS_PLUS("colfits-plus", "Column-oriented FITS plus",
            "FITS file containing a BINTABLE with a single row; "
            + "each cell of the row contains a whole column's worth of data. "
            + "The primary HDU also contains a VOTable representation of the metadata."),
    COLFITS_BASIC("colfits-basic", "Column-oriented FITS basic",
            "FITS file containing a BINTABLE with a single row; "
            + "each cell of the row contains a whole column's worth of data. The primary HDU contains nothing."),  
    VOTABLE_TABLEDATA("votable-tabledata","VOTable document with TABLEDATA"
            ,"VOTable document with TABLEDATA (pure XML) encoding"),
    VOTABLE_BINARY_INLINE("votable-binary-inline","VOTable including BINARY-encoded data"
            ,"VOTable document with BINARY-encoded data inline within a STREAM element. "
            + "If VOTable 1.3 output is in force (see votable.version system property), votable-binary2-inline is provided instead."),
    VOTABLE_BINARY_HREF("votable-binary-href","VOTable with separate BINARY-encoded data",
            "VOTable document with BINARY-encoded data in a separate file (only if not writing to a stream). "
            + "If VOTable 1.3 output is in force (see votable.version system property), votable-binary2-href is provided instead."),
    VOTABLE_FITS_HREF("votable-fits-href","VOTable with separate FITS-encoded data",
            "VOTable document with FITS-encoded data in a separate file (only if not writing to a stream)"),
    VOTABLE_FITS_INLINE("votable-fits-inline","VOTable with including FITS-encoded data",
            "VOTable document with FITS-encoded data inline within a STREAM element"),
    ASCII("ascii","Whitespace Separated Value format","Simple space-separated ASCII file format"),
    TEXT("text","Human-readable plain text",
            "Human-readable plain text (with headers and column boundaries marked out"),
    CSV("csv","Comma-Separated Value format",
            "Comma-Separated Value format. The first line is a header which contains the column names."),
    CSV_NOHEADER("csv-noheader","Headerless Comma-Separated Value format","Comma-Separated Value format with no header line."),
    IPAC("ipac","IPAC Table Format","IPAC Table Format."),
    TST("tst","Tab-Separated Table format","Tab-Separated Table format."),
    HTML("html","Standalone HTML TABLE","Standalone HTML document containing a TABLE element"),
    HTMK_ELEMENT("html-element","HTML TABLE element","HTML TABLE element"),
    LATEX("latex","LaTeX tabular environment","LaTeX tabular environment"),
    LATEX_DOCUMENT("latex-document","Standalone latex-document",
            "LaTeX standalone document containing a tabular environment"),
    MIRAGE("mirage","Mirage input format","Mirage input format"); 
    
    private final String stiltsName;
    private final String userName;
    private final String description;

    StiltsOutputFormat(String stiltsName, String userName, String description){
        this.stiltsName = stiltsName; 
        this.userName = userName;
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
    
    /*public static StiltsOutputFormat byStiltsName(String stiltsName){
        for (StiltsOutputFormat format:StiltsOutputFormat.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsOutputFormat known for " + stiltsName);
    }*/

    @Override
    public String toString(){
        return userName;
    }

}
