package com.github.wintersteve25.tau.layout;

@FunctionalInterface
public interface LayoutSetting {
    LayoutSetting START = (maxLength, componentLength) -> 0;
    LayoutSetting CENTER = (maxLength, componentLength) -> (maxLength - componentLength) / 2;
    LayoutSetting END = (maxLength, componentLength) -> maxLength - componentLength;
    
    static LayoutSetting percentage(float percent) {
        return ((maxLength, componentLength) -> (int) ((maxLength - componentLength) * percent));
    }
    
    int place(int maxLength, int componentLength);
}
