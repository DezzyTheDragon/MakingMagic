/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.util;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

/**
 * Equivalent to {@link Consumer}, except with nonnull contract.
 * 
 * @see Consumer
 */
@FunctionalInterface
public interface NonNullConsumer<T>
{
    void accept(@Nonnull T t);
}
