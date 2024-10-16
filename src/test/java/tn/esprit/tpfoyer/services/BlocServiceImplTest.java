package tn.esprit.tpfoyer.services;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
public class BlocServiceImplTest {
    @Autowired
    IBlocService iBlocService;

    @Test
    @Order(1)
    public void testRetrieveAllBlocs() {

        List<Bloc> result = iBlocService.retrieveAllBlocs();
        Assertions.assertEquals(0, result.size());
    }

}
