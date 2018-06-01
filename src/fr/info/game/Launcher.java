package fr.info.game;

import fr.info.game.logic.math.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Launcher extends JFrame {

    private final JPanel contentPane = new JPanel();

    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel progressLabel = new JLabel("Loading the game...");

    public Launcher() {
        super(AsterixAndObelixGame.GAME_TITLE);
        setContentPane(contentPane);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setPreferredSize(new Dimension(500, 200));

        contentPane.setLayout(new GridBagLayout());
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setPreferredSize(new Dimension(490, 90));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.5;
        c.insets = new Insets(5, 5, 5, 5);

        contentPane.add(progressBar, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0.5;
        c.insets = new Insets(0, 0, 0, 0);

        progressLabel.setPreferredSize(new Dimension(500, 90));
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(progressLabel, c);

        pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2-this.getSize().height / 2);
    }

    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public float getProgress() {
        return progressBar.getValue() / (float) (progressBar.getMaximum() - progressBar.getMinimum());
    }

    public void setProgress(float progress) {
        progress = MathUtils.clamp(progress, 0, 1);
        this.progressBar.setValue((int) (progress * 100));
    }

    public String getProgressText() {
        return progressLabel.getText();
    }

    public void setProgressText(String progressText) {
        this.progressLabel.setText(progressText);
    }
}
