package com.smarterfit.enums;

public enum SubscriptionStatus {
    PENDING, // Recém-criada, aguardando ativação
    ACTIVE, // Está ativa e válida
    EXPIRED, // Expirou naturalmente (data fim atingida)
    CANCELED // Cancelada manualmente (antes do vencimento)
}
