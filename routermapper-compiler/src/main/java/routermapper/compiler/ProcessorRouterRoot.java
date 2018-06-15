package routermapper.compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import routermapper.Route;

/**
 * Created by LiCola on 2017/6/21.
 *
 * 注解生成代码：生成目录在项目build包下，和目标类同级
 *
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ProcessorRouterRoot extends AbstractProcessor {

  private static final String DEFAULT_ROUTE_MAPPER_NAME = "RouteMapper";
  private static final String DEFAULT_ROUTE_CONTRACT_NAME = "RouteContract";
  private static final String PACKAGE_OF_GENERATE_FILE = "com.licola.routermapper.router";


  private Filer filer;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    filer = processingEnv.getFiler();
  }


  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> types = new LinkedHashSet<>();
    for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
      types.add(annotation.getCanonicalName());
    }
    return types;
  }

  private Set<Class<? extends Annotation>> getSupportedAnnotations() {
    Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
    annotations.add(Route.class);
    return annotations;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
      RoundEnvironment roundEnvironment) {
    Messager messager = processingEnv.getMessager();

    List<OutWriteCommand> commands = new ArrayList<>();

    commands.add(new OutWriteCommand() {
      @Override
      public void execute() throws IOException {

        TypeSpec typeSpec = ProcessorRouteContract
            .build(roundEnvironment.getElementsAnnotatedWith(Route.class))
            .process(DEFAULT_ROUTE_CONTRACT_NAME);

        if (typeSpec == null) {
          return;
        }

        JavaFile javaFile = JavaFile.builder(PACKAGE_OF_GENERATE_FILE, typeSpec).build();
        javaFile.writeTo(filer);
      }
    });

    commands.add(new OutWriteCommand() {
      @Override
      public void execute() throws IOException {

        TypeSpec typeSpec = ProcessorRouteMapper
            .build(roundEnvironment.getElementsAnnotatedWith(Route.class))
            .process(DEFAULT_ROUTE_MAPPER_NAME, DEFAULT_ROUTE_CONTRACT_NAME,
                PACKAGE_OF_GENERATE_FILE);

        if (typeSpec == null) {
          return;
        }

        JavaFile javaFile = JavaFile.builder(PACKAGE_OF_GENERATE_FILE, typeSpec).build();
        javaFile.writeTo(filer);
      }
    });

    try {
      //依次生成代码
      for (OutWriteCommand item : commands) {
        item.execute();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return true;
  }

}