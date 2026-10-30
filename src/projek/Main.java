/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek;

import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    private static StudentDAO studentDAO = new StudentDAO();
    private static AttendanceDAO attendanceDAO = new AttendanceDAO();
    private static AttendanceService service = new AttendanceService(studentDAO, attendanceDAO);
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int choice;
        
        while (true) {
            displayMenu();
            choice = getIntInput("Pilih menu: ");
            
            switch (choice) {
                case 1:
                    tambahSiswa();
                    break;
                case 2:
                    lihatDaftarSiswa();
                    break;
                case 3:
                    inputAbsensi();
                    break;
                case 4:
                    lihatLaporanAbsensi();
                    break;
                case 5:
                    hapusSiswa();
                    break;
                case 6:
                    System.out.println("Terima kasih!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid!");
            }
            
            System.out.println("\nTekan Enter untuk lanjut...");
            scanner.nextLine();
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n====== SISTEM ABSENSI ======");
        System.out.println("1. Tambah Siswa");
        System.out.println("2. Lihat Daftar Siswa");
        System.out.println("3. Input Absensi");
        System.out.println("4. Lihat Laporan Absensi");
        System.out.println("5. Hapus Siswa");
        System.out.println("6. Keluar");
        System.out.println("===========================");
    }
    
    private static void tambahSiswa() {
        System.out.println("\n--- TAMBAH SISWA ---");
        System.out.print("Nama Siswa: ");
        String name = scanner.nextLine();
        System.out.print("Kelas: ");
        String className = scanner.nextLine();
        
        service.addStudent(name, className);
        System.out.println("✓ Siswa berhasil ditambahkan!");
    }
    
    private static void lihatDaftarSiswa() {
        System.out.println("\n--- DAFTAR SISWA ---");
        var students = service.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("Tidak ada data siswa.");
            return;
        }
        
        System.out.println(String.format("%-5s %-20s %-10s", "ID", "Nama", "Kelas"));
        System.out.println("--------------------------------------------");
        for (Student s : students) {
            System.out.println(String.format("%-5d %-20s %-10s", 
                s.getStudentId(), s.getName(), s.getClassName()));
        }
    }
    
    private static void inputAbsensi() {
        System.out.println("\n--- INPUT ABSENSI ---");
        
        lihatDaftarSiswa();
        
        System.out.print("\nID Siswa: ");
        int studentId = getIntInput("");
        
        var student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("✗ Siswa tidak ditemukan!");
            return;
        }
        
        System.out.println("\nStatus Absensi:");
        System.out.println("1. HADIR");
        System.out.println("2. IZIN");
        System.out.println("3. SAKIT");
        System.out.println("4. ALPA");
        
        int statusChoice = getIntInput("Pilih status (1-4): ");
        String status;
        
        switch (statusChoice) {
            case 1:
                status = "HADIR";
                break;
            case 2:
                status = "IZIN";
                break;
            case 3:
                status = "SAKIT";
                break;
            case 4:
                status = "ALPA";
                break;
            default:
                System.out.println("✗ Pilihan tidak valid!");
                return;
        }
        
        service.recordAttendance(studentId, status);
        System.out.println("✓ Absensi berhasil dicatat! (" + student.getName() + " - " + status + ")");
    }
    
    private static void lihatLaporanAbsensi() {
        System.out.println("\n--- LAPORAN ABSENSI ---");
        
        lihatDaftarSiswa();
        
        System.out.print("\nID Siswa (0 untuk semua): ");
        int studentId = getIntInput("");
        
        if (studentId == 0) {
            var attendances = attendanceDAO.getAllAttendances();
            if (attendances.isEmpty()) {
                System.out.println("Tidak ada data absensi.");
                return;
            }
            
            System.out.println(String.format("%-5s %-10s %-12s %-10s %-10s", 
                "ID", "Siswa", "Tanggal", "Waktu", "Status"));
            System.out.println("------------------------------------------------------");
            for (Attendance a : attendances) {
                var student = studentDAO.getStudentById(a.getStudentId());
                String studentName = student != null ? student.getName() : "Unknown";
                System.out.println(String.format("%-5d %-10s %-12s %-10s %-10s", 
                    a.getAttendanceId(), studentName, a.getDate(), 
                    a.getTimeIn(), a.getStatus()));
            }
        } else {
            var student = studentDAO.getStudentById(studentId);
            if (student == null) {
                System.out.println("✗ Siswa tidak ditemukan!");
                return;
            }
            
            var attendances = service.getStudentAttendanceReport(studentId);
            if (attendances.isEmpty()) {
                System.out.println("Tidak ada data absensi untuk siswa ini.");
                return;
            }
            
            System.out.println("\nLaporan Absensi: " + student.getName() + " (" + student.getClassName() + ")");
            System.out.println(String.format("%-12s %-10s %-10s", "Tanggal", "Waktu", "Status"));
            System.out.println("--------------------------------------------");
            for (Attendance a : attendances) {
                System.out.println(String.format("%-12s %-10s %-10s", 
                    a.getDate(), a.getTimeIn(), a.getStatus()));
            }
            
            System.out.println("\n--- RINGKASAN ---");
            var summary = service.getAttendanceSummary(studentId);
            summary.forEach((status, count) -> 
                System.out.println(status + ": " + count)
            );
        }
    }
    
    private static void hapusSiswa() {
        System.out.println("\n--- HAPUS SISWA ---");

        lihatDaftarSiswa();

        System.out.print("\nID Siswa yang mau dihapus (0 untuk batal): ");
        int id = getIntInput("");

        if (id == 0) {
            System.out.println("Dibatalkan.");
            return;
        }

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("✗ Siswa dengan ID " + id + " tidak ditemukan!");
            return;
        }

        System.out.print("Yakin hapus \"" + student.getName() + "\" (" + student.getClassName() + ")? (y/n): ");
        String confirm = scanner.nextLine().trim();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Dibatalkan.");
            return;
        }

        attendanceDAO.deleteByStudentId(id);
        studentDAO.deleteStudent(id);
        System.out.println("✓ Siswa \"" + student.getName() + "\" berhasil dihapus!");
    }

    private static int getIntInput(String prompt) {
        try {
            System.out.print(prompt);
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
            return getIntInput(prompt);
        }
    }
}