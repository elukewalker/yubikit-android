/*
 * Copyright (C) 2019 Yubico.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yubico.yubikit.fido;

import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * Attestation Conveyance Preference Enumeration
 * WebAuthn Relying Parties may use AttestationConveyancePreference to specify their preference regarding attestation conveyance during credential generation.
 *
 * none
 * This value indicates that the Relying Party is not interested in authenticator attestation. For example, in order to potentially avoid having to obtain user consent to relay identifying information to the Relying Party, or to save a roundtrip to an Attestation CA.
 *
 * This is the default value.
 *
 * indirect
 * This value indicates that the Relying Party prefers an attestation conveyance yielding verifiable attestation statements, but allows the client to decide how to obtain such attestation statements. The client MAY replace the authenticator-generated attestation statements with attestation statements generated by an Anonymization CA, in order to protect the user’s privacy, or to assist Relying Parties with attestation verification in a heterogeneous ecosystem.
 *
 * Note: There is no guarantee that the Relying Party will obtain a verifiable attestation statement in this case. For example, in the case that the authenticator employs self attestation.
 *
 * direct
 * This value indicates that the Relying Party wants to receive the attestation statement as generated by the authenticator.
 *
 */
public enum AttestationConveyancePreference {
    NONE(com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference.NONE.toString()),
    INDIRECT(com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference.INDIRECT.toString()),
    DIRECT(com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference.DIRECT.toString());

    @NonNull
    private final String value;

    AttestationConveyancePreference(String attestation) {
        value = attestation;
    }

    public String toString() {
        return value;
    }

    public static AttestationConveyancePreference fromString(@NonNull String attestation) throws UnsupportedAttestationConveyancePreferenceException {
        if (attestation != null) {
            for (AttestationConveyancePreference attestationConveyancePreference : values()) {
                if (attestationConveyancePreference.value.equals(attestation)) {
                    return attestationConveyancePreference;
                }
            }
        }

        throw new UnsupportedAttestationConveyancePreferenceException(attestation);
    }

    com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference toAttestationConveyancePreference() {
        try {
            return com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference.fromString(value);
        } catch (com.google.android.gms.fido.fido2.api.common.AttestationConveyancePreference.UnsupportedAttestationConveyancePreferenceException e) {
            // this never happens because we check values on creation
            throw new IllegalStateException();
        }
    }

    public static class UnsupportedAttestationConveyancePreferenceException extends Exception {
        static final long serialVersionUID = 42L;

        public UnsupportedAttestationConveyancePreferenceException(String attachment) {
            super(String.format(Locale.ROOT, "Attestation conveyance preference %s not supported", attachment));
        }
    }
}
