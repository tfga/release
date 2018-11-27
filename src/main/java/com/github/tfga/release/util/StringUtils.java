package com.github.tfga.release.util;


import static com.github.underscore.lodash.U.chain;

import java.util.List;

import com.github.underscore.lodash.U;
import com.github.underscore.lodash.U.Chain;

public class StringUtils {

    public static <T> String join(List<T> args, String delimiter, Formatter<T> formatter) {
        
        return chain(args)
                        .map(formatter::format)
                        .join(delimiter)
                        .item() // Duh...
                        ;
    }
}
