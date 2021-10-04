package by.academy.it.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Objects;

/**
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
        return Identifier.toIdentifier(prefix.concat(toSnakeCase(name).getText()));
    }

    private Identifier toSnakeCase(final Identifier name) {
        if (Objects.isNull(name)) {
            return name;
        }
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = name.getText()
            .replaceAll(regex, replacement)
            .toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
