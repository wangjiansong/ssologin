package org.jasig.cas.authentication.handler;

public class StringUtil {
	   /**
     * Replaces all occurences of a pattern within a String.
     * @param source The source string.
     * @param toReplace The character to replace.
     * @param replacement The replacement.
     * @return The new String with characters replaced.
     */
    public static String replaceAll(final String source, final String toReplace,
            final String replacement) {
        if (source == null) {
            return null;
        }
        String returnValue = source;
        int idx = source.lastIndexOf(toReplace);
        if (idx != -1) {
            StringBuffer ret = new StringBuffer(source);
            ret.replace(idx, idx + toReplace.length(), replacement);
            while ((idx = source.lastIndexOf(toReplace, idx - 1)) != -1) {
                ret.replace(idx, idx + toReplace.length(), replacement);
            }
            returnValue = ret.toString();
        }
        return returnValue;
    }
}
