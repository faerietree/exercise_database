//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:17 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CharacterPropertyExceptions;
import DIaLOGIKa.b2xtranslator.DocFileFormat.DateAndTime;
import DIaLOGIKa.b2xtranslator.DocFileFormat.SinglePropertyModifier;

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
public class RevisionData   
{
    public enum RevisionType
    {
        NoRevision,
        Inserted,
        Deleted,
        Changed
    }
    public DateAndTime Dttm;
    public short Isbt;
    public RevisionType Type = RevisionType.NoRevision;
    public CSList<SinglePropertyModifier> Changes;
    public int RsidDel;
    public int RsidProp;
    public int Rsid;
    public RevisionData() throws Exception {
        this.Changes = new CSList<SinglePropertyModifier>();
    }

    /**
    * Collects the revision data of a CHPX
    * 
    *  @param chpx
    */
    public RevisionData(CharacterPropertyExceptions chpx) throws Exception {
        boolean collectRevisionData = true;
        this.Changes = new CSList<SinglePropertyModifier>();
        for (SinglePropertyModifier sprm : chpx.grpprl)
        {
            switch(((Enum)sprm.OpCode).ordinal())
            {
                case 0xCA89: 
                    //revision data
                    //revision mark
                    collectRevisionData = false;
                    //author
                    this.Isbt = System.BitConverter.ToInt16(sprm.Arguments, 1);
                    //date
                    byte[] dttmBytes = new byte[4];
                    Array.Copy(sprm.Arguments, 3, dttmBytes, 0, 4);
                    this.Dttm = new DateAndTime(dttmBytes);
                    break;
                case 0x0801: 
                    //revision mark
                    collectRevisionData = false;
                    break;
                case 0x4804: 
                    //author
                    this.Isbt = System.BitConverter.ToInt16(sprm.Arguments, 0);
                    break;
                case 0x6805: 
                    //date
                    this.Dttm = new DateAndTime(sprm.Arguments);
                    break;
                case 0x0800: 
                    //delete mark
                    this.Type = RevisionType.Deleted;
                    break;
                case 0x6815: 
                    this.RsidProp = System.BitConverter.ToInt32(sprm.Arguments, 0);
                    break;
                case 0x6816: 
                    this.Rsid = System.BitConverter.ToInt32(sprm.Arguments, 0);
                    break;
                case 0x6817: 
                    this.RsidDel = System.BitConverter.ToInt32(sprm.Arguments, 0);
                    break;
            
            }
            //put the sprm on the revision stack
            if (collectRevisionData)
            {
                this.Changes.add(sprm);
            }
             
        }
        //type
        if (this.Type != RevisionType.Deleted)
        {
            if (collectRevisionData)
            {
                //no mark was found, so this CHPX doesn't contain revision data
                this.Type = RevisionType.NoRevision;
            }
            else
            {
                if (this.Changes.size() > 0)
                {
                    this.Type = RevisionType.Changed;
                }
                else
                {
                    this.Type = RevisionType.Inserted;
                    this.Changes.clear();
                } 
            } 
        }
         
    }

}


