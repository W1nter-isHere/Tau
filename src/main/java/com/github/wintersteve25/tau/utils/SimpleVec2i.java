package com.github.wintersteve25.tau.utils;

public class SimpleVec2i {
    public int x;
    public int y;

    public SimpleVec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(SimpleVec2i other) {
        this.x += other.x;
        this.y += other.y;
    }

    public SimpleVec2i addNew(SimpleVec2i other) {
        return new SimpleVec2i(x + other.x, y + other.y);
    }

    public boolean outside(SimpleVec2i other) {
        return x > other.x || y > other.y;
    }

    public static SimpleVec2i zero() {
        return new SimpleVec2i(0, 0);
    }

    public static boolean within(int mouseX, int mouseY, SimpleVec2i position, SimpleVec2i size) {
        return mouseX > position.x && mouseX < position.x + size.x && mouseY > position.y && mouseY < position.y + size.y;
    }

    public static boolean within(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
