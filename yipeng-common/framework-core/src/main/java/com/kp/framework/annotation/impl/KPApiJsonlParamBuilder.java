package com.kp.framework.annotation.impl;

import com.fasterxml.classmate.TypeResolver;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.annotations.ApiModelProperty;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * @Author lipeng
 * @Description 注解ApiJsonModel实现方式
 * @Date 2022/5/19 19:28
 * @return
 **/
@Component
//@Order
@Slf4j
public class KPApiJsonlParamBuilder implements ParameterBuilderPlugin {

    @Autowired
    private TypeResolver typeResolver;
    //动态生成的Class的包路径 自由定义
    private final static String BASE_PACKAGE = "com.kp.framework.swagger.json.parameter.entity.";
    //默认类名
    private final static String DEFAULT_CLASS_NAME = "KPJsonParamBO";
    //序号 防止重名
    private static Integer i = 1;

    @Override
    public void apply(ParameterContext context) {
        String key = DEFAULT_CLASS_NAME + i++;
        try {
            // 从方法或参数上获取指定注解的Optional
            Optional<KPApiJsonlParam> optional = context.getOperationContext().findAnnotation(KPApiJsonlParam.class);
            if (!optional.isPresent()) optional = context.resolvedMethodParameter().findAnnotation(KPApiJsonlParam.class);
            if (!optional.isPresent()) return;
            try {
                Class.forName(BASE_PACKAGE + key);
                //已经存在该文件
                throw new KPServiceException(BASE_PACKAGE + key + "是框架自动生成文件，您的项目中已经存在，请删除该文件或者重新命名");
            } catch (ClassNotFoundException e) {}


            KPApiJsonlParam apiAnno = optional.get();
            ApiModelProperty[] properties = apiAnno.value();
            // 将生成的Class添加到SwaggerModels
            context.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(createRefModel(properties, key)));
            // 修改Json参数的ModelRef为动态生成的class
            context.parameterBuilder().parameterType("body").modelRef(new ModelRef(key)).name(KPStringUtil.initialsLowerCase(key)).description(key);
        } catch (Exception e) {
            log.error("@KPApiJsonlParam Error", e);
        }
    }

    /**
     * MapReaderForApi
     * 根据propertys中的值动态生成含有Swagger注解的javaBeen
     *
     * @author chengz
     * @date 2020/10/14
     */
    private Class<?> createRefModel(ApiModelProperty[] propertys, String key) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(BASE_PACKAGE + key);
        ctClass.setModifiers(Modifier.ABSTRACT);
        for (ApiModelProperty property : propertys) {
            ctClass.addField(createField(property, ctClass));
        }
        return ctClass.toClass();
    }

    /**
     * 根据property的值生成含有swagger apiModelProperty注解的属性
     */
    private CtField createField(ApiModelProperty property, CtClass ctClass) throws Exception {
        String dataType = property.dataType();
        CtField ctField = null;
        if (KPStringUtil.isEmpty(dataType)) {
            ctField = new CtField(ClassPool.getDefault().get(String.class.getName()), property.name(), ctClass);
        } else if (dataType.equalsIgnoreCase("int")) {
            ctField = new CtField(ClassPool.getDefault().get(Integer.class.getName()), property.name(), ctClass);
        }else if (dataType.equalsIgnoreCase("long")) {
            ctField = new CtField(ClassPool.getDefault().get(Long.class.getName()), property.name(), ctClass);
        }else if (dataType.equalsIgnoreCase("boolean")) {
            ctField = new CtField(ClassPool.getDefault().get(Boolean.class.getName()), property.name(), ctClass);
        }else if (dataType.equalsIgnoreCase("list<int>")) {
            ctField = new CtField(ClassPool.getDefault().get(List.class.getName()), property.name(), ctClass);
            // 设置泛型类型
            ctField.setGenericSignature("Ljava/util/List<Ljava/lang/Integer;>;");
        }else if (dataType.equalsIgnoreCase("list<string>")) {
            ctField = new CtField(ClassPool.getDefault().get(List.class.getName()), property.name(), ctClass);
            // 设置泛型类型
            ctField.setGenericSignature("Ljava/util/List<Ljava/lang/String;>;");
        }else if (dataType.equalsIgnoreCase("list")) {
            ctField = new CtField(ClassPool.getDefault().get(List.class.getName()), property.name(), ctClass);
        }

//        else if (dataType.equalsIgnoreCase("array")) {
//            ctField = new CtField(ClassPool.getDefault().get(Array.class.getName()), property.name(), ctClass);
//        }


        //此处默认字段类型为String 如果不是 swagger也是取注解的dataType 字段类型就不重要了
//        CtField ctField = new CtField(ClassPool.getDefault().get(String.class.getName()), property.name(), ctClass);
        ctField.setModifiers(Modifier.PUBLIC);
        ConstPool constPool = ctClass.getClassFile().getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        //不知道是否有直接把property转为对应Annotation的方法 它们本质是一样的
        Annotation anno = new Annotation(ApiModelProperty.class.getTypeName(), constPool);
        Method[] members = ApiModelProperty.class.getDeclaredMethods();
        for (Method member : members) {
            Object value = member.invoke(property);
            //使用默认值的就不重复赋值了
            if (!value.equals(member.getDefaultValue())) {
                //由于只拷贝了String,int,boolean等返回值的属性,所以诸如accessMode,extensions这样的属性设置将无效
                Type type = member.getReturnType();
                if (type == String.class) {
                    anno.addMemberValue(member.getName(), new StringMemberValue(String.valueOf(member.invoke(property)), constPool));
                } else if (type == int.class) {
                    anno.addMemberValue(member.getName(), new IntegerMemberValue((Integer) member.invoke(property), constPool));
                } else if (type == Long.class) {
                    anno.addMemberValue(member.getName(), new LongMemberValue((Long) member.invoke(property), constPool));
                }else if (type == Integer.class) {
                    anno.addMemberValue(member.getName(), new IntegerMemberValue((Integer) member.invoke(property), constPool));
                }else if (type == boolean.class) {
                    anno.addMemberValue(member.getName(), new BooleanMemberValue((Boolean) member.invoke(property), constPool));
                }else if (type == List.class || type == Array.class) {
                    anno.addMemberValue(member.getName(), new ArrayMemberValue((MemberValue) member.invoke(property), constPool));
//                      解决list 类型 貌似没啥用
//                    // 处理 List 类型
//                    List<?> listValue = (List<?>) member.invoke(property);
//                    MemberValue[] memberValues = new MemberValue[listValue.size()];
//                    for (int i = 0; i < listValue.size(); i++) {
//                        Object item = listValue.get(i);
//                        if (item instanceof Integer) {
//                            memberValues[i] = new IntegerMemberValue((Integer) item, constPool);
//                        } else if (item instanceof String) {
//                            memberValues[i] = new StringMemberValue((String) item, constPool);
//                        } else if (item instanceof Boolean) {
//                            memberValues[i] = new BooleanMemberValue((Boolean) item, constPool);
//                        }
//                    }
//                    anno.addMemberValue(member.getName(), new ArrayMemberValue(memberValues, constPool));

                }
            }
        }
        attr.addAnnotation(anno);
        ctField.getFieldInfo().addAttribute(attr);
        return ctField;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}