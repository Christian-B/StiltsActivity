package net.sf.taverna.t2.activities.table.utils;

/**
 * Specifies the format of the output table.
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/outFormats.html
 * @author christian
 */
public enum TableOutputFormat implements DescribableInterface {

    CSV("csv","Comma-Separated Value format",
            "Comma-Separated Value format. The first line is a header which contains the column names."),
    CSV_NOHEADER("csv-noheader","Comma-Separated Value (Headerless ) format","Comma-Separated Value format with no header line."),
    HTML("html","Html Table (Standalone)","Standalone HTML document containing a TABLE element"),
    HTMK_ELEMENT("html-element","Html Table (element)","HTML TABLE element"),
    TST("tst","Tab-Separated Table format","Tab-Separated Table format."),
    LATEX("latex","LaTeX table (element)","LaTeX tabular environment"),
    LATEX_DOCUMENT("latex-document","LaTex table (standalone)",
            "LaTeX standalone document containing a tabular environment"),
    TEXT("text","Plain text (human-readable)",
            "Human-readable plain text (with headers and column boundaries marked out"),
    ASCII("ascii","Whitespace Separated Value format","Simple space-separated ASCII file format"),

    FITS_BASIC("fits-basic","FITS basic format",
            "FITS file; primary HDU is data-less, subsequent extensions contain a FITS binary table"),
    COLFITS_BASIC("colfits-basic", "FITS basic (Column-oriented)",
            "FITS file containing a BINTABLE with a single row;\n"
            + "each cell of the row contains a whole column's worth of data.\n"
            + " The primary HDU contains nothing."),  
    FITS_PLUS("fits-plus","FITS plus format",
            "FITS file; primary HDU contains a VOTable representation of the metadata,\n"
            + "subsequent extensions contain one or more FITS binary tables (behaves the same as fits-basic for most purposes)"),
    COLFITS_PLUS("colfits-plus", "FITS plus (Column-oriented)",
            "FITS file containing a BINTABLE with a single row;\n"
            + "each cell of the row contains a whole column's worth of data.\n"
            + "The primary HDU also contains a VOTable representation of the metadata.\n"),
    IPAC("ipac","IPAC Table Format","IPAC Table Format."),
    MIRAGE("mirage","Mirage input format","Mirage input format"),
    VOTABLE_TABLEDATA("votable-tabledata","VOTable document with TABLEDATA"
            ,"VOTable document with TABLEDATA (pure XML) encoding"),
    VOTABLE_BINARY_INLINE("votable-binary-inline","VOTable including BINARY-encoded data"
            ,"VOTable document with BINARY-encoded data inline within a STREAM element.\n"
            + "If VOTable 1.3 output is in force (see votable.version system property),\n"
            + "votable-binary2-inline is provided instead."),
    VOTABLE_BINARY_HREF("votable-binary-href","VOTable with separate BINARY-encoded data",
            "VOTable document with BINARY-encoded data in a separate file (only if not writing to a stream).\n"
            + "If VOTable 1.3 output is in force (see votable.version system property),\n"
            + "votable-binary2-href is provided instead."),
    VOTABLE_FITS_HREF("votable-fits-href","VOTable with separate FITS-encoded data",
            "VOTable document with FITS-encoded data in a separate file (only if not writing to a stream)"),
    VOTABLE_FITS_INLINE("votable-fits-inline","VOTable with including FITS-encoded data",
            "VOTable document with FITS-encoded data inline within a STREAM element");
    
    private final String stiltsName;
    private final String userName;
    private final String description;

    TableOutputFormat(String stiltsName, String userName, String description){
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
    
    /*public static TableOutputFormat byStiltsName(String stiltsName){
        for (TableOutputFormat format:TableOutputFormat.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No TableOutputFormat known for " + stiltsName);
    }*/

    @Override
    public String toString(){
        return userName;
    }

}
