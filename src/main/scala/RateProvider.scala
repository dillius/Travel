class RateProvider {
  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]) : Seq[BestGroupPrice] = {
    prices
      .map(price => (price, rates.find(rate => rate.rateCode == price.rateCode)))
      .filter(priceRate => priceRate._2.isDefined)
      .groupBy(priceRate => (priceRate._1.cabinCode, priceRate._2.get.rateGroup))
      .map {
        case (key: (String, String), priceRates: Seq[(CabinPrice, Option[Rate])]) =>
          val minPriceRate = priceRates.minBy(priceRate => priceRate._1.price);
          BestGroupPrice(key._1, minPriceRate._2.get.rateCode, minPriceRate._1.price, key._2 )}
      .toSeq
      .sortBy(bgp => bgp.cabinCode + bgp.rateCode) //Order by done just to make the output exactly match, may not be necessary
  }
}

case class Rate(rateCode: String, rateGroup: String)

case class CabinPrice(cabinCode: String, rateCode: String, price: BigDecimal)

case class BestGroupPrice(cabinCode: String, rateCode: String, price: BigDecimal, rateGroup: String)