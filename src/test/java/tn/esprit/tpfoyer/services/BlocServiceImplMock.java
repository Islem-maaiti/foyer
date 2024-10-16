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
    public void testRetrieveBloc() {
        // Ajustez le mock pour retourner un bloc spécifique
        Mockito.when(blocRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1L); // Remplacez "2" par un ID existant

        Assertions.assertNotNull(retrievedBloc);

    }
}
