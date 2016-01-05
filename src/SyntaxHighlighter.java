import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by calin on 1/4/16.
 */
public class SyntaxHighlighter {
    Pattern[] keyWordsPatterns;
    Pattern hexPattern;
    String kwColor = "green";

    public SyntaxHighlighter(){
        initPatterns();
    }

    private void initPatterns() {
        keyWordsPatterns = new Pattern[15];

        keyWordsPatterns[0]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)nop\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[1]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)in\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[2]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)out\\b",Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[3]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)load\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[4]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)inc\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[5]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)mov\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[6]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)sub\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[7]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)add\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[8]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)jump\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[9]     = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)ifeq\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[10]    = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)ifz\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[11]    = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)ifl\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[12]    = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)push\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[13]    = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)pop\\b", Pattern.CASE_INSENSITIVE);
        keyWordsPatterns[14]    = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)halt\\b", Pattern.CASE_INSENSITIVE);


        hexPattern = Pattern.compile("\\b(?<!<font color=\\S{1,10}>)[0-9A-Fa-f]{2}h\\b");

    }

    public String returnHtml(String input){
        Matcher m = null;
        StringBuilder sb = new StringBuilder(input);

        for (Pattern pattern: keyWordsPatterns) {
            m = pattern.matcher(sb);
            while(m.find()){

                sb.insert(m.end(), "</b></font>");
                sb.insert(m.start(), "<b><font color=" + kwColor + ">");
                m = pattern.matcher(sb);

            }
        }

        m = hexPattern.matcher(sb);
        while (m.find()){
            sb.insert(m.end(), "</font>");
            sb.insert(m.start(), "<font color=blue>");
            m = hexPattern.matcher(sb);
        }

//        sb.insert(0, "<html><p>");
//        sb.append("</p></html>");
        return sb.toString();
    }
}
