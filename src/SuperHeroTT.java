import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SuperHeroTT extends JFrame {

    private JComboBox<Integer> numberDropdown;
    private JLabel productLabel;
    private JTextField answerField;
    private JLabel feedbackLabel;
    private JButton startButton;
    private JLabel ratingLabel;
    private int correctCount = 0;
    private int questionCount = 0;
    private int currentMultiplier;
    private int currentRandom;

    public SuperHeroTT() {
        // Setup window
        setTitle("SuperHero Times Tables");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Create component
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        numberDropdown = new JComboBox<>(numbers);
        productLabel = new JLabel("Product");
        answerField = new JTextField(5);
        feedbackLabel = new JLabel("Answer");
        startButton = new JButton("Start");
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("superhero.jpg")));
        ratingLabel = new JLabel("Rating");

        // Layout setting
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(numberDropdown, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(startButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(productLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(answerField, gbc);

        gbc.gridx = 2;
        add(feedbackLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(feedbackLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(imageLabel, gbc);

        gbc.gridx = 2;
        add(ratingLabel, gbc);

        // Button action monitor
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButton.getText().equals("Start")) {
                    ratingLabel.setText("Rating");
                    startRound();
                } else {
                    checkAnswer();
                }
            }
        });
    }

    private void startRound() {
        correctCount = 0;
        questionCount = 0;
        startButton.setText("Next");
        nextQuestion();
    }

    private void nextQuestion() {
        if (questionCount >= 5) {
            startButton.setText("Start");
            ratingLabel.setText("You got " + correctCount + " correct!");
        } else {
            Random rand = new Random();
            currentMultiplier = (Integer) numberDropdown.getSelectedItem();
            currentRandom = rand.nextInt(12) + 1;
            productLabel.setText(currentMultiplier + " * " + currentRandom + "=");
            questionCount++;
        }
    }

    private void checkAnswer() {
        try {
            if (answerField.getText().equals("")) return;
            int userAnswer = Integer.parseInt(answerField.getText());
            int correctAnswer = currentMultiplier * currentRandom;
            if (userAnswer == correctAnswer) {
                feedbackLabel.setText("Correct!");
                correctCount++;
            } else {
                feedbackLabel.setText("Wrong! " + productLabel.getText() + " = " + correctAnswer);
            }
        } catch (NumberFormatException e) {
            // No input or invalid input, no processing
        }
        answerField.setText("");
        nextQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SuperHeroTT().setVisible(true);
            }
        });
    }
}
