package org.ocr.poseidon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ocr.poseidon.domain.RuleName;

@Data
@NoArgsConstructor
public class RuleCreateDTO {
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

     public RuleName convertToRuleName(){
         RuleName ruleName = new RuleName();
         ruleName.setName(this.name);
         ruleName.setDescription(this.description);
         ruleName.setJson(this.json);
         ruleName.setTemplate(this.template);
         ruleName.setSqlStr(this.sqlStr);
         ruleName.setSqlPart(this.sqlPart);
         return ruleName;
     }

}
