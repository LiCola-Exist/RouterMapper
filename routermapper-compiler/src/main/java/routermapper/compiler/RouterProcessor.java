package routermapper.compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import routermapper.Route;
import routermapper.compiler.process.ProcessRouteContract;
import routermapper.compiler.process.ProcessRouteMapper;

/**
 * Created by LiCola on 2017/6/21.
 *
 * 注解生成代码：生成目录在项目build包下，和目标类同级
 *
 * 简单示例说明：{@link <a href="http://blog.stablekernel.com/the-10-step-guide-to-annotation-processing-in-android-studio">}
 * 引入{@link <a href="https://github.com/square/javapoet">}
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("routermapper.Route")
@SupportedOptions(value = {"RoutePackage", "RouteMapperClass", "RouteContractClass"})
public class RouterProcessor extends AbstractProcessor {

  private static final String TAG = RouterProcessor.class.getSimpleName();

  private static final String DEFAULT_ROUTE_MAPPER_NAME = "RouteMapper";
  private static final String DEFAULT_ROUTE_CONTRACT_NAME = "RouteContract";

  public static final String OPTION_ROUTE_PACKAGE = "RoutePackage";
  public static final String OPTION_ROUTE_MAPPER = "RouteMapperClass";
  public static final String OPTION_ROUTE_CONTRACT = "RouteContractClass";

  private Filer filer;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    filer = processingEnv.getFiler();
  }


  @Override
  public boolean process(Set<? extends TypeElement> annotations,
      RoundEnvironment roundEnvironment) {
    Messager messager = processingEnv.getMessager();

    String packageName = processingEnv.getOptions().get(OPTION_ROUTE_PACKAGE);
    if (CheckUtils.isEmpty(packageName)) {
      messager.printMessage(Kind.ERROR, "解析异常");
      throw new IllegalArgumentException(TAG + ":请配置路由包名");
    }

    String routeMapperName = processingEnv.getOptions()
        .getOrDefault(OPTION_ROUTE_MAPPER, DEFAULT_ROUTE_MAPPER_NAME);
    String routeContract = processingEnv.getOptions()
        .getOrDefault(OPTION_ROUTE_CONTRACT, DEFAULT_ROUTE_CONTRACT_NAME);

    List<TypeSpec> specs = new ArrayList<>();

    specs.add(ProcessRouteContract.build(roundEnvironment.getElementsAnnotatedWith(Route.class))
        .process(routeContract));

    specs.add(
        ProcessRouteMapper.build(roundEnvironment.getElementsAnnotatedWith(Route.class))
            .process(routeMapperName, routeContract, packageName));

    for (TypeSpec item : specs) {
      writeToJavaFile(packageName, item);
    }

    return true;
  }

  private void writeToJavaFile(String packageName, TypeSpec typeSpec) {
    if (typeSpec == null) {
      return;
    }
    JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
    try {
      javaFile.writeTo(filer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
