package com.stefanantal.graprical;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GraduatedPricingCalculator {
  /**
   * Calculates the final price for a number of users with a graduated pricing model.
   * Pricing will be sorted in ascending order by the limit value of each price-limit pair.
   *
   * @param pricing       list of price-limit pairs.
   *                      Has to be an even number of values in the form of:
   *                      price1, limit1, price2, limit2, etc.
   *                      The last price-limit pair contains the price for all remaining users
   *                      above the last limit.
   * @param users         number of users the price shall be calculated for.
   * @param lowFixedPrice if set to true, the lowest price-limit pair is considered a flat price
   *                      if the users are equal or below that pair's limit. If the users are
   *                      above, this pricing pair will be disregarded.
   *                      above, this pricing pair will be disregarded. If set to false, the
   *                      lowest price-limit pair will be treated as the other pairs.
   * @return final price for given users within the pricing model as {@link BigDecimal}.
   * @throws IllegalArgumentException if pricing contains no or an uneven number of values.
   */
  public static BigDecimal calculate(List<Double> pricing, int users, boolean lowFixedPrice)
      throws IllegalArgumentException {

    // Check for invalid arguments and exit early.
    if (pricing.isEmpty()) {
      throw new IllegalArgumentException("At least one pricing pair has to be given.");
    }

    if (pricing.size() % 2 != 0) {
      throw new IllegalArgumentException("One input parameter is missing its pair.");
    }

    // No need to calculate, exit early.
    if (users <= 0) {
      return BigDecimal.valueOf(0);
    }

    List<PricingPair> sortedPricing = sortPricing(pricing);

    BigDecimal result = new BigDecimal(0);

    /*
     Pricing contains flat price-limit pair.
     Flat price of overall users is lower than the flat limit.
     */
    if (lowFixedPrice && users <= sortedPricing.get(0).getLimit()) {
      return BigDecimal.valueOf(sortedPricing.get(0).getPrice());
    }

    for (int i = 0; i < sortedPricing.size(); i++) {
      PricingPair pp = sortedPricing.get(i);

      // Disregard the first pair as it is the flat pricing pair.
      if (lowFixedPrice) {
        lowFixedPrice = false;
        continue;
      }

      // More users left than the current tier limit and not last price-limit pair.
      if (users > pp.getLimit() && i != sortedPricing.size() - 1) {
        users = users - pp.getLimit();
        result = result.add(calcSinglePrice(pp.getPrice(), pp.getLimit()));
      } else {
        result = result.add(calcSinglePrice(pp.getPrice(), users));
        break;
      }
    }
    return result;
  }

  /**
   * Calculates the pricing for a single price by multiplying the price by the limit.
   *
   * @param price first multiplication param.
   * @param limit second multiplication param.
   * @return {@link BigDecimal} as result with a precision set to IEEE 754R Decimal32 format.
   */
  private static BigDecimal calcSinglePrice(double price, int limit) {
    return BigDecimal.valueOf(price).multiply(new BigDecimal(limit), MathContext.DECIMAL32);
  }

  /**
   * Convert a list of numbers into {@link PricingPair} and sort it by its limit value
   * in ascending order.
   *
   * @param pricing mut be not null and have at least two entries. Furthermore, the size must be
   *                an even number.
   * @return sorted list of {@link PricingPair}.
   */
  private static List<PricingPair> sortPricing(List<Double> pricing) {
    List<PricingPair> result = new ArrayList<>();

    for (int i = 0; i < pricing.size() - 1; i = i + 2) {
      result.add(new PricingPair(pricing.get(i), pricing.get(i + 1).intValue()));
    }

    result.sort(Comparator.comparingInt(PricingPair::getLimit));

    return result;
  }
}
