import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Servicio {
    private Repositorio repositorio;
    private MailerStub mailer;

    /**
     * Clase principal que actúa como servicio de lógica de negocio.
     * Se encarga de gestionar las tareas, interactuar con el repositorio y enviar notificaciones.
     */
    public Servicio(Repositorio repositorio, MailerStub mailer) {
        this.repositorio = repositorio;
        this.mailer = mailer;
    }

    /**
     * Crea una nueva tarea y la almacena en el repositorio.
     * Tras añadirla, comprueba si hay tareas vencidas para alertar a los usuarios.
     * * @param nombre El nombre de la nueva tarea.
     * @param fechaLimite La fecha tope en la que debe completarse.
     * @return true si la tarea se creó con éxito, false si el nombre es nulo o vacío.
     */
    public boolean crearToDo(String nombre, LocalDate fechaLimite) {
        if (nombre == null || nombre.trim().isEmpty()) return false; // Validación
        ToDo nuevaTarea = new ToDo(nombre, "Sin descripción", fechaLimite);
        repositorio.almacenarToDo(nuevaTarea);
        comprobarFechasYAlertar(); // Comprueba tras añadir
        return true;
    }

    public boolean agregarEmail(String email) {
        if (email == null || !email.contains("@")) return false; // Validación
        repositorio.almacenarEmail(email);
        comprobarFechasYAlertar(); // Comprueba tras añadir
        return true;
    }

    public boolean marcarFinalizada(String nombre) {
        boolean exito = repositorio.marcarCompletado(nombre);
        comprobarFechasYAlertar(); // Comprueba tras consultar
        return exito;
    }

    public List<ToDo> consultarPendientes() {
        comprobarFechasYAlertar(); // Comprueba tras consultar
        List<ToDo> pendientes = new ArrayList<>();
        for (ToDo tarea : repositorio.obtenerTodasLasTareas()) {
            if (!tarea.isCompletado()) {
                pendientes.add(tarea);
            }
        }
        return pendientes;
    }

    private void comprobarFechasYAlertar() {
        LocalDate hoy = LocalDate.now();
        boolean hayVencidas = false;

        for (ToDo tarea : repositorio.obtenerTodasLasTareas()) {
            if (!tarea.isCompletado() && tarea.getFechaLimite().isBefore(hoy)) {
                hayVencidas = true;
                break;
            }
        }

        if (hayVencidas) {
            for (String email : repositorio.obtenerEmails()) {
                mailer.enviarCorreo(email, "¡Alerta! Tienes tareas pendientes con fecha límite pasada.");
            }
        }
    }
}