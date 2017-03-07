//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:03 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.CorePropertiesPart;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class CoreMapping  extends AbstractOpenXmlMapping implements IMapping<IVisitable>
{
    public CoreMapping(CorePropertiesPart corePart, XmlWriterSettings xws) throws Exception {
        super(XmlWriter.Create(corePart.getStream(), xws));
    }

    public void apply(IVisitable x) throws Exception {
        // Start the document
        _writer.WriteStartDocument();
        _writer.WriteStartElement("cp", "coreProperties", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
        // Force declaration of these namespaces at document start
        _writer.WriteAttributeString("xmlns", "cp", null, "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
        _writer.WriteAttributeString("xmlns", "dc", null, "http://purl.org/dc/elements/1.1/");
        _writer.WriteAttributeString("xmlns", "dcterms", null, "http://purl.org/dc/terms/");
        _writer.WriteAttributeString("xmlns", "dcmitype", null, "http://purl.org/dc/dcmitype/");
        _writer.WriteAttributeString("xmlns", "xsi", null, "http://www.w3.org/2001/XMLSchema-instance");
        _writer.WriteElementString("dc", "title", "http://purl.org/dc/elements/1.1/", "Title");
        _writer.WriteElementString("dc", "creator", "http://purl.org/dc/elements/1.1/", "Creator");
        _writer.WriteElementString("dc", "description", "http://purl.org/dc/elements/1.1/", "Description");
        _writer.WriteElementString("cp", "lastModifiedBy", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties", "Modifier");
        _writer.WriteElementString("cp", "revision", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties", "10");
        _writer.WriteElementString("cp", "lastPrinted", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties", "1601-01-01T00:00:00Z");
        _writer.WriteStartElement("dcterms", "created", "http://purl.org/dc/terms/");
        _writer.WriteAttributeString("xsi", "type", "http://www.w3.org/2001/XMLSchema-instance", "dcterms:W3CDTF");
        _writer.writeString("2008-05-13T17:08:28Z");
        _writer.writeEndElement();
        _writer.WriteStartElement("dcterms", "modified", "http://purl.org/dc/terms/");
        _writer.WriteAttributeString("xsi", "type", "http://www.w3.org/2001/XMLSchema-instance", "dcterms:W3CDTF");
        _writer.writeString("2009-03-30T11:17:58Z");
        _writer.writeEndElement();
        // End the document
        _writer.writeEndElement();
        //coreProperties
        _writer.WriteEndDocument();
        _writer.Flush();
    }

}


