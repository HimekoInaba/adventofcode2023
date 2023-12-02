package me.syrym.day2;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toMap;
import static me.syrym.FileReader.readLinesAsStream;
import static me.syrym.day2.CubeColor.*;

public class Day2 {

    public static void main(String[] args) {
        solvePart1();
    }

    private static void solvePart1() {
        Map<CubeColor, Integer> thresholds = Map.of(
                RED, 12,
                GREEN, 13,
                BLUE, 14
        );

        var i = new AtomicInteger(0);
        var res = readLinesAsStream("src/main/resources/day2_part1.txt")
                .map(line -> line.substring(line.indexOf(":") + 2)) // remove "Game #: "
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
                .collect(toMap(n -> i.incrementAndGet(), game -> game))
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
}
