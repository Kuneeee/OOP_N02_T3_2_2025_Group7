package com.taphoa.appbanhang.service;

import com.taphoa.appbanhang.entity.DoanhThu;
import com.taphoa.appbanhang.repository.DoanhThuRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoanhThuService {

    @Autowired
    private DoanhThuRepository doanhThuRepository;

    @Autowired
    private XuatBanService xuatBanService;

    @Autowired
    private NhapService nhapService;

    // Lấy tất cả báo cáo doanh thu
    public List<DoanhThu> findAll() {
        return doanhThuRepository.findAll();
    }
    
    public List<DoanhThu> getAllDoanhThu() {
        return findAll();
    }

    // Lấy báo cáo doanh thu theo ID
    public Optional<DoanhThu> findById(Long id) {
        return doanhThuRepository.findById(id);
    }
    
    public Optional<DoanhThu> getDoanhThuById(Long id) {
        return findById(id);
    }
    
    // Tìm kiếm theo khoảng thời gian
    public List<DoanhThu> findByDateRange(LocalDate tuNgay, LocalDate denNgay) {
        return doanhThuRepository.findByNgayTaoBetween(tuNgay, denNgay);
    }

    // Tạo báo cáo doanh thu cho ngày
    public DoanhThu createDailyReport(LocalDate ngayBaoCao) {
        // Kiểm tra đã có báo cáo cho ngày này chưa
        if (doanhThuRepository.existsByNgayBaoCao(ngayBaoCao)) {
            throw new RuntimeException("Đã có báo cáo doanh thu cho ngày " + ngayBaoCao);
        }

        // Tính toán doanh thu và chi phí
        Double tongDoanhThu = xuatBanService.getTotalRevenueByDate(ngayBaoCao);
        Double tongChiPhi = nhapService.getTotalCostByDate(ngayBaoCao);

        DoanhThu doanhThu = new DoanhThu();
        doanhThu.setNgayBaoCao(ngayBaoCao);
        doanhThu.setTongDoanhThu(tongDoanhThu);
        doanhThu.setTongChiPhi(tongChiPhi);
        
        // Tính toán lợi nhuận và tỷ suất
        doanhThu.tinhToanDoanhThu();

        return doanhThuRepository.save(doanhThu);
    }

    // Tạo báo cáo doanh thu cho khoảng thời gian
    public DoanhThu createPeriodReport(LocalDate startDate, LocalDate endDate) {
        Double tongDoanhThu = xuatBanService.getTotalRevenueBetweenDates(startDate, endDate);
        Double tongChiPhi = nhapService.getTotalCostBetweenDates(startDate, endDate);

        DoanhThu doanhThu = new DoanhThu();
        doanhThu.setNgayBaoCao(endDate); // Sử dụng ngày kết thúc làm ngày báo cáo
        doanhThu.setTongDoanhThu(tongDoanhThu);
        doanhThu.setTongChiPhi(tongChiPhi);
        doanhThu.setGhiChu("Báo cáo từ " + startDate + " đến " + endDate);
        
        // Tính toán lợi nhuận và tỷ suất
        doanhThu.tinhToanDoanhThu();

        return doanhThuRepository.save(doanhThu);
    }

    // Cập nhật báo cáo doanh thu
    public DoanhThu updateDoanhThu(Long id, DoanhThu doanhThuDetails) {
        Optional<DoanhThu> optionalDoanhThu = doanhThuRepository.findById(id);
        if (optionalDoanhThu.isPresent()) {
            DoanhThu doanhThu = optionalDoanhThu.get();
            doanhThu.setTongDoanhThu(doanhThuDetails.getTongDoanhThu());
            doanhThu.setTongChiPhi(doanhThuDetails.getTongChiPhi());
            doanhThu.setGhiChu(doanhThuDetails.getGhiChu());
            
            // Tính toán lại lợi nhuận và tỷ suất
            doanhThu.tinhToanDoanhThu();
            
            return doanhThuRepository.save(doanhThu);
        }
        throw new RuntimeException("Không tìm thấy báo cáo doanh thu với ID: " + id);
    }

    // Xóa báo cáo doanh thu
    public void deleteDoanhThu(Long id) {
        if (doanhThuRepository.existsById(id)) {
            doanhThuRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy báo cáo doanh thu với ID: " + id);
        }
    }

    // Lấy báo cáo theo ngày
    public Optional<DoanhThu> getReportByDate(LocalDate ngayBaoCao) {
        return doanhThuRepository.findByNgayBaoCao(ngayBaoCao);
    }

    // Lấy báo cáo trong khoảng thời gian
    public List<DoanhThu> getReportsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return doanhThuRepository.findByNgayBaoCaoBetween(startDate, endDate);
    }

    // Lấy báo cáo theo tháng
    public List<DoanhThu> getMonthlyReports(int year, int month) {
        return doanhThuRepository.findByYearAndMonth(year, month);
    }

    // Lấy báo cáo theo năm
    public List<DoanhThu> getYearlyReports(int year) {
        return doanhThuRepository.findByYear(year);
    }

    // Tính tổng doanh thu trong khoảng thời gian
    public Double getTotalRevenueBetweenDates(LocalDate startDate, LocalDate endDate) {
        Double total = doanhThuRepository.getTotalRevenueBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }

    // Tính tổng lợi nhuận trong khoảng thời gian
    public Double getTotalProfitBetweenDates(LocalDate startDate, LocalDate endDate) {
        Double total = doanhThuRepository.getTotalProfitBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }

    // Lấy trung bình tỷ suất lợi nhuận
    public Double getAverageProfitMargin(LocalDate startDate, LocalDate endDate) {
        Double average = doanhThuRepository.getAverageProfitMargin(startDate, endDate);
        return average != null ? average : 0.0;
    }

    // Tạo file Excel báo cáo
    public byte[] exportToExcel(LocalDate startDate, LocalDate endDate) throws IOException {
        List<DoanhThu> reports = getReportsBetweenDates(startDate, endDate);
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo cáo Doanh thu");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Ngày báo cáo", "Tổng doanh thu", "Tổng chi phí", "Lợi nhuận", "Tỷ suất lợi nhuận (%)", "Ghi chú"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            
            // Định dạng header
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            cell.setCellStyle(headerStyle);
        }

        // Thêm dữ liệu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int rowNum = 1;
        
        for (DoanhThu doanhThu : reports) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(doanhThu.getNgayBaoCao().format(formatter));
            row.createCell(1).setCellValue(doanhThu.getTongDoanhThu());
            row.createCell(2).setCellValue(doanhThu.getTongChiPhi());
            row.createCell(3).setCellValue(doanhThu.getLoiNhuan());
            row.createCell(4).setCellValue(doanhThu.getTyXuatLoiNhuan());
            row.createCell(5).setCellValue(doanhThu.getGhiChu() != null ? doanhThu.getGhiChu() : "");
        }

        // Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Chuyển thành byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        return outputStream.toByteArray();
    }

    // Xuất báo cáo ra file Excel
    public void exportToExcel(String filePath, LocalDate tuNgay, LocalDate denNgay) throws IOException {
        List<DoanhThu> doanhThuList;
        
        if (tuNgay != null && denNgay != null) {
            doanhThuList = findByDateRange(tuNgay, denNgay);
        } else {
            doanhThuList = findAll();
        }
        
        // Tạo workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo Cáo Doanh Thu");
        
        // Tạo header
        String[] headers = {
            "Loại", "Mô tả", "Ghi chú", "Tổng doanh thu", 
            "Tổng chi phí", "Lợi nhuận", "Ngày báo cáo"
        };
        
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Thêm dữ liệu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int rowNum = 1;
        
        for (DoanhThu doanhThu : doanhThuList) {
            Row row = sheet.createRow(rowNum++);
            
            // DoanhThu entity không có thông tin hàng hóa cụ thể, chỉ có tổng hợp
            row.createCell(0).setCellValue("Tổng hợp");
            row.createCell(1).setCellValue("Báo cáo doanh thu");
            row.createCell(2).setCellValue(""); // Số lượng không áp dụng cho tổng hợp
            row.createCell(3).setCellValue(doanhThu.getTongDoanhThu());
            row.createCell(4).setCellValue(doanhThu.getTongChiPhi());
            row.createCell(5).setCellValue(doanhThu.getLoiNhuan());
            row.createCell(6).setCellValue(doanhThu.getNgayBaoCao().format(formatter));
        }

        // Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi ra file
        java.io.FileOutputStream fileOut = new java.io.FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    // Tạo báo cáo tự động cho hôm nay
    public DoanhThu generateTodayReport() {
        LocalDate today = LocalDate.now();
        
        // Kiểm tra đã có báo cáo chưa
        Optional<DoanhThu> existingReport = getReportByDate(today);
        if (existingReport.isPresent()) {
            // Cập nhật báo cáo hiện có
            DoanhThu doanhThu = existingReport.get();
            doanhThu.setTongDoanhThu(xuatBanService.getTotalRevenueByDate(today));
            doanhThu.setTongChiPhi(nhapService.getTotalCostByDate(today));
            doanhThu.tinhToanDoanhThu();
            return doanhThuRepository.save(doanhThu);
        } else {
            // Tạo báo cáo mới
            return createDailyReport(today);
        }
    }
}
