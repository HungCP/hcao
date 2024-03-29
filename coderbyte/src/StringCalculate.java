import java.util.*;

/**
 * Using the Java language, have the function Calculator(str) take the str parameter being passed and evaluate the
 * mathematical expression within in. For example, if str were "2+(3-1)*3" the output should be 8.
 * Another example: if str were "(2-0)(6/2)" the output should be 6. There can be parenthesis within the string so you
 * must evaluate it properly according to the rules of arithmetic.
 * The string will contain the operators: +, -, /, *, (, and ). If you have a string like this: #/#*# or #+#(#)/#,
 * then evaluate from left to right. So divide then multiply, and for the second one multiply, divide, then add.
 * The evaluations will be such that there will not be any decimal operations, so you do not need to account for
 * rounding and whatnot.
 */

public class StringCalculate {
    static String StringCalculate(String str) {
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("\\*\\*", "^");
        str = str.replaceAll("[)][(]", ")*(");
        str = str.replaceAll("([0-9])[(]", "$1*(");
        str = str.replaceAll("[)]([0-9])", ")*$1");

        str = evalBrackets(str);
        str = evalPow(str);
        str = evalDivivisionAndMultiplication(str);
        str = evalAdditionAndSubtraction(str);

        return str;
    }

    static String evalBrackets(String str) {
        StringBuilder result = new StringBuilder(str);

        int openBracketIndex = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                openBracketIndex = i;
            } else if (c == ')') {
                String calc = str.substring(openBracketIndex, i + 1);
                String betweenBrackets = calc.substring(1, calc.length() - 1);
                String res = evalPow(betweenBrackets);
                res = evalDivivisionAndMultiplication(res);
                res = evalAdditionAndSubtraction(res);

                int startIndex = result.indexOf(calc);
                result.replace(startIndex, startIndex + calc.length(), res);
            }
        }

        return result.toString();
    }

    static String evalPow(String str) {
        StringBuilder result = new StringBuilder(str);

        while (result.indexOf("^") != -1) {
            for (int i = 0; i < result.length(); i++) {
                char c = result.charAt(i);

                if (c == '^') {
                    String before = getDigitBeforeSign(result.toString(), i);
                    String after = getDigitAfterSign(result.toString(), i);
                    String calc = result.substring(i - before.length(), i + after.length() + 1);
                    double res = Math.pow(Double.parseDouble(before), Double.parseDouble(after));

                    int startIndex = result.indexOf(calc);
                    result.replace(startIndex, startIndex + calc.length(), String.valueOf(res));
                    break;
                }
            }
        }
        return result.toString();
    }

    static String evalDivivisionAndMultiplication(String str) {
        StringBuilder result = new StringBuilder(str);

        while (result.indexOf("*") != -1 || result.indexOf("/") != -1) {
            for (int i = 0; i < result.length(); i++) {
                char c = result.charAt(i);

                if (c == '*' || c == '/') {
                    String before = getDigitBeforeSign(result.toString(), i);
                    String after = getDigitAfterSign(result.toString(), i);
                    String calc = result.substring(i - before.length(), i + after.length() + 1);
                    double res = c == '*' ? Double.parseDouble(before) * Double.parseDouble(after) : Double.parseDouble(before) / Double.parseDouble(after);

                    int startIndex = result.indexOf(calc);
                    result.replace(startIndex, startIndex + calc.length(), String.valueOf(res));
                    break;
                }
            }
        }
        return result.toString();
    }

    static String evalAdditionAndSubtraction(String str) {
        StringBuilder result = new StringBuilder(str);

        while (result.indexOf("+") != -1 || result.lastIndexOf("-") > 0) {
            for (int i = 0; i < result.length(); i++) {
                char c = result.charAt(i);

                if (c == '+' || (c == '-' && i != 0)) {
                    String before = getDigitBeforeSign(result.toString(), i);
                    String after = getDigitAfterSign(result.toString(), i);
                    String calc = result.substring(i - before.length(), i + after.length() + 1);
                    double res = c == '+' ? Double.parseDouble(before) + Double.parseDouble(after) : Double.parseDouble(before) - Double.parseDouble(after);

                    int startIndex = result.indexOf(calc);
                    result.replace(startIndex, startIndex + calc.length(), String.valueOf(res));
                    break;
                }
            }
        }
        return result.toString();
    }

    static String getDigitBeforeSign(String str, int signIndex) {
        StringBuilder digit = new StringBuilder("");
        for (int i = signIndex - 1; i >= 0; i--) {
            char d = str.charAt(i);

            if (d == '+' || (d == '-' && i != 0) || d == '/' || d == '*' || d == '^') {
                return digit.toString();
            } else {
                digit.insert(0, d);
            }
        }
        return digit.toString();
    }

    static String getDigitAfterSign(String str, int signIndex) {
        StringBuilder digit = new StringBuilder("");
        for (int i = signIndex + 1; i < str.length(); i++) {
            char d = str.charAt(i);

            if (d == '+' || d == '-' || d == '/' || d == '*' || d == '^') {
                return digit.toString();
            } else if (Character.isDigit(d) || d == '.') {
                digit.append(d);
            }
        }
        return digit.toString();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print(StringCalculate(s.nextLine()));
    }
}
