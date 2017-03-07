//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;


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
public class StyleSheetInformation   
{
    public static class LatentStyleData   
    {
        public LatentStyleData() {
        }

        public long grflsd;
        public boolean fLocked;
    }

    /**
    * Count of styles in stylesheet
    */
    public UInt16 cstd = new UInt16();
    /**
    * Length of STD Base as stored in a file
    */
    public UInt16 cbSTDBaseInFile = new UInt16();
    /**
    * Are built-in stylenames stored?
    */
    public boolean fStdStylenamesWritten;
    /**
    * Max sti known when this file was written
    */
    public UInt16 stiMaxWhenSaved = new UInt16();
    /**
    * How many fixed-index istds are there?
    */
    public UInt16 istdMaxFixedWhenSaved = new UInt16();
    /**
    * Current version of built-in stylenames
    */
    public UInt16 nVerBuiltInNamesWhenSaved = new UInt16();
    /**
    * This is a list of the default fonts for this style sheet.
    * The first is for ASCII characters (0-127), the second is for East Asian characters,
    * and the third is the default font for non-East Asian, non-ASCII text.
    */
    public UInt16[] rgftcStandardChpStsh;
    /**
    * Size of each lsd in mpstilsd
    * The count of lsd's is stiMaxWhenSaved
    */
    public UInt16 cbLSD = new UInt16();
    /**
    * latent style data (size == stiMaxWhenSaved upon save!)
    */
    public LatentStyleData[] mpstilsd;
    /**
    * Parses the bytes to retrieve a StyleSheetInformation
    * 
    *  @param bytes
    */
    public StyleSheetInformation(byte[] bytes) throws Exception {
        this.cstd = System.BitConverter.ToUInt16(bytes, 0);
        this.cbSTDBaseInFile = System.BitConverter.ToUInt16(bytes, 2);
        if (bytes[4] == 1)
        {
            this.fStdStylenamesWritten = true;
        }
         
        //byte 5 is spare
        this.stiMaxWhenSaved = System.BitConverter.ToUInt16(bytes, 6);
        this.istdMaxFixedWhenSaved = System.BitConverter.ToUInt16(bytes, 8);
        this.nVerBuiltInNamesWhenSaved = System.BitConverter.ToUInt16(bytes, 10);
        this.rgftcStandardChpStsh = new UInt16[4];
        this.rgftcStandardChpStsh[0] = System.BitConverter.ToUInt16(bytes, 12);
        this.rgftcStandardChpStsh[1] = System.BitConverter.ToUInt16(bytes, 14);
        this.rgftcStandardChpStsh[2] = System.BitConverter.ToUInt16(bytes, 16);
        if (bytes.length > 18)
        {
            this.rgftcStandardChpStsh[3] = System.BitConverter.ToUInt16(bytes, 18);
        }
         
        //not all stylesheet contain latent styles
        if (bytes.length > 20)
        {
            this.cbLSD = System.BitConverter.ToUInt16(bytes, 20);
            this.mpstilsd = new LatentStyleData[this.stiMaxWhenSaved];
            for (int i = 0;i < this.mpstilsd.length;i++)
            {
                LatentStyleData lsd = new LatentStyleData();
                lsd.grflsd = System.BitConverter.ToUInt32(bytes, 22 + (i * cbLSD));
                lsd.fLocked = DIaLOGIKa.b2xtranslator.Tools.Utils.bitmaskToBool((int)lsd.grflsd,0x1);
                this.mpstilsd[i] = lsd;
            }
        }
         
    }

}


