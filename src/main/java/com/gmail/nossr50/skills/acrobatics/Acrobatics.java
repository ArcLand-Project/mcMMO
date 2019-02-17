package com.gmail.nossr50.skills.acrobatics;

import com.gmail.nossr50.config.MainConfig;

public final class Acrobatics {
    public static double dodgeDamageModifier   = AdvancedConfig.getInstance().getDodgeDamageModifier();
    public static int dodgeXpModifier = ExperienceConfig.getInstance().getDodgeXPModifier();
    public static boolean dodgeLightningDisabled = MainConfig.getInstance().getDodgeLightningDisabled();

    private Acrobatics() {}

    protected static double calculateModifiedDodgeDamage(double damage, double damageModifier) {
        return Math.max(damage / damageModifier, 1.0);
    }
}
