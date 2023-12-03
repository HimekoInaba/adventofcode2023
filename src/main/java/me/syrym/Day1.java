package me.syrym;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;
import static me.syrym.FileReader.readLinesAsStream;

public class Day1 {

    public static void main(String[] args) {
        solvePart2();
    }

    private static void solvePart1(List<String> input) {
        var res = input.stream()
                .map(line -> line.chars()
                        .mapToObj(x -> (char) x)
                        .filter(Character::isDigit)
                        .toList())
                .map(list -> String.valueOf(list.get(0)) + list.get(list.size() - 1))
                .map(Integer::valueOf)
                .collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("RESULT:" + res);
    }

    private static void solvePart2() {
        var map = Map.ofEntries(
                entry("one", "1"),
                entry("two", "2"),
                entry("three", "3"),
                entry("four", "4"),
                entry("five", "5"),
                entry("six", "6"),
                entry("seven", "7"),
                entry("eight", "8"),
                entry("nine", "9")
        );

        var input = readLinesAsStream("src/main/resources/day1_part2.txt")
                .map(line -> {
                    var res = new StringBuilder();
                    for (int i = 0; i < line.length(); i++) {
                        if (Character.isDigit(line.charAt(i))) {
                            res.append(line.charAt(i));
                            continue;
                        }
                        var sb = new StringBuilder();
                        for (int j = i; j < line.length(); j++) {
                            if (!Character.isDigit(line.charAt(j))) {
                                var str = sb.append(line.charAt(j)).toString();
                                if (map.containsKey(str)) {
                                    res.append(map.get(str));
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    return res.toString();
                })
                .toList();
        solvePart1(input);
    }
}
