import utest._
import backend.FlowerShop
import backend.Flower

object FlowerShopSuite extends TestSuite {

  val tests = Tests {
    test("test add") - {
      val fs = new FlowerShop()
      val fl = Flower("id1","rose", 20.50, 10)
      fs.addFlower(fl)

      fs.list().size ==> 1
    }
     test("test get") - {
      val fs = new FlowerShop()
      val fl = Flower("id1", "rose", 20.50, 10)
      val id = fs.addFlower(fl)
      val r = fs.get(id).get

      r ==> fl
      fl.name ==> "rose"
    } 
    test("test list") - {
      val fs = new FlowerShop()
      val r = fs.list()

      r.size ==> 0
    }
    test("test update ") - {
      val fs = new FlowerShop()
      val fl = Flower("id1","rose", 20.50, 10)
      val f1id = fs.addFlower(fl)
      val cpy = fs.get(f1id).get.copy(price = 30)
       val cpy2 = fl.copy(price = 30)
      fs.update(f1id, cpy)


      val r = fs.get(f1id).get.price

      r ==> 30
    }
    test("test buy ") - {
      val fs = new FlowerShop()
      val fl = Flower("id1", "rose", 20.50, 10)
      val id = fs.addFlower(fl)
      val cost = fs.buy(id, 6)
      val updated = fs.get(id).get

      cost ==> 123
      updated.stock ==> 4
    }
    test("test delete ") - {
      val fs = new FlowerShop()
      val fl = Flower("id1","rose", 20.50, 10)
      val fl2 = Flower("id2","ros", 10.5, 10)
      val t = fs.addFlower(fl)
      fs.addFlower(fl2)
      fs.delete(t)

      val r = fs.list()

      r.size ==> 1
    }
  }
}
