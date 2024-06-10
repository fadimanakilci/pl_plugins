/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/network/business/services/network_platform_interface.dart';

import '../../domain/sources/network_protocol.dart';

class NetworkController extends NetworkPlatformInterface {
  static NetworkController? _singleton;

  NetworkController._();

  factory NetworkController() => _singleton ??= NetworkController._();

  static NetworkPlatformInterface get _platform => NetworkPlatformInterface.instance;

  /// Returns a broadcast stream of events from the device network at the
  /// given sampling frequency.
  @override
  Stream<List<NetworkProtocol>> networkEventStream() {
    return _platform.networkEventStream();
  }

  @override
  Future<List<NetworkProtocol>?> getNetworkEvent() {
    return _platform.getNetworkEvent();
  }

  @override
  Future<bool> requestOnMobile() {
    return _platform.requestOnMobile();
  }

  /// Stops returning the broadcast stream of events from the device
  /// network at the given sampling frequency.
  @override
  void stopsNetworkEventStream([String taskId = 'Example']) {
    return _platform.stopsNetworkEventStream();
  }
}