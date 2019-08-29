package me.piggypiglet.framework.bootstrap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------

/**
 * Boot priorities available to startup registerables, ran in the order as defined in here
 */
public enum BootPriority {
    BEFORE_IMPL,
    IMPL,
    AFTER_IMPL,
    BEFORE_MANUAL,
    MANUAL,
    AFTER_MANUAL,
    BEFORE_COMMANDS,
    COMMANDS,
    AFTER_COMMANDS,
    BEFORE_ADDONS,
    ADDONS,
    AFTER_ADDONS,
    BEFORE_SHUTDOWN,
    SHUTDOWN,
    AFTER_SHUTDOWN
}
