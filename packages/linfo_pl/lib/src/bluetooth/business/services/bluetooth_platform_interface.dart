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

import '../../../../bluetooth.dart';
import '../controllers/bluetooth_channel_controller.dart';

abstract class BluetoothPlatformInterface extends PlatformInterface {
  /// Constructs a LinfoPlPlatform.
  BluetoothPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static BluetoothPlatformInterface _instance = BluetoothChannelController();

  /// The default instance of [BluetoothPlatformInterface] to use.
  ///
  /// Defaults to [MethodChannelLinfoPl].
  static BluetoothPlatformInterface get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BluetoothPlatformInterface] when
  /// they register themselves.
  static set instance(BluetoothPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// Returns a broadcast stream of events from the device bluetooth at the
  /// given sampling frequency.
  Stream<BluetoothProtocol> bluetoothEventStream() {
    throw UnimplementedError(
        'bluetoothEventStream has not been implemented.');
  }

  Future<BluetoothProtocol?> getBluetoothEvent() {
    throw UnimplementedError('getBluetoothEvent has not been implemented.');
  }

  /// Stops returning the broadcast stream of events from the device
  /// bluetooth at the given sampling frequency.
  Future<bool> bluetoothStateOn() {
    throw ArgumentError('bluetoothStateOn has not been implemented.');
  }

  Future<bool> bluetoothStateOff() {
    throw ArgumentError('bluetoothStateOff has not been implemented.');
  }

  /// Stops returning the broadcast stream of events from the device
  /// bluetooth because the stream has timeout at the given sampling frequency.
  void onTimeout([String taskId ='Example']) {
    throw StateError("[BackgroundFetch] task timed-out without onTimeout callback: "
        "$taskId.  You should provide an onTimeout callback to BackgroundFetch.configure.");
  }
}