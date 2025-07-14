package com.taphoa.appbanhang.ui;

import com.taphoa.appbanhang.ui.panels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public class MainFrame extends JFrame {

    @Autowired
    private HanghoaPanel hanghoaPanel;
    
    @Autowired
    private NhapPanel nhapPanel;
    
    @Autowired
    private XuatBanPanel xuatBanPanel;
    
    @Autowired
    private DoanhThuPanel doanhThuPanel;
    
    @Autowired
    private DashboardPanel dashboardPanel;

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Ứng Dụng Quản Lý Tạp Hóa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Thiết lập kích thước và vị trí
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Tạo menu bar
        createMenuBar();
        
        // Tạo toolbar
        createToolBar();
        
        // Tạo content panel với CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Thêm status bar
        createStatusBar();
        
        add(contentPanel, BorderLayout.CENTER);
    }

    @Autowired
    public void initializePanels() {
        // Thêm các panel vào content panel sau khi dependency injection hoàn tất
        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(hanghoaPanel, "Hanghoa");
        contentPanel.add(nhapPanel, "Nhap");
        contentPanel.add(xuatBanPanel, "XuatBan");
        contentPanel.add(doanhThuPanel, "DoanhThu");
        
        // Hiển thị dashboard mặc định
        cardLayout.show(contentPanel, "Dashboard");
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Hệ thống
        JMenu systemMenu = new JMenu("Hệ thống");
        systemMenu.add(createMenuItem("Thoát", "exit", e -> System.exit(0)));
        
        // Menu Quản lý
        JMenu managementMenu = new JMenu("Quản lý");
        managementMenu.add(createMenuItem("Hàng hóa", "hanghoa", e -> showPanel("Hanghoa")));
        managementMenu.add(createMenuItem("Nhập hàng", "nhap", e -> showPanel("Nhap")));
        managementMenu.add(createMenuItem("Xuất bán", "xuatban", e -> showPanel("XuatBan")));
        managementMenu.addSeparator();
        managementMenu.add(createMenuItem("Báo cáo doanh thu", "doanhthu", e -> showPanel("DoanhThu")));
        
        // Menu Xem
        JMenu viewMenu = new JMenu("Xem");
        viewMenu.add(createMenuItem("Dashboard", "dashboard", e -> showPanel("Dashboard")));
        
        // Menu Trợ giúp
        JMenu helpMenu = new JMenu("Trợ giúp");
        helpMenu.add(createMenuItem("Hướng dẫn", "help", e -> showHelp()));
        helpMenu.add(createMenuItem("Giới thiệu", "about", e -> showAbout()));
        
        menuBar.add(systemMenu);
        menuBar.add(managementMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        toolBar.add(createToolButton("🏠", "Dashboard", e -> showPanel("Dashboard")));
        toolBar.addSeparator();
        toolBar.add(createToolButton("📦", "Hàng hóa", e -> showPanel("Hanghoa")));
        toolBar.add(createToolButton("📥", "Nhập hàng", e -> showPanel("Nhap")));
        toolBar.add(createToolButton("📤", "Xuất bán", e -> showPanel("XuatBan")));
        toolBar.addSeparator();
        toolBar.add(createToolButton("📊", "Báo cáo", e -> showPanel("DoanhThu")));
        
        add(toolBar, BorderLayout.NORTH);
    }

    private void createStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        
        JLabel statusLabel = new JLabel("Sẵn sàng");
        statusBar.add(statusLabel);
        
        add(statusBar, BorderLayout.SOUTH);
    }

    private JMenuItem createMenuItem(String text, String actionCommand, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(actionCommand);
        item.addActionListener(listener);
        return item;
    }

    private JButton createToolButton(String icon, String tooltip, ActionListener listener) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        return button;
    }

    private void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
    
    public void switchToPanel(String panelName) {
        showPanel(panelName);
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(this,
            "Hướng dẫn sử dụng:\n\n" +
            "1. Quản lý hàng hóa: Thêm, sửa, xóa sản phẩm\n" +
            "2. Nhập hàng: Ghi nhận phiếu nhập\n" +
            "3. Xuất bán: Ghi nhận giao dịch bán hàng\n" +
            "4. Báo cáo: Xem thống kê doanh thu",
            "Hướng dẫn", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        try {
            com.taphoa.appbanhang.ui.panels.AboutDialog aboutDialog = 
                new com.taphoa.appbanhang.ui.panels.AboutDialog(this);
            aboutDialog.setVisible(true);
        } catch (Exception e) {
            // Fallback nếu có lỗi
            JOptionPane.showMessageDialog(this,
                "Ứng Dụng Quản Lý Tạp Hóa\n" +
                "Phiên bản: 1.0.0\n" +
                "Được phát triển bằng Java Swing & Spring Boot\n\n" +
                "© 2025 - Ứng dụng quản lý cửa hàng tạp hóa",
                "Giới thiệu", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
