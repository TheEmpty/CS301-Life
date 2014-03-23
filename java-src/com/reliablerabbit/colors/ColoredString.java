/*
Copyright (c) 2013-2014 Mohammad El-Abid

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.reliablerabbit.colors;

public class ColoredString {
        private String s;
        private int options;
        public ColoredString(String s)          { this.s = s; }
        public ColoredString(ColoredString c)   { this.s = c.getS(); }
        public static ColoredString s(String s) { return new ColoredString(s); }
        public String getS()                    { return s; }

        // font modifiers
        public ColoredString bold()      { options = options | Colors.BOLD;      return this; }
        public ColoredString underline() { options = options | Colors.UNDERLINE; return this; }
        public ColoredString blink()     { options = options | Colors.BLINK;     return this; }

        // text colors
        public ColoredString black()   { options = options | Colors.BLACK;   return this; }
        public ColoredString red()     { options = options | Colors.RED;     return this; }
        public ColoredString green()   { options = options | Colors.GREEN;   return this; }
        public ColoredString yellow()  { options = options | Colors.YELLOW;  return this; }
        public ColoredString blue()    { options = options | Colors.BLUE;    return this; }
        public ColoredString magenta() { options = options | Colors.MAGENTA; return this; }
        public ColoredString cyan()    { options = options | Colors.CYAN;    return this; }
        public ColoredString white()   { options = options | Colors.WHITE;   return this; }

        // render
        @Override
        public String toString() {
                // if netbeans: return Colors.c( options & Colors.COLOR_MASK, s );
                // if windows: return s;
                // if NIX: return Colors.c(options, s);
                return Colors.c(options, s);
        }

        public String rainbow() {
                StringBuffer rainbow = new StringBuffer();
                char[] characters = s.toCharArray();
                short[] colors    = { Colors.RED, Colors.YELLOW, Colors.GREEN, Colors.CYAN, Colors.BLUE };
                int offset        = (int) (System.nanoTime() % colors.length);

                for(int i = 0; i < characters.length; i++) {
                        short color = colors[ ((i + offset) % colors.length) ];
                        rainbow.append( Colors.c(options | color, String.valueOf(characters[i])) );
                }

                return rainbow.toString();
        }
}
