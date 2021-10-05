package by.academy.it.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Objects;

/**
 * Hibernate 5 provides two different naming strategies for use with Hibernate entities:
 * 1) an Implicit Naming Strategy
 * 2) and a Physical Naming Strategy.
 * <p>
 * The Implicit Naming Strategy governs how Hibernate derives a logical name from our Java class and property names.
 * We can select from four built-in strategies, or we can create our own.
 * <p>
 * Hibernate uses the Physical Naming Strategy to map our logical names to a SQL table and its columns.
 * Example taken from:
 * @link https://www.baeldung.com/hibernate-naming-strategy
 * Created by alexanderleonovich on 13.05.15.
 */
public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalCatalogName(final Identifier name, final JdbcEnvironment context) {
        return toSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier name, final JdbcEnvironment context) {
        return toSnakeCase("f_", name);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier name, final JdbcEnvironment context) {
        return toSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier name, final JdbcEnvironment context) {
        return toSnakeCase(name);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier name, final JdbcEnvironment context) {
        return toSnakeCase("t_", name);
    }

    private Identifier toSnakeCase(final String prefix, final Identifier name) {
        if (Objects.isNull(name)) {
            return name;
        }
        return Identifier.toIdentifier(prefix.concat(convertValue(name.getText())));
    }

    private Identifier toSnakeCase(final Identifier name) {
        if (Objects.isNull(name)) {
            return name;
        }
        return Identifier.toIdentifier(convertValue(name.getText()));
    }

    private String convertValue(String name) {
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        return name.replaceAll(regex, replacement).toLowerCase();
    }
}
