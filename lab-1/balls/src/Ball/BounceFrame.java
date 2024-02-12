package Ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BounceFrame extends JFrame {
    private JTextField textFieldCreateBallsNumber;
    private JTextField textFieldBallsInPocket;
    private JTextField textFieldCreateMaxPriorityBallsNumber;
    private JTextField textFieldCreateMinPriorityBallsNumber;
    private final JRadioButton radioButtonBlueFirst = new JRadioButton("Blue");
    private final JRadioButton radioButtonRedFirst = new JRadioButton("Red");
    private final ArrayList<BallThread> ballThreads = new ArrayList<>();;
    private BallCanvas canvas;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 450;
    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        this.canvas.setBallsChangedHandler(new BallsInPocketChangedEventHandler() {
            @Override
            public void onBallsInPocketChanged(int ballsInPockets) {
                textFieldBallsInPocket.setText(Integer.toString(ballsInPockets));
            }
        });

        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(Color.yellow);
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        addControls(controlsPanel);
        content.add(controlsPanel, BorderLayout.SOUTH);
    }

    protected void addControls(JPanel controlsPanel) {
        controlsPanel.add(createInputOutputPanel());
        controlsPanel.add(createButtonsPanel(), BorderLayout.SOUTH);
    }

    protected JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonAddPocket= new JButton("Add Pocket");
        JButton buttonStop = new JButton("Stop");
        JButton buttonCreate = new JButton("Create");
        JButton buttonCreateInOnePlace = new JButton("Create Red and Blue");
        JButton buttonJoin = new JButton("Join");
        JButton buttonJoinInside = new JButton("Join Inside");
        JButton buttonCountAliveThreads = new JButton("Count Alive");

        buttonAddPocket.addActionListener(createAddPocketAction());
        buttonStop.addActionListener(createStopAction());
        buttonCreate.addActionListener(createBallsAction());
        buttonCreateInOnePlace.addActionListener(createRedBlueBallsAction());
        buttonJoin.addActionListener(createJoinAction());
        buttonJoinInside.addActionListener(createJoinInsideBallThreadAction());
        buttonCountAliveThreads.addActionListener(createCountAliveThreadsAction());

        buttonPanel.add(buttonAddPocket);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonCreate);
        buttonPanel.add(buttonCreateInOnePlace);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonJoinInside);
        buttonPanel.add(buttonCountAliveThreads);

        return buttonPanel;
    }
    protected JPanel createInputOutputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel grayBallsPanel = new JPanel();
        JLabel labelCreateBallsNumber = new JLabel("Enter balls number to create:");
        textFieldCreateBallsNumber = new JTextField("1", 10);

        JLabel labelBallsInPocket = new JLabel("Balls in pocket:");
        textFieldBallsInPocket = new JTextField("0", 5);
        textFieldBallsInPocket.setEditable(false);

        grayBallsPanel.add(labelCreateBallsNumber);
        grayBallsPanel.add(textFieldCreateBallsNumber);
        grayBallsPanel.add(labelBallsInPocket);
        grayBallsPanel.add(textFieldBallsInPocket);


        JPanel redBlueBallsPanel = new JPanel();

        JLabel labelCreateMaxPriorityBallsNumber = new JLabel("Red balls (max prior):");
        textFieldCreateMaxPriorityBallsNumber = new JTextField("1", 10);
        JLabel labelCreateMinPriorityBallsNumber = new JLabel("Blue balls (min prior):");
        textFieldCreateMinPriorityBallsNumber = new JTextField("1", 10);
        createRedBlueBallRadioButtons();

        redBlueBallsPanel.add(labelCreateMaxPriorityBallsNumber);
        redBlueBallsPanel.add(textFieldCreateMaxPriorityBallsNumber);
        redBlueBallsPanel.add(labelCreateMinPriorityBallsNumber);
        redBlueBallsPanel.add(textFieldCreateMinPriorityBallsNumber);

        redBlueBallsPanel.add(radioButtonBlueFirst);
        redBlueBallsPanel.add(radioButtonRedFirst);

        panel.add(grayBallsPanel);
        panel.add(redBlueBallsPanel);
        return panel;
    }

    private void createRedBlueBallRadioButtons() {
        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(radioButtonBlueFirst);
        buttonGroup.add(radioButtonRedFirst);

        radioButtonBlueFirst.setSelected(true);
    }

    protected ActionListener createStopAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

    protected ActionListener createAddPocketAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pocket pocket = new Pocket(canvas);
                canvas.add(pocket);
                canvas.repaint();
            }
        };
    }
    protected ActionListener createBallsAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int ballCreateNumber = Integer.parseInt(textFieldCreateBallsNumber.getText());
                    for (int i = 0; i < ballCreateNumber; i++) {
                        Ball b = new Ball(canvas);
                        canvas.add(b);
                        BallThread thread = new BallThread(b);
                        ballThreads.add(thread);
                        thread.start();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(canvas, "Invalid input. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    protected ActionListener createRedBlueBallsAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maxPriorityBallsNumber = Integer.parseInt(textFieldCreateMaxPriorityBallsNumber.getText());
                    int minPriorityBallsNumber = Integer.parseInt(textFieldCreateMinPriorityBallsNumber.getText());

                    Color maxPriorityColor = Color.red;
                    Color minPriorityColor = Color.blue;

                    int xStartCoord = 0;
                    int yStartCoord = 5;

                    var redBlueBallThreads = new ArrayList<BallThread>();

                    for (int i = 0; i < minPriorityBallsNumber; i++) {
                        var thread = addBallToCanvas(xStartCoord, yStartCoord, minPriorityColor, Thread.MIN_PRIORITY);
                        redBlueBallThreads.add(thread);
                    }

                    for (int i = 0; i < maxPriorityBallsNumber; i++) {
                        var thread = addBallToCanvas(xStartCoord, yStartCoord, maxPriorityColor, Thread.MAX_PRIORITY);
                        redBlueBallThreads.add(thread);
                    }

                    if (radioButtonBlueFirst.isSelected()) {
                        for (BallThread redBlueBallThread : redBlueBallThreads) {
                            redBlueBallThread.start();
                        }
                    }
                    else {
                        for (int i = redBlueBallThreads.size()-1; i >= 0; i--) {
                            var thread = redBlueBallThreads.get(i);
                            thread.start();
                        }
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(canvas, "Invalid input. Please enter an integer.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private BallThread addBallToCanvas(int xStartCoord, int yStartCoord, Color priorityColor) {
        return addBallToCanvas(xStartCoord, yStartCoord, priorityColor, Thread.NORM_PRIORITY);
    }
    private BallThread addBallToCanvas(int xStartCoord, int yStartCoord, Color priorityColor, int priority) {
        Ball b = new Ball(canvas, xStartCoord, yStartCoord, priorityColor);
        canvas.add(b);
        var thread = new BallThread(b);
        thread.setPriority(priority);
        ballThreads.add(thread);
        return thread;
    }

    private BallThread addBallWithJoinToCanvas(int xStartCoord, int yStartCoord, Color priorityColor, Thread threadToWait) {
        Ball b = new Ball(canvas, xStartCoord, yStartCoord, priorityColor);
        canvas.add(b);
        var thread = new BallThreadWithJoin(b, threadToWait);
        ballThreads.add(thread);
        return thread;
    }

    protected ActionListener createJoinAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var redThread = addBallToCanvas(0, 0, Color.red);
                var greenThread = addBallToCanvas(0, 30, Color.green);
                var yellowThread = addBallToCanvas(0, 70, Color.yellow);

                var runBallsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Starting RED thread from " + Thread.currentThread().getName() + " thread");
                        redThread.start();
                        try {
                            redThread.join();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println("Starting GREEN thread from " + Thread.currentThread().getName() + " thread");
                        greenThread.start();
                        try {
                            greenThread.join();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

                        System.out.println("Starting YELLOW thread from " + Thread.currentThread().getName() + " thread");
                        yellowThread.start();
                    }
                });
                runBallsThread.start();
            }
        };
    }

    protected ActionListener createJoinInsideBallThreadAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var redThread = addBallToCanvas(0, 0, Color.red);
                var greenThread = addBallWithJoinToCanvas(0, 30, Color.green, redThread);
                var yellowThread = addBallWithJoinToCanvas(0, 70, Color.yellow, greenThread);

                System.out.println("Starting RED thread from " + Thread.currentThread().getName() + " thread");
                redThread.start();
                System.out.println("Starting GREEN thread from " + Thread.currentThread().getName() + " thread");
                greenThread.start();
                System.out.println("Starting YELLOW thread from " + Thread.currentThread().getName() + " thread");
                yellowThread.start();
            }
        };
    }

    protected ActionListener createCountAliveThreadsAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var aliveThreadNumber = 0;
                var terminatedThreadNumber = 0;

                for(BallThread thread : ballThreads) {
                    if (thread.isAlive())
                        aliveThreadNumber++;
                    else
                        terminatedThreadNumber++;
                }

                JOptionPane.showMessageDialog(canvas, "Alive threads: " + aliveThreadNumber +
                                ", Terminated threads: " + terminatedThreadNumber  , "Thread statistics",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
}