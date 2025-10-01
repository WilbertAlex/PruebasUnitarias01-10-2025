package pe.edu.upeu.sysalmacen.unidad_medida;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import pe.edu.upeu.sysalmacen.modelo.UnidadMedida;
import pe.edu.upeu.sysalmacen.repositorio.IUnidadMedidaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@Rollback(false) // Descomentar si quieres que los cambios se guarden en la BD real
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test") // Si tuvieras un perfil "test" configurado en application-test.properties
public class IUnidadMedidaRepositoryTest {

    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    private static Long unidadId;

    @BeforeEach
    public void setUp() {
        UnidadMedida unidad = new UnidadMedida();
        unidad.setNombreMedida("Kilogramo");
        UnidadMedida guardada = unidadMedidaRepository.save(unidad);
        unidadId = guardada.getIdUnidad(); // Guardamos el ID para pruebas posteriores
    }

    @Test
    @Order(1)
    public void testGuardarUnidadMedida() {
        UnidadMedida nuevaUnidad = new UnidadMedida();
        nuevaUnidad.setNombreMedida("Litro");
        UnidadMedida guardada = unidadMedidaRepository.save(nuevaUnidad);
        assertNotNull(guardada.getIdUnidad());
        assertEquals("Litro", guardada.getNombreMedida());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<UnidadMedida> unidad = unidadMedidaRepository.findById(unidadId);
        assertTrue(unidad.isPresent());
        assertEquals("Kilogramo", unidad.get().getNombreMedida());
    }

    @Test
    @Order(3)
    public void testActualizarUnidadMedida() {
        UnidadMedida unidad = unidadMedidaRepository.findById(unidadId).orElseThrow();
        unidad.setNombreMedida("Kilogramo (Kg)");
        UnidadMedida actualizada = unidadMedidaRepository.save(unidad);
        assertEquals("Kilogramo (Kg)", actualizada.getNombreMedida());
    }

    @Test
    @Order(4)
    public void testListarUnidadesMedida() {
        List<UnidadMedida> unidades = unidadMedidaRepository.findAll();
        assertFalse(unidades.isEmpty());
        System.out.println("Total unidades de medida registradas: " + unidades.size());
        for (UnidadMedida u : unidades) {
            System.out.println(u.getNombreMedida() + "\t" + u.getIdUnidad());
        }
    }

    @Test
    @Order(5)
    public void testEliminarUnidadMedida() {
        unidadMedidaRepository.deleteById(unidadId);
        Optional<UnidadMedida> eliminada = unidadMedidaRepository.findById(unidadId);
        assertFalse(eliminada.isPresent(), "La unidad de medida debería haber sido eliminada");
    }
}
