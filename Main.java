import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/atletas", "root", "admin")) {

            System.out.println("Conexión exitosa a MariaDB");

            // Nombre
            String nombre;
            do {
                System.out.print("Ingrese el nombre: ");
                nombre = sc.nextLine().trim();
                if (nombre.isEmpty()) System.out.println("El nombre no puede estar vacío");
            } while (nombre.isEmpty());
            // Apellidos
            String apellidos;
            do {
                System.out.print("Ingrese los apellidos: ");
                apellidos = sc.nextLine().trim();
                if (apellidos.isEmpty()) System.out.println("Los apellidos no pueden estar vacíos");
            } while (apellidos.isEmpty());

            // Fecha de nacimiento
            String fechaNacimiento;
            do {
                System.out.print("Ingrese la fecha de nacimiento (YYYY-MM-DD): ");
                fechaNacimiento = sc.nextLine().trim();
                if (fechaNacimiento.isEmpty()) System.out.println("Fecha de nacimiento requerida");
            } while (fechaNacimiento.isEmpty());

            // Sexo
            String sexo;
            do {
                System.out.print("Ingrese el sexo (M/F): ");
                sexo = sc.nextLine().trim().toUpperCase();
                if (!sexo.equals("M") && !sexo.equals("F")) System.out.println("Sexo inválido, solo M o F");
            } while (!sexo.equals("M") && !sexo.equals("F"));

            // Dirección
            String direccion;
            System.out.print("Ingrese la dirección: ");
            direccion = sc.nextLine().trim();

            // Teléfono
            String telefono;
            do {
                System.out.print("Ingrese el teléfono (máx 8 dígitos): ");
                telefono = sc.nextLine().trim();
                if (!telefono.matches("\\d{1,8}")) {
                    System.out.println("Teléfono inválido, solo números hasta 8 dígitos");
                    telefono = "";
                }
            } while (telefono.isEmpty());

            // Gmail
            String gmail;
            do {
                System.out.print("Ingrese el correo (máx 40 caracteres): ");
                gmail = sc.nextLine().trim();
                if (gmail.length() > 40) {
                    System.out.println("Gmail demasiado largo. Recortando a 40 caracteres.");
                    gmail = gmail.substring(0, 40);
                }
                if (gmail.isEmpty()) System.out.println("Gmail no puede estar vacío");
            } while (gmail.isEmpty());
            // Insertar en la base de datos
            String sql = "INSERT INTO controlatletas " +
                    "(nombre, apellidos, fecha_nacimiento, sexo, direccion, telefono, gmail) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, apellidos);
                stmt.setString(3, fechaNacimiento);
                stmt.setString(4, sexo);
                stmt.setString(5, direccion);
                stmt.setString(6, telefono);
                stmt.setString(7, gmail);
                stmt.executeUpdate();
                System.out.println("Registro insertado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}