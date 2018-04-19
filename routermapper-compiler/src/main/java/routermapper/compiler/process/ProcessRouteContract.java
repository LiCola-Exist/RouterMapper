package routermapper.compiler.process;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.lang.annotation.Retention;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import routermapper.compiler.CheckUtils;

/**
 * Created by LiCola on 2017/6/21.
 */

public class ProcessRouteContract {

  private static final String PLACEHOLDER_ACTIVITY = "UnknownAty";

  static final String AnnotationContractName = "Name";//契约类注释名

  public Set<? extends Element> elements;

  public static ProcessRouteContract build(Set<? extends Element> elements) {
    return new ProcessRouteContract(elements);
  }

  private ProcessRouteContract(Set<? extends Element> elements) {
    this.elements = elements;
  }

  public TypeSpec process(String className) {
    if (CheckUtils.isEmpty(elements)) {
      return null;
    }
    //定义接口型 契约类
    Builder classSpecBuild = TypeSpec.interfaceBuilder(className);
    //定义IntDef注释
    AnnotationSpec.Builder interfaceAnnotationIntDefBuilder =
        AnnotationSpec.builder(ClassName.get("android.support.annotation", "IntDef"));

    int index = 0;
    //添加一个占位 用于在界面没有完成的请求下 代码能够编译
    addFieldAndAnnotationField(classSpecBuild, interfaceAnnotationIntDefBuilder, index++,
        PLACEHOLDER_ACTIVITY);
    for (Element element : elements) {
      String itemAty = element.getSimpleName().toString();
      addFieldAndAnnotationField(classSpecBuild, interfaceAnnotationIntDefBuilder, index,
          itemAty);
      index++;
    }

    //定义Retention注释
    AnnotationSpec interfaceAnnotationRetention = AnnotationSpec.builder(Retention.class)
        .addMember("value", "$L", ClassName.get("java.lang.annotation.RetentionPolicy", "SOURCE"))
        .build();

    //定义并构建 AnnotationContractName 注释
    TypeSpec annotationIntent = TypeSpec.annotationBuilder(AnnotationContractName)
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .addAnnotation(interfaceAnnotationIntDefBuilder.build())//添加IntDef
        .addAnnotation(interfaceAnnotationRetention)//添加Retention
        .build();

    classSpecBuild.addModifiers(Modifier.PUBLIC).addType(annotationIntent);//添加注释字段

    return classSpecBuild.build();
  }

  private void addFieldAndAnnotationField(Builder classSpecBuild,
      AnnotationSpec.Builder interfaceAnnotationIntDefBuilder, int index, String itemAty) {
    //根据遍历 定义并构建 Aty同名int字段（自增）
    FieldSpec interfaceField = FieldSpec.builder(int.class, itemAty)
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
        .initializer("$L", index)
        .build();
    classSpecBuild.addField(interfaceField);//添加同名字段到契约类
    interfaceAnnotationIntDefBuilder.addMember("value", "$L", itemAty);//添加同名字段到注释中
  }
}
