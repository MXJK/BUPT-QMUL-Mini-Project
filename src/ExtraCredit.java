import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ExtraCredit extends JFrame {

    private JComboBox<Integer> numberDropdown;
    private JLabel productLabel;
    private JTextField answerField;
    private JLabel feedbackLabel;
    private JButton startButton;
    private JButton selectButton;
    private JButton deleteButton;
    private JLabel ratingLabel;
    private JLabel selectedNumbers;
    private int correctCount = 0;
    private int questionCount = 0;
    private int currentMultiplier;
    private int currentRandom;
    private ArrayList<Integer> selectedMultiplicand;
    //Timer attribute
    private Timer timer;
    private int secondsElapsed;

    public ExtraCredit() {
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
        selectButton = new JButton("Select");
        deleteButton = new JButton("Delete");
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("superhero.jpg")));
        ratingLabel = new JLabel("Rating");
        selectedNumbers = new JLabel("Please select the multiplier" + "  Your multiplier: " + selectedMultiplicand);
        selectedMultiplicand = new ArrayList<>();
        secondsElapsed = 0;

        // Layout setting
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(numberDropdown, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(selectButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(deleteButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(selectedNumbers, gbc);

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

        //Set timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                System.out.println(secondsElapsed);
            }
        });

        // Select button action monitor
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!selectedMultiplicand.contains((Integer) numberDropdown.getSelectedItem())) {
                    selectedMultiplicand.add((Integer) numberDropdown.getSelectedItem());
                    selectedNumbers.setText("Your multiplier: " + selectedMultiplicand);
                    answerField.requestFocusInWindow();
                }
                else {
                    showPopup2();
                }
            }
        });

        //Delete button action monitor
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMultiplicand.contains((Integer) numberDropdown.getSelectedItem())) {
                    selectedMultiplicand.remove((Integer) numberDropdown.getSelectedItem());
                    selectedNumbers.setText("Your multiplier: " + selectedMultiplicand);
                    answerField.requestFocusInWindow();
                }
                else {
                    selectedNumbers.setText("You haven't chosen this multiplier." +
                            "  Your multiplier: " + selectedMultiplicand);
                }
            }
        });

        // Start button action monitor
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMultiplicand.isEmpty()) {
                    showPopup1();
                }
                else {
                    if (startButton.getText().equals("Start")) {
                        ratingLabel.setText("Rating");
                        startRound();
                    } else {
                        checkAnswer();
                    }
                }
            }
        });
    }

    public void showPopup1() {
        // Display pop-ups using the JOptionPane
        JOptionPane.showMessageDialog(this, "Please select at least one number as the multiplier",
                "error", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showPopup2() {
        // Display pop-ups using the JOptionPane
        JOptionPane.showMessageDialog(this, "You can't pick a duplicate number as a multiplier",
                "error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startRound() {
        selectedNumbers.setText("Please select the multiplier" + "  Your multiplier: " + selectedMultiplicand);
        answerField.setText("");
        correctCount = 0;
        questionCount = 0;
        startButton.setText("Next");
        timer.start();
        System.out.println("Start!");
        answerField.requestFocusInWindow();
        nextQuestion();
    }

    private void nextQuestion() {
        if (questionCount >= 5) {
            startButton.setText("Start");
            ratingLabel.setText("You got " + correctCount + " correct!");
            selectedMultiplicand.clear();
            productLabel.setText("Product");
            feedbackLabel.setText("You took" + secondsElapsed + "seconds");
            timer.stop();
            System.out.println("Ended!");
            secondsElapsed = 0;
        } else {
            Random random = new Random();
            currentRandom = random.nextInt(12) + 1;
            if (!selectedMultiplicand.isEmpty()) {
                currentMultiplier = selectedMultiplicand.get(random.nextInt(selectedMultiplicand.size()));
                productLabel.setText(currentMultiplier + " * " + currentRandom + "=");
                questionCount++;
            }
        }
    }

    private void checkAnswer() {
        if (answerField.getText().trim().isEmpty()) {
            answerField.requestFocusInWindow();
            return;
        }
        try {
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
        answerField.requestFocusInWindow();//Set focus to answer box
        nextQuestion();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExtraCredit().setVisible(true);
            }
        });
    }
}
