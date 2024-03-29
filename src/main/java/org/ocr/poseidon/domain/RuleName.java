package org.ocr.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ocr.poseidon.interfaces.CrudEntity;

@Entity
@Data
@Table(name = "rulename")
public class RuleName implements CrudEntity<RuleName> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "name", length = 125)
    private String name;

    @Column(name = "description", length = 125)
    private String description;

    @Column(name = "json", length = 125)
    private String json;

    @Column(name = "template", length = 512)
    private String template;

    @Column(name = "sql_str", length = 125)
    private String sqlStr;

    @Column(name = "sql_part", length = 125)
    private String sqlPart;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public RuleName update(RuleName entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.json = entity.getJson();
        this.template = entity.getTemplate();
        this.sqlStr = entity.getSqlStr();
        this.sqlPart = entity.getSqlPart();
        return this;
    }
}
