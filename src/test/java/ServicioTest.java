import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
class ServicioTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCrearToDo() {
		// 1. ARRANGE (Preparar: construimos los objetos necesarios)
		DBStub db = new DBStub();
		Repositorio repo = new Repositorio(db);
		MailerStub mailer = new MailerStub();
		Servicio servicio = new Servicio(repo, mailer);
		
		LocalDate fechaLimite = LocalDate.now().plusDays(3); // Fecha dentro de 3 días

		// 2. ACT (Actuar: llamamos al método que queremos testar)
		// NOTA: Aquí es donde el programa se va a estrellar porque pusimos la excepción
		boolean resultado = servicio.crearToDo("Aprobar TDD", fechaLimite);

		// 3. ASSERT (Comprobar: verificamos el resultado)
		assertTrue(resultado, "La tarea debería haberse creado correctamente");
	}

}
