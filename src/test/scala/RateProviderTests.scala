import org.scalatest.FlatSpec

class RateProviderTests extends FlatSpec {

  behavior of "Rate Provider"

  it should "return the best prices" in {
    val rateProvider = new RateProvider()
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("M2", "Military"),
      Rate("S1", "Senior"),
      Rate("S2", "Senior")
    )
    val cabinPrices = Seq(
      CabinPrice("CA", "M1", 200.00),
      CabinPrice("CA", "M2", 250.00),
      CabinPrice("CA", "S1", 225.00),
      CabinPrice("CA", "S2", 260.00),
      CabinPrice("CB", "M1", 230.00),
      CabinPrice("CB", "M2", 260.00),
      CabinPrice("CB", "S1", 245.00),
      CabinPrice("CB", "S2", 270.00),
    )

    val expected = Seq(
      BestGroupPrice("CA", "M1", 200.00, "Military"),
      BestGroupPrice("CA", "S1", 225.00, "Senior"),
      BestGroupPrice("CB", "M1", 230.00, "Military"),
      BestGroupPrice("CB", "S1", 245.00, "Senior"),
    )

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }

  it should "return the best prices 2" in {
    val rateProvider = new RateProvider()
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("M2", "Military"),
      Rate("S1", "Senior"),
      Rate("S2", "Senior")
    )
    val cabinPrices = Seq(
      CabinPrice("CA", "M1", 300.00),
      CabinPrice("CA", "M2", 250.00),
      CabinPrice("CA", "S1", 325.00),
      CabinPrice("CA", "S2", 260.00),
      CabinPrice("CB", "M1", 330.00),
      CabinPrice("CB", "M2", 260.00),
      CabinPrice("CB", "S1", 345.00),
      CabinPrice("CB", "S2", 270.00),
    )

    val expected = Seq(
      BestGroupPrice("CA", "M2", 250.00, "Military"),
      BestGroupPrice("CA", "S2", 260.00, "Senior"),
      BestGroupPrice("CB", "M2", 260.00, "Military"),
      BestGroupPrice("CB", "S2", 270.00, "Senior"),
    )

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }

  it should "handle empty rates and prices" in {
    val rateProvider = new RateProvider()
    val rates = Seq()
    val cabinPrices = Seq()

    val expected = Seq()

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }

  it should "handle empty rates" in {
    val rateProvider = new RateProvider()
    val rates = Seq()
    val cabinPrices = Seq(
      CabinPrice("CA", "M1", 300.00),
      CabinPrice("CA", "M2", 250.00),
      CabinPrice("CA", "S1", 325.00),
      CabinPrice("CA", "S2", 260.00),
      CabinPrice("CB", "M1", 330.00),
      CabinPrice("CB", "M2", 260.00),
      CabinPrice("CB", "S1", 345.00),
      CabinPrice("CB", "S2", 270.00),
    )

    val expected = Seq()

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }

  it should "handle empty prices" in {
    val rateProvider = new RateProvider()
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("M2", "Military"),
      Rate("S1", "Senior"),
      Rate("S2", "Senior")
    )
    val cabinPrices = Seq()

    val expected = Seq()

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }

  it should "ignore rates without pricing" in {
    val rateProvider = new RateProvider()
    val rates = Seq(
      Rate("M1", "Military"),
      Rate("M2", "Military"),
      Rate("S1", "Senior"),
      Rate("S2", "Senior")
    )
    val cabinPrices = Seq(
      CabinPrice("CA", "M1", 200.00),
      CabinPrice("CA", "M2", 250.00),
      CabinPrice("CB", "M1", 230.00),
      CabinPrice("CB", "M2", 260.00),
    )

    val expected = Seq(
      BestGroupPrice("CA", "M1", 200.00, "Military"),
      BestGroupPrice("CB", "M1", 230.00, "Military"),
    )

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }

  it should "ignore pricing without rates" in {
    val rateProvider = new RateProvider()
    val rates = Seq(
      Rate("S1", "Senior"),
      Rate("S2", "Senior")
    )
    val cabinPrices = Seq(
      CabinPrice("CA", "M1", 200.00),
      CabinPrice("CA", "M2", 250.00),
      CabinPrice("CA", "S1", 225.00),
      CabinPrice("CA", "S2", 260.00),
      CabinPrice("CB", "M1", 230.00),
      CabinPrice("CB", "M2", 260.00),
      CabinPrice("CB", "S1", 245.00),
      CabinPrice("CB", "S2", 270.00),
    )

    val expected = Seq(
      BestGroupPrice("CA", "S1", 225.00, "Senior"),
      BestGroupPrice("CB", "S1", 245.00, "Senior"),
    )

    assert(rateProvider.getBestGroupPrices(rates, cabinPrices) === expected)
  }
}
