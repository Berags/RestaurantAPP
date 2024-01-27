package edu.unifi.model.entities;

import java.awt.*;

public enum TableState {
    FREE,
    RESERVED,
    AWAITING_DISHES,
    DINING,
    AWAITING_CHECK,
    ;

    public Color getColor() {
        return switch (this) {
            case FREE -> Color.GREEN;
            case RESERVED -> Color.YELLOW;
            case AWAITING_DISHES -> Color.ORANGE;
            case DINING -> Color.RED;
            case AWAITING_CHECK -> Color.MAGENTA;
            default -> Color.GRAY;
        };
    }
}
