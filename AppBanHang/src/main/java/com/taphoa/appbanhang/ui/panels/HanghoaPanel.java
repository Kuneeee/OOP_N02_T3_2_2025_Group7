package com.taphoa.appbanhang.ui.panels;

import com.taphoa.appbanhang.entity.Hanghoa;
import com.taphoa.appbanhang.service.HanghoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
public class HanghoaPanel extends JPanel {

    @Autowired
    private HanghoaService hanghoaService;

    private JTable hanghoaTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;

    public HanghoaPanel() {
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        // Tiêu đề và tìm kiếm
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Bảng dữ liệu
        add(createTablePanel(), BorderLayout.CENTER);
        
        // Panel nút bấm
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Tiêu đề
        JLabel titleLabel = new JLabel("Quản Lý Hàng Hóa");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        panel.add(titleLabel);
        
        // Tìm kiếm
        panel.add(new JLabel("Tìm kiếm:"));
        
        searchField = new JTextField(15);
        panel.add(searchField);
        
        searchTypeCombo = new JComboBox<>(new String[]{"Tên", "Loại", "Nhà sản xuất"});
        panel.add(searchTypeCombo);
        
        JButton searchBtn = new JButton("Tìm");
        searchBtn.addActionListener(e -> searchProducts());
        panel.add(searchBtn);
        
        return panel;
    }

    private JScrollPane createTablePanel() {
        // Tạo model cho bảng
        String[] columns = {"ID", "Tên Hàng Hóa", "Số Lượng", "Nhà Sản Xuất", "Năm SX", "Loại"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp
            }
        };
        
        hanghoaTable = new JTable(tableModel);
        hanghoaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hanghoaTable.getTableHeader().setReorderingAllowed(false);
        
        // Thiết lập độ rộng cột
        hanghoaTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        hanghoaTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        hanghoaTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        hanghoaTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        hanghoaTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        hanghoaTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        
        JScrollPane scrollPane = new JScrollPane(hanghoaTable);
        scrollPane.setPreferredSize(new Dimension(0, 400));
        
        return scrollPane;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton addBtn = new JButton("Thêm");
        addBtn.addActionListener(e -> addProduct());
        panel.add(addBtn);
        
        JButton editBtn = new JButton("Sửa");
        editBtn.addActionListener(e -> editProduct());
        panel.add(editBtn);
        
        JButton deleteBtn = new JButton("Xóa");
        deleteBtn.addActionListener(e -> deleteProduct());
        panel.add(deleteBtn);
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        panel.add(refreshBtn);
        
        JButton lowStockBtn = new JButton("Hàng sắp hết");
        lowStockBtn.addActionListener(e -> showLowStock());
        panel.add(lowStockBtn);
        
        return panel;
    }

    @Autowired
    public void loadData() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Hanghoa> hanghoaList = hanghoaService.getAllHanghoa();
                updateTable(hanghoaList);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
            }
        });
    }

    private void updateTable(List<Hanghoa> hanghoaList) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        
        for (Hanghoa hanghoa : hanghoaList) {
            Object[] row = {
                hanghoa.getHanghoaID(),
                hanghoa.getTenHangHoa(),
                hanghoa.getSoLuongHangHoa(),
                hanghoa.getNhaSanXuat(),
                hanghoa.getNamSanXuat(),
                hanghoa.getLoaiHangHoa()
            };
            tableModel.addRow(row);
        }
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        try {
            List<Hanghoa> results;
            String searchType = (String) searchTypeCombo.getSelectedItem();
            
            switch (searchType) {
                case "Loại":
                    results = hanghoaService.searchByLoaiHangHoa(keyword);
                    break;
                case "Nhà sản xuất":
                    results = hanghoaService.searchByNhaSanXuat(keyword);
                    break;
                default:
                    results = hanghoaService.searchByTenHangHoa(keyword);
                    break;
            }
            
            updateTable(results);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    private void addProduct() {
        HanghoaDialog dialog = new HanghoaDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Hàng Hóa", true);
        dialog.setVisible(true);
        
        Hanghoa newHanghoa = dialog.getHanghoa();
        if (newHanghoa != null) {
            try {
                hanghoaService.saveHanghoa(newHanghoa);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm hàng hóa thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi thêm hàng hóa: " + e.getMessage());
            }
        }
    }

    private void editProduct() {
        int selectedRow = hanghoaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng hóa cần sửa!");
            return;
        }

        Long hanghoaId = (Long) tableModel.getValueAt(selectedRow, 0);
        try {
            Hanghoa hanghoa = hanghoaService.getHanghoaById(hanghoaId).orElse(null);
            if (hanghoa == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hàng hóa!");
                return;
            }

            HanghoaDialog dialog = new HanghoaDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Hàng Hóa", true);
            dialog.setHanghoa(hanghoa);
            dialog.setVisible(true);

            Hanghoa updatedHanghoa = dialog.getHanghoa();
            if (updatedHanghoa != null) {
                hanghoaService.updateHanghoa(hanghoaId, updatedHanghoa);
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật hàng hóa thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật hàng hóa: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        int selectedRow = hanghoaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hàng hóa cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa hàng hóa này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Long hanghoaId = (Long) tableModel.getValueAt(selectedRow, 0);
            try {
                hanghoaService.deleteHanghoa(hanghoaId);
                loadData();
                JOptionPane.showMessageDialog(this, "Xóa hàng hóa thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi xóa hàng hóa: " + e.getMessage());
            }
        }
    }

    private void showLowStock() {
        try {
            List<Hanghoa> lowStockItems = hanghoaService.getLowStockItems(10);
            updateTable(lowStockItems);
            JOptionPane.showMessageDialog(this, 
                "Hiển thị " + lowStockItems.size() + " sản phẩm có số lượng < 10");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
}
