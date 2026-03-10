import java.time.LocalDate;

/**
 * Representa una tarea (ToDo) dentro del sistema.
 * Contiene información sobre el nombre, la descripción, su fecha límite de finalización y su estado.
 */
public class ToDo {
    private String nombre;
    private String descripcion;
    private LocalDate fechaLimite;
    private boolean completado;
    /**
     * Constructor de la clase ToDo.
     * * @param nombre El nombre identificativo de la tarea.
     * @param descripcion La descripción detallada de la tarea.
     * @param fechaLimite La fecha tope para completar la tarea.
     */

    public ToDo(String nombre, String descripcion, LocalDate fechaLimite) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.completado = false; // Por defecto, una tarea no está completada
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }
}