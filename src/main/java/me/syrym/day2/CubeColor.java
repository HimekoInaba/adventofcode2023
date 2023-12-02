package me.syrym.day2;

public enum CubeColor {
    BLUE("blue"),
    RED("red"),
    GREEN("green");

    public String value;

    CubeColor(String color) {
        this.value = color;
    }

    public static CubeColor fromString(String str) {
        return CubeColor.valueOf(str.toUpperCase());
    }
}
