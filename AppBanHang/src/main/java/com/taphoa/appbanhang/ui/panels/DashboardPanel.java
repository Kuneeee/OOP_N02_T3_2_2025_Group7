package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.service.HanghoaService;
import com.taphoa.appbanhang.service.XuatBanService;
import com.taphoa.appbanhang.service.DoanhThuService;
import com.taphoa.appbanhang.service.NhapService;
import com.taphoa.appbanhang.ui.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Component
public class DashboardPanel extends JPanel {

    @Autowired
    private HanghoaService hanghoaService;
    
    @Autowired
    private XuatBanService xuatBanService;
    
    @Autowired
    private DoanhThuService doanhThuService;
    
    @Autowired
    private NhapService nhapService;

    private JLabel totalProductsLabel;
    private JLabel todayRevenueLabel;
    private JLabel lowStockLabel;
    private JLabel monthlyProfitLabel;

    public DashboardPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));
        initializeUI();
    }

    private void initializeUI() {
        // Tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Dashboard - Tổng Quan");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Tạo main panel với BorderLayout
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chứa các card stats
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Tạo các card thống kê
        statsPanel.add(createStatsCard("📦", "Tổng Hàng Hóa", "0", Color.GREEN));
        statsPanel.add(createStatsCard("💰", "Doanh Thu Hôm Nay", "0 ₫", Color.BLUE));
        statsPanel.add(createStatsCard("⚠️", "Hàng Sắp Hết", "0", Color.ORANGE));
        statsPanel.add(createStatsCard("📈", "Lợi Nhuận Tháng", "0 ₫", Color.RED));
        
        add(statsPanel, BorderLayout.CENTER);

        // Panel hoạt động nhanh
        add(createQuickActionsPanel(), BorderLayout.SOUTH);
    }

    @Autowired
    public void loadData() {
        SwingUtilities.invokeLater(this::updateStats);
    }

    private JPanel createStatsCard(String icon, String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(Color.WHITE);

        // Top panel với icon và title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        
        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 48));
        topPanel.add(iconLabel);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        titleLabel.setForeground(Color.GRAY);
        topPanel.add(titleLabel);

        // Value panel
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        valuePanel.setBackground(Color.WHITE);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        valueLabel.setForeground(accentColor);
        valuePanel.add(valueLabel);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);

        // Lưu reference để cập nhật sau
        switch (title) {
            case "Tổng Hàng Hóa":
                totalProductsLabel = valueLabel;
                break;
            case "Doanh Thu Hôm Nay":
                todayRevenueLabel = valueLabel;
                break;
            case "Hàng Sắp Hết":
                lowStockLabel = valueLabel;
                break;
            case "Lợi Nhuận Tháng":
                monthlyProfitLabel = valueLabel;
                break;
        }

        return card;
    }

    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Thao Tác Nhanh"));

        // Các nút thao tác nhanh
        panel.add(createActionButton("📦 Thêm Hàng Hóa", this::addProduct));
        panel.add(createActionButton("📥 Nhập Hàng", this::importProducts));
        panel.add(createActionButton("📤 Bán Hàng", this::sellProducts));
        panel.add(createActionButton("📊 Xem Báo Cáo", this::viewReports));
        panel.add(createActionButton("📋 Báo Cáo Tổng Hợp", () -> viewSummaryReport()));

        panel.add(createActionButton("🔍 Tìm Sản Phẩm", this::searchProducts));
        panel.add(createActionButton("⚠️ Hàng Sắp Hết", this::viewLowStock));
        panel.add(createActionButton("💾 Sao Lưu", this::backup));
        panel.add(createActionButton("🔄 Làm Mới", this::refresh));
        panel.add(createActionButton("⚙️ Cài Đặt", () -> openSettings()));

        return panel;
    }

    private JButton createActionButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        button.addActionListener(e -> action.run());
        return button;
    }

    private void updateStats() {
        try {
            // Cập nhật tổng số hàng hóa
            int totalProducts = hanghoaService.getAllHanghoa().size();
            if (totalProductsLabel != null) {
                totalProductsLabel.setText(String.valueOf(totalProducts));
            }

            // Cập nhật doanh thu hôm nay
            Double todayRevenue = xuatBanService.getTotalRevenueByDate(LocalDate.now());
            if (todayRevenueLabel != null) {
                todayRevenueLabel.setText(formatCurrency(todayRevenue));
            }

            // Cập nhật hàng sắp hết
            int lowStockCount = hanghoaService.getLowStockItems(10).size();
            if (lowStockLabel != null) {
                lowStockLabel.setText(String.valueOf(lowStockCount));
            }

            // Cập nhật lợi nhuận tháng
            LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate endOfMonth = LocalDate.now();
            Double monthlyProfit = doanhThuService.getTotalProfitBetweenDates(startOfMonth, endOfMonth);
            if (monthlyProfitLabel != null) {
                monthlyProfitLabel.setText(formatCurrency(monthlyProfit));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu: " + e.getMessage());
        }
    }

    private String formatCurrency(Double amount) {
        if (amount == null) amount = 0.0;
        return String.format("%,.0f ₫", amount);
    }

    // Các phương thức xử lý sự kiện
    private void addProduct() {
        // Hiển thị dialog thêm hàng hóa
        try {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            HanghoaDialog dialog = new HanghoaDialog(parentFrame, "Thêm Hàng Hóa Mới", true);
            dialog.setVisible(true);
            
            Hanghoa newHanghoa = dialog.getHanghoa();
            if (newHanghoa != null) {
                hanghoaService.saveHanghoa(newHanghoa);
                updateStats(); // Cập nhật lại thống kê
                JOptionPane.showMessageDialog(this, "Thêm hàng hóa thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm hàng hóa: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importProducts() {
        // Hiển thị dialog nhập hàng
        try {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            NhapDialog dialog = new NhapDialog(parentFrame, "Nhập Hàng Mới", hanghoaService);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                updateStats(); // Cập nhật lại thống kê
                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi nhập hàng: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sellProducts() {
        // Hiển thị dialog bán hàng
        try {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            XuatBanDialog dialog = new XuatBanDialog(parentFrame, "Bán Hàng Mới", hanghoaService);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                updateStats(); // Cập nhật lại thống kê
                JOptionPane.showMessageDialog(this, "Bán hàng thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi bán hàng: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewReports() {
        // Chuyển đến panel báo cáo doanh thu
        MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        if (mainFrame != null) {
            mainFrame.switchToPanel("DoanhThu");
        }
    }
    
    private void viewSummaryReport() {
        // Hiển thị báo cáo tổng hợp
        try {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            ReportDialog reportDialog = new ReportDialog(parentFrame, hanghoaService, 
                xuatBanService, nhapService, doanhThuService);
            reportDialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tạo báo cáo: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchProducts() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập từ khóa tìm kiếm sản phẩm:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            try {
                List<Hanghoa> results = hanghoaService.searchByTenHangHoa(keyword.trim());
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào!", 
                        "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder message = new StringBuilder("Tìm thấy " + results.size() + " sản phẩm:\n\n");
                    for (int i = 0; i < Math.min(results.size(), 5); i++) {
                        Hanghoa h = results.get(i);
                        message.append("• ").append(h.getTenHangHoa())
                               .append(" (SL: ").append(h.getSoLuongHangHoa()).append(")\n");
                    }
                    if (results.size() > 5) {
                        message.append("... và ").append(results.size() - 5).append(" sản phẩm khác");
                    }
                    
                    JOptionPane.showMessageDialog(this, message.toString(), 
                        "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                        
                    // Chuyển đến tab hàng hóa để xem chi tiết
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                    if (mainFrame != null) {
                        mainFrame.switchToPanel("Hanghoa");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewLowStock() {
        try {
            List<Hanghoa> lowStockItems = hanghoaService.getLowStockItems(10);
            if (lowStockItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tất cả sản phẩm đều có số lượng đủ!", 
                    "Hàng tồn kho", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder message = new StringBuilder("Có " + lowStockItems.size() + " sản phẩm sắp hết:\n\n");
                for (Hanghoa h : lowStockItems) {
                    message.append("• ").append(h.getTenHangHoa())
                           .append(" (SL: ").append(h.getSoLuongHangHoa()).append(")\n");
                }
                message.append("\nBạn có muốn chuyển đến quản lý hàng hóa không?");
                
                int choice = JOptionPane.showConfirmDialog(this, message.toString(), 
                    "Cảnh báo hàng sắp hết", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    
                if (choice == JOptionPane.YES_OPTION) {
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                    if (mainFrame != null) {
                        mainFrame.switchToPanel("Hanghoa");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra hàng tồn: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backup() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí sao lưu");
            fileChooser.setSelectedFile(new java.io.File("backup_" + 
                LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy")) + ".xlsx"));
            
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                // Sao lưu tất cả dữ liệu doanh thu
                doanhThuService.exportToExcel(filePath, null, null);
                
                JOptionPane.showMessageDialog(this, 
                    "Sao lưu dữ liệu thành công!\nFile: " + filePath, 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi sao lưu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refresh() {
        SwingUtilities.invokeLater(() -> {
            updateStats();
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    private void openSettings() {
        try {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            SettingsDialog settingsDialog = new SettingsDialog(parentFrame);
            settingsDialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi mở cài đặt: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
