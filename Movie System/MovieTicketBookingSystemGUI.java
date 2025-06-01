import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MovieTicketBookingSystemGUI {

    public static class Theatre {
        private int totalSeats;
        private boolean[] seats;

        public Theatre(int totalSeats) {
            this.totalSeats = totalSeats;
            this.seats = new boolean[totalSeats];
        }

        public boolean isSeatAvailable(int seatNumber) {
            return !seats[seatNumber];
        }

        public void bookSeat(int seatNumber) {
            seats[seatNumber] = true;
        }

        public String getBookedSeats() {
            StringBuilder bookedSeats = new StringBuilder();
            for (int i = 0; i < seats.length; i++) {
                if (seats[i]) {
                    bookedSeats.append(i).append(" ");
                }
            }
            return bookedSeats.toString().trim();
        }
    }

    public static void main(String[] args) {
        // Initialize data
        String[] movieTitles = {"AMARAN", "LEO", "VADACHENNAI", "MANMADHAN"};
        Map<String, Theatre> movieTheatres = new HashMap<>();
        for (String title : movieTitles) {
            movieTheatres.put(title, new Theatre(100));
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Movie Ticket Booking System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLayout(new BorderLayout());

            // Header
            JLabel header = new JLabel("Movie Ticket Booking System", JLabel.CENTER);
            header.setFont(new Font("Arial", Font.BOLD, 20));
            header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            frame.add(header, BorderLayout.NORTH);

            // Main panel
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            Dimension fieldSize = new Dimension(300, 30);

            // Email input
            JLabel emailLabel = new JLabel("Enter your Gmail:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            mainPanel.add(emailLabel, gbc);

            JTextField emailField = new JTextField();
            emailField.setPreferredSize(fieldSize);
            gbc.gridx = 1;
            mainPanel.add(emailField, gbc);

            // Movie selection
            JLabel movieLabel = new JLabel("Select a Movie:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            mainPanel.add(movieLabel, gbc);

            JComboBox<String> movieDropdown = new JComboBox<>(movieTitles);
            movieDropdown.setPreferredSize(fieldSize);
            gbc.gridx = 1;
            mainPanel.add(movieDropdown, gbc);

            // Number of seats input
            JLabel numSeatsLabel = new JLabel("Enter number of seats (1-20):");
            gbc.gridx = 0;
            gbc.gridy = 2;
            mainPanel.add(numSeatsLabel, gbc);

            JTextField numSeatsField = new JTextField();
            numSeatsField.setPreferredSize(fieldSize);
            gbc.gridx = 1;
            mainPanel.add(numSeatsField, gbc);

            // Seat selection
            JLabel seatsLabel = new JLabel("Enter seat numbers (space-separated):");
            gbc.gridx = 0;
            gbc.gridy = 3;
            mainPanel.add(seatsLabel, gbc);

            JTextField seatsField = new JTextField();
            seatsField.setPreferredSize(fieldSize);
            gbc.gridx = 1;
            mainPanel.add(seatsField, gbc);

            // Payment method
            JLabel paymentLabel = new JLabel("Select Payment Method:");
            gbc.gridx = 0;
            gbc.gridy = 4;
            mainPanel.add(paymentLabel, gbc);

            JComboBox<String> paymentDropdown = new JComboBox<>(new String[]{"Cash", "UPI", "Banking"});
            paymentDropdown.setPreferredSize(fieldSize);
            gbc.gridx = 1;
            mainPanel.add(paymentDropdown, gbc);

            // Book button
            JButton bookButton = new JButton("Book Tickets");
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            mainPanel.add(bookButton, gbc);

            // Exit button
            JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(80, 25)); // Reduced size
            gbc.gridy = 6;
            gbc.gridwidth = 2; // Center-align by spanning columns
            mainPanel.add(exitButton, gbc);

            // Booked seats label below Exit button
            JLabel bookedSeatsLabel = new JLabel("Booked Seats: None", JLabel.CENTER);
            gbc.gridy = 7;
            mainPanel.add(bookedSeatsLabel, gbc);

            frame.add(mainPanel, BorderLayout.CENTER);

            // Footer for Ruby Cinemas branding
            JPanel footerPanel = new JPanel();
            JLabel footerLabel = new JLabel("Ruby Cinemas", JLabel.CENTER);
            footerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            footerPanel.add(footerLabel);
            frame.add(footerPanel, BorderLayout.SOUTH);

            frame.setVisible(true);

            // Action listeners
            movieDropdown.addActionListener(e -> {
                String selectedMovie = (String) movieDropdown.getSelectedItem();
                Theatre theatre = movieTheatres.get(selectedMovie);
                bookedSeatsLabel.setText("Booked Seats: " + theatre.getBookedSeats());
            });

            bookButton.addActionListener(e -> {
                String email = emailField.getText();
                String selectedMovie = (String) movieDropdown.getSelectedItem();
                Theatre theatre = movieTheatres.get(selectedMovie);
                String numSeatsInput = numSeatsField.getText();
                String seatsInput = seatsField.getText();
                String paymentMethod = (String) paymentDropdown.getSelectedItem();

                try {
                    if (!email.endsWith("@gmail.com")) {
                        throw new IllegalArgumentException("Please enter a valid Gmail address ending with @gmail.com.");
                    }

                    int numSeats = Integer.parseInt(numSeatsInput);
                    if (numSeats < 1 || numSeats > 20) {
                        throw new IllegalArgumentException("You can only book between 1 and 20 seats.");
                    }

                    String[] seatNumbersInput = seatsInput.split("\\s+");
                    if (seatNumbersInput.length != numSeats) {
                        throw new IllegalArgumentException("Please enter exactly " + numSeats + " seat numbers.");
                    }

                    int[] seatNumbers = new int[seatNumbersInput.length];
                    for (int i = 0; i < seatNumbersInput.length; i++) {
                        seatNumbers[i] = Integer.parseInt(seatNumbersInput[i].trim());
                        if (seatNumbers[i] < 0 || seatNumbers[i] >= theatre.totalSeats || !theatre.isSeatAvailable(seatNumbers[i])) {
                            throw new IllegalArgumentException("Seat is not available.");
                        }
                    }

                    for (int seatNumber : seatNumbers) {
                        theatre.bookSeat(seatNumber);
                    }

                    double ticketPrice = 120.00;
                    double totalAmount = seatNumbers.length * ticketPrice;
                    JOptionPane.showMessageDialog(frame, "Payment of ₹" + totalAmount + " via " + paymentMethod + " successful!");
                    JOptionPane.showMessageDialog(frame, "Booking confirmed for " + email + " for movie: " + selectedMovie + " at seats: " + seatsInput);

                    bookedSeatsLabel.setText("Booked Seats: " + theatre.getBookedSeats());

                    emailField.setText("");
                    numSeatsField.setText("");
                    seatsField.setText("");

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            exitButton.addActionListener(e -> System.exit(0));
        });
    }
}
