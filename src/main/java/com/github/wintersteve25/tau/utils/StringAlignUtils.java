package com.github.wintersteve25.tau.utils;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StringAlignUtils extends Format {

    public enum Alignment {
        LEFT, CENTER, RIGHT,
    }

    private final Alignment currentAlignment;
    private final int maxChars;

    public StringAlignUtils(int maxChars, Alignment align) {
        switch (align) {
            case LEFT:
            case CENTER:
            case RIGHT:
                this.currentAlignment = align;
                break;
            default:
                throw new IllegalArgumentException("invalid justification arg.");
        }
        if (maxChars < 0) {
            throw new IllegalArgumentException("maxChars must be positive.");
        }
        this.maxChars = maxChars;
    }

    public StringBuffer format(Object input, StringBuffer where, FieldPosition ignore)
    {
        String s = input.toString();
        List<String> strings = splitInputString(s);
        ListIterator<String> listItr = strings.listIterator();

        while (listItr.hasNext())
        {
            String wanted = listItr.next();

            //Get the spaces in the right place.
            switch (currentAlignment)
            {
                case RIGHT:
                    pad(where, maxChars - wanted.length());
                    where.append(wanted);
                    break;

                case CENTER:
                    int toAdd = maxChars - wanted.length();
                    pad(where, toAdd / 2);
                    where.append(wanted);
                    pad(where, toAdd - toAdd / 2);
                    break;

                case LEFT:
                    where.append(wanted);
                    pad(where, maxChars - wanted.length());
                    break;
            }
            where.append("\n");
        }
        return where;
    }

    protected final void pad(StringBuffer to, int howMany) {
        for (int i = 0; i < howMany; i++)
            to.append(' ');
    }

    public String format(String s) {
        return format(s, new StringBuffer(), null).toString();
    }

    public Object parseObject(String source, ParsePosition pos) {
        return source;
    }

    private List<String> splitInputString(String str) {
        List<String> list = new ArrayList<String>();
        if (str == null)
            return list;
        for (int i = 0; i < str.length(); i = i + maxChars)
        {
            int endindex = Math.min(i + maxChars, str.length());
            list.add(str.substring(i, endindex));
        }
        return list;
    }
}
