//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:40 AM
//

package DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records;

import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.BiffRecordAttribute;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.Records.AbstractCellContent;
import DIaLOGIKa.b2xtranslator.Spreadsheet.XlsFileFormat.RecordType;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.IStreamReader;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public class BoolErr  extends AbstractCellContent 
{
    public static final RecordType ID = RecordType.BoolErr;
    /**
    * An unsigned integer that specifies either a Boolean value or an error value, depending on the value of fError.
    */
    public byte bBoolErr;
    /**
    * A Boolean that specifies whether bBoolErr contains an error code or a Boolean value.
    */
    public boolean fError;
    public BoolErr(IStreamReader reader, RecordType id, UInt16 length) throws Exception {
        super(reader, id, length);
        // assert that the correct record type is instantiated
        Debug.Assert(this.Id == ID);
        // initialize class members from stream
        this.bBoolErr = reader.readByte();
        this.fError = reader.readByte() == 0x1;
        // assert that the correct number of bytes has been read from the stream
        Debug.Assert(this.Offset + this.Length == this.Reader.BaseStream.Position);
    }

}


