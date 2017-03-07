//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:18 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DocumentProperties;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import java.util.Collections;

public class SettingsMapping  extends PropertiesMapping implements IMapping<DocumentProperties>
{
    private enum FootnotePosition
    {
        beneathText,
        docEnd,
        pageBottom,
        sectEnd
    }
    private enum ZoomType
    {
        none,
        fullPage,
        bestFit
    }
    private ConversionContext _ctx;
    public SettingsMapping(ConversionContext ctx, OpenXmlPart targetPart) throws Exception {
        super(XmlWriter.Create(targetPart.getStream(), ctx.getWriterSettings()));
        _ctx = ctx;
    }

    public void apply(DocumentProperties dop) throws Exception {
        //start w:settings
        _writer.WriteStartElement("w", "settings", OpenXmlNamespaces.WordprocessingML);
        //zoom
        _writer.WriteStartElement("w", "zoom", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "percent", OpenXmlNamespaces.WordprocessingML, String.valueOf(dop.wScaleSaved));
        ZoomType zoom = (ZoomType)dop.zkSaved;
        if (zoom != ZoomType.none)
        {
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, zoom.toString());
        }
         
        _writer.writeEndElement();
        //doc protection
        //<w:documentProtection w:edit="forms" w:enforcement="1"/>
        //embed system fonts
        if (!dop.fDoNotEmbedSystemFont)
        {
            _writer.WriteElementString("w", "embedSystemFonts", OpenXmlNamespaces.WordprocessingML, "");
        }
         
        //mirror margins
        if (dop.fMirrorMargins)
        {
            _writer.WriteElementString("w", "mirrorMargins", OpenXmlNamespaces.WordprocessingML, "");
        }
         
        //evenAndOddHeaders
        if (dop.fFacingPages)
        {
            _writer.WriteElementString("w", "evenAndOddHeaders", OpenXmlNamespaces.WordprocessingML, "");
        }
         
        //proof state
        XmlElement proofState = _nodeFactory.createElement("w", "proofState", OpenXmlNamespaces.WordprocessingML);
        if (dop.fGramAllClean)
            appendValueAttribute(proofState,"grammar","clean");
         
        if (proofState.getAttributes().size() > 0)
            proofState.WriteTo(_writer);
         
        //stylePaneFormatFilter
        if (dop.grfFmtFilter != 0)
        {
            _writer.WriteStartElement("w", "stylePaneFormatFilter", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x4}"),dop.grfFmtFilter));
            _writer.writeEndElement();
        }
         
        //default tab stop
        _writer.WriteStartElement("w", "defaultTabStop", OpenXmlNamespaces.WordprocessingML);
        _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, dop.dxaTab.toString());
        _writer.writeEndElement();
        //drawing grid
        if (dop.dogrid != null)
        {
            _writer.WriteStartElement("w", "displayHorizontalDrawingGridEvery", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.valueOf(dop.dogrid.dxGridDisplay));
            _writer.writeEndElement();
            _writer.WriteStartElement("w", "displayVerticalDrawingGridEvery", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, String.valueOf(dop.dogrid.dyGridDisplay));
            _writer.writeEndElement();
            if (dop.dogrid.fFollowMargins == false)
            {
                _writer.WriteElementString("w", "doNotUseMarginsForDrawingGridOrigin", OpenXmlNamespaces.WordprocessingML, "");
            }
             
        }
         
        //typography
        if (dop.doptypography != null)
        {
            if (dop.doptypography.fKerningPunct == false)
            {
                _writer.WriteElementString("w", "noPunctuationKerning", OpenXmlNamespaces.WordprocessingML, "");
            }
             
        }
         
        //footnote properties
        XmlElement footnotePr = _nodeFactory.createElement("w", "footnotePr", OpenXmlNamespaces.WordprocessingML);
        if (dop.nFtn != 0)
            appendValueAttribute(footnotePr,"numStart",String.valueOf(dop.nFtn));
         
        if (dop.rncFtn != 0)
            appendValueAttribute(footnotePr,"numRestart",String.valueOf(dop.rncFtn));
         
        if (dop.Fpc != 0)
            appendValueAttribute(footnotePr,"pos",((FootnotePosition)dop.Fpc).toString());
         
        if (footnotePr.getAttributes().size() > 0)
            footnotePr.WriteTo(_writer);
         
        writeCompatibilitySettings(dop);
        writeRsidList();
        //close w:settings
        _writer.writeEndElement();
        _writer.Flush();
    }

    private void writeRsidList() throws Exception {
        //convert the rsid list
        _writer.WriteStartElement("w", "rsids", OpenXmlNamespaces.WordprocessingML);
        Collections.sort(_ctx.AllRsids);
        for (String rsid : _ctx.AllRsids)
        {
            _writer.WriteStartElement("w", "rsid", OpenXmlNamespaces.WordprocessingML);
            _writer.WriteAttributeString("w", "val", OpenXmlNamespaces.WordprocessingML, rsid);
            _writer.writeEndElement();
        }
        _writer.writeEndElement();
    }

    private void writeCompatibilitySettings(DocumentProperties dop) throws Exception {
        //compatibility settings
        _writer.WriteStartElement("w", "compat", OpenXmlNamespaces.WordprocessingML);
        //some settings must always be written
        _writer.WriteElementString("w", "useNormalStyleForList", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "doNotUseIndentAsNumberingTabStop", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "useAltKinsokuLineBreakRules", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "allowSpaceOfSameStyleInTable", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "doNotSuppressIndentation", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "doNotAutofitConstrainedTables", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "autofitToFirstFixedWidthCell", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "displayHangulFixedWidth", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "splitPgBreakAndParaMark", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "doNotVertAlignCellWithSp", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "doNotBreakConstrainedForcedTable", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "doNotVertAlignInTxbx", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "useAnsiKerningPairs", OpenXmlNamespaces.WordprocessingML, "");
        _writer.WriteElementString("w", "cachedColBalance", OpenXmlNamespaces.WordprocessingML, "");
        //others are saved in the file
        if (!dop.fDontAdjustLineHeightInTable)
            _writer.WriteElementString("w", "adjustLineHeightInTable", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fAlignTablesRowByRow)
            _writer.WriteElementString("w", "alignTablesRowByRow", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fApplyBreakingRules)
            _writer.WriteElementString("w", "applyBreakingRules", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fUseAutoSpaceForFullWidthAlpha)
            _writer.WriteElementString("w", "autoSpaceLikeWord95", OpenXmlNamespaces.WordprocessingML, "");
         
        if (!dop.fDntBlnSbDbWid)
            _writer.WriteElementString("w", "balanceSingleByteDoubleByteWidth", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fConvMailMergeEsc)
            _writer.WriteElementString("w", "convMailMergeEsc", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fDontBreakWrappedTables)
            _writer.WriteElementString("w", "doNotBreakWrappedTables", OpenXmlNamespaces.WordprocessingML, "");
         
        if (!dop.fExpShRtn)
            _writer.WriteElementString("w", "doNotExpandShiftReturn", OpenXmlNamespaces.WordprocessingML, "");
         
        if (!dop.fLeaveBackslashAlone)
            _writer.WriteElementString("w", "doNotLeaveBackslashAlone", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fDontSnapToGridInCell)
            _writer.WriteElementString("w", "doNotSnapToGridInCell", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fDontUseAsianBreakRules)
            _writer.WriteElementString("w", "doNotUseEastAsianBreakRules", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fDontUseHTMLParagraphAutoSpacing)
            _writer.WriteElementString("w", "doNotUseHTMLParagraphAutoSpacing", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fDontWrapTextWithPunct)
            _writer.WriteElementString("w", "doNotWrapTextWithPunct", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fFtnLayoutLikeWW8)
            _writer.WriteElementString("w", "footnoteLayoutLikeWW8", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fForgetLastTabAlign)
            _writer.WriteElementString("w", "forgetLastTabAlignment", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fGrowAutofit)
            _writer.WriteElementString("w", "growAutofit", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fLayoutRawTableWidth)
            _writer.WriteElementString("w", "layoutRawTableWidth", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fLayoutTableRowsApart)
            _writer.WriteElementString("w", "layoutTableRowsApart", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fLineWrapLikeWord6)
            _writer.WriteElementString("w", "lineWrapLikeWord6", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fMWSmallCaps)
            _writer.WriteElementString("w", "mwSmallCaps", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fNoColumnBalance)
            _writer.WriteElementString("w", "noColumnBalance", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fNoLeading)
            _writer.WriteElementString("w", "noLeading", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fNoSpaceRaiseLower)
            _writer.WriteElementString("w", "noSpaceRaiseLower", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fNoTabForInd)
            _writer.WriteElementString("w", "noTabHangInd", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fPrintBodyBeforeHdr)
            _writer.WriteElementString("w", "printBodyTextBeforeHeader", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fMapPrintTextColor)
            _writer.WriteElementString("w", "printColBlack", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fDontAllowFieldEndSelect)
            _writer.WriteElementString("w", "selectFldWithFirstOrLastChar", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fSpLayoutLikeWW8)
            _writer.WriteElementString("w", "shapeLayoutLikeWW8", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fShowBreaksInFrames)
            _writer.WriteElementString("w", "showBreaksInFrames", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fMakeSpaceForUL)
            _writer.WriteElementString("w", "spaceForUL", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fTruncDxaExpand)
            _writer.WriteElementString("w", "spacingInWholePoints", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fSubOnSize)
            _writer.WriteElementString("w", "subFontBySize", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fSuppressSpbfAfterPageBreak)
            _writer.WriteElementString("w", "suppressSpBfAfterPgBrk", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fSuppressTopSpacing)
            _writer.WriteElementString("w", "suppressTopSpacing", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fSwapBordersFacingPgs)
            _writer.WriteElementString("w", "swapBordersFacingPages", OpenXmlNamespaces.WordprocessingML, "");
         
        if (!dop.fDntULTrlSpc)
            _writer.WriteElementString("w", "ulTrailSpace", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fPrintMet)
            _writer.WriteElementString("w", "usePrinterMetrics", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fUseWord2002TableStyleRules)
            _writer.WriteElementString("w", "useWord2002TableStyleRules", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fUserWord97LineBreakingRules)
            _writer.WriteElementString("w", "useWord97LineBreakRules", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fWPJust)
            _writer.WriteElementString("w", "wpJustification", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fWPSpace)
            _writer.WriteElementString("w", "wpSpaceWidth", OpenXmlNamespaces.WordprocessingML, "");
         
        if (dop.fWrapTrailSpaces)
            _writer.WriteElementString("w", "wrapTrailSpaces", OpenXmlNamespaces.WordprocessingML, "");
         
        _writer.writeEndElement();
    }

}


