/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../../../battery.dart';
import '../../domain/sources/battery_protocol.dart';
import '../../domain/sources/charge_protocol.dart';
import '../controllers/battery_channel_controller.dart';

abstract class BatteryPlatformInterface extends PlatformInterface {
  /// Constructs a LinfoPlPlatform.
  BatteryPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static BatteryPlatformInterface _instance = BatteryChannelController();

  /// The default instance of [BatteryPlatformInterface] to use.
  ///
  /// Defaults to [MethodChannelLinfoPl].
  static BatteryPlatformInterface get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BatteryPlatformInterface] when
  /// they register themselves.
  static set instance(BatteryPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// Returns a broadcast stream of events from the device battery at the
  /// given sampling frequency.
  Stream<BatteryModel> batteryEventStream() {
    throw UnimplementedError(
        'batteryEventStream has not been implemented.');
  }

  Stream<int> batteryLevelStream() {
    throw UnimplementedError(
        'batteryLevelStream has not been implemented.');
  }

  Stream<ChargeProtocol> chargingStream() {
    throw UnimplementedError(
        'isChargingStream has not been implemented.');
  }

  Future<int?> getBatteryLevel() {
    throw UnimplementedError('getBatteryEvent has not been implemented.');
  }

  /// Stops returning the broadcast stream of events from the device
  /// battery because the stream has timeout at the given sampling frequency.
  void onTimeout([String taskId ='Example']) {
    throw StateError("[BackgroundFetch] task timed-out without onTimeout callback: "
        "$taskId.  You should provide an onTimeout callback to BackgroundFetch.configure.");
  }
}