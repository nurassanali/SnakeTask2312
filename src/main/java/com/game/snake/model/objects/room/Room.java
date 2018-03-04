package com.game.snake.model.objects.room;

import com.game.snake.view.gui.play.PlayGUI;
import com.game.snake.model.objects.mouse.Mouse;
import com.game.snake.view.graphics.ChangeColor;
import com.game.snake.view.graphics.Layer;
import com.game.snake.model.objects.snake.Snake;
import com.game.snake.model.objects.snake.SnakeDirection;
import com.game.snake.model.objects.snake.SnakeSection;
import com.game.snake.model.setting.Setting;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;

/**
 * @author Koliadin Nikita
 * @version 1.13
 */
public class Room implements Runnable {

    @Getter @Setter private static Room room; 

    private final Setting setting = Setting.getInstance();

    private final JFrame jFrame;

    private final PlayGUI playGUI = new PlayGUI();

    @Getter @Setter private Snake snake;
    @Getter @Setter private Mouse mouse;

    @Getter @Setter private int width = setting.getRoomWidth(); //TODO: edit setting
    @Getter @Setter private int height = setting.getRoomHeight(); //TODO: edit setting

    public Room(final JFrame jFrame) {
        this.jFrame = jFrame;
        this.snake = new Snake();
        createMouse();
    }

    @Override
    public void run() {
        Executors.newSingleThreadExecutor().execute(playGUI); // FIXME: shutdown

        while (snake.isAlive()) {
            if (playGUI.hasKeyEvents()) {
                final KeyEvent event = playGUI.getEventFromTop();

                checkPause(event);

                if (isExit(event)) {
                    gameOver();
                    return;
                }

                checkDirection(event);
            }
            snake.move();
            print();
            sleep();
        }
        gameOver();
    }

    public void eatMouse() {
        createMouse();
    }

    private void checkPause(@NotNull final KeyEvent event) {
        if (event.getKeyChar() == 'p') {
            while (true) {
                sleep(1000);
                if (playGUI.hasKeyEvents()) {
                    KeyEvent eventNew = playGUI.getEventFromTop();
                    if (eventNew.getKeyChar() == 'p') {
                        break;
                    }
                }
            }
        }
    }

    private boolean isExit(@NotNull final KeyEvent event) {
        return event.getKeyChar() == KeyEvent.VK_ESCAPE;
    }

    private void checkDirection(@NotNull final KeyEvent event) {
        final int i = event.getKeyCode();

        if (i == KeyEvent.VK_LEFT) {
            if (snake.getDirection() != SnakeDirection.RIGHT) {
                snake.setDirection(SnakeDirection.LEFT);
            }
        } else if (i == KeyEvent.VK_RIGHT) {
            if (snake.getDirection() != SnakeDirection.LEFT) {
                snake.setDirection(SnakeDirection.RIGHT);
            }
        } else if (i == KeyEvent.VK_UP) {
            if (snake.getDirection() != SnakeDirection.DOWN) {
                snake.setDirection(SnakeDirection.UP);
            }
        } else if (i == KeyEvent.VK_DOWN) {
            if (snake.getDirection() != SnakeDirection.UP) {
                snake.setDirection(SnakeDirection.DOWN);
            }
        } else if (i == KeyEvent.VK_W) {
            if (snake.getDirection() != SnakeDirection.DOWN) {
                snake.setDirection(SnakeDirection.UP);
            }
        } else if (i == KeyEvent.VK_S) {
            if (snake.getDirection() != SnakeDirection.UP) {
                snake.setDirection(SnakeDirection.DOWN);
            }
        } else if (i == KeyEvent.VK_A) {
            if (snake.getDirection() != SnakeDirection.RIGHT) {
                snake.setDirection(SnakeDirection.LEFT);
            }
        } else if (i == KeyEvent.VK_D) {
            if (snake.getDirection() != SnakeDirection.LEFT) {
                snake.setDirection(SnakeDirection.RIGHT);
            }
        }
    }

    private void gameOver() {
        playGUI.offVisible();
        ChangeColor.setMainMenuWaitThread(false);
        jFrame.setVisible(true);
    }

    private void createMouse() {
        mouse = new Mouse((int) (Math.random() * width + 1), (int) (Math.random() * height + 1));
        for (SnakeSection snakeSection : snake.getSections()) {
            if (snakeSection.getX() == mouse.getX() && snakeSection.getY() == mouse.getY()) {
                createMouse();
            }
        }
    }

    private void print() {
        if (playGUI != null) {
            playGUI.getJFrame().setContentPane(new Layer());
            playGUI.onVisible();
        }
    }

    private void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level <= 16 ? (300 - 10 * level - 1) : 150;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
