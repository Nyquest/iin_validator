import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldChecker {

    private static final Pattern IIN_AND_BIN_COMMON_PATTERN = Pattern.compile("\\d{12}");

    //проверка ИИН
    public static boolean softCheckIinAndBin(String value){
        if(value == null) return false;
        Matcher m = IIN_AND_BIN_COMMON_PATTERN.matcher(value);
        return m.matches();
    }

    //полная проверка ИИН
    public static boolean fullCheckIin(String value){
        if(softCheckIinAndBin(value)){
            //первые 6 цифр -- это дата в формате ГГММДД
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
            dateFormat.setLenient(false);
            String dateSubstr = value.substring(0, 6);
            try {
                dateFormat.parse(dateSubstr);
            } catch (ParseException pe) {
                return false;
            }

            return checkIinBinSum(value);
        }
        return false;
    }

    //проверка контрольной суммы ИИН
    private static boolean checkIinBinSum(String value){
        String endChar = value.substring(11);
        int endDigit = Integer.parseInt(endChar);
        int sum = 0;
        int[] digits = new int[12];
        for (int i = 0; i < 12; i++) {
            digits[i] = Integer.parseInt(value.substring(i, i + 1));
        }
        for (int i = 0; i < 11; i++) {
            sum += (i + 1) * digits[i];
        }
        int k = sum % 11;
        if (k == 10) {
            sum = 0;
            int t;
            for (int i = 0; i < 11; i++) {
                t = (i + 3) % 11;
                if (t == 0) {
                    t = 11;
                }
                sum += t * digits[i];
            }
            k = sum % 11;
            if (k == 10 || k != endDigit) {
                return false;
            }
        }
        return k == endDigit;
    }

}
