public class FastChecker {

    //проверка ИИН
    public static boolean softCheckIinAndBin(String value){
        if(value == null) return false;
        int len;
        if( (len = value.length()) != 12) {
            return false;
        }
        for(int i = 0; i < len; ++i) {
            if(value.charAt(i) < '0' || value.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    //полная проверка ИИН
    public static boolean fullCheckIin(String value){
        return softCheckIinAndBin(value)
                && checkBirthDate(value)
                && checkIinBinSum(value);
    }

    private static boolean checkBirthDate(String value) {
        final char m1 = value.charAt(2);
        final char m2 = value.charAt(3);

        if(m1 == '0' && m2 == '2') {
            final char d1 = value.charAt(4);
            final char d2 = value.charAt(5);

            final char y1 = value.charAt(0);
            final char y2 = value.charAt(1);

            char last;

            if(y1 == 0 && y2 == 0 && (value.charAt(6) >= '1' && value.charAt(6) < '4')) {
                last = '8';
            } else {
                switch (y1) {
                    case '0':
                    case '2':
                    case '4':
                    case '6':
                    case '8':
                        switch (y2) {
                            case '0':
                            case '4':
                            case '8':
                                last = '9';
                                break;
                            default:
                                last = '8';
                                break;
                        }
                        break;
                    case '1':
                    case '3':
                    case '5':
                    case '7':
                    case '9':
                        switch (y2) {
                            case '2':
                            case '6':
                                last = '9';
                                break;
                            default:
                                last = '8';
                                break;
                        }
                        break;
                    default:
                        return false;
                }
            }

            switch (d1) {
                case '0':
                    if(d2 < '1' || d2 > '9') {
                        return false;
                    }
                    break;
                case '1':
                    if(d2 < '0' || d2 > '9') {
                        return false;
                    }
                    break;
                case '2':
                    if(d2 < '0' || d2 > last) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        } else {
            char last;

            if(m1 == '0' && (m2 == '1' || m2 == '3' || m2 == '5' || m2 == '7' || m2 == '8')
                    || m1 == '1' && (m2 == '0' || m2 == '2')) {
                last = '1';
            } else if (m1 == '0' && (m2 == '4' || m2 == '6' || m2 == '9')
                    || m1 == '1' && m2 == '1') {
                last = '0';
            } else {
                return false;
            }

            final char d1 = value.charAt(4);
            final char d2 = value.charAt(5);

            switch (d1) {
                case '0':
                    if(d2 < '1' || d2 > '9') {
                        return false;
                    }
                    break;
                case '1':
                case '2':
                    if(d2 < '0' || d2 > '9') {
                        return false;
                    }
                    break;
                case '3':
                    if(d2 < '0' || d2 > last) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }

        }


        return true;
    }

    //проверка контрольной суммы ИИН
    private static boolean checkIinBinSum(String value){
        int endDigit = value.charAt(11) - '0';
        int sum = 0;
        int[] digits = new int[12];
        for (int i = 0; i < 12; ++i) {
            digits[i] = value.charAt(i) - '0';
        }
        for (int i = 0; i < 11; ++i) {
            sum += (i + 1) * digits[i];
        }
        int k = sum % 11;
        if (k == 10) {
            sum = 0;
            int t;
            for (int i = 0; i < 11; ++i) {
                t = i + 3;
                if(t >= 11) {
                    t -= 11;
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
