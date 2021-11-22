package voltskiya.apple.configs.module.configs;

import ycm.yml.manager.fields.YcmField;
import ycm.yml.manager.fields.YcmInlineComment;
import ycm.yml.manager.fields.YcmNewlineComment;

public class ConfigExample1 {
    @YcmField
    public static String val7 = "static vals don't get added";
    @YcmInlineComment("whoa comment 1")
    @YcmField
    public double val1 = 3.123345566;
    @YcmField
    @YcmInlineComment("whoa comment 2")
    public String val2 = "hello";
    @YcmNewlineComment("new line comment 3")
    @YcmField
    public String val3 = "person";
    @YcmField
    @YcmNewlineComment("new line comment 4")
    public int val4;
    @YcmField
    public OtherConfig val5 = new OtherConfig();
    @YcmField
    public String val6 = "hmm";

    public static class OtherConfig {
        @YcmField
        public String val51 = "inside";
        @YcmField
        public String val52 = "config";
    }
}
