/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/battery/domain/sources/health_protocol.dart';

HealthProtocol parseHealthProtocols(String? state) {
  switch (state?.trim()) {
    case 'good':
      return HealthProtocol.good;
    case 'overheat':
      return HealthProtocol.overheat;
    case 'dead':
      return HealthProtocol.dead;
    case 'over_voltage':
      return HealthProtocol.overVoltage;
    case 'unspecified_failure':
      return HealthProtocol.unspecifiedFailure;
    case 'cold':
      return HealthProtocol.cold;
    case 'unknown':
      return HealthProtocol.unknown;
    default:
      throw ArgumentError('$state is not a valid health protocol.');
  }
}