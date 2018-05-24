package io.nebulas.schema;

/**
 * 打开调启App
 * Created by donald99 on 18/5/23.
 */

public class OpenAppSchema {

    /**
     * 获取schema url
     * @param paramsJSON json 字符串
     * @return schemaurl
     */
    public static String getSchemaUrl(String paramsJSON){
        return String.format("openapp.nasnano://virtual?params=%s", paramsJSON);
    }
}
