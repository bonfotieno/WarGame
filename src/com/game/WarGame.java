package com.game;


import com.game.controller.WarGameWorld;

import java.io.IOException;

public class WarGame {
    public static void main(String[] args) {
        try {
            new WarGameWorld().run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
