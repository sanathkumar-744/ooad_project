import javax.swing.*;
import java.awt.*;
import java.util.*;

class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}

class Player extends Person {
    private String role;
    private int matchesPlayed;
    private int runsScored;
    private int wicketsTaken;
    private int trainingSessions; // New field for training sessions

    public Player(String name, int age, String role) {
        super(name, age);
        this.role = role;
        this.matchesPlayed = 0;
        this.runsScored = 0;
        this.wicketsTaken = 0;
        this.trainingSessions = 0; // Initialize training sessions to 0
    }

    public String getRole() {
        return role;
    }

    public void playMatch(int runsScored, int wicketsTaken) {
        matchesPlayed++;
        this.runsScored += runsScored;
        this.wicketsTaken += wicketsTaken;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    // Methods for training sessions
    public void attendTrainingSession() {
        trainingSessions++;
    }

    public int getTrainingSessions() {
        return trainingSessions;
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: " + role + ", Matches Played: " + matchesPlayed +
                ", Runs Scored: " + runsScored + ", Wickets Taken: " + wicketsTaken +
                ", Training Sessions: " + trainingSessions;
    }
}

class Match {
    private String opponent;
    private Map<String, Integer> playerRunsMap;
    private Map<String, Integer> playerWicketsMap;

    public Match(String opponent) {
        this.opponent = opponent;
        playerRunsMap = new HashMap<>();
        playerWicketsMap = new HashMap<>();
    }

    public void addPlayerPerformance(Player player, int runsScored, int wicketsTaken) {
        playerRunsMap.put(player.getName(), runsScored);
        playerWicketsMap.put(player.getName(), wicketsTaken);
        player.playMatch(runsScored, wicketsTaken);
    }

    public String getOpponent() {
        return opponent;
    }

    public Map<String, Integer> getPlayerRunsMap() {
        return new HashMap<>(playerRunsMap);
    }

    public Map<String, Integer> getPlayerWicketsMap() {
        return new HashMap<>(playerWicketsMap);
    }
}

class Coach extends Person {
    private int experienceYears;

    public Coach(String name, int age, int experienceYears) {
        super(name, age);
        this.experienceYears = experienceYears;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    @Override
    public String toString() {
        return super.toString() + ", Experience: " + experienceYears + " years";
    }
}

class Club {
    private String name;
    private java.util.List<Player> players;
    private Coach coach;
    private java.util.List<Match> matches;

    public Club(String name, Coach coach) {
        this.name = name;
        this.coach = coach;
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public java.util.List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public void addCoach(Coach newCoach) {
        this.coach = newCoach;
    }

    public void removeCoach() {
        this.coach = null;
    }

    public Coach getCoach() {
        return coach;
    }

    public void scheduleMatch(String opponent) {
        matches.add(new Match(opponent));
    }

    public void recordMatchPerformance(Match match, Player player, int runsScored, int wicketsTaken) {
        match.addPlayerPerformance(player, runsScored, wicketsTaken);
    }

    public void displaySquad() {
        System.out.println("Club: " + name);
        System.out.println("Coach: " + coach);
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println(player);
        }
    }

    public void displayMatches() {
        System.out.println("Matches Scheduled:");
        for (Match match : matches) {
            System.out.println("Opponent: " + match.getOpponent());
        }
    }

    public java.util.List<Match> getMatches() {
        return new ArrayList<>(matches);
    }

    public int calculateRunsAgainstOpponent(String opponent) {
        int totalRuns = 0;
        for (Match match : matches) {
            if (match.getOpponent().equals(opponent)) {
                for (Integer runs : match.getPlayerRunsMap().values()) {
                    totalRuns += runs;
                }
            }
        }
        return totalRuns;
    }
}

public class Main {
    private static Club club;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Cricket Club Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set initial window size

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("cricket.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Welcome to the Cricket Club Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 50, 0));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        JButton addButton = createStyledButton("Add Player");
        addButton.addActionListener(e -> addPlayerDialog(frame));
        panel.add(addButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton removeButton = createStyledButton("Remove Player");
        removeButton.addActionListener(e -> removePlayerDialog(frame));
        panel.add(removeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton addCoachButton = createStyledButton("Add Coach");
        addCoachButton.addActionListener(e -> addCoachDialog(frame));
        panel.add(addCoachButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton removeCoachButton = createStyledButton("Remove Coach");
        removeCoachButton.addActionListener(e -> removeCoachDialog(frame));
        panel.add(removeCoachButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton displayButton = createStyledButton("Display Squad");
        displayButton.addActionListener(e -> displaySquadDialog(frame));
        panel.add(displayButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton matchButton = createStyledButton("Schedule Match");
        matchButton.addActionListener(e -> scheduleMatchDialog(frame));
        panel.add(matchButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton calculateRunsButton = createStyledButton("Calculate Runs Against Opponent");
        calculateRunsButton.addActionListener(e -> calculateRunsDialog(frame));
        panel.add(calculateRunsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton recordPerformanceButton = createStyledButton("Record Player Performance");
        recordPerformanceButton.addActionListener(e -> recordPerformanceDialog(frame));
        panel.add(recordPerformanceButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton displayPlayerPerformancesButton = createStyledButton("Display Player Performances");
        displayPlayerPerformancesButton.addActionListener(e -> displayPlayerPerformancesDialog(frame));
        panel.add(displayPlayerPerformancesButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton recordTrainingSessionButton = createStyledButton("Record Training Session");
        recordTrainingSessionButton.addActionListener(e -> recordTrainingSessionDialog(frame));
        panel.add(recordTrainingSessionButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton displayTrainingSessionsButton = createStyledButton("Display Training Sessions");
        displayTrainingSessionsButton.addActionListener(e -> displayTrainingSessionsDialog(frame));
        panel.add(displayTrainingSessionsButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Initialize the club
        club = new Club("Cricket Club", null);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(300, 50)); // Set fixed button size
        button.setBackground(new Color(65, 105, 225));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private static void addPlayerDialog(JFrame parentFrame) {
        JTextField nameField = new JTextField(10);
        JTextField ageField = new JTextField(5);
        JTextField roleField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Role:"));
        panel.add(roleField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Add Player", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String role = roleField.getText().trim();
                Player player = new Player(name, age, role);
                club.addPlayer(player);
                JOptionPane.showMessageDialog(parentFrame, "Player added successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid input. Please enter valid data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void removePlayerDialog(JFrame parentFrame) {
        if (club.getPlayers().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No players to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Player> playerComboBox = new JComboBox<>(club.getPlayers().toArray(new Player[0]));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select Player:"));
        panel.add(playerComboBox);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Remove Player", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
            club.removePlayer(selectedPlayer);
            JOptionPane.showMessageDialog(parentFrame, "Player removed successfully.");
        }
    }

    private static void displaySquadDialog(JFrame parentFrame) {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder squadInfo = new StringBuilder("Club: " + club.getName() + "\n");
        squadInfo.append("Players:\n");
        for (Player player : club.getPlayers()) {
            squadInfo.append(player.toString()).append("\n");
        }
        textArea.setText(squadInfo.toString());

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Club Squad", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void scheduleMatchDialog(JFrame parentFrame) {
        JTextField opponentField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Opponent:"));
        panel.add(opponentField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Schedule Match", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String opponent = opponentField.getText().trim();
            club.scheduleMatch(opponent);
            JOptionPane.showMessageDialog(parentFrame, "Match against " + opponent + " scheduled successfully.");
        }
    }

    private static void addCoachDialog(JFrame parentFrame) {
        JTextField nameField = new JTextField(10);
        JTextField ageField = new JTextField(5);
        JTextField experienceField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Experience (years):"));
        panel.add(experienceField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Add Coach", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                int experience = Integer.parseInt(experienceField.getText().trim());
                Coach coach = new Coach(name, age, experience);
                club.addCoach(coach);
                JOptionPane.showMessageDialog(parentFrame, "Coach added successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid input. Please enter valid data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void removeCoachDialog(JFrame parentFrame) {
        if (club.getCoach() == null) {
            JOptionPane.showMessageDialog(parentFrame, "No coach to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Coach> coachComboBox = new JComboBox<>();
        coachComboBox.addItem(club.getCoach());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select Coach to Remove:"));
        panel.add(coachComboBox);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Remove Coach", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Coach selectedCoach = (Coach) coachComboBox.getSelectedItem();
            int confirmResult = JOptionPane.showConfirmDialog(parentFrame, "Remove Coach: " + selectedCoach.getName() + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                club.removeCoach();
                JOptionPane.showMessageDialog(parentFrame, "Coach removed successfully.");
            }
        }
    }

    private static void calculateRunsDialog(JFrame parentFrame) {
        JTextField opponentField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Opponent:"));
        panel.add(opponentField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Calculate Runs Against Opponent",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String opponent = opponentField.getText().trim();
            int totalRuns = club.calculateRunsAgainstOpponent(opponent);
            JOptionPane.showMessageDialog(parentFrame, "Total runs scored against " + opponent + ": " + totalRuns);
        }
    }

    private static Match selectedMatch;

    private static void recordPerformanceDialog(JFrame parentFrame) {
        if (club.getPlayers().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No players in the squad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Player> playerComboBox = new JComboBox<>(club.getPlayers().toArray(new Player[0]));
        JTextField runsField = new JTextField(5);
        JTextField wicketsField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select Player:"));
        panel.add(playerComboBox);
        panel.add(new JLabel("Runs Scored:"));
        panel.add(runsField);
        panel.add(new JLabel("Wickets Taken:"));
        panel.add(wicketsField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Record Player Performance",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
                int runs = Integer.parseInt(runsField.getText().trim());
                int wickets = Integer.parseInt(wicketsField.getText().trim());
                // Set the selected match
                selectedMatch = club.getMatches().get(club.getMatches().size() - 1);
                club.recordMatchPerformance(selectedMatch, selectedPlayer, runs, wickets);
                JOptionPane.showMessageDialog(parentFrame, "Performance recorded successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid input. Please enter valid data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void displayPlayerPerformancesDialog(JFrame parentFrame) {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder performanceInfo = new StringBuilder("Player Performances:\n");
        for (Match match : club.getMatches()) {
            performanceInfo.append("Opponent: ").append(match.getOpponent()).append("\n");
            performanceInfo.append("Player Performances:\n");
            for (Map.Entry<String, Integer> entry : match.getPlayerRunsMap().entrySet()) {
                String playerName = entry.getKey();
                int runs = entry.getValue();
                int wickets = match.getPlayerWicketsMap().get(playerName);
                performanceInfo.append("Player: ").append(playerName).append(", Runs: ").append(runs)
                        .append(", Wickets: ").append(wickets).append("\n");
            }
            performanceInfo.append("\n");
        }

        textArea.setText(performanceInfo.toString());

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Player Performances", JOptionPane.INFORMATION_MESSAGE);
    }

    // New method for recording training sessions
    private static void recordTrainingSessionDialog(JFrame parentFrame) {
        if (club.getPlayers().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No players in the squad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Player> playerComboBox = new JComboBox<>(club.getPlayers().toArray(new Player[0]));
        JTextField hoursField = new JTextField(5);
        JTextField battingField = new JTextField(5);
        JTextField bowlingField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select Player:"));
        panel.add(playerComboBox);
        panel.add(new JLabel("Number of Hours:"));
        panel.add(hoursField);
        panel.add(new JLabel("Batting Practice (hours):"));
        panel.add(battingField);
        panel.add(new JLabel("Bowling Practice (hours):"));
        panel.add(bowlingField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Record Training Session",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
                int hours = Integer.parseInt(hoursField.getText().trim());
                int battingHours = Integer.parseInt(battingField.getText().trim());
                int bowlingHours = Integer.parseInt(bowlingField.getText().trim());
                selectedPlayer.attendTrainingSession(); // Increment training session count
                JOptionPane.showMessageDialog(parentFrame, "Training session recorded successfully for " + selectedPlayer.getName() + ".");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid input. Please enter valid data.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // New method for displaying training sessions of individual players
    private static void displayTrainingSessionsDialog(JFrame parentFrame) {
        if (club.getPlayers().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No players in the squad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Player> playerComboBox = new JComboBox<>(club.getPlayers().toArray(new Player[0]));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select Player:"));
        panel.add(playerComboBox);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Display Training Sessions",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(parentFrame, selectedPlayer.getName() + " has attended " + selectedPlayer.getTrainingSessions() + " training session(s).");
        }
    }
}

