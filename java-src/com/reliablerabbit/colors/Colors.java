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

public class Colors {
    public final static short BLINK     = 4096;
    public final static short BOLD      =    1;
    public final static short UNDERLINE =    4;

    public final static short BLACK      =   16;
    public final static short RED        =   32;
    public final static short GREEN      =   64;
    public final static short YELLOW     =  128;
    public final static short BLUE       =  256;
    public final static short MAGENTA    =  512;
    public final static short CYAN       = 1024;
    public final static short WHITE      = 2048;
    public final static short COLOR_MASK = 0x3fb;

    public final static String CLEAR_SCREEN = "\033[2J\033[;H";
    public final static String CLEAR = "\u001B[0m";

    public static String c(int options, String s) {
        StringBuffer buff = new StringBuffer();

        if((options & Colors.BOLD)      == Colors.BOLD)      buff.append("\u001B[1m");
        if((options & Colors.UNDERLINE) == Colors.UNDERLINE) buff.append("\u001B[4m");
        if((options & Colors.BLINK)     == Colors.BLINK)     buff.append("\u001B[5m");

        if((options & Colors.BLACK)     == Colors.BLACK)     buff.append("\u001B[30m");
        if((options & Colors.RED)       == Colors.RED)       buff.append("\u001B[31m");
        if((options & Colors.GREEN)     == Colors.GREEN)     buff.append("\u001B[32m");
        if((options & Colors.YELLOW)    == Colors.YELLOW)    buff.append("\u001B[33m");
        if((options & Colors.BLUE)      == Colors.BLUE)      buff.append("\u001B[34m");
        if((options & Colors.MAGENTA)   == Colors.MAGENTA)   buff.append("\u001B[35m");
        if((options & Colors.CYAN)      == Colors.CYAN)      buff.append("\u001B[36m");
        if((options & Colors.WHITE)     == Colors.WHITE)     buff.append("\u001B[37m");

        buff.append(s);
        buff.append(CLEAR);
        return buff.toString();
    }
}
