object Travel {
  def main(args: Array[String]): Unit = {
    println("Exercise Output")

    //Rate Provider Exercise
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

    println("Best Group Rates: ")
    println(rateProvider.getBestGroupPrices(rates, cabinPrices))

    println()

    //Promotion Service Exercise
    val promotionService = new PromotionService()
    val promotions = Seq(
      Promotion("P1", Seq("P3")),    // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")),  // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")),    // P3 is not combinable with P1
      Promotion("P4", Seq("P2")),    // P4 is not combinable with P2
      Promotion("P5", Seq("P2"))    // P5 is not combinable with P
    )

    println("All Promotion Combinations: ")
    println(promotionService.allCombinablePromotions(promotions))
    println("Combinations with P1: ")
    println(promotionService.combinablePromotions("P1", promotions))
    println("Combinations with P3: ")
    println(promotionService.combinablePromotions("P3", promotions))

  }
}
