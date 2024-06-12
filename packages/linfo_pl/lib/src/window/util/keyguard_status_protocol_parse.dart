/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

import '../domain/sources/keyguard_status_protocol.dart';

KeyguardStatusProtocol parseKeyguardStatusProtocols(int? status) {
  switch (status) {
    case 0:
      return KeyguardStatusProtocol.error;
    case 1:
      return KeyguardStatusProtocol.succeeded;
    case 2:
      return KeyguardStatusProtocol.canceled;
    default:
      return KeyguardStatusProtocol.undefined;
  }
}