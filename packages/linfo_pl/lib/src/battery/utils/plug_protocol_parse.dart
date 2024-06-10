/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/battery/domain/sources/plug_protocol.dart';

PlugProtocol parsePlugProtocols(String? state) {
  switch (state?.trim()) {
    case 'ac':
      return PlugProtocol.ac;
    case 'usb':
      return PlugProtocol.usb;
    case 'wireless':
      return PlugProtocol.wireless;
    case 'dock':
      return PlugProtocol.dock;
    case 'discharging':
      return PlugProtocol.discharging;
    case 'unknown':
      return PlugProtocol.unknown;
    default:
      throw ArgumentError('$state is not a valid plug protocol.');
  }
}