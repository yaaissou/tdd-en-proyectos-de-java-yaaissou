import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DBStub {
    private HashMap<String, ToDo> tareas = new HashMap<>();
    private HashSet<String> emails = new HashSet<>();

    public void guardarTarea(ToDo tarea) { tareas.put(tarea.getNombre(), tarea); }
    public ToDo obtenerTarea(String nombre) { return tareas.get(nombre); }
    public void actualizarTarea(ToDo tarea) { tareas.put(tarea.getNombre(), tarea); }
    public void borrarTarea(String nombre) { tareas.remove(nombre); }
    public List<ToDo> obtenerTodasLasTareas() { return new ArrayList<>(tareas.values()); }

    public void guardarEmail(String email) { emails.add(email); }
    public HashSet<String> obtenerEmails() { return emails; }
}