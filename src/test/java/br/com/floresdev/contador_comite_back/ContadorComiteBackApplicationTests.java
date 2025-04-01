package br.com.floresdev.contador_comite_back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "api.security.token.secret=7cdlcqSgFITok+LwWCsXIVWVJojeR78G/Yz9pKlunRU="
})
class ContadorComiteBackApplicationTests {

    @Test
    void contextLoads() {
    }

}
