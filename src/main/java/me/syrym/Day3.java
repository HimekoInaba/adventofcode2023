package me.syrym;

import java.util.List;
import java.util.stream.Stream;

import static me.syrym.FileReader.readLinesAsStream;

public class Day3 {

    public static void main(String[] args) {
        solvePart2();
    }

    private static void solvePart1() {
        List<List<Character>> list = getParsedInput();

        int res = 0;
        for (int i = 0; i < list.size(); i++) {
            int start = i;
            boolean prevIsDigit = false;
            for (int j = 0; j < list.get(0).size(); j++) {
                char curr = list.get(i).get(j);

                if (Character.isDigit(curr)) {
                    if (!prevIsDigit) {
                        prevIsDigit = true;
                        start = j;
                    }
                } else {
                    if (prevIsDigit) {
                        prevIsDigit = false;

                        var sb = new StringBuilder();
                        var isValid = false;
                        for (int k = start; k < j; k++) {
                            isValid |= checkAround(list, i, k);
                            sb.append(list.get(i).get(k));
                        }
                        if (isValid) {
                            res += Integer.parseInt(sb.toString());
                        }
                    }
                }
            }
            if (prevIsDigit) {
                var sb = new StringBuilder();
                var isValid = false;
                for (int k = start; k < list.get(0).size(); k++) {
                    isValid |= checkAround(list, i, k);
                    sb.append(list.get(i).get(k));
                }
                if (isValid) {
                    res += Integer.parseInt(sb.toString());
                }
            }
        }

        System.out.println(res);
    }

    private static void solvePart2() {
        var list = getParsedInput();

        int res = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                var curr = list.get(i).get(j);

                if (isGear(curr)) {
                    res += adjacentNumsProduct(list, i, j);
                }
            }
        }

        System.out.println(res);
    }

    private static int adjacentNumsProduct(List<List<Character>> list, int i, int j) {
        int left = getNum(list, i, j - 1);
        int right = getNum(list, i, j + 1);
        int down = getNum(list, i + 1, j);
        int up = getNum(list, i - 1, j);
        int leftUp = getNum(list, i - 1, j - 1);
        int rightUp = getNum(list, i - 1, j + 1);
        int leftDown = getNum(list, i + 1, j - 1);
        int rightDown = getNum(list, i + 1, j + 1);

        var r = Stream.of(left, right, down, up, leftUp, rightUp, leftDown, rightDown)
                .filter(it -> it != Integer.MIN_VALUE)
                .distinct()
                .toList();
        if (r.size() != 2) {
            return 0;
        }
        return r.get(0) * r.get(1);
    }

    private static int getNum(List<List<Character>> list, int i, int j) {
        if (i < 0 || j < 0 || i >= list.size() || j >= list.get(0).size()) {
            return Integer.MIN_VALUE;
        }
        var curr = list.get(i).get(j);
        if (!Character.isDigit(curr)) {
            return Integer.MIN_VALUE;
        }
        var sb = new StringBuilder();
        var currList = list.get(i);
        int start = j;
        while (start >= 0 && Character.isDigit(currList.get(start))) {
            if (start - 1 < 0) {
                break;
            }
            if (!Character.isDigit(currList.get(start - 1))) {
                break;
            }
            start--;
        }
        while (start < currList.size() && Character.isDigit(currList.get(start))) {
            sb.append(currList.get(start));
            start++;
        }
        return Integer.parseInt(sb.toString());
    }

    private static List<List<Character>> getParsedInput() {
        return readLinesAsStream("src/main/resources/day3_part1.txt")
                .map(line -> line.chars()
                        .mapToObj(c -> (char) c)
                        .toList())
                .toList();
    }

    private static boolean checkAround(List<List<Character>> list, int i, int j) {
        return isSpecial(list, i + 1, j)
                || isSpecial(list, i - 1, j)
                || isSpecial(list, i, j + 1)
                || isSpecial(list, i, j - 1)
                || isSpecial(list, i + 1, j + 1)
                || isSpecial(list, i - 1, j - 1)
                || isSpecial(list, i - 1, j + 1)
                || isSpecial(list, i + 1, j - 1);
    }

    private static boolean isSpecial(List<List<Character>> list, int i, int j) {
        if (i < 0 || j < 0 || i >= list.size() || j >= list.get(0).size()) {
            return false;
        }
        return isPart(list.get(i).get(j));
    }

    private static boolean isPart(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private static boolean isGear(char c) {
        return c == '*';
    }
}
