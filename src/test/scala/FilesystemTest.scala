
import java.nio.file.{Files, Path}
import java.util.stream.Collectors

import cats.Id
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.JavaConverters.asScalaBufferConverter

class FilesystemTest extends AnyFlatSpec with Matchers {

  trait Environment {
    implicit val fs = new RealFilesystem[Id]

    val consumer = new Consumer[Id, Path, Path]
    val dir = Files.createTempDirectory("tsarn-scala-lab8")
    consumer.run(dir)
  }

  "Lab8" should "get me a good grade" in new Environment {
    assert(Files.exists(dir.resolve("test_dir")))

    val expected: List[Path] = List(
      dir.resolve("test_dir"),
      dir.resolve("test_dir/b"),
      dir.resolve("test_dir/b/bar"),
      dir.resolve("test_dir/b/baz"),
      dir.resolve("test_dir/f"),
      dir.resolve("test_dir/f/foo"),
    )
    val actual: List[Path] = Files.walk(dir.resolve("test_dir")).sorted().collect(Collectors.toList[Path]).asScala.toList
    assert(actual == expected)
  }
}
