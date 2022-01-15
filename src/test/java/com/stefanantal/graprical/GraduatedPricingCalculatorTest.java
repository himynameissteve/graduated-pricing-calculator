package com.stefanantal.graprical;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraduatedPricingCalculatorTest {

  @Test
  void calculate_lowFixedPriceTrue_test() {
    BigDecimal result =
        GraduatedPricingCalculator.calculate(List.of(7.0, 100.0, 5.0, 150.0, 1.10, 250.0, 10.0, 10.0), 1, true);

    Assertions.assertEquals(10.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(7.0, 100.0, 5.0, 150.0, 1.10, 250.0, 10.0, 10.0), 10, true);

    Assertions.assertEquals(10.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(7.0, 100.0, 5.0, 150.0, 1.10, 250.0, 10.0, 10.0), 100, true);

    Assertions.assertEquals(700.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(7.0, 100.0, 5.0, 150.0, 1.10, 250.0, 10.0, 10.0), 123, true);

    Assertions.assertEquals(815.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(7.0, 100.0, 5.0, 150.0, 1.10, 250.0, 10.0, 10.0), 1234, true);

    Assertions.assertEquals(2532.4, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(7.0, 100.0, 5.0, 150.0, 1.10, 250.0, 10.0, 10.0), 0, true);

    Assertions.assertEquals(0.0, result.doubleValue());
  }

  @Test
  void calculate_lowFixedPriceFalse_test() {
    BigDecimal result = GraduatedPricingCalculator.calculate(List.of(5.0, 150.0, 1.10, 250.0, 7.0, 100.0), 1, false);

    Assertions.assertEquals(7.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(5.0, 150.0, 1.10, 250.0, 7.0, 100.0), 10, false);

    Assertions.assertEquals(70.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(5.0, 150.0, 1.10, 250.0, 7.0, 100.0), 100, false);

    Assertions.assertEquals(700.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(5.0, 150.0, 1.10, 250.0, 7.0, 100.0), 123, false);

    Assertions.assertEquals(815.0, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(5.0, 150.0, 1.10, 250.0, 7.0, 100.0), 1234, false);

    Assertions.assertEquals(2532.4, result.doubleValue());

    result = GraduatedPricingCalculator.calculate(List.of(5.0, 150.0, 1.10, 250.0, 7.0, 100.0), 0, false);

    Assertions.assertEquals(0.0, result.doubleValue());
  }

  @Test
  void calculate_emptyPricing_test() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(), 100, true), "At least one pricing pair has to be given.");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(), 100, false), "At least one pricing pair has to be given.");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(), 0, true), "At least one pricing pair has to be given.");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(), 0, false), "At least one pricing pair has to be given.");
  }

  @Test
  void calculate_oddSizeOfPricing_test() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(1.0), 100, true), "One input parameter is missing its pair.");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(1.0), 100, false), "One input parameter is missing its pair.");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(1.0), 0, true), "One input parameter is missing its pair.");

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> GraduatedPricingCalculator.calculate(List.of(1.0), 0, false), "One input parameter is missing its pair.");
  }
}
