import scala.language.higherKinds

trait MkDir[F[_], Dir] {
  def mkDir(base: Dir, name: String): F[Dir]
}

trait MkFile[F[_], Dir, File] {
  def mkFile(base: Dir, name: String): F[File]
}

trait ListDir[F[_], Dir, File] {
  def listFilesInDir(dir: Dir): F[List[File]]
}

trait Printer[F[_], File] {
  def printName(file: File): F[Unit]
}

trait StringifyFile[F[_], File] {
  def stringifyFile(file: File): F[String]
}

trait MoveFile[F[_], Dir, File] {
  def moveFile(file: File, dir: Dir): F[File]
}

trait Filesystem[F[_], Dir, File] extends
  MkDir[F, Dir] with
  MkFile[F, Dir, File] with
  ListDir[F, Dir, File] with
  Printer[F, File] with
  StringifyFile[F, File] with
  MoveFile[F, Dir, File]