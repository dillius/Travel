import org.scalatest.FlatSpec

class PromotionServiceTests extends FlatSpec {

  behavior of "Promotion Service"

  it should "return all promotion combinations" in {
    val service = new PromotionService

    val promotions = Seq(
      Promotion("P1", Seq("P3")),    // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")),  // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")),    // P3 is not combinable with P1
      Promotion("P4", Seq("P2")),    // P4 is not combinable with P2
      Promotion("P5", Seq("P2"))    // P5 is not combinable with P
    )

    val expected = Seq(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5")),
      PromotionCombo(Seq("P2", "P3")),
      PromotionCombo(Seq("P3", "P4", "P5")),
    )

    assert(service.allCombinablePromotions(promotions) === expected)
  }

  it should "return valid combinations for P1" in {
    val service = new PromotionService

    val promotions = Seq(
      Promotion("P1", Seq("P3")),    // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")),  // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")),    // P3 is not combinable with P1
      Promotion("P4", Seq("P2")),    // P4 is not combinable with P2
      Promotion("P5", Seq("P2"))    // P5 is not combinable with P
    )

    val expected = Seq(
      PromotionCombo(Seq("P1", "P2")),
      PromotionCombo(Seq("P1", "P4", "P5")),
    )

    assert(service.combinablePromotions("P1", promotions) === expected)
  }

  it should "return valid combinations for P3" in {
    val service = new PromotionService

    val promotions = Seq(
      Promotion("P1", Seq("P3")),    // P1 is not combinable with P3
      Promotion("P2", Seq("P4", "P5")),  // P2 is not combinable with P4 and P5
      Promotion("P3", Seq("P1")),    // P3 is not combinable with P1
      Promotion("P4", Seq("P2")),    // P4 is not combinable with P2
      Promotion("P5", Seq("P2"))    // P5 is not combinable with P
    )

    val expected = Seq(
      PromotionCombo(Seq("P3", "P2")),
      PromotionCombo(Seq("P3", "P4", "P5")),
    )

    assert(service.combinablePromotions("P3", promotions) === expected)
  }

  it should "handle an empty promotion set" in {
    val service = new PromotionService

    val promotions = Seq()
    val expected = Seq()

    assert(service.allCombinablePromotions(promotions) === expected)
  }

  it should "handle a set that does not allow any combinations" in {
    val service = new PromotionService

    val promotions = Seq(
      Promotion("P1", Seq("P2", "P3")),
      Promotion("P2", Seq("P1", "P3")),
      Promotion("P3", Seq("P1", "P2")),
    )

    val expected = Seq()

    assert(service.allCombinablePromotions(promotions) === expected)
  }

  it should "handle a set with no combination restrictions" in {
    val service = new PromotionService

    val promotions = Seq(
      Promotion("P1", Seq()),
      Promotion("P2", Seq()),
      Promotion("P3", Seq()),
    )

    val expected = Seq(PromotionCombo(Seq("P1", "P2", "P3")))

    assert(service.allCombinablePromotions(promotions) === expected)
  }

}
