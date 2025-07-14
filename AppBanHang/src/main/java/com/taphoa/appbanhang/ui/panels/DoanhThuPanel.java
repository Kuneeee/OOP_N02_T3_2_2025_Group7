package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.DoanhThu;
import com.taphoa.appbanhang.service.DoanhThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DoanhThuPanel extends JPanel {

    @Autowired
    private DoanhThuService doanhThuService;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tuNgayField, denNgayField;
    private JButton filterButton, refreshButton, exportButton;
    private JLabel tongDoanhThuLabel, tongLoiNhuanLabel;

    public DoanhThuPanel() {
        initializeUI();
    }

    @Autowired
    public void initializeData() {
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Báo Cáo Doanh Thu"));

        // Panel filter
        createFilterPanel();
        
        // Bảng hiển thị dữ liệu
        createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel thống kê và các nút
        createBottomPanel();
    }

    private void createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        filterPanel.add(new JLabel("Từ ngày:"));
        tuNgayField = new JTextField(10);
        tuNgayField.setText(LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        filterPanel.add(tuNgayField);
        
        filterPanel.add(new JLabel("Đến ngày:"));
        denNgayField = new JTextField(10);
        denNgayField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        filterPanel.add(denNgayField);
        
        filterButton = new JButton("🔍 Lọc");
        filterButton.addActionListener(this::filterData);
        filterPanel.add(filterButton);
        
        refreshButton = new JButton("🔄 Làm mới");
        refreshButton.addActionListener(e -> loadData());
        filterPanel.add(refreshButton);
        
        filterPanel.add(new JLabel(""), "growx"); // Spacer
        
        exportButton = new JButton("📊 Xuất Excel");
        exportButton.addActionListener(this::exportToExcel);
        filterPanel.add(exportButton);
        
        add(filterPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columnNames = {
            "ID", "Mã hàng", "Tên hàng", "Số lượng bán", 
            "Doanh thu", "Giá vốn", "Lợi nhuận", "Ngày tạo"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Đặt độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(80);  // Mã hàng
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên hàng
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Số lượng bán
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Doanh thu
        table.getColumnModel().getColumn(5).setPreferredWidth(120); // Giá vốn
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Lợi nhuận
        table.getColumnModel().getColumn(7).setPreferredWidth(120); // Ngày tạo
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Panel thống kê
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Tổng Kết"));
        
        statsPanel.add(new JLabel("Tổng doanh thu:"));
        tongDoanhThuLabel = new JLabel("0 VNĐ");
        tongDoanhThuLabel.setForeground(Color.BLUE);
        tongDoanhThuLabel.setFont(tongDoanhThuLabel.getFont().deriveFont(Font.BOLD));
        statsPanel.add(tongDoanhThuLabel);
        
        statsPanel.add(new JLabel("Tổng lợi nhuận:"));
        tongLoiNhuanLabel = new JLabel("0 VNĐ");
        tongLoiNhuanLabel.setForeground(Color.GREEN);
        tongLoiNhuanLabel.setFont(tongLoiNhuanLabel.getFont().deriveFont(Font.BOLD));
        statsPanel.add(tongLoiNhuanLabel);
        
        bottomPanel.add(statsPanel, BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<DoanhThu> doanhThuList = doanhThuService.findAll();
            loadDoanhThuData(doanhThuList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDoanhThuData(List<DoanhThu> doanhThuList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        double tongDoanhThu = 0;
        double tongLoiNhuan = 0;
        
        for (DoanhThu doanhThu : doanhThuList) {
            Object[] row = {
                doanhThu.getId(),
                doanhThu.getHanghoa().getMaHang(),
                doanhThu.getHanghoa().getTenHang(),
                doanhThu.getSoLuongBan(),
                String.format("%,.0f", doanhThu.getDoanhThu()),
                String.format("%,.0f", doanhThu.getGiaVon()),
                String.format("%,.0f", doanhThu.getLoiNhuan()),
                doanhThu.getNgayTao().format(formatter)
            };
            tableModel.addRow(row);
            
            tongDoanhThu += doanhThu.getDoanhThu();
            tongLoiNhuan += doanhThu.getLoiNhuan();
        }
        
        // Cập nhật thống kê
        tongDoanhThuLabel.setText(String.format("%,.0f VNĐ", tongDoanhThu));
        tongLoiNhuanLabel.setText(String.format("%,.0f VNĐ", tongLoiNhuan));
        
        // Đổi màu lợi nhuận
        if (tongLoiNhuan >= 0) {
            tongLoiNhuanLabel.setForeground(Color.GREEN);
        } else {
            tongLoiNhuanLabel.setForeground(Color.RED);
        }
    }

    private void filterData(ActionEvent e) {
        String tuNgayStr = tuNgayField.getText().trim();
        String denNgayStr = denNgayField.getText().trim();
        
        if (tuNgayStr.isEmpty() || denNgayStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ từ ngày và đến ngày!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate tuNgay = LocalDate.parse(tuNgayStr, formatter);
            LocalDate denNgay = LocalDate.parse(denNgayStr, formatter);
            
            if (tuNgay.isAfter(denNgay)) {
                JOptionPane.showMessageDialog(this, "Từ ngày không được lớn hơn đến ngày!", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            tableModel.setRowCount(0);
            List<DoanhThu> doanhThuList = doanhThuService.findByDateRange(tuNgay, denNgay);
            loadDoanhThuData(doanhThuList);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lọc dữ liệu: " + ex.getMessage() + "\nĐịnh dạng ngày: dd/MM/yyyy", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToExcel(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu báo cáo Excel");
        fileChooser.setSelectedFile(new java.io.File("BaoCao_DoanhThu_" + 
            LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".xlsx"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                // Lấy dữ liệu hiện tại trong bảng
                String tuNgayStr = tuNgayField.getText().trim();
                String denNgayStr = denNgayField.getText().trim();
                
                LocalDate tuNgay = null;
                LocalDate denNgay = null;
                
                if (!tuNgayStr.isEmpty() && !denNgayStr.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    tuNgay = LocalDate.parse(tuNgayStr, formatter);
                    denNgay = LocalDate.parse(denNgayStr, formatter);
                }
                
                doanhThuService.exportToExcel(filePath, tuNgay, denNgay);
                
                JOptionPane.showMessageDialog(this, 
                    "Xuất báo cáo Excel thành công!\nFile: " + filePath, 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    
                // Mở file
                Desktop.getDesktop().open(new java.io.File(filePath));
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xuất Excel: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
