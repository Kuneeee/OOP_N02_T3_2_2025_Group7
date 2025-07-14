package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.entity.XuatBan;
import com.taphoa.appbanhang.service.HanghoaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class XuatBanDialog extends JDialog {
    private HanghoaService hanghoaService;
    private JComboBox<Hanghoa> hanghoaComboBox;
    private JTextField soLuongField;
    private JTextField giaBanField;
    private JTextField thanhTienField;
    private JTextField khachHangField;
    private JTextArea ghiChuArea;
    private JLabel tonKhoLabel;
    private boolean confirmed = false;
    private XuatBan xuatBan;

    public XuatBanDialog(Frame parent, String title, HanghoaService hanghoaService) {
        this(parent, title, hanghoaService, null);
    }

    public XuatBanDialog(Frame parent, String title, HanghoaService hanghoaService, XuatBan xuatBan) {
        super(parent, title, true);
        this.hanghoaService = hanghoaService;
        this.xuatBan = xuatBan;
        initializeUI();
        loadData();
        if (xuatBan != null) {
            populateFields();
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setSize(500, 450);
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
        hanghoaComboBox.addActionListener(e -> updateTonKho());
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(hanghoaComboBox, gbc);

        // Tồn kho
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Tồn kho:"), gbc);
        tonKhoLabel = new JLabel("0");
        tonKhoLabel.setForeground(Color.BLUE);
        tonKhoLabel.setFont(tonKhoLabel.getFont().deriveFont(Font.BOLD));
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(tonKhoLabel, gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Số lượng:"), gbc);
        soLuongField = new JTextField();
        soLuongField.addActionListener(e -> calculateThanhTien());
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(soLuongField, gbc);

        // Giá bán
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Giá bán:"), gbc);
        giaBanField = new JTextField();
        giaBanField.addActionListener(e -> calculateThanhTien());
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(giaBanField, gbc);

        // Thành tiền
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Thành tiền:"), gbc);
        thanhTienField = new JTextField();
        thanhTienField.setEditable(false);
        thanhTienField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(thanhTienField, gbc);

        // Khách hàng
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Khách hàng:"), gbc);
        khachHangField = new JTextField();
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(khachHangField, gbc);

        // Ghi chú
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
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
        giaBanField.addCaretListener(e -> calculateThanhTien());
    }

    private void loadData() {
        try {
            List<Hanghoa> hanghoaList = hanghoaService.findAll();
            hanghoaComboBox.removeAllItems();
            for (Hanghoa hh : hanghoaList) {
                hanghoaComboBox.addItem(hh);
            }
            updateTonKho();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách hàng hóa: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTonKho() {
        Hanghoa selectedHanghoa = (Hanghoa) hanghoaComboBox.getSelectedItem();
        if (selectedHanghoa != null) {
            tonKhoLabel.setText(String.valueOf(selectedHanghoa.getSoLuongTon()));
            giaBanField.setText(String.valueOf(selectedHanghoa.getGiaBan()));
            calculateThanhTien();
        } else {
            tonKhoLabel.setText("0");
        }
    }

    private void populateFields() {
        if (xuatBan != null) {
            // Chọn hàng hóa
            for (int i = 0; i < hanghoaComboBox.getItemCount(); i++) {
                Hanghoa hh = hanghoaComboBox.getItemAt(i);
                if (hh.getId().equals(xuatBan.getHanghoa().getId())) {
                    hanghoaComboBox.setSelectedIndex(i);
                    break;
                }
            }
            
            soLuongField.setText(String.valueOf(xuatBan.getSoLuong()));
            giaBanField.setText(String.valueOf(xuatBan.getGiaBan()));
            thanhTienField.setText(String.valueOf(xuatBan.getThanhTien()));
            khachHangField.setText(xuatBan.getKhachHang() != null ? xuatBan.getKhachHang() : "");
            ghiChuArea.setText(xuatBan.getGhiChu() != null ? xuatBan.getGhiChu() : "");
        }
    }

    private void calculateThanhTien() {
        try {
            String soLuongText = soLuongField.getText().trim();
            String giaBanText = giaBanField.getText().trim();
            
            if (!soLuongText.isEmpty() && !giaBanText.isEmpty()) {
                int soLuong = Integer.parseInt(soLuongText);
                double giaBan = Double.parseDouble(giaBanText);
                double thanhTien = soLuong * giaBan;
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

            // Kiểm tra tồn kho
            Hanghoa hanghoa = (Hanghoa) hanghoaComboBox.getSelectedItem();
            int tonKho = hanghoa.getSoLuongTon();
            
            // Nếu đang sửa, cộng thêm số lượng cũ
            if (xuatBan != null) {
                tonKho += xuatBan.getSoLuong();
            }
            
            if (soLuong > tonKho) {
                JOptionPane.showMessageDialog(this, 
                    "Số lượng xuất (" + soLuong + ") vượt quá tồn kho (" + tonKho + ")!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra giá bán
        try {
            double giaBan = Double.parseDouble(giaBanField.getText().trim());
            if (giaBan <= 0) {
                JOptionPane.showMessageDialog(this, "Giá bán phải lớn hơn 0!", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá bán không hợp lệ!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public XuatBan getXuatBan() {
        if (!confirmed) return null;

        XuatBan result = new XuatBan();
        result.setHanghoa((Hanghoa) hanghoaComboBox.getSelectedItem());
        result.setSoLuong(Integer.parseInt(soLuongField.getText().trim()));
        result.setGiaBan(Double.parseDouble(giaBanField.getText().trim()));
        result.setThanhTien(Double.parseDouble(thanhTienField.getText().trim()));
        result.setNgayBan(java.time.LocalDate.now());
        result.setKhachHang(khachHangField.getText().trim());
        result.setGhiChu(ghiChuArea.getText().trim());

        return result;
    }
}
