/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/battery/domain/sources/charge_protocol.dart';

ChargeProtocol parseChargeProtocols(String? state) {
  switch (state?.trim()) {
    case 'charging':
      return ChargeProtocol.charging;
    case 'discharging':
      return ChargeProtocol.discharging;
    case 'not_charging':
      return ChargeProtocol.notCharging;
    case 'full':
      return ChargeProtocol.full;
    case 'unknown':
      return ChargeProtocol.unknown;
    default:
      throw ArgumentError('$state is not a valid charge protocol.');
  }
}