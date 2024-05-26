package com.github.wintersteve25.tau.menu;

import com.github.wintersteve25.tau.menu.handlers.ISlotHandler;
import com.github.wintersteve25.tau.utils.SimpleVec2i;

public record MenuSlot<T extends ISlotHandler>(SimpleVec2i pos, T handler) {
}
