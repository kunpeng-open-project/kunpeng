package com.kp.framework.annotation.impl.util;

import com.fasterxml.classmate.TypeResolver;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.utils.kptool.KPReflectUtil;
import io.swagger.annotations.ApiModelProperty;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.MemberValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ApiJsonlParamModeUtil {

    @Autowired
    private TypeResolver typeResolver;

    //动态生成的Class的包路径 自由定义
    private final static String BASE_PACKAGE = "com.kp.framework.swagger.json.parameter.entity.";
    //包含默认类名
    private final static String INCLUDE_CLASS_NAME = "KPJsonParamIncludeBO";
    //包含序号防止重名
    private static Integer INCLUDE_SORT = 1;
    //包含默认类名
    private final static String IGNORES_CLASS_NAME = "KPJsonParamIgnoresBO";
    //包含序号防止重名
    private static Integer IGNORES_SORT = 1;



    /**
     * @Author lipeng
     * @Description 在指定文件中抽取指定字段生成新的swagger说明文档类
     * @Date 2024/12/17 17:44
     * @param apiAnno
     * @param parameterContext
     * @return void
     **/
    public void include(KPApiJsonlParamMode apiAnno, ParameterContext parameterContext){
        String key = INCLUDE_CLASS_NAME + INCLUDE_SORT++;
        try {
            Class.forName(BASE_PACKAGE + key);
            //已经存在该文件
            throw new KPServiceException(BASE_PACKAGE + key + "是框架自动生成文件，您的项目中已经存在，请删除该文件或者重新命名");
        } catch (ClassNotFoundException e) {}

        try {
            String separator = apiAnno.separator();
            List<String> fieldList = Arrays.asList(apiAnno.includes().split(separator));
            // 获取默认的ClassPool实例
            ClassPool pool = ClassPool.getDefault();
            // 创建新的CtClass对象
            CtClass ctClass = pool.makeClass(BASE_PACKAGE + key);
            // 设置类为抽象类
            ctClass.setModifiers(Modifier.ABSTRACT);
            //处理 javassist.NotFoundException
            pool.insertClassPath(new ClassClassPath(apiAnno.component()));
            CtClass globalCtClass = pool.getCtClass(apiAnno.component().getName());
            //从globalCtClass拷贝指定字段到动态创建的类中
            for (String field : fieldList) {
                //若指定的字段不存在 throw NotFoundException
                CtField ctField = globalCtClass.getDeclaredField(field);
                CtField newCtField = new CtField(ctField, ctClass);
                handleField(newCtField);
                ctClass.addField(newCtField);
            }
            // 将生成的Class添加到SwaggerModels
            parameterContext.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(ctClass.toClass()));
            // 修改Json参数的ModelRef为动态生成的class
            parameterContext.parameterBuilder().parameterType("body").modelRef(new ModelRef(key)).name("body").description("body");

        }catch (Exception ex) {
            throw new KPServiceException(BASE_PACKAGE + key + "生成异常, 异常信息为：" + ex.getMessage());
        }
    }




    /**
     * @Author lipeng
     * @Description 2024/12/17 18:44
     * @Date 2024/12/19 17:37
     * @param apiAnno
     * @param parameterContext
     * @return void
     **/
    public void ignores(KPApiJsonlParamMode apiAnno, ParameterContext parameterContext){
        String key = IGNORES_CLASS_NAME + IGNORES_SORT++;
        try {
            Class.forName(BASE_PACKAGE + key);
            //已经存在该文件
            throw new KPServiceException(BASE_PACKAGE + key + "是框架自动生成文件，您的项目中已经存在，请删除该文件或者重新命名");
        } catch (ClassNotFoundException e) {}

        try {
            String separator = apiAnno.separator();
            List<String> ignoreList = Arrays.asList(apiAnno.ignores().split(separator));
            List<Field> fieldAll = KPReflectUtil.getAllDeclaredFields(apiAnno.component());
            List<String> fieldList = new ArrayList<>();
            fieldAll.forEach(field -> {
                if (!field.getName().equals("serialVersionUID") && !ignoreList.contains(field.getName())){
                    fieldList.add(field.getName());
                }
            });

            // 获取默认的ClassPool实例
            ClassPool pool = ClassPool.getDefault();
            // 创建新的CtClass对象
            CtClass ctClass = pool.makeClass(BASE_PACKAGE + key);
            // 设置类为抽象类
            ctClass.setModifiers(Modifier.ABSTRACT);
            //处理 javassist.NotFoundException
            pool.insertClassPath(new ClassClassPath(apiAnno.component()));
            CtClass globalCtClass = pool.getCtClass(apiAnno.component().getName());
            //从globalCtClass拷贝指定字段到动态创建的类中
            for (String field : fieldList) {
                //若指定的字段不存在 throw NotFoundException
                CtField ctField = null;
                try {
                    ctField = globalCtClass.getDeclaredField(field);
                }catch(Exception ex){
                    CtClass clazz = globalCtClass.getSuperclass();
                    ctField = clazz.getDeclaredField(field);
                }
                CtField newCtField = new CtField(ctField, ctClass);
                handleField(newCtField);
                ctClass.addField(newCtField);
            }
            // 将生成的Class添加到SwaggerModels
            parameterContext.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(ctClass.toClass()));
            // 修改Json参数的ModelRef为动态生成的class
            parameterContext.parameterBuilder().parameterType("body").modelRef(new ModelRef(key)).name("body").description("body");

        }catch (Exception ex) {
            throw new KPServiceException(BASE_PACKAGE + key + "生成异常, 异常信息为：" + ex.getMessage());
        }
    }

    /**
     * @Author lipeng
     * @Description 处理ApiModelProperty name
     * @Date 2024/12/17 17:28
     * @param field
     * @return void
     **/
    private void handleField(CtField field) {
        //防止private又没有getter
        field.setModifiers(Modifier.PUBLIC);
        //有name的把字段名改为name
        //因为JSON格式化的原因,ApiModelProperty的name属性无效 所以如果有name,直接更改字段名为name
        AnnotationsAttribute annos = ((AnnotationsAttribute) field.getFieldInfo().getAttribute("RuntimeVisibleAnnotations"));
        if (annos != null) {
            Annotation anno = annos.getAnnotation(ApiModelProperty.class.getTypeName());
            if (anno != null) {
                MemberValue name = anno.getMemberValue("name");
                if (name != null) {
                    //这里返回的name会以引号包裹
                    String fName = name.toString().replace("\"", "").trim();
                    if (fName.length() > 0) {
                        field.setName(fName);
                    }
                }
            }
        }
    }
}
