/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/power/domain/sources/thermal_status_protocol.dart';

ThermalStatusProtocol parseThermalStatusProtocols(String? state) {
  switch (state?.trim()) {
    case 'none':
      return ThermalStatusProtocol.none;
    case 'light':
      return ThermalStatusProtocol.light;
    case 'moderate':
      return ThermalStatusProtocol.moderate;
    case 'severe':
      return ThermalStatusProtocol.severe;
    case 'critical':
      return ThermalStatusProtocol.critical;
    case 'emergency':
      return ThermalStatusProtocol.emergency;
    case 'shutdown':
      return ThermalStatusProtocol.shutdown;
    case 'unknown':
      return ThermalStatusProtocol.unknown;
    default:
      throw ArgumentError('$state is not a valid thermal status protocol.');
  }
}