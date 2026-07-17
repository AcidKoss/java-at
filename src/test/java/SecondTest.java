import org.junit.jupiter.api.Test;

public class SecondTest {


    @Test
    public void test() {

        System.out.println("isEven для 5: " + isEven(5));
        System.out.println("isEven для 6: " + isEven(6));
        System.out.println("checkAccess для 19: " + checkAccess(19));
        System.out.println("checkAccess для 18: " + checkAccess(18));
        System.out.println("isPositive для 1: " + isPositive(1));
        System.out.println("isPositive для -1: " + isPositive(-1));
        System.out.println("getGrade для -1: " + getGrade(-1));
        System.out.println("getGrade для 11: " + getGrade(11));
        System.out.println("getGrade для 24: " + getGrade(24));
        System.out.println("getGrade для 46: " + getGrade(46));
        System.out.println("getGrade для 67: " + getGrade(67));
        System.out.println("getGrade для 94: " + getGrade(94));
        System.out.println("getGrade для 101: " + getGrade(101));
        System.out.println("blastOff для 5: " + blastOff(5));
        System.out.println("blastOff для 2: " + blastOff(2));
        System.out.println("sumToN для 2: " + sumToN(2));
        System.out.println("sumToN для 10: " + sumToN(10));

    }

    /*
    Задача 1: разработать метод с сигнатурой publiс static boolean isEven(int n).
    Метод возвращает true, если число чётное, и false — если нечётное.
     */
    public static boolean isEven(int n) {
        return !(n % 2 > 0);
    }

    /*
Задача 2: разработать метод с сигнатурой public static String checkAccess(int age).
Метод возвращает Allowed, если число строго больше 18, и Denied — если меньше.
 */

    public static String checkAccess(int age) {
        return (age > 18) ? "Allowed" : "Denied";
    }

    /*
Задача 3: разработать метод с сигнатурой public static boolean isPositive(int n).
Метод должен возвращать true, если переданное число больше или равно нулю, и false,
если переданное число меньше нуля. Проверка внутри метода должна происходить
с помощью тернарного оператора.
 */

    public static boolean isPositive(int n) {
        return n > 0 ? true : false;
    }

    /*
Задача 4: разработать метод с сигнатурой public static String getGrade(int score).
Метод возвращает строку, соответствующую строгому вхождению в границы:
0–20: E;
21–40: D;
41–60: C;
61–80: B;
81–100: A.
Если переданное число не входит в границы — вернуть строку Error.
 */

    public static String getGrade(int score) {

        if (score >= 0 && score <= 20)
            return "E";
        else if (score >= 21 && score <= 40)
            return "D";
        else if (score >= 41 && score <= 60)
            return "C";
        else if (score >= 61 && score <= 80)
            return "B";
        else if (score >= 80 && score <= 100)
            return "A";
        return "Error";
    }

     /*
Задача 5: разработать метод с сигнатурой public static String blastOff(int start).
Метод принимает стартовое число (например, 5) и возвращает строку со всеми числами до 1
и словом «Поехали!» в конце (например, «5 4 3 2 1 Поехали!»).
 */

    public static String blastOff(int start){
        String rez = "";
        for (int i = start; i > 0; i--) {
            rez = rez + i + " ";
        }
        return rez + "Поехали!";
    }

/*
Задача 6: разработать метод с сигнатурой publiс static int sumToN(int n).
Метод возвращает сумму всех целых чисел от 1 до n.
 */
    public static int sumToN(int n){
        int rez = 0;
        for (int i = 1; i <= n; i++) {
            rez = rez + i;

    }
        return rez;
    }




}


/*
Задача 1: разработать метод с сигнатурой publiс static boolean isEven(int n).
Метод возвращает true, если число чётное, и false — если нечётное.

Задача 2: разработать метод с сигнатурой public static String checkAccess(int age).
Метод возвращает Allowed, если число строго больше 18, и Denied — если меньше.

Задача 3: разработать метод с сигнатурой public static boolean isPositive(int n).
Метод должен возвращать true, если переданное число больше или равно нулю, и false, если переданное число меньше нуля. Проверка внутри метода должна происходить с помощью тернарного оператора.

Задача 4: разработать метод с сигнатурой public static String getGrade(int score).
Метод возвращает строку, соответствующую строгому вхождению в границы:

0–20: E;
21–40: D;
41–60: C;
61–80: B;
81–100: A.
Если переданное число не входит в границы — вернуть строку Error.

Задача 5: разработать метод с сигнатурой public static String blastOff(int start).
Метод принимает стартовое число (например, 5) и возвращает строку со всеми числами до 1 и словом «Поехали!» в конце (например, «5 4 3 2 1 Поехали!»).

Задача 6: разработать метод с сигнатурой publiс static int sumToN(int n).
Метод возвращает сумму всех целых чисел от 1 до n.

Задача 7: разработать метод с сигнатурой publiс static boolean hasBug(String[] messages).
Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug. Сравнение можно выполнять без учёта регистра.

Задача 8: разработать метод с сигнатурой publiс static getEvenInRange(int start, int end).
Метод принимает границы диапазона и возвращает строку, состоящую только из чётных чисел внутри этого промежутка (включая границы), разделённых пробелом. Перед первым и после последнего числа пробел не ставится. Например: (2, 5) -> “2 4”

Задача 9: разработать метод с сигнатурой publiс static public int findMax(int[] arr).
Метод находит и возвращает самое большое число в переданном массиве.

Задача 10: разработать метод с сигнатурой publiс static String[] reverse(String[] arr).
Метод возвращает новый массив, в котором элементы исходного массива расположены в обратном порядке. Например, {“One”, “Two”, “Zero”} -> {“Zero”, “Two”, “One}.

Задача 11: разработать метод с сигнатурой publiс static calcAverage(List<Integer> list).
Метод вычисляет и возвращает среднее арифметическое всех чисел в списке.

Задача 12: разработать метод с сигнатурой
publiс static List<String> removeSpecificName(List<String> list, String nameToRemove).
Метод принимает список и имя, которое нужно исключить. Возвращает новый список,
не содержащий указанного имени.
 */
