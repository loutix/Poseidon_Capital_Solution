package org.ocr.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.RuleName;

@Data
public class RuleUpdateDTO {
    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String json;
    @NotBlank
    private String template;
    @NotBlank
    private String sqlStr;
    @NotBlank
    private String sqlPart;


    public RuleUpdateDTO(RuleName ruleName) {
        this.id = ruleName.getId();
        this.name = ruleName.getName();
        this.description = ruleName.getDescription();
        this.json = ruleName.getJson();
        this.template = ruleName.getTemplate();
        this.sqlStr = ruleName.getSqlStr();
        this.sqlPart = ruleName.getSqlPart();
    }


    public RuleUpdateDTO() {
    }

    public RuleName convertToRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setId(this.id);
        ruleName.setName(this.name);
        ruleName.setDescription(this.description);
        ruleName.setJson(this.json);
        ruleName.setTemplate(this.template);
        ruleName.setSqlStr(this.sqlStr);
        ruleName.setSqlPart(this.sqlPart);
        return ruleName;
    }


}
