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

    private static final int ROWS_PER_PAGE = 5; // Number of rows per page
    private int currentPage = 1;
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
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add MouseListener to detect double-clicks on a row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {  // Double-click
                    int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                    Object[] rowData = allData.get(selectedRow);
                    int sopirId = (int) rowData[0];  // Get Sopir ID
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(DataSopirPanel.this);
                    if (parentFrame != null) {
                        new EditSopirDialog(parentFrame, sopirId, rowData, DataSopirPanel.this).setVisible(true);
                    } else {
                        System.err.println("Parent frame is null.");
                    }
                }
            }
        });

        fetchAndDisplayData();

        // Add pagination panel
        JPanel paginationPanel = createPaginationPanel();
        add(paginationPanel, BorderLayout.SOUTH);
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

            displayPage(1); // Display the first page
            System.out.println("Data sopir berhasil dimuat, jumlah data: " + allData.size());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil data sopir dari database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
    }

    public boolean hasData() {
        return allData != null && !allData.isEmpty();
    }

    // Method to refresh data after an edit
    public void refreshData() {
        fetchAndDisplayData();
    }
}
