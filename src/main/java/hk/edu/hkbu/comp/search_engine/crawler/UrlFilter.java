package hk.edu.hkbu.comp.search_engine.crawler;

import hk.edu.hkbu.comp.search_engine.utils.SensitiveFilterService;
import hk.edu.hkbu.comp.search_engine.utils.SensitiveType;

import java.util.regex.Pattern;

public class UrlFilter implements FilterTool {
    @Override
    public boolean accpect(String url) {
        //blacklist of url
        SensitiveFilterService filter = new SensitiveFilterService(SensitiveType.url);
        int level = filter.CheckSensitiveWord(url, 0, 1);
        if (level > 0)
            return false;
        if (
                url.contains("javascript:")
                        || url.contains("/*")
                        || url.contains("__")
        )
            return false;
        return true;
    }
}
