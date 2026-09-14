package io.github.jcodeforge.invoice4jbase.ubl;

import java.util.Objects;

public final class UblConfigurationOptions {

    private final UblProfile profile;

    private final boolean prettyPrint;

    private UblConfigurationOptions(Builder builder) {
        this.profile = builder.profile;
        this.prettyPrint = builder.prettyPrint;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UblProfile getProfile() {
        return profile;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public static final class Builder {

        private UblProfile profile = UblProfile.EN16931;

        private boolean prettyPrint;

        public Builder profile(UblProfile profile) {
            this.profile = Objects.requireNonNull(profile, "profile must not be null");
            return this;
        }

        public Builder prettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            return this;
        }

        public UblConfigurationOptions build() {
            return new UblConfigurationOptions(this);
        }
    }
}