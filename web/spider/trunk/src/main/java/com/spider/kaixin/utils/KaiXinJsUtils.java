package com.spider.kaixin.utils;

import java.io.FileReader;
import java.io.LineNumberReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class KaiXinJsUtils {
	private static Context cx= Context.enter();
    private static Scriptable scope=cx.initStandardObjects();


    public static Object runJavaScript(String filename) {
        String jsContent = getJsContent(filename);
        Object result = cx.evaluateString(scope, jsContent, filename, 1, null);
        return result;
    }

    private static String getJsContent(String filename) {
        LineNumberReader reader;
        try {
            reader = new LineNumberReader(new FileReader(filename));
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = reader.readLine()) != null) {
                sb.append(s).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Scriptable getScope() {
        return scope;
    }

    public static String getPassword(String oriPassword, String key) {
        String filename = System.getProperty("user.dir") + "/src/main/resources/kaixin/kaixin001.js";
        @SuppressWarnings("unused")
        Object result = runJavaScript(filename);
        Scriptable scope = getScope();
        Function getPassword = (Function) scope.get("getPassword", scope);
        Object password = getPassword.call(Context.getCurrentContext(), scope,
                getPassword, new Object[] { oriPassword, key });
        return Context.toString(password);
    }
}
