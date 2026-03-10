public class MailerStub {
    public boolean enviarCorreo(String direccion, String mensaje) {
        System.out.println("Enviando EMAIL a [" + direccion + "]: " + mensaje);
        return true; // Confirmación de éxito
    }
}