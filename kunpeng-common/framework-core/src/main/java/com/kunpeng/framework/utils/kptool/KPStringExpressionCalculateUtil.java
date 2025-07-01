package com.kunpeng.framework.utils.kptool;


import bsh.EvalError;
import bsh.Interpreter;

/**
 * @Author lipeng
 * @Description 计算字符串表达式
 * @Date 2020/10/10 9:44
 * @Param
 * @return
 **/
public final class KPStringExpressionCalculateUtil {
    private Interpreter interpreter = null;
    private String value;

    private KPStringExpressionCalculateUtil(){}

    public KPStringExpressionCalculateUtil(String expression) throws EvalError {
        if(interpreter==null)
            interpreter = new Interpreter();
        Object object=interpreter.eval(expression);
        value = object.toString();
    }

    public static void main(String[] args) {
        try {

            Interpreter interpreter = new Interpreter();
            Object object=interpreter.eval("return 65656565==65656561");

            System.out.println(object.toString());;
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }

    }


    public Boolean getBoolean(){
        return  Boolean.valueOf(this.value);
    }

    public String getString(){
        return this.value;
    }

    public String getRound(){
        return String.format("%.2f", Double.valueOf(this.value));
    }

}
