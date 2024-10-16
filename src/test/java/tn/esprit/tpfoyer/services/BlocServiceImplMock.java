package tn.esprit.tpfoyer.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@TestMethodOrder(MethodOrderer.class)
@ExtendWith(MockitoExtension.class)
public class BlocServiceImplMock {

    @Mock
    BlocRepository blocRepository;

    @InjectMocks
    BlocServiceImpl blocService;

    Bloc bloc = new Bloc("1", 50); // Ajoutez un point-virgule ici

    List<Bloc> listBlocs = new ArrayList<Bloc>() {{
        add(new Bloc("Bloc A", 50)); // Foyer laissé vide
        add(new Bloc("Bloc B", 30)); // Foyer laissé vide
        add(new Bloc("Bloc C", 40)); // Foyer laissé vide
    }};

    @Test
    @Order(1)
    public void testRetrieveBloc() {
        // Ajustez le mock pour retourner un bloc spécifique
        Mockito.when(blocRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1L); // Remplacez "2" par un ID existant

        Assertions.assertNotNull(retrievedBloc);

    }


    @Test
    @Order(2)
    public void testRetrieveAllBlocs() {
        // Arrange: Mocking the behavior of the repository
        Mockito.when(blocRepository.findAll()).thenReturn(listBlocs);

        // Act: Calling the service method
        List<Bloc> listB = blocService.retrieveAllBlocs();

        // Assert: Verifying the size of the returned list
        Assertions.assertEquals(3, listB.size()); // Assurez-vous que listBlocs contient 3 blocs
    }

    @Test
    @Order(3)

    public void testAddBloc() {
        Mockito.when(blocRepository.save(Mockito.any(Bloc.class))).thenAnswer(invocation -> {
            Bloc savedBloc = invocation.getArgument(0);
            listBlocs.add(savedBloc);
            return savedBloc;
        });

        Bloc newBloc = new Bloc();
        newBloc.setNomBloc("Bloc D");
        newBloc.setCapaciteBloc(5);
        blocService.addBloc(newBloc);

        Assertions.assertEquals(4, listBlocs.size());
    }

    @Test
    @Order(4)
    public void testRemoveBloc() {
        Bloc blocToRemove = new Bloc("Bloc E", 20);
        listBlocs.add(blocToRemove);

        Mockito.doNothing().when(blocRepository).deleteById(Mockito.anyLong());

        blocService.removeBloc(blocToRemove.getIdBloc());
        Assertions.assertEquals(  4, listBlocs.size());

    }

    @Test
    @Order(5)
    public void testRetrieveBlocsSelonCapacite() {
        List<Bloc> expectedBlocs = new ArrayList<>() {{
            add(new Bloc("Bloc A", 50));
        }};

        // Mock le comportement pour renvoyer des blocs ayant une capacité >= 40
        Mockito.when(blocRepository.findAll()).thenReturn(listBlocs);

        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(40);

        Assertions.assertEquals(expectedBlocs.size(), result.size(), "Le nombre de blocs retournés doit correspondre à la capacité minimale.");
        Assertions.assertTrue(result.containsAll(expectedBlocs), "La liste des blocs retournés doit contenir les blocs attendus.");
    }


    @Test
    @Order(6)
    public void testModifyBloc() {
        // Préparez un bloc à modifier
        Bloc blocToModify = new Bloc("Bloc A", 50);
        blocToModify.setIdBloc(1L); // Assurez-vous d'assigner un ID

        // Mock le comportement pour retourner le bloc modifié
        Mockito.when(blocRepository.save(Mockito.any(Bloc.class))).thenReturn(blocToModify);

        // Appelez la méthode de modification
        Bloc result = blocService.modifyBloc(blocToModify);

        // Vérifiez que le résultat est le bloc modifié
        Assertions.assertNotNull(result, "Le bloc modifié ne doit pas être nul.");
        Assertions.assertEquals("Bloc A", result.getNomBloc(), "Le nom du bloc modifié doit être 'Bloc A'.");
        Assertions.assertEquals(50, result.getCapaciteBloc(), "La capacité du bloc modifié doit être 50.");
    }

    @Test
    @Order(7)
    public void testTrouverBlocsParNomEtCap() {
        // Mock le comportement pour renvoyer des blocs selon un nom et une capacité
        String nomRecherche = "Bloc A";
        long capaciteRecherche = 50;

        List<Bloc> expectedBlocs = new ArrayList<>() {{
            add(new Bloc(nomRecherche, capaciteRecherche));
        }};

        Mockito.when(blocRepository.findAllByNomBlocAndCapaciteBloc(nomRecherche, capaciteRecherche)).thenReturn(expectedBlocs);

        List<Bloc> result = blocService.trouverBlocsParNomEtCap(nomRecherche, capaciteRecherche);

        Assertions.assertEquals(expectedBlocs.size(), result.size(), "Le nombre de blocs retournés doit correspondre à ceux filtrés par nom et capacité.");
        Assertions.assertTrue(result.containsAll(expectedBlocs), "La liste des blocs retournés doit contenir les blocs attendus.");
    }
    @Test
    @Order(8)
    public void testTrouverBlocsSansFoyer() {
        // Mock le comportement pour renvoyer des blocs sans foyer
        List<Bloc> expectedBlocs = new ArrayList<>() {{
            add(new Bloc("Bloc A", 50)); // Bloc sans foyer
            add(new Bloc("Bloc B", 30)); // Bloc sans foyer
        }};

        Mockito.when(blocRepository.findAllByFoyerIsNull()).thenReturn(expectedBlocs);

        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        Assertions.assertEquals(expectedBlocs.size(), result.size(), "Le nombre de blocs retournés doit correspondre à ceux sans foyer.");
        Assertions.assertTrue(result.containsAll(expectedBlocs), "La liste des blocs retournés doit contenir les blocs attendus.");
    }





}
