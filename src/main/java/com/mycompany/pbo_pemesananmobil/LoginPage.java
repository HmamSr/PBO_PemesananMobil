package com.mycompany.pbo_pemesananmobil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JDialog {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton button1;

    public LoginPage(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(panel1);
        setSize(new Dimension(450, 475));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        button1.addActionListener(e -> {
            String username = textField1.getText();
            String password = new String(passwordField1.getPassword());
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = textField1.getText();
                String password = String.valueOf(passwordField1.getPassword());

                Admin admin = getAuthenticatedUser(userName, password);
                if (admin != null) {
                    JOptionPane.showMessageDialog(LoginPage.this, "Login berhasil! Selamat datang, " + admin.getNama());
                    dispose();  // Menutup dialog setelah login berhasil
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Login gagal! Username atau password salah.");
                }
            }
        });

        setVisible(true);
    }

    // Mendefinisikan kelas Admin untuk menyimpan informasi dari tabel admin
    public class Admin {
        private int id;
        private String username;
        private String nama;
        private String email;
        private String level;

        public Admin(int id, String username, String nama, String email, String level) {
            this.id = id;
            this.username = username;
            this.nama = nama;
            this.email = email;
            this.level = level;
        }

        public String getNama() {
            return nama;
        }

        // Anda dapat menambahkan getter lain jika diperlukan
    }

    // Metode untuk autentikasi user dari database MySQL
    private Admin getAuthenticatedUser(String username, String password) {
        Admin admin = null;

        // Ubah URL, username, dan password sesuai dengan database Anda
        String url = "jdbc:mysql://localhost:3306/sewa_mobil";
        String dbUsername = "root";  // Sesuaikan dengan database Anda
        String dbPassword = "";      // Sesuaikan dengan password database Anda

        // SQL untuk mengambil pengguna berdasarkan username dan password
        String sql = "SELECT id, username, nama, email, level FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set parameter untuk query
            statement.setString(1, username);
            statement.setString(2, password);

            // Eksekusi query
            ResultSet resultSet = statement.executeQuery();

            // Jika data ditemukan, buat objek Admin
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String uname = resultSet.getString("username");
                String nama = resultSet.getString("nama");
                String email = resultSet.getString("email");
                String level = resultSet.getString("level");

                // Buat objek Admin dengan data dari database
                admin = new Admin(id, uname, nama, email, level);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database.");
        }

        return admin;
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage(null);
    }
}
