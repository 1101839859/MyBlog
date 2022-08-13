
public class Config {
    @com.alibaba.fastjson.annotation.JSONField(name = "title")
    public String title;
    @com.alibaba.fastjson.annotation.JSONField(name = "top_instruction")
    public String topInstruction;
    @com.alibaba.fastjson.annotation.JSONField(name = "selectsavebutton")
    public String selectsavebutton;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopInstruction() {
        return topInstruction;
    }

    public void setTopInstruction(String topInstruction) {
        this.topInstruction = topInstruction;
    }

    public String getSelectsavebutton() {
        return selectsavebutton;
    }

    public void setSelectsavebutton(String selectsavebutton) {
        this.selectsavebutton = selectsavebutton;
    }

    public String getAttributeLabel() {
        return attributeLabel;
    }

    public void setAttributeLabel(String attributeLabel) {
        this.attributeLabel = attributeLabel;
    }

    public String getDefaultloc() {
        return defaultloc;
    }

    public void setDefaultloc(String defaultloc) {
        this.defaultloc = defaultloc;
    }

    public String getStartbutton() {
        return startbutton;
    }

    public void setStartbutton(String startbutton) {
        this.startbutton = startbutton;
    }

    public String getSuccessNotice() {
        return successNotice;
    }

    public void setSuccessNotice(String successNotice) {
        this.successNotice = successNotice;
    }

    public String getFailedAttribute() {
        return failedAttribute;
    }

    public void setFailedAttribute(String failedAttribute) {
        this.failedAttribute = failedAttribute;
    }

    public String getFailedBase() {
        return failedBase;
    }

    public void setFailedBase(String failedBase) {
        this.failedBase = failedBase;
    }

    @com.alibaba.fastjson.annotation.JSONField(name = "attributeLabel")
    public String attributeLabel;
    @com.alibaba.fastjson.annotation.JSONField(name = "defaultloc")
    public String defaultloc;
    @com.alibaba.fastjson.annotation.JSONField(name = "startbutton")
    public String startbutton;
    @com.alibaba.fastjson.annotation.JSONField(name = "success_notice")
    public String successNotice;
    @com.alibaba.fastjson.annotation.JSONField(name = "failed_attribute")
    public String failedAttribute;
    @com.alibaba.fastjson.annotation.JSONField(name = "failed_base")
    public String failedBase;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @com.alibaba.fastjson.annotation.JSONField(name = "author")
    public String author;

    @com.alibaba.fastjson.annotation.JSONField(name = "warning_notice")
    public String warning_notice;

    public String getWarning_notice() {
        return warning_notice;
    }

    public void setWarning_notice(String warning_notice) {
        this.warning_notice = warning_notice;
    }
}
