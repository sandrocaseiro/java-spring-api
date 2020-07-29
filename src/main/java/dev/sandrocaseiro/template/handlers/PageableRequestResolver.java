package dev.sandrocaseiro.template.handlers;

import dev.sandrocaseiro.template.exceptions.PageableBadRequestException;
import dev.sandrocaseiro.template.models.DPageable;
import dev.sandrocaseiro.template.utils.NumberUtil;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageableRequestResolver implements HandlerMethodArgumentResolver {
    private static final String SORT_PATTERN = "^(\\-?)(.*)";
    private static final String OFFSET_PARAM = "$pageoffset";
    private static final String LIMIT_PARAM = "$pagelimit";
    private static final String SORT_PARAM = "$sort";

    private static final Pattern pattern = Pattern.compile(SORT_PATTERN);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(DPageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        int pageOffset;
        try {
            pageOffset = NumberUtil.toInt(nativeWebRequest.getParameter(OFFSET_PARAM), DPageable.CURRENT_PAGE);
        }
        catch (NumberFormatException e) {
            throw new PageableBadRequestException(OFFSET_PARAM, e);
        }
        int pageLimit;
        try {
            pageLimit = NumberUtil.toInt(nativeWebRequest.getParameter(LIMIT_PARAM), DPageable.PAGE_SIZE);
        }
        catch (NumberFormatException e) {
            throw new PageableBadRequestException(LIMIT_PARAM, e);
        }

        if (pageOffset <= 0)
            throw new PageableBadRequestException(OFFSET_PARAM);

        String sort = nativeWebRequest.getParameter(SORT_PARAM);
        String[] requestSortFields = new String[0];
        if (!StringUtils.isEmpty(sort))
            requestSortFields = sort.split(",");

        List<DPageable.Sort> sortFields = new ArrayList<>(requestSortFields.length);
        for (String sortField : requestSortFields) {
            Matcher matcher = pattern.matcher(sortField.trim());
            if (!matcher.matches())
                throw new PageableBadRequestException(SORT_PARAM);

            String signal = matcher.group(1);
            String field = matcher.group(2);

            if (!StringUtils.isEmpty(signal) && !"-".equals(signal))
                throw new PageableBadRequestException(SORT_PARAM);

            sortFields.add(new DPageable.Sort(field, "-".equals(signal)));
        }

        return new DPageable(pageOffset, pageLimit, sortFields);
    }
}
