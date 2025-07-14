package com.taphoa.appbanhang.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Properties;
import java.io.*;

public class SettingsDialog extends JDialog {
    
    private JTextField companyNameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField emailField;
    private JSpinner lowStockThresholdSpinner;
    private JCheckBox autoBackupCheckBox;
    private JComboBox<String> lookAndFeelComboBox;
    
    private Properties settings;
    private final String SETTINGS_FILE = "settings.properties";
    
    public SettingsDialog(Frame parent) {
        super(parent, "Cài Đặt Hệ Thống", true);
        
        loadSettings();
        initializeUI();
        loadSettingsToUI();
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("CÀI ĐẶT HỆ THỐNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel chính
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Tab thông tin công ty
        tabbedPane.addTab("📍 Thông Tin", createCompanyInfoPanel());
        
        // Tab cài đặt hệ thống
        tabbedPane.addTab("⚙️ Hệ Thống", createSystemSettingsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("💾 Lưu");
        JButton cancelButton = new JButton("❌ Hủy");
        JButton resetButton = new JButton("🔄 Mặc Định");
        
        saveButton.addActionListener(this::saveSettings);
        cancelButton.addActionListener(e -> dispose());
        resetButton.addActionListener(this::resetToDefaults);
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createCompanyInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tên công ty
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tên cửa hàng:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        companyNameField = new JTextField(20);
        panel.add(companyNameField, gbc);
        
        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        addressField = new JTextField(20);
        panel.add(addressField, gbc);
        
        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        phoneField = new JTextField(20);
        panel.add(phoneField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        return panel;
    }
    
    private JPanel createSystemSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Ngưỡng cảnh báo hàng sắp hết
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Ngưỡng cảnh báo hàng sắp hết:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        lowStockThresholdSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        panel.add(lowStockThresholdSpinner, gbc);
        
        // Tự động sao lưu
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        autoBackupCheckBox = new JCheckBox("Tự động sao lưu dữ liệu hàng ngày");
        panel.add(autoBackupCheckBox, gbc);
        
        // Giao diện
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(new JLabel("Giao diện:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        lookAndFeelComboBox = new JComboBox<>(new String[]{
            "FlatLaf Light", "FlatLaf Dark", "System Default"
        });
        panel.add(lookAndFeelComboBox, gbc);
        
        return panel;
    }
    
    private void loadSettings() {
        settings = new Properties();
        try {
            File settingsFile = new File(SETTINGS_FILE);
            if (settingsFile.exists()) {
                settings.load(new FileInputStream(settingsFile));
            } else {
                // Tạo cài đặt mặc định
                setDefaultSettings();
            }
        } catch (IOException e) {
            setDefaultSettings();
        }
    }
    
    private void setDefaultSettings() {
        settings.setProperty("company.name", "Tạp Hóa ABC");
        settings.setProperty("company.address", "123 Đường ABC, Phường XYZ");
        settings.setProperty("company.phone", "0123456789");
        settings.setProperty("company.email", "contact@taphoa.com");
        settings.setProperty("system.lowstock.threshold", "10");
        settings.setProperty("system.auto.backup", "true");
        settings.setProperty("system.lookandfeel", "FlatLaf Light");
    }
    
    private void loadSettingsToUI() {
        companyNameField.setText(settings.getProperty("company.name", ""));
        addressField.setText(settings.getProperty("company.address", ""));
        phoneField.setText(settings.getProperty("company.phone", ""));
        emailField.setText(settings.getProperty("company.email", ""));
        
        lowStockThresholdSpinner.setValue(Integer.parseInt(
            settings.getProperty("system.lowstock.threshold", "10")));
        autoBackupCheckBox.setSelected(Boolean.parseBoolean(
            settings.getProperty("system.auto.backup", "true")));
        lookAndFeelComboBox.setSelectedItem(
            settings.getProperty("system.lookandfeel", "FlatLaf Light"));
    }
    
    private void saveSettings(ActionEvent e) {
        try {
            settings.setProperty("company.name", companyNameField.getText().trim());
            settings.setProperty("company.address", addressField.getText().trim());
            settings.setProperty("company.phone", phoneField.getText().trim());
            settings.setProperty("company.email", emailField.getText().trim());
            
            settings.setProperty("system.lowstock.threshold", 
                lowStockThresholdSpinner.getValue().toString());
            settings.setProperty("system.auto.backup", 
                String.valueOf(autoBackupCheckBox.isSelected()));
            settings.setProperty("system.lookandfeel", 
                (String) lookAndFeelComboBox.getSelectedItem());
            
            settings.store(new FileOutputStream(SETTINGS_FILE), 
                "App Settings - Generated on " + new java.util.Date());
            
            JOptionPane.showMessageDialog(this, 
                "Lưu cài đặt thành công!\nMột số thay đổi có thể yêu cầu khởi động lại ứng dụng.", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                
            dispose();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lưu cài đặt: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetToDefaults(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn khôi phục cài đặt mặc định không?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            setDefaultSettings();
            loadSettingsToUI();
        }
    }
    
    public Properties getSettings() {
        return settings;
    }
}
