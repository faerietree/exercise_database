//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:02 AM
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
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES,
 * LOSS OF USE, DATA, OR PROFITS, OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class Global   
{
    public enum JustificationCode
    {
        left,
        center,
        right,
        both,
        distribute,
        mediumKashida,
        numTab,
        highKashida,
        lowKashida,
        thaiDistribute
    }
    public enum ColorIdentifier
    {
        auto,
        black,
        blue,
        cyan,
        green,
        magenta,
        red,
        yellow,
        white,
        darkBlue,
        darkCyan,
        darkGreen,
        darkMagenta,
        darkRed,
        darkYellow,
        darkGray,
        lightGray
    }
    public enum TextAnimation
    {
        none,
        lights,
        blinkBackground,
        sparkle,
        antsBlack,
        antsRed,
        shimmer
    }
    public enum FarEastLayout
    {
        none,
        tatenakayoko,
        warichu,
        kumimoji,
        all
    }
    public enum WarichuBracket
    {
        none,
        parentheses,
        squareBrackets,
        angledBrackets,
        braces
    }
    public enum HyphenationRule
    {
        none,
        normal,
        addLetterBefore,
        changeLetterBefore,
        deleteLetterBefore,
        changeLetterAfter,
        deleteAndChange
    }
    public enum UnderlineCode
    {
        none,
        single,
        word,
        Double,
        dotted,
        notUsed1,
        thick,
        dash,
        notUsed2,
        dotDash,
        dotDotDash,
        wave,
        dottedHeavy,
        dashedHeavy,
        dashDotHeavy,
        dashDotDotHeavy,
        wavyHeavy,
        dashLong,
        wavyDouble,
        dashLongHeavy
    }
    public enum TabLeader
    {
        none,
        dot,
        hyphen,
        underscore,
        heavy,
        middleDot
    }
    public enum DashStyle
    {
        solid,
        shortdash,
        shortdot,
        shortdashdot,
        shortdashdotdot,
        dot,
        dash,
        longdash,
        dashdot,
        longdashdot,
        longdashdotdot
    }
    public enum TextFlow
    {
        lrTb,
        tbRl,
        __dummyEnum__0,
        btLr,
        lrTbV,
        tbRlV
    }
    public enum VerticalMergeFlag
    {
        fvmClear,
        fvmMerge,
        __dummyEnum__0,
        fvmRestart
    }
    public enum VerticalAlign
    {
        top,
        center,
        bottom
    }
    public enum CellWidthType
    {
        nil,
        auto,
        pct,
        dxa
    }
    public enum VerticalPositionCode
    {
        margin,
        page,
        text,
        none
    }
    public enum HorizontalPositionCode
    {
        text,
        margin,
        page,
        none
    }
    public enum TextFrameWrapping
    {
        auto,
        notBeside,
        around,
        none,
        tight,
        through
    }
}


