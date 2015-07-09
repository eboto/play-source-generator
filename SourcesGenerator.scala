import java.io.File
import java.nio.file.Files

import play.twirl.api.Template0
import play.twirl.api.Txt

object SourcesGenerator extends App {

  val outDir = new File(args(0))
  val templates = args.drop(1)

  templates.foreach { template =>
    val templateClazz = Class.forName(template + "$")
    val templateObject = classOf[Template0[Txt]].cast(templateClazz.getField("MODULE$").get(null))
    val source = templateObject.render()
    val file = new File(outDir, template)
    file.getParentFile.mkdirs()
    Files.write(file.toPath, source.body.getBytes())
    System.out.println("Generated " + file.getAbsolutePath)
  }
}
