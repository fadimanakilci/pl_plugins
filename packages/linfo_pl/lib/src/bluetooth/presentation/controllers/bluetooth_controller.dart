/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../../../../bluetooth.dart';
import '../../business/services/bluetooth_platform_interface.dart';

class BluetoothController extends BluetoothPlatformInterface {
  static BluetoothController? _singleton;

  BluetoothController._();

  factory BluetoothController() => _singleton ??= BluetoothController._();

  static BluetoothPlatformInterface get _platform => BluetoothPlatformInterface.instance;

  /// Returns a broadcast stream of events from the device bluetooth at the
  /// given sampling frequency.
  @override
  Stream<BluetoothProtocol> bluetoothEventStream() {
    return _platform.bluetoothEventStream();
  }

  @override
  Future<BluetoothProtocol?> getBluetoothEvent() {
    return _platform.getBluetoothEvent();
  }

  /// Stops returning the broadcast stream of events from the device
  /// bluetooth at the given sampling frequency.
  @override
  Future<bool> bluetoothStateOn() {
    return _platform.bluetoothStateOn();
  }

  @override
  Future<bool> bluetoothStateOff() {
    return _platform.bluetoothStateOff();
  }
}