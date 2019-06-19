class PromotionService {

  def combinable(promotionCode: String, promotions: Seq[Promotion]): Seq[Promotion] = {
    promotions
      .filter(next => next.code != promotionCode)
      .filter(next => !next.notCombinableWith.contains(promotionCode))
  }

  /* TODO: Multiple ways to combine these for reusability.
  combinablePromotions could call allCombinablePromotions, then filter.
  allCombinablePromotions could iterate over its list, calling combinablePromotions for each.

  Which is better depends on use cases; can we memoize/cache the allCombinablePromotions call?
  Which one is used more frequently?
  How much variance are there in the promotions sets we will be running against these?
  */

  def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
//
//    def helper(promotionCombo: Seq[String], promotions: Seq[Promotion]): Seq[Seq[String]] = {
//      val combinable = promotions
//        .filter(next => !promotionCombo.contains(next.code))
//        .filter(next => !promotionCombo.exists(next.notCombinableWith.contains))
//
//        if(combinable.isEmpty) {
//          Seq(promotionCombo)
//        } else {
//          combinable.flatMap(next => helper(promotionCombo :+ next.code, combinable))
//        }
//    }
//
//    allPromotions.flatMap(promo => helper(Seq(promo.code), allPromotions))
//      .filter(_.length > 1)
//      .map(_.toSet)
//      .distinct
//      .map(_.toSeq)
//      .map(PromotionCombo)

    def helper(promotionCombo: Seq[String], promotions: Seq[Promotion]): Seq[Seq[String]] = {
      if(promotions.isEmpty) {
        Seq(promotionCombo)
      } else {
        promotions.flatMap(next => helper(promotionCombo :+ next.code, combinable(next.code, promotions)))
      }
    }

    allPromotions.flatMap(promo => helper(Seq(promo.code), combinable(promo.code, allPromotions)))
      .filter(_.length > 1)
      .map(_.toSet)
      .distinct
      .map(_.toSeq)
      .map(PromotionCombo)

    //TODO: Realistically if we wanted to unify with the other method, would want a 3rd method to not deal with case classes on initial return
//    allPromotions
//      .flatMap(promo => combinablePromotions(promo.code, allPromotions))
//      .map(_.promotionCodes)
//      .map(_.toSet)
//      .distinct
//      .map(_.toSeq)
//      .map(PromotionCombo)
  }

  def combinablePromotions(promotionCode: String, allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {

    def helper(combo: Seq[String], promotions: Seq[Promotion]): Seq[String] = promotions match {
      case head +: tail => helper(if (head.notCombinableWith.exists(combo.contains)) combo else combo :+ head.code, tail)
      case _ => combo;
    }

    combinable(promotionCode, allPromotions)
      .map(promo => helper(Seq(promotionCode, promo.code), allPromotions.filterNot(_.code == promo.code)))
      .map(_.toSet)
      .distinct
      .map(_.toSeq)
      .map(PromotionCombo)


    //TODO: Whether or not this is better depends on use case; also changes sequence order relative to assignment output
//    allCombinablePromotions(allPromotions).filter(_.promotionCodes.contains(promotionCode))
  }
}


case class Promotion(code: String, notCombinableWith: Seq[String])
case class PromotionCombo(promotionCodes: Seq[String])
