package com.mycompany.pbo_pemesananmobil;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataSopirPanel extends JPanel {

<<<<<<< HEAD
    private static final int ROWS_PER_PAGE = 5; // Number of rows per page
    private int currentPage = 1;
=======
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
    private List<Object[]> allData;
    private DefaultTableModel model;
    private JTable table;
    private DatabaseManager dbManager;

    public DataSopirPanel() {
        setLayout(new BorderLayout());
        dbManager = DatabaseManager.getInstance();
        model = new DefaultTableModel(new String[]{"ID", "Nama Sopir", "Email", "Nomer Telepon", "Alamat", "Status Sopir", "Harga Sewa per Hari", "Created At"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Nonaktifkan edit langsung di tabel
            }
        };
        table = new JTable(model);
<<<<<<< HEAD
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add MouseListener to detect double-clicks on a row
=======

        fetchAndDisplayData();

        // Tambahkan MouseListener untuk mendeteksi klik dua kali pada baris
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {  // Double-click
                    int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                    Object[] rowData = allData.get(selectedRow);
<<<<<<< HEAD
                    int sopirId = (int) rowData[0];  // Get Sopir ID
=======
                    int sopirId = (int) rowData[0];  // Ambil ID sopir
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(DataSopirPanel.this);
                    if (parentFrame != null) {
                        new EditSopirDialog(parentFrame, sopirId, rowData, DataSopirPanel.this).setVisible(true);
                    } else {
                        System.err.println("Parent frame is null.");
                    }
                }
            }
        });

<<<<<<< HEAD
        fetchAndDisplayData();

        // Add pagination panel
        JPanel paginationPanel = createPaginationPanel();
        add(paginationPanel, BorderLayout.SOUTH);
=======
        add(new JScrollPane(table), BorderLayout.CENTER);
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
    }

    public void fetchAndDisplayData() {
        allData = new ArrayList<>();
        try {
            ResultSet rs = dbManager.fetchSopirData();
            while (rs.next()) {
                allData.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama_sopir"),
                        rs.getString("email"),
                        rs.getString("nomer_telepon"),
                        rs.getString("alamat"),
                        rs.getString("status_sopir"),
                        rs.getDouble("harga_sewa_per_hari"),
                        rs.getTimestamp("created_at")
                });
            }

<<<<<<< HEAD
            displayPage(1); // Display the first page
=======
            updateTableData();
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
            System.out.println("Data sopir berhasil dimuat, jumlah data: " + allData.size());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil data sopir dari database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

<<<<<<< HEAD
    private void displayPage(int pageNumber) {
        model.setRowCount(0); // Clear previous data from the table model
        int start = (pageNumber - 1) * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, allData.size());

        for (int i = start; i < end; i++) {
            model.addRow(allData.get(i));
        }

        currentPage = pageNumber;
    }

    private JPanel createPaginationPanel() {
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnPrevious = new JButton("Previous");
        JButton btnNext = new JButton("Next");

        btnPrevious.addActionListener(e -> {
            if (currentPage > 1) {
                displayPage(currentPage - 1);
            }
        });

        btnNext.addActionListener(e -> {
            if (currentPage * ROWS_PER_PAGE < allData.size()) {
                displayPage(currentPage + 1);
            }
        });

        paginationPanel.add(btnPrevious);
        paginationPanel.add(btnNext);

        return paginationPanel;
=======
    private void updateTableData() {
        model.setRowCount(0);
        for (Object[] rowData : allData) {
            model.addRow(rowData);
        }
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
    }

    public boolean hasData() {
        return allData != null && !allData.isEmpty();
    }

<<<<<<< HEAD
    // Method to refresh data after an edit
    public void refreshData() {
        fetchAndDisplayData();
    }
}
=======
    // Tambahkan metode refreshData untuk memperbarui tabel setelah edit
    public void refreshData() {
        fetchAndDisplayData();
    }
}
>>>>>>> 5f76b55a9c07f9cd97b816e5d3219c4f07405bcf
