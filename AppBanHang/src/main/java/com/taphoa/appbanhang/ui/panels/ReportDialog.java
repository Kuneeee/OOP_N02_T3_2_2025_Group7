package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.service.HanghoaService;
import com.taphoa.appbanhang.service.XuatBanService;
import com.taphoa.appbanhang.service.NhapService;
import com.taphoa.appbanhang.service.DoanhThuService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportDialog extends JDialog {
    
    private HanghoaService hanghoaService;
    private XuatBanService xuatBanService;
    private NhapService nhapService;
    private DoanhThuService doanhThuService;
    
    private JTextArea reportArea;
    
    public ReportDialog(Frame parent, HanghoaService hanghoaService, 
                       XuatBanService xuatBanService, NhapService nhapService, 
                       DoanhThuService doanhThuService) {
        super(parent, "Báo Cáo Tổng Hợp", true);
        this.hanghoaService = hanghoaService;
        this.xuatBanService = xuatBanService;
        this.nhapService = nhapService;
        this.doanhThuService = doanhThuService;
        
        initializeUI();
        generateReport();
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("BÁO CÁO TỔNG HỢP KINH DOANH", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
        
        // Nội dung báo cáo
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        reportArea.setBackground(getBackground());
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        // Nút đóng
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Đóng");
        JButton exportButton = new JButton("Xuất Excel");
        
        closeButton.addActionListener(e -> dispose());
        exportButton.addActionListener(e -> exportReport());
        
        buttonPanel.add(exportButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void generateReport() {
        StringBuilder report = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        
        report.append("NGÀY BÁO CÁO: ").append(today.format(formatter)).append("\n");
        report.append("".repeat(60)).append("\n\n");
        
        try {
            // 1. Thống kê hàng hóa
            report.append("1. THỐNG KÊ HÀNG HÓA\n");
            report.append("-".repeat(40)).append("\n");
            
            int totalProducts = hanghoaService.getAllHanghoa().size();
            int lowStockCount = hanghoaService.getLowStockItems(10).size();
            
            report.append(String.format("• Tổng số mặt hàng: %d\n", totalProducts));
            report.append(String.format("• Hàng sắp hết (< 10): %d\n", lowStockCount));
            
            // Tổng giá trị tồn kho
            double totalInventoryValue = hanghoaService.getAllHanghoa().stream()
                .mapToDouble(h -> h.getSoLuongHangHoa() * 1000.0) // Giả sử giá trung bình 1000/sp
                .sum();
            report.append(String.format("• Tổng giá trị tồn kho: %,.0f VNĐ\n\n", totalInventoryValue));
            
            // 2. Thống kê bán hàng
            report.append("2. THỐNG KÊ BÁN HÀNG\n");
            report.append("-".repeat(40)).append("\n");
            
            Double todayRevenue = xuatBanService.getTotalRevenueByDate(today);
            Double monthlyRevenue = xuatBanService.getTotalRevenueBetweenDates(startOfMonth, today);
            
            report.append(String.format("• Doanh thu hôm nay: %,.0f VNĐ\n", 
                todayRevenue != null ? todayRevenue : 0.0));
            report.append(String.format("• Doanh thu tháng này: %,.0f VNĐ\n", 
                monthlyRevenue != null ? monthlyRevenue : 0.0));
            
            // Số đơn hàng
            int todayOrders = xuatBanService.findAll().size(); // Tạm thời dùng tổng số
            report.append(String.format("• Tổng số đơn hàng: %d\n\n", todayOrders));
            
            // 3. Thống kê nhập hàng
            report.append("3. THỐNG KÊ NHẬP HÀNG\n");
            report.append("-".repeat(40)).append("\n");
            
            int totalImports = nhapService.findAll().size();
            report.append(String.format("• Tổng số phiếu nhập: %d\n\n", totalImports));
            
            // 4. Lợi nhuận
            report.append("4. THỐNG KÊ LỢI NHUẬN\n");
            report.append("-".repeat(40)).append("\n");
            
            Double monthlyProfit = doanhThuService.getTotalProfitBetweenDates(startOfMonth, today);
            double profitMargin = (monthlyRevenue != null && monthlyRevenue > 0) ? 
                ((monthlyProfit != null ? monthlyProfit : 0.0) / monthlyRevenue * 100) : 0.0;
            
            report.append(String.format("• Lợi nhuận tháng này: %,.0f VNĐ\n", 
                monthlyProfit != null ? monthlyProfit : 0.0));
            report.append(String.format("• Tỷ suất lợi nhuận: %.2f%%\n\n", profitMargin));
            
            // 5. Thông tin hệ thống
            report.append("5. THÔNG TIN HỆ THỐNG\n");
            report.append("-".repeat(40)).append("\n");
            report.append("• Hệ thống quản lý tạp hóa\n");
            report.append("• Phiên bản: 1.0.0\n");
            report.append("• Ngày tạo báo cáo: ").append(LocalDate.now().format(formatter)).append("\n");
            
        } catch (Exception e) {
            report.append("Lỗi khi tạo báo cáo: ").append(e.getMessage());
        }
        
        reportArea.setText(report.toString());
    }
    
    private void exportReport() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Xuất báo cáo");
            fileChooser.setSelectedFile(new java.io.File("BaoCao_TongHop_" + 
                LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".txt"));
            
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                java.nio.file.Files.write(
                    java.nio.file.Paths.get(filePath), 
                    reportArea.getText().getBytes(java.nio.charset.StandardCharsets.UTF_8)
                );
                
                JOptionPane.showMessageDialog(this, 
                    "Xuất báo cáo thành công!\nFile: " + filePath, 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xuất báo cáo: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
