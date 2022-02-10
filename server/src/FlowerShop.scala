package backend
import java.util.UUID
import upickle.default.{ReadWriter => RW, macroRW}
case class Flower(id: FlowerId, name: String, price: Double, stock: Int)
object Flower {
  implicit val rw: RW[Flower] = macroRW
}
type FlowerId = String

class FlowerShop {
  var flowers: Map[String, Flower] = Map.empty
  flowers = flowers + ("fid1" -> Flower("fid1", "ros", 10.0, 7))
  flowers = flowers + ("fid2" -> Flower("fid2", "tulpan", 11.0, 9))
  flowers = flowers + ("fid3" -> Flower("fid3", "solros", 7.0, 11))

  def createID() = UUID.randomUUID.toString()

  def addFlower(f: Flower): FlowerId = {
    val id = createID()
    flowers = flowers + (id -> f.copy(id = id))
    id
  }
  def list(): List[Flower] = flowers.values.toList

  def get(fid: FlowerId): Option[Flower] = {
    flowers.get(fid)
  }
  def update(fid: FlowerId, f: Flower): Unit = {
    flowers = flowers + (fid -> f)
  }
  def buy(fid: FlowerId, amount: Int): Double = {
    val f = get(fid).get
    val newamount = f.stock - amount
    update(fid, f.copy(stock = newamount))
    f.price * amount
  }
  def delete(fid: FlowerId): Unit = {
    val taBortF = flowers.removed(fid)
    flowers = taBortF
  }

}
