package com.udacity.pricing.api;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PricingService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;


/**
 * Implements testing of the PricingController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PricingControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PricingService pricingService;

  @Test
  public void get() throws Exception {
    Price price = new Price("USD", new BigDecimal("9250.48"), 1L);
    BDDMockito.given(pricingService.getPrice(ArgumentMatchers.anyLong())).willReturn(price);

    mvc.perform(MockMvcRequestBuilders.get(new URI("/services/price"))
            .param("vehicleId", "1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
        .andExpect(MockMvcResultMatchers.jsonPath("$.currency", Matchers.is("USD")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(9250.48)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.vehicleId", Matchers.is(1)));

  }
}
