/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/inFormats.html
 * @author christian
 */
public enum StiltsInputFormat {
     FITS("fits", "FITS format - FITS binary or ASCII tables can be read. "
             + "For commands which take a single input table, by default the first table HDU in the file will used, "
             + "but this can be altered for multi-extension FITS files by supplying an identifier after a '#' sign. "
             + "The identifier can be either an HDU index or the extension name (EXTNAME header, "
             + "possibly followed by \"-\" and the EXTVER header), so \"table.fits#3\" means the third HDU extension, "
             + "and \"table.fits#UV_DATA\" means the HDU with the value \"UV_DATA\" for its EXTNAME header card."),
     COLFITS("colfits", "Column-oriented FITS format. "
             + "This is where a table is stored as a BINTABLE extension which contains a single row, "
             + "each cell of the row containing a whole column of the table it represents. "
             + "This has different performance characteristics from normal FITS tables; "
             + "in particular it may be considerably more efficient for very large, "
             + "and especially very wide tables where not all of the columns are required at any one time. "
             + "Only available for uncompressed files on disk."),
     VOTABLE("votable", "VOTable format - any legal version 1.0, 1.1 or 1.2 format VOTable documents, "
             + "and many illegal ones, can be read. For commands which take a single input table, "
             + "by default the first TABLE element in the document is used, "
             + "but this can be altered by supplying the 0-based index after a '#' sign, "
             + "so \"table.xml#4\" means the fifth TABLE element in the document. "),
     //votable 1.3 only added in Stilts 2.5
     ///VOTABLE("votable", "VOTable format - any legal version 1.0, 1.1, 1.2 or 1.3 format VOTable documents, "
     //        + "and many illegal ones, can be read. For commands which take a single input table, "
     //        + "by default the first TABLE element in the document is used, "
     //        + "but this can be altered by supplying the 0-based index after a '#' sign, "
     //        + "so \"table.xml#4\" means the fifth TABLE element in the document. "),
     //Ony added in 2.5.1
     //CDF("cdf", "NASA Common Data Format. CDF is described at http://cdf.gsfc.nasa.gov/."),
     ASCII("ascii", "Plain text file with one row per column in which columns are separated by whitespace."),
     CSV("csv", "Comma-Separated Values format, using approximately the conventions used by MS Excel."),
     TST("tst", "Tab-Separated Table format, as used by Starlink's GAIA and ESO's SkyCat amongst other tools."),
     IPAC("ipac", "IPAC Table Format."),
     WDC("wdc", "World Datacentre Format (experimental). ");

    private final String stiltsName;
    private final String description;

    StiltsInputFormat(String stiltsName, String description){
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
    
    public static StiltsInputFormat byStiltsName(String stiltsName){
        for (StiltsInputFormat format:StiltsInputFormat.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsInputFormat known for " + stiltsName);
    }
}
