package cloud.jillion.component.common.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class SPELUtils {

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 解析 spel 表达式
     *
     * @param method        方法
     * @param arguments     参数
     * @param spel          表达式
     * @param clazz         返回结果的类型
     * @param defaultResult 默认结果
     * @return 执行spel表达式后的结果
     */
    public static <T> T parseSPEL(Method method, Object[] arguments, String spel, Class<T> clazz, T defaultResult) {
        String[] params = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        Assert.notNull(params, "Method params is empty");
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        try {
            Expression expression = EXPRESSION_PARSER.parseExpression(spel);
            return expression.getValue(context, clazz);
        } catch (Exception e) {
            return defaultResult;
        }
    }
}
