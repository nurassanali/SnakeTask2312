package com.game.snake.view.gui.mainmenu;

import com.game.snake.view.gui.mainmenu.component.*;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Koliadin Nikita
 * @version 1.13
 */
public class MainMenuGUI implements Runnable {

    private final JFrame jFrame = new JFrame();

    private final Container container = jFrame.getContentPane();
    private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

    @Getter @Setter private LabelWelcome labelWelcome = new LabelWelcome(container, gridBagConstraints);
    @Getter @Setter private ButtonPlay buttonPlay = new ButtonPlay(container, gridBagConstraints, jFrame);
    @Getter @Setter private ButtonSetting buttonSetting = new ButtonSetting(container, gridBagConstraints);
    @Getter @Setter private ButtonInfo buttonInfo = new ButtonInfo(container, gridBagConstraints);
    @Getter @Setter private ButtonExit buttonExit = new ButtonExit(container, gridBagConstraints, jFrame);
    @Getter @Setter private LabelAuthor labelAuthor =  new LabelAuthor(container, gridBagConstraints);

    @Getter @Setter private int minWidth = 350;
    @Getter @Setter private int minHeight = 350;

    public MainMenuGUI() {
        container.setLayout(new GridBagLayout());
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }

    @Override
    public void run() {
        val componentList = loadComponentList();

        componentList.forEach(component -> {
            component.init();
            component.setAction();
        });

        initJFrame();
    }

    @NotNull
    private List<MainMenuComponent> loadComponentList() {
        return new ArrayList<>(Arrays.asList(
                labelWelcome,
                buttonPlay,
                buttonSetting,
                buttonInfo,
                buttonExit,
                labelAuthor
        ));
    }

    private void initJFrame() {
        jFrame.setTitle("Snake - MAIN MENU");
        setJFrameSize();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null); /* Set the center of the screen */
        jFrame.setVisible(true);
    }

    private void setJFrameSize() {
        jFrame.setMinimumSize(new Dimension(
                minWidth,
                minHeight
        ));
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}
