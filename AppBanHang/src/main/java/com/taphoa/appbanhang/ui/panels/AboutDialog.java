package com.taphoa.appbanhang.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AboutDialog extends JDialog {
    
    public AboutDialog(Frame parent) {
        super(parent, "Thông Tin Ứng Dụng", true);
        
        initializeUI();
        
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Panel chính với thông tin
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Logo và tiêu đề
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        // Icon ứng dụng (sử dụng emoji làm logo)
        JLabel logoLabel = new JLabel("🏪", SwingConstants.CENTER);
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 48));
        headerPanel.add(logoLabel, BorderLayout.NORTH);
        
        // Tên ứng dụng
        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ TẠP HÓA", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Thông tin chi tiết
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        addInfoRow(infoPanel, "Phiên bản:", "1.0.0");
        addInfoRow(infoPanel, "Ngày phát hành:", "15/07/2025");
        addInfoRow(infoPanel, "Nền tảng:", "Java 21 + Spring Boot 3.2.0");
        addInfoRow(infoPanel, "Giao diện:", "Swing + FlatLaf");
        addInfoRow(infoPanel, "Cơ sở dữ liệu:", "H2 Database (In-Memory)");
        addInfoRow(infoPanel, "Giấy phép:", "MIT License");
        
        // Thêm khoảng trống
        infoPanel.add(new JLabel(" "));
        
        // Mô tả
        JLabel descLabel = new JLabel("<html><center><i>Ứng dụng quản lý tạp hóa đơn giản và hiệu quả<br>" +
            "Hỗ trợ quản lý hàng hóa, nhập/xuất, và báo cáo doanh thu</i></center></html>");
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(descLabel);
        
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton systemInfoButton = new JButton("🖥️ Thông Tin Hệ Thống");
        JButton closeButton = new JButton("✅ Đóng");
        
        systemInfoButton.addActionListener(this::showSystemInfo);
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(systemInfoButton);
        buttonPanel.add(closeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addInfoRow(JPanel parent, String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        
        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.EAST);
        
        parent.add(row);
    }
    
    private void showSystemInfo(ActionEvent e) {
        StringBuilder info = new StringBuilder();
        info.append("THÔNG TIN HỆ THỐNG\n");
        info.append("═".repeat(40)).append("\n\n");
        
        // Thông tin Java
        info.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        info.append("Java Vendor: ").append(System.getProperty("java.vendor")).append("\n");
        info.append("Java Home: ").append(System.getProperty("java.home")).append("\n\n");
        
        // Thông tin hệ điều hành
        info.append("OS Name: ").append(System.getProperty("os.name")).append("\n");
        info.append("OS Version: ").append(System.getProperty("os.version")).append("\n");
        info.append("OS Architecture: ").append(System.getProperty("os.arch")).append("\n\n");
        
        // Thông tin bộ nhớ
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        info.append("Max Memory: ").append(formatBytes(maxMemory)).append("\n");
        info.append("Total Memory: ").append(formatBytes(totalMemory)).append("\n");
        info.append("Used Memory: ").append(formatBytes(usedMemory)).append("\n");
        info.append("Free Memory: ").append(formatBytes(freeMemory)).append("\n\n");
        
        // Thông tin user
        info.append("User Name: ").append(System.getProperty("user.name")).append("\n");
        info.append("User Home: ").append(System.getProperty("user.home")).append("\n");
        info.append("Working Directory: ").append(System.getProperty("user.dir")).append("\n");
        
        // Hiển thị trong dialog
        JTextArea textArea = new JTextArea(info.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Thông Tin Hệ Thống", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024));
        return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
    }
}
