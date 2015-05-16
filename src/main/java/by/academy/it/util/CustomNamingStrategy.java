package by.academy.it.util;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Created by alexanderleonovich on 13.05.15.
 * FOR WHAT THIS CLASS???
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {

    public String classToTableName(String className) {
        return "T_" + super.classToTableName(className).toUpperCase();
    }

    public String propertyToColumnName(String propName) {
        return "F_" + super.propertyToColumnName(propName);
    }

    public String columnName(String columnName) {
        return columnName;
    }

    public String tableName(String tableName) {
        return tableName;
    }
}
