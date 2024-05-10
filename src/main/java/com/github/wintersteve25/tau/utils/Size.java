package com.github.wintersteve25.tau.utils;

import com.github.wintersteve25.tau.Tau;

@FunctionalInterface
public interface Size {
    Size ZERO = (s) -> SimpleVec2i.zero();

    SimpleVec2i get(SimpleVec2i maxSize);

    static Size percentage(float percentage) {
        if (percentage < 0 || percentage > 1) {
            Tau.LOGGER.error("Size percentage can not be less than 0 or greater than 1");
            return ZERO;
        }

        return size -> new SimpleVec2i(Math.round(size.x * percentage), Math.round(size.y * percentage));
    }

    static Size percentage(float percentageX, float percentageY) {
        if (percentageX < 0 || percentageX > 1 || percentageY < 0 || percentageY > 1) {
            Tau.LOGGER.error("Size percentage can not be less than 0 or greater than 1");
            return ZERO;
        }

        return size -> new SimpleVec2i(Math.round(size.x * percentageX), Math.round(size.y * percentageY));
    }

    static Size staticSize(SimpleVec2i size) {
        return s -> size;
    }

    static Size staticSize(int width, int height) {
        SimpleVec2i size = new SimpleVec2i(width, height);
        return s -> size;
    }
}
