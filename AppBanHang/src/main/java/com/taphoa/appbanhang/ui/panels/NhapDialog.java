package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.entity.Nhap;
import com.taphoa.appbanhang.service.HanghoaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class NhapDialog extends JDialog {
    private HanghoaService hanghoaService;
    private JComboBox<Hanghoa> hanghoaComboBox;
    private JTextField soLuongField;
    private JTextField giaNhapField;
    private JTextField thanhTienField;
    private JTextArea ghiChuArea;
    private boolean confirmed = false;
    private Nhap nhap;

    public NhapDialog(Frame parent, String title, HanghoaService hanghoaService) {
        this(parent, title, hanghoaService, null);
    }

    public NhapDialog(Frame parent, String title, HanghoaService hanghoaService, Nhap nhap) {
        super(parent, title, true);
        this.hanghoaService = hanghoaService;
        this.nhap = nhap;
        initializeUI();
        loadData();
        if (nhap != null) {
            populateFields();
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Hàng hóa
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Hàng hóa:"), gbc);
        hanghoaComboBox = new JComboBox<>();
        hanghoaComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Hanghoa) {
                    Hanghoa hh = (Hanghoa) value;
                    setText(hh.getMaHang() + " - " + hh.getTenHang());
                }
                return this;
            }
        });
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(hanghoaComboBox, gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Số lượng:"), gbc);
        soLuongField = new JTextField();
        soLuongField.addActionListener(e -> calculateThanhTien());
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(soLuongField, gbc);

        // Giá nhập
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Giá nhập:"), gbc);
        giaNhapField = new JTextField();
        giaNhapField.addActionListener(e -> calculateThanhTien());
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(giaNhapField, gbc);

        // Thành tiền
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Thành tiền:"), gbc);
        thanhTienField = new JTextField();
        thanhTienField.setEditable(false);
        thanhTienField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(thanhTienField, gbc);

        // Ghi chú
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Ghi chú:"), gbc);
        ghiChuArea = new JTextArea(3, 20);
        ghiChuArea.setLineWrap(true);
        ghiChuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(ghiChuArea);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("✅ Lưu");
        JButton cancelButton = new JButton("❌ Hủy");

        okButton.addActionListener(this::onOk);
        cancelButton.addActionListener(this::onCancel);

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners for auto-calculation
        soLuongField.addCaretListener(e -> calculateThanhTien());
        giaNhapField.addCaretListener(e -> calculateThanhTien());
    }

    private void loadData() {
        try {
            List<Hanghoa> hanghoaList = hanghoaService.findAll();
            hanghoaComboBox.removeAllItems();
            for (Hanghoa hh : hanghoaList) {
                hanghoaComboBox.addItem(hh);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách hàng hóa: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateFields() {
        if (nhap != null) {
            // Chọn hàng hóa
            for (int i = 0; i < hanghoaComboBox.getItemCount(); i++) {
                Hanghoa hh = hanghoaComboBox.getItemAt(i);
                if (hh.getId().equals(nhap.getHanghoa().getId())) {
                    hanghoaComboBox.setSelectedIndex(i);
                    break;
                }
            }
            
            soLuongField.setText(String.valueOf(nhap.getSoLuong()));
            giaNhapField.setText(String.valueOf(nhap.getGiaNhap()));
            thanhTienField.setText(String.valueOf(nhap.getThanhTien()));
            ghiChuArea.setText(nhap.getGhiChu() != null ? nhap.getGhiChu() : "");
        }
    }

    private void calculateThanhTien() {
        try {
            String soLuongText = soLuongField.getText().trim();
            String giaNhapText = giaNhapField.getText().trim();
            
            if (!soLuongText.isEmpty() && !giaNhapText.isEmpty()) {
                int soLuong = Integer.parseInt(soLuongText);
                double giaNhap = Double.parseDouble(giaNhapText);
                double thanhTien = soLuong * giaNhap;
                thanhTienField.setText(String.format("%.0f", thanhTien));
            }
        } catch (NumberFormatException e) {
            thanhTienField.setText("");
        }
    }

    private void onOk(ActionEvent e) {
        if (validateInput()) {
            confirmed = true;
            setVisible(false);
        }
    }

    private void onCancel(ActionEvent e) {
        confirmed = false;
        setVisible(false);
    }

    private boolean validateInput() {
        // Kiểm tra hàng hóa
        if (hanghoaComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng hóa!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra số lượng
        try {
            int soLuong = Integer.parseInt(soLuongField.getText().trim());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra giá nhập
        try {
            double giaNhap = Double.parseDouble(giaNhapField.getText().trim());
            if (giaNhap <= 0) {
                JOptionPane.showMessageDialog(this, "Giá nhập phải lớn hơn 0!", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá nhập không hợp lệ!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Nhap getNhap() {
        if (!confirmed) return null;

        Nhap result = new Nhap();
        result.setHanghoa((Hanghoa) hanghoaComboBox.getSelectedItem());
        result.setSoLuong(Integer.parseInt(soLuongField.getText().trim()));
        result.setGiaNhap(Double.parseDouble(giaNhapField.getText().trim()));
        result.setThanhTien(Double.parseDouble(thanhTienField.getText().trim()));
        result.setNgayNhap(java.time.LocalDate.now());
        result.setGhiChu(ghiChuArea.getText().trim());

        return result;
    }
}
