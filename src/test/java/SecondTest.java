import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SecondTest {

    static final Random random = new Random();

    @BeforeEach
    public void first() {
        System.out.println("========================\n" +
                "\n" +
                "Test method start");
    }

    @AfterEach
    public void last() {
        System.out.println("Test method end\n" +
                "\n" +
                "========================");
    }

    @Test
    @Disabled
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
        System.out.println("hasBug для случая нет bug: " + hasBug(new String[]{"Hello", "Кот", "World"}));
        System.out.println("hasBug для случая есть bug: " + hasBug(new String[]{"Hello", "BUG", "World"}));
        System.out.println("getEvenInRange: " + getEvenInRange(1, 9));
        System.out.println("findMax -7,5,10,-45,41,-32,1: " + findMax(new int[]{-7, 5, 10, -45, 41, -32, 1}));
        System.out.println("reverse: " + Arrays.toString(reverse(new String[]{"Zero", "One", "Two"})));
        System.out.println("calcAverage 4, 12, 73, 52, 13: " + calcAverage(List.of(4, 12, 73, 52, 13)));
        System.out.println("removeSpecificName - \"Hello\", \"BUG\", \"World\" удалим \"BUG\": " + removeSpecificName(List.of("Hello", "BUG", "World"), "BUG"));

    }

    /*
        Задание 1
         */
    @Test
    @Tag("Test")
    public void isEvenOldTest() {

        int number = random.nextInt(1, 101);
        System.out.println("isEven с числом " + number + ": " + isEven(number));
    }

    @Test
    @Tag("Test")
    public void checkAccessOldTest() {
        for (int i = 1; i <= 20; i++) {
            int number = random.nextInt(1, 100);
            System.out.println(i + "-ый запуск checkAccess с числом " + number + ": " + checkAccess(number));
        }
    }

    @ParameterizedTest
    @MethodSource("numbers")
    @Tag("Test")
    public void getGradeOldTest(int number) {
        System.out.println("запуск getGrade с числом " + number + ": " + getGrade(number));
    }


    /*
        Задание 2
         */

    @Test
    @Tag("Test")
    public void isEvenTest() {
        int num = randomNumber_1_100();
        System.out.println("Проверка на четность числа: " + num);
        if (isEven(num)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void checkAccessTest() {
        int num = randomNumber_1_100();
        System.out.println("Проверка чисел на больше 18: " + num);
        if (checkAccess(num).equals("Allowed")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void isPositiveTest() {
        int num = randomNumber_minus100_100();
        System.out.println("Проверка чисел на больше или равно 0: " + num);
        if (isPositive(num)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void getGradeTest() {
        int num = randomNumber_minus100_100();
        System.out.println("Проверка грейда в зависимости от числа: " + num);
        switch (getGrade(num)) {
            case "A","B","C","D","E" -> System.out.println("TEST PASSED");
            default -> System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void blastOffTest() {
        int num = random.nextInt(0,10);
        System.out.println("Проверка вывода строчки 5 4 3 2 1 Поехали!: " + num);
        switch (blastOff(num)) {
            case "5 4 3 2 1 Поехали!","4 3 2 1 Поехали!","3 2 1 Поехали!","2 1 Поехали!","1 Поехали!" -> System.out.println("TEST PASSED");
            default -> System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void sumToNTest() {
        int num = random.nextInt(1,10);
        System.out.println("Проверка метода возврата суммы всех целых чисел для 1,2,3,4,5: " + num);
        switch (sumToN(num)) {
            case 1,3,6,10,15 -> System.out.println("TEST PASSED");
            default -> System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void hasBugTest() {
        String[] arr = randomString();
        System.out.println("Проверка наличия bug в массиве " + Arrays.toString(arr));
        if (hasBug(arr)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    /*
    Вспомогательный метод для создания рандомного массива
     */
    static int[] numbers() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1, 101);
        }
        return arr;
    }

    /*
    Вспомогательный метод для создания рандомного числа от 1 до 100
     */
    static int randomNumber_1_100() {
        return random.nextInt(1, 101);
    }

    /*
    Вспомогательный метод для создания рандомного числа от -100 до 100
     */
    static int randomNumber_minus100_100() {
        return random.nextInt(-100, 101);
    }

    /*
    Вспомогательный метод для возврата рандомной строчки содержащей\не содержащей "bug"
            */
    static String[] randomString() {
        String[] var1 = new String[]{"Hello", "Кот", "World"};
        String[] var2 = new String[]{"Hello", "BUG", "World"};
        int num = random.nextInt(1, 3);
        if (num == 1) {
            return var1;
        }
        return var2;
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

    public static String blastOff(int start) {
        String result = "";
        for (int i = start; i > 0; i--) {
            result = result + i + " ";
        }
        return result + "Поехали!";
    }

    /*
    Задача 6: разработать метод с сигнатурой publiс static int sumToN(int n).
    Метод возвращает сумму всех целых чисел от 1 до n.
     */
    public static int sumToN(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result = result + i;

        }
        return result;
    }

/*
Задача 7: разработать метод с сигнатурой public static boolean hasBug(String[] messages).
Метод принимает массив строк и возвращает true, если хотя бы одна строка в массиве равна Bug.
Сравнение можно выполнять без учёта регистра.
 */

    public static boolean hasBug(String[] messages) {
        for (String message : messages) {
            if (message.equalsIgnoreCase("bug"))
                return true;

        }
        return false;
    }

    /*
Задача 8: разработать метод с сигнатурой publiс static getEvenInRange(int start, int end).
Метод принимает границы диапазона и возвращает строку, состоящую только из
чётных чисел внутри этого промежутка (включая границы), разделённых пробелом.
Перед первым и после последнего числа пробел не ставится. Например: (2, 5) -> “2 4”
 */

    public static String getEvenInRange(int start, int end) {
        String result = "";
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0)
                result = result + i + " ";
        }
        return result;
    }

    /*
Задача 9: разработать метод с сигнатурой publiс static public int findMax(int[] arr).
Метод находит и возвращает самое большое число в переданном массиве.
 */

    public static int findMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max)
                max = i;

        }
        return max;
    }

/*
Задача 10: разработать метод с сигнатурой publiс static String[] reverse(String[] arr).
Метод возвращает новый массив, в котором элементы исходного массива расположены в обратном порядке.
Например, {“One”, “Two”, “Zero”} -> {“Zero”, “Two”, “One}.
 */

    public static String[] reverse(String[] arr) {
        String[] reverse = new String[arr.length];
        int reverseIndex = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            reverse[reverseIndex] = arr[i];
            reverseIndex++;
        }
        return reverse;
    }

/*
Задача 11: разработать метод с сигнатурой publiс static calcAverage(List<Integer> list).
Метод вычисляет и возвращает среднее арифметическое всех чисел в списке.
 */

    public static double calcAverage(List<Integer> list) {
        int sum = 0;
        for (int element : list)
            sum = sum + element;
        return (double) sum / list.size();
    }

    /*
Задача 12: разработать метод с сигнатурой
publiс static List<String> removeSpecificName(List<String> list, String nameToRemove).
Метод принимает список и имя, которое нужно исключить. Возвращает новый список,
не содержащий указанного имени.
 */

    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> newList = new ArrayList<>(list);
        newList.remove(nameToRemove);
        return newList;
    }
}

