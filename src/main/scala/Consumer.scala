import java.nio.file.{Path, Paths}

import cats.data.StateT
import cats.{Functor, Id, Monad}

import scala.language.higherKinds
import cats.implicits._

class Consumer[F[_], Dir, File](implicit F: Monad[F], fs: Filesystem[F, Dir, File]) {
  def run(dir: Dir): F[Unit] = for {
    testDir <- fs.mkDir(dir, "test_dir")
    _ <- fs.mkFile(testDir, "foo")
    _ <- fs.mkFile(testDir, "bar")
    _ <- fs.mkFile(testDir, "baz")
    listOfFiles <- fs.listFilesInDir(testDir)
    _ <- listOfFiles.traverse(file => for {
      _ <- fs.printName(file)
      str <- fs.stringifyFile(file)
      dir <- fs.mkDir(testDir, str(0).toString)
      _ <- fs.moveFile(file, dir)
    } yield ())
  } yield ()
}


object Main {
  def main(args: Array[String]): Unit = {
    implicit val fs: RealFilesystem[Id] = new RealFilesystem[Id]

    val consumer = new Consumer[Id, Path, Path]
    consumer.run(Paths.get("."))
  }
}