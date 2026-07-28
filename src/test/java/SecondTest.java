import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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
    @Tag("Test")
    public void isEvenTest() {
        int number = random.nextInt(-100, 101);
        boolean expected = number % 2 == 0;
        assertThat(isEven(number))
                .as("Не верно определилась четность у числа: " + number)
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void checkAccessTest() {
        int number = random.nextInt(-100, 101);
        String expected = "";
        if (number > 18)
            expected = "Allowed";
        else
            expected = "Denied";
        assertThat(checkAccess(number))
                .as("Не верно сработала проверка на больше 18: " + number)
                .isEqualTo(expected);
    }


    @Tag("Test")
    @RepeatedTest(2)
    public void isPositiveTest() {
        int number = random.nextInt(-100, 101);
        boolean expected = number > 0;
        assertThat(isPositive(number))
                .as("Не верно сработала проверка на позитивность числа: " + number)
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void getGradeTest() {
        int number = random.nextInt(-100, 1000);
        String expected = "";
        if (number >= 0 && number <= 20)
            expected = "E";
        else if (number >= 21 && number <= 40)
            expected = "D";
        else if (number >= 41 && number <= 60)
            expected = "C";
        else if (number >= 61 && number <= 80)
            expected = "B";
        else if (number >= 81 && number <= 100)
            expected = "A";
        else
            expected = "Error";
        assertThat(getGrade(number))
                .as("Не верно определился грейд для числа: " + number)
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void blastOffTest() {
        int number = random.nextInt(0, 6);
        String expected = "";
        if (number == 0)
            expected = "Поехали!";
        else if (number == 1)
            expected = "1 Поехали!";
        else if (number == 2)
            expected = "2 1 Поехали!";
        else if (number == 3)
            expected = "3 2 1 Поехали!";
        else if (number == 4)
            expected = "4 3 2 1 Поехали!";
        else
            expected = "5 4 3 2 1 Поехали!";

        assertThat(blastOff(number))
                .as("Не верно составилась строча Поехали! для числа: " + number)
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void sumToNTest() {
        int number = random.nextInt(1, 10);

        int expected = 0;
        for (int i = 1; i <= number; i++) {
            expected = expected + i;
        }

        assertThat(sumToN(number))
                .as("Не верно посчиталась сумма целых чисел до числа: " + number)
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void hasBugTest() {
        String[] arr = new String[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = String.valueOf(random.nextInt(1000, 10000));
        }
        arr[random.nextInt(0,arr.length)] = "bug";
        boolean expected = false;
        for (String element : arr) {
            if (element.equalsIgnoreCase("bug"))
                expected = true;
        }

        assertThat(hasBug(arr))
                .as("Не верно определяется наличие BUG в массиве : " + Arrays.toString(arr))
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void getEvenInRangeTest() {
        int first = random.nextInt(1, 7);
        int second = first + random.nextInt(2, 7);
        String expected = "";

        for (int i = first; i <= second; i++) {
            if (i % 2 == 0 && i < second)
                expected = expected + i + " ";
            else if (i % 2 == 0 && i == second)
                expected = expected + i;
        }

        assertThat(getEvenInRange(first, second))
                .as("Не верно вывелись четные числа для границы: " + first + " " + second)
                .isEqualTo(expected);
    }

    @Test
    @Tag("Test")
    public void findMaxTest() {
        int[] arr = numbers();
        System.out.println("Проверка самого большого числа в массиве " + Arrays.toString(arr));
        if (findMax(arr) == 100) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void reverseTest() {
        String[] arr = randomStringArr();
        System.out.println("Проверка перевернутого массива " + Arrays.toString(arr));
        if (Arrays.equals(reverse(arr), new String[]{"World", "Кот", "Hello"})) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void calcAverageTest() {
        List<Integer> arr = randomIntList();
        System.out.println("Проверка средне арифметического " + arr);
        if (calcAverage(arr) == 10) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Test
    @Tag("Test")
    public void removeSpecificNameTest() {
        List<String> arr = randomStringList();
        System.out.println("Проверка удаление слова BUG из списка " + arr);
        if (!removeSpecificName(arr, "BUG").contains("BUG")) {
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
    static String[] randomStringArr() {
        String[] var1 = new String[]{"Hello", "Кот", "World"};
        String[] var2 = new String[]{"Hello", "BUG", "World"};
        int num = random.nextInt(1, 3);
        if (num == 1) {
            return var1;
        }
        return var2;
    }

    /*
        Вспомогательный метод для возврата Листа с рандомными числами"
                */
    static List<Integer> randomIntList() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(random.nextInt(1, 101));
        }
        return numbers;
    }

    /*
        Вспомогательный метод для возврата Листа с рандомными словами + BUG"
                */
    static List<String> randomStringList() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add(String.valueOf(random.nextInt(100, 10000)));
        }
        stringList.add(random.nextInt(0, 10), "BUG");
        return stringList;
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
        else if (score >= 81 && score <= 100)
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
            if (i % 2 == 0 && i < end)
                result = result + i + " ";
            else if (i % 2 == 0 && i == end)
                result = result + i;
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

