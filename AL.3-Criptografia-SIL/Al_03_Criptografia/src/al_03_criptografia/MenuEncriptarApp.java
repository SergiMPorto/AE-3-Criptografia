package al_03_criptografia;


import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MenuEncriptarApp  {
    private static final int MAX_INTENTOS = 3;
    private static final Map<String, String> usuarios = new HashMap<>();

    public static void main(String[] args) {
        /*
         * CREAMOS LOS USUSRIOS CON LAS CONTRASEÑAS
         */
        usuarios.put("Anton", hashearContrasena("contrasena1"));
        usuarios.put("Sergio", hashearContrasena("contrasena2"));
        usuarios.put("Silvian", hashearContrasena("contrasena3"));

        /*
         * PARA MOSTRAR EL MENU PEDIMOS LOS DATOS AL USUARIO: NOMBRE DEL USUARIO Y CONTTRASEÑA
         */
        boolean autenticado = autenticarUsuario();
        if (!autenticado) {
            System.out.println("HAS SUPERADO EL NUMERO DE INTENTOS! APLICACION DETENIDA.");
            return;
        }

      /*
       * CREAMOS EL MENU QUE SE MONSTRARA AL INTRODCUIR BIEN LOS DATOS  DEL  USUARIO Y CONTRASEÑA 
       */
        int opcion;
        String fraseEncriptada = null;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("              ★ ★ ★ MENÚ ★ ★ ★                          ");
            System.out.println("1.            SALIR DEL PROGRAMA                          ");
            System.out.println("2.            ENCRIPTAR LA FRASE                          ");
            System.out.println("3.           DESENCRIPATR LA FRASE                        ");
            System.out.println(" PARA INICAR EL PROCESO SELECCIONA UNA OPCION.GRACIAS.    ");

            opcion = scanner.nextInt();
            scanner.nextLine();  // SALTO DE LINEA 
            switch (opcion) {
                case 1:
                    System.out.println("¡HASTA LUEGO !");
                    break;
                case 2:
                    System.out.print(" INTRODUZCA LA FRASE DE ENCRIPTAR: ");
                    String frase = scanner.nextLine();
                    fraseEncriptada = encriptarFrase(frase);
                    System.out.println("FRASE ENCRIPTADA CORRECTAMENTE! ");
                    break;
                case 3:
                    if (fraseEncriptada == null) {
                        System.out.println(" NO HAY FRASE ENCRIPTADA! ");
                    } else {
                        String fraseDesencriptada = desencriptarFrase(fraseEncriptada);
                        System.out.println("FRASE ENCRIPTADA: " + fraseDesencriptada);
                    }
                    break;
                default:
                    System.out.println("LA OPCIÓN QUE HAS INTRODUCIDO NO ESTA CORRECTA. POR FAVOR INTENTA DE NUEVO!");
            }

        } while (opcion != 1);

        scanner.close();
    }

    private static boolean autenticarUsuario() {
        Scanner scanner = new Scanner(System.in);

        for (int intentos = 0; intentos < MAX_INTENTOS; intentos++) {
            System.out.print("INTRODUZCA EL NOMBRE DEL USUARIO: ");
            String usuario = scanner.nextLine();

            System.out.print("INTRODUZCA LA CONTRASEÑA: ");
            String contrasena = scanner.nextLine();

            if (validarCredenciales(usuario, contrasena)) {
                System.out.println(" BIENVENIDO " + usuario + "!");
                return true;
            } else {
                System.out.println("CREDENCIALES INCORRECTAS. INTENTO   " + (intentos + 1) + " de " + MAX_INTENTOS);
            }
        }

        return false;
   
    }

    private static boolean validarCredenciales(String usuario, String contrasena) {
        String contrasenaHasheada = usuarios.get(usuario);
        return contrasenaHasheada != null && contrasenaHasheada.equals(hashearContrasena(contrasena));
    }

    private static String encriptarFrase(String frase) {
      //TENEOMS QUE AÑADIR EL METODO DE ENCRIPTAR
        return frase.toUpperCase();
    }

    private static String desencriptarFrase(String fraseEncriptada) {
       // TENEMOS QUE ÑADIR EL METODO DE DESENCRIPTAR
    	
        return fraseEncriptada.toLowerCase();
    }

    private static String hashearContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(contrasena.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}