package com.taphoa.appbanhang;

import com.formdev.flatlaf.FlatLightLaf;
import com.taphoa.appbanhang.ui.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class AppBanHangApplication implements CommandLineRunner {

    private static ConfigurableApplicationContext context;
    
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        // Thiết lập Look and Feel hiện đại
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            // Fallback to system look and feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Thiết lập system properties cho Swing
        System.setProperty("java.awt.headless", "false");
        
        // Khởi động Spring Boot context
        context = SpringApplication.run(AppBanHangApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Method này sẽ được gọi sau khi Spring context khởi tạo xong
        // Lấy MainFrame từ injected ApplicationContext
        MainFrame mainFrame = applicationContext.getBean(MainFrame.class);
        
        // Khởi động UI trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Lỗi khởi động giao diện: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
