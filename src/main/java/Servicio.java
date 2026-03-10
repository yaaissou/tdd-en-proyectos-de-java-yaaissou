import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que actúa como servicio de lógica de negocio.
 * Se encarga de gestionar las tareas, interactuar con el repositorio y enviar notificaciones.
 */
public class Servicio {
    private Repositorio repositorio;
    private MailerStub mailer;

    /**
     * Constructor de la clase Servicio.
     * * @param repositorio El repositorio donde se almacenan y consultan los datos.
     * @param mailer El servicio (stub) encargado de enviar los correos electrónicos.
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
        compruebaFechasYAlertar(); // Comprueba tras añadir
        return true;
    }

    /**
     * Añade una nueva dirección de correo electrónico al sistema de alertas.
     * * @param email La dirección de correo electrónico a añadir.
     * @return true si el formato del email es válido, false en caso contrario.
     */
    public boolean agregarEmail(String email) {
        if (email == null || !email.contains("@")) return false; // Validación
        repositorio.almacenarEmail(email);
        compruebaFechasYAlertar(); // Comprueba tras añadir
        return true;
    }

    /**
     * Marca una tarea existente como finalizada en el sistema.
     * * @param nombre El nombre exacto de la tarea a marcar.
     * @return true si la operación tuvo éxito, false si falló (ej. no existe la tarea).
     */
    public boolean marcarFinalizada(String nombre) {
        boolean exito = repositorio.marcarCompletado(nombre);
        compruebaFechasYAlertar(); // Comprueba tras consultar
        return exito;
    }

    /**
     * Consulta y devuelve una lista con todas las tareas que aún no están completadas.
     * * @return Una lista de objetos ToDo que representan las tareas pendientes.
     */
    public List<ToDo> consultarPendientes() {
        compruebaFechasYAlertar(); // Comprueba tras consultar
        List<ToDo> pendientes = new ArrayList<>();
        for (ToDo tarea : repositorio.obtenerTodasLasTareas()) {
            if (!tarea.isCompletado()) {
                pendientes.add(tarea);
            }
        }
        return pendientes;
    }

    /**
     * Método interno que comprueba si existen tareas cuya fecha límite es anterior a hoy.
     * Si encuentra alguna, envía un correo de alerta a todas las direcciones registradas.
     */
    private void compruebaFechasYAlertar() {
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