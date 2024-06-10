/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'src/network/domain/sources/network_protocol.dart';
import 'src/network/presentation/controllers/network_controller.dart';

export 'package:linfo_pl/src/network/domain/sources/network_protocol.dart' show NetworkProtocol;

final _network = NetworkController();

/// Returns a broadcast stream of events from the device network at the
/// given sampling frequency.
@override
Stream<List<NetworkProtocol>> networkEventStream() {
  return _network.networkEventStream();
}

@override
Future<List<NetworkProtocol>?> getNetworkEvent() {
  return _network.getNetworkEvent();
}

@override
Future<bool> requestOnMobile() {
  return _network.requestOnMobile();
}

/// Stops returning the broadcast stream of events from the device
/// network at the given sampling frequency.
@override
void stopsNetworkEventStream() {
  return _network.stopsNetworkEventStream();
}
