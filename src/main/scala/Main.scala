import cats.effect._
import cats.effect.concurrent._
import cats.syntax.all._

import scala.concurrent.duration._


object Main extends IOApp {
  def runCounter(mvar: MVar[IO, Int]): Resource[IO, Unit] = {
    def routine(cnt: Int): IO[Unit] = for {
      _ <- mvar.put(cnt)
      _ <- IO.sleep(FiniteDuration(1, SECONDS))
      _ <- routine(cnt + 1)
    } yield ()

    Resource.make(routine(0).start)(_.cancel).void
  }

  def runPrinter(mvar: MVar[IO, Int]): Resource[IO, Unit] = {
    def routine: IO[Unit] = for {
      x <- mvar.take
      _ <- IO(println(x))
      _ <- routine
    } yield ()

    Resource.make(routine.start)(_.cancel).void
  }

  override def run(args: List[String]): IO[ExitCode] = {
    def program: Resource[IO, Unit] = for {
      mvar <- Resource.make(MVar.empty[IO, Int])(_ => IO.unit)
      _ <- runCounter(mvar)
      _ <- runPrinter(mvar)
    } yield ()

    program.use(_ => IO.never)
  }
}
