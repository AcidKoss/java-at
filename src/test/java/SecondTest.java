import org.junit.jupiter.api.Test;

public class SecondTest {


    @Test
    public void test(){

        System.out.println("isEven для 5: " + isEven(5));
        System.out.println("isEven для 6: " + isEven(6));
        System.out.println("checkAccess для 19: " + checkAccess(19));
        System.out.println("checkAccess для 18: " + checkAccess(18));
    }

/*
Задача 1: разработать метод с сигнатурой publiс static boolean isEven(int n).
Метод возвращает true, если число чётное, и false — если нечётное.
 */
    public static boolean isEven(int n){
        return !(n % 2 > 0);
    }

    /*
Задача 2: разработать метод с сигнатурой public static String checkAccess(int age).
Метод возвращает Allowed, если число строго больше 18, и Denied — если меньше.
 */

    public static String checkAccess(int age){
        return (age > 18) ? "Allowed" : "Denied";
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
