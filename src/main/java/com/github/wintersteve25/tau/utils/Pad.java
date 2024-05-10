package com.github.wintersteve25.tau.utils;

public class Pad {
    public final int top;
    public final int bottom;
    public final int left;
    public final int right;

    public Pad(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public SimpleVec2i getSize() {
        return new SimpleVec2i(left + right, top + bottom);
    }

    public static final class Builder {
        private int top;
        private int bottom;
        private int left;
        private int right;

        public Builder() {
        }

        public Builder top(int top) {
            this.top = top;
            return this;
        }

        public Builder bottom(int bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder left(int left) {
            this.left = left;
            return this;
        }

        public Builder right(int right) {
            this.right = right;
            return this;
        }

        public Builder all(int amount) {
            this.top = amount;
            this.bottom = amount;
            this.left = amount;
            this.right = amount;
            return this;
        }

        public Pad build() {
            return new Pad(top, bottom, left, right);
        }
    }
}
