package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.entity.XuatBan;
import com.taphoa.appbanhang.service.HanghoaService;
import com.taphoa.appbanhang.service.XuatBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class XuatBanPanel extends JPanel {

    @Autowired
    private XuatBanService xuatBanService;
    
    @Autowired
    private HanghoaService hanghoaService;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, refreshButton;

    public XuatBanPanel() {
        initializeUI();
    }

    @Autowired
    public void initializeData() {
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Quản Lý Xuất Bán"));

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
        
        addButton.addActionListener(this::addXuatBan);
        editButton.addActionListener(this::editXuatBan);
        deleteButton.addActionListener(this::deleteXuatBan);
        refreshButton.addActionListener(e -> loadData());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createTable() {
        String[] columnNames = {"ID", "Mã hàng", "Tên hàng", "Số lượng", "Giá bán", "Thành tiền", "Ngày bán", "Khách hàng", "Ghi chú"};
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
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Giá bán
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Thành tiền
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Ngày bán
        table.getColumnModel().getColumn(7).setPreferredWidth(120); // Khách hàng
        table.getColumnModel().getColumn(8).setPreferredWidth(150); // Ghi chú
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<XuatBan> xuatBanList = xuatBanService.findAll();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (XuatBan xuatBan : xuatBanList) {
                Object[] row = {
                    xuatBan.getId(),
                    xuatBan.getHanghoa().getMaHang(),
                    xuatBan.getHanghoa().getTenHang(),
                    xuatBan.getSoLuong(),
                    String.format("%,.0f", xuatBan.getGiaBan()),
                    String.format("%,.0f", xuatBan.getThanhTien()),
                    xuatBan.getNgayBan().format(formatter),
                    xuatBan.getKhachHang() != null ? xuatBan.getKhachHang() : "",
                    xuatBan.getGhiChu() != null ? xuatBan.getGhiChu() : ""
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
            List<XuatBan> xuatBanList = xuatBanService.search(keyword);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (XuatBan xuatBan : xuatBanList) {
                Object[] row = {
                    xuatBan.getId(),
                    xuatBan.getHanghoa().getMaHang(),
                    xuatBan.getHanghoa().getTenHang(),
                    xuatBan.getSoLuong(),
                    String.format("%,.0f", xuatBan.getGiaBan()),
                    String.format("%,.0f", xuatBan.getThanhTien()),
                    xuatBan.getNgayBan().format(formatter),
                    xuatBan.getKhachHang() != null ? xuatBan.getKhachHang() : "",
                    xuatBan.getGhiChu() != null ? xuatBan.getGhiChu() : ""
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addXuatBan(ActionEvent e) {
        XuatBanDialog dialog = new XuatBanDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                               "Thêm Phiếu Xuất Bán", hanghoaService);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            try {
                XuatBan xuatBan = dialog.getXuatBan();
                
                // Kiểm tra tồn kho
                Hanghoa hanghoa = xuatBan.getHanghoa();
                if (hanghoa.getSoLuongTon() < xuatBan.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, 
                        "Không đủ hàng trong kho!\nTồn kho hiện tại: " + hanghoa.getSoLuongTon(), 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                xuatBanService.save(xuatBan);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm phiếu xuất bán thành công!", 
                                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi thêm phiếu xuất bán: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editXuatBan(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu xuất bán cần sửa!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Long id = (Long) tableModel.getValueAt(selectedRow, 0);
            XuatBan xuatBan = xuatBanService.findById(id).orElse(null);
            if (xuatBan == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất bán!", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            XuatBanDialog dialog = new XuatBanDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                                   "Sửa Phiếu Xuất Bán", hanghoaService, xuatBan);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                XuatBan updatedXuatBan = dialog.getXuatBan();
                updatedXuatBan.setId(id);
                
                // Kiểm tra tồn kho (trừ số lượng cũ, cộng số lượng mới)
                Hanghoa hanghoa = updatedXuatBan.getHanghoa();
                int soLuongKhaDung = hanghoa.getSoLuongTon() + xuatBan.getSoLuong();
                if (soLuongKhaDung < updatedXuatBan.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, 
                        "Không đủ hàng trong kho!\nTồn kho có thể sử dụng: " + soLuongKhaDung, 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                xuatBanService.save(updatedXuatBan);
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu xuất bán thành công!", 
                                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi sửa phiếu xuất bán: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteXuatBan(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu xuất bán cần xóa!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa phiếu xuất bán này?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long id = (Long) tableModel.getValueAt(selectedRow, 0);
                xuatBanService.deleteById(id);
                loadData();
                JOptionPane.showMessageDialog(this, "Xóa phiếu xuất bán thành công!", 
                                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xóa phiếu xuất bán: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
