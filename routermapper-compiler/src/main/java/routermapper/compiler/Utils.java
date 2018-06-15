package routermapper.compiler;

import com.google.auto.common.MoreElements;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;

/**
 * Created by LiCola on 2018/6/7.
 */
public class Utils {

  public static PackageElement getPackageElement(Element elementItem) {
    return MoreElements
        .asPackage(MoreElements.asType(elementItem).getEnclosingElement());
  }

  private static final String DATE_FORMAT_LOG_FILE = "yyyy/MM/dd HH:mm:ss";

  public static String getNowTime() {
    return new SimpleDateFormat(DATE_FORMAT_LOG_FILE,
        Locale.CHINA).format(new Date());
  }

}
