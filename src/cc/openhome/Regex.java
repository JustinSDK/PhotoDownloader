package cc.openhome;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static final Pattern hostPattern = Pattern.compile("http://(.*?)/");
    public static final Pattern pageTitlePattern = Pattern.compile("<title>(.*?) \\("); // for macis
    public static final Pattern srcPattern = Pattern.compile("<img src=\"..(.*?)\" alt=\"Preview\" />"); // for macis
    public static final Pattern jpgPattern = Pattern.compile("/.*/(.*?).jpg"); // for macis

    public static String matchOne(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher.group(1);
    }
}
