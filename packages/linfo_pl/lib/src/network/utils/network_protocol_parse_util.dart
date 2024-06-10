/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/network/domain/sources/network_protocol.dart';

List<NetworkProtocol> parseNetworkProtocols(List<String> states) {
  return states.map((state) {
    switch (state.trim()) {
      case 'wifi':
        return NetworkProtocol.wifi;
      case 'mobile':
        return NetworkProtocol.mobile;
      case 'ethernet':
        return NetworkProtocol.ethernet;
      case 'bluetooth':
        return NetworkProtocol.bluetooth;
      case 'vpn':
        return NetworkProtocol.vpn;
      case 'other':
        return NetworkProtocol.other;
      default:
        return NetworkProtocol.none;
    }
  }).toList();
}