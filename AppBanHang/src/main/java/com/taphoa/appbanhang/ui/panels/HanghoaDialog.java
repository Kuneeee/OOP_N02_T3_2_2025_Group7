package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Hanghoa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HanghoaDialog extends JDialog {

    private JTextField tenHangHoaField;
    private JSpinner soLuongSpinner;
    private JTextField nhaSanXuatField;
    private JSpinner namSanXuatSpinner;
    private JTextField loaiHangHoaField;
    
    private Hanghoa hanghoa;
    private boolean confirmed = false;

    public HanghoaDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initializeUI();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tên hàng hóa
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Tên hàng hóa:"), gbc);
        tenHangHoaField = new JTextField(20);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tenHangHoaField, gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Số lượng:"), gbc);
        soLuongSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(soLuongSpinner, gbc);

        // Nhà sản xuất
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Nhà sản xuất:"), gbc);
        nhaSanXuatField = new JTextField(20);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nhaSanXuatField, gbc);

        // Năm sản xuất
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Năm sản xuất:"), gbc);
        namSanXuatSpinner = new JSpinner(new SpinnerNumberModel(2024, 1900, 2100, 1));
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(namSanXuatSpinner, gbc);

        // Loại hàng hóa
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Loại hàng hóa:"), gbc);
        loaiHangHoaField = new JTextField(20);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loaiHangHoaField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    confirmed = true;
                    hanghoa = createHanghoaFromInput();
                    dispose();
                }
            }
        });
        buttonPanel.add(okButton);

        JButton cancelButton = new JButton("Hủy");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Set default button
        getRootPane().setDefaultButton(okButton);
    }

    private boolean validateInput() {
        // Kiểm tra tên hàng hóa
        if (tenHangHoaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hàng hóa!");
            tenHangHoaField.requestFocus();
            return false;
        }

        // Kiểm tra nhà sản xuất
        if (nhaSanXuatField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nhà sản xuất!");
            nhaSanXuatField.requestFocus();
            return false;
        }

        // Kiểm tra loại hàng hóa
        if (loaiHangHoaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập loại hàng hóa!");
            loaiHangHoaField.requestFocus();
            return false;
        }

        return true;
    }

    private Hanghoa createHanghoaFromInput() {
        Hanghoa h = new Hanghoa();
        h.setTenHangHoa(tenHangHoaField.getText().trim());
        h.setSoLuongHangHoa((Integer) soLuongSpinner.getValue());
        h.setNhaSanXuat(nhaSanXuatField.getText().trim());
        h.setNamSanXuat((Integer) namSanXuatSpinner.getValue());
        h.setLoaiHangHoa(loaiHangHoaField.getText().trim());
        return h;
    }

    public void setHanghoa(Hanghoa hanghoa) {
        if (hanghoa != null) {
            tenHangHoaField.setText(hanghoa.getTenHangHoa());
            soLuongSpinner.setValue(hanghoa.getSoLuongHangHoa());
            nhaSanXuatField.setText(hanghoa.getNhaSanXuat());
            namSanXuatSpinner.setValue(hanghoa.getNamSanXuat());
            loaiHangHoaField.setText(hanghoa.getLoaiHangHoa());
        }
    }

    public Hanghoa getHanghoa() {
        return confirmed ? hanghoa : null;
    }
}
