/**
 * 
 */
package HauptProgramm;

/**
 * Enum, die die unterstuetzten Dateiendungen auflistet.
 * 
 * 
 * @author Schweiner, Artiom, J. R.I.B.-Wein
 *
 */
public enum DocType {
	DOC(".doc")
	, DOCX(".docx")
//	, HTML(".html")
	, ODT(".odt")
	, PDF(".pdf")
	, RTF(".rtf")
	, TEX(".tex")
	, TXT(".txt");
	
	 private String ending;

	 private DocType(String c) {
	 	ending = c;
	 }

	 public String getCode() {
	 	return ending;
	 }
	 
	 
	 
	//=======METHODEN --
	/* annotation by JRIBW: EVen though I think e.g. RTF(rtf)
	   is REDUNDANT! I have added all those following lines below */
	public String toString() {
	    return this.ending;
	}
	
	public String getEnding() {
	    return this.ending;
	}
	
	public static DocType getByEnding(String ending) {
	    /*
	    System.out.println(name);
	    if (name.equalsIgnoreCase("title")) {
	        return Resource.Private;
	    }
	    */
	    if (ending != null) {
	        for (DocType cat : DocType.values()) {
	            if (("." + ending).equalsIgnoreCase(cat.ending)) {
	                return cat;
	            }
	        }
	    }
	    return null;
	}
		
	 
	 
	 
	 
}
