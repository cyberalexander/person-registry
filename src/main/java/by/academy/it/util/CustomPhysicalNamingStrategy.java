/*
 * MIT License
 *
 * Copyright (c) 2015-2021 Aliaksandr Leanovich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package by.academy.it.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;
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

    private String convertValue(final String name) {
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        return name.replaceAll(regex, replacement).toLowerCase(Locale.getDefault());
    }
}
