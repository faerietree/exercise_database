//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:36 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;

public class CurvedUpArrowType  extends ShapeType 
{
    public CurvedUpArrowType() throws Exception {
        this.ShapeConcentricFill = false;
        this.Joins = JoinStyle.miter;
        this.Path = "ar0@22@3@21,,0@4@21@14@22@1@21@7@21@12@2l@13@2@8,0@11@2wa0@22@3@21@10@2@16@24@14@22@1@21@16@24@14,xewr@14@22@1@21@7@21@16@24nfe";
        this.Formulas = new CSList<String>();
        this.Formulas.add("val #0");
        this.Formulas.add("val #1");
        this.Formulas.add("val #2");
        this.Formulas.add("sum #0 width #1");
        this.Formulas.add("prod @3 1 2");
        this.Formulas.add("sum #1 #1 width ");
        this.Formulas.add("sum @5 #1 #0");
        this.Formulas.add("prod @6 1 2");
        this.Formulas.add("mid width #0");
        this.Formulas.add("ellipse #2 height @4");
        this.Formulas.add("sum @4 @9 0 ");
        this.Formulas.add("sum @10 #1 width");
        this.Formulas.add("sum @7 @9 0 ");
        this.Formulas.add("sum @11 width #0 ");
        this.Formulas.add("sum @5 0 #0 ");
        this.Formulas.add("prod @14 1 2 ");
        this.Formulas.add("mid @4 @7 ");
        this.Formulas.add("sum #0 #1 width ");
        this.Formulas.add("prod @17 1 2 ");
        this.Formulas.add("sum @16 0 @18 ");
        this.Formulas.add("val width ");
        this.Formulas.add("val height ");
        this.Formulas.add("sum 0 0 height");
        this.Formulas.add("sum @16 0 @4 ");
        this.Formulas.add("ellipse @23 @4 height ");
        this.Formulas.add("sum @8 128 0 ");
        this.Formulas.add("prod @5 1 2 ");
        this.Formulas.add("sum @5 0 128 ");
        this.Formulas.add("sum #0 @16 @11 ");
        this.Formulas.add("sum width 0 #0 ");
        this.Formulas.add("prod @29 1 2 ");
        this.Formulas.add("prod height height 1 ");
        this.Formulas.add("prod #2 #2 1 ");
        this.Formulas.add("sum @31 0 @32 ");
        this.Formulas.add("sqrt @33 ");
        this.Formulas.add("sum @34 height 0 ");
        this.Formulas.add("prod width height @35");
        this.Formulas.add("sum @36 64 0 ");
        this.Formulas.add("prod #0 1 2 ");
        this.Formulas.add("ellipse @30 @38 height ");
        this.Formulas.add("sum @39 0 64 ");
        this.Formulas.add("prod @4 1 2");
        this.Formulas.add("sum #1 0 @41 ");
        this.Formulas.add("prod height 4390 32768");
        this.Formulas.add("prod height 28378 32768");
        this.AdjustmentValues = "12960,19440,7200";
        this.ConnectorLocations = "@8,0;@11,@2;@15,0;@16,@21;@13,@2";
        this.ConnectorAngles = "270,270,270,90,0";
        this.TextboxRectangle = "@41,@43,@42,@44";
        this.Handles = new CSList<Handle>();
        Handle HandleOne = new Handle();
        HandleOne.position = "#0,topLeft";
        HandleOne.xrange = "@37,@27";
        this.Handles.Add(HandleOne);
        Handle HandleTwo = new Handle();
        HandleOne.position = "#1,topLeft";
        HandleOne.xrange = "@25,@20";
        this.Handles.Add(HandleTwo);
        Handle HandleThree = new Handle();
        HandleThree.position = "bottomRight,#2";
        HandleThree.yrange = "0,@40";
        this.Handles.Add(HandleThree);
    }

}


