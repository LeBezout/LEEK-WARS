package com.leekwars.utils.fastgarden.impl;

import com.leekwars.utils.fastgarden.FastGardenParam;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Created by ble on 08/07/17.
 */
class PropertiesFastGardenParamImplTest {

    @Test
    void test_chargement() throws IOException {
        final FastGardenParam lParams = new PropertiesFastGardenParamImpl("/fastgarden.properties");
        System.out.println(lParams);
    }
}
