package com.smarterfit.modules.billing.util;

import org.springframework.stereotype.Component;

import com.smarterfit.common.util.CryptoUtil;
import com.smarterfit.common.util.SensitiveDataDecryptor;
import com.smarterfit.modules.billing.dto.response.subscription.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.dto.response.subscriptionuser.SubscriptionUserResponseDTO;

@Component
public class SensitiveBillingDataDecryptor extends SensitiveDataDecryptor {
   public SensitiveBillingDataDecryptor(CryptoUtil cryptoUtil) {
      super(cryptoUtil);
   }

   public SubscriptionResponseDTO decrypt(SubscriptionResponseDTO subscription) {
      if (subscription != null && subscription.getOwner() != null) {
         decrypt(subscription.getOwner());
      }
      return subscription;
   }

   public SubscriptionUserResponseDTO decrypt(SubscriptionUserResponseDTO subscriptionUser) {
      if (subscriptionUser != null && subscriptionUser.getUser() != null) {
         decrypt(subscriptionUser.getUser());
      }

      if (subscriptionUser != null && subscriptionUser.getSubscription() != null) {
         decrypt(subscriptionUser.getSubscription());
      }
      return subscriptionUser;
   }
}
