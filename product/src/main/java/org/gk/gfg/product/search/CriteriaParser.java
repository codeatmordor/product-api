package org.gk.gfg.product.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.gk.gfg.product.exception.ProductServiceException;
import org.gk.gfg.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Joiner;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class CriteriaParser {

  private static Logger log = LoggerFactory.getLogger(ProductService.class);
  private static Map<String, Operator> ops;

  private static String operatorString;

  static {
    final List<String> l = new ArrayList<>();
    for (final String v : SearchOperators.SIMPLE_OPERATION_SET) {
      l.add(v);
      l.add(String.format(" %s ", v));
      if (v == "=" || v == "<>") {
        l.add(String.format("%s ", v));
        l.add(String.format(" %s", v));
      }
    }
    operatorString = Joiner.on("|").join(l.toArray());
  }

  private static Pattern specificationCriteriaRegex =
      Pattern.compile("^([A-Za-z0-9_\\.-]*)(" + operatorString + ")([ A-Za-z0-9_\\.,'-\\[\\]]*)$");

  private enum Operator {
    OR(1), AND(2);
    final int precedence;

    Operator(final int p) {
      precedence = p;
    }
  }

  static {
    final Map<String, Operator> tempMap = new HashMap<>();
    tempMap.put("AND", Operator.AND);
    tempMap.put("OR", Operator.OR);
    tempMap.put("or", Operator.OR);
    tempMap.put("and", Operator.AND);

    ops = Collections.unmodifiableMap(tempMap);
  }

  private static boolean isHigherPrecedenceOperator(final String currOp, final String prevOp) {
    return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
  }

  public Deque<?> parse(final String searchParam,
      final SpecificationSearchCriteriaCreator criteriaCreator) throws ProductServiceException {

    final Deque<Object> output = new LinkedList<>();
    final Deque<String> stack = new LinkedList<>();
    try {
      if (!isQueryStringValueBalance(searchParam.trim())) {
        throw new ProductServiceException("where.clause.invalid");
      }

      final String modSearchParam = searchParam.replace("(", "~#~(~#~").replace(")", "~#~)~#~")
          .replace(" or ", "~#~or~#~").replace(" OR ", "~#~OR~#~").replace(" and ", "~#~and~#~")
          .replace(" AND ", "~#~AND~#~");
      Arrays.stream(modSearchParam.split("~#~")).forEach(token -> {
        token = token.trim();
        if (ops.containsKey(token)) {
          while (!stack.isEmpty() && isHigherPrecedenceOperator(token, stack.peek()))
            output.push(stack.pop().equalsIgnoreCase(SearchOperators.OR_OPERATOR)
                ? SearchOperators.OR_OPERATOR
                : SearchOperators.AND_OPERATOR);
          stack.push(
              token.equalsIgnoreCase(SearchOperators.OR_OPERATOR) ? SearchOperators.OR_OPERATOR
                  : SearchOperators.AND_OPERATOR);
        } else if (token.equals(SearchOperators.LEFT_PARANTHESIS)) {
          stack.push(SearchOperators.LEFT_PARANTHESIS);
        } else if (token.equals(SearchOperators.RIGHT_PARANTHESIS)) {
          while (!stack.peek().equals(SearchOperators.LEFT_PARANTHESIS))
            output.push(stack.pop());
          stack.pop();
        } else {
          final Matcher matcher = specificationCriteriaRegex.matcher(token);
          boolean f = matcher.find();
          if (f) {
            String gp1 = matcher.group(1);
            String gp2 = matcher.group(2);
            String gp3 = matcher.group(3);;
            output.push(criteriaCreator.create(gp1.trim(), gp2.trim(), "",
                gp3.replaceAll("'|\\[|\\]|^\\s+|\\s+$", "").trim(), ""));
          }
          /*
           * while (matcher.find()) { output.push(criteriaCreator.create(matcher.group(1).trim(),
           * matcher.group(2).trim(), "", matcher.group(3).replaceAll("'|\\[|\\]|^\\s+|\\s+$",
           * "").trim(), "")); }
           */
        }
      });

      while (!stack.isEmpty())
        output.push(stack.pop());
    } catch (final ProductServiceException tex) {
      throw tex;
    } catch (final Exception ex) {
      log.error("Failed to parse where clause : {}", searchParam);
      log.error("Error Stack Trace : {}", ex);
      throw new ProductServiceException("criteria.specification.creation.failed");
    }
    return output;
  }

  private boolean isQueryStringValueBalance(final String value) {
    if (value.contains("[") || value.contains("]") || value.contains("'") || value.contains("'")) {
      final char[] arr = value.toCharArray();
      final Deque<String> bracStack = new LinkedList<>();
      final List<String> quotesList = new ArrayList<>();
      try {
        for (final char a : arr) {
          if (a == '[') {
            bracStack.push(String.valueOf(a));
          } else if (a == '\'') {
            quotesList.add(String.valueOf(a));
          } else if (a == ']') {
            bracStack.pop();
          }
        }
      } catch (final Exception ex) {
        log.error("Query string value not balanced : {}", ex.getMessage());
        return false;
      }
      if (bracStack.size() > 0 | quotesList.size() % 2 != 0) {
        log.debug("brackets or quotes not matching");
        return false;
      }
    }
    return true;
  }

}
