package projek;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import projek.Attendance;
import projek.AttendanceDAO;
import projek.AttendanceService;
import projek.Student;
import projek.StudentDAO;

public class Main {
    private static StudentDAO studentDAO = SpringContext.get(StudentDAO.class);
    private static AttendanceDAO attendanceDAO = SpringContext.get(AttendanceDAO.class);
    private static AttendanceService service = SpringContext.get(AttendanceService.class);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            Main.displayMenu();
            int choice = Main.getIntInput("Pilih menu: ");
            switch (choice) {
                case 1: {
                    Main.tambahSiswa();
                    break;
                }
                case 2: {
                    Main.lihatDaftarSiswa();
                    break;
                }
                case 3: {
                    Main.inputAbsensi();
                    break;
                }
                case 4: {
                    Main.lihatLaporanAbsensi();
                    break;
                }
                case 5: {
                    Main.hapusSiswa();
                    break;
                }
                case 6: {
                    System.out.println("Terima kasih!");
                    scanner.close();
                    System.exit(0);
                }
                default: {
                    System.out.println("Pilihan tidak valid!");
                }
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
        System.out.println("\u2713 Siswa berhasil ditambahkan!");
    }

    private static void lihatDaftarSiswa() {
        System.out.println("\n--- DAFTAR SISWA ---");
        List<Student> students = service.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Tidak ada data siswa.");
            return;
        }
        System.out.println(String.format("%-5s %-20s %-10s", "ID", "Nama", "Kelas"));
        System.out.println("--------------------------------------------");
        for (Student s : students) {
            System.out.println(String.format("%-5d %-20s %-10s", s.getStudentId(), s.getName(), s.getClassName()));
        }
    }

    private static void inputAbsensi() {
        String status;
        System.out.println("\n--- INPUT ABSENSI ---");
        Main.lihatDaftarSiswa();
        System.out.print("\nID Siswa: ");
        int studentId = Main.getIntInput("");
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("\u2717 Siswa tidak ditemukan!");
            return;
        }
        System.out.println("\nStatus Absensi:");
        System.out.println("1. HADIR");
        System.out.println("2. IZIN");
        System.out.println("3. SAKIT");
        System.out.println("4. ALPA");
        int statusChoice = Main.getIntInput("Pilih status (1-4): ");
        switch (statusChoice) {
            case 1: {
                status = "HADIR";
                break;
            }
            case 2: {
                status = "IZIN";
                break;
            }
            case 3: {
                status = "SAKIT";
                break;
            }
            case 4: {
                status = "ALPA";
                break;
            }
            default: {
                System.out.println("\u2717 Pilihan tidak valid!");
                return;
            }
        }
        service.recordAttendance(studentId, status);
        System.out.println("\u2713 Absensi berhasil dicatat! (" + student.getName() + " - " + status + ")");
    }

    private static void lihatLaporanAbsensi() {
        System.out.println("\n--- LAPORAN ABSENSI ---");
        Main.lihatDaftarSiswa();
        System.out.print("\nID Siswa (0 untuk semua): ");
        int studentId = Main.getIntInput("");
        if (studentId == 0) {
            List<Attendance> attendances = attendanceDAO.getAllAttendances();
            if (attendances.isEmpty()) {
                System.out.println("Tidak ada data absensi.");
                return;
            }
            System.out.println(String.format("%-5s %-10s %-12s %-10s %-10s", "ID", "Siswa", "Tanggal", "Waktu", "Status"));
            System.out.println("------------------------------------------------------");
            for (Attendance a : attendances) {
                Student student = studentDAO.getStudentById(a.getStudentId());
                String studentName = student != null ? student.getName() : "Unknown";
                System.out.println(String.format("%-5d %-10s %-12s %-10s %-10s", a.getAttendanceId(), studentName, a.getDate(), a.getTimeIn(), a.getStatus()));
            }
        } else {
            Student student = studentDAO.getStudentById(studentId);
            if (student == null) {
                System.out.println("\u2717 Siswa tidak ditemukan!");
                return;
            }
            List<Attendance> attendances = service.getStudentAttendanceReport(studentId);
            if (attendances.isEmpty()) {
                System.out.println("Tidak ada data absensi untuk siswa ini.");
                return;
            }
            System.out.println("\nLaporan Absensi: " + student.getName() + " (" + student.getClassName() + ")");
            System.out.println(String.format("%-12s %-10s %-10s", "Tanggal", "Waktu", "Status"));
            System.out.println("--------------------------------------------");
            for (Attendance a : attendances) {
                System.out.println(String.format("%-12s %-10s %-10s", a.getDate(), a.getTimeIn(), a.getStatus()));
            }
            System.out.println("\n--- RINGKASAN ---");
            Map<String, Integer> summary = service.getAttendanceSummary(studentId);
            summary.forEach((status, count) -> System.out.println(status + ": " + count));
        }
    }

    private static void hapusSiswa() {
        System.out.println("\n--- HAPUS SISWA ---");
        Main.lihatDaftarSiswa();
        System.out.print("\nID Siswa yang mau dihapus (0 untuk batal): ");
        int id = Main.getIntInput("");
        if (id == 0) {
            System.out.println("Dibatalkan.");
            return;
        }
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("\u2717 Siswa dengan ID " + id + " tidak ditemukan!");
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
        System.out.println("\u2713 Siswa \"" + student.getName() + "\" berhasil dihapus!");
    }

    private static int getIntInput(String prompt) {
        try {
            System.out.print(prompt);
            return Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka!");
            return Main.getIntInput(prompt);
        }
    }
}

