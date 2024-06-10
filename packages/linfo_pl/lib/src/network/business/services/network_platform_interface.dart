/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/network/business/controllers/network_channel_controller.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../domain/sources/network_protocol.dart';

abstract class NetworkPlatformInterface extends PlatformInterface {
  /// Constructs a LinfoPlPlatform.
  NetworkPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static NetworkPlatformInterface _instance = NetworkChannelController();

  /// The default instance of [NetworkPlatformInterface] to use.
  ///
  /// Defaults to [MethodChannelLinfoPl].
  static NetworkPlatformInterface get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [NetworkPlatformInterface] when
  /// they register themselves.
  static set instance(NetworkPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// Returns a broadcast stream of events from the device network at the
  /// given sampling frequency.
  Stream<List<NetworkProtocol>> networkEventStream() {
    throw UnimplementedError(
        'listenToNetworkEvents has not been implemented.');
  }

  Future<List<NetworkProtocol>?> getNetworkEvent() {
    throw UnimplementedError('getNetworkEvent has not been implemented.');
  }

  Future<bool> requestOnMobile() {
    throw UnimplementedError('requestOnMobile has not been implemented.');
  }

  /// Stops returning the broadcast stream of events from the device
  /// network at the given sampling frequency.
  void stopsNetworkEventStream([String taskId = 'Example']) {
    throw ArgumentError('Unable to taskId - $taskId');
  }

  /// Stops returning the broadcast stream of events from the device
  /// network because the stream has timeout at the given sampling frequency.
  void onTimeout([String taskId ='Example']) {
    throw StateError("[BackgroundFetch] task timed-out without onTimeout callback: "
        "$taskId.  You should provide an onTimeout callback to BackgroundFetch.configure.");
  }
}