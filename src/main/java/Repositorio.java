import java.util.HashSet;
import java.util.List;

public class Repositorio {
    private DBStub db;

    public Repositorio(DBStub db) {
        this.db = db;
    }

    public ToDo encontrarToDo(String nombre) {
        return db.obtenerTarea(nombre);
    }

    public boolean marcarCompletado(String nombre) {
        ToDo tarea = db.obtenerTarea(nombre);
        if (tarea != null) {
            tarea.setCompletado(true);
            db.actualizarTarea(tarea);
            return true;
        }
        return false;
    }

    public boolean almacenarToDo(ToDo todo) {
        db.guardarTarea(todo);
        return true;
    }

    public boolean almacenarEmail(String email) {
        db.guardarEmail(email);
        return true;
    }
    
    public List<ToDo> obtenerTodasLasTareas() { return db.obtenerTodasLasTareas(); }
    public HashSet<String> obtenerEmails() { return db.obtenerEmails(); }
}