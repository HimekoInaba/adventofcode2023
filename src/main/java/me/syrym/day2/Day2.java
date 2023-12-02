package me.syrym.day2;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toMap;
import static me.syrym.FileReader.readLinesAsStream;
import static me.syrym.day2.CubeColor.*;

public class Day2 {

    public static void main(String[] args) {
        solvePart2();
    }

    private static void solvePart1() {
        Map<CubeColor, Integer> thresholds = Map.of(
                RED, 12,
                GREEN, 13,
                BLUE, 14
        );

        var res = getParsedInput()
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue()
                        .gameSets
                        .stream()
                        .anyMatch(gameSets -> gameSets.cubes
                                .stream()
                                .anyMatch(cube -> cube.value > thresholds.get(cube.color))))
                .map(Map.Entry::getKey)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println(res);
    }

    private static void solvePart2() {
        var res = getParsedInput()
                .entrySet()
                .stream()
                .map(game -> {
                    int maxRed = 0;
                    int maxGreen = 0;
                    int maxBlue = 0;
                    for (GameSet gameSet : game.getValue().gameSets) {
                        maxBlue = Math.max(maxBlue, gameSet.cubes
                                .stream()
                                .filter(cube -> cube.color == BLUE)
                                .map(it -> it.value)
                                .mapToInt(Integer::intValue)
                                .max()
                                .orElse(0));
                        maxGreen = Math.max(maxGreen, gameSet.cubes
                                .stream()
                                .filter(cube -> cube.color == GREEN)
                                .map(it -> it.value)
                                .mapToInt(Integer::intValue)
                                .max()
                                .orElse(0));
                        maxRed = Math.max(maxRed, gameSet.cubes
                                .stream()
                                .filter(cube -> cube.color == RED)
                                .map(it -> it.value)
                                .mapToInt(Integer::intValue)
                                .max()
                                .orElse(0));
                    }
                    return maxBlue * maxRed * maxGreen;
                })
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(res);
    }

    private static Map<Integer, Game> getParsedInput() {
        var i = new AtomicInteger(0);
        return readLinesAsStream("src/main/resources/day2_part1.txt")
                .map(line -> line.substring(line.indexOf(":") + 1)) // remove "Game #: "
                .map(line -> Arrays.stream(line.split(";"))
                        .map(singleGame -> Arrays.stream(singleGame.split(","))
                                .map(String::trim)
                                .map(cube -> {
                                    var pair = cube.split(" ");
                                    return new Cube(CubeColor.fromString(pair[1]), Integer.valueOf(pair[0]));
                                })
                                .toList())
                        .map(GameSet::new)
                        .toList())
                .map(Game::new)
                .collect(toMap(n -> i.incrementAndGet(), game -> game));
    }
}
