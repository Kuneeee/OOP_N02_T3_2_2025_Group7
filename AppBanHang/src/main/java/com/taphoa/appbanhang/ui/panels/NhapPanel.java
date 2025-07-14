package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Nhap;
import com.taphoa.appbanhang.service.HanghoaService;
import com.taphoa.appbanhang.service.NhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NhapPanel extends JPanel {

    @Autowired
    private NhapService nhapService;
    
    @Autowired
    private HanghoaService hanghoaService;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, refreshButton;

    public NhapPanel() {
        initializeUI();
    }

    @Autowired
    public void initializeData() {
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Quản Lý Nhập Hàng"));

        // Panel tìm kiếm và các nút
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("🔍 Tìm");
        
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
        
        topPanel.add(searchLabel);
        topPanel.add(searchField, "growx");
        topPanel.add(searchButton);
        
        add(topPanel, BorderLayout.NORTH);

        // Bảng hiển thị dữ liệu
        createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        addButton = new JButton("➕ Thêm");
        editButton = new JButton("✏️ Sửa");
        deleteButton = new JButton("🗑️ Xóa");
        refreshButton = new JButton("🔄 Làm mới");
        
        addButton.addActionListener(this::addNhap);
        editButton.addActionListener(this::editNhap);
        deleteButton.addActionListener(this::deleteNhap);
        refreshButton.addActionListener(e -> loadData());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createTable() {
        String[] columnNames = {"ID", "Mã hàng", "Tên hàng", "Số lượng", "Giá nhập", "Thành tiền", "Ngày nhập", "Ghi chú"};
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
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Số lượng
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Giá nhập
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Thành tiền
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Ngày nhập
        table.getColumnModel().getColumn(7).setPreferredWidth(150); // Ghi chú
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Nhap> nhapList = nhapService.findAll();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Nhap nhap : nhapList) {
                Object[] row = {
                    nhap.getId(),
                    nhap.getHanghoa().getMaHang(),
                    nhap.getHanghoa().getTenHang(),
                    nhap.getSoLuong(),
                    String.format("%,.0f", nhap.getGiaNhap()),
                    String.format("%,.0f", nhap.getThanhTien()),
                    nhap.getNgayNhap().format(formatter),
                    nhap.getGhiChu() != null ? nhap.getGhiChu() : ""
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        try {
            List<Nhap> nhapList = nhapService.search(keyword);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Nhap nhap : nhapList) {
                Object[] row = {
                    nhap.getId(),
                    nhap.getHanghoa().getMaHang(),
                    nhap.getHanghoa().getTenHang(),
                    nhap.getSoLuong(),
                    String.format("%,.0f", nhap.getGiaNhap()),
                    String.format("%,.0f", nhap.getThanhTien()),
                    nhap.getNgayNhap().format(formatter),
                    nhap.getGhiChu() != null ? nhap.getGhiChu() : ""
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNhap(ActionEvent e) {
        NhapDialog dialog = new NhapDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                         "Thêm Phiếu Nhập", hanghoaService);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            try {
                Nhap nhap = dialog.getNhap();
                nhapService.save(nhap);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!", 
                                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi thêm phiếu nhập: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editNhap(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần sửa!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            Nhap nhap = nhapService.findById(id).orElse(null);
            if (nhap == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập!", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            NhapDialog dialog = new NhapDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                             "Sửa Phiếu Nhập", hanghoaService, nhap);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                Nhap updatedNhap = dialog.getNhap();
                updatedNhap.setId(id);
                nhapService.save(updatedNhap);
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu nhập thành công!", 
                                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi sửa phiếu nhập: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteNhap(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa phiếu nhập này?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long id = (Long) tableModel.getValueAt(selectedRow, 0);
                nhapService.deleteById(id);
                loadData();
                JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!", 
                                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xóa phiếu nhập: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
