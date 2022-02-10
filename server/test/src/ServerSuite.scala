import io.undertow.Undertow
import utest._

object ServerSuite extends TestSuite {
  val tests = Tests {
    test("list") - withServer(WebServer) { host =>
      val r = requests.get(host + "/flowers")
      val json = ujson.read(r.text())

      val flowers = json("flowers").arr

      flowers.size ==> 3
    }
    test("add and list") - withServer(WebServer) { host =>
      val createResponse =
        requests.post(
          url = host + "/flowers",
          data =
            """{"name": "rose", "price": 30, "stock": 15}"""
        )
      println("Nötter" + createResponse.text())
      val json = ujson.read(createResponse.text())
      val id = json("flowerId").str

      val r = requests.get(host + "/flowers")
      val json2 = ujson.read(r.text())

      val flowers = json2("flowers").arr

      flowers.size ==> 4
    }
    test("create and get") - withServer(WebServer) { host =>
      val createResponse =
        requests.post(
          url = host + "/flowers",
          data =
            """{"name": "rose", "price": 30, "stock": 15}"""
        )
      val createJson = ujson.read(createResponse.text())
      val fid = createJson("flowerId").str

      val getResponse = requests.get(host + s"/flowers/$fid")
      val getJson = ujson.read(getResponse.text())

      val name = getJson(0)("name").str

      name ==> "rose"
    }
    test("create, update and get") - withServer(WebServer) { host =>
      val createResponse =
        requests.post(
          url = host + "/flowers",
          data =
            """{"name": "rose", "price": 30, "stock": 15}"""
        )

      val createJson = ujson.read(createResponse.text())
      val fid = createJson("flowerId").str

      val updateResponse =
        requests.post(
          url = host + s"/flowers/$fid/update",
          data = """{"name": "matte"}"""
        )

      val updateJson = ujson.read(updateResponse.text())
      val updatedName = updateJson("updated")

      val getResponse = requests.get(host + s"/flowers/$fid")
      val getJson = ujson.read(getResponse.text())

      val name = getJson(0)("name").str

      name ==> "matte"

    }
    test("create and delete") - withServer(WebServer) { host =>
      val createResponse =
        requests.post(
          url = host + "/flowers",
          data =
            """{"name": "rose", "price": 30, "stock": 15}"""
        )

      val createJson = ujson.read(createResponse.text())
      val fid = createJson("flowerId").str

      val deleteResponse =
        requests.delete(
          url = host + s"/flowers/$fid",
        )

      val updateJson = ujson.read(deleteResponse.text())
      val updatedName = updateJson("deleted")

      val getResponse = requests.get(host + s"/flowers/$fid")
      val getJson = ujson.read(getResponse.text())

      val deletedJson = updateJson("deleted").bool

      deletedJson ==> true

    }
  }

  def withServer[T](m: cask.main.Main)(f: String => T): T = {
    val port = 8081
    val server = Undertow.builder
      .addHttpListener(port, "localhost")
      .setHandler(m.defaultHandler)
      .build
    server.start()
    val res =
      try f(s"http://localhost:$port")
      finally server.stop()
    res
  }
}
