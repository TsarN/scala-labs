import java.nio.file.{Files, Path, StandardCopyOption}

import cats.{Applicative, Monad}
import cats.implicits._

import scala.collection.JavaConverters._
import scala.language.higherKinds

class RealFilesystem[F[_]: Monad] extends Filesystem[F, Path, Path] {
  override def mkFile(base: Path, name: String): F[Path] =
    Files.createFile(base.resolve(name)).pure[F]

  override def mkDir(base: Path, name: String): F[Path] =
    Files.createDirectories(base.resolve(name)).pure[F]

  override def listFilesInDir(dir: Path): F[List[Path]] =
    Files.list(dir).iterator().asScala.toList.pure[F]

  override def printName(file: Path): F[Unit] = println(file.getFileName).pure[F]

  override def moveFile(file: Path, dir: Path): F[Path] =
    Files.move(file, dir.resolve(file.getFileName), StandardCopyOption.REPLACE_EXISTING).pure[F]

  override def stringifyFile(file: Path): F[String] =
    file.getFileName.toString.pure[F]
}
